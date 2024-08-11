package com.example.poo_p2_g01_android.ControladorCategoria;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import com.example.poo_p2_g01_android.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import java.util.List;
import modelo.movimiento.Categoria;

/**
 * Adaptador personalizado para mostrar una lista de categorías en un ListView.
 */
public class ListViewAdapter extends ArrayAdapter<Categoria> {

    /**
     * Lista de categorías que se mostrarán en el adaptador.
     */
    private List<Categoria> lstCategorias;


    /**
     * Contexto en el que se utiliza el adaptador.
     */
    private Context context;

    /**
     * Recurso de diseño para cada elemento de la lista.
     */
    private int resource;

    /**
     * Constructor del adaptador.
     *
     * @param context El contexto en el que se utiliza el adaptador.
     * @param resource El recurso de diseño para cada elemento de la lista.
     * @param objects La lista de categorías a mostrar.
     */
    public ListViewAdapter(@NonNull Context context, int resource, @NonNull List<Categoria> objects) {
        super(context, resource, objects);
        this.lstCategorias = objects;
        this.context = context;
        this.resource = resource;
    }

    /**
     * Obtiene la vista para un elemento en una posición específica.
     *
     * @param position La posición del elemento en la lista.
     * @param convertView La vista reciclada para reutilizar, si está disponible.
     * @param parent El contenedor en el que se colocará la vista.
     * @return La vista para el elemento en la posición especificada.
     */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if(view == null){
            view = LayoutInflater.from(context).inflate(resource, null);
        }

        if (convertView != null) {
            convertView.setOnClickListener(null);
        }
        if (convertView != null) {
            convertView.setClickable(false);
        }

        Categoria categoria = lstCategorias.get(position);
        TextView textView = view.findViewById(R.id.textViewCategoria);
        ImageView imageView = view.findViewById(R.id.imageView);
        textView.setText(categoria.getNombre());

        imageView.setOnClickListener(view1 -> {
            AlertDialog alertDialog = new MaterialAlertDialogBuilder(getContext())
                    .setTitle("ALERTA")
                    .setMessage("¿Seguro de eliminar la categoría?")
                    .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            lstCategorias.remove(categoria);
                            Categoria.eliminarDatos(getContext().getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), categoria);
                            notifyDataSetChanged();
                            dialogInterface.dismiss();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    }).create();

            alertDialog.show();

            TextView messageView = alertDialog.findViewById(android.R.id.message);
            if (messageView != null) {
                messageView.setTextSize(18);
            }

            Button positiveButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
            if (positiveButton != null) {
                positiveButton.setTextSize(18);
            }

            Button negativeButton = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE);
            if (negativeButton != null) {
                negativeButton.setTextSize(18);
            }
        });

        return view;
    }

}
