package br.com.nespressohelper.daitan.nespressohelper;

import android.content.ContentValues;
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

        return database.insert(DB.TABLE_COFFEE, null, cv) != -1;
    }

    public boolean updatePessoa(Coffee coffee) {
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
