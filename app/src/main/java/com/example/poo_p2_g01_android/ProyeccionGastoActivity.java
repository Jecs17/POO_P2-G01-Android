package com.example.poo_p2_g01_android;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.poo_p2_g01_android.ControladorCategoria.CategoryActivity;
import com.example.poo_p2_g01_android.ControladorReporte.ReporteActivity;
import com.example.poo_p2_g01_android.ControladorReporte.TablaReporteGasto;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import modelo.movimiento.Categoria;
import modelo.movimiento.Gasto;

public class ProyeccionGastoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_proyeccion_gasto);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.vistaProyeccionGasto), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        regresar();
        //TODO: ReporteActivity -> obtener nombre mes, reporte crear gastosPorMesYMismaCategoria()
        //TODO: Parte final reemplazar formato String por Lista de ROWs
    }

    @Override
    protected void onResume() {
        super.onResume();
        prueba();
    }

    private void regresar(){
        ImageButton btnRegresar = findViewById(R.id.btnRegresarProyeccion);
        btnRegresar.setOnClickListener(v -> finish());
    }

    /**
     * Obtiene los meses correspondientes a los tres meses anteriores al actual.
     *
     * @return Lista de meses correspondientes.
     */
    private List<Month> obtenerMeses(){
        int indiceMesActual = LocalDate.now().getMonthValue();
        List<Month> TresMesesAntes = new ArrayList<>();
        for(int i = 0; i < 3; i++){
            TresMesesAntes.add(Month.of(indiceMesActual));
            indiceMesActual -= 1;
        }

        return TresMesesAntes;
    }

    /**
     * Obtiene los nombres de los meses de la proyección de gastos.
     *
     * @return Arreglo de cadenas con los nombres de los meses.
     */
    private String[] nombreMeses(){
        List<Month> obtenerMeses = this.obtenerMeses();
        String[] meses = new String[4];
        int indiceMes = LocalDate.now().getMonthValue()+1;
        if(indiceMes > 12){
            indiceMes = 1;
        }
        meses[0] = ReporteActivity.obtenerNombreMes(indiceMes);
        for(int i=1; i < meses.length; i++){
            String nombreMes = ReporteActivity.obtenerNombreMes(obtenerMeses.get(i-1).getValue());
            meses[i] = nombreMes;
        }
        return meses;
    }

    public String gastosDeTresMeses(){
        List<Month> tresMeses = obtenerMeses();
        List<Categoria> listaCategoriaGasto = CategoryActivity.leerDatosGasto(this);
        List<Gasto> gastosMes1 = new ArrayList<>();
        List<Gasto> gastosMes2 = new ArrayList<>();
        List<Gasto> gastosMes3 = new ArrayList<>();

        for(Month mes: tresMeses){
            List<Gasto> obtenerGastoMes = TablaReporteGasto.gastosPorMesYMismaCategoria(mes,this);
            if(gastosMes1.isEmpty()){
                gastosMes1.addAll(obtenerGastoMes);
            }else if(gastosMes2.isEmpty()){
                gastosMes2.addAll(obtenerGastoMes);
            }else if(gastosMes3.isEmpty()){
                gastosMes3.addAll(obtenerGastoMes);
            }
        }

        String formatoValores="";

        for(Categoria categoria: listaCategoriaGasto){
            List<Double> valoresNetosPorCategoria = new ArrayList<>();
            boolean sinGasto = true;
            for(Gasto gasto: gastosMes3){
                if(categoria.equals(gasto.getCategoria())){
                    valoresNetosPorCategoria.add(gasto.getValorNeto());
                    sinGasto = false;
                }
            }
            if(sinGasto){
                valoresNetosPorCategoria.add(0.0);
            }
            sinGasto = true;
            for(Gasto gasto: gastosMes2){
                if(categoria.equals(gasto.getCategoria())){
                    valoresNetosPorCategoria.add(gasto.getValorNeto());
                    sinGasto = false;
                }
            }
            if(sinGasto){
                valoresNetosPorCategoria.add(0.0);
            }
            sinGasto = true;
            for(Gasto gasto: gastosMes1){
                if(categoria.equals(gasto.getCategoria())){
                    valoresNetosPorCategoria.add(gasto.getValorNeto());
                    sinGasto = false;
                }
            }
            if(sinGasto){
                valoresNetosPorCategoria.add(0.0);
            }
            double sumaValores = 0;
            for(Double valor: valoresNetosPorCategoria){
                sumaValores += valor;
            }

            double promedioValores = sumaValores/valoresNetosPorCategoria.size();
            formatoValores += String.format("%-25s %-8.2f %-8.2f %-8.2f %-8.2f%n", categoria.getNombre(), valoresNetosPorCategoria.get(0), valoresNetosPorCategoria.get(1), valoresNetosPorCategoria.get(2), promedioValores);
        }
        return formatoValores;
    }

    private void prueba(){
        Log.i("Proyeccion", gastosDeTresMeses());
    }
}