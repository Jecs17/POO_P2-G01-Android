package com.example.poo_p2_g01_android.ControladorCategoria;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;
import com.example.poo_p2_g01_android.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;
import enums.TipoCategoria;
import modelo.movimiento.Categoria;


public class CategoryActivity extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_category);
        inicializarTabLayout();
        cargarDatos();
        eventoBotonRetroceso();
        eventoBotonAgregarCategoria();


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    // Método para configurar el botón de retroceso
    private void eventoBotonRetroceso() {
        ImageButton backButton = findViewById(R.id.btnAtras);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    //Método donde se configura el evento del boton agregar Categoria
    private void eventoBotonAgregarCategoria(){
        CardView cvAgregarCategoria = findViewById(R.id.cardViewAgregarCategoria);


        cvAgregarCategoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View view1 = LayoutInflater.from(CategoryActivity.this).inflate(R.layout.dialog_seleccion_categorias, null);
                //falta controlar eventos
                TextInputEditText etNombreCategoria = findViewById(R.id.textNombreCategoria);
                Spinner spinner = findViewById(R.id.spinner_categorias);

                AlertDialog alertDialog = new MaterialAlertDialogBuilder(CategoryActivity.this)
                        .setTitle("Agregar Categoría")
                        .setView(view1)
                        .setPositiveButton("Agregar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        }).create();
                alertDialog.show();
            }
        });
    }

    private void inicializarTabLayout() {
        TabLayout tabLayout = findViewById(R.id.tabLCategoria);
        ViewPager2 viewPager2 = findViewById(R.id.viewPager);
        CategoryAdapter categoryAdapter = new CategoryAdapter(this);
        viewPager2.setAdapter(categoryAdapter);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                tabLayout.getTabAt(position).select();
            }
        });
    }

    //escribe el archivo con las categorias.
    private void cargarDatos(){
        boolean guardado = false;
        try {
            guardado = Categoria.crearDatosIniciales(this.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS));
        } catch(Exception e) {
            Log.d("App", "Error al crear los datos iniciales" + e.getMessage());
        }
        if(guardado){
            Log.d("App", "Datos escritos exitosamente, archivo categoria");
        }
    }

    //lee el archivo de categorias de tipo ingreso
    public List<Categoria> LeerDatosIngreso(){
        List<Categoria> lstCategoriaIngreso = new ArrayList<>();
        try {
            List<Categoria> lstCategoria = Categoria.cargarCategorias(this.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS));
            for(Categoria categoria: lstCategoria){
                if(categoria.getTipoCategoria() == TipoCategoria.INGRESO)
                    lstCategoriaIngreso.add(categoria);
            }

        }catch(Exception e){
            Log.d("App", "Error al leer archivo categoria" + e.getMessage());
        }
        return lstCategoriaIngreso;
    }

    //lee el archivo de categorias de tipo gasto
    public List<Categoria> LeerDatosGasto(){
        List<Categoria> lstCategoriaGasto = new ArrayList<>();
        try {
            List<Categoria> lstCategoria = Categoria.cargarCategorias(this.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS));
            for(Categoria categoria: lstCategoria){
                if(categoria.getTipoCategoria() == TipoCategoria.GASTO)
                    lstCategoriaGasto.add(categoria);
            }

        }catch(Exception e){
            Log.d("App", "Error al leer archivo categoria" + e.getMessage());
        }
        return lstCategoriaGasto;
    }

}