package com.claudiovitorio.firebaseapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

public class LoginActivity extends AppCompatActivity {
private Button btnCadastrar;
private Button btnLogin;
private EditText editEmail, editSenha;

private FirebaseAuth auth = FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnCadastrar = findViewById(R.id.login_btn_cadastrar);
        btnLogin = findViewById(R.id.login_btn_logar);
        editEmail = findViewById(R.id.login_edit_email);
        editSenha = findViewById(R.id.login_edit_senha);

        //caso usario logado
        if (auth.getCurrentUser()!=null){
            String email = auth.getCurrentUser().getEmail();
            Intent intent = new Intent(getApplicationContext(), NavigationActivity.class);
            intent.putExtra("email", email);
            startActivity(intent);
        }

        btnCadastrar.setOnClickListener(view -> {
                Intent intent = new Intent(getApplicationContext(),CadastroActivty.class);
                startActivity(intent);

        });
        btnLogin.setOnClickListener(view -> {
            logar();
        });
    }
    public void logar(){
        String email = editEmail.getText().toString();
        String senha = editSenha.getText().toString();
        if (email.isEmpty() || senha.isEmpty()){
            Toast.makeText(this,"Preencha os campos!!",Toast.LENGTH_SHORT).show();
            return;
        }
        //t -> ?? uma tarefa para logar
         auth.signInWithEmailAndPassword(email,senha)
        .addOnSuccessListener(authResult -> {
            Toast.makeText(this,"Bem vindo", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), NavigationActivity.class);
            startActivity(intent);
        })
        //listener de falha
        .addOnFailureListener(e ->{
          try {
              //disparando a exce????o
              throw  e;
          }catch (FirebaseAuthInvalidUserException userException){
              //exce????o para o email errado
              Toast.makeText(this, "Email inv??lido!", Toast.LENGTH_SHORT).show();
          }catch (FirebaseAuthInvalidCredentialsException creadException){
              //exce????o p/ a Senha errada
              Toast.makeText(this, "Senha inv??lido!", Toast.LENGTH_SHORT).show();
          }catch (Exception ex){
              //exce????o gen??rica
              Toast.makeText(this, "Erro!", Toast.LENGTH_SHORT).show();
          }

        });
    }
}
