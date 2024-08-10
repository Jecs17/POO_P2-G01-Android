package com.example.poo_p2_g01_android.ControladorCategoria;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.example.poo_p2_g01_android.R;

import java.util.ArrayList;
import java.util.List;
import modelo.movimiento.Categoria;

public class CategoryIngreso extends Fragment {

    private List<Categoria> categoriasIngresos;
    private ListViewAdapter adapter;


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

    public void agregarCategoriaI(Categoria nuevaCategoria) {
        categoriasIngresos.add(nuevaCategoria);
        adapter.notifyDataSetChanged();

    }

    private void llenarListaI() {
        CategoryActivity categoryActivity = (CategoryActivity) getActivity();
        categoriasIngresos.clear();
        if(categoryActivity != null) {
            categoriasIngresos.addAll(categoryActivity.leerDatosIngreso());
            adapter.notifyDataSetChanged();
        }
    }

    public void actualizarListaI() {
        llenarListaI();
    }

}