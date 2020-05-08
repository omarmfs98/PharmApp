package com.example.pharmapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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

public class Login extends AppCompatActivity {
    private EditText editcorreo, editcontraseña;
    private Button mButtonLogin;
    private ProgressDialog progressDialog;

    private String email="";
    private String password="";

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        editcorreo = (EditText) findViewById(R.id.reg_correo);
        editcontraseña = (EditText) findViewById(R.id.reg_contra);
        mButtonLogin = (Button) findViewById(R.id.iniciar_login);
        progressDialog = new ProgressDialog(this);

        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = editcorreo.getText().toString();
                password = editcontraseña.getText().toString();

                if (!email.isEmpty() && !password.isEmpty()){
                    loginUser();
                }else {
                    Toast.makeText(Login.this, "Por favor complete los campos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private  void loginUser(){
      mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
          @Override
          public void onComplete(@NonNull Task<AuthResult> task) {
              if(task.isSuccessful()){
                  startActivity(new Intent(Login.this, Home.class));
                  progressDialog.setMessage("Realizando Ingreso en linea...");
                  progressDialog.show();
                  finish();
              }else {
                  Toast.makeText(Login.this, "No se pudo iniciar sesion compruebe que los datos sean correctos", Toast.LENGTH_SHORT).show();

              }

          }
      });
    }
}
