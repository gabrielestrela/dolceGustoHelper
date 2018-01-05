package br.com.nespressohelper.daitan.nespressohelper;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.gcm.GcmNetworkManager;
import com.google.android.gms.gcm.GcmTaskService;
import com.google.android.gms.gcm.PeriodicTask;
import com.google.android.gms.gcm.TaskParams;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by estrela on 1/2/18.
 */

public class CheckConnStatus extends GcmTaskService {

    private static int SYNC_PERIOD_SECONDS = 120;
    private final static String PERIODIC_SYNC_TAG = "CONNECTED";
    protected static final String ACTION_START = "br.com.nespressohelper.daitan.nespressohelper.action.START";
    protected static final String ACTION_STOP= "br.com.nespressohelper.daitan.nespressohelper.action.STOP";
    protected static final String ACTION_TO_SERVICE = "br.com.nespressohelper.daitan.nespressohelper.action.TO_SERVICE";
    protected static final String ACTION_FROM_SERVICE = "br.com.nespressohelper.daitan.nespressohelper.action.FROM_SERVICE";

    @Override
    public int onRunTask(TaskParams taskParams) {
        String TAG = taskParams.getTag();
        switch (TAG) {
            case PERIODIC_SYNC_TAG:
                Log.i("INTERNET", TAG);
                // CHECK DB
                // SYNC
                String url = "http://10.0.2.2:8080/coffee/";
                Response response;
                try {
                    response = getResponse(url);
                    checkAndSyncLocalRemoteDb(response.body().string());
                    Log.i("STATUS", String.valueOf(response.code()));
                } catch (IOException e) {
                    Log.i("SERVER", "DOWN");
                    //e.printStackTrace();
                }
                return GcmNetworkManager.RESULT_SUCCESS;
            default:
                return GcmNetworkManager.RESULT_FAILURE;
        }
    }

    public void checkAndSyncLocalRemoteDb(String json) {
        ArrayList<Coffee> coffeesRemote = new ArrayList<>();
        ArrayList<Coffee> coffeesLocal = new ArrayList<>();

        coffeesRemote = BackEndComm.jsonToObj(json);

        DBRead read = new DBRead();

        coffeesLocal = read.getCoffees();

        Log.i("DB_SIZE", String.valueOf(coffeesRemote.size()));
        Log.i("LOCAL_DB_SIZE", String.valueOf(coffeesLocal.size()));

        if(coffeesRemote.size() > coffeesLocal.size()) {
            Log.i(">>>", "Remote database is bigger than local database!");
            int startDiffIndex = coffeesRemote.size() - (coffeesRemote.size() - coffeesLocal.size()) + 1;
            syncDataBase(coffeesRemote, startDiffIndex);
        }else if(coffeesLocal.size() == coffeesRemote.size()) {
            Log.i(">>>", "Local Database has the same size as the remote database!");
        }else {
            Log.i(">>>", "Local Database is bigger than remote database!");
        }

    }

    public void syncDataBase(ArrayList<Coffee> coffeesRemote, int startDiffIndex){
        DBUpdate update = new DBUpdate();

        Log.d("SYNC_DATABASE", coffeesRemote.get(startDiffIndex-1).getName());

        for (int i = startDiffIndex-1; i < coffeesRemote.size(); i++) {
            update.addCoffee(coffeesRemote.get(i));
        }
        Log.i("SYNC", "CALLED");
        sendToActivity(startDiffIndex-1);
    }

    private void sendToActivity(Integer startDiffIndex) {
        Log.d("BroadcastService", "Sending message to activity: " + startDiffIndex);
        final Intent intent = new Intent(CheckConnStatus.ACTION_FROM_SERVICE);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("data", startDiffIndex);
        sendBroadcast(intent, br.com.nespressohelper.daitan.nespressohelper.Manifest.permission.ALLOW);
    }

    public Response getResponse(String url) throws IOException {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();

        return response;
    }
    

    public static void isConnected(Context context) {
        GcmNetworkManager gcmNetManager = GcmNetworkManager.getInstance(context);
        PeriodicTask pTask = new PeriodicTask.Builder()
                .setPeriod(SYNC_PERIOD_SECONDS)
                .setRequiredNetwork(PeriodicTask.NETWORK_STATE_CONNECTED)
                .setTag(PERIODIC_SYNC_TAG)
                .setService(CheckConnStatus.class)
                .setPersisted(true)
                .setUpdateCurrent(true)
                .build();
        gcmNetManager.schedule(pTask);
        Log.i("Teste", "onRunTask");
    }

//    public void showSnack(boolean isConnected) {
//        String msg;
//        int color;
//        if(isConnected){
//            msg = "Connected to the internet";
//            color = Color.WHITE;
//        }else{
//            msg = "Not connected to the internet";
//            color = Color.RED;
//        }
//
//        Snackbar bar = Snackbar.make(, msg, Snackbar.LENGTH_SHORT);//Snackbar.make(findViewById(R.id.fab), msg, Snackbar.LENGTH_LONG);
//
//        View sbView = bar.getView();
//        TextView textview = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
//        textview.setTextColor(color);
//        bar.show();
//    }
}
