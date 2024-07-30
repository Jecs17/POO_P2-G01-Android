package com.example.poo_p2_g01_android;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public CardView cardViewCategoria, cardViewIngreso, cardViewGasto, cardViewCxCobrar, cardViewCxPagar, cardViewCBancaria, cardViewInversion, cardViewPersonaBanco, cardViewReporte, cardViewProyeccionGasto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        cardViewCategoria = findViewById(R.id.cvCategoria);
        cardViewIngreso = findViewById(R.id.cvIngresos);
        cardViewGasto = findViewById(R.id.cvGastos);
        cardViewCxCobrar = findViewById(R.id.cvCuentaxCobrar);
        cardViewCxPagar = findViewById(R.id.cvCuentaxPagar);
        cardViewCBancaria = findViewById(R.id.cvCuentaBancaria);
        cardViewInversion = findViewById(R.id.cvInversiones);
        cardViewPersonaBanco = findViewById(R.id.cvPersonaBanco);
        cardViewReporte = findViewById(R.id.cvReportes);
        cardViewProyeccionGasto = findViewById(R.id.cvProyeccionGastos);

        cardViewCategoria.setOnClickListener(this);
        cardViewIngreso.setOnClickListener(this);
        cardViewGasto.setOnClickListener(this);
        cardViewCxCobrar.setOnClickListener(this);
        cardViewCxPagar.setOnClickListener(this);
        cardViewCBancaria.setOnClickListener(this);
        cardViewInversion.setOnClickListener(this);
        cardViewPersonaBanco.setOnClickListener(this);
        cardViewReporte.setOnClickListener(this);
        cardViewProyeccionGasto.setOnClickListener(this);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    public void onClick(View view) {
        Intent i;
        int vistaId = view.getId();
        if(vistaId == R.id.cvCategoria){
            i = new Intent(this, RegistrarPersona.class);
        }else if(vistaId == R.id.cvIngresos) {
            i = new Intent(this, IngresoActivity.class);
        }else if(vistaId == R.id.cvGastos){
            i = new Intent(this, GastoActivity.class);
        }else if(vistaId == R.id.cvCuentaxCobrar){
            i = new Intent(this, CuentaxCobrarActivity.class);
        }else if(vistaId == R.id.cvCuentaxPagar){
            i = new Intent(this, CuentaxPagarActivity.class);
        }else if(vistaId == R.id.cvCuentaBancaria){
            i = new Intent(this, CuentaBancariaActivity.class);
        }else if(vistaId == R.id.cvInversiones){
            i = new Intent(this, InversionActivity.class);
        }else if(vistaId == R.id.cvPersonaBanco){
            i = new Intent(this, PersonaBancoActivity.class);
        }else if(vistaId == R.id.cvReportes){
            i = new Intent(this, ReporteActivity.class);
        }else if(vistaId == R.id.cvProyeccionGastos){
            i = new Intent(this, ProyeccionGastoActivity.class);
        }else{
            return;
        }
        startActivity(i);
    }
}
