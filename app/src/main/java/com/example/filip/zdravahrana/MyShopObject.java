package com.example.filip.zdravahrana;

/**
 * Created by Filip on 1/18/2018.
 */

public class MyShopObject
{
    String title;
    double value;
    short type;



    public boolean isInCard() {
        return isInCard;
    }

    public void setInCard(boolean inCard) {
        isInCard = inCard;
    }

    boolean isInCard;



    public MyShopObject(String title, double value) {
        this.title = title;
        this.value = value;

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public short getType() {
        return type;
    }

    public void setType(short type) {
        this.type = type;
    }

}
