package br.com.nespressohelper.daitan.nespressohelper;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by estrela on 12/21/17.
 */

public class dbDelete {

    public void removeTable() {
        SQLiteDatabase database = db.getInstance().getWritableDatabase();
        String query = "DROP TABLE IF EXISTS " + db.TABLE_COFFEE;
        database.execSQL(query);
    }

    public boolean removeCoffee(Coffee coffee) {
        SQLiteDatabase database = db.getInstance().getWritableDatabase();
        String query = "NAME = '" + coffee.getName() + "'";
        return database.delete(db.TABLE_COFFEE, query, null) > 0;
    }
}
