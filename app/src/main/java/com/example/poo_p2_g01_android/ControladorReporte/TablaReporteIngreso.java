package com.example.poo_p2_g01_android.ControladorReporte;

import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
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

import com.example.poo_p2_g01_android.IngresoActivity;
import com.example.poo_p2_g01_android.R;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import enums.TipoRepeticion;
import modelo.cuenta.CuentaFinanciera;
import modelo.cuenta.CuentaxCobrar;
import modelo.movimiento.Ingreso;
import modelo.movimiento.Movimiento;

/**
 * Actividad para mostrar un reporte de ingresos en una tabla.
 *
 * Esta clase maneja la visualización de ingresos en un formato tabular
 * y configura la vista de acuerdo con el tipo de reporte seleccionado.
 */
public class TablaReporteIngreso extends AppCompatActivity {

    /**
     * Lista de ingresos que se mostrarán en la tabla.
     */
    private List<Ingreso> lstIngreso;

    @Override
    /**
     * Método llamado al crear la actividad. Configura la vista y ajusta los márgenes
     * para tener en cuenta las barras del sistema.
     *
     * @param savedInstanceState Estado guardado de la actividad, si existe.
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tabla_reporte_ingreso);
        obtenerIntentExtra();
        retroceso();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.VistaReporteIngreso), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    /**
     * Configura el evento del botón de retroceso para finalizar la actividad.
     */
    private void retroceso() {
        ImageButton backButton = findViewById(R.id.btnAtrasReporteIngreso);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    /**
     * Obtiene el tipo de tabla desde el intent y muestra la tabla correspondiente.
     *
     * Tipos de tabla:
     * - "Mes actual": Muestra la tabla del mes actual.
     * - "Año": Muestra la tabla del año actual.
     */
    private void obtenerIntentExtra() {
        lstIngreso = cargarListaIngresos();
        String tipoTabla = getIntent().getStringExtra("tipoTabla");
        Log.e("Ingreso Activity", tipoTabla+"");
        if (tipoTabla != null) {
            switch (tipoTabla) {
                case "Mes actual":
                    mostrarTablaMesActual();
                    break;
                case "Año":
                    mostrarTablaAnioActual();
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * Muestra la tabla de ingresos del mes actual en la vista.
     * Carga los ingresos del mes actual, los organiza en una tabla y los muestra en la interfaz.
     */
    private void mostrarTablaMesActual() {
        List<Ingreso> lstIngresoMesActual = obtenerIngresoMesActual();
        Log.e("TablaReporteIngreso", lstIngresoMesActual.toString());

        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.tabla_mes_actual, null);

        FrameLayout frameLayout = findViewById(R.id.frameLayoutReporteIngreso);

        if(frameLayout != null) {
            frameLayout.removeAllViews();
        }
        TableLayout tableLayout = view.findViewById(R.id.tablaMesActual);
        IngresoActivity.cleanTable(tableLayout);

        View lineaDivisorEncabezado = new View(this);
        TableRow.LayoutParams paramsDivisorEncabezado = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, 3);
        paramsDivisorEncabezado.span = 2;
        lineaDivisorEncabezado.setLayoutParams(paramsDivisorEncabezado);
        lineaDivisorEncabezado.setBackgroundColor(getColor(R.color.md_theme_onBackground));

        TextView tvMesActual = findViewById(R.id.textViewMesActual);
        tvMesActual.setText("Mes actual: " + ReporteActivity.obtenerNombreMes(ReporteActivity.getMesActual()));
        tvMesActual.setTextColor(ContextCompat.getColor(this, R.color.md_theme_onBackground));
        tableLayout.addView(lineaDivisorEncabezado);

        for (Ingreso ingreso : lstIngresoMesActual) {
            TableRow tr = new TableRow(this);
            tr.setPadding(0, 20, 0, 20);

            TextView tvCategoria = new TextView(this);
            tvCategoria.setText(ingreso.getCategoria().getNombre());
            TableRow.LayoutParams paramsCategoria = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f);
            tvCategoria.setLayoutParams(paramsCategoria);
            tvCategoria.setGravity(Gravity.CENTER);
            tvCategoria.setTextColor(ContextCompat.getColor(this, R.color.md_theme_inverseSurface));

            TextView tvValor = new TextView(this);
            tvValor.setText(String.valueOf(ingreso.getValorNeto()));
            TableRow.LayoutParams paramsValor = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f);
            tvValor.setLayoutParams(paramsValor);
            tvValor.setGravity(Gravity.CENTER);
            tvValor.setTextColor(ContextCompat.getColor(this, R.color.md_theme_inverseSurface));


            tr.addView(tvCategoria);
            tr.addView(tvValor);

            tableLayout.addView(tr);
        }
        View lineaDivisorTotal = new View(this);
        TableRow.LayoutParams paramsDivisorTotal = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, 3);
        paramsDivisorTotal.span = 2;
        lineaDivisorTotal.setLayoutParams(paramsDivisorTotal);
        lineaDivisorTotal.setBackgroundColor(getColor(R.color.md_theme_onBackground));

