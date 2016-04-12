package com.example.learndraw;

/**
 * Created by Ahmed on 30.01.14.
 */
public class MagnificentChartItem {

// #MARK - Constants

    public int color;
    public int value;
    public String title;
    public String price;

// #MARK - Constructors

   public MagnificentChartItem(String title, int value, int color, String price){
        this.color = color;
        this.value = value;
        this.title = title;
        this.price = price;
    }

}