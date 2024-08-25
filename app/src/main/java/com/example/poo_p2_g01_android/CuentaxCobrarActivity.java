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

/**
 * Actividad para gestionar las cuentas por cobrar.
 *
 * Permite registrar nuevas cuentas por cobrar, mostrar una tabla con las cuentas existentes
 * y regresar a la actividad anterior. También incluye métodos para cargar y buscar cuentas
 * asociadas a una persona.
 *
 * @author Grupo1
 */
public class CuentaxCobrarActivity extends AppCompatActivity {

    private final Context context = this;

    /**
     * Método llamado al crear la actividad.
     *
     * Inicializa la vista de la actividad, configura los márgenes de la vista para que no se superponga
     * con las barras del sistema y establece los oyentes de eventos para los botones de registro y regreso.
     *
     * @param savedInstanceState El estado guardado de la actividad, si existe.
     */
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

    /**
     * Método llamado cuando la actividad se reanuda.
     *
     * Actualiza la tabla con la lista más reciente de cuentas por cobrar.
     */
    @Override
    protected void onResume() {
        super.onResume();
        llenarTabla();
    }

    /**
     * Configura el botón para regresar a la actividad anterior.
     */
    private void regresar(){
        ImageButton btnRegresar = findViewById(R.id.btnRegresarCuentaCobrar);
        btnRegresar.setOnClickListener(v -> finish());
    }

    /**
     * Configura el botón para registrar una nueva cuenta por cobrar.
     */
    private void registrarCuenta(){
        Button btnRegistrarCuenta = findViewById(R.id.btnRegistrarCuentaCobrar);
        btnRegistrarCuenta.setOnClickListener((v) -> {
            Intent intent = new Intent(CuentaxCobrarActivity.this, RegistrarCuentasFinancieras.class);
            RegistrarCuentasFinancieras.esAcreedor = false;
            startActivity(intent);
        });
    }

    /**
     * Carga la lista de cuentas por cobrar desde el almacenamiento externo.
     *
     * @param context El contexto de la aplicación.
     * @return Lista de cuentas por cobrar.
     */
    private static ArrayList<CuentaxCobrar> cargarListaCuenta(Context context){
        ArrayList<CuentaxCobrar> listaCuenta = new ArrayList<>();
        try {
            listaCuenta = CuentaFinanciera.cargarCuentasxCobrar(context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS));
        } catch(Exception e){
            Log.e("CuentaFinanciera", "Error al cargar los datos de cuentaxcobrar "+ e.getMessage());
        }
        return listaCuenta;
    }

    /**
     * Llena la tabla con la lista de cuentas por cobrar.
     */
    private void llenarTabla(){
        ArrayList<CuentaxCobrar> listaCuentaCobrar = cargarListaCuenta(context);

        TableLayout tablaCuentaxCobrar = findViewById(R.id.tablaCuentaCobrar);
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

    /**
     * Limpia todas las filas de una tabla, excepto la primera.
     *
     * @param table La tabla a limpiar.
     */
    private void cleanTable(TableLayout table) {
        int childCount = table.getChildCount();

        // Remove all rows except the first one
        if (childCount > 1) {
            table.removeViews(1, childCount - 1);
        }
    }

    /**
     * Busca cuentas por cobrar asociadas a una persona específica.
     *
     * @param identificacion La identificación de la persona.
     * @param context El contexto de la aplicación.
     * @return Lista de cuentas por cobrar asociadas a la persona.
     */
    public static ArrayList<CuentaxCobrar> buscarCuentasAsociada(String identificacion, Context context){
        Persona persona = PersonaBancoActivity.buscarPersona(identificacion, context);
        ArrayList<CuentaxCobrar> listaCuentaAsociadas= new ArrayList<>();
        for(CuentaxCobrar cuenta: cargarListaCuenta(context)){
            if(persona != null && persona.equals(cuenta.getDeudor())){
                listaCuentaAsociadas.add(cuenta);
            }
        }
        return listaCuentaAsociadas;
    }

}