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

/**
 * This Activity is responsible to show the properties of the selected coffees.
 */
public class ProductActivity extends AppCompatActivity {

    BitmapHandler bitHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        bitHandler = new BitmapHandler();

        /**
         * Getting information from MainActivity, such as the coffee name
         * coffee description and the number for post calculation related
         * to the preparation time.
         */
         Bundle b = this.getIntent().getExtras();
         final String coffeeName = b.getString("NAME");
         DBRead coffeRead = new DBRead();
         final Coffee coffe;

         coffe = coffeRead.getCoffee(coffeeName);

         ImageView img = (ImageView) findViewById(R.id.imageID);
         if(coffe.getImage() == null){
             coffe.setImage(bitHandler.getImageBitmapData(0, 0, null,null));
         }
         img.setImageBitmap(bitHandler.decodeByteArray(coffe.getImage()));

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
                Intent i = new Intent(ProductActivity.this, TimerActivity.class);
                i.putExtras(d);
                startActivity(i);
            }
        });

    }
}
