package br.ifamjogo.view;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

public class MainActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    int moeda;
    int pontos;
    private Button bt_1;
    private Button bt_2;
    private Button bt_3;
    private Button apostar;
    private Button novoJogo;
    private Button voltar;
    private TextView lbl_resultado_moeda;
    private TextView lbl_resultado_pontuacao;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bt_1=findViewById(R.id.bt_1);
        bt_2=findViewById(R.id.bt_2);
        bt_3=findViewById(R.id.bt_3);
        apostar = findViewById(R.id.apostar);
        novoJogo=findViewById(R.id.novoJogo);
        voltar =findViewById(R.id.voltar);
        lbl_resultado_moeda=findViewById(R.id.lbl_resultado_moeda);
        lbl_resultado_pontuacao=findViewById(R.id.lbl_resultado_pontuacao);
        iniciarSom();


        novoJogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                moeda=10;
                pontos=0;
                bt_1.setEnabled(true);
                bt_2.setEnabled(true);
                bt_3.setEnabled(true);
                apostar.setEnabled(true);
                lbl_resultado_moeda.setText("" + moeda);
                lbl_resultado_pontuacao.setText(""+pontos);
            }
        });

        voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               finish();

            }
        });

        apostar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int i1,i2,i3;
                Random r1 = new Random();
                Random r2 = new Random();
                Random r3 = new Random();
                //Definição dos valores nas variaveis
                i1 = r1.nextInt(10 );
                i2 = r2.nextInt(10);
                i3 = r3.nextInt(10);
                bt_1.setText(String.valueOf(i1));
                bt_2.setText(String.valueOf(i2));
                bt_3.setText(String.valueOf(i3));
                checarJogo(i1,i2,i3);
            }
        });

    }

    //Loógica De Pontos
    public void checarJogo(int a, int b, int c){

        //Lógica se os três números cairem iguais
        if(     (a==9) &&(b==9)&&(c==9) ||
                (a==8) &&(b==8)&&(c==8) ||
                (a==7) &&(b==7)&&(c==7) ||
                (a==6) &&(b==6)&&(c==6) ||
                (a==5) &&(b==5)&&(c==5) ||
                (a==4) &&(b==4)&&(c==4) ||
                (a==3) &&(b==3)&&(c==3) ||
                (a==2) &&(b==2)&&(c==2) ||
                (a==1) &&(b==1)&&(c==1) ||
                (a==0) &&(b==0)&&(c==0) ||
                (a==0) &&(b==0)&&(c==7)){

            pararSom();
            moeda = moeda + 100;
            pontos=pontos+100;
            //Winner
            AlertDialog.Builder alertaVenceu = new AlertDialog.Builder(this);
            alertaVenceu.setTitle("VITÓRIA!!");
            alertaVenceu.setIcon(R.drawable.winner);
            alertaVenceu.setMessage("Parabéns, você venceu!");
            alertaVenceu.setPositiveButton("VOLTAR",new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    pararWinner();
                    iniciarSom();
                }
            });
               // .setPositiveButtonIcon(getDrawable(R.drawable.botao_voltar));
            alertaVenceu.create();
            alertaVenceu.show();
            lbl_resultado_moeda.setText(String.valueOf(moeda));
            lbl_resultado_pontuacao.setText(String.valueOf(pontos));
            bt_1.setEnabled(false);
            bt_2.setEnabled(false);
            bt_3.setEnabled(false);
            winner_Som();
        }
        else{
            //Lógica se só dois números cairem iguais
            if(((a==7) && (b==7)) || (a==7)&&(c==7) || ((b==7))&&((c==7))){
                moeda=moeda+2;
                pontos=pontos+20;
                lbl_resultado_moeda.setText(String.valueOf(moeda));
                lbl_resultado_pontuacao.setText(String.valueOf(pontos));
            }
            else{
                //Lógica se só um número cair igual
                if((a==7) || (b==7) || (c==7)){
                    moeda=moeda+1;
                    pontos=pontos+10;
                    lbl_resultado_moeda.setText(String.valueOf(moeda));
                    lbl_resultado_pontuacao.setText(String.valueOf(pontos));
                }
                else{
                    //Lógica GameOver
                    if((a!=7) && (b!=7)&&(c!=7)){
                        moeda=moeda-1;
                        lbl_resultado_moeda.setText(String.valueOf(moeda));
                        lbl_resultado_pontuacao.setText(String.valueOf(pontos));
                        if(moeda==0){
                            pararSom();
                            gameOver_Som();
                            AlertDialog.Builder alertaPerdeu = new AlertDialog.Builder(this);
                            alertaPerdeu.setTitle("GAME OVER '-'");
                            alertaPerdeu.setIcon(R.drawable.gameover);
                            alertaPerdeu.setMessage("Infelizmente você perdeu! \nTente Novamente!");
                            alertaPerdeu.setPositiveButton("VOLTAR",new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                        pararGameOver();
                                        iniciarSom();

                                }
                            });
                              // .setPositiveButtonIcon(getDrawable(R.drawable.botao_voltar));
                            alertaPerdeu.create();
                            alertaPerdeu.show();
                            bt_1.setEnabled(false);
                            bt_2.setEnabled(false);
                            bt_3.setEnabled(false);
                            apostar.setEnabled(false);

                        }
                    }
                }

            }

        }


    }

    //Sistemas de som e Parar Som
    public void winner_Som() {
        mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.winner);
        mediaPlayer.start();
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mediaPlayer.release();
            }
        });
    }

    public void pararWinner() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    public void gameOver_Som() {
        mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.game_over);
        mediaPlayer.start();
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mediaPlayer.release();
            }
        });
    }
    public void pararGameOver() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }


    public void iniciarSom() {
        mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.musica_game);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mediaPlayer.release();
            }
        });
    }


    public void pararSom() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        pararSom();
    }


}