package com.example.poo_p2_g01_android;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class PersonaBancoActivity extends AppCompatActivity {

    private Button btnPersona, btnBanco;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_persona_banco);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.vistaPersonaBanco), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        registrarPersona();
        registrarBanco();
    }

    public void registrarPersona(){
        btnPersona = findViewById(R.id.btnRegistrarPersona);
        btnPersona.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PersonaBancoActivity.this, RegistrarPersonaBanco.class);
                RegistrarPersonaBanco.esPersona = true;
                startActivity(i);
            }
        });
    }

    public void registrarBanco(){
        btnPersona = findViewById(R.id.btnRegistrarBanco);
        btnPersona.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PersonaBancoActivity.this, RegistrarPersonaBanco.class);
                RegistrarPersonaBanco.esPersona = false;
                startActivity(i);
            }
        });
    }

}