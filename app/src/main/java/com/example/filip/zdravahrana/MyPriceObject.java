package com.example.filip.zdravahrana;

/**
 * Created by Filip on 12/24/2018.
 */

public class MyPriceObject {
    String title;
    String value;
    short type;

    public MyPriceObject(double kikiriki) {

    }

    public boolean isInCard() {
        return isInCard;
    }

    public void setInCard(boolean inCard) {
        isInCard = inCard;
    }

    boolean isInCard;



    public MyPriceObject(String title, String value) {
        this.title = title;
        this.value = value;

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public short getType() {
        return type;
    }

    public void setType(short type) {
        this.type = type;
    }
}
