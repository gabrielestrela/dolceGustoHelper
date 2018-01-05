package br.com.nespressohelper.daitan.nespressohelper;

import android.app.Application;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

/**
 * Created by estrela on 1/3/18.
 */

public class BitmapHandler {

    private Drawable drawable;
    private Bitmap bitmap;
    private ByteArrayOutputStream stream;
    private byte[] bitmapData;

    public byte[] getImageBitmapData(Bitmap bitmap) {
        if(bitmap == null) {
            drawable = ContextCompat.getDrawable(App.getContext(), R.drawable.coffecapsule);//getDrawable(R.drawable.coffecapsule);
            bitmap = ((BitmapDrawable) drawable).getBitmap();
            stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            bitmapData = stream.toByteArray();
            return bitmapData;
        }else{
            stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            bitmapData = stream.toByteArray();
            return bitmapData;
        }
    }

    public byte[] getImageBitmapData(int index, int length, TypedArray drawables) {
        if(index >= length) {
            drawable = ContextCompat.getDrawable(App.getContext(), R.drawable.coffecapsule);
            bitmap = ((BitmapDrawable) drawable).getBitmap();
            stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            bitmapData = stream.toByteArray();
            return bitmapData;
        }else{
            drawable = ContextCompat.getDrawable(App.getContext(), drawables.getResourceId(index, -1));
            bitmap = ((BitmapDrawable) drawable).getBitmap();
            stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            return bitmapData = stream.toByteArray();
        }
    }

    public Bitmap decodeByteArray(byte[] byteArray) {
//        Log.i("ARRAY", String.valueOf(byteArray));
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
    }

}
