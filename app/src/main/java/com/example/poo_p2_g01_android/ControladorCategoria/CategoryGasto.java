package com.example.poo_p2_g01_android.ControladorCategoria;

import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.poo_p2_g01_android.R;

import java.util.ArrayList;
import java.util.List;
import modelo.movimiento.Categoria;

/**
 * Fragmento que muestra una lista de categorías de gasto.
 *
 * Este fragmento inflará una vista con un ListView que muestra las categorías de gasto.
 * Permite agregar nuevas categorías de gasto a la lista y actualiza la vista cuando se añaden o se cargan datos.
 */
public class CategoryGasto extends Fragment  {

    /**
     * Lista de categorías de gasto que se muestran en el fragmento.
     */
    private List<Categoria> categoriasGastos;

    /**
     * Adaptador para vincular los datos de categorías de gasto con la vista ListView.
     */
    private ArrayAdapter<Categoria> adapter;

    /**
     * Infla la vista del fragmento y configura el ListView para mostrar las categorías de gasto.
     *
     * @param inflater El objeto LayoutInflater que se utilizará para inflar la vista.
     * @param container El contenedor al que se adjuntará la vista.
     * @param savedInstanceState El estado guardado del fragmento (si lo hay).
     * @return La vista inflada del fragmento.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category_gasto, container, false);
        ListView listViewG = view.findViewById(R.id.listViewGasto);

        categoriasGastos = new ArrayList<>();
        adapter = new ListViewAdapter(getContext(), R.layout.item_row, categoriasGastos);
        listViewG.setAdapter(adapter);
        llenarListaG();
        return view;
    }

    /**
     * Agrega una nueva categoría de gasto a la lista y actualiza la vista.
     *
     * @param nuevaCategoria La categoría de gasto que se agregará a la lista.
     */
    public void agregarCategoriaG(Categoria nuevaCategoria) {
        categoriasGastos.add(nuevaCategoria);
        adapter.notifyDataSetChanged();
    }

    /**
     * Llena la lista de categorías de gasto con los datos leídos desde la actividad principal.
     */
    private void llenarListaG() {
        CategoryActivity categoryActivity = (CategoryActivity) getActivity();
        Context context = getContext();
        if(categoryActivity != null) {
            categoriasGastos.addAll(categoryActivity.leerDatosGasto(context));
            adapter.notifyDataSetChanged();
        }
    }
}