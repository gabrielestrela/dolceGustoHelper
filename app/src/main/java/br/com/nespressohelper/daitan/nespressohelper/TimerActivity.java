package br.com.nespressohelper.daitan.nespressohelper;

import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.TextView;
import com.github.lzyzsd.circleprogress.DonutProgress;

/**
 * This activity is responsible to alert the user when to stop
 * the water flow from the machine. It receives all the
 * necessary information from the previous activities, like
 * how many bars the coffee has.
 *
 */
public class TimerActivity extends AppCompatActivity {

    private DonutProgress dp;
    private static int SECONDSPERBAR = 3 * 1000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        /**
         * Receiving the number of bars.
         */
        Bundle b = this.getIntent().getExtras();
        final String coffeeName = b.getString("NAME");
        DBRead coffeeRead = new DBRead();
        Coffee coffee = coffeeRead.getCoffee(coffeeName);
        final int bars1 = coffee.getBars1();
        final int bars2 = coffee.getBars2();

        final int calc1 = bars1 * 3;
        final int calc2 = bars2 * 3;

        /**
         * Checking if the coffee utilizes two capsules,
         * if not, it will only alert one time, otherwise,
         * it will ring twice, one time per capsule.
         */
        if(bars2 == 0){
            dp = (DonutProgress) findViewById(R.id.donut_progress);
            TextView instructions = (TextView) findViewById(R.id.instructions);

            instructions.setText("\n1 - Coloque a capsula de cafe/chocolate/cha na maquina!\n\n2 - Acione a maquina e Clique em OK no celular\n\n3 - Espere o alarme tocar para desligar a maquina");

            // 1. Instantiate an AlertDialog.Builder with its constructor
            AlertDialog.Builder builder = new AlertDialog.Builder(TimerActivity.this);

            // 2. Chain together various setter methods to set the dialog characteristics
            builder.setMessage("Clique em Ok para iniciar o timer da capsula de cafe/chocolate/cha!")
                    .setTitle("Iniciar");

            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    new CountDownTimer(bars1 * SECONDSPERBAR, 1000) {

                        public void onTick(long millisUntilFinished) {
                            dp.setProgress(dp.getProgress()+ 100/calc1);
                        }

                        public void onFinish() {
                            dp.setProgress(100);
                            MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.shipbell);
                            mediaPlayer.start(); // no need to call prepare(); create() does that for you
                        }
                    }.start();
                }
            });

            // 3. Get the AlertDialog from create()
            AlertDialog dialog = builder.create();

            dialog.show();
        }
        /**
         * The coffee has two capsules, so it needs two timers
         * one for each capsule. At the end of each timer it will ring
         * alarm, to warns the user that he/she can stops the water flow.
         */
        else {
            dp = (DonutProgress) findViewById(R.id.donut_progress);
            TextView instructions = (TextView) findViewById(R.id.instructions);

            instructions.setText("\n1 - Coloque a capsula de cafe/chocolate/leite/cha na maquina!\n\n2 - Acione a maquina e Clique em OK no celular\n\n3 - Espere o alarme tocar para desligar a maquina");

            // 1. Instantiate an AlertDialog.Builder with its constructor
            AlertDialog.Builder builder = new AlertDialog.Builder(TimerActivity.this);

            // 2. Chain together various setter methods to set the dialog characteristics
            builder.setMessage("Clique em Ok para iniciar o timer da capsula de cafe/chocolate/cha!")
                    .setTitle("Iniciar!");

            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    new CountDownTimer(bars1* SECONDSPERBAR, 1000) {

                        public void onTick(long millisUntilFinished) {
                            dp.setProgress(dp.getProgress()+ 100/calc1);
                        }

                        public void onFinish() {
                            dp.setProgress(100);

                            MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.shipbell);
                            mediaPlayer.start(); // no need to call prepare(); create() does that for you

                            // 1. Instantiate an AlertDialog.Builder with its constructor
                            AlertDialog.Builder builder = new AlertDialog.Builder(TimerActivity.this);

                            // 2. Chain together various setter methods to set the dialog characteristics
                            builder.setMessage("Clique em Ok para iniciar o timer da capsula de leite!")
                                    .setTitle("Continuar?");

                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    new CountDownTimer(bars2* SECONDSPERBAR, 1000) {

                                        public void onTick(long millisUntilFinished) {
                                            dp.setProgress(dp.getProgress()+ 100/calc2);
                                        }

                                        public void onFinish() {
                                            dp.setProgress(100);
                                            MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.shipbell);
                                            mediaPlayer.start(); // no need to call prepare(); create() does that for you
                                        }
                                    }.start();
                                }
                            });

                            // 3. Get the AlertDialog from create()
                            AlertDialog dialog = builder.create();

                            dialog.show();
                        }
                    }.start();
                }
            });
            AlertDialog dialog = builder.create();

            dialog.show();
        }
    }
}
