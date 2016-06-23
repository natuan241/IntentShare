package com.natuan.intentshare;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button facebook, twitter, snapchat, whatsapp, line, skype, pick;
    private ShareEnum shareEnum;

    private final int SELECT_PHOTO = 1;
    private ImageView imageView;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //facebook,twitter,snapchat,whatsapp,line,skype

        initViews();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void initViews() {
        pick = (Button) findViewById(R.id.pick);
        imageView = (ImageView) findViewById(R.id.imageView);
        facebook = (Button) findViewById(R.id.facebook);
        twitter = (Button) findViewById(R.id.twitter);
        snapchat = (Button) findViewById(R.id.snapchat);
        whatsapp = (Button) findViewById(R.id.whatsapp);
        line = (Button) findViewById(R.id.line);
        skype = (Button) findViewById(R.id.skype);

        pick.setOnClickListener(this);
        facebook.setOnClickListener(this);
        twitter.setOnClickListener(this);
        snapchat.setOnClickListener(this);
        whatsapp.setOnClickListener(this);
        line.setOnClickListener(this);
        skype.setOnClickListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        switch (requestCode) {
            case SELECT_PHOTO:
                if (resultCode == RESULT_OK) {
                    try {
                        imageUri = imageReturnedIntent.getData();
                        final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                        final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                        imageView.setImageBitmap(selectedImage);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                }
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pick:
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, SELECT_PHOTO);
                break;
            case R.id.facebook:
                shareEnum = ShareEnum.FACEBOOK;
                break;
            case R.id.twitter:
                shareEnum = ShareEnum.TWITTER;
                break;
            case R.id.snapchat:
                shareEnum = ShareEnum.SNAPCHAT;
                break;
            case R.id.whatsapp:
                shareEnum = ShareEnum.WhATSAPP;
                break;
            case R.id.line:
                shareEnum = ShareEnum.LINE;
                break;
            case R.id.skype:
                shareEnum = ShareEnum.SKYPE;
                break;
            default:
                break;
        }

        if (shareEnum == null)
            return;
        /**
         * Show share dialog BOTH image and text
         */
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.setPackage(shareEnum.name);
        shareIntent.putExtra(Intent.EXTRA_TEXT, "Share");
        shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
        shareIntent.setType("image/jpeg");
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        try {
            startActivity(shareIntent);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, shareEnum.toString() + " have not been installed.", Toast.LENGTH_SHORT).show();
        }
    }
}
