package br.com.nespressohelper.daitan.nespressohelper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;


public class ProductActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);



         Bundle b = this.getIntent().getExtras();
         ArrayList<String> productNames = b.getStringArrayList("ProductNames");
         ArrayList<String> productDescs = b.getStringArrayList("Desc");
         ArrayList<Integer> imageIDs = b.getIntegerArrayList("ImagesIDs");
         final ArrayList<Integer> tracosCapsula1Array = b.getIntegerArrayList("Capsula1");
         final ArrayList<Integer> tracosCapsula2Array = b.getIntegerArrayList("Capsula2");
         final int pos = b.getInt("Pos");


         ImageView img = (ImageView) findViewById(R.id.imageID);
         img.setImageResource(imageIDs.get(pos));

         TextView name = (TextView) findViewById(R.id.productName);
         name.setText(productNames.get(pos));

         TextView desc = (TextView) findViewById(R.id.description);
         desc.setText(productDescs.get(pos));

        Button button = (Button) findViewById(R.id.prepare);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle d = new Bundle();
                d.putIntegerArrayList("Capsula1", tracosCapsula1Array);
                d.putIntegerArrayList("Capsula2", tracosCapsula2Array);
                d.putInt("Pos", pos);
                Intent i = new Intent(ProductActivity.this, TimerActivity.class);
                i.putExtras(d);
                startActivity(i);
            }
        });

    }
}
