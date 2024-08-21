package com.example.poo_p2_g01_android.ControladorCategoria;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

import com.example.poo_p2_g01_android.IngresoActivity;
import com.example.poo_p2_g01_android.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputEditText;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import enums.TipoCategoria;
import modelo.movimiento.Categoria;

/**
 * Activity para gestionar las categorías de ingresos y gastos.
 * Proporciona funcionalidades para agregar, eliminar categorías y navegar entre ellas.
 */
public class CategoryActivity extends AppCompatActivity  {
    /**
     * Adaptador utilizado para gestionar y mostrar las categorías en el ViewPager2.
     */
    CategoryAdapter categoryAdapter;

    /**
     * Se llama cuando la actividad es creada. Inicializa los componentes de la interfaz de usuario y
     * configura los eventos de los botones.
     *
     * @param savedInstanceState El estado guardado de la instancia, si está disponible.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_category);
        inicializarTabLayout();
        cargarDatos();
        eventoBotonRetroceso();
        eventoBotonAgregarCategoria();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.vistaCategory), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    /**
     * Configura el evento del botón de retroceso para finalizar la actividad.
     */
    private void eventoBotonRetroceso() {
        ImageButton backButton = findViewById(R.id.btnAtras);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    /**
     * Configura el evento del botón para agregar una categoría.
     * Muestra un diálogo para agregar una nueva categoría.
     */
    private void eventoBotonAgregarCategoria() {
        CardView cvAgregarCategoria = findViewById(R.id.cardViewAgregarCategoria);
        File directorio = this.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);

        cvAgregarCategoria.setOnClickListener(view -> {
            CategoryGasto categoryGastoFrag = categoryAdapter.getCategoryGastoFragment();
            CategoryIngreso categoryIngresoFrag = categoryAdapter.getCategoryIngresoFragment();
            mostrarDialogoAgregarCategoria(directorio, categoryGastoFrag, categoryIngresoFrag);
        });
    }

    /**
     * Muestra un diálogo para agregar una nueva categoría.
     *
     * @param directorio El directorio donde se guardarán los datos de la categoría.
     * @param categoryGastoFrag Fragmento que maneja las categorías de gasto.
     * @param categoryIngresoFrag Fragmento que maneja las categorías de ingreso.
     */
    private void mostrarDialogoAgregarCategoria(File directorio, CategoryGasto categoryGastoFrag, CategoryIngreso categoryIngresoFrag) {
        View dialogView = LayoutInflater.from(CategoryActivity.this).inflate(R.layout.dialog_seleccion_categorias, null);
        Spinner spinnerCategoria = dialogView.findViewById(R.id.spinner_categorias);
        TextInputEditText etNombreCategoria = dialogView.findViewById(R.id.textNombreCategoria);
        ViewPager2 viewPager2 = findViewById(R.id.viewPager);
        int posicionActual =  viewPager2.getCurrentItem();
        inicializarSpinner(spinnerCategoria, posicionActual);

        AlertDialog alertDialog = new MaterialAlertDialogBuilder(CategoryActivity.this)
                .setTitle("Agregar Categoría")
                .setView(dialogView)
                .setPositiveButton("Agregar", null)
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .create();

        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                Button btnAgregar = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
                btnAgregar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(!etNombreCategoria.getText().toString().isEmpty()) {
                            boolean siExiste = agregarCategoria(directorio, spinnerCategoria, etNombreCategoria, categoryGastoFrag, categoryIngresoFrag);
                            if(!siExiste) {
                                Toast.makeText(CategoryActivity.this, "!Ingreso registrado!", Toast.LENGTH_SHORT).show();
                                dialogInterface.dismiss();
                            }
                        }
                        else {
                            Toast.makeText(CategoryActivity.this, "Ingrese categoría", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

      alertDialog.show();

        Button positiveButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
        if (positiveButton != null) {
            positiveButton.setTextSize(18);
        }

        Button negativeButton = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE);
        if (negativeButton != null) {
            negativeButton.setTextSize(18);
        }
    }

    /**
     * Agrega una nueva categoría a la lista y guarda los datos.
     *
     * @param directorio El directorio donde se guardarán los datos de la categoría.
     * @param spinnerCategoria El spinner que contiene el tipo de categoría.
     * @param etNombreCategoria El campo de texto con el nombre de la categoría.
     * @param categoryGastoFrag Fragmento que maneja las categorías de gasto.
     * @param categoryIngresoFrag Fragmento que maneja las categorías de ingreso.
     */
    private boolean agregarCategoria(File directorio, Spinner spinnerCategoria, TextInputEditText etNombreCategoria, CategoryGasto categoryGastoFrag, CategoryIngreso categoryIngresoFrag) {
        String nombreCategoria = String.valueOf(etNombreCategoria.getText()).trim();
        TipoCategoria tipoCategoria = (TipoCategoria) spinnerCategoria.getSelectedItem();
        boolean siExiste = false;
        if (!nombreCategoria.isEmpty() && tipoCategoria != null) {
            Categoria nuevaCategoria = new Categoria(nombreCategoria, tipoCategoria);
            siExiste = guardarCategoria(directorio, nuevaCategoria, categoryGastoFrag, categoryIngresoFrag);

        }
        return siExiste;
    }

    /**
     * Guarda una nueva categoría en el archivo y actualiza la vista.
     *
     * @param directorio El directorio donde se guardarán los datos de la categoría.
     * @param nuevaCategoria La nueva categoría a guardar.
     * @param categoryGastoFrag Fragmento que maneja las categorías de gasto.
     * @param categoryIngresoFrag Fragmento que maneja las categorías de ingreso.
     */
    private boolean guardarCategoria(File directorio, Categoria nuevaCategoria, CategoryGasto categoryGastoFrag, CategoryIngreso categoryIngresoFrag) {
        boolean siExiste;
        try {
            siExiste = validarCategoriaSiExiste(nuevaCategoria);
            if(!siExiste) {
                Categoria.actualizarDatos(directorio, nuevaCategoria);

                actualizarVista(categoryIngresoFrag, categoryGastoFrag, nuevaCategoria);

            }
        } catch (Exception e) {
            siExiste = false;
            Log.d("Category Activity", "Error al escribir el archivo");
        }
        return siExiste;
    }

    /**
     * Actualiza la vista de las categorías en los fragmentos y notifica al adaptador.
     *
     * @param categoryIngresoFrag Fragmento que maneja las categorías de ingreso.
     * @param categoryGastoFrag Fragmento que maneja las categorías de gasto.
     * @param nuevaCategoria La nueva categoría agregada.
     */
    private void actualizarVista(CategoryIngreso categoryIngresoFrag, CategoryGasto categoryGastoFrag, Categoria nuevaCategoria){
        if (categoryGastoFrag != null && nuevaCategoria.getTipoCategoria() == TipoCategoria.GASTO) {
            categoryGastoFrag.agregarCategoriaG(nuevaCategoria);
            categoryAdapter.notifyDataSetChanged();
        } else if (categoryIngresoFrag != null && nuevaCategoria.getTipoCategoria() == TipoCategoria.INGRESO) {
            categoryIngresoFrag.agregarCategoriaI(nuevaCategoria);
            categoryAdapter.notifyDataSetChanged();
        }
    }


    /**
     * Valida si una categoría ya existe en el archivo.
     *
     * @param nuevaCategoria La categoría a validar.
     * @return `true` si la categoría ya existe, `false` en caso contrario.
     */
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

    /**
     * Inicializa el TabLayout y ViewPager2 para la navegación entre pestañas.
     */
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

    /**
     * Carga los datos iniciales de las categorías desde el archivo.
     */
    private void cargarDatos(){
        boolean guardado = false;
        File directorio = this.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);
        try {
            guardado = Categoria.crearDatosIniciales(directorio);
        } catch(Exception e) {
            Log.d("App", "Error al crear los datos iniciales" + e.getMessage());
        }
        if(guardado){
            Log.d("App", "Datos escritos exitosamente, archivo categoria");
        }
    }

