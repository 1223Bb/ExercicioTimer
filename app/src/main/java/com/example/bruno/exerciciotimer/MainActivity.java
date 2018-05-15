package com.example.bruno.exerciciotimer;

import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity {
    private AnimationDrawable anima;
    private AnimationUtils an01, an02;
    public MediaPlayer player;
    public SeekBar skb;
    public int num=0;
    private CountDownTimer time;
    private boolean iniciouDevagar = false;
    private ImageView img;
    private Button btnGira;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnGira = (Button)findViewById(R.id.btnGirar);
        skb = (SeekBar)findViewById(R.id.skbTempo);
        img = (ImageView)findViewById(R.id.imgPiao);
        player = MediaPlayer.create(MainActivity.this, R.raw.piao);
        botao();
    }

    private void botao(){
        btnGira.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num = skb.getProgress();
                if (num>30){
                    giraRapido();
                    tempo(num*1000);
                }else{
                    giraDevagar();
                    tempo(num*1000);
                }
            }
        });
    }

    private void tempo(int seg){
        time = new CountDownTimer(seg, 1000){
            @Override
            public void onTick(long l){
                num = skb.getProgress();
                num--;
                skb.setProgress(num);
                if (num<40 && iniciouDevagar == false){
                    giraDevagar();
                }
            }
            @Override
            public void onFinish(){
                anima.stop();
            }
        }.start();
    }

    private void giraRapido(){
        tocarSom();
        img.setBackgroundResource(R.drawable.giranumrapido);
        anima = (AnimationDrawable)img.getBackground();
        anima.start();
    }

    private void giraDevagar(){
        tocarSom();
        iniciouDevagar = true;
        img.setBackgroundResource(R.drawable.giranumdevagar);
        anima = (AnimationDrawable)img.getBackground();
        anima.start();
    }

    private void tocarSom(){
        try{
            if (player.isPlaying() && iniciouDevagar==false){
                player.release();
                player = MediaPlayer.create(MainActivity.this, R.raw.piao);
                iniciouDevagar=true;
            }
            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mp.release();
                }
            });
            player.start();
        }catch (Exception e){
            player.release();
        }
    }
}
