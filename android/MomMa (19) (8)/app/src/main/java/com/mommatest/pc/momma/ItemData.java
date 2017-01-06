package com.mommatest.pc.momma;

public class ItemData  {
    String num;
    int image;
    int imagelevel;
    String name;
    String level;
    float rating;

    public ItemData(String num, int image,int imagelevel, String name, String level, float rating)
    {
        this.num = num;
        this.image = image;
        this.imagelevel = imagelevel;
        this.name = name;
        this.level = level;
        this.rating = rating;
    }

    /*public String getTitle(){
        return title;
    }*/
}
