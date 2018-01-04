package br.com.nespressohelper.daitan.nespressohelper;

import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
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

        if(coffees.size() != 0) {

            Log.i("TESTE", "ENTROU IF");


            webArray = new ArrayList<>();
            imageArray = new ArrayList<>();

            for(it = 0; it < coffees.size(); it++) {
                webArray.add(coffees.get(it).getName());
                Log.i("IMAGE", String.valueOf(coffees.get(it).getImage()));
                Log.i("NAME", coffees.get(it).getName());
                BitmapHandler bitHandler = new BitmapHandler();
                imageArray.add(bitHandler.decodeByteArray(coffees.get(it).getImage()));
            }
        }else {

            Log.i("TESTE", "ENTROU ELSE");

            BitmapHandler bitHandler = new BitmapHandler();
            webArray = new ArrayList<>();
            imageArray = new ArrayList<>();
            webArray.add("testeNULL");
            Bitmap bitmap = null;
            imageArray.add(bitHandler.decodeByteArray(bitHandler.getImageBitmapData(bitmap)));
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
                coffees.get(i).setImage(bitHandler.getImageBitmapData(i, length, drawables));
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

    // Method to manually check connection status
    private void checkConnection() {
        NetworkHandler netHandler = new NetworkHandler();
        boolean isConnected = netHandler.isAnyConnection();
        showSnack(isConnected);
    }

    public void showSnack(boolean isConnected) {
        String msg;
        int color;
        if(isConnected){
            msg = "Connected to the internet";
            color = Color.WHITE;
        }else{
            msg = "Not connected to the internet";
            color = Color.RED;
        }

        Snackbar bar = Snackbar.make(findViewById(R.id.fab), msg, Snackbar.LENGTH_LONG);

        View sbView = bar.getView();
        TextView textview = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textview.setTextColor(color);
        bar.show();
    }

}
