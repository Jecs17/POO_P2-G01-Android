package com.example.poo_p2_g01_android;

import android.os.Bundle;
import android.view.Gravity;
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

import com.example.poo_p2_g01_android.ControladorCategoria.CategoryActivity;
import com.example.poo_p2_g01_android.ControladorReporte.ReporteActivity;
import com.example.poo_p2_g01_android.ControladorReporte.TablaReporteGasto;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import modelo.movimiento.Categoria;
import modelo.movimiento.Gasto;

/**
 * Esta actividad se encarga de proyectar los gastos basados en los gastos de los
 * últimos tres meses y mostrar los resultados en una tabla.
 *
 * @author Grupo1
 */
public class ProyeccionGastoActivity extends AppCompatActivity {

    /**
     * Método llamado cuando la actividad es creada.
     *
     * @param savedInstanceState Contiene los datos más recientes de la actividad.
     */
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
    }

    /**
     * Método llamado cuando la actividad entra en primer plano.
     * Se encarga de llenar la tabla con los datos proyectados.
     */
    @Override
    protected void onResume() {
        super.onResume();
        llenarTabla();
    }

    /**
     * Configura el botón de regreso para finalizar la actividad cuando es presionado.
     */
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

    /**
     * Obtiene las filas de la tabla con los gastos de los últimos tres meses por categoría.
     *
     * @return Lista de filas de tabla con los datos de gastos.
     */
    private ArrayList<TableRow> gastosDeTresMeses(){
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

        ArrayList<TableRow> lstFilas = new ArrayList<>();

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

            TableRow tr = new TableRow(this);

            TextView tvCategoria = new TextView(this);
            tvCategoria.setTextColor(ContextCompat.getColor(this, R.color.md_theme_background));
            tvCategoria.setPadding(20, 10, 20, 10);
            tvCategoria.setText(categoria.getNombre());

            TextView tvValorMes1 = new TextView(this);
            tvValorMes1.setTextColor(ContextCompat.getColor(this, R.color.md_theme_background));
            tvValorMes1.setPadding(20, 10, 20, 10);
            tvValorMes1.setText(String.valueOf(valoresNetosPorCategoria.get(0)));
            tvValorMes1.setGravity(Gravity.CENTER_HORIZONTAL);

            TextView tvValorMes2 = new TextView(this);
            tvValorMes2.setTextColor(ContextCompat.getColor(this, R.color.md_theme_background));
            tvValorMes2.setPadding(20, 10, 20, 10);
            tvValorMes2.setText(String.valueOf(valoresNetosPorCategoria.get(1)));
            tvValorMes2.setGravity(Gravity.CENTER_HORIZONTAL);

            TextView tvValorMes3 = new TextView(this);
            tvValorMes3.setTextColor(ContextCompat.getColor(this, R.color.md_theme_background));
            tvValorMes3.setPadding(20, 10, 20, 10);
            tvValorMes3.setText(String.valueOf(valoresNetosPorCategoria.get(2)));
            tvValorMes3.setGravity(Gravity.CENTER_HORIZONTAL);

            TextView tvValorMesProyectado = new TextView(this);
            tvValorMesProyectado.setTextColor(ContextCompat.getColor(this, R.color.md_theme_background));
            tvValorMesProyectado.setPadding(20, 10, 22, 10);
            tvValorMesProyectado.setText(String.valueOf(promedioValores));
            tvValorMesProyectado.setGravity(Gravity.CENTER_HORIZONTAL);

            tr.addView(tvCategoria);
            tr.addView(tvValorMes1);
            tr.addView(tvValorMes2);
            tr.addView(tvValorMes3);
            tr.addView(tvValorMesProyectado);
            tr.setPadding(0, 15, 0, 0);

            lstFilas.add(tr);
        }
        return lstFilas;
    }

    /**
     * Llena la tabla con los datos de los gastos proyectados y sus respectivos meses.
     */
    private void llenarTabla(){
        ArrayList<TableRow> lstFilas = gastosDeTresMeses();
        String[] meses = nombreMeses();

        TextView contenidoProyectado = findViewById(R.id.txtviewContextoProyectado);

        String textoProyectado = getString(R.string.contenidoProyectado) + " " + meses[0];
        contenidoProyectado.setText(textoProyectado);

        TextView mes1 = findViewById(R.id.txtviewMes1);
        TextView mes2 = findViewById(R.id.txtviewMes2);
        TextView mes3 = findViewById(R.id.txtviewMes3);
        TextView mesProyectado = findViewById(R.id.txtviewMesProyectado);

        mes1.setText(meses[3]);
        mes2.setText(meses[2]);
        mes3.setText(meses[1]);
        mesProyectado.setText(meses[0]);

        TableLayout tablaProyeccion = findViewById(R.id.tablaProyeccionGastos);
        cleanTable(tablaProyeccion);
        for(TableRow fila: lstFilas){
            tablaProyeccion.addView(fila);
        }
    }

    /**
     * Limpia la tabla eliminando todas las filas excepto la primera.
     *
     * @param table Tabla a limpiar.
     */
    private void cleanTable(TableLayout table) {

        int childCount = table.getChildCount();

        // Elimina todas las filas excepto la primera
        if (childCount > 1) {
            table.removeViews(1, childCount - 1);
        }
    }
}