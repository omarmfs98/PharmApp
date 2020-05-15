package com.example.pharmapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Registro extends AppCompatActivity {
    private EditText nombreUsuario, editcorreo, editcontraseña, edittelefono;
    private Switch typeUser;
    private Button registro;
    private Button atras;
    private ProgressDialog progressDialog;

    private String name="";
    private String email="";
    private String password="";
    private String phone="";
    private Boolean type = false;
    FirebaseFirestore db;
    FirebaseAuth mAuth;
    DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        nombreUsuario = (EditText) findViewById(R.id.reg_name);
        editcorreo = (EditText) findViewById(R.id.reg_correo);
        editcontraseña = (EditText) findViewById(R.id.reg_contra);
        edittelefono = (EditText) findViewById(R.id.reg_telefono);
        typeUser = (Switch) findViewById(R.id.typeUser);
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
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
             name = nombreUsuario.getText().toString();
             email = editcorreo.getText().toString();
             password = editcontraseña.getText().toString();
             phone = edittelefono.getText().toString();
             type = typeUser.isChecked();

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
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful() ) {
                    FirebaseUser createdUser = mAuth.getCurrentUser();
                    Map<String, Object> user = new HashMap<>();
                    user.put("uid", createdUser.getUid());
                    user.put("name", name);
                    user.put("email", email);
                    user.put("password", password);
                    user.put("phone", phone);
                    user.put("type", type);
                    progressDialog.setMessage("Realizando registro en linea...");
                    progressDialog.show();

                    db.collection("users")
                        .add(user)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                startActivity(new Intent(Registro.this, Home.class));
                                finish();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(Registro.this, "No se pudieron crear los datos correctamente", Toast.LENGTH_SHORT).show();
                            }
                        });
                } else {
                    Toast.makeText(Registro.this, "No se pudo registrar este usuario", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

}
