package com.example.poo_p2_g01_android.ControladorReporte;

import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
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
import modelo.cuenta.CuentaxPagar;
import modelo.movimiento.Gasto;
import modelo.movimiento.Ingreso;
import modelo.movimiento.Movimiento;

public class TablaReporteGasto extends AppCompatActivity {


    private List<Gasto> lstGasto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tabla_reporte_gasto);
        obtenerIntentExtra();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.VistaReporteGasto), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


    }

    private void obtenerIntentExtra() {
        lstGasto = cargarListaGastos();
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

    private void mostrarTablaMesActual() {
        List<Gasto> lstGastoMesActual = obtenerGastoMesActual();
        Log.e("TablaReporteIngreso", lstGastoMesActual.toString());

        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.tabla_mes_actual, null);

        FrameLayout frameLayout = findViewById(R.id.frameLayoutReporteGasto);

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

        TextView tvMesActual = findViewById(R.id.textViewMesActualGasto);
        tvMesActual.setText("Mes actual: " + ReporteActivity.obtenerNombreMes(ReporteActivity.getMesActual()));
        tvMesActual.setTextColor(ContextCompat.getColor(this, R.color.md_theme_onBackground));
        tableLayout.addView(lineaDivisorEncabezado);

        for (Gasto gasto : lstGastoMesActual) {
            TableRow tr = new TableRow(this);
            tr.setPadding(0, 20, 0, 20);

            TextView tvCategoria = new TextView(this);
            tvCategoria.setText(gasto.getCategoria().getNombre());
            TableRow.LayoutParams paramsCategoria = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f);
            tvCategoria.setLayoutParams(paramsCategoria);
            tvCategoria.setGravity(Gravity.CENTER);
            tvCategoria.setTextColor(ContextCompat.getColor(this, R.color.md_theme_inverseSurface));

            TextView tvValor = new TextView(this);
            tvValor.setText(String.valueOf(gasto.getValorNeto()));
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
        tvTotalCuotas.setText(String.valueOf(cuotasGastosxMes()));
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
        tvTotalValor.setText(String.valueOf(calcularTotalGastoxMes()));
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
     * Ordena la lista de gastos por fecha de inicio.
     *
     * @return Lista de gastos ordenados por fecha de inicio.
     */
    private List<Gasto> ordenarGastos(List<Gasto> lstGasto) {
        List<Gasto> lstGastosOrdenados = new ArrayList<>(lstGasto);

        // Ordenar la lista usando un Comparator
        Collections.sort(lstGastosOrdenados, new Comparator<Gasto>() {
            @Override
            public int compare(Gasto gasto1, Gasto gasto2) {
                return gasto1.getFechaInicio().compareTo(gasto2.getFechaInicio());
            }
        });
        return lstGastosOrdenados;
    }

    /**
     * Obtiene los gastos del mes actual.
     *
     * @return Lista de gastos del mes actual.
     */
    public List<Gasto> obtenerGastoMesActual() {
        List<Gasto> gastosMesActual = new ArrayList<>();
        LocalDate fechaActual = LocalDate.now();
        Month mesActual = fechaActual.getMonth();
        int anioActual = fechaActual.getYear();
        LocalDate inicioMesActual = LocalDate.of(anioActual, mesActual, 1);
        LocalDate finMesActual = inicioMesActual.withDayOfMonth(inicioMesActual.lengthOfMonth());

        for (Gasto gasto : this.ordenarGastos(lstGasto)) {
            DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate fechaInicio = gasto.getFechaInicio();
            LocalDate fechaFin = LocalDate.parse(gasto.getFechaFin(), formato);

            if ((fechaInicio.isBefore(finMesActual) || fechaInicio.isEqual(finMesActual)) &&
                    (fechaFin.isAfter(inicioMesActual) || fechaFin.isEqual(inicioMesActual))) {
                gastosMesActual.add(gasto);
            }
        }

        return gastosMesActual;
    }

    /**
     * Calcula el total de gastos del mes actual.
     *
     * @return Total de gastos del mes actual.
     */
    public double calcularTotalGastoxMes(){
        double total = 0;
        for (Gasto gasto : this.obtenerGastoMesActual()) {
            total += gasto.getValorNeto();
        }
        total += cuotasGastosxMes();
        return total;
    }

    /**
     * Calcula el total de cuotas de gastos para el mes actual.
     *
     * @return Total de cuotas de ingresos del mes actual.
     */
    public double cuotasGastosxMes() {
        LocalDate fechaActual = LocalDate.now();
        Month mesActual = fechaActual.getMonth();
        int anioActual = fechaActual.getYear();
        LocalDate inicioMesActual = LocalDate.of(anioActual, mesActual, 1);
        LocalDate finMesActual = inicioMesActual.withDayOfMonth(inicioMesActual.lengthOfMonth());

        List<CuentaxPagar> cuentasxPagar = cargarCuentasxPagar();
        double cuotasGastos = 0;

        for (CuentaxPagar cuentaxPagar : cuentasxPagar) {
            LocalDate fechaInicio = cuentaxPagar.getFechaInicio();
            LocalDate fechaFin = cuentaxPagar.getFechaFin();

            if ((fechaInicio.isBefore(finMesActual) || fechaInicio.isEqual(finMesActual)) &&
                    (fechaFin.isAfter(inicioMesActual) || fechaFin.isEqual(inicioMesActual))) {
                cuotasGastos += cuentaxPagar.getCuota();
            }
        }

        return cuotasGastos;
    }

    private void mostrarTablaAnioActual() {
        List<Gasto> lstGastoAnioActual = ordenarGastos(procesarIngresosPorMeses(obtenerGastoAnioActual()));
        Log.e("TablaReporteIngreso", lstGastoAnioActual.toString());


        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.tabla_anio_actual, null);

        FrameLayout frameLayout = findViewById(R.id.frameLayoutReporteGasto);

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

        TextView tvAnioActual = findViewById(R.id.textViewMesActualGasto);
        tvAnioActual.setText("Año Actual: " + LocalDate.now().getYear());
        tvAnioActual.setTextColor(ContextCompat.getColor(this, R.color.md_theme_onBackground));
        tableLayout.addView(lineaDivisorEncabezado);

        List<String> meses = new ArrayList<>();
        for (Gasto gasto : lstGastoAnioActual) {
            TableRow tr = new TableRow(this);
            tr.setPadding(0, 20, 0, 20);
            String mes = ReporteActivity.obtenerNombreMes(ReporteActivity.getMesxFecha(gasto.getFechaInicio()));
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
            tvCategoria.setText(gasto.getCategoria().getNombre());
            tvCategoria.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f));
            tvCategoria.setGravity(Gravity.CENTER);
            tvCategoria.setTextColor(ContextCompat.getColor(this, R.color.md_theme_inverseSurface));

            TextView tvValor = new TextView(this);
            tvValor.setText(String.valueOf(gasto.getValorNeto()));
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
        tvCuotas.setTextColor(getColor(R.color.md_theme_onBackground));

        TextView tvVacio = new TextView(this);
        tvVacio.setText("");
        TableRow.LayoutParams paramsVacio = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f);
        tvVacio.setLayoutParams(paramsVacio);
        tvVacio.setGravity(Gravity.CENTER);

        TextView tvTotalCuotas = new TextView(this);
        tvTotalCuotas.setText(String.valueOf(cuotasGastosxAnio()));
        TableRow.LayoutParams paramsValorCuotas = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f);
        tvTotalCuotas.setLayoutParams(paramsValorCuotas);
        tvTotalCuotas.setGravity(Gravity.CENTER);
        tvTotalCuotas.setTextColor(getColor(R.color.md_theme_onBackground));

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
        tvTotal.setTextColor(getColor(R.color.md_theme_onBackground));

        TextView tvVacio1 = new TextView(this);
        tvVacio1.setText("");
        TableRow.LayoutParams paramsVacio1 = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f);
        tvVacio1.setLayoutParams(paramsVacio1);
        tvVacio1.setGravity(Gravity.CENTER);

        TextView tvTotalValor = new TextView(this);
        tvTotalValor.setText(String.valueOf(calcularTotalGastoxAnio()));
        tvTotalValor.setTypeface(null, Typeface.BOLD);
        TableRow.LayoutParams paramsValorTotal = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f);
        tvTotalValor.setLayoutParams(paramsValorTotal);
        tvTotalValor.setGravity(Gravity.CENTER);
        tvTotalValor.setTextColor(getColor(R.color.md_theme_onBackground));

        trTotal.addView(tvTotal);
        trTotal.addView(tvVacio1);
        trTotal.addView(tvTotalValor);
        tableLayout.addView(trTotal);

        if (frameLayout != null) {
            frameLayout.addView(view);
        }
    }

    public List<Gasto> procesarIngresosPorMeses(List<Gasto> gastos) {
        List<Gasto> lstGastosExtendidos = new ArrayList<>();
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate fechaActual = LocalDate.now();

        for (Gasto gasto : gastos) {
            LocalDate fechaInicio = gasto.getFechaInicio();
            LocalDate fechaFin;

            if (gasto.getTipoRepeticion() == TipoRepeticion.MES) {
                if (gasto.getFechaFin().equals("No definida")) {
                    lstGastosExtendidos.add(new Gasto(
                            gasto.getCategoria(),
                            gasto.getValorNeto(),
                            gasto.getDescripcion(),
                            fechaInicio,
                            null,
                            gasto.getTipoRepeticion()
                    ));
                } else {

                    fechaFin = LocalDate.parse(gasto.getFechaFin(), formato);

                    if (fechaInicio.getYear() < fechaActual.getYear()) {
                        fechaInicio = LocalDate.of(fechaActual.getYear(), 1, 1);
                    }

                    if (fechaFin.getYear() > fechaActual.getYear()) {
                        fechaFin = LocalDate.of(fechaActual.getYear(), 12, 31);
                    }

                    LocalDate fechaIterador = fechaInicio.withDayOfMonth(1);
                    while (!fechaIterador.isAfter(fechaActual)) {
                        if (!fechaIterador.isAfter(fechaFin)) {
                            lstGastosExtendidos.add(new Gasto(
                                    gasto.getCategoria(),
                                    gasto.getValorNeto(),
                                    gasto.getDescripcion(),
                                    fechaIterador,
                                    fechaFin,
                                    gasto.getTipoRepeticion()
                            ));
                        }
                        fechaIterador = fechaIterador.plusMonths(1);
                    }
                }
            } else {
                lstGastosExtendidos.add(gasto);
            }
        }

        return lstGastosExtendidos;
    }
    /**
     * Calcula el total de gastos del año actual.
     *
     * @return Total de gastos del año actual.
     */
    public double calcularTotalGastoxAnio(){
        double total = 0;
        for (Gasto gasto : this.procesarIngresosPorMeses(obtenerGastoAnioActual())) {
            total += gasto.getValorNeto();
        }
        total += cuotasGastosxAnio();
        return total;
    }

    /**
     * Obtiene los gastos del año actual, considerando la fecha de inicio y la fecha de fin.
     *
     * @return Lista de gastos del año actual.
     */
    public List<Gasto> obtenerGastoAnioActual() {
        List<Gasto> gastosAnioActual = new ArrayList<>();
        LocalDate fechaActual = LocalDate.now();
        int añoActual = fechaActual.getYear();

        for (Gasto gasto : this.ordenarGastos(lstGasto)) {
            LocalDate fechaInicio = gasto.getFechaInicio();
            LocalDate fechaFin;
            if (gasto.getFechaFin().equals("No definida")) {
                fechaFin = fechaActual;
            } else {
                fechaFin = LocalDate.parse(gasto.getFechaFin(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            }

            if ((fechaInicio.getYear() == añoActual || fechaFin.getYear() == añoActual) && (fechaInicio.getMonthValue() <= fechaActual.getMonthValue() || fechaFin.getMonthValue() >= fechaActual.getMonthValue())) {
                gastosAnioActual.add(gasto);
            }
        }
        return gastosAnioActual;
    }

    /**
     * Calcula el total de cuotas de gastos acumuladas hasta el mes actual del año actual,
     * considerando la fecha de inicio y la fecha de fin de cada cuenta.
     *
     * @return Total de cuotas de gastos acumuladas hasta el mes actual del año actual.
     */
    public double cuotasGastosxAnio() {
        LocalDate fechaActual = LocalDate.now();
        int añoActual = fechaActual.getYear();
        Month mesActual = fechaActual.getMonth();
        double cuotas = 0;

        for (CuentaxPagar cuenta : cargarCuentasxPagar()) {
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

    private List<Gasto> cargarListaGastos() {
        List<Gasto> listaGasto = new ArrayList<>();
        try {
            List<Movimiento> listaMovimiento = Movimiento.cargarMovimientos(this.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS));
            Log.d("TablaReporteGasto", listaMovimiento.toString());
            for(Movimiento movimiento: listaMovimiento) {
                if(movimiento instanceof Gasto) {
                    Gasto gasto = (Gasto) movimiento;
                    listaGasto.add(gasto);
                }
            }
        } catch(Exception e){
            e.printStackTrace();
        }
        return listaGasto;
    }

    private List<CuentaxPagar> cargarCuentasxPagar(){
        ArrayList<CuentaxPagar> listaCuenta = new ArrayList<>();
        try {
            listaCuenta = CuentaFinanciera.cargarCuentasxPagar(this.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS));
        } catch(Exception e){
            Log.e("TablaReporteGasto", "Error al cargar los datos de cuentaxcobrar "+ e.getMessage());
        }
        return listaCuenta;
    }

}
