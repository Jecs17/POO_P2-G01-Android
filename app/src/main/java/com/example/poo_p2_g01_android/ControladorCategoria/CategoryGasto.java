package com.example.poo_p2_g01_android.ControladorCategoria;

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


public class CategoryGasto extends Fragment {

    private List<Categoria> categoriasGastos;
    private ArrayAdapter<Categoria> adapter;

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

    public void agregarCategoriaG(Categoria nuevaCategoria) {
        categoriasGastos.add(nuevaCategoria);
        adapter.notifyDataSetChanged();
    }

    private void llenarListaG() {
        CategoryActivity categoryActivity = (CategoryActivity) getActivity();
        if(categoryActivity != null) {
            categoriasGastos.clear();
            categoriasGastos.addAll(categoryActivity.leerDatosGasto());
            adapter.notifyDataSetChanged();
        }
    }

    public void actualizarListaG() {
        llenarListaG();
    }

}