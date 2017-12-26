package br.com.nespressohelper.daitan.nespressohelper;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by estrela on 12/21/17.
 */

public class dbCreate {

    public void createTable() {
        SQLiteDatabase database = db.getInstance().getWritableDatabase();
        String cols = "(NAME TEXT, CAPSULES INTEGER, BARS1 INTEGER, BARS2 INTEGER, DESC TEXT, KEY_IMAGE BLOB, unique(NAME, CAPSULES, BARS1, BARS2, DESC, KEY_IMAGE) )";
        String query = "CREATE TABLE IF NOT EXISTS " + db.TABLE_COFFEE + cols;
        database.execSQL(query);
    }
}