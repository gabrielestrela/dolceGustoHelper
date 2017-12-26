package br.com.nespressohelper.daitan.nespressohelper;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by estrela on 12/21/17.
 */

public class db extends SQLiteOpenHelper {

    private static String DB_NAME = "DB";
    private static int DB_VERSION = 1;
    public static String TABLE_COFFEE = "COFFEES";

    private static db instance;

    public static db getInstance() {
        if(instance == null)
            instance = new db();
        return instance;
    }

    private db() {
        super(app.getContext(), DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

    }
}