    /**
     * Lee las categorías del archivo filtrando por el tipo especificado.
     *
     * @param tipoCategoria El tipo de categoría para filtrar.
     * @return Una lista de categorías que coinciden con el tipo especificado.
     */
    public static List<Categoria> leerDatosPorTipo(TipoCategoria tipoCategoria, Context context) {
        List<Categoria> lstCategoriaPorTipo = new ArrayList<>();
        try {
            List<Categoria> lstCategoria = Categoria.cargarCategorias(context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS));
            for (Categoria categoria : lstCategoria) {
                if (categoria.getTipoCategoria() == tipoCategoria)
                    lstCategoriaPorTipo.add(categoria);
            }
        } catch (Exception e) {
            Log.d("App", "Error al leer archivo de categoría: " + e.getMessage());
        }
        return lstCategoriaPorTipo;
    }

    /**
     * Lee las categorías de tipo ingreso desde el archivo.
     *
     * @return Una lista de categorías de tipo ingreso.
     */
    public static List<Categoria> leerDatosIngreso(Context context) {
        return leerDatosPorTipo(TipoCategoria.INGRESO, context);
    }

    /**
     * Lee las categorías de tipo gasto desde el archivo.
     *
     * @return Una lista de categorías de tipo gasto.
     */
    public static List<Categoria> leerDatosGasto(Context context) {
        return leerDatosPorTipo(TipoCategoria.GASTO, context);
    }

    /**
     * Lista de todas las categorias existentes;
     * @param context context donde es utilizado
     * @return lista de objetos categorias
     */
    public static List<Categoria> listaCategoriasUnidas(Context context) {
        List<Categoria> listaCategoria = new ArrayList<>();
        listaCategoria.addAll(leerDatosIngreso(context));
        listaCategoria.addAll(leerDatosGasto(context));
        return listaCategoria;
    }

    /**
     * Inicializa el Spinner con las opciones disponibles para las categorías.
     *
     * @param spinner El Spinner que se debe inicializar.
     */
    private void inicializarSpinner(Spinner spinner, int valor) {
        List<TipoCategoria> opciones = new ArrayList<>();
        if (valor == 0) {
            opciones.add(TipoCategoria.INGRESO);
        } else if (valor == 1) {
            opciones.add(TipoCategoria.GASTO);
        }
        ArrayAdapter<TipoCategoria> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, opciones);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
        spinner.setAdapter(adapter);
    }

    /**
     * Busca una categoría por su nombre dentro de la lista de categorías.
     *
     * @param nombre El nombre de la categoría a buscar.
     * @return La categoría encontrada o null si no se encuentra.
     */
    public static Categoria buscarCategoriaPorNombre(String nombre, Context context){
        List<Categoria> listaCategoria = CategoryActivity.listaCategoriasUnidas(context);
        for(Categoria categoria: listaCategoria){
            if(categoria!=null && categoria.getNombre().equalsIgnoreCase(nombre)){
                return categoria;
            }
        }
        return null;
    }
}