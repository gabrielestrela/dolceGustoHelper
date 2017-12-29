package br.com.nespressohelper.daitan.nespressohelper;

import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
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
        comm.get(url, this);

    }

    public Bitmap decodeByteArray(byte[] byteArray) {
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
    }

    public void generateArrays() {
        ArrayList<Coffee> coffees = new DBRead().getCoffees();

        webArray = new ArrayList<>();
        imageArray = new ArrayList<>();

        for(it = 0; it < coffees.size(); it++) {
            webArray.add(coffees.get(it).getName());
            imageArray.add(decodeByteArray(coffees.get(it).getImage()));
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         * Creating database if does not exists
         */
//        DBDelete d = new DBDelete();
//        d.removeTable();
        new DBCreate().createTable();

        generateArrays();

        /**
         * Initilizing the GridView to show the coffees.
         */
        adapter = new CoffeGridAdapter(getApplicationContext(), webArray, imageArray);

        grid = (GridView) findViewById(R.id.coffeGrid);

        grid.setAdapter(adapter);

        try {
            startCoffeeFetchRequest();
        } catch (IOException e) {
            e.printStackTrace();
        }

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

    public byte[] setImageBitmapData(int index, int length, TypedArray drawables) {
        if(index >= length) {
            drawable = getDrawable(R.drawable.coffecapsule);
            bitmap = ((BitmapDrawable) drawable).getBitmap();
            stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);

            return bitmapData = stream.toByteArray();
        }else{
            drawable = getDrawable(drawables.getResourceId(index, -1));//getDrawable(drawables.get(i));
            bitmap = ((BitmapDrawable) drawable).getBitmap();
            stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);

            return bitmapData = stream.toByteArray();
        }
    }

    @Override
    public void handleResponse(String response, int httpResponseCode, Method method) {

        if(method == Method.GET) {
            ArrayList<Coffee> coffees = new ArrayList<>();
            int length = getResources().getTextArray(R.array.imagesArray).length;
            TypedArray drawables = getResources().obtainTypedArray(R.array.imagesArray);

            coffees = BackEndComm.jsonToObj(response);

            for(int i = 0; i < coffees.size(); i++) {
                coffees.get(i).setImage(setImageBitmapData(i, length, drawables));
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
}
