package br.com.nespressohelper.daitan.nespressohelper;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * This activity shows a form, that retrieves from the user,
 * information about the coffee that will be added to the list
 * of coffees.
 */
public class AddProductActivity extends AppCompatActivity implements BackEndCommHandler{

    public static final int GET_FROM_GALLERY = 1;
    private static Bitmap userInputBitmap;
    private Drawable drawable;
    private Bitmap bitmap;
    private ByteArrayOutputStream stream;
    private byte[] bitmapData;
    EditText coffeeName;
    EditText coffeeDesc;
    EditText coffeeBars1;
    EditText coffeeBars2;
    EditText coffeeIntensity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        coffeeName = (EditText) findViewById(R.id.prodNome);
        coffeeDesc = (EditText) findViewById(R.id.prodDesc);
        coffeeBars1 = (EditText) findViewById(R.id.tracos1);
        coffeeBars2 = (EditText) findViewById(R.id.tracos2);
        coffeeIntensity = (EditText) findViewById(R.id.intensity);

        final ImageView uploadImage = (ImageView) findViewById(R.id.uploadImage);
        Button addCoffe = (Button) findViewById(R.id.addBtn);

        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI), GET_FROM_GALLERY);
            }
        });

        addCoffe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Log.d("CLICOU", "ADD");
                if(validateInput()) {
                    generateCoffeeObj();
                }// if
                else {
                    /**
                     * A little warning for the user. When he/she does not
                     * fill some of the fields.
                     */
                    AlertDialog.Builder builder = new AlertDialog.Builder(AddProductActivity.this);
                    builder.setMessage("Por favor, preencha todos os campos!")
                            .setTitle("Atenção");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                        }
                    });
                    AlertDialog dialog = builder.create();

                    dialog.show();
                }
            }
        });
    }

    public boolean validateInput() {

        if(coffeeName.getText().toString().equals("") ||
                coffeeDesc.getText().toString().equals("") ||
                coffeeBars1.getText().toString().equals("")  ||
                coffeeBars2.getText().toString().equals("")  ||
                coffeeIntensity.getText().toString().equals("")) {

            return false;

        }else {
            return true;
        }
    }

    public void generateCoffeeObj(){
        Coffee coffee = new Coffee();

        String name;
        name = coffeeName.getText().toString();
        coffee.setName(name);

        int bars1;
        bars1 = Integer.parseInt(coffeeBars1.getText().toString());
        coffee.setBars1(bars1);

        int bars2;
        bars2 = Integer.parseInt(coffeeBars2.getText().toString());
        coffee.setBars2(bars2);

        int capsules;
        if(bars1 == 0 || bars2 == 0){
            capsules = 1;
        }else{
            capsules = 2;
        }
        coffee.setCapsules(capsules);

        int intensity;
        intensity = Integer.parseInt(coffeeIntensity.getText().toString());
        coffee.setIntensity(intensity);

        String description;
        description = coffeeDesc.getText().toString();
        coffee.setDescription(description);

        bitmap = userInputBitmap;
        BitmapHandler bitHandler = new BitmapHandler();
        coffee.setImage(bitHandler.getImageBitmapData(bitmap));

        DBUpdate update = new DBUpdate();
        if(update.addCoffee(coffee)) {
            BackEndComm comm = new BackEndComm();
            String json = comm.objToJson(coffee);
            String url = "http://10.0.2.2:8080/coffee/";
            try {
                comm.post(url, json, AddProductActivity.this);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        //Detects request codes
        if(requestCode==GET_FROM_GALLERY && resultCode == Activity.RESULT_OK) {
            Uri selectedImage = data.getData();
            userInputBitmap = null;
            try {
                userInputBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void handleResponse(String response, int httpResponseCode, Method method) {
        if(method == Method.POST) {
            if(httpResponseCode == 200){
                Handler mainHandler = new Handler(getMainLooper());
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        Intent i = new Intent(AddProductActivity.this, MainActivity.class);
                        startActivity(i);
                    }
                });
            }else{
                View addProductView = findViewById(R.id.contraintLayout);
                Snackbar snackbar = Snackbar.make(addProductView, "Falha ao inserir café no banco de dados.", Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        }
    }
}
