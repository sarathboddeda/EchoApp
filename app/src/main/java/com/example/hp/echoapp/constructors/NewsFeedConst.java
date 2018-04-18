package com.example.hp.echoapp.constructors;

/**
 * Created by HP on 2/2/2018.
 */

public class NewsFeedConst {
    private String feedtitle,feedlink,feedimage;
    private int newsimage;

    public NewsFeedConst(String feedtitle, String feedlink, String feedimage) {
        this.feedtitle = feedtitle;
        this.feedlink = feedlink;
        this.feedimage = feedimage;
    }

    public String getFeedtitle() {
        return feedtitle;
    }

    public void setFeedtitle(String feedtitle) {
        this.feedtitle = feedtitle;
    }

    public String getFeedlink() {
        return feedlink;
    }

    public void setFeedlink(String feedlink) {
        this.feedlink = feedlink;
    }

    public String getFeedimage() {
        return feedimage;
    }

    public void setFeedimage(String feedimage) {
        this.feedimage = feedimage;
    }
}
