package br.com.nespressohelper.daitan.nespressohelper;

import android.app.Application;
import android.os.SystemClock;

/**
 * Created by gabrielestrela on 09/11/17.
 */

public class SplashDelay extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SystemClock.sleep(1000);
    }
}
