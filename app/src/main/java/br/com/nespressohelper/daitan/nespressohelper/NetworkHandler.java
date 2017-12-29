package br.com.nespressohelper.daitan.nespressohelper;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by estrela on 12/29/17.
 */

public class NetworkHandler extends BroadcastReceiver {

    private ConnectivityManager connManager;
    private Context context;
    private NetworkInfo netInfo;

    public static NetworkHandlerListener connRecvListener;

    public NetworkHandler() {
        super();
    }

    public boolean isWifiConnected() {
        connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        netInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        boolean isWifiOn = netInfo.isConnected();
        return isWifiOn;
    }

    public boolean isMobileConnected() {
        connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        netInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        boolean isMobileOn = netInfo.isConnected();
        return isMobileOn;
    }

    public boolean isAnyConnection() {
        connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        netInfo = connManager.getActiveNetworkInfo();
        return (netInfo != null && netInfo.isConnectedOrConnecting());
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        boolean isConnected = isAnyConnection();

        if(connRecvListener != null) {
            connRecvListener.onNetworkConnectionChanged(isConnected);
        }
    }

    public interface NetworkHandlerListener {
        void onNetworkConnectionChanged(boolean isConnected);
    }
}
