package br.com.nespressohelper.daitan.nespressohelper;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by estrela on 12/21/17.
 */

public class DBUpdate {

    public boolean addCoffee(Coffee coffee){

        SQLiteDatabase database = DB.getInstance().getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("NAME", coffee.getName());
        cv.put("CAPSULES", coffee.getCapsules());
        cv.put("BARS1", coffee.getBars1());
        cv.put("BARS2", coffee.getBars2());
        cv.put("INTENSITY", coffee.getIntensity());
        cv.put("DESC", coffee.getDescription());
        cv.put("KEY_IMAGE", coffee.getImage());

        return database.insertWithOnConflict(DB.TABLE_COFFEE, null, cv, SQLiteDatabase.CONFLICT_IGNORE) != -1;

    }

    public boolean isDataAlreadyInDb(Coffee coffee) {

        SQLiteDatabase database = DB.getInstance().getReadableDatabase();
        String query = "SELECT * FROM " + DB.TABLE_COFFEE + " WHERE NAME = '" + coffee.getName() + "'";
        Cursor cursor = database.rawQuery(query, null);
        if(cursor.getCount() <= 0) {
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }

    public boolean updateCoffee(Coffee coffee) {
        SQLiteDatabase database = DB.getInstance().getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("NAME", coffee.getName());
        cv.put("CAPSULES", coffee.getCapsules());
        cv.put("BARS1", coffee.getBars1());
        cv.put("BARS2", coffee.getBars2());
        cv.put("DESC", coffee.getDescription());
        cv.put("INTENSITY", coffee.getIntensity());
        cv.put("KEY_IMAGE", coffee.getImage());

        String query = "NAME = '" + coffee.getName() + "'";

        return database.update(DB.TABLE_COFFEE, cv, query, null) > 0;

    }
}
