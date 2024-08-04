package com.example.poo_p2_g01_android.ControladorCategoria;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.poo_p2_g01_android.R;


public class CategoryIngreso extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_category_ingreso, container, false);
    }
}