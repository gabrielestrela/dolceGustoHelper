package br.com.nespressohelper.daitan.nespressohelper;

import android.app.Application;
import android.content.Context;

/**
 * Created by estrela on 12/21/17.
 */

public class App extends Application {

    private static Context mContext;
    private static App myInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        myInstance = this;
    }

    public static Context getContext() {
        return mContext;
    }

    public static synchronized App getInstance() {
        return myInstance;
    }

}
