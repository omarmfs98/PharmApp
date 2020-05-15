package com.example.pharmapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class PerfilActivity extends AppCompatActivity {
    private TextView infoUser;
    private Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        infoUser = (TextView) findViewById(R.id.infoUser);
        if (user != null) {
            infoUser.setText("Correo: " + user.getEmail());
        }
        logout = (Button)findViewById(R.id.logoutBtn);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View vista) { Intent intent = new Intent(PerfilActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("EXIT", true);
            startActivity(intent);
            FirebaseAuth.getInstance().signOut();
        } });
    }
}
