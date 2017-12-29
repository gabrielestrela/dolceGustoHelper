package br.com.nespressohelper.daitan.nespressohelper;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by estrela on 12/21/17.
 */

public class DBRead {

    public ArrayList<Coffee> getCoffees() {

        SQLiteDatabase database = DB.getInstance().getReadableDatabase();
        String query = "SELECT * FROM " + DB.TABLE_COFFEE;
        ArrayList<Coffee> coffees = new ArrayList<>();

        Cursor cursor = database.rawQuery(query, null);

        if(cursor.moveToFirst()) {

            do {
                Coffee coffee = new Coffee();
                coffee.setName(cursor.getString(CoffeeTableColumns.NAME));
                coffee.setCapsules(cursor.getInt(CoffeeTableColumns.CAPSULES));
                coffee.setBars1(cursor.getInt(CoffeeTableColumns.BARS1));
                coffee.setBars2(cursor.getInt(CoffeeTableColumns.BARS2));
                coffee.setIntensity(cursor.getInt(CoffeeTableColumns.INTENSITY));
                coffee.setDescription(cursor.getString(CoffeeTableColumns.DESCRIPTION));
                coffee.setImage(cursor.getBlob(CoffeeTableColumns.IMAGE));

                coffees.add(coffee);
            }while (cursor.moveToNext());
        }

        cursor.close();
        return coffees;
    }

    public Coffee getCoffee(String name) {

        Coffee coffee = new Coffee();

        SQLiteDatabase database = DB.getInstance().getReadableDatabase();
        String query = "SELECT * FROM " + DB.TABLE_COFFEE + " WHERE NAME = '" + name + "'";

        Cursor cursor = database.rawQuery(query, null);

        if(cursor.moveToFirst()) {
            coffee.setName(cursor.getString(CoffeeTableColumns.NAME));
            coffee.setCapsules(cursor.getInt(CoffeeTableColumns.CAPSULES));
            coffee.setBars1(cursor.getInt(CoffeeTableColumns.BARS1));
            coffee.setBars2(cursor.getInt(CoffeeTableColumns.BARS2));
            coffee.setIntensity(cursor.getInt(CoffeeTableColumns.INTENSITY));
            coffee.setDescription(cursor.getString(CoffeeTableColumns.DESCRIPTION));
            coffee.setImage(cursor.getBlob(CoffeeTableColumns.IMAGE));
        }

        cursor.close();
        return coffee;
    }

    public boolean existCoffe(String name) {
        SQLiteDatabase database = DB.getInstance().getReadableDatabase();
        String query = "SELECT * FROM " + DB.TABLE_COFFEE + " WHERE NAME = '" + name + "'";

        Cursor cursor = database.rawQuery(query, null);

        Boolean existsInDB = cursor.moveToFirst();
        cursor.close();
        return existsInDB;
    }

}
