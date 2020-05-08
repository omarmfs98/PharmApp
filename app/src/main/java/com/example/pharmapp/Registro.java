package com.example.pharmapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class Registro extends AppCompatActivity {
    private EditText nombreUsuario, editcorreo, editcontraseña, edittelefono;
    private Button registro;
    private Button atras;
    private ProgressDialog progressDialog;

    private String name="";
    private String email="";
    private String password="";
    private String phone="";

        FirebaseAuth mAuth;
        DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        nombreUsuario = (EditText) findViewById(R.id.reg_name);
        editcorreo = (EditText) findViewById(R.id.reg_correo);
        editcontraseña = (EditText) findViewById(R.id.reg_contra);
        edittelefono = (EditText) findViewById(R.id.reg_telefono);
        registro = (Button) findViewById(R.id.enviar_registro);
        atras=(Button)findViewById(R.id.atras);
        progressDialog = new ProgressDialog(this);

        atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View vista) { Intent intent = new Intent(Registro.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("EXIT", true);
                startActivity(intent);
            } });

        registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             name = nombreUsuario.getText().toString();
             email = editcorreo.getText().toString();
             password = editcontraseña.getText().toString();
             phone = edittelefono.getText().toString();

             if (!name.isEmpty() && !email.isEmpty() && !password.isEmpty() && !phone.isEmpty()){
                 if (password.length() >=6){
                     registerUser();
                 }else{
                     Toast.makeText(Registro.this, "La contraseña debe tener al menos 6 caracteres", Toast.LENGTH_SHORT).show();
                 }
             }else{
                 Toast.makeText(Registro.this, "Debe completar los demas campos", Toast.LENGTH_SHORT).show();
             }
            }
        });
    }
    private void registerUser(){
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful() ){
                    Map<String, Object> map = new HashMap<>();
                    map.put("name",name);
                    map.put("email", email);
                    map.put("password",password);
                    map.put("phone", phone);
                    progressDialog.setMessage("Realizando registro en linea...");
                    progressDialog.show();

                    String id = mAuth.getCurrentUser().getUid();
                  mDatabase.child("Users").child(id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                      @Override
                      public void onComplete(@NonNull Task<Void> task2) {
                          if(task2.isSuccessful()){
                            startActivity(new Intent(Registro.this, Home.class));
                            finish();
                          }else{
                              Toast.makeText(Registro.this, "No se pudieron crear los datos correctamente", Toast.LENGTH_SHORT).show();
                          }
                      }
                  });
                }else{
                    Toast.makeText(Registro.this, "No se pudo registrar este usuario", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

}
