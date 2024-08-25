package com.example.poo_p2_g01_android;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import enums.TipoCategoria;
import enums.TipoRepeticion;
import modelo.movimiento.Categoria;
import modelo.movimiento.Ingreso;
import modelo.movimiento.Movimiento;

/**
 * Actividad para registrar ingresos en la aplicación.
 */
public class RegistrarIngresos extends AppCompatActivity {
    /** Día de la fecha seleccionada. */
    private int dia;

    /** Mes de la fecha seleccionada. */
    private int mes;

    /** Año de la fecha seleccionada. */
    private int anio;

    /** Campo de texto para la fecha de inicio del ingreso. */
    TextView txtInicioFechaIngreso;

    /** Campo de texto para la fecha de fin del ingreso. */
    TextView txtFechaFinIngreso;

    /** Botón para seleccionar la fecha de inicio. */
    Button btnFechaInicio;

    /** Botón para seleccionar la fecha de fin. */
    Button btnFechaFin;

    /** Botón para registrar el ingreso. */
    Button btnRegistrar;

    /** Diálogo para mostrar la selección de fecha. */
    private Dialog dialog;

    /**
     * Inicializa la actividad de registrar ingresos.
     * Configura la interfaz de usuario, incluyendo la selección de fechas y los elementos del formulario.
     * También maneja el ajuste de márgenes de ventana para la vista de la actividad.
     *
     * @param savedInstanceState Estado guardado de la instancia, si está disponible.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registrar_ingresos);
        inicializarSpinnerCategoria();
        inicializarSpinnerRepeticion();
        inicializarReferencias();
        dialogFechaFin();
        RegistrarDatosIngresos();
        retroceso();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.vistaRegistrarIngresos), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }


    /**
     * Configura el evento del botón de retroceso para finalizar la actividad.
     */
    private void retroceso() {
        ImageButton backButton = findViewById(R.id.btnRetrocesoRegistroIngreso);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    /**
     * Inicializa el spinner de categorías de ingreso.
     * Carga las categorías desde una lista y configura el adaptador del spinner.
     * Establece un color diferente para el ítem de selección por defecto.
     */
    private void inicializarSpinnerCategoria() {
        Spinner spinnerIngreso = findViewById(R.id.spinner_ingresos);
        List<Categoria> categorias = new ArrayList<>();
        categorias.add(new Categoria("Seleccione una opción", TipoCategoria.INGRESO));
        categorias.addAll(cargarListaCategorias());
        ArrayAdapter<Categoria> adapter = new ArrayAdapter<Categoria>(this, android.R.layout.simple_spinner_item, categorias) {
            @Override
            public boolean isEnabled(int position) {
                return position != 0;
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView textView = (TextView) view;
                if (position == 0) {
                    textView.setTextColor(getColor(R.color.md_theme_onSurfaceVariant));
                } else {
                    textView.setTextColor(getColor(R.color.md_theme_onBackground));
                }
                return view;
            }
        };
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerIngreso.setAdapter(adapter);
    }

    /**
     * Carga una lista de categorías de tipo ingreso desde un archivo.
     * Lee las categorías desde un archivo y filtra solo las de tipo ingreso.
     *
     * @return Lista de categorías de tipo ingreso.
     */
    private List<Categoria> cargarListaCategorias(){
        List<Categoria> listaCategoriaIngresos = new ArrayList<>();
        try {
            File directorio = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);
            List<Categoria> categoriasDesdeArchivo = Categoria.cargarCategorias(directorio);

            for (Categoria categoria : categoriasDesdeArchivo) {
                if (categoria.getTipoCategoria() == TipoCategoria.INGRESO) {
                    listaCategoriaIngresos.add(categoria);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaCategoriaIngresos;
    }

    /**
     * Inicializa el Spinner para seleccionar la repetición de ingresos.
     * Configura el adaptador con opciones predefinidas y ajusta el color del texto en función de la selección.
     */
    private void inicializarSpinnerRepeticion() {
        Spinner spinnerRepeticion = findViewById(R.id.spinner_ingresos_repeticion);
        String[] repeticiones = {"Seleccione una opción", "Sin Repetición", "Mes"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, repeticiones) {
            @Override
            public boolean isEnabled(int position) {
                return position != 0;
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView textView = (TextView) view;
                if (position == 0) {
                    textView.setTextColor(getColor(R.color.md_theme_onSurfaceVariant));
                } else {
                    textView.setTextColor(getColor(R.color.md_theme_onBackground));
                }
                return view;
            }
        };
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRepeticion.setAdapter(adapter);
    }

    /**
     * Inicializa referencias a elementos de la UI para gestionar fechas de ingreso.
     */
    private void inicializarReferencias() {
        txtFechaFinIngreso = findViewById(R.id.txtFechaFinIngreso);
        txtInicioFechaIngreso = findViewById(R.id.txtFechaInicioIngreso);

        btnFechaInicio = findViewById(R.id.btnInicioIngreso);
        btnFechaFin = findViewById(R.id.btnFinIngreso);

        btnFechaFin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
            }
        });

        btnFechaInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ventanaFecha(txtInicioFechaIngreso);
            }
        });
    }

    /**
     * Muestra diálogo para seleccionar fecha final.
     */
    private void dialogFechaFin(){
        dialog = new Dialog(RegistrarIngresos.this);
        dialog.setContentView(R.layout.dialog_seleccion_fecha);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.dialog_style));
        dialog.setCancelable(false);

        Button btnNingunaFecha = dialog.findViewById(R.id.btnNinguno);
        Button btnAbrirCalendario = dialog.findViewById(R.id.btnCalendario);

        btnNingunaFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtFechaFinIngreso.setText("Sin Fecha");
                dialog.dismiss();
            }
        });
        btnAbrirCalendario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ventanaFecha(txtFechaFinIngreso);
                dialog.dismiss();
            }
        });
    }

    /**
     * Muestra un diálogo de selección de fecha y actualiza el TextView proporcionado con la fecha elegida en formato AAAA-MM-DD.
     */
    private void ventanaFecha(TextView txtview){
        final Calendar c = Calendar.getInstance();
        dia = c.get(Calendar.DAY_OF_MONTH);
        mes = c.get(Calendar.MONTH);
        anio = c.get(Calendar.YEAR);

        DatePickerDialog datePD = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String dia = String.valueOf(dayOfMonth);
                String mes = String.valueOf(month+1);
                dia = dia.length() < 2 ? dia = "0" + dia : dia;
                mes = mes.length() < 2 ? mes = "0" + mes : mes;
                txtview.setText(String.format("%s-%s-%s",year,mes,dia));
            }
        }, anio, mes, dia);
        datePD.show();
    }


    /**
     * Verifica y convierte una cadena de texto en formato de fecha válida.
     *
     * @param fecha Cadena de texto que representa una fecha en formato "yyyy-MM-dd".
     * @return Objeto LocalDate correspondiente a la fecha parseada, o null si no se puede convertir.
     */
    private LocalDate verificarFecha(String fecha){
        try{
            DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate date = LocalDate.parse(fecha, formato);
            return date;
        }catch (DateTimeParseException e) {
            return null;
        }
    }

    /**
     * Convierte un nombre de tipo de categoría en un tipo de repetición
     *
     * @param nombreTipoCategoria Nombre del tipo de categoría a convertir.
     * @return Tipo de repetición correspondiente al nombre proporcionado.
     */
    public TipoRepeticion convertirTipoCategoriaRepeticion(String nombreTipoCategoria){
        try{
            if(nombreTipoCategoria.equals("Sin Repetición")) {
                nombreTipoCategoria = "SIN_REPETICION";
            }
            return TipoRepeticion.valueOf(nombreTipoCategoria.toUpperCase());
        } catch(IllegalArgumentException e){
            return null;
        }
    }

    /**
     * Convertir un valor String a Double si es numero.
     * @param valor valor ingresado por el usuario a ser convertido.
     * @return un objeto {@link Double} si es numero, sino null.
     */
    public Double convertirADouble(String valor){
        try{
            return Double.valueOf(valor);
        } catch(NumberFormatException e){
            return null;
        }
    }

    /**
     * Registra un nuevo ingreso.
     *
     * Obtiene datos de la UI, valida y crea un objeto Ingreso.
     * Guarda el ingreso y muestra un mensaje de confirmación o error.
     */
    private void RegistrarDatosIngresos() {
        Context context = this;
        Spinner spinnerCategoria = findViewById(R.id.spinner_ingresos);
        EditText edValorIngreso = findViewById(R.id.edValorIngresos);
        EditText edDescripcionIngreso = findViewById(R.id.edDescripcionIngresos);
        Spinner spinnerRepIngreso = findViewById(R.id.spinner_ingresos_repeticion);

        btnRegistrar = findViewById(R.id.btnActivityRegistrarIngresos);
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //FALTA VALIDACIONES CREO
                Categoria categoria = (Categoria) spinnerCategoria.getSelectedItem();
                Double valor = convertirADouble(edValorIngreso.getText().toString());
                String descripcion = String.valueOf(edDescripcionIngreso.getText()).trim();
                TipoRepeticion tipoRepeticion = convertirTipoCategoriaRepeticion(spinnerRepIngreso.getSelectedItem().toString());
                LocalDate fechaInicio = verificarFecha(txtInicioFechaIngreso.getText().toString());
                LocalDate fechaFin = verificarFecha(txtFechaFinIngreso.getText().toString());

                if (categoria == null || valor == null || valor <= 0  || descripcion.isEmpty() || tipoRepeticion == null || fechaInicio == null) {
                    Toast.makeText(RegistrarIngresos.this, "Por favor complete todos los campos", Toast.LENGTH_SHORT).show();
                    return;
                }

                Ingreso ingreso = new Ingreso(categoria, valor, descripcion, fechaInicio, fechaFin, tipoRepeticion);
                boolean guardado = Movimiento.agregarMovimiento(context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), ingreso);

                if (guardado) {
                    Toast.makeText(RegistrarIngresos.this, "¡INGRESO REGISTRADO!", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(RegistrarIngresos.this, "Error al registrar el ingreso", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * Inicia el registro de ingresos al reanudarse la actividad.
     */
    @Override
    protected void onResume() {
        super.onResume();
        RegistrarDatosIngresos();
    }

}