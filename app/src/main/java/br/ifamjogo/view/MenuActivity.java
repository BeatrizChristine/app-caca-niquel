package br.ifamjogo.view;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER_PORTRAIT);
        Button bJogar = findViewById(R.id.jogar);
        Button bSair = findViewById(R.id.sair);
        Button bSobre = findViewById(R.id.sobre);
        bJogar.setOnClickListener(this);
        bSair.setOnClickListener(this);

    }

    public void onClick(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MenuActivity.this);
        switch (v.getId()) {
            case R.id.sobre:
                builder.setTitle("Regras:");
                builder.setMessage("1 - Se você acertar números iguais nos três quadrados, você ganha!\n" +
                        "\n2 - Se você acertar o número 7 só em dois locais, você ganha 2 moedas e 20 pontos!\n" +
                        "\n3 - Se você acertar o número 7 só um um local, você ganha 1 moeda e 10 ponto!\n" +
                        "\n4 - Se você ficar com 0 moedas para apostar, Game Over!\n" +
                        "\n5 - Total de moedas iniciais: 10\n"+
                        "\nJogo feito para obtenção de nota da matéria de Jogos Mobile.");
                builder.setIcon(android.R.drawable.btn_star_big_on);
                builder.setPositiveButton("Voltar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       
                    }
                }).setPositiveButtonIcon(getDrawable(R.drawable.ic_voltar));
                AlertDialog alert = builder.create();
                alert.show();
        }
        switch (v.getId()) {
            case R.id.jogar:
                Intent i = new Intent(MenuActivity.this, MainActivity.class);
                startActivity(i);
                break;

            case R.id.sair:
                finish();
        }

    }



}