package br.com.nespressohelper.daitan.nespressohelper;

import android.app.Application;
import android.content.Context;

/**
 * Created by estrela on 12/21/17.
 */

public class app extends Application {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
    }

    public static Context getContext() {
        return mContext;
    }

}
