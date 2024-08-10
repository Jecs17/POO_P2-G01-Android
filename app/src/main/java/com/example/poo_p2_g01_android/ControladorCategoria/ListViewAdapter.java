package com.example.poo_p2_g01_android.ControladorCategoria;

import android.content.Context;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.example.poo_p2_g01_android.R;

import java.util.List;

import enums.TipoCategoria;
import modelo.movimiento.Categoria;

public class ListViewAdapter extends ArrayAdapter<Categoria> {
    private List<Categoria> lstCategorias;
    private Context context;
    private int resource;

    public ListViewAdapter(@NonNull Context context, int resource, @NonNull List<Categoria> objects) {
        super(context, resource, objects);
        this.lstCategorias = objects;
        this.context = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if(view == null){
            view = LayoutInflater.from(context).inflate(resource, null);
        }

        Categoria categoria = lstCategorias.get(position);
        TextView textView = view.findViewById(R.id.textViewCategoria);
        ImageView imageView = view.findViewById(R.id.imageView);
        textView.setText(categoria.getNombre());

        imageView.setOnClickListener(view1 -> {
            lstCategorias.remove(categoria);
            Categoria.eliminarDatos(getContext().getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), categoria);
            notifyDataSetChanged();
        });

        return view;
    }

}
