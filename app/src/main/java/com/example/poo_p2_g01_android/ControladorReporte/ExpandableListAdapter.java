package com.example.poo_p2_g01_android.ControladorReporte;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.poo_p2_g01_android.R;

import java.util.List;

public class ExpandableListAdapter extends BaseExpandableListAdapter {
    /**
     * Contexto de la aplicación o actividad donde se utiliza este adaptador.
     * Es utilizado para acceder a recursos, servicios y otras funcionalidades propias de Android.
     */

    private Context context;

    /**
     * Lista de cadenas que representa los grupos principales en la lista expandible.
     * Cada elemento en esta lista corresponde a un grupo que puede ser expandido o contraído.
     */
    private List<String> groupList;

    /**
     * Lista de listas de cadenas que contiene los elementos hijos de cada grupo.
     * Cada lista dentro de esta lista representa los elementos hijos que pertenecen a un grupo en la {@code groupList}.
     */
    private List<List<String>> childItemLists;

    /**
     * Constructor de la clase ExpandableListAdapter.
     *
     * @param context       El contexto de la aplicación o actividad.
     * @param groupList     La lista de grupos principales.
     * @param childItemLists La lista de listas que contiene los elementos hijos para cada grupo.
     */
    public ExpandableListAdapter(Context context, List<String> groupList, List<List<String>> childItemLists) {
        this.context = context;
        this.groupList = groupList;
        this.childItemLists = childItemLists;
    }

    /**
     * Devuelve el número total de grupos en la lista expandible.
     *
     * @return El número de grupos en {@code groupList}.
     */
    @Override
    public int getGroupCount() {
        return groupList.size();
    }

    /**
     * Devuelve el número de elementos hijos en un grupo específico.
     *
     * @param i El índice del grupo en {@code groupList}.
     * @return El número de elementos hijos en el grupo en {@code childItemLists}.
     */
    @Override
    public int getChildrenCount(int i) {
        return childItemLists.get(i).size();
    }

    /**
     * Devuelve el grupo en una posición específica.
     *
     * @param i El índice del grupo en {@code groupList}.
     * @return El grupo en la posición especificada de {@code groupList}.
     */
    @Override
    public Object getGroup(int i) {
        return groupList.get(i);
    }

    /**
     * Devuelve el elemento hijo en una posición específica dentro de un grupo.
     *
     * @param i  El índice del grupo en {@code groupList}.
     * @param i1 El índice del hijo dentro del grupo especificado.
     * @return El elemento hijo en la posición especificada de {@code childItemLists}.
     */
    @Override
    public Object getChild(int i, int i1) {
        return childItemLists.get(i).get(i1);
    }

    /**
     * Devuelve el ID del grupo en una posición específica.
     *
     * @param i El índice del grupo en {@code groupList}.
     * @return El ID del grupo, que en este caso es el mismo índice {@code i}.
     */
    @Override
    public long getGroupId(int i) {
        return i;
    }

    /**
     * Devuelve el ID del elemento hijo en una posición específica dentro de un grupo.
     *
     * @param i  El índice del grupo en {@code groupList}.
     * @param i1 El índice del hijo dentro del grupo especificado.
     * @return El ID del elemento hijo, que en este caso es el mismo índice {@code i1}.
     */
    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    /**
     * Indica si los IDs de los grupos y los hijos son estables en el tiempo.
     *
     * @return {@code false}, ya que los IDs no son estables.
     */
    @Override
    public boolean hasStableIds() {
        return false;
    }

    /**
     * Proporciona una vista para representar un grupo en la lista expandible.
     *
     * @param groupPosition La posición del grupo dentro de {@code groupList}.
     * @param isExpanded    Indica si el grupo está expandido o contraído.
     * @param convertView   La vista reutilizada para representar el grupo, o {@code null} si es necesario inflar una nueva vista.
     * @param parent        El contenedor principal al que esta vista se adjuntará.
     * @return Una vista que representa el grupo en la posición especificada.
     */
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String groupTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.expandable_group_list, parent, false);
        }
        TextView textView = convertView.findViewById(R.id.group_text);
        textView.setTextColor(ContextCompat.getColor(parent.getContext(), R.color.md_theme_onBackground));
        textView.setTypeface(null, Typeface.BOLD);
        textView.setText(groupTitle);
        return convertView;
    }

    /**
     * Proporciona una vista para representar un elemento hijo en la lista expandible.
     *
     * @param groupPosition La posición del grupo padre dentro de {@code groupList}.
     * @param childPosition La posición del hijo dentro del grupo padre en {@code childItemLists}.
     * @param isLastChild   Indica si este hijo es el último dentro del grupo.
     * @param convertView   La vista reutilizada para representar el hijo, o {@code null} si es necesario inflar una nueva vista.
     * @param parent        El contenedor principal al que esta vista se adjuntará.
     * @return Una vista que representa el hijo en la posición especificada.
     */
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        String childTitle = (String) getChild(groupPosition, childPosition);
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.expandable_child_list, parent, false);
        }
        TextView textView = convertView.findViewById(R.id.child_text);
        textView.setTextColor(ContextCompat.getColor(parent.getContext(), R.color.md_theme_inverseSurface));
        textView.setText(childTitle);
        return convertView;
    }

    /**
     * Indica si un elemento hijo específico es seleccionable.
     *
     * @param i  El índice del grupo en {@code groupList}.
     * @param i1 El índice del hijo dentro del grupo en {@code childItemLists}.
     * @return {@code true} siempre, ya que todos los elementos hijos son seleccionables.
     */
    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}
