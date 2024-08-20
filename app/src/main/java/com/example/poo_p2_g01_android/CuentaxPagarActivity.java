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

import modelo.banco.Banco;
import modelo.cuenta.CuentaFinanciera;
import modelo.cuenta.CuentaxCobrar;
import modelo.cuenta.CuentaxPagar;
import modelo.persona.Persona;

public class CuentaxPagarActivity extends AppCompatActivity {

    private ImageButton btnRegresar;
    private Button btnRegistrarCuenta;
    private TableLayout tablaCuentaxPagar;
    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cuentax_pagar);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.vistaCuentaPagar), (v, insets) -> {
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
        btnRegresar = findViewById(R.id.btnRegresarCuentaPagar);
        btnRegresar.setOnClickListener(v -> {
            finish();
        });
    }

    private void registrarCuenta(){
        btnRegistrarCuenta = findViewById(R.id.btnRegistrarCuentaPagar);
        btnRegistrarCuenta.setOnClickListener((v) -> {
            Intent intent = new Intent(CuentaxPagarActivity.this, RegistrarCuentasFinancieras.class);
            RegistrarCuentasFinancieras.esAcreedor = true;
            startActivity(intent);
        });
    }

    private static ArrayList<CuentaxPagar> cargarListaCuenta(Context context){
        ArrayList<CuentaxPagar> listaCuenta = new ArrayList<>();
        try {
            listaCuenta = CuentaFinanciera.cargarCuentasxPagar(context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS));
        } catch(Exception e){
            Log.e("CuentaFinanciera", "Error al cargar los datos de cuentaxpagar "+ e.getMessage());
        }
        return listaCuenta;
    }

    private void llenarTabla(){
        ArrayList<CuentaxPagar> listaCuentaPagar = cargarListaCuenta(context);

        tablaCuentaxPagar = findViewById(R.id.tablaCuentaPagar);
        Log.d("CuentaFinanciera", "Listado para la tabla: " + listaCuentaPagar.toString());
        cleanTable(tablaCuentaxPagar);

        for (CuentaxPagar cuenta: listaCuentaPagar) {

            TableRow tr = new TableRow(this);

            TextView tvCodigo = new TextView(this);
            tvCodigo.setTextColor(ContextCompat.getColor(this, R.color.md_theme_background));
            tvCodigo.setPadding(8, 10, 8, 10);
            tvCodigo.setText(String.valueOf(cuenta.getCodigo()));

            TextView acreedor = new TextView(this);
            acreedor.setTextColor(ContextCompat.getColor(this, R.color.md_theme_background));
            acreedor.setPadding(8, 10, 8, 10);
            acreedor.setText(nombreAcreedor(cuenta));

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
            tr.addView(acreedor);
            tr.addView(valor);
            tr.addView(descripcion);
            tr.addView(fechaPrestamo);
            tr.addView(cuota);
            tr.setPadding(0, 15, 0, 0);
            //agregar al Tableview
            tablaCuentaxPagar.addView(tr);
        }
    }

    public static String nombreAcreedor(CuentaxPagar cuenta){
        Persona persona = cuenta.getAcreedor();
        Banco banco = cuenta.getBanco();
        String nombre = "";
        if(persona != null){
            nombre = persona.getNombre();
        }else if( banco != null){
            nombre = banco.getEntidadBancaria();
        }
        return nombre;
    }

    private void cleanTable(TableLayout table) {

        int childCount = table.getChildCount();

        // Remove all rows except the first one
        if (childCount > 1) {
            table.removeViews(1, childCount - 1);
        }
    }

    public static ArrayList<CuentaxPagar> buscarCuentasAsociada(String identificacion, Context context){
        Persona persona = PersonaBancoActivity.buscarPersona(identificacion, context);
        Banco banco = PersonaBancoActivity.buscarBanco(identificacion, context);
        ArrayList<CuentaxPagar> listaCuentaAsociadas= new ArrayList<>();
        for(CuentaxPagar cuenta: cargarListaCuenta(context)){
            if(persona!= null && persona.equals(cuenta.getAcreedor())){
                listaCuentaAsociadas.add(cuenta);
            }
            if(banco != null && banco.equals(cuenta.getBanco())){
                listaCuentaAsociadas.add(cuenta);
            }
        }
        return listaCuentaAsociadas;
    }
}