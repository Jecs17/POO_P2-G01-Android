package com.example.poo_p2_g01_android;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

import modelo.cuenta.CuentaFinanciera;
import modelo.cuenta.CuentaxCobrar;
import modelo.persona.Persona;

public class CuentaxCobrarActivity extends AppCompatActivity {

    private Button btnRegistrarCuenta;
    private ImageButton btnRegresar;
    private TableLayout tablaCuentaxCobrar;
    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cuentax_cobrar);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.vistaCuentaCobrar), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        registrarCuenta();
        regresar();
    }

    @Override
    protected void onResume() {
        super.onResume();
        llenarTabla();
    }

    private void regresar(){
        btnRegresar = findViewById(R.id.btnRegresarCuentaCobrar);
        btnRegresar.setOnClickListener(v -> {
            finish();
        });
    }

    private void registrarCuenta(){
        btnRegistrarCuenta = findViewById(R.id.btnRegistrarCuentaCobrar);
        btnRegistrarCuenta.setOnClickListener((v) -> {
            Intent intent = new Intent(CuentaxCobrarActivity.this, RegistrarCuentasFinancieras.class);
            RegistrarCuentasFinancieras.esAcreedor = false;
            startActivity(intent);
        });
    }

    private ArrayList<CuentaxCobrar> cargarListaCuenta(Context context){
        ArrayList<CuentaxCobrar> listaCuenta = new ArrayList<>();
        try {
            listaCuenta = CuentaFinanciera.cargarCuentasxCobrar(context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS));
        } catch(Exception e){
            Log.e("CuentaFinanciera", "Error al cargar los datos de cuentaxcobrar "+ e.getMessage());
        }
        return listaCuenta;
    }

    private void llenarTabla(){
        ArrayList<CuentaxCobrar> listaCuentaCobrar= cargarListaCuenta(context);

        tablaCuentaxCobrar = findViewById(R.id.tablaCuentaCobrar);
        Log.d("CuentaFinanciera", "Listado para la tabla: " + listaCuentaCobrar.toString());
        cleanTable(tablaCuentaxCobrar);

        for (CuentaxCobrar cuenta: listaCuentaCobrar) {

            TableRow tr = new TableRow(this);

            TextView tvCodigo = new TextView(this);
            tvCodigo.setTextColor(ContextCompat.getColor(this, R.color.md_theme_background));
            tvCodigo.setPadding(8, 10, 8, 10);
            tvCodigo.setText(String.valueOf(cuenta.getCodigo()));

            TextView deudor = new TextView(this);
            deudor.setTextColor(ContextCompat.getColor(this, R.color.md_theme_background));
            deudor.setPadding(8, 10, 8, 10);
            deudor.setText(cuenta.getDeudor().getNombre());

            TextView valor = new TextView(this);
            valor.setTextColor(ContextCompat.getColor(this, R.color.md_theme_background));
            valor.setPadding(8, 10, 8, 10);
            valor.setText(String.valueOf(cuenta.getValor()));

            TextView descripcion = new TextView(this);
            descripcion.setTextColor(ContextCompat.getColor(this, R.color.md_theme_background));
            descripcion.setPadding(8, 10, 8, 10);
            descripcion.setText(cuenta.getDescripcion());

            TextView fechaPrestamo = new TextView(this);
            fechaPrestamo.setTextColor(ContextCompat.getColor(this, R.color.md_theme_background));
            fechaPrestamo.setPadding(8, 10, 10, 10);
            fechaPrestamo.setText(cuenta.getFechaPrestamo().toString());

            TextView cuota = new TextView(this);
            cuota.setTextColor(ContextCompat.getColor(this, R.color.md_theme_background));
            cuota.setPadding(8, 10, 8, 10);
            cuota.setText(String.valueOf(cuenta.getCuota()));

            //agregar al TableRow
            tr.addView(tvCodigo);
            tr.addView(deudor);
            tr.addView(valor);
            tr.addView(descripcion);
            tr.addView(fechaPrestamo);
            tr.addView(cuota);
            tr.setPadding(0, 15, 0, 0);
            //agregar al Tableview
            tablaCuentaxCobrar.addView(tr);
        }
    }

    private void cleanTable(TableLayout table) {

        int childCount = table.getChildCount();

        // Remove all rows except the first one
        if (childCount > 1) {
            table.removeViews(1, childCount - 1);
        }
    }


}