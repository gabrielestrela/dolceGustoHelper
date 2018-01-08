package br.com.nespressohelper.daitan.nespressohelper;

import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;

/**
 * Created by estrela on 1/3/18.
 */

public class BitmapHandler {
    

    public byte[] getImageBitmapData(int index, int length, TypedArray drawables, Bitmap bitmap) {

        if(bitmap == null) {
            Drawable drawable;
            if(index >= length) {
                drawable = ContextCompat.getDrawable(App.getContext(), R.drawable.coffecapsule);
            }else {
                drawable = ContextCompat.getDrawable(App.getContext(), drawables.getResourceId(index, -1));
            }
            bitmap = ((BitmapDrawable) drawable).getBitmap();
        }

        return getByteArrayFromBitmap(bitmap);
    }

    public byte[] getByteArrayFromBitmap(Bitmap bitmap) {

        ByteArrayOutputStream stream;

        stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);

        return stream.toByteArray();
    }

}
