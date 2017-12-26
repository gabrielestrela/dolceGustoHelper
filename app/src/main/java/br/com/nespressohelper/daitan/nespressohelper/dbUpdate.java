package br.com.nespressohelper.daitan.nespressohelper;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by estrela on 12/21/17.
 */

public class dbUpdate {

    public boolean addCoffee(Coffee coffee){

        SQLiteDatabase database = db.getInstance().getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("NAME", coffee.getName());
        cv.put("CAPSULES", coffee.getCapsules());
        cv.put("BARS1", coffee.getBars1());
        cv.put("BARS2", coffee.getBars2());
        cv.put("DESC", coffee.getDescription());
//        cv.put("IMAGE_ID", coffee.getImageId());
        cv.put("KEY_IMAGE", coffee.getImage());

        return database.insert(db.TABLE_COFFEE, null, cv) != -1;
    }

    public boolean updatePessoa(Coffee coffee) {
        SQLiteDatabase database = db.getInstance().getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("NAME", coffee.getName());
        cv.put("CAPSULES", coffee.getCapsules());
        cv.put("BARS1", coffee.getBars1());
        cv.put("BARS2", coffee.getBars2());
        cv.put("DESC", coffee.getDescription());
//        cv.put("IMAGE_ID", coffee.getImageId());
        cv.put("KEY_IMAGE", coffee.getImage());

        String query = "NAME = '" + coffee.getName() + "'";

        return database.update(db.TABLE_COFFEE, cv, query, null) > 0;

    }
}
