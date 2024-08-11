package com.example.poo_p2_g01_android.ControladorCategoria;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

/**
 * Adaptador para gestionar y proporcionar fragmentos de categorías en un ViewPager2.
 *
 * Este adaptador gestiona dos fragmentos: uno para categorías de ingreso y otro para categorías de gasto.
 * Proporciona los fragmentos adecuados en función de la posición del ítem y permite obtener referencias a los fragmentos individuales.
 */
public class CategoryAdapter extends FragmentStateAdapter {

    /**
     * Fragmento para manejar las categorías de gasto.
     */
    private CategoryGasto categoryGastoFragment;

    /**
     * Fragmento para manejar las categorías de ingreso.
     */
    private CategoryIngreso categoryIngresoFragment;

    /**
     * Crea una nueva instancia de CategoryAdapter.
     *
     * @param fragmentActivity La actividad del fragmento que se utilizará para crear el adaptador.
     */
    public CategoryAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
        categoryIngresoFragment = new CategoryIngreso();
        categoryGastoFragment = new CategoryGasto();
    }

    /**
     * Devuelve el fragmento correspondiente a la posición dada.
     *
     * @param position La posición del ítem en el ViewPager2.
     * @return El fragmento correspondiente a la posición.
     */
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return categoryIngresoFragment;
            case 1:
                return categoryGastoFragment;
            default:
                return categoryIngresoFragment;
        }
    }

    /**
     * Devuelve el número total de ítems en el adaptador.
     *
     * @return El número total de ítems (en este caso, 2).
     */
    @Override
    public int getItemCount() {
        return 2;
    }

    /**
     * Devuelve el identificador del ítem en la posición dada.
     *
     * @param position La posición del ítem.
     * @return El identificador del ítem.
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * Verifica si el ítem con el identificador dado está contenido en el adaptador.
     *
     * @param itemId El identificador del ítem.
     * @return Verdadero si el ítem está contenido en el adaptador, falso en caso contrario.
     */
    @Override
    public boolean containsItem(long itemId) {
        return itemId >= 0 && itemId < 2;
    }

    /**
     * Devuelve el fragmento de categorías de gasto.
     *
     * @return El fragmento de categorías de gasto.
     */
    public CategoryGasto getCategoryGastoFragment() {
        return categoryGastoFragment;
    }

    /**
     * Devuelve el fragmento de categorías de ingreso.
     *
     * @return El fragmento de categorías de ingreso.
     */
    public CategoryIngreso getCategoryIngresoFragment() {
        return categoryIngresoFragment;
    }
}
