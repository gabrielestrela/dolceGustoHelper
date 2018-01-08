package br.com.nespressohelper.daitan.nespressohelper;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * MainActivity, it shows a coffe list to the user, a GridView with all Dolce Gusto options
 * which the user can select to read some information about the coffe and than start a timer
 * to help the preparation of the selected coffee.
 */

public class MainActivity extends AppCompatActivity implements BackEndCommHandler{

    private int it;

    private static boolean firstRun = true;

    private String coffeName;

    private GridView grid;

    private Drawable drawable;

    private Bitmap bitmap;

    private byte[] bitmapData;

    private ByteArrayOutputStream stream;

    private ArrayList<String> webArray;

    private ArrayList<Bitmap> imageArray;

    private int broadcastDiffIndex;

    private boolean serverStatus;

    CoffeGridAdapter adapter;

    /**
     * It initializes the arrays for the GridView, with all Dolce Gusto coffees.
     * It also initializes the values for the calculation of preparation time.
     * Since I ain`t using any database, all resides in array lists.
     */

    public void startCoffeeFetchRequest() throws IOException {


        BackEndComm comm = new BackEndComm();
        String url = "http://10.0.2.2:8080/coffee/";
        comm.fetch(url, this);

    }

    public void generateArrays() {
        ArrayList<Coffee> coffees = new DBRead().getCoffees();
        BitmapHandler bitHandler = new BitmapHandler();

        if(coffees.size() != 0) {

            webArray = new ArrayList<>();
            imageArray = new ArrayList<>();

            for(it = 0; it < coffees.size(); it++) {
                webArray.add(coffees.get(it).getName());
//                Log.d("IMAGE", String.valueOf(coffees.get(it).getImage()));
                if(coffees.get(it).getImage() != null) {
                    imageArray.add(bitHandler.decodeByteArray(coffees.get(it).getImage()));
                }else{
                    imageArray.add(bitHandler.decodeByteArray(bitHandler.getImageBitmapData(0, 0, null, null)));
                }
            }

        }else if (broadcastDiffIndex != 0 && coffees.size() != 0) {
            Log.i("UPDATE", "REMOTE DB BIGGER THAN LOCAL");
            for(int i = broadcastDiffIndex; i < coffees.size(); i++){
                webArray.add(coffees.get(i).getName());
                imageArray.add(bitHandler.decodeByteArray(bitHandler.getImageBitmapData(0, 0, null, null)));
            }
        }else {
            webArray = new ArrayList<>();
            imageArray = new ArrayList<>();
            webArray.add("testeNULL");
            Bitmap bitmap = null;
            imageArray.add(bitHandler.decodeByteArray(bitHandler.getImageBitmapData(0, 0, null, bitmap)));
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        broadcastDiffIndex = 0;

        generateArrays();

        try {
            startCoffeeFetchRequest();
        } catch (IOException e) {
            e.printStackTrace();
        }

        CheckConnStatus.isConnected(this);

        /**
         * Initilizing the GridView to show the coffees.
         */
        adapter = new CoffeGridAdapter(getApplicationContext(), webArray, imageArray);

        grid = (GridView) findViewById(R.id.coffeGrid);

        grid.setAdapter(adapter);

        final IntentFilter myFilter = new IntentFilter(CheckConnStatus.ACTION_FROM_SERVICE);
        registerReceiver(mReceiver, myFilter);

        /**
         * Preparing data to be sent to the next acitivity, which will display information about the
         * selected coffee.
         */
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle b = new Bundle();
                coffeName = webArray.get(position);
                b.putString("NAME", coffeName);
                Intent i = new Intent(MainActivity.this, ProductActivity.class);
                i.putExtras(b);
                startActivity(i);
            }
        });


        /**
         * Generation FAB that will add coffees to the GridView.
         * The FAB button will hide when the user scrolls down the screen and
         * appears when the user scrolls up.
         */
        // Add a product
        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        grid.setOnScrollListener(new AbsListView.OnScrollListener() {
            private int mLastFirstVisibleItem;
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if(mLastFirstVisibleItem < firstVisibleItem) {
                    fab.hide();
                    //SCROLL DOWN
                }
                if(mLastFirstVisibleItem > firstVisibleItem) {
                    fab.show();
                    //SCROLL UP
                }
                mLastFirstVisibleItem = firstVisibleItem;
            }
        });

        /**
         * Starting the AddProductActivity.
         */
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Click action
                Intent j = new Intent(getApplicationContext(), AddProductActivity.class);
                startActivity(j);
            }
        });

    }

    @Override
    public void handleResponse(String response, int httpResponseCode, Method method) {

        BitmapHandler bitHandler;

        Log.i("ENTROU", "HANDLE");

        if(method == Method.GET) {
            ArrayList<Coffee> coffees = new ArrayList<>();
            int length = getResources().getTextArray(R.array.imagesArray).length;
            TypedArray drawables = getResources().obtainTypedArray(R.array.imagesArray);

            coffees = BackEndComm.jsonToObj(response);

            for(int i = 0; i < coffees.size(); i++) {
                bitHandler = new BitmapHandler();
                coffees.get(i).setImage(bitHandler.getImageBitmapData(i, length, drawables, null));
            }

            DBUpdate update = new DBUpdate();

            for(it = 0; it < coffees.size(); it++) {
                if(update.addCoffee(coffees.get(it))) {
                    Log.i("Added coffe", " => " + coffees.get(it).getName());
                }
            }

            generateArrays();

            Handler mainHandler = new Handler(getMainLooper());
            mainHandler.post(new Runnable() {
                @Override
                public void run() {
                    adapter.setGridValues(webArray, imageArray);
                }
            });
        }
    }

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            broadcastDiffIndex = intent.getIntExtra("data", -1);
            serverStatus = intent.getBooleanExtra("isServerOn", false);
            Log.d("BroadcastService", " Received From Service: " + broadcastDiffIndex + " " + "serverStatus");
            if(serverStatus == false){
                showSnack(serverStatus);
            }else{
                generateArrays();
                showSnack(serverStatus);
            }
            adapter.setGridValues(webArray, imageArray);
        }
    };

    private void syncDataBase() {
        DBRead read = new DBRead();
        ArrayList<Coffee> coffees = new ArrayList<>();

        coffees = read.getCoffees();


    }

    @Override
    public void onDestroy() {
        stopService(new Intent(CheckConnStatus.ACTION_STOP));
        unregisterReceiver(mReceiver);
        super.onDestroy();
    }

    public void showSnack(boolean isConnected) {
        String msg;
        int color;
        if(isConnected){
            msg = "Server online";
            color = Color.WHITE;
        }else{
            msg = "Server is not online";
            color = Color.RED;
        }

        Snackbar bar = Snackbar.make(findViewById(R.id.fab), msg, Snackbar.LENGTH_SHORT);//Snackbar.make(findViewById(R.id.fab), msg, Snackbar.LENGTH_LONG);

        View sbView = bar.getView();
        TextView textview = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textview.setTextColor(color);
        bar.show();
    }

}
