package com.example.poo_p2_g01_android.ControladorCategoria;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.poo_p2_g01_android.R;

import java.util.ArrayList;
import java.util.List;
import modelo.movimiento.Categoria;

/**
 * Fragmento que muestra las categorías de ingreso en una vista de lista.
 */
public class CategoryIngreso extends Fragment {

    /**
     * Lista de categorías de ingreso que se muestran en el fragmento.
     */
    private List<Categoria> categoriasIngresos;

    /**
     * Adaptador para vincular los datos de categorías de ingreso con la vista ListView.
     */
    private ListViewAdapter adapter;


    /**
     * Infla la vista del fragmento y configura el adaptador para la lista de categorías de ingreso.
     *
     * @param inflater El objeto LayoutInflater utilizado para inflar el diseño del fragmento.
     * @param container El contenedor en el que el fragmento se inserta.
     * @param savedInstanceState El estado previamente guardado del fragmento.
     * @return La vista inflada del fragmento.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category_ingreso, container, false);
        ListView listViewI = view.findViewById(R.id.listViewIngreso);
        categoriasIngresos = new ArrayList<>();
        adapter = new ListViewAdapter(getContext(), R.layout.item_row, categoriasIngresos);
        listViewI.setAdapter(adapter);
        llenarListaI();
        return view;
    }

    /**
     * Agrega una nueva categoría de ingreso a la lista y actualiza la vista.
     *
     * @param nuevaCategoria La categoría de ingreso que se desea agregar.
     */
    public void agregarCategoriaI(Categoria nuevaCategoria) {
        categoriasIngresos.add(nuevaCategoria);
        adapter.notifyDataSetChanged();

    }

    /**
     * Llena la lista de categorías de ingreso con los datos obtenidos de la actividad.
     */
    private void llenarListaI() {
        CategoryActivity categoryActivity = (CategoryActivity) getActivity();
        categoriasIngresos.clear();
        if(categoryActivity != null) {
            categoriasIngresos.addAll(categoryActivity.leerDatosIngreso());
            adapter.notifyDataSetChanged();
        }
    }
}