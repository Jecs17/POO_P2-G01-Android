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
import com.example.poo_p2_g01_android.ControladorReporte.ReporteActivity;

import java.io.File;
import java.util.List;

import modelo.banco.Banco;
import modelo.movimiento.Categoria;
import modelo.movimiento.Movimiento;
import modelo.cuenta.CuentaFinanciera;
import modelo.persona.Persona;

/**
 * <h3>Activity Principal de la Aplicación</h3>
 * <p>Esta actividad es la pantalla principal de la aplicación y gestiona la navegación entre diferentes secciones.</p>
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * Método llamado cuando la actividad es creada.
     *
     * @param savedInstanceState el estado guardado de la instancia, si está disponible.
     */
     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        inicializarCardViews();
        cargarDatos(this);
        cargarMovimientosIniciales(this);
        codigoUnicoActualizado(this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    /**
     * Inicializa los CardViews y asigna el listener de clic a cada uno.
     */
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

    /**
     * Maneja los clics en los CardViews de la actividad.
     *
     * @param view la vista que fue clickeada.
     *
     * <p>Este método determina qué CardView fue clickeado y abre la actividad correspondiente basada en el ID de la vista.</p>
     */
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
            intent = new Intent(this, CuentaxCobrarActivity.class);
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

    /**
     * Carga los datos iniciales de las categorías, personas, bancos y cuentas financieras.
     *
     * @param context el contexto de la aplicación {@link Context}.
     */
    public static void cargarDatos(Context context) {
        boolean guardadoCategoria = false;
        boolean guardadoPersona = false;
        boolean guardadoBanco = false;
        boolean guardadoCuentaFinanciera = false;

        File file = context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);

        try {
            guardadoCategoria = Categoria.crearDatosIniciales(file);
            guardadoPersona = Persona.crearDatosIniciales(file);
            guardadoBanco = Banco.crearDatosIniciales(file);
            guardadoCuentaFinanciera = CuentaFinanciera.crearDatosIniciales(file, context);

        } catch (Exception e) {
            guardadoCategoria = false;
            guardadoPersona = false;
            guardadoBanco = false;
            guardadoCuentaFinanciera = false;
            Log.d("Datos", "Error al cargar los datos iniciales" + e.getMessage());
        }

        if(guardadoCategoria){
            Log.d("Datos","Datos iniciales de Categoria - guardados");
        }
        if(guardadoPersona && guardadoBanco){
            Log.d("Datos","Datos iniciales de Persona y Banco - guardados");
        }
        if(guardadoCuentaFinanciera){
            Log.d("Datos","Datos iniciales de Cuenta Financiera - guardados");
        }

    }

    /**
     * Carga los movimientos iniciales desde el archivo de datos.
     *
     * @param context el contexto de la aplicación {@link Context}.
     */
    private static void cargarMovimientosIniciales(Context context) {
        boolean guardado;
        try {
            guardado = Movimiento.crearDatosIniciales(context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), context);
        } catch (Exception e) {
            guardado = false;
            Log.d("Main Activity", "Error al cargar los datos iniciales de movimiento" + e.getMessage());
        }
        if(guardado){
            Log.d("Main Activity", "Datos iniciales de movimientos agregados exitosamente");
        }
    }

    /**
     * Actualiza el código único para los movimientos cargados.
     *
     * @param context el contexto de la aplicación {@link Context}.
     */
    private static void codigoUnicoActualizado(Context context) {
        try {
            List<Movimiento> lstMovimiento = Movimiento.cargarMovimientos(context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS));
            int codigoMayor = 0;
            for(Movimiento movimiento: lstMovimiento) {
                if(movimiento.getCodigoUnico() > codigoMayor) {
                    codigoMayor = movimiento.getCodigoUnico();
                }
            }
            Movimiento.actualizarCodigo(codigoMayor + 1);
        } catch (Exception e) {
            Log.e("Main Activity", "Error al cargar los movimientos en el main");
        }
    }
}
