package com.mommatest.pc.momma;

public class ItemDataLevel {
    int image, level;
    String num, name;
    float rating;


    public ItemDataLevel(int image, String num, String name, int level, float rating)
    {
        this.image = image;
        this.num = num;
        this.name = name;
        this.level = level;
        this.rating = rating;
    }
}