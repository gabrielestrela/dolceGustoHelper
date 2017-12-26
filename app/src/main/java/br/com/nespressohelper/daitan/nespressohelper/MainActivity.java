package br.com.nespressohelper.daitan.nespressohelper;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.TreeSet;

/**
 * MainActivity, it shows a coffe list to the user, a GridView with all Dolce Gusto options
 * which the user can select to read some information about the coffe and than start a timer
 * to help the preparation of the selected coffee.
 */

public class MainActivity extends AppCompatActivity {

    private int c1, c2, it;

    private static boolean firstRun = true;

    private String coffeName, d;

    private GridView grid;

    private Drawable drawable;

    private Bitmap bitmap;

    private byte[] bitmapData;

    private ByteArrayOutputStream stream;

    private ArrayList<String> webArray;

    private ArrayList<Bitmap> imageArray;

//    private ArrayList<String> descArray = new ArrayList<>();
//
//    static private ArrayList<Integer> tracosCapsula1Array = new ArrayList<>();
//
//    static private ArrayList<Integer> tracosCapsula2Array = new ArrayList<>();
//
    private static ArrayList<String> namesExtra = new ArrayList<>();
//    private static ArrayList<String> descriptionsExtra = new ArrayList<>();
    private static ArrayList<Bitmap> imagesExtra = new ArrayList<>();
//    private static ArrayList<Integer> tracos1Extra = new ArrayList<>();
//    private static ArrayList<Integer> tracos2Extra = new ArrayList<>();


    CoffeGrid adapter;

