package com.example.pharmapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

public class Home extends AppCompatActivity implements View.OnClickListener {
private CardView perfilCard,explorarCard,ayudaCard,informacionCard,contactosCard,ajustesCard,SignOutCard;

private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mAuth = FirebaseAuth.getInstance();

        perfilCard = (CardView) findViewById(R.id.perfil_card);
        explorarCard = (CardView) findViewById(R.id.explorar_card);
        ayudaCard = (CardView) findViewById(R.id.ayuda_card);
        informacionCard = (CardView) findViewById(R.id.informacion_card);
        contactosCard = (CardView) findViewById(R.id.contactos_card);
        ajustesCard = (CardView) findViewById(R.id.ajustes_card);
        SignOutCard = (CardView) findViewById(R.id.cerrar_card);

        perfilCard.setOnClickListener(this);
        explorarCard.setOnClickListener(this);
        ayudaCard.setOnClickListener(this);
        informacionCard.setOnClickListener(this);
        contactosCard.setOnClickListener(this);
        ajustesCard.setOnClickListener(this);

        SignOutCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               mAuth.signOut();
               startActivity(new Intent(Home.this, MainActivity.class));
               finish();
            }
        });
    }
    @Override
    public void onClick(View v) {
     Intent i;
         switch (v.getId()){
             case R.id.perfil_card : i = new Intent(this,PerfilActivity.class);startActivity(i);break;
             case R.id.explorar_card : i = new Intent(this,ExplorarActivity.class);startActivity(i);break;
             case R.id.ayuda_card : i = new Intent(this,AyudaActivity.class);startActivity(i);break;
             case R.id.informacion_card : i = new Intent(this,InformacionAtivity.class);startActivity(i);break;
             case R.id.contactos_card : i = new Intent(this,ContactosActivity.class);startActivity(i);break;
             case R.id.ajustes_card : i = new Intent(this,AjustesActivity.class);startActivity(i);break;
             default:break;
         }
    }
}
