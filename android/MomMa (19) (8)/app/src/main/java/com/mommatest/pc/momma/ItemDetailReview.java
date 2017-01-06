package com.mommatest.pc.momma;

/**
 * Created by uhees on 2016-12-30.
 */

public class ItemDetailReview {
    String num;
    String user;
    String title;
    float rate;

    public ItemDetailReview(String num, String title, String user, float rate)
    {
        this.num = num;
        this.title = title;
        this.user = user;
        this.rate = rate;
    }
}
