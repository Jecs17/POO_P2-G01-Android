package com.example.poo_p2_g01_android;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.poo_p2_g01_android.ControladorCategoria.CategoryActivity;

import modelo.banco.Banco;
import modelo.persona.Persona;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        inicializarCardViews();
        cargarDatos(this);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void inicializarCardViews() {
        CardView[] cardViews = {
                findViewById(R.id.cvCategoria),
                findViewById(R.id.cvIngresos),
                findViewById(R.id.cvGastos),
                findViewById(R.id.cvCuentaxCobrar),
                findViewById(R.id.cvCuentaxPagar),
                findViewById(R.id.cvCuentaBancaria),
                findViewById(R.id.cvInversiones),
                findViewById(R.id.cvPersonaBanco),
                findViewById(R.id.cvReportes),
                findViewById(R.id.cvProyeccionGastos)
        };

        for (CardView cardView : cardViews) {
            cardView.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View view) {
        Intent intent = null;
        int id = view.getId();
        if (id == R.id.cvCategoria) {
            intent = new Intent(this, CategoryActivity.class);
        } else if (id == R.id.cvIngresos) {
            intent = new Intent(this, IngresoActivity.class);
        } else if (id == R.id.cvGastos) {
            intent = new Intent(this, GastoActivity.class);
        } else if (id == R.id.cvCuentaxCobrar) {
            intent = new Intent(this, RegistrarCuentasFinancieras.class);
        } else if (id == R.id.cvCuentaxPagar) {
            intent = new Intent(this, CuentaxPagarActivity.class);
        } else if (id == R.id.cvCuentaBancaria) {
            intent = new Intent(this, CuentaBancariaActivity.class);
        } else if (id == R.id.cvInversiones) {
            intent = new Intent(this, InversionActivity.class);
        } else if (id == R.id.cvPersonaBanco) {
            intent = new Intent(this, PersonaBancoActivity.class);
        } else if (id == R.id.cvReportes) {
            intent = new Intent(this, ReporteActivity.class);
        } else if (id == R.id.cvProyeccionGastos) {
            intent = new Intent(this, ProyeccionGastoActivity.class);
        }

        if (intent != null) {
            startActivity(intent);
        }
    }

    public static void cargarDatos(Context context){
        boolean guardadoPersona = false;
        boolean guardadoBanco = false;

        try {
            guardadoPersona = Persona.crearDatosIniciales(context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS));
            guardadoBanco = Banco.crearDatosIniciales(context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS));

        } catch(Exception e){
            guardadoPersona = false;
            guardadoBanco = false;
            Log.d("ActivityPersonaBanco", "Error al cargar los datos iniciales"+ e.getMessage());
        }

        if(guardadoPersona && guardadoBanco){
            Log.d("ActivityPersonaBanco","Datos iniciales guardados");
        }
    }
}
