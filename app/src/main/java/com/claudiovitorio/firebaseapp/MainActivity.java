package com.claudiovitorio.firebaseapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.claudiovitorio.firebaseapp.model.Upload;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
private FirebaseAuth auth = FirebaseAuth.getInstance();
private Button btnLogout,btnStorage;
private DatabaseReference database = FirebaseDatabase.getInstance().getReference("Uploads");
private ArrayList<Upload> listaUploads = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnLogout = findViewById(R.id.main_btn_logout);
        btnStorage = findViewById(R.id.main_btn_storage);

        btnLogout.setOnClickListener(v -> {
            //deslogar usuario
            auth.signOut();
            finish();

        });
        TextView  textEmail = findViewById(R.id.main_text_email);
        textEmail.setText(auth.getCurrentUser().getEmail());

        TextView  textNome = findViewById(R.id.main_text_user);
        textNome.setText(auth.getCurrentUser().getDisplayName());

        btnStorage.setOnClickListener( v-> {

            Intent i = new Intent(getApplicationContext(),StorageActivity.class);
            startActivity(i);
        });

    }

    @Override
    protected void onStart() {
        // onStart -> faz parte do cliclo de vida de uma Activity
        // -> e executado depois do OnCreate
        // -> é executado quando o app incia,
        // -> e quando volta do background
        super.onStart();
        getData();
    }
    public void getData(){
        //listener p/ nó uploads
        // - caso ocorra alguma alteracao -> retorna tODOS os dados!!
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for( DataSnapshot no_filho : snapshot.getChildren()){
                    Upload upload = no_filho.getValue(Upload.class);
                    listaUploads.add(upload);
                    Log.i("DATABASE", "id:"+ upload.getId()+ ", nome:" + upload.getNomeImagem());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
