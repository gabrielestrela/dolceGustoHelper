package br.com.nespressohelper.daitan.nespressohelper;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by estrela on 12/21/17.
 */

public class DBDelete {

    public void removeTable() {
        SQLiteDatabase database = DB.getInstance().getWritableDatabase();
        String query = "DROP TABLE IF EXISTS " + DB.TABLE_COFFEE;
        database.execSQL(query);
    }

    public boolean removeCoffee(Coffee coffee) {
        SQLiteDatabase database = DB.getInstance().getWritableDatabase();
        String query = "NAME = '" + coffee.getName() + "'";
        return database.delete(DB.TABLE_COFFEE, query, null) > 0;
    }
}
