package com.example.poo_p2_g01_android.ControladorReporte;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.poo_p2_g01_android.R;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * ReporteActivity es una actividad que se encarga de mostrar el reporte financiero
 * utilizando una vista expandible para los datos.
 * Esta actividad está configurada para utilizar la funcionalidad Edge-to-Edge para
 * una experiencia de usuario más inmersiva.
 */
public class ReporteActivity extends AppCompatActivity {

    /**
     * Método que se llama cuando se crea la actividad.
     * Aquí se configuran los elementos de la interfaz, se habilita el modo Edge-to-Edge,
     * se inicializa la vista expandible, y se ajusta el padding de la vista según las
     * barras del sistema (status bar, navigation bar, etc.).
     *
     * @param savedInstanceState Estado de la actividad guardado previamente, si existe.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_reporte);
        inicializarExpandable();
        retroceso();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.vistaReporte), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

    }

    /**
     * Configura el evento del botón de retroceso para finalizar la actividad.
     */
    private void retroceso() {
        ImageButton backButton = findViewById(R.id.btnAtrasReporte);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    /**
     * Inicializa y configura la vista expandible en la actividad, incluyendo la asignación de datos
     * a los grupos y elementos hijos, y establece un listener para manejar los clics en los elementos hijos.
     *
     * Este método se encarga de:
     * 1. Crear y configurar la vista expandible.
     * 2. Inicializar los datos de los grupos ("Ingresos" y "Gastos") y sus respectivos elementos hijos.
     * 3. Establecer un adaptador personalizado para manejar la vista expandible.
     * 4. Configurar un listener para manejar los eventos de clic en los elementos hijos.
     */
    public void inicializarExpandable() {
        ExpandableListView expandableListView;
        ExpandableListAdapter adapter;
        List<String> groupList;
        List<List<String>> childItemLists;

        expandableListView = findViewById(R.id.expandableListView);

        groupList = new ArrayList<>();
        childItemLists = new ArrayList<>();

        groupList.add("Ingresos");
        groupList.add("Gastos");

        List<String> childItems1 = new ArrayList<>();
        childItems1.add("Mes actual");
        childItems1.add("Año");

        List<String> childItems2 = new ArrayList<>();
        childItems2.add("Mes actual");
        childItems2.add("Año");

        childItemLists.add(childItems1);
        childItemLists.add(childItems2);

        adapter = new ExpandableListAdapter(this, groupList, childItemLists);
        expandableListView.setAdapter(adapter);

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                String group = groupList.get(groupPosition);
                String child = childItemLists.get(groupPosition).get(childPosition);
                EventoClickExpandable(group, child);
                return true;
            }
        });
    }

    /**
     * Maneja el evento de clic en un elemento hijo de la vista expandible.
     * Dependiendo del grupo y el hijo seleccionados, crea un Intent para iniciar
     * una actividad específica y pasa información adicional mediante extras.
     *
     * @param group El nombre del grupo al que pertenece el elemento hijo seleccionado.
     * @param child El nombre del elemento hijo seleccionado.
     */
    private void EventoClickExpandable(String group, String child) {
        Intent intent = null;

        if (child != null) {
            if (group.equals("Ingresos")) {
                intent = new Intent(this, TablaReporteIngreso.class);
            } else if (group.equals("Gastos")) {
                intent = new Intent(this, TablaReporteGasto.class);
            }

            if (intent != null) {
                intent.putExtra("tipoTabla", child);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Error al crear el Intent", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "No se ha seleccionado una opción válida", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Obtiene el nombre del mes basado en el número de mes proporcionado.
     *
     * @param mes Número de mes (1-12).
     * @return Nombre del mes correspondiente o "Mes inválido" si el número de mes es incorrecto.
     */
    public static String obtenerNombreMes(int mes) {
        switch (mes) {
            case 1:
                return "Enero";
            case 2:
                return "Febrero";
            case 3:
                return "Marzo";
            case 4:
                return "Abril";
            case 5:
                return "Mayo";
            case 6:
                return "Junio";
            case 7:
                return "Julio";
            case 8:
                return "Agosto";
            case 9:
                return "Septiembre";
            case 10:
                return "Octubre";
            case 11:
                return "Noviembre";
            case 12:
                return "Diciembre";
            default:
                return "Mes inválido";
        }
    }

    /**
     * Obtiene el número de mes actual.
     *
     * @return Número de mes (1-12).
     */
    public static int getMesActual() {
        LocalDate fechaActual = LocalDate.now();
        return fechaActual.getMonth().getValue();
    }

    /**
     * Obtiene el número del mes a partir de una fecha proporcionada.
     *
     * @param fecha La fecha de la cual se extraerá el número del mes. No debe ser {@code null}.
     * @return El número del mes correspondiente a la fecha proporcionada (1 para enero, 2 para febrero, etc.).
     * @throws NullPointerException Si {@code fecha} es {@code null}.
     */
    public static int getMesxFecha(LocalDate fecha) {
        return fecha.getMonth().getValue();
    }
}