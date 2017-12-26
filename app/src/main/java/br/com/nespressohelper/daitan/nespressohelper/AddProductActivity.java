package br.com.nespressohelper.daitan.nespressohelper;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
public class AddProductActivity extends AppCompatActivity {

    private String name, desc;
    private int capsula1, capsula2;
    public static final int GET_FROM_GALLERY = 1;
    private Coffee coffee;
    private static Bitmap bitmapAux;
    private Drawable drawable;
    private Bitmap bitmap;
    private ByteArrayOutputStream stream;
    private byte[] bitmapData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        final EditText prodNome = (EditText) findViewById(R.id.prodNome);
        final EditText prodDesc = (EditText) findViewById(R.id.prodDesc);
        final EditText tracos1 = (EditText) findViewById(R.id.tracos1);
        final EditText tracos2 = (EditText) findViewById(R.id.tracos2);
        final ImageView uploadImage = (ImageView) findViewById(R.id.uploadImage);
        Button addCoffe = (Button) findViewById(R.id.addBtn);

        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI), GET_FROM_GALLERY);
            }
        });

        addCoffe.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View view) {
//                Log.d("CLICOU", "ADD");
                if(prodNome.getText().toString().equals("") ||
                   prodDesc.getText().toString().equals("") ||
                   tracos1.getText().toString().equals("")  ||
                   tracos2.getText().toString().equals("")){


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
                }// if
                /**
                 * Passing the filled information back to the MainAcitivity.
                 */
                else {
                    name = prodNome.getText().toString();
                    desc = prodDesc.getText().toString();
                    capsula1 = Integer.parseInt(tracos1.getText().toString());
                    capsula2 = Integer.parseInt(tracos2.getText().toString());
//                    Log.d("Cafe", "Adicionado");
                    coffee = new Coffee();
                    coffee.setName(name);
                    coffee.setDescription(desc);
                    coffee.setBars1(capsula1);
                    coffee.setBars2(capsula2);
                    if(capsula1 == 0 || capsula2 == 0) {
                        coffee.setCapsules(1);
                    }else {
                        coffee.setCapsules(2);
                    }
                    bitmap = bitmapAux;
                    if(bitmap == null) {
                        drawable = getDrawable(R.drawable.coffecapsule);
                        bitmap = ((BitmapDrawable) drawable).getBitmap();
                        stream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                        bitmapData = stream.toByteArray();
                        coffee.setImage(bitmapData);
                    }else {
                        stream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                        bitmapData = stream.toByteArray();
                        coffee.setImage(bitmapData);
                    }
                    dbUpdate update = new dbUpdate();
                    if(update.addCoffee(coffee)) {
                        Log.i("Coffe Added", "!");
                    }
//                    Bundle b = new Bundle();
//                    b.putString("Nome", name);
//                    b.putString("Desc", desc);
//                    b.putInt("Tracos1", capsula1);
//                    b.putInt("Tracos2", capsula2);
                    Intent i = new Intent(AddProductActivity.this, MainActivity.class);
//                    i.putExtras(b);
//                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
//                    startActivityForResult(i, 1234);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        //Detects request codes
        if(requestCode==GET_FROM_GALLERY && resultCode == Activity.RESULT_OK) {
            Uri selectedImage = data.getData();
            bitmapAux = null;
            try {
                bitmapAux = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);

            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
