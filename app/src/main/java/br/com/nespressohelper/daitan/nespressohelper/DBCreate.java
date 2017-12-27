package br.com.nespressohelper.daitan.nespressohelper;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by estrela on 12/21/17.
 */

public class DBCreate {

    public void createTable() {
        SQLiteDatabase database = DB.getInstance().getWritableDatabase();
        String cols = "(NAME TEXT, CAPSULES INTEGER, BARS1 INTEGER, BARS2 INTEGER, INTENSITY INTEGER, DESC TEXT, KEY_IMAGE BLOB, unique(NAME, CAPSULES, BARS1, BARS2, INTENSITY, DESC, KEY_IMAGE) )";
        String query = "CREATE TABLE IF NOT EXISTS " + DB.TABLE_COFFEE + cols;
        database.execSQL(query);
    }
}