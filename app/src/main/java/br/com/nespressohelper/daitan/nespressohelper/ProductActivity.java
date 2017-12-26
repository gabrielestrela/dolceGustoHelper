package br.com.nespressohelper.daitan.nespressohelper;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;

/**
 * This Activity is responsible to show the properties of the selected coffees.
 */
public class ProductActivity extends AppCompatActivity {

    public Bitmap decodeByteArray(byte[] byteArray) {
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);


        /**
         * Getting information from MainActivity, such as the coffee name
         * coffee description and the number for post calculation related
         * to the preparation time.
         */
         Bundle b = this.getIntent().getExtras();
         final String coffeeName = b.getString("NAME");
         dbRead coffeRead = new dbRead();
         final Coffee coffe;

         coffe = coffeRead.getCoffee(coffeeName);

//         ArrayList<String> productNames = b.getStringArrayList("ProductNames");
//         ArrayList<String> productDescs = b.getStringArrayList("Desc");
//         ArrayList<Integer> imageIDs = b.getIntegerArrayList("ImagesIDs");
//         final ArrayList<Integer> tracosCapsula1Array = b.getIntegerArrayList("Capsula1");
//         final ArrayList<Integer> tracosCapsula2Array = b.getIntegerArrayList("Capsula2");
//         final int pos = b.getInt("Pos");


         ImageView img = (ImageView) findViewById(R.id.imageID);
         img.setImageBitmap(decodeByteArray(coffe.getImage()));

         TextView name = (TextView) findViewById(R.id.productName);
         name.setText(coffe.getName());

         TextView desc = (TextView) findViewById(R.id.description);
         desc.setText(coffe.getDescription());

        Button button = (Button) findViewById(R.id.prepare);

        /**
         * Starting the TimerActivity, which helps the user to prepare the
         * selected coffee and passing the necessary information.
         */
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle d = new Bundle();
                d.putString("NAME", coffeeName);
//                d.putIntegerArrayList("Capsula1", tracosCapsula1Array);
//                d.putIntegerArrayList("Capsula2", tracosCapsula2Array);
//                d.putInt("Pos", pos);
                Intent i = new Intent(ProductActivity.this, TimerActivity.class);
                i.putExtras(d);
                startActivity(i);
            }
        });

    }
}
