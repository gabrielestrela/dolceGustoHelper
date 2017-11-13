package br.com.nespressohelper.daitan.nespressohelper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private int c1, c2;

    private String n, d;

    private GridView grid;

    private static boolean firstRun = true;

    private ArrayList<String> webArray = new ArrayList<>();

    private ArrayList<Integer> imageIdArray = new ArrayList<>();

    private ArrayList<String> descArray = new ArrayList<>();

    static private ArrayList<Integer> tracosCapsula1Array = new ArrayList<>();

    static private ArrayList<Integer> tracosCapsula2Array = new ArrayList<>();

    private static ArrayList<String> namesExtra = new ArrayList<>();
    private static ArrayList<String> descriptionsExtra = new ArrayList<>();
    private static ArrayList<Integer> imagesExtra = new ArrayList<>();
    private static ArrayList<Integer> tracos1Extra = new ArrayList<>();
    private static ArrayList<Integer> tracos2Extra = new ArrayList<>();


    CoffeGrid adapter;

    public void createArrays(){
        webArray.add("Espresso Intenso Decaffeinato");
        webArray.add("Ristretto");
        webArray.add("Ristretto Ardenza");
        webArray.add("Espresso");
        webArray.add("Espresso Decaffeinato");
        webArray.add("Espresso Intenso");
        webArray.add("Espresso Barista");
        webArray.add("Café Au Lait");
        webArray.add("Capuccino");
        webArray.add("Caramel Latte Macchiato");
        webArray.add("Cortado");
        webArray.add("Latte Macchiato");
        webArray.add("Soy Cappuccino");
        webArray.add("Vanilla Latte Macchiato");
        webArray.add("Nestea Limão");
        webArray.add("Nestea Pêssego");
        webArray.add("Chococino");
        webArray.add("Chococino Caramel");
        webArray.add("Mocha");
        webArray.add("Nescau");
        webArray.add("Café Matinal");
        webArray.add("Lungo");
        webArray.add("Chai Tea Latte");
        webArray.add("Marrakesh Style Tea");

        imageIdArray.add(R.drawable.espressointensodecaf);
        imageIdArray.add(R.drawable.ristretto);
        imageIdArray.add(R.drawable.ristrettoardenza);
        imageIdArray.add(R.drawable.espresso);
        imageIdArray.add(R.drawable.espressodecaf);
        imageIdArray.add(R.drawable.espressointensodecaf);
        imageIdArray.add(R.drawable.espressobarista);
        imageIdArray.add(R.drawable.cafeaulait);
        imageIdArray.add(R.drawable.capuccino);
        imageIdArray.add(R.drawable.caramellattemacchiato);
        imageIdArray.add(R.drawable.cortado);
        imageIdArray.add(R.drawable.lattemacchiato);
        imageIdArray.add(R.drawable.soycappuccino);
        imageIdArray.add(R.drawable.vanillalattemacchiato);
        imageIdArray.add(R.drawable.nestealimao);
        imageIdArray.add(R.drawable.nesteapessego);
        imageIdArray.add(R.drawable.chococino);
        imageIdArray.add(R.drawable.chococinocaramel);
        imageIdArray.add(R.drawable.mocha);
        imageIdArray.add(R.drawable.nescau);
        imageIdArray.add(R.drawable.cafematinal);
        imageIdArray.add(R.drawable.lungo);
        imageIdArray.add(R.drawable.chaitealatte);
        imageIdArray.add(R.drawable.marrakesh);

        descArray.add("Cuidadosamente descafeinado, preserva todo o aroma, intensidade e sabor do nosso Espresso Intenso.");
        descArray.add("Um blend de grãos selecionados Arábica e Robusta com torra mais intensa, sabor marcante e elegante, revelando um mix de aromas, como castanha, maçã e finalizando com notas de limão. Para aqueles que gostam de um “shot” de café, como os italianos.");
        descArray.add("Blend de Arábica e Robusta, em perfeito de equilíbrio: perfil sensorial mais intenso de todos os cafés com notas de alcaçuz e leve toque picante. Café marcante que deixa um ater tasting após a degustação.");
        descArray.add("O autêntico café Espresso. Encorpado e com uma crema única. Feito com grãos 100% Arábica, cuidadosamente torrados e moídos.");
        descArray.add("Feito com grãos 100% Arábica, nosso descafeinado preserva todo o aroma e sabor do Espresso.");
        descArray.add("O irmão mais velho de nosso Espresso, ainda mais aromático e intenso. Aroma floral, sabor levemente picante, amargo e forte. Combinação de grãos selecionados de Arábica e Robusta.");
        descArray.add("Café de alta torrefação de seus grãos Arábicas e Robusta, aroma aveludado e crema generosa, estilo café italiano. Rico e equilibrado com notas de chocolate e sabor requintado. Para aqueles que entendem e apreciam um bom café.");
        descArray.add("A combinação perfeita do Café com Leite. Em uma só cápsula, grãos selecionados do tipo Robusta e leite integral.");
        descArray.add("Descubra o que ocorre quando um aromático Espresso 100% Arábica encontra a cremosidade do leite. Crema densa e aveludada e corpo equilibrado. O nosso clássico italiano.");
        descArray.add("Todo o sabor do tradicional Latte Macchiato, com um toque irresistível de caramelo.");
        descArray.add("Encorpado e intenso, nosso Espresso 100% Arábica com uma toque especial de leite.");
        descArray.add("Quando você preparar, vai entender que esse sabor não é só um Café com Leite. Torrefação suave, combinado com a crema de leite integral, levemente adocicado. Grãos 100% Arábica.");
        descArray.add("O Soy Cappuccino mantém o harmonioso encontro entre o café 100% Arábica e a cremosidade do leite, sem lactose.");
        descArray.add("O sabor da baunilha é que faz essa bebida tão especial. Descubra o sabor do encontro de um delicioso café, leite cremoso e um toque de baunilha.");
        descArray.add("Todo o frescor de Nestea® em um só clique. Chá refrescante com sabor limão e uma generosa camada de espuma.");
        descArray.add("Todo o frescor de Nestea® em um só clique. Chá refrescante com sabor pêssego e uma generosa camada de espuma.");
        descArray.add("Chocolate quente cremoso feito com os melhores grãos de cacau, que se misturam com a riqueza e cremosidade do leite. Para todos aqueles que apreciam o verdadeiro sabor do chocolate.");
        descArray.add("Uma combinação única. Todo o sabor do chocolate com a cremosidade do leite um irresistível toque de caramelo. Seu paladar vai se apaixonar.");
        descArray.add("A bebida perfeita para quem quer entrar no mundo do café. Combinação do leite cremoso, chocolate e um toque de café 100% Arábica.");
        descArray.add("O sabor do achocolatado mais famoso do Brasil, em uma só cápsula. Contém Active-Go: um composto exclusivo com cálcio, ferro e vitaminas para o seu café da manhã ficar ainda mais completo.");
        descArray.add("Para deixar ainda mais brasileiro e associá-lo a sua ocasião perfeita: o Café da Manhã!");
        descArray.add("Grãos 100% Arábica com torrefação média, aroma frutado e torrado. Intenso sabor aromático, ligeiramente picante, com notas persistentes no paladar mesmo após degustação. Pode ser degustado no café da manhã ou em qualquer hora do dia.");
        descArray.add("A exótica combinação do chá preto, leite cremoso e uma seleta combinação de especiarias: canela, gengibre cardamomo e cravo.");
        descArray.add("Mergulhe na maravilha do chá marroquino. Uma mistura do aromático chá verde, menta intensamente saborosa e refrescante, coberto com a cremosa espuma e adoçado com perfeição.");


        tracosCapsula1Array.add(2);
        tracosCapsula1Array.add(1);
        tracosCapsula1Array.add(1);
        tracosCapsula1Array.add(2);
        tracosCapsula1Array.add(2);
        tracosCapsula1Array.add(2);
        tracosCapsula1Array.add(1);
        tracosCapsula1Array.add(6);
        tracosCapsula1Array.add(1);
        tracosCapsula1Array.add(2);
        tracosCapsula1Array.add(3);
        tracosCapsula1Array.add(2);
        tracosCapsula1Array.add(2);
        tracosCapsula1Array.add(2);
        tracosCapsula1Array.add(7);
        tracosCapsula1Array.add(7);
        tracosCapsula1Array.add(3);
        tracosCapsula1Array.add(3);
        tracosCapsula1Array.add(3);
        tracosCapsula1Array.add(4);
        tracosCapsula1Array.add(4);
        tracosCapsula1Array.add(4);
        tracosCapsula1Array.add(3);
        tracosCapsula1Array.add(6);

        tracosCapsula2Array.add(0);
        tracosCapsula2Array.add(0);
        tracosCapsula2Array.add(0);
        tracosCapsula2Array.add(0);
        tracosCapsula2Array.add(0);
        tracosCapsula2Array.add(0);
        tracosCapsula2Array.add(0);
        tracosCapsula2Array.add(0);
        tracosCapsula2Array.add(6);
        tracosCapsula2Array.add(5);
        tracosCapsula2Array.add(0);
        tracosCapsula2Array.add(5);
        tracosCapsula2Array.add(5);
        tracosCapsula2Array.add(5);
        tracosCapsula2Array.add(0);
        tracosCapsula2Array.add(0);
        tracosCapsula2Array.add(3);
        tracosCapsula2Array.add(3);
        tracosCapsula2Array.add(3);
        tracosCapsula2Array.add(0);
        tracosCapsula2Array.add(0);
        tracosCapsula2Array.add(0);
        tracosCapsula2Array.add(3);
        tracosCapsula2Array.add(0);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(firstRun) {
            createArrays();
        }else{
            webArray.clear();
            imageIdArray.clear();
            descArray.clear();
            tracosCapsula1Array.clear();
            tracosCapsula2Array.clear();
            createArrays();
            Bundle c = this.getIntent().getExtras();
            if(c != null) {
                n = c.getString("Nome");
                d = c.getString("Desc");
                c1 = c.getInt("Tracos1");
                c2 = c.getInt("Tracos2");

//                Log.i("=======> N ", " => " + n);
//                Log.i("=======> D ", " => " + d);

//                Log.i("WebArray Size: ", " => " + webArray.size());

                namesExtra.add(n);
                descriptionsExtra.add(d);
                imagesExtra.add(R.drawable.coffecapsule);
                tracos1Extra.add(c1);
                tracos2Extra.add(c2);

                webArray.addAll(namesExtra);
                descArray.addAll(descriptionsExtra);
                imageIdArray.addAll(imagesExtra);
                tracosCapsula1Array.addAll(tracos1Extra);
                tracosCapsula2Array.addAll(tracos2Extra);

            }
        }
        firstRun = false;

//        Log.i("ONCREATE CALLED ", "!!!" );

//        Log.i("WebArray Size: ", " => " + webArray.size());
        adapter =  new CoffeGrid(MainActivity.this, webArray, imageIdArray);

        grid = (GridView) findViewById(R.id.coffeGrid);

        grid.setAdapter(adapter);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(MainActivity.this, "You Clicked at " + web[position], Toast.LENGTH_SHORT).show();
                Bundle b = new Bundle();
                b.putIntegerArrayList("ImagesIDs", imageIdArray);
                b.putStringArrayList("ProductNames", webArray);
                b.putStringArrayList("Desc", descArray);
                b.putIntegerArrayList("Capsula1", tracosCapsula1Array);
                b.putIntegerArrayList("Capsula2", tracosCapsula2Array);
                b.putInt("Pos", position);
//                Log.d("TESTE", "position: " + position);
                Intent i = new Intent(MainActivity.this, ProductActivity.class);
                i.putExtras(b);
                startActivity(i);
            }
        });

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

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Click action
                Intent j = new Intent(getApplicationContext(), AddProductActivity.class);
                startActivity(j);
            }
        });

    }
}
