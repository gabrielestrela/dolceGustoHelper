package br.com.nespressohelper.daitan.nespressohelper;

import android.content.Context;
import android.util.Log;

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

    private static int SYNC_PERIOD_SECONDS = 10;
    private final static String PERIODIC_SYNC_TAG = "CONNECTED";

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

        for (int i = startDiffIndex; i <= coffeesRemote.size(); i++) {
            update.addCoffee(coffeesRemote.get(i));
        }

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
}
