package com.example.poo_p2_g01_android.ControladorReporte;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
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

public class ReporteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_reporte);
        inicializarExpandable();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.vistaReporte), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

    }

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

    public static int getMesxFecha(LocalDate fecha) {
        return fecha.getMonth().getValue();
    }
}