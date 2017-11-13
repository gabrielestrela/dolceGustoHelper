package br.com.nespressohelper.daitan.nespressohelper;

import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.TextView;


import com.github.lzyzsd.circleprogress.DonutProgress;

import java.util.ArrayList;

public class TimerActivity extends AppCompatActivity {

    private DonutProgress dp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        Bundle b = this.getIntent().getExtras();
        final ArrayList<Integer> tracosCapsula1 = b.getIntegerArrayList("Capsula1");
        final ArrayList<Integer> tracosCapsula2 = b.getIntegerArrayList("Capsula2");
        final int pos = b.getInt("Pos");

        final int calc1 = tracosCapsula1.get(pos) * 3;
        final int calc2 = tracosCapsula2.get(pos) * 3;

//        Log.d("TESTE", "100/tracosCapsula[pos]*3: " + (float) 100 / calc1);

        if(tracosCapsula2.get(pos) == 0){
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
                    new CountDownTimer(tracosCapsula1.get(pos)*3*1000, 1000) {

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
        }else {
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
                    new CountDownTimer(tracosCapsula1.get(pos)*3*1000, 1000) {

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
                                    new CountDownTimer(tracosCapsula2.get(pos)*3*1000, 1000) {

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