    /**
     * It initializes the arrays for the GridView, with all Dolce Gusto coffees.
     * It also initializes the values for the calculation of preparation time.
     * Since I ain`t using any database, all resides in array lists.
     */

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void initiateDB(){
        ArrayList<Coffee> coffees = new ArrayList<>();
        Coffee coffee = new Coffee();

        coffee.setName("Espresso Intenso Decaffeinato");
        coffee.setCapsules(1);
        coffee.setBars1(2);
        coffee.setBars2(0);
        coffee.setDescription("Cuidadosamente descafeinado, preserva todo o aroma, intensidade e sabor do nosso Espresso Intenso.");
//        coffee.setImageId(R.drawable.espressointensodecaf);

        // Adding the image to the database
        drawable = getDrawable(R.drawable.espressointensodecaf);
        bitmap = ((BitmapDrawable) drawable).getBitmap();
        stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        bitmapData = stream.toByteArray();
        coffee.setImage(bitmapData);

        coffees.add(coffee);

        coffee = new Coffee();

        coffee.setName("Ristretto");
        coffee.setCapsules(1);
        coffee.setBars1(1);
        coffee.setBars2(0);
        coffee.setDescription("Um blend de grãos selecionados Arábica e Robusta com torra mais intensa, sabor marcante e elegante, revelando um mix de aromas, como castanha, maçã e finalizando com notas de limão. Para aqueles que gostam de um “shot” de café, como os italianos.");
//        coffee.setImageId(R.drawable.ristretto);

        drawable = getDrawable(R.drawable.ristretto);
        bitmap = ((BitmapDrawable) drawable).getBitmap();
        stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        bitmapData = stream.toByteArray();
        coffee.setImage(bitmapData);

        coffees.add(coffee);

        coffee = new Coffee();

        coffee.setName("Ristretto Ardenza");
        coffee.setCapsules(1);
        coffee.setBars1(1);
        coffee.setBars2(0);
        coffee.setDescription("Blend de Arábica e Robusta, em perfeito de equilíbrio: perfil sensorial mais intenso de todos os cafés com notas de alcaçuz e leve toque picante. Café marcante que deixa um ater tasting após a degustação.");
//        coffee.setImageId(R.drawable.ristrettoardenza);

        drawable = getDrawable(R.drawable.ristrettoardenza);
        bitmap = ((BitmapDrawable) drawable).getBitmap();
        stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        bitmapData = stream.toByteArray();
        coffee.setImage(bitmapData);

        coffees.add(coffee);

        coffee = new Coffee();

        coffee.setName("Espresso");
        coffee.setCapsules(1);
        coffee.setBars1(2);
        coffee.setBars2(0);
        coffee.setDescription("O autêntico café Espresso. Encorpado e com uma crema única. Feito com grãos 100% Arábica, cuidadosamente torrados e moídos.");
//        coffee.setImageId(R.drawable.espresso);

        drawable = getDrawable(R.drawable.espresso);
        bitmap = ((BitmapDrawable) drawable).getBitmap();
        stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        bitmapData = stream.toByteArray();
        coffee.setImage(bitmapData);

        coffees.add(coffee);

        coffee = new Coffee();

        coffee.setName("Espresso Decaffeinato");
        coffee.setCapsules(1);
        coffee.setBars1(2);
        coffee.setBars2(0);
        coffee.setDescription("Feito com grãos 100% Arábica, nosso descafeinado preserva todo o aroma e sabor do Espresso.");
//        coffee.setImageId(R.drawable.espressodecaf);

        drawable = getDrawable(R.drawable.espressodecaf);
        bitmap = ((BitmapDrawable) drawable).getBitmap();
        stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        bitmapData = stream.toByteArray();
        coffee.setImage(bitmapData);

        coffees.add(coffee);

        coffee = new Coffee();

        //6
        coffee.setName("Espresso Intenso");
        coffee.setCapsules(1);
        coffee.setBars1(2);
        coffee.setBars2(0);
        coffee.setDescription("O irmão mais velho de nosso Espresso, ainda mais aromático e intenso. Aroma floral, sabor levemente picante, amargo e forte. Combinação de grãos selecionados de Arábica e Robusta.");
//        coffee.setImageId(R.drawable.espressointenso);

        drawable = getDrawable(R.drawable.espressointenso);
        bitmap = ((BitmapDrawable) drawable).getBitmap();
        stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        bitmapData = stream.toByteArray();
        coffee.setImage(bitmapData);

        coffees.add(coffee);

        coffee = new Coffee();

        //7
        coffee.setName("Espresso Barista");
        coffee.setCapsules(1);
        coffee.setBars1(1);
        coffee.setBars2(0);
        coffee.setDescription("Café de alta torrefação de seus grãos Arábicas e Robusta, aroma aveludado e crema generosa, estilo café italiano. Rico e equilibrado com notas de chocolate e sabor requintado. Para aqueles que entendem e apreciam um bom café.");
//        coffee.setImageId(R.drawable.espressobarista);

        drawable = getDrawable(R.drawable.espressobarista);
        bitmap = ((BitmapDrawable) drawable).getBitmap();
        stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        bitmapData = stream.toByteArray();
        coffee.setImage(bitmapData);

        coffees.add(coffee);

        coffee = new Coffee();

        //8
        coffee.setName("Café Au Lait");
        coffee.setCapsules(1);
        coffee.setBars1(6);
        coffee.setBars2(0);
        coffee.setDescription("A combinação perfeita do Café com Leite. Em uma só cápsula, grãos selecionados do tipo Robusta e leite integral.");
//        coffee.setImageId(R.drawable.cafeaulait);

        drawable = getDrawable(R.drawable.cafeaulait);
        bitmap = ((BitmapDrawable) drawable).getBitmap();
        stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        bitmapData = stream.toByteArray();
        coffee.setImage(bitmapData);

        coffees.add(coffee);

        coffee = new Coffee();

        //9
        coffee.setName("Capuccino");
        coffee.setCapsules(2);
        coffee.setBars1(1);
        coffee.setBars2(6);
        coffee.setDescription("Descubra o que ocorre quando um aromático Espresso 100% Arábica encontra a cremosidade do leite. Crema densa e aveludada e corpo equilibrado. O nosso clássico italiano.");
//        coffee.setImageId(R.drawable.capuccino);

        drawable = getDrawable(R.drawable.capuccino);
        bitmap = ((BitmapDrawable) drawable).getBitmap();
        stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        bitmapData = stream.toByteArray();
        coffee.setImage(bitmapData);

        coffees.add(coffee);

        coffee = new Coffee();

        //10
        coffee.setName("Caramel Latte Macchiato");
        coffee.setCapsules(2);
        coffee.setBars1(2);
        coffee.setBars2(5);
        coffee.setDescription("Todo o sabor do tradicional Latte Macchiato, com um toque irresistível de caramelo.");
//        coffee.setImageId(R.drawable.caramellattemacchiato);

        drawable = getDrawable(R.drawable.caramellattemacchiato);
        bitmap = ((BitmapDrawable) drawable).getBitmap();
        stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        bitmapData = stream.toByteArray();
        coffee.setImage(bitmapData);

        coffees.add(coffee);

        coffee = new Coffee();

        //11
        coffee.setName("Cortado");
        coffee.setCapsules(1);
        coffee.setBars1(3);
        coffee.setBars2(0);
        coffee.setDescription("Encorpado e intenso, nosso Espresso 100% Arábica com uma toque especial de leite.");
//        coffee.setImageId(R.drawable.cortado);

        drawable = getDrawable(R.drawable.cortado);
        bitmap = ((BitmapDrawable) drawable).getBitmap();
        stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        bitmapData = stream.toByteArray();
        coffee.setImage(bitmapData);

        coffees.add(coffee);

        coffee = new Coffee();

        //12
        coffee.setName("Latte Macchiato");
        coffee.setCapsules(2);
        coffee.setBars1(2);
        coffee.setBars2(5);
        coffee.setDescription("Quando você preparar, vai entender que esse sabor não é só um Café com Leite. Torrefação suave, combinado com a crema de leite integral, levemente adocicado. Grãos 100% Arábica.");
//        coffee.setImageId(R.drawable.lattemacchiato);

        drawable = getDrawable(R.drawable.lattemacchiato);
        bitmap = ((BitmapDrawable) drawable).getBitmap();
        stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        bitmapData = stream.toByteArray();
        coffee.setImage(bitmapData);

        coffees.add(coffee);

        coffee = new Coffee();

        //13
        coffee.setName("Soy Cappuccino");
        coffee.setCapsules(2);
        coffee.setBars1(2);
        coffee.setBars2(5);
        coffee.setDescription("O Soy Cappuccino mantém o harmonioso encontro entre o café 100% Arábica e a cremosidade do leite, sem lactose.");
//        coffee.setImageId(R.drawable.soycappuccino);

        drawable = getDrawable(R.drawable.soycappuccino);
        bitmap = ((BitmapDrawable) drawable).getBitmap();
        stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        bitmapData = stream.toByteArray();
        coffee.setImage(bitmapData);

        coffees.add(coffee);

        coffee = new Coffee();

        //14
        coffee.setName("Vanilla Latte Macchiato");
        coffee.setCapsules(2);
        coffee.setBars1(2);
        coffee.setBars2(5);
        coffee.setDescription("O sabor da baunilha é que faz essa bebida tão especial. Descubra o sabor do encontro de um delicioso café, leite cremoso e um toque de baunilha.");
//        coffee.setImageId(R.drawable.vanillalattemacchiato);

        drawable = getDrawable(R.drawable.vanillalattemacchiato);
        bitmap = ((BitmapDrawable) drawable).getBitmap();
        stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        bitmapData = stream.toByteArray();
        coffee.setImage(bitmapData);

        coffees.add(coffee);

        coffee = new Coffee();

        //15
        coffee.setName("Nestea Limão");
        coffee.setCapsules(1);
        coffee.setBars1(7);
        coffee.setBars2(0);
        coffee.setDescription("Todo o frescor de Nestea® em um só clique. Chá refrescante com sabor limão e uma generosa camada de espuma.");
//        coffee.setImageId(R.drawable.nestealimao);

        drawable = getDrawable(R.drawable.nestealimao);
        bitmap = ((BitmapDrawable) drawable).getBitmap();
        stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        bitmapData = stream.toByteArray();
        coffee.setImage(bitmapData);

        coffees.add(coffee);

        coffee = new Coffee();

        //16
        coffee.setName("Nestea Pêssego");
        coffee.setCapsules(1);
        coffee.setBars1(7);
        coffee.setBars2(0);
        coffee.setDescription("Todo o frescor de Nestea® em um só clique. Chá refrescante com sabor pêssego e uma generosa camada de espuma.");
//        coffee.setImageId(R.drawable.nesteapessego);

        drawable = getDrawable(R.drawable.nesteapessego);
        bitmap = ((BitmapDrawable) drawable).getBitmap();
        stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        bitmapData = stream.toByteArray();
        coffee.setImage(bitmapData);

        coffees.add(coffee);

        coffee = new Coffee();

        //17
        coffee.setName("Chococino");
        coffee.setCapsules(2);
        coffee.setBars1(3);
        coffee.setBars2(3);
        coffee.setDescription("Chocolate quente cremoso feito com os melhores grãos de cacau, que se misturam com a riqueza e cremosidade do leite. Para todos aqueles que apreciam o verdadeiro sabor do chocolate.");
//        coffee.setImageId(R.drawable.chococino);

        drawable = getDrawable(R.drawable.chococino);
        bitmap = ((BitmapDrawable) drawable).getBitmap();
        stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        bitmapData = stream.toByteArray();
        coffee.setImage(bitmapData);

        coffees.add(coffee);

        coffee = new Coffee();

        //18
        coffee.setName("Chococino Caramel");
        coffee.setCapsules(2);
        coffee.setBars1(3);
        coffee.setBars2(3);
        coffee.setDescription("Uma combinação única. Todo o sabor do chocolate com a cremosidade do leite um irresistível toque de caramelo. Seu paladar vai se apaixonar.");
//        coffee.setImageId(R.drawable.chococinocaramel);

        drawable = getDrawable(R.drawable.chococinocaramel);
        bitmap = ((BitmapDrawable) drawable).getBitmap();
        stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        bitmapData = stream.toByteArray();
        coffee.setImage(bitmapData);

        coffees.add(coffee);

        coffee = new Coffee();

        //19
        coffee.setName("Mocha");
        coffee.setCapsules(2);
        coffee.setBars1(3);
        coffee.setBars2(3);
        coffee.setDescription("A bebida perfeita para quem quer entrar no mundo do café. Combinação do leite cremoso, chocolate e um toque de café 100% Arábica.");
//        coffee.setImageId(R.drawable.mocha);

        drawable = getDrawable(R.drawable.mocha);
        bitmap = ((BitmapDrawable) drawable).getBitmap();
        stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        bitmapData = stream.toByteArray();
        coffee.setImage(bitmapData);

        coffees.add(coffee);

        coffee = new Coffee();

        //20
        coffee.setName("Nescau");
        coffee.setCapsules(1);
        coffee.setBars1(4);
        coffee.setBars2(0);
        coffee.setDescription("O sabor do achocolatado mais famoso do Brasil, em uma só cápsula. Contém Active-Go: um composto exclusivo com cálcio, ferro e vitaminas para o seu café da manhã ficar ainda mais completo.");
//        coffee.setImageId(R.drawable.nescau);

        drawable = getDrawable(R.drawable.nescau);
        bitmap = ((BitmapDrawable) drawable).getBitmap();
        stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        bitmapData = stream.toByteArray();
        coffee.setImage(bitmapData);

        coffees.add(coffee);

        coffee = new Coffee();

        //21
        coffee.setName("Café Matinal");
        coffee.setCapsules(1);
        coffee.setBars1(4);
        coffee.setBars2(0);
        coffee.setDescription("Para deixar ainda mais brasileiro e associá-lo a sua ocasião perfeita: o Café da Manhã!");
//        coffee.setImageId(R.drawable.cafematinal);

        drawable = getDrawable(R.drawable.cafematinal);
        bitmap = ((BitmapDrawable) drawable).getBitmap();
        stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        bitmapData = stream.toByteArray();
        coffee.setImage(bitmapData);

        coffees.add(coffee);

        coffee = new Coffee();

        //22
        coffee.setName("Lungo");
        coffee.setCapsules(1);
        coffee.setBars1(4);
        coffee.setBars2(0);
        coffee.setDescription("Grãos 100% Arábica com torrefação média, aroma frutado e torrado. Intenso sabor aromático, ligeiramente picante, com notas persistentes no paladar mesmo após degustação. Pode ser degustado no café da manhã ou em qualquer hora do dia.");
//        coffee.setImageId(R.drawable.lungo);

        drawable = getDrawable(R.drawable.lungo);
        bitmap = ((BitmapDrawable) drawable).getBitmap();
        stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        bitmapData = stream.toByteArray();
        coffee.setImage(bitmapData);

        coffees.add(coffee);

        coffee = new Coffee();

        //23
        coffee.setName("Chai Tea Latte");
        coffee.setCapsules(2);
        coffee.setBars1(3);
        coffee.setBars2(3);
        coffee.setDescription("A exótica combinação do chá preto, leite cremoso e uma seleta combinação de especiarias: canela, gengibre cardamomo e cravo.");
//        coffee.setImageId(R.drawable.chaitealatte);

        drawable = getDrawable(R.drawable.chaitealatte);
        bitmap = ((BitmapDrawable) drawable).getBitmap();
        stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        bitmapData = stream.toByteArray();
        coffee.setImage(bitmapData);

        coffees.add(coffee);

        coffee = new Coffee();

        //24
        coffee.setName("Marrakesh Style Tea");
        coffee.setCapsules(1);
        coffee.setBars1(6);
        coffee.setBars2(0);
        coffee.setDescription("Mergulhe na maravilha do chá marroquino. Uma mistura do aromático chá verde, menta intensamente saborosa e refrescante, coberto com a cremosa espuma e adoçado com perfeição.");
//        coffee.setImageId(R.drawable.marrakesh);

        drawable = getDrawable(R.drawable.marrakesh);
        bitmap = ((BitmapDrawable) drawable).getBitmap();
        stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        bitmapData = stream.toByteArray();
        coffee.setImage(bitmapData);

        coffees.add(coffee);

        coffee = new Coffee();

        dbUpdate update = new dbUpdate();

        for(it = 0; it < coffees.size(); it++) {
            if(update.addCoffee(coffees.get(it))) {
                Log.i("Added coffe", " => " + coffees.get(it).getName());
//                Log.i("Added coffe", " => " + coffees.get(it).getCapsules());
//                Log.i("Added coffe", " => " + coffees.get(it).getBars1());
//                Log.i("Added coffe", " => " + coffees.get(it).getBars2());
//                Log.i("Added coffe", " => " + coffees.get(it).getDescription());
            }
        }
//        webArray.add("Espresso Intenso Decaffeinato");
//        webArray.add("Ristretto");
//        webArray.add("Ristretto Ardenza");
//        webArray.add("Espresso");
//        webArray.add("Espresso Decaffeinato");
//        webArray.add("Espresso Intenso");
//        webArray.add("Espresso Barista");
//        webArray.add("Café Au Lait");
//        webArray.add("Capuccino");
//        webArray.add("Caramel Latte Macchiato");
//        webArray.add("Cortado");
//        webArray.add("Latte Macchiato");
//        webArray.add("Soy Cappuccino");
//        webArray.add("Vanilla Latte Macchiato");
//        webArray.add("Nestea Limão");
//        webArray.add("Nestea Pêssego");
//        webArray.add("Chococino");
//        webArray.add("Chococino Caramel");
//        webArray.add("Mocha");
//        webArray.add("Nescau");
//        webArray.add("Café Matinal");
//        webArray.add("Lungo");
//        webArray.add("Chai Tea Latte");
//        webArray.add("Marrakesh Style Tea");
//
//        imageIdArray.add(R.drawable.espressointensodecaf);
//        imageIdArray.add(R.drawable.ristretto);
//        imageIdArray.add(R.drawable.ristrettoardenza);
//        imageIdArray.add(R.drawable.espresso);
//        imageIdArray.add(R.drawable.espressodecaf);
//        imageIdArray.add(R.drawable.espressointensodecaf);
//        imageIdArray.add(R.drawable.espressobarista);
//        imageIdArray.add(R.drawable.cafeaulait);
//        imageIdArray.add(R.drawable.capuccino);
//        imageIdArray.add(R.drawable.caramellattemacchiato);
//        imageIdArray.add(R.drawable.cortado);
//        imageIdArray.add(R.drawable.lattemacchiato);
//        imageIdArray.add(R.drawable.soycappuccino);
//        imageIdArray.add(R.drawable.vanillalattemacchiato);
//        imageIdArray.add(R.drawable.nestealimao);
//        imageIdArray.add(R.drawable.nesteapessego);
//        imageIdArray.add(R.drawable.chococino);
//        imageIdArray.add(R.drawable.chococinocaramel);
//        imageIdArray.add(R.drawable.mocha);
//        imageIdArray.add(R.drawable.nescau);
//        imageIdArray.add(R.drawable.cafematinal);
//        imageIdArray.add(R.drawable.lungo);
//        imageIdArray.add(R.drawable.chaitealatte);
//        imageIdArray.add(R.drawable.marrakesh);
//
//        descArray.add("Cuidadosamente descafeinado, preserva todo o aroma, intensidade e sabor do nosso Espresso Intenso.");
//        descArray.add("Um blend de grãos selecionados Arábica e Robusta com torra mais intensa, sabor marcante e elegante, revelando um mix de aromas, como castanha, maçã e finalizando com notas de limão. Para aqueles que gostam de um “shot” de café, como os italianos.");
//        descArray.add("Blend de Arábica e Robusta, em perfeito de equilíbrio: perfil sensorial mais intenso de todos os cafés com notas de alcaçuz e leve toque picante. Café marcante que deixa um ater tasting após a degustação.");
//        descArray.add("O autêntico café Espresso. Encorpado e com uma crema única. Feito com grãos 100% Arábica, cuidadosamente torrados e moídos.");
//        descArray.add("Feito com grãos 100% Arábica, nosso descafeinado preserva todo o aroma e sabor do Espresso.");
        //5
//        descArray.add("O irmão mais velho de nosso Espresso, ainda mais aromático e intenso. Aroma floral, sabor levemente picante, amargo e forte. Combinação de grãos selecionados de Arábica e Robusta.");
//        descArray.add("Café de alta torrefação de seus grãos Arábicas e Robusta, aroma aveludado e crema generosa, estilo café italiano. Rico e equilibrado com notas de chocolate e sabor requintado. Para aqueles que entendem e apreciam um bom café.");
//        descArray.add("A combinação perfeita do Café com Leite. Em uma só cápsula, grãos selecionados do tipo Robusta e leite integral.");
//        descArray.add("Descubra o que ocorre quando um aromático Espresso 100% Arábica encontra a cremosidade do leite. Crema densa e aveludada e corpo equilibrado. O nosso clássico italiano.");
//        descArray.add("Todo o sabor do tradicional Latte Macchiato, com um toque irresistível de caramelo.");
        //10
//        descArray.add("Encorpado e intenso, nosso Espresso 100% Arábica com uma toque especial de leite.");
//        descArray.add("Quando você preparar, vai entender que esse sabor não é só um Café com Leite. Torrefação suave, combinado com a crema de leite integral, levemente adocicado. Grãos 100% Arábica.");
//        descArray.add("O Soy Cappuccino mantém o harmonioso encontro entre o café 100% Arábica e a cremosidade do leite, sem lactose.");
//        descArray.add("O sabor da baunilha é que faz essa bebida tão especial. Descubra o sabor do encontro de um delicioso café, leite cremoso e um toque de baunilha.");
//        descArray.add("Todo o frescor de Nestea® em um só clique. Chá refrescante com sabor limão e uma generosa camada de espuma.");
        //15
//        descArray.add("Todo o frescor de Nestea® em um só clique. Chá refrescante com sabor pêssego e uma generosa camada de espuma.");
//        descArray.add("Chocolate quente cremoso feito com os melhores grãos de cacau, que se misturam com a riqueza e cremosidade do leite. Para todos aqueles que apreciam o verdadeiro sabor do chocolate.");
//        descArray.add("Uma combinação única. Todo o sabor do chocolate com a cremosidade do leite um irresistível toque de caramelo. Seu paladar vai se apaixonar.");
//        descArray.add("A bebida perfeita para quem quer entrar no mundo do café. Combinação do leite cremoso, chocolate e um toque de café 100% Arábica.");
//        descArray.add("O sabor do achocolatado mais famoso do Brasil, em uma só cápsula. Contém Active-Go: um composto exclusivo com cálcio, ferro e vitaminas para o seu café da manhã ficar ainda mais completo.");
        //20
//        descArray.add("Para deixar ainda mais brasileiro e associá-lo a sua ocasião perfeita: o Café da Manhã!");
//        descArray.add("Grãos 100% Arábica com torrefação média, aroma frutado e torrado. Intenso sabor aromático, ligeiramente picante, com notas persistentes no paladar mesmo após degustação. Pode ser degustado no café da manhã ou em qualquer hora do dia.");
//        descArray.add("A exótica combinação do chá preto, leite cremoso e uma seleta combinação de especiarias: canela, gengibre cardamomo e cravo.");
//        descArray.add("Mergulhe na maravilha do chá marroquino. Uma mistura do aromático chá verde, menta intensamente saborosa e refrescante, coberto com a cremosa espuma e adoçado com perfeição.");
//
//
//        tracosCapsula1Array.add(2);
//        tracosCapsula1Array.add(1);
//        tracosCapsula1Array.add(1);
//        tracosCapsula1Array.add(2);
//        tracosCapsula1Array.add(2); //5
//        tracosCapsula1Array.add(2);
//        tracosCapsula1Array.add(1);
//        tracosCapsula1Array.add(6);
//        tracosCapsula1Array.add(1);
//        tracosCapsula1Array.add(2); //10
//        tracosCapsula1Array.add(3);
//        tracosCapsula1Array.add(2);
//        tracosCapsula1Array.add(2);
//        tracosCapsula1Array.add(2);
//        tracosCapsula1Array.add(7); //15
//        tracosCapsula1Array.add(7);
//        tracosCapsula1Array.add(3);
//        tracosCapsula1Array.add(3);
//        tracosCapsula1Array.add(3);
//        tracosCapsula1Array.add(4); //20
//        tracosCapsula1Array.add(4);
//        tracosCapsula1Array.add(4);
//        tracosCapsula1Array.add(3);
//        tracosCapsula1Array.add(6);
//
//        tracosCapsula2Array.add(0);
//        tracosCapsula2Array.add(0);
//        tracosCapsula2Array.add(0);
//        tracosCapsula2Array.add(0);
//        tracosCapsula2Array.add(0); //5
//        tracosCapsula2Array.add(0);
//        tracosCapsula2Array.add(0);
//        tracosCapsula2Array.add(0);
//        tracosCapsula2Array.add(6);
//        tracosCapsula2Array.add(5); //10
//        tracosCapsula2Array.add(0);
//        tracosCapsula2Array.add(5);
//        tracosCapsula2Array.add(5);
//        tracosCapsula2Array.add(5);
//        tracosCapsula2Array.add(0); //15
//        tracosCapsula2Array.add(0);
//        tracosCapsula2Array.add(3);
//        tracosCapsula2Array.add(3);
//        tracosCapsula2Array.add(3);
//        tracosCapsula2Array.add(0); //20
//        tracosCapsula2Array.add(0);
//        tracosCapsula2Array.add(0);
//        tracosCapsula2Array.add(3);
//        tracosCapsula2Array.add(0);

    }

