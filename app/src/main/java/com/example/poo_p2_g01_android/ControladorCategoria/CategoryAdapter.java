package com.example.poo_p2_g01_android.ControladorCategoria;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class CategoryAdapter extends FragmentStateAdapter {
    private CategoryGasto categoryGastoFragment;
    private CategoryIngreso categoryIngresoFragment;

    public CategoryAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                if (categoryIngresoFragment == null) {
                    categoryIngresoFragment = new CategoryIngreso();
                }
                return categoryIngresoFragment;
            case 1:
                if (categoryGastoFragment == null) {
                    categoryGastoFragment = new CategoryGasto();
                }
                return categoryGastoFragment;
            default:
                return new Fragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    public CategoryGasto getCategoryGastoFragment() {
        return categoryGastoFragment;
    }

    public CategoryIngreso getCategoryIngresoFragment() {
        return categoryIngresoFragment;
    }
}
