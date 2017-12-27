package br.com.nespressohelper.daitan.nespressohelper;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Adapter;
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

    CoffeGrid adapter;

    /**
     * It initializes the arrays for the GridView, with all Dolce Gusto coffees.
     * It also initializes the values for the calculation of preparation time.
     * Since I ain`t using any database, all resides in array lists.
     */

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void updateDB() throws IOException {


        BackEndComm comm = new BackEndComm();
        String url = "http://10.0.2.2:8080/coffee/";
        comm.get(url, this);

//        Coffee coffee = new Coffee();
//
//        coffee.setName("Espresso Intenso Decaffeinato");
//        coffee.setCapsules(1);
//        coffee.setBars1(2);
//        coffee.setBars2(0);
//        coffee.setDescription("Cuidadosamente descafeinado, preserva todo o aroma, intensidade e sabor do nosso Espresso Intenso.");
//
//        // Adding the image to the database
//        drawable = getDrawable(R.drawable.espressointensodecaf);
//        bitmap = ((BitmapDrawable) drawable).getBitmap();
//        stream = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
//        bitmapData = stream.toByteArray();
//        coffee.setImage(bitmapData);
//
//        coffees.add(coffee);

    }

    public ArrayList<Integer> generateDrawablesIds() {

        ArrayList<Integer> drawables = new ArrayList<>();
        drawables.add(R.drawable.espressointensodecaf);
        drawables.add(R.drawable.ristretto);
        drawables.add(R.drawable.ristrettoardenza);
        drawables.add(R.drawable.espresso);
        drawables.add(R.drawable.espressodecaf);
        drawables.add(R.drawable.espressointenso);
        drawables.add(R.drawable.espressobarista);
        drawables.add(R.drawable.cafeaulait);
        drawables.add(R.drawable.capuccino);
        drawables.add(R.drawable.caramellattemacchiato);
        drawables.add(R.drawable.cortado);
        drawables.add(R.drawable.lattemacchiato);
        drawables.add(R.drawable.soycappuccino);
        drawables.add(R.drawable.vanillalattemacchiato);
        drawables.add(R.drawable.nestealimao);
        drawables.add(R.drawable.nesteapessego);
        drawables.add(R.drawable.chococino);
        drawables.add(R.drawable.chococinocaramel);
        drawables.add(R.drawable.mocha);
        drawables.add(R.drawable.nescau);
        drawables.add(R.drawable.cafematinal);
        drawables.add(R.drawable.lungo);
        drawables.add(R.drawable.chaitealatte);
        drawables.add(R.drawable.marrakesh);

        return drawables;
    }

    public Bitmap decodeByteArray(byte[] byteArray) {
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
    }

    public void generateArrays() {
        ArrayList<Coffee> coffees = new DBRead().getCoffees();

        Log.d("DB SIZE", " " + coffees.size());

        webArray = new ArrayList<>();
        imageArray = new ArrayList<>();

        for(it = 0; it < coffees.size(); it++) {
            webArray.add(coffees.get(it).getName());
            imageArray.add(decodeByteArray(coffees.get(it).getImage()));
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
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

        /**
         * Step to run the array initialization only once, than
         * resetting the array, with the purpose of adding new
         * coffees to the list.
         */


        generateArrays();

        /**
         * Initilizing the GridView to show the coffees.
         */
        adapter = new CoffeGrid(getApplicationContext(), webArray, imageArray);

        grid = (GridView) findViewById(R.id.coffeGrid);

        grid.setAdapter(adapter);

        try {
            updateDB();
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
//                Toast.makeText(MainActivity.this, "You Clicked at " + web[position], Toast.LENGTH_SHORT).show();
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
//                    Log.d("SCROLL DOWN", "TRUE");
                }
                if(mLastFirstVisibleItem > firstVisibleItem) {
                    fab.show();
//                    Log.d("SCROLL UP", "TRUE");
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

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void handleResponse(String response, Method m) {

        if(m == Method.GET) {
            ArrayList<Coffee> coffees = new ArrayList<>();
            ArrayList<Integer> drawables = generateDrawablesIds();

            coffees = BackEndComm.jsonToObj(response);

            for(int i = 0; i < coffees.size(); i++) {
                if(i >= drawables.size()) {
                    drawable = getDrawable(R.drawable.coffecapsule);
                    bitmap = ((BitmapDrawable) drawable).getBitmap();
                    stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    bitmapData = stream.toByteArray();
                    coffees.get(i).setImage(bitmapData);
                }else{
                    drawable = getDrawable(drawables.get(i));
                    bitmap = ((BitmapDrawable) drawable).getBitmap();
                    stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    bitmapData = stream.toByteArray();
                    coffees.get(i).setImage(bitmapData);
                }
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

    @Override
    public void handleResponse(int code, Method m) {
        //
    }
}
