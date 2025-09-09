package br.ifamjogo.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import br.ifamjogo.model.ConfiguracaoFirebase;
import br.ifamjogo.model.Usuario;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth autenticacao;
    Switch vaiCadastrar;
    EditText email;
    EditText senha;
    EditText apelido;
    DatabaseReference banco;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        FirebaseApp.initializeApp(LoginActivity.this);
        banco = ConfiguracaoFirebase.getFirebase();
        vaiCadastrar = findViewById(R.id.cadastro_switch);
        email = findViewById(R.id.editTextTextEmail);
        senha = findViewById(R.id.editTextTextPassword);
        apelido = findViewById(R.id.editTextTextNome);
        vaiCadastrar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (vaiCadastrar.isChecked()) {
                    apelido.setVisibility(View.VISIBLE);
                } else {
                    apelido.setVisibility(View.INVISIBLE);
                }
            }
        });
    }
    public void logar(View view) {
        if (email.getText().toString().isEmpty() || senha.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Não foi possivel executar a operação, pois tem campo não preenchido na tela ", Toast.LENGTH_SHORT).show();
        } else {
//Cadastrar
            if (vaiCadastrar.isChecked()) {
                autenticacao.createUserWithEmailAndPassword(email.getText().toString(), senha.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show();
                            Usuario usuario = new Usuario();
                            usuario.setIdUsuario(autenticacao.getCurrentUser().getUid());
                            usuario.setNome(apelido.getText().toString().trim());
                            banco.child("usuarios").child(usuario.getIdUsuario()).setValue(usuario);
                        } else {
                            Toast.makeText(getApplicationContext(), "Erro no cadastro: Senha ou Email Inválidos" , Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            } else {
//Login
                autenticacao.signInWithEmailAndPassword(email.getText().toString(), senha.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), "Erro ao fazer login: Senha ou Email Inválidos " , Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }
    }
}