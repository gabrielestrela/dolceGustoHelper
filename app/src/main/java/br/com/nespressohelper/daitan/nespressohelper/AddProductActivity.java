package br.com.nespressohelper.daitan.nespressohelper;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * This activity shows a form, that retrieves from the user,
 * information about the coffee that will be added to the list
 * of coffees.
 */
public class AddProductActivity extends AppCompatActivity {

    private String name, desc;
    private int capsula1, capsula2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        final EditText prodNome = (EditText) findViewById(R.id.prodNome);
        final EditText prodDesc = (EditText) findViewById(R.id.prodDesc);
        final EditText tracos1 = (EditText) findViewById(R.id.tracos1);
        final EditText tracos2 = (EditText) findViewById(R.id.tracos2);

        Button addCoffe = (Button) findViewById(R.id.addBtn);

        addCoffe.setOnClickListener(new View.OnClickListener() {
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
                    Bundle b = new Bundle();
                    b.putString("Nome", name);
                    b.putString("Desc", desc);
                    b.putInt("Tracos1", capsula1);
                    b.putInt("Tracos2", capsula2);
                    Intent i = new Intent(AddProductActivity.this, MainActivity.class);
                    i.putExtras(b);
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivityForResult(i, 1234);
                }
            }
        });
    }
}
