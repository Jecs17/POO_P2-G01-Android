package com.example.poo_p2_g01_android.ControladorCategoria;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

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

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import enums.TipoCategoria;
import modelo.movimiento.Categoria;


public class CategoryActivity extends AppCompatActivity  {
    CategoryAdapter categoryAdapter;
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

    private void eventoBotonAgregarCategoria() {
        CardView cvAgregarCategoria = findViewById(R.id.cardViewAgregarCategoria);
        File directorio = this.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);

        cvAgregarCategoria.setOnClickListener(view -> {
            CategoryGasto categoryGastoFrag = categoryAdapter.getCategoryGastoFragment();
            CategoryIngreso categoryIngresoFrag = categoryAdapter.getCategoryIngresoFragment();
            mostrarDialogoAgregarCategoria(directorio, categoryGastoFrag, categoryIngresoFrag);
        });
    }

    private void mostrarDialogoAgregarCategoria(File directorio, CategoryGasto categoryGastoFrag, CategoryIngreso categoryIngresoFrag) {
        View dialogView = LayoutInflater.from(CategoryActivity.this).inflate(R.layout.dialog_seleccion_categorias, null);
        Spinner spinnerCategoria = dialogView.findViewById(R.id.spinner_categorias);
        TextInputEditText etNombreCategoria = dialogView.findViewById(R.id.textNombreCategoria);

        inicializarSpinner(spinnerCategoria);

        AlertDialog alertDialog = new MaterialAlertDialogBuilder(CategoryActivity.this)
                .setTitle("Agregar Categoría")
                .setView(dialogView)
                .setPositiveButton("Agregar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        agregarCategoria(directorio, spinnerCategoria, etNombreCategoria, categoryGastoFrag, categoryIngresoFrag);
                        dialogInterface.dismiss();
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .create();

        alertDialog.show();
    }

    private void agregarCategoria(File directorio, Spinner spinnerCategoria, TextInputEditText etNombreCategoria, CategoryGasto categoryGastoFrag, CategoryIngreso categoryIngresoFrag) {
        String nombreCategoria = String.valueOf(etNombreCategoria.getText()).trim();
        TipoCategoria tipoCategoria = (TipoCategoria) spinnerCategoria.getSelectedItem();

        if (!nombreCategoria.isEmpty() && tipoCategoria != null) {
            Categoria nuevaCategoria = new Categoria(nombreCategoria, tipoCategoria);
            guardarCategoria(directorio, nuevaCategoria, categoryGastoFrag, categoryIngresoFrag);

        }
    }

    private void guardarCategoria(File directorio, Categoria nuevaCategoria, CategoryGasto categoryGastoFrag, CategoryIngreso categoryIngresoFrag) {
        try {
            boolean siExiste = validarCategoriaSiExiste(nuevaCategoria);
            if(!siExiste) {
                Categoria.actualizarDatos(directorio, nuevaCategoria);

                actualizarVista(categoryIngresoFrag, categoryGastoFrag, nuevaCategoria);

                cambiarViewPager2(nuevaCategoria);
                //actualizar adaptador
                if (categoryGastoFrag != null) {
                    categoryGastoFrag.actualizarListaG();
                }
                if (categoryIngresoFrag != null) {
                    categoryIngresoFrag.actualizarListaI();
                }
            }
        } catch (Exception e) {
            Log.d("Category Activity", "Error al escribir el archivo");
        }
    }

    private void actualizarVista(CategoryIngreso categoryIngresoFrag, CategoryGasto categoryGastoFrag, Categoria nuevaCategoria){
        if (categoryGastoFrag != null && nuevaCategoria.getTipoCategoria() == TipoCategoria.GASTO) {
            categoryGastoFrag.agregarCategoriaG(nuevaCategoria);
        } else if (categoryIngresoFrag != null && nuevaCategoria.getTipoCategoria() == TipoCategoria.INGRESO) {
            categoryIngresoFrag.agregarCategoriaI(nuevaCategoria);
        }
    }

    private void cambiarViewPager2(Categoria nuevaCategoria) {
        ViewPager2 viewPager2 = findViewById(R.id.viewPager);
        if (nuevaCategoria.getTipoCategoria() == TipoCategoria.GASTO) {
            viewPager2.setCurrentItem(1);
        } else if (nuevaCategoria.getTipoCategoria() == TipoCategoria.INGRESO) {
            viewPager2.setCurrentItem(0);
        }
    }

    private boolean validarCategoriaSiExiste(Categoria nuevaCategoria) {
        boolean siExiste = false;
        try {
            List<Categoria> lstCategoria = Categoria.cargarCategorias(this.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS));
            for(Categoria categoria: lstCategoria) {
                if (categoria.equals(nuevaCategoria)) {
                    Toast.makeText(this, "¡¡CATEGORIA YA EXISTE!!", Toast.LENGTH_SHORT).show();
                    siExiste = true;
                }
            }
        } catch (Exception e) {
            Log.e("Category Activity", "Error al leer el archivo categorias.ser" + e.getMessage());
        }
        return siExiste;
    }

    private void inicializarTabLayout() {
        TabLayout tabLayout = findViewById(R.id.tabLCategoria);
        ViewPager2 viewPager2 = findViewById(R.id.viewPager);
        categoryAdapter = new CategoryAdapter(this);
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

    // Lee el archivo de categorías según el tipo especificado
    public List<Categoria> leerDatosPorTipo(TipoCategoria tipoCategoria) {
        List<Categoria> lstCategoriaPorTipo = new ArrayList<>();
        try {
            List<Categoria> lstCategoria = Categoria.cargarCategorias(this.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS));
            for (Categoria categoria : lstCategoria) {
                if (categoria.getTipoCategoria() == tipoCategoria)
                    lstCategoriaPorTipo.add(categoria);
            }
        } catch (Exception e) {
            Log.d("App", "Error al leer archivo de categoría: " + e.getMessage());
        }
        return lstCategoriaPorTipo;
    }

    // Lee las categorías de tipo ingreso
    public List<Categoria> leerDatosIngreso() {
        return leerDatosPorTipo(TipoCategoria.INGRESO);
    }

    // Lee las categorías de tipo gasto
    public List<Categoria> leerDatosGasto() {
        return leerDatosPorTipo(TipoCategoria.GASTO);
    }

    private void inicializarSpinner(Spinner spinner) {
        List<TipoCategoria> opciones = obtenerOpcionesSpinner();
        ArrayAdapter<TipoCategoria> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, opciones);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
        spinner.setAdapter(adapter);
    }

    private List<TipoCategoria> obtenerOpcionesSpinner() {
        List<TipoCategoria> opciones = new ArrayList<>();
        opciones.add(TipoCategoria.INGRESO);
        opciones.add(TipoCategoria.GASTO);
        return opciones;
    }
}