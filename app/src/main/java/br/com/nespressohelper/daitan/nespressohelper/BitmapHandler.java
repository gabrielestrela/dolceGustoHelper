package br.com.nespressohelper.daitan.nespressohelper;

import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;

/**
 * Created by estrela on 1/3/18.
 */

public class BitmapHandler extends AppCompatActivity {

    private Drawable drawable;
    private Bitmap bitmap;
    private ByteArrayOutputStream stream;
    private byte[] bitmapData;

    public byte[] getImageBitmapData(Bitmap bitmap) {
        if(bitmap == null) {
            drawable = getDrawable(R.drawable.coffecapsule);
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
            drawable = getDrawable(R.drawable.coffecapsule);
            bitmap = ((BitmapDrawable) drawable).getBitmap();
            stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            bitmapData = stream.toByteArray();
            return bitmapData;
        }else{
            drawable = getDrawable(drawables.getResourceId(index, -1));
            bitmap = ((BitmapDrawable) drawable).getBitmap();
            stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            return bitmapData = stream.toByteArray();
        }
    }

}
