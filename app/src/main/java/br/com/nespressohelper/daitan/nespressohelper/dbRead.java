package br.com.nespressohelper.daitan.nespressohelper;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by estrela on 12/21/17.
 */

public class dbRead {

    public ArrayList<Coffee> getCoffees() {

        SQLiteDatabase database = db.getInstance().getReadableDatabase();
        String query = "SELECT * FROM " + db.TABLE_COFFEE;
        ArrayList<Coffee> coffees = new ArrayList<>();

        Cursor c = database.rawQuery(query, null);

        if(c.moveToFirst()) {

            do {
                Coffee coffee = new Coffee();
                coffee.setName(c.getString(0));
                coffee.setCapsules(c.getInt(1));
                coffee.setBars1(c.getInt(2));
                coffee.setBars2(c.getInt(3));
                coffee.setDescription(c.getString(4));
//                coffee.setImageId(c.getInt(5));
                coffee.setImage(c.getBlob(5));

                coffees.add(coffee);
            }while (c.moveToNext());
        }

        c.close();
        return coffees;
    }

    public Coffee getCoffee(String name) {

        Coffee coffee = new Coffee();

        SQLiteDatabase database = db.getInstance().getReadableDatabase();
        String query = "SELECT * FROM " + db.TABLE_COFFEE + " WHERE NAME = '" + name + "'";

        Cursor c = database.rawQuery(query, null);

        if(c.moveToFirst()) {
            coffee.setName(c.getString(0));
            coffee.setCapsules(c.getInt(1));
            coffee.setBars1(c.getInt(2));
            coffee.setBars2(c.getInt(3));
            coffee.setDescription(c.getString(4));
//            coffee.setImageId(c.getInt(5));
            coffee.setImage(c.getBlob(5));
        }

        c.close();
        return coffee;
    }

    public boolean existCoffe(String name) {
        SQLiteDatabase database = db.getInstance().getReadableDatabase();
        String query = "SELECT * FROM " + db.TABLE_COFFEE + " WHERE NAME = '" + name + "'";

        Cursor c = database.rawQuery(query, null);

        if(c.moveToFirst()) {
            c.close();
            return true;
        }

        c.close();
        return false;
    }

}
