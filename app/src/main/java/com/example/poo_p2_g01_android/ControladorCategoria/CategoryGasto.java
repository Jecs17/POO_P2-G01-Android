package com.example.poo_p2_g01_android.ControladorCategoria;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.example.poo_p2_g01_android.R;
import java.util.List;
import modelo.movimiento.Categoria;


public class CategoryGasto extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category_gasto, container, false);
        llenarListViewG(view);
        return view;
    }

    private void llenarListViewG(View view) {
        List<Categoria> lstCategoriaGasto;
        ListView listViewG = view.findViewById(R.id.listViewGasto);
        CategoryActivity categoryActivity = (CategoryActivity) this.getActivity();
        if (categoryActivity != null) {
            lstCategoriaGasto = categoryActivity.LeerDatosGasto();
            ArrayAdapter adapter = new ArrayAdapter(categoryActivity, android.R.layout.simple_list_item_1, lstCategoriaGasto);
            listViewG.setAdapter(adapter);
        }
    }
}