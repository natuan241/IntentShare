package com.natuan.intentshare;

/**
 * Created by TuanNA on 6/23/2016.
 */

public enum ShareEnum {
    FACEBOOK(1,"com.facebook.katana"),TWITTER(2,"com.twitter.android"),LINE(3,"jp.naver.line.android"),SNAPCHAT(4,"com.snapchat.android"),WhATSAPP(5,"com.whatsapp"),SKYPE(6,"com.skype.raider");
    public int post;
    public String name;

    ShareEnum(int post, String name) {
        this.post = post;
        this.name = name;
    }
}