    public Bitmap decodeByteArray(byte[] byteArray) {
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
    }

    public void generateArrays() {
        ArrayList<Coffee> coffees = new dbRead().getCoffees();

        Log.d("DB SIZE", " " + coffees.size());

        webArray = new ArrayList<>();
        imageArray = new ArrayList<>();

        webArray.clear();
        imageArray.clear();

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
        new dbCreate().createTable();
//        dbDelete d = new dbDelete();
//        d.removeTable();

        /**
         * Step to run the array initialization only once, than
         * resetting the array, with the purpose of adding new
         * coffees to the list.
         */
        if(firstRun) {
            initiateDB();
            generateArrays();
            Log.i("PRIMEIRA VEZ", "!");
        }else{
//            webArray.clear();
//            imageIdArray.clear();
//            descArray.clear();
//            tracosCapsula1Array.clear();
//            tracosCapsula2Array.clear();
//            createArrays();
            generateArrays();
            Log.i("AGLUMA VEZ", "!");

            /**
             * Checking for information from the AddProductActivity activity
             * which will be used to refresh the list of coffees.
             */

//            Bundle c = this.getIntent().getExtras();
//            if(c != null) {
//                n = c.getString("Nome");
//                d = c.getString("Desc");
//                c1 = c.getInt("Tracos1");
//                c2 = c.getInt("Tracos2");
//
////                Log.i("=======> N ", " => " + n);
////                Log.i("=======> D ", " => " + d);
//
////                Log.i("WebArray Size: ", " => " + webArray.size());
//
//                namesExtra.add(n);
//                descriptionsExtra.add(d);
//                imagesExtra.add(R.drawable.coffecapsule);
//                tracos1Extra.add(c1);
//                tracos2Extra.add(c2);
//
//                webArray.addAll(namesExtra);
//                descArray.addAll(descriptionsExtra);
//                imageIdArray.addAll(imagesExtra);
//                tracosCapsula1Array.addAll(tracos1Extra);
//                tracosCapsula2Array.addAll(tracos2Extra);
//
//            }
        }
        firstRun = false;

//        Log.i("ONCREATE CALLED ", "!!!" );

//        Log.i("WebArray Size: ", " => " + webArray.size());

        /**
         * Initilizing the GridView to show the coffees.
         */

        adapter =  new CoffeGrid(MainActivity.this, webArray, imageArray);

        grid = (GridView) findViewById(R.id.coffeGrid);

        grid.setAdapter(adapter);

        /**
         * Preparing data to be sent to the next acitivity, which will display information about the
         * selected coffee.
         */
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(MainActivity.this, "You Clicked at " + web[position], Toast.LENGTH_SHORT).show();
                Bundle b = new Bundle();
//                b.putIntegerArrayList("ImagesIDs", imageIdArray);
//                b.putStringArrayList("ProductNames", webArray);
//                b.putStringArrayList("Desc", descArray);
//                b.putIntegerArrayList("Capsula1", tracosCapsula1Array);
//                b.putIntegerArrayList("Capsula2", tracosCapsula2Array);
//                b.putInt("Pos", position);
                coffeName = webArray.get(position);
                b.putString("NAME", coffeName);
//                Log.d("TESTE", "position: " + position);
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
}
