package com.example.poo_p2_g01_android.ControladorCategoria;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class CategoryAdapter extends FragmentStateAdapter {

    public CategoryAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if(position == 0) {
            return new CategoryIngreso();
        } else if (position == 1) {
            return new CategoryGasto();
        }
        return new CategoryIngreso();
    }


    @Override
    public int getItemCount() {
        return 2;
    }
}
