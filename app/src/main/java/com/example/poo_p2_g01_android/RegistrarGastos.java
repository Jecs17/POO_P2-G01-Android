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
import modelo.movimiento.Gasto;
import modelo.movimiento.Ingreso;
import modelo.movimiento.Movimiento;

public class RegistrarGastos extends AppCompatActivity {
    private int dia, mes, anio;
    TextView txtInicioFechaGasto, txtFechaFinGasto;
    Button btnFechaInicio, btnFechaFin, btnRegistrar;
    private Dialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registrar_gastos);
        inicializarSpinnerCategoria();
        inicializarSpinnerRepeticion();
        inicializarReferencias();
        dialogFechaFin();
        RegistrarDatosGastos();
        retroceso();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.vistaRegistrarGastos), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    /**
     * Configura el evento del botón de retroceso para finalizar la actividad.
     */
    private void retroceso() {
        ImageButton backButton = findViewById(R.id.btnRetrocesoRegistroGasto);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void inicializarSpinnerCategoria() {
        Spinner spinnerGasto = findViewById(R.id.spinner_gastos);
        List<Categoria> categorias = new ArrayList<>();
        categorias.add(new Categoria("Seleccione una opción", TipoCategoria.GASTO));
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
                    textView.setTextColor(Color.GRAY);
                } else {
                    textView.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGasto.setAdapter(adapter);

    }


    private List<Categoria> cargarListaCategorias(){
        List<Categoria> listaCategoriaGastos = new ArrayList<>();
        try {
            File directorio = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);
            List<Categoria> categoriasDesdeArchivo = Categoria.cargarCategorias(directorio);

            for (Categoria categoria : categoriasDesdeArchivo) {
                if (categoria.getTipoCategoria() == TipoCategoria.GASTO) {
                    listaCategoriaGastos.add(categoria);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaCategoriaGastos;
    }

    private void inicializarSpinnerRepeticion() {
        Spinner spinnerRepeticion = findViewById(R.id.spinner_gastos_repeticion);
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
                    textView.setTextColor(Color.GRAY);
                } else {
                    textView.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRepeticion.setAdapter(adapter);
    }

    private void inicializarReferencias() {
        txtFechaFinGasto = findViewById(R.id.txtFechaFinGasto);
        txtInicioFechaGasto = findViewById(R.id.txtFechaInicioGasto);

        btnFechaInicio = findViewById(R.id.btnInicioGasto);
        btnFechaFin = findViewById(R.id.btnFinGasto);

        btnFechaFin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
            }
        });

        btnFechaInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ventanaFecha(txtInicioFechaGasto);
            }
        });

    }

    private void dialogFechaFin(){
        dialog = new Dialog(RegistrarGastos.this);
        dialog.setContentView(R.layout.dialog_seleccion_fecha);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.dialog_style));
        dialog.setCancelable(false);

        Button btnNingunaFecha = dialog.findViewById(R.id.btnNinguno);
        Button btnAbrirCalendario = dialog.findViewById(R.id.btnCalendario);

        btnNingunaFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtFechaFinGasto.setText("Sin Fecha");
                dialog.dismiss();
            }
        });
        btnAbrirCalendario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ventanaFecha(txtFechaFinGasto);
                dialog.dismiss();
            }
        });
    }

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


    private void RegistrarDatosGastos() {
        Context context = this;
        Spinner spinnerCategoria = findViewById(R.id.spinner_gastos);
        EditText edValorGasto = findViewById(R.id.edValorGastos);
        EditText edDescripcionGasto = findViewById(R.id.edDescripcionGastos);
        Spinner spinnerRepGasto = findViewById(R.id.spinner_gastos_repeticion);

        btnRegistrar = findViewById(R.id.btnActivityRegistrarGastos);
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //FALTA VALIDACIONES CREO
                Categoria categoria = (Categoria) spinnerCategoria.getSelectedItem();
                Double valor = convertirADouble(edValorGasto.getText().toString());
                String descripcion = String.valueOf(edDescripcionGasto.getText()).trim();
                TipoRepeticion tipoRepeticion = convertirTipoCategoriaRepeticion(spinnerRepGasto.getSelectedItem().toString());
                LocalDate fechaInicio = verificarFecha(txtInicioFechaGasto.getText().toString());
                LocalDate fechaFin = verificarFecha(txtFechaFinGasto.getText().toString());

                if (categoria == null || valor == null || valor <= 0  || descripcion.isEmpty() || tipoRepeticion == null || fechaInicio == null) {
                    Toast.makeText(RegistrarGastos.this, "Por favor complete todos los campos", Toast.LENGTH_SHORT).show();
                    return;
                }

                Gasto gasto = new Gasto(categoria, valor, descripcion, fechaInicio, fechaFin, tipoRepeticion);
                boolean guardado = Movimiento.agregarMovimiento(context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), gasto);

                if (guardado) {
                    Toast.makeText(RegistrarGastos.this, "¡GASTO REGISTRADO!", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(RegistrarGastos.this, "Error al registrar el gasto", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        RegistrarDatosGastos();
    }

}