        tableLayout.addView(lineaDivisorTotal);

        TableRow trCuotas = new TableRow(this);
        trCuotas.setPadding(0, 20, 0, 20);

        TextView tvCuotas = new TextView(this);
        tvCuotas.setText("Cuotas");
        TableRow.LayoutParams paramsCuotas = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f);
        tvCuotas.setLayoutParams(paramsCuotas);
        tvCuotas.setGravity(Gravity.CENTER);
        tvCuotas.setTextColor(ContextCompat.getColor(this, R.color.md_theme_onBackground));

        TextView tvTotalCuotas = new TextView(this);
        tvTotalCuotas.setText(String.valueOf(cuotasIngresosxMes()));
        TableRow.LayoutParams paramsValorCuotas = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f);
        tvTotalCuotas.setLayoutParams(paramsValorCuotas);
        tvTotalCuotas.setGravity(Gravity.CENTER);
        tvTotalCuotas.setTextColor(ContextCompat.getColor(this, R.color.md_theme_onBackground));

        trCuotas.addView(tvCuotas);
        trCuotas.addView(tvTotalCuotas);
        tableLayout.addView(trCuotas);


        TableRow trTotal = new TableRow(this);
        trTotal.setPadding(0, 20, 0, 20);

        TextView tvTotal = new TextView(this);
        tvTotal.setText("TOTAL");
        tvTotal.setTypeface(null, Typeface.BOLD);
        TableRow.LayoutParams paramsTotal = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f);
        tvTotal.setLayoutParams(paramsTotal);
        tvTotal.setGravity(Gravity.CENTER);
        tvTotal.setTextColor(ContextCompat.getColor(this, R.color.md_theme_onBackground));

        TextView tvTotalValor = new TextView(this);
        tvTotalValor.setText(String.valueOf(calcularTotalIngresoxMes()));
        tvTotalValor.setTypeface(null, Typeface.BOLD);
        TableRow.LayoutParams paramsValorTotal = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f);
        tvTotalValor.setLayoutParams(paramsValorTotal);
        tvTotalValor.setGravity(Gravity.CENTER);
        tvTotalValor.setTextColor(ContextCompat.getColor(this, R.color.md_theme_onBackground));

        trTotal.addView(tvTotal);
        trTotal.addView(tvTotalValor);
        tableLayout.addView(trTotal);


        if (frameLayout != null) {
            frameLayout.addView(view);
        }
    }


    /**
     * Ordena la lista de ingresos por fecha de inicio.
     *
     * @return Lista de ingresos ordenados por fecha de inicio.
     */
    private List<Ingreso> ordenarIngresos(List<Ingreso> lstIngreso) {
        List<Ingreso> lstIngresosOrdenados = new ArrayList<>(lstIngreso);

        // Ordenar la lista usando un Comparator
        Collections.sort(lstIngresosOrdenados, new Comparator<Ingreso>() {
            @Override
            public int compare(Ingreso ingreso1, Ingreso ingreso2) {
                return ingreso1.getFechaInicio().compareTo(ingreso2.getFechaInicio());
            }
        });
        return lstIngresosOrdenados;
    }

    /**
     * Obtiene los ingresos del mes actual.
     *
     * @return Lista de ingresos del mes actual.
     */
    public List<Ingreso> obtenerIngresoMesActual() {
        List<Ingreso> ingresosMesActual = new ArrayList<>();
        LocalDate fechaActual = LocalDate.now();
        Month mesActual = fechaActual.getMonth();
        int anioActual = fechaActual.getYear();
        LocalDate inicioMesActual = LocalDate.of(anioActual, mesActual, 1);
        LocalDate finMesActual = inicioMesActual.withDayOfMonth(inicioMesActual.lengthOfMonth());

        for (Ingreso ingreso : this.ordenarIngresos(lstIngreso)) {
            DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate fechaInicio = ingreso.getFechaInicio();
            LocalDate fechaFin = LocalDate.parse(ingreso.getFechaFin(), formato);

            if ((fechaInicio.isBefore(finMesActual) || fechaInicio.isEqual(finMesActual)) &&
                    (fechaFin.isAfter(inicioMesActual) || fechaFin.isEqual(inicioMesActual))) {
                ingresosMesActual.add(ingreso);
            }
        }

        return ingresosMesActual;
    }

    /**
     * Calcula el total de ingresos del mes actual.
     *
     * @return Total de ingresos del mes actual.
     */
    public double calcularTotalIngresoxMes(){
        double total = 0;
        for (Ingreso ingreso : this.obtenerIngresoMesActual()) {
            total += ingreso.getValorNeto();
        }
        total += cuotasIngresosxMes();
        return total;
    }

    /**
     * Calcula el total de cuotas de ingresos para el mes actual.
     *
     * @return Total de cuotas de ingresos del mes actual.
     */
    public double cuotasIngresosxMes() {
        LocalDate fechaActual = LocalDate.now();
        Month mesActual = fechaActual.getMonth();
        int añoActual = fechaActual.getYear();
        LocalDate inicioMesActual = LocalDate.of(añoActual, mesActual, 1);
        LocalDate finMesActual = inicioMesActual.withDayOfMonth(inicioMesActual.lengthOfMonth());

        List<CuentaxCobrar> cuentasxCobrar = cargarCuentasxCobrar();
        double cuotasIngresos = 0;

        for (CuentaxCobrar cuentaxCobrar : cuentasxCobrar) {
            LocalDate fechaInicio = cuentaxCobrar.getFechaInicio();
            LocalDate fechaFin = cuentaxCobrar.getFechaFin();

            if ((fechaInicio.isBefore(finMesActual) || fechaInicio.isEqual(finMesActual)) &&
                    (fechaFin.isAfter(inicioMesActual) || fechaFin.isEqual(inicioMesActual))) {
                cuotasIngresos += cuentaxCobrar.getCuota();
            }
        }

        return cuotasIngresos;
    }

    /**
     * Muestra la tabla de ingresos del año actual en la vista.
     * Organiza los ingresos por mes, los muestra en una tabla, y agrega los totales al final.
     */
    private void mostrarTablaAnioActual() {
        List<Ingreso> lstIngresoAnioActual = ordenarIngresos(procesarIngresosPorMeses(obtenerIngresoAnioActual()));
        Log.e("TablaReporteIngreso", lstIngresoAnioActual.toString());


        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.tabla_anio_actual, null);

        FrameLayout frameLayout = findViewById(R.id.frameLayoutReporteIngreso);

        if(frameLayout != null) {
            frameLayout.removeAllViews();
        }
        TableLayout tableLayout = view.findViewById(R.id.tablaAnioActual);
        IngresoActivity.cleanTable(tableLayout);

        View lineaDivisorEncabezado = new View(this);
        TableRow.LayoutParams paramsDivisorEncabezado = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, 3);
        paramsDivisorEncabezado.span = 3;
        lineaDivisorEncabezado.setLayoutParams(paramsDivisorEncabezado);
        lineaDivisorEncabezado.setBackgroundColor(getColor(R.color.md_theme_onBackground));

        TextView tvAnioActual = findViewById(R.id.textViewMesActual);
        tvAnioActual.setText("Año Actual: " + LocalDate.now().getYear());
        tvAnioActual.setTextColor(ContextCompat.getColor(this, R.color.md_theme_onBackground));
        tableLayout.addView(lineaDivisorEncabezado);

        List<String> meses = new ArrayList<>();
        for (Ingreso ingreso : lstIngresoAnioActual) {
                TableRow tr = new TableRow(this);
                tr.setPadding(0, 20, 0, 20);
                String mes = ReporteActivity.obtenerNombreMes(ReporteActivity.getMesxFecha(ingreso.getFechaInicio()));
                TextView tvMes = new TextView(this);
                if (!meses.contains(mes)) {
                    tvMes.setText(mes);
                    tvMes.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f));
                    tvMes.setGravity(Gravity.CENTER);
                    tvMes.setTextColor(ContextCompat.getColor(this, R.color.md_theme_inverseSurface));
                    meses.add(mes);

                } else {
                    tvMes.setText("");
                    tvMes.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f));
                    tvMes.setGravity(Gravity.CENTER);
                }

                TextView tvCategoria = new TextView(this);
                tvCategoria.setText(ingreso.getCategoria().getNombre());
                tvCategoria.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f));
                tvCategoria.setGravity(Gravity.CENTER);
                tvCategoria.setTextColor(ContextCompat.getColor(this, R.color.md_theme_inverseSurface));

                TextView tvValor = new TextView(this);
                tvValor.setText(String.valueOf(ingreso.getValorNeto()));
                tvValor.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f));
                tvValor.setGravity(Gravity.CENTER);
                tvValor.setTextColor(ContextCompat.getColor(this, R.color.md_theme_inverseSurface));

                tr.addView(tvMes);
                tr.addView(tvCategoria);
                tr.addView(tvValor);

                tableLayout.addView(tr);

        }
        View lineaDivisorTotal = new View(this);
        TableRow.LayoutParams paramsDivisorTotal = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, 3);
        paramsDivisorTotal.span = 3;
        lineaDivisorTotal.setLayoutParams(paramsDivisorTotal);
        lineaDivisorTotal.setBackgroundColor(getColor(R.color.md_theme_onBackground));

        tableLayout.addView(lineaDivisorTotal);

        TableRow trCuotas = new TableRow(this);
        trCuotas.setPadding(0, 20, 0, 20);

        TextView tvCuotas = new TextView(this);
        tvCuotas.setText("Cuotas");
        TableRow.LayoutParams paramsCuotas = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f);
        tvCuotas.setLayoutParams(paramsCuotas);
        tvCuotas.setGravity(Gravity.CENTER);
        tvCuotas.setTextColor(ContextCompat.getColor(this, R.color.md_theme_onBackground));

        TextView tvVacio = new TextView(this);
        tvVacio.setText("");
        TableRow.LayoutParams paramsVacio = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f);
        tvVacio.setLayoutParams(paramsVacio);
        tvVacio.setGravity(Gravity.CENTER);

        TextView tvTotalCuotas = new TextView(this);
        tvTotalCuotas.setText(String.valueOf(cuotasIngresosxAnio()));
        TableRow.LayoutParams paramsValorCuotas = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f);
        tvTotalCuotas.setLayoutParams(paramsValorCuotas);
        tvTotalCuotas.setGravity(Gravity.CENTER);
        tvTotalCuotas.setTextColor(ContextCompat.getColor(this, R.color.md_theme_onBackground));

        trCuotas.addView(tvCuotas);
        trCuotas.addView(tvVacio);
        trCuotas.addView(tvTotalCuotas);
        tableLayout.addView(trCuotas);


        TableRow trTotal = new TableRow(this);
        trTotal.setPadding(0, 20, 0, 20);

        TextView tvTotal = new TextView(this);
        tvTotal.setText("TOTAL");
        tvTotal.setTypeface(null, Typeface.BOLD);
        TableRow.LayoutParams paramsTotal = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f);
        tvTotal.setLayoutParams(paramsTotal);
        tvTotal.setGravity(Gravity.CENTER);
        tvTotal.setTextColor(ContextCompat.getColor(this, R.color.md_theme_onBackground));

        TextView tvVacio1 = new TextView(this);
        tvVacio1.setText("");
        TableRow.LayoutParams paramsVacio1 = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f);
        tvVacio1.setLayoutParams(paramsVacio1);
        tvVacio1.setGravity(Gravity.CENTER);

        TextView tvTotalValor = new TextView(this);
        tvTotalValor.setText(String.valueOf(calcularTotalIngresoxAnio()));
        tvTotalValor.setTypeface(null, Typeface.BOLD);
        TableRow.LayoutParams paramsValorTotal = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f);
        tvTotalValor.setLayoutParams(paramsValorTotal);
        tvTotalValor.setGravity(Gravity.CENTER);
        tvTotalValor.setTextColor(ContextCompat.getColor(this, R.color.md_theme_onBackground));

        trTotal.addView(tvTotal);
        trTotal.addView(tvVacio1);
        trTotal.addView(tvTotalValor);
        tableLayout.addView(trTotal);

        if (frameLayout != null) {
            frameLayout.addView(view);
        }
    }

    public List<Ingreso> procesarIngresosPorMeses(List<Ingreso> ingresos) {
        List<Ingreso> lstIngresosExtendidos = new ArrayList<>();
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate fechaActual = LocalDate.now();

        for (Ingreso ingreso : ingresos) {
            LocalDate fechaInicio = ingreso.getFechaInicio();
            LocalDate fechaFin;

            if (ingreso.getTipoRepeticion() == TipoRepeticion.MES) {
                if (ingreso.getFechaFin().equals("No definida")) {
                    lstIngresosExtendidos.add(new Ingreso(
                            ingreso.getCategoria(),
                            ingreso.getValorNeto(),
                            ingreso.getDescripcion(),
                            fechaInicio,
                            null,
                            ingreso.getTipoRepeticion()
                    ));
                } else {
                    fechaFin = LocalDate.parse(ingreso.getFechaFin(), formato);

                    if (fechaInicio.getYear() < fechaActual.getYear()) {
                        fechaInicio = LocalDate.of(fechaActual.getYear(), 1, 1);
                    }

                    if (fechaFin.getYear() > fechaActual.getYear()) {
                        fechaFin = LocalDate.of(fechaActual.getYear(), 12, 31);
                    }

                    LocalDate fechaIterador = fechaInicio.withDayOfMonth(1);
                    while (!fechaIterador.isAfter(fechaActual)) {
                        if (!fechaIterador.isAfter(fechaFin)) {
                            lstIngresosExtendidos.add(new Ingreso(
                                    ingreso.getCategoria(),
                                    ingreso.getValorNeto(),
                                    ingreso.getDescripcion(),
                                    fechaIterador,
                                    fechaFin,
                                    ingreso.getTipoRepeticion()
                            ));
                        }
                        fechaIterador = fechaIterador.plusMonths(1);
                    }
                }
            } else {
                lstIngresosExtendidos.add(ingreso);
            }
        }

        return lstIngresosExtendidos;
    }

    /**
     * Calcula el total de ingresos del año actual.
     *
     * @return Total de ingresos del año actual.
     */
    public double calcularTotalIngresoxAnio(){
        double total = 0;
        for (Ingreso ingreso : this.procesarIngresosPorMeses(obtenerIngresoAnioActual())) {
            total += ingreso.getValorNeto();
        }
        total += cuotasIngresosxAnio();
        return total;
    }

    /**
     * Obtiene los ingresos del año actual, considerando la fecha de inicio y la fecha de fin.
     *
     * @return Lista de ingresos del año actual.
     */
    public List<Ingreso> obtenerIngresoAnioActual() {
        List<Ingreso> ingresosAñoActual = new ArrayList<>();
        LocalDate fechaActual = LocalDate.now();
        int añoActual = fechaActual.getYear();

        for (Ingreso ingreso : this.ordenarIngresos(lstIngreso)) {
            LocalDate fechaInicio = ingreso.getFechaInicio();
            LocalDate fechaFin;
            if (ingreso.getFechaFin().equals("No definida")) {
                fechaFin = fechaActual;
            } else {
                fechaFin = LocalDate.parse(ingreso.getFechaFin(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            }

            if ((fechaInicio.getYear() == añoActual || fechaFin.getYear() == añoActual) &&
                    (fechaInicio.getMonthValue() <= fechaActual.getMonthValue() || fechaFin.getMonthValue() >= fechaActual.getMonthValue())) {
                ingresosAñoActual.add(ingreso);
            }
        }
        return ingresosAñoActual;
    }

    /**
     * Calcula el total de cuotas de ingresos acumuladas hasta el mes actual del año actual,
     * considerando la fecha de inicio y la fecha de fin de cada cuenta.
     *
     * @return Total de cuotas de ingresos acumuladas hasta el mes actual del año actual.
     */
    public double cuotasIngresosxAnio() {
        LocalDate fechaActual = LocalDate.now();
        int añoActual = fechaActual.getYear();
        Month mesActual = fechaActual.getMonth();
        double cuotas = 0;

        for (CuentaxCobrar cuenta : cargarCuentasxCobrar()) {
            LocalDate fechaInicio = cuenta.getFechaInicio();
            LocalDate fechaFin;
            if (cuenta.getFechaFin().equals("No definida")) {
                fechaFin = fechaActual;
            } else {
                fechaFin = cuenta.getFechaFin();
            }

            if ((fechaInicio.getYear() == añoActual || fechaFin.getYear() == añoActual) &&
                    (fechaInicio.getMonthValue() <= mesActual.getValue() || fechaFin.getMonthValue() >= mesActual.getValue())) {
                cuotas += cuenta.getCuota();
            }
        }
        return cuotas;
    }

    /**
     * Carga una lista de objetos {@link Ingreso} desde un archivo de movimientos almacenado en el directorio de descargas externo.
     *
     * @return Una lista de objetos {@link Ingreso} cargados desde el archivo.
     */
    private List<Ingreso> cargarListaIngresos() {
        List<Ingreso> listaIngreso = new ArrayList<>();
        try {
            List<Movimiento> listaMovimiento = Movimiento.cargarMovimientos(this.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS));
            Log.d("TablaReporteIngreso", listaMovimiento.toString());
            for(Movimiento movimiento: listaMovimiento) {
                if(movimiento instanceof Ingreso) {
                    Ingreso ingreso = (Ingreso) movimiento;
                    listaIngreso.add(ingreso);
                }
            }
        } catch(Exception e){
            e.printStackTrace();
        }
        return listaIngreso;
    }

    /**
     * Carga una lista de objetos {@link CuentaxCobrar} desde un archivo almacenado en el directorio de descargas externo.
     *
     * @return Una lista de objetos {@link CuentaxCobrar} cargados desde el archivo.
     */
    private List<CuentaxCobrar> cargarCuentasxCobrar(){
        ArrayList<CuentaxCobrar> listaCuenta = new ArrayList<>();
        try {
            listaCuenta = CuentaFinanciera.cargarCuentasxCobrar(this.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS));
        } catch(Exception e){
            Log.e("TablaReporteIngreso", "Error al cargar los datos de cuentaxcobrar "+ e.getMessage());
        }
        return listaCuenta;
    }

}
