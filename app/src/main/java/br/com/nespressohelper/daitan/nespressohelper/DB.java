package br.com.nespressohelper.daitan.nespressohelper;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by estrela on 12/21/17.
 */

public class DB extends SQLiteOpenHelper {

    private static String DB_NAME = "DB";
    private static int DB_VERSION = 1;
    public static String TABLE_COFFEE = "COFFEES";

    private static DB instance;

    public static DB getInstance() {
        if(instance == null)
            instance = new DB();
        return instance;
    }

    private DB() {
        super(App.getContext(), DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        new DBCreate().createTable();
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

    }
}
