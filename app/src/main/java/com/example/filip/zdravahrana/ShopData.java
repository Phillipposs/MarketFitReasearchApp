package com.example.filip.zdravahrana;

import android.provider.BaseColumns;

/**
 * Created by Filip on 12/21/2018.
 */

final class ShopData {


    private int id;
    private String name;
    private String type;
    private double longitude;
    private double latitude;
    private double kikiriki;
    private double susam;
    private double socivo;
    private double djumbir;
    private double ovsene_pahulje;
    private double kari;
    private double semenke;
    private double suncokret;
    private double cimet;
    private double korn_fleks;

    public ShopData() {

    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getOvsene_pahulje() {
        return ovsene_pahulje;
    }

    public void setOvsene_pahulje(double ovsene_pahulje) {
        this.ovsene_pahulje = ovsene_pahulje;
    }

    public double getKari() {
        return kari;
    }

    public void setKari(double kari) {
        this.kari = kari;
    }

    public double getSemenke() {
        return semenke;
    }

    public void setSemenke(double semenke) {
        this.semenke = semenke;
    }

    public double getSuncokret() {
        return suncokret;
    }

    public void setSuncokret(double suncokret) {
        this.suncokret = suncokret;
    }

    public double getCimet() {
        return cimet;
    }

    public void setCimet(double cimet) {
        this.cimet = cimet;
    }

    public double getKorn_fleks() {
        return korn_fleks;
    }

    public void setKorn_fleks(double korn_fleks) {
        this.korn_fleks = korn_fleks;
    }

    public double getKikiriki() {
        return kikiriki;
    }

    public void setKikiriki(double kikiriki) {
        this.kikiriki = kikiriki;
    }

    public double getSusam() {
        return susam;
    }

    public void setSusam(double susam) {
        this.susam = susam;
    }

    public double getSocivo() {
        return socivo;
    }

    public void setSocivo(double socivo) {
        this.socivo = socivo;
    }

    public double getDjumbir() {
        return djumbir;
    }

    public void setDjumbir(double djumbir) {
        this.djumbir = djumbir;
    }


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public static class ShopEntry implements BaseColumns {

        public static final String TABLE_NAME = "shops";
        public static final String COLUMN_NAME_TITLE = "Shop_Name";
        public static final String COLUMN_NAME_ADDRESS = "Adresa";
        public static final String COLUMN_NAME_LONGITUDE = "Longitude";
        public static final String COLUMN_NAME_LATITUDE = "Latitiude";


    }

    public static class ShopPrices implements BaseColumns {

        public static final String TABLE_NAME = "prices";
        public static final String COLUMN_NAME_SHOP_NAME = "Shop_Name";
        public static final String COLUMN_NAME_SUSAM = "Susam";
        public static final String COLUMN_NAME_KIKIRIKI = "Kikiriki";
        public static final String COLUMN_NAME_SOCIVO = "Socivo";
        public static final String COLUMN_NAME_DJUMBIR = "Djumbir";
        public static final String COLUMN_NAME_OVSENE_PAHULJE = "Ovsene_Pahulje";
        public static final String COLUMN_NAME_KARI = "Kari";
        public static final String COLUMN_NAME_SEMENKE = "Semenke";
        public static final String COLUMN_NAME_SUNCOKRET = "Suncokret";
        public static final String COLUMN_NAME_CIMET = "Cimet";
        public static final String COLUMN_NAME_KORN_FLEKS = "Korn_Fleks";


    }

    public static class PublicBuildingsEntry implements BaseColumns {

        public static final String TABLE_NAME = "public_buildings";
        public static final String COLUMN_NAME_TITLE = "Building_Name";
        public static final String COLUMN_NAME_TYPE = "Type";
        public static final String COLUMN_NAME_LONGITUDE = "Longitude";
        public static final String COLUMN_NAME_LATITUDE = "Latitiude";


    }


}
