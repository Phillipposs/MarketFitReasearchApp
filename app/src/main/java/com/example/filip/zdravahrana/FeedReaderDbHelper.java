package com.example.filip.zdravahrana;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import java.util.ArrayList;

/**
 * Created by Filip on 12/21/2018.
 */

public class FeedReaderDbHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    private Context context;
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "zdrava_hrana7";
    private static final String SQL_CREATE_SHOPS =
            "CREATE TABLE IF NOT EXISTS " + ShopData.ShopEntry.TABLE_NAME + " (" +
                    ShopData.ShopEntry._ID + " INTEGER PRIMARY KEY," +
                    ShopData.ShopEntry.COLUMN_NAME_TITLE + " TEXT," +
                    ShopData.ShopEntry.COLUMN_NAME_LONGITUDE + " REAL," +
                    ShopData.ShopEntry.COLUMN_NAME_LATITUDE + " REAL " + ")";
    private static final String SQL_CREATE_PRICES =
            "CREATE TABLE IF NOT EXISTS " + ShopData.ShopPrices.TABLE_NAME + " (" +
                    ShopData.ShopPrices._ID + " INTEGER PRIMARY KEY," +
                    ShopData.ShopPrices.COLUMN_NAME_SHOP_NAME + " TEXT," +
                    ShopData.ShopPrices.COLUMN_NAME_KIKIRIKI + " REAL," +
                    ShopData.ShopPrices.COLUMN_NAME_SUSAM + " REAL, " +
                    ShopData.ShopPrices.COLUMN_NAME_SOCIVO + " REAL, " +
                    ShopData.ShopPrices.COLUMN_NAME_DJUMBIR + " REAL, " +
                    ShopData.ShopPrices.COLUMN_NAME_OVSENE_PAHULJE + " REAL, " +
                    ShopData.ShopPrices.COLUMN_NAME_KARI + " REAL, " +
                    ShopData.ShopPrices.COLUMN_NAME_SEMENKE + " REAL, " +
                    ShopData.ShopPrices.COLUMN_NAME_SUNCOKRET + " REAL, " +
                    ShopData.ShopPrices.COLUMN_NAME_CIMET + " REAL, " +
                    ShopData.ShopPrices.COLUMN_NAME_KORN_FLEKS + " REAL " +
                    ")";
    private static final String SQL_CREATE_PUBLIC_BUILDING =
            "CREATE TABLE IF NOT EXISTS " + ShopData.PublicBuildingsEntry.TABLE_NAME + " (" +
                    ShopData.PublicBuildingsEntry._ID + " INTEGER PRIMARY KEY," +
                    ShopData.PublicBuildingsEntry.COLUMN_NAME_TITLE + " TEXT," +
                    ShopData.PublicBuildingsEntry.COLUMN_NAME_TYPE + " TEXT," +
                    ShopData.PublicBuildingsEntry.COLUMN_NAME_LONGITUDE + " REAL," +
                    ShopData.PublicBuildingsEntry.COLUMN_NAME_LATITUDE + " REAL " + ")";
    private static final String SQL_DELETE_ENTRIES;

    static {
        SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + ShopData.ShopEntry.TABLE_NAME;
    }

    public FeedReaderDbHelper(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_SHOPS);
        db.execSQL(SQL_CREATE_PRICES);
        db.execSQL(SQL_CREATE_PUBLIC_BUILDING);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public void insertShop(SQLiteDatabase db, int id, String title, double latitude, double longitude) {


        ContentValues values = new ContentValues();
        values.put(ShopData.ShopEntry._ID, id);
        values.put(ShopData.ShopEntry.COLUMN_NAME_TITLE, title);
        values.put(ShopData.ShopEntry.COLUMN_NAME_LATITUDE, latitude);
        values.put(ShopData.ShopEntry.COLUMN_NAME_LONGITUDE, longitude);


// Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(ShopData.ShopEntry.TABLE_NAME, null, values);
    }

    public void insertPublicBuildings(SQLiteDatabase db, int id, String title, String type, double latitude, double longitude) {


        ContentValues values = new ContentValues();
        values.put(ShopData.PublicBuildingsEntry._ID, id);
        values.put(ShopData.PublicBuildingsEntry.COLUMN_NAME_TITLE, title);
        values.put(ShopData.PublicBuildingsEntry.COLUMN_NAME_TYPE, type);
        values.put(ShopData.PublicBuildingsEntry.COLUMN_NAME_LATITUDE, latitude);
        values.put(ShopData.PublicBuildingsEntry.COLUMN_NAME_LONGITUDE, longitude);


// Insert the new row, returning the primary key value of the new row
        long newRowId1 = db.insert(ShopData.PublicBuildingsEntry.TABLE_NAME, null, values);
    }

    public void insertPrices(SQLiteDatabase db, int id, String name, double kikiriki, double susam, double socivo, double djumbir, double ovsene_pahulje, double kari, double semenke, double suncokret, double cimet, double korn_fleks) {
        ContentValues values = new ContentValues();
        values.put(ShopData.ShopPrices._ID, id);
        values.put(ShopData.ShopPrices.COLUMN_NAME_SHOP_NAME, name);
        values.put(ShopData.ShopPrices.COLUMN_NAME_KIKIRIKI, kikiriki);
        values.put(ShopData.ShopPrices.COLUMN_NAME_SUSAM, susam);
        values.put(ShopData.ShopPrices.COLUMN_NAME_SOCIVO, socivo);
        values.put(ShopData.ShopPrices.COLUMN_NAME_DJUMBIR, djumbir);
        values.put(ShopData.ShopPrices.COLUMN_NAME_OVSENE_PAHULJE, ovsene_pahulje);
        values.put(ShopData.ShopPrices.COLUMN_NAME_KARI, kari);
        values.put(ShopData.ShopPrices.COLUMN_NAME_SEMENKE, semenke);
        values.put(ShopData.ShopPrices.COLUMN_NAME_SUNCOKRET, suncokret);
        values.put(ShopData.ShopPrices.COLUMN_NAME_CIMET, cimet);
        values.put(ShopData.ShopPrices.COLUMN_NAME_KORN_FLEKS, korn_fleks);

        long newRowId = db.insert(ShopData.ShopPrices.TABLE_NAME, null, values);
    }

    public ArrayList<ShopData> getShopData() {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<ShopData> shopDatas = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + ShopData.ShopEntry.TABLE_NAME;
        Cursor cursor = db.rawQuery(selectQuery, null);
      /*  Cursor cursor = db.query(ShopData.ShopEntry.TABLE_NAME,
                new String[]{ShopData.ShopEntry._ID, ShopData.ShopEntry.COLUMN_NAME_TITLE, ShopData.ShopEntry.COLUMN_NAME_LONGITUDE, ShopData.ShopEntry.COLUMN_NAME_LATITUDE},
                ShopData.ShopEntry._ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
*/
        if (cursor.moveToFirst()) {
            do {
                ShopData shopData = new ShopData();
                shopData.setId(cursor.getInt(cursor.getColumnIndex(ShopData.ShopEntry._ID)));
                shopData.setName(cursor.getString(cursor.getColumnIndex(ShopData.ShopEntry.COLUMN_NAME_TITLE)));
                shopData.setLongitude(cursor.getDouble(cursor.getColumnIndex(ShopData.ShopEntry.COLUMN_NAME_LONGITUDE)));
                shopData.setLatitude(cursor.getDouble(cursor.getColumnIndex(ShopData.ShopEntry.COLUMN_NAME_LATITUDE)));

                shopDatas.add(shopData);
            } while (cursor.moveToNext());
        }

        // close db connection
        //db.close();

        // return shopData1 list
        return shopDatas;
    }

    public ShopData getShopPrices(int id) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<ShopData> shopDatas = new ArrayList<>();
        String[] projection = {
                BaseColumns._ID,
                ShopData.ShopPrices.COLUMN_NAME_KIKIRIKI,
                ShopData.ShopPrices.COLUMN_NAME_SUSAM,
                ShopData.ShopPrices.COLUMN_NAME_SOCIVO,
                ShopData.ShopPrices.COLUMN_NAME_DJUMBIR,
                ShopData.ShopPrices.COLUMN_NAME_OVSENE_PAHULJE,
                ShopData.ShopPrices.COLUMN_NAME_KARI,
                ShopData.ShopPrices.COLUMN_NAME_SEMENKE,
                ShopData.ShopPrices.COLUMN_NAME_SUNCOKRET,
                ShopData.ShopPrices.COLUMN_NAME_CIMET,
                ShopData.ShopPrices.COLUMN_NAME_KORN_FLEKS
        };

        String selection = ShopData.ShopPrices._ID + " = ?";
        String[] selectionArgs = {Integer.toString(id)};


        Cursor cursor = db.query(
                ShopData.ShopPrices.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null            // The sort order
        );
        // Select All Query
       /* String selectQuery = "SELECT  * FROM " + ShopData.ShopEntry.TABLE_NAME ;
        Cursor cursor = db.rawQuery(selectQuery, null);*/
      /*  Cursor cursor = db.query(ShopData.ShopEntry.TABLE_NAME,
                new String[]{ShopData.ShopEntry._ID, ShopData.ShopEntry.COLUMN_NAME_TITLE, ShopData.ShopEntry.COLUMN_NAME_LONGITUDE, ShopData.ShopEntry.COLUMN_NAME_LATITUDE},
                ShopData.ShopEntry._ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
*/
        ShopData shopData = new ShopData();
        if (cursor.moveToFirst()) {
            do {

                shopData.setId(cursor.getInt(cursor.getColumnIndex(ShopData.ShopPrices._ID)));
                //shopData.setName(cursor.getString(cursor.getColumnIndex(ShopData.ShopPrices.COLUMN_NAME_SHOP_NAME)));
                shopData.setKikiriki(cursor.getDouble(cursor.getColumnIndex(ShopData.ShopPrices.COLUMN_NAME_KIKIRIKI)));
                shopData.setSusam(cursor.getDouble(cursor.getColumnIndex(ShopData.ShopPrices.COLUMN_NAME_SUSAM)));
                shopData.setSocivo(cursor.getDouble(cursor.getColumnIndex(ShopData.ShopPrices.COLUMN_NAME_SOCIVO)));
                shopData.setDjumbir(cursor.getDouble(cursor.getColumnIndex(ShopData.ShopPrices.COLUMN_NAME_DJUMBIR)));
                shopData.setOvsene_pahulje(cursor.getDouble(cursor.getColumnIndex(ShopData.ShopPrices.COLUMN_NAME_OVSENE_PAHULJE)));
                shopData.setKari(cursor.getDouble(cursor.getColumnIndex(ShopData.ShopPrices.COLUMN_NAME_KARI)));
                shopData.setSemenke(cursor.getDouble(cursor.getColumnIndex(ShopData.ShopPrices.COLUMN_NAME_SEMENKE)));
                shopData.setSuncokret(cursor.getDouble(cursor.getColumnIndex(ShopData.ShopPrices.COLUMN_NAME_SUNCOKRET)));
                shopData.setCimet(cursor.getDouble(cursor.getColumnIndex(ShopData.ShopPrices.COLUMN_NAME_CIMET)));
                shopData.setKorn_fleks(cursor.getDouble(cursor.getColumnIndex(ShopData.ShopPrices.COLUMN_NAME_KORN_FLEKS)));

                shopDatas.add(shopData);
            } while (cursor.moveToNext());
        }

        // close db connection
        // db.close();

        // return shopData1 list
        return shopData;
    }

    public ArrayList<ShopData> getPublicBuildings() {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<ShopData> shopDatas = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + ShopData.PublicBuildingsEntry.TABLE_NAME;
        Cursor cursor = db.rawQuery(selectQuery, null);
      /*  Cursor cursor = db.query(ShopData.ShopEntry.TABLE_NAME,
                new String[]{ShopData.ShopEntry._ID, ShopData.ShopEntry.COLUMN_NAME_TITLE, ShopData.ShopEntry.COLUMN_NAME_LONGITUDE, ShopData.ShopEntry.COLUMN_NAME_LATITUDE},
                ShopData.ShopEntry._ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
*/
        if (cursor.moveToFirst()) {
            do {
                ShopData shopData = new ShopData();
                shopData.setId(cursor.getInt(cursor.getColumnIndex(ShopData.PublicBuildingsEntry._ID)));
                shopData.setName(cursor.getString(cursor.getColumnIndex(ShopData.PublicBuildingsEntry.COLUMN_NAME_TITLE)));
                shopData.setType(cursor.getString(cursor.getColumnIndex(ShopData.PublicBuildingsEntry.COLUMN_NAME_TYPE)));
                shopData.setLongitude(cursor.getDouble(cursor.getColumnIndex(ShopData.PublicBuildingsEntry.COLUMN_NAME_LONGITUDE)));
                shopData.setLatitude(cursor.getDouble(cursor.getColumnIndex(ShopData.PublicBuildingsEntry.COLUMN_NAME_LATITUDE)));

                shopDatas.add(shopData);
            } while (cursor.moveToNext());
        }

        // close db connection
        // db.close();

        // return shopData1 list
        return shopDatas;
    }

}
