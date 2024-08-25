package com.example.poo_p2_g01_android;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import modelo.movimiento.Gasto;
import modelo.movimiento.Ingreso;
import modelo.movimiento.Movimiento;

/**
 * Actividad para el registro de ingresos.
 * <p>
 * Maneja la interfaz de usuario para registrar ingresos, incluyendo la visualización de datos y la interacción con el usuario.
 * </p>
 */
public class IngresoActivity extends AppCompatActivity {

    /**
     * Lista de ingresos que se mostrarán y manejarán en esta actividad.
     */
    private List<Ingreso> listaIngreso;

    /**
     * Campo de entrada de texto para el código de ingreso.
     */
    TextInputEditText txtCodigo;


    /**
     * Inicializa la actividad de registro de ingresos.
     * <p>
     * Configura la vista, los elementos de la interfaz de usuario, y los listeners necesarios para registrar ingresos.
     * También ajusta el padding para tener en cuenta las barras del sistema.
     * </p>
     *
     * @param savedInstanceState Estado guardado de la actividad, si está disponible.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ingreso);

        vistaRegistrarIngreso();
        vistaFinalizarIngreso();
        retroceso();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.vistaIngreso), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    /**
     * Configura el evento del botón de retroceso para finalizar la actividad.
     */
    private void retroceso() {
        ImageButton backButton = findViewById(R.id.btnRegresarIngreso);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    /**
     * Configura el botón para registrar un nuevo ingreso.
     * <p>
     * Asocia un `OnClickListener` al botón de registro de ingresos (`btnRegistrarIngreso`).
     * Cuando el botón es presionado, se inicia una nueva actividad (`RegistrarIngresos`) para registrar un ingreso.
     * </p>
     */
    private void vistaRegistrarIngreso() {
        Button btnRegistroIngreso = findViewById(R.id.btnRegistrarIngreso);

        btnRegistroIngreso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(IngresoActivity.this, RegistrarIngresos.class);
                startActivity(i);
            }
        });
    }

    /**
     * Configura el botón para finalizar un ingreso.
     * <p>
     * Asocia un `OnClickListener` al botón de finalizar ingreso (`btnFinalizarIngreso`).
     * Cuando el botón es presionado, se muestra un diálogo para ingresar la fecha de finalización del ingreso.
     * </p>
     */
    private void vistaFinalizarIngreso() {
        Button btnFinalizarIngreso = findViewById(R.id.btnFinalizarIngreso);

        btnFinalizarIngreso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrarDialogoFechaFinIngreso();
            }
        });
    }

    /**
     * Muestra un diálogo para ingresar un código único asociado a un ingreso.
     * <p>
     * Este método infla una vista personalizada para el diálogo, que incluye un campo de texto para ingresar un código.
     * Se agrega un `TextWatcher` al campo de texto para eliminar los errores cuando el usuario comienza a escribir.
     * El diálogo tiene dos botones: "Aceptar" y "Cancelar".
     * Al presionar "Aceptar", se valida el código ingresado y, si es válido, se procesa el ingreso seleccionado.
     * Si el código es inválido o no se ingresa un código, se muestra un mensaje de error adecuado.
     * </p>
     */
    private void mostrarDialogoFechaFinIngreso() {
        LayoutInflater layoutInflater = getLayoutInflater();
        View dialogCodigoView = layoutInflater.inflate(R.layout.dialog_ingresar_codigo, null);
        txtCodigo = dialogCodigoView.findViewById(R.id.textCodigoUnico);
        TextInputLayout textInputLayout = dialogCodigoView.findViewById(R.id.textInputLayout3);

        txtCodigo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                textInputLayout.setError(null);
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

        AlertDialog alertDialog = new MaterialAlertDialogBuilder(IngresoActivity.this)
                .setTitle("Ingrese código")
                .setView(dialogCodigoView)
                .setPositiveButton("Aceptar", null)
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
                Button btnAceptar = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                btnAceptar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String codigoTexto = txtCodigo.getText().toString();
                        if (!codigoTexto.isEmpty()) {
                            int codigoIngreso = Integer.parseInt(codigoTexto);
                            List<Ingreso> ingresos = cargarListaIngresos();

                            if (verificarCodigoExiste(codigoIngreso, ingresos)) {

                                procesarIngresoSeleccionado(codigoIngreso);
                                dialogInterface.dismiss();
                            } else {
                                validarEntradaSiExiste(textInputLayout);
                            }
                        } else {
                            validarEntrada(textInputLayout);
                        }
                    }
                });
            }
        });

        alertDialog.show();
    }

    /**
     * Muestra mensaje llamativo al usuario
     *
     * @param textInputLayout recibe la referencia de la vista
     */
    private void validarEntrada(TextInputLayout textInputLayout) {
        String input = txtCodigo.getText().toString();
        if (input.isEmpty()) {
            textInputLayout.setError("Ingrese código");
        } else {
            textInputLayout.setError(null);
        }
    }

    /**
     * Muestra mensaje llamativo al usuario
     *
     * @param textInputLayout recibe la referencia de la vista
     */
    private void validarEntradaSiExiste(TextInputLayout textInputLayout) {
        textInputLayout.setError("¡Código no existe!");
    }

    /**
     * Verifica si un código de ingreso está presente en la lista de ingresos.
     *
     * @param codigoIngreso El código único del ingreso.
     * @param ingresos La lista de ingresos a verificar.
     * @return {@code true} si el código existe, {@code false} en caso contrario.
     */
    private boolean verificarCodigoExiste(int codigoIngreso, List<Ingreso> ingresos) {
        for (Ingreso ingreso : ingresos) {
            if (ingreso.getCodigoUnico() == codigoIngreso) {
                return true;
            }
        }
        return false;
    }

    /**
     * Procesa el ingreso con el código dado y muestra sus detalles en un diálogo.
     * Muestra un mensaje de error si el ingreso no se encuentra.
     *
     * @param codigoSeleccionado El código del ingreso a procesar.
     */
    private void procesarIngresoSeleccionado(int codigoSeleccionado) {
        Ingreso ingresoSeleccionado = buscarIngresoPorCodigo(codigoSeleccionado);
        if (ingresoSeleccionado != null) {
            mostrarDialogoDatosIngreso(ingresoSeleccionado);
        } else {
            Toast.makeText(IngresoActivity.this, "Ingreso no encontrado", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Muestra un diálogo con los datos del ingreso seleccionado.
     * Permite modificar el ingreso si se confirma una alerta sobre la fecha.
     *
     * @param ingresoSeleccionado El ingreso cuyas datos se mostrarán en el diálogo.
     */
    private void mostrarDialogoDatosIngreso(Ingreso ingresoSeleccionado) {
        LinearLayout linearLayout = crearLayoutDatosIngreso(ingresoSeleccionado);

        AlertDialog alertDialog1 = new MaterialAlertDialogBuilder(IngresoActivity.this)
                .setTitle("Datos del ingreso")
                .setView(linearLayout)
                .setPositiveButton("Modificar", null)
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .create();

        alertDialog1.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                Button btnModificar = alertDialog1.getButton(DialogInterface.BUTTON_POSITIVE);
                btnModificar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        boolean esFechaMayor = esFechaMayor(ingresoSeleccionado, linearLayout);
                        if(esFechaMayor) {
                            AlertDialog alertDialog = new MaterialAlertDialogBuilder(IngresoActivity.this)
                                    .setTitle("ALERTA")
                                    .setMessage("Si modifica el ingreso no se tomará en cuenta para fecha posteriores en el reporte")
                                    .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            modificarIngreso(ingresoSeleccionado, linearLayout);
                                            dialogInterface.dismiss();
                                        }
                                    }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialogInterface.dismiss();
                                        }
                                    }).create();
                            alertDialog.show();
                            dialogInterface.dismiss();
                        }
                    }
                });
            }
        });
        alertDialog1.show();
    }

    /**
     * Crea un `LinearLayout` que muestra los datos del ingreso seleccionado.
     * Incluye información como código, valor neto, descripción, repetición,
     * fecha de inicio y fecha de fin, y un botón para modificar la fecha de fin.
     *
     * @param ingresoSeleccionado El ingreso cuyas datos se mostrarán en el `LinearLayout`.
     * @return Un `LinearLayout` con los datos del ingreso y un botón para modificar la fecha de fin.
     */
    private LinearLayout crearLayoutDatosIngreso(Ingreso ingresoSeleccionado) {
        LinearLayout linearLayout = new LinearLayout(IngresoActivity.this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setPadding(70, 40, 40, 40);

        TextView codigo = crearTextView("Código: " + ingresoSeleccionado.getCodigoUnico());
        TextView valorNeto = crearTextView("Valor Neto: " + ingresoSeleccionado.getValorNeto());
        TextView descripcion = crearTextView("Descripción: " + ingresoSeleccionado.getDescripcion());
        TextView repeticion = crearTextView("Repetición: " + ingresoSeleccionado.getRepeticion());
        TextView fechaInicio = crearTextView("Fecha Inicio: " + ingresoSeleccionado.getFechaInicio());
        TextView fechaFin = crearTextView("Fecha Fin: " + ingresoSeleccionado.getFechaFin());

        LinearLayout linearLayout1 = new LinearLayout(IngresoActivity.this);
        linearLayout1.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout1.setPadding(0, 20, 40, 20);

        LayoutInflater inflater = getLayoutInflater();
        View viewBtn = inflater.inflate(R.layout.dialog_button_fecha_fin, null);

        Button btnFechaFin = viewBtn.findViewById(R.id.btnFinLayout);
        TextView textFechaFin = viewBtn.findViewById(R.id.txtFechaFinLayout);
        textFechaFin.setText(ingresoSeleccionado.getFechaFin());

        btnFechaFin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ventanaFecha(textFechaFin);
            }
        });

        linearLayout.addView(codigo);
        linearLayout.addView(valorNeto);
        linearLayout.addView(descripcion);
        linearLayout.addView(repeticion);
        linearLayout.addView(fechaInicio);
        linearLayout.addView(fechaFin);

        linearLayout1.addView(viewBtn);

        linearLayout.addView(linearLayout1);

        return linearLayout;
    }

    /**
     * Crea un `TextView` con el texto especificado y un padding vertical.
     *
     * @param texto El texto que se mostrará en el `TextView`.
     * @return Un `TextView` con el texto proporcionado y padding vertical.
     */
    private TextView crearTextView(String texto) {
        TextView textView = new TextView(IngresoActivity.this);
        textView.setText(texto);
        textView.setPadding(0, 20, 0, 20);
        return textView;
    }

    /**
     * Modifica el ingreso seleccionado con los datos actualizados en el `LinearLayout`.
     * Actualiza el ingreso en el archivo de movimientos y muestra un mensaje de éxito o error.
     *
     * @param ingresoSeleccionado El ingreso que se desea modificar.
     * @param linearLayout El `LinearLayout` que contiene los datos actualizados.
     */
    private void modificarIngreso(Ingreso ingresoSeleccionado, LinearLayout linearLayout) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        TextView textFechaFin = linearLayout.findViewById(R.id.txtFechaFinLayout);
        LocalDate fechaFinSeleccionada = LocalDate.parse(textFechaFin.getText().toString(), formatter);

        ingresoSeleccionado.setFechaFin(fechaFinSeleccionada);
        boolean actualizado = Movimiento.actualizarMovimiento(IngresoActivity.this.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), ingresoSeleccionado);
        if (actualizado) {
            llenarTabla();
            Toast.makeText(IngresoActivity.this, "Ingreso modificado con éxito", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(IngresoActivity.this, "Error al modificar el ingreso", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Verifica si la fecha de fin proporcionada es mayor que la fecha de inicio del ingreso seleccionado.
     * Muestra un mensaje de error si la fecha de fin no es válida.
     *
     * @param ingresoSeleccionado El ingreso cuyo rango de fechas se debe verificar.
     * @param linearLayout El `LinearLayout` que contiene la fecha de fin proporcionada.
     * @return {@code true} si la fecha de fin es mayor que la fecha de inicio; {@code false} en caso contrario.
     */
    private boolean esFechaMayor(Ingreso ingresoSeleccionado, LinearLayout linearLayout) {
        boolean esMayor = false;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        TextView textFechaFin = linearLayout.findViewById(R.id.txtFechaFinLayout);

        try {
            LocalDate fechaFinSeleccionada = LocalDate.parse(textFechaFin.getText().toString(), formatter);
            LocalDate fechaInicio = ingresoSeleccionado.getFechaInicio();
            String fechaFinOriginalStr = ingresoSeleccionado.getFechaFin();

            if (fechaFinOriginalStr.equals("No definida")) {
                if (fechaFinSeleccionada.isAfter(fechaInicio)) {
                    esMayor = true;
                } else {
                    Toast.makeText(IngresoActivity.this, "Fecha fin debe ser mayor a la fecha inicio", Toast.LENGTH_SHORT).show();
                }
            } else {
                LocalDate fechaFinOriginal = LocalDate.parse(fechaFinOriginalStr, formatter);

                if (!fechaFinSeleccionada.equals(fechaFinOriginal)) {
                    if (fechaFinSeleccionada.isAfter(fechaInicio)) {
                        esMayor = true;
                    } else {
                        Toast.makeText(IngresoActivity.this, "Fecha fin debe ser mayor a la fecha inicio", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(IngresoActivity.this, "Ingrese la fecha fin nueva", Toast.LENGTH_SHORT).show();
                }
            }
        } catch (DateTimeParseException e) {
            Toast.makeText(IngresoActivity.this, "Formato de fecha inválido. Por favor, ingrese una fecha válida.", Toast.LENGTH_SHORT).show();
        }
        return esMayor;
    }

    /**
     * Muestra un `DatePickerDialog` para seleccionar una fecha y actualiza el `TextView` con la fecha seleccionada.
     *
     * @param txtview El `TextView` que se actualizará con la fecha seleccionada en el formato "yyyy-MM-dd".
     */
    private void ventanaFecha(TextView txtview){
        final Calendar c = Calendar.getInstance();
        int dia = c.get(Calendar.DAY_OF_MONTH);
        int mes = c.get(Calendar.MONTH);
        int anio = c.get(Calendar.YEAR);

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
     * Se llama cuando la actividad se reanuda después de estar en pausa.
     * Este método llama al método `llenarTabla` para actualizar la tabla con datos actuales.
     */
    @Override
    protected void onResume() {
        super.onResume();
        llenarTabla();
    }

    /**
     * Carga una lista de ingresos desde un archivo de movimientos.
     *
     * @return Lista de objetos {@link Ingreso} cargados.
     */
    public List<Ingreso> cargarListaIngresos() {
        List<Ingreso> listaIngreso = new ArrayList<>();
        try {
            List<Movimiento> listaMovimiento = Movimiento.cargarMovimientos(this.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS));
            Log.d("Ingreso Activity", listaMovimiento.toString());
            for(Movimiento movimiento: listaMovimiento) {
                if(movimiento instanceof Ingreso) {
                    Ingreso ingreso = (Ingreso) movimiento;
                    listaIngreso.add(ingreso);
                }
            }
        } catch(Exception e){
            e.printStackTrace();
        }
        return listaIngreso;
    }

    /**
     * Limpia todas las filas de una tabla, excepto la fila de encabezado.
     *
     * @param table El {@link TableLayout} que se va a limpiar.
     */
    public static void cleanTable(TableLayout table) {
        int childCount = table.getChildCount();
        if (childCount > 1) {
            table.removeViews(1, childCount - 1);
        }
    }

    /**
     * Llena la tabla con los ingresos cargados.
     * Cada fila de la tabla muestra los detalles de un ingreso,
     * incluyendo código, fecha de inicio, categoría, valor,
     * descripción, fecha de fin y tipo de repetición.
     * También añade un botón para eliminar cada ingreso,
     * con una confirmación de eliminación.
     */
    private void llenarTabla() {
        listaIngreso = cargarListaIngresos();
        Log.d("IngresoActivity", "Número de ingresos cargados: " + listaIngreso.size());
        TableLayout tableLayout = findViewById(R.id.tablaIngreso);
        cleanTable(tableLayout);

        for (Ingreso ingreso: listaIngreso) {
            TableRow tr = new TableRow(this);
            tr.setPadding(0, 15, 0, 10);

            TextView tvCodigo = new TextView(this);
            tvCodigo.setTextColor(ContextCompat.getColor(this, R.color.md_theme_background));
            tvCodigo.setPadding(45,15,15,10);
            tvCodigo.setText(ingreso.getCodigoUnico()+"");

            TextView fechaInicio = new TextView(this);
            fechaInicio.setTextColor(ContextCompat.getColor(this, R.color.md_theme_background));
            fechaInicio.setPadding(30,15,15,10);
            fechaInicio.setText(ingreso.getFechaInicio().toString());

            TextView categoria = new TextView(this);
            categoria.setTextColor(ContextCompat.getColor(this, R.color.md_theme_background));
            categoria.setText(ingreso.getCategoria().toString());
            categoria.setPadding(45,15,15,10);

            TextView valor = new TextView(this);
            valor.setTextColor(ContextCompat.getColor(this, R.color.md_theme_background));
            valor.setText(String.valueOf(ingreso.getValorNeto()));
            valor.setPadding(66,15,45,10);

            TextView descripcion = new TextView(this);
            descripcion.setTextColor(ContextCompat.getColor(this, R.color.md_theme_background));
            descripcion.setText(ingreso.getDescripcion());
            descripcion.setPadding(60,15,90,10);

            TextView textViewFinalizar = new TextView(this);
            textViewFinalizar.setTextColor(ContextCompat.getColor(this,R.color.md_theme_background));
            textViewFinalizar.setText(ingreso.getFechaFin());
            textViewFinalizar.setPadding(0,15,15,10);

            TextView tipoRepeticion = new TextView(this);
            tipoRepeticion.setTextColor(ContextCompat.getColor(this, R.color.md_theme_background));
            tipoRepeticion.setText(ingreso.getRepeticion());
            tipoRepeticion.setPadding(100,15,15,10);

            ImageButton btnEliminar = new ImageButton(this);
            btnEliminar.setAdjustViewBounds(true);
            btnEliminar.setMaxWidth(100);
            btnEliminar.setMaxHeight(100);
            btnEliminar.setPadding(5, 10, 5, 10);
            btnEliminar.setBackgroundColor(getColor(com.google.android.material.R.color.mtrl_btn_transparent_bg_color));
            btnEliminar.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.trash_can));

            btnEliminar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog alertDialog = new MaterialAlertDialogBuilder(IngresoActivity.this)
                            .setTitle("ALERTA")
                            .setMessage("¿Seguro de eliminar el ingreso?")
                            .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    eliminarIngreso(ingreso);
                                    tableLayout.removeView(tr);
                                    dialogInterface.dismiss();
                                }
                            }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                     dialogInterface.dismiss();
                                }

                            }).create();
                    alertDialog.show();
                }
            });

            tr.addView(tvCodigo);
            tr.addView(fechaInicio);
            tr.addView(categoria);
            tr.addView(valor);
            tr.addView(descripcion);
            tr.addView(textViewFinalizar);
            tr.addView(tipoRepeticion);
            tr.addView(btnEliminar);

            tableLayout.addView(tr);
        }
    }

    /**
     * Elimina un ingreso de la lista y del almacenamiento.
     * Intenta eliminar el ingreso usando el método `eliminarMovimiento`.
     * Si la eliminación es exitosa, también lo elimina de la lista local `listaIngreso`.
     *
     * @param ingreso El objeto `Ingreso` a eliminar.
     */
    private void eliminarIngreso(Ingreso ingreso) {
        boolean eliminado = Movimiento.eliminarMovimiento(this.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), ingreso);
        if (eliminado) {
            listaIngreso.remove(ingreso); //elimina el ingreso usando el metodo equals lo elimina por codigo único
            Log.d("IngresoActivity", "Ingreso eliminado: ");
        } else {
            Log.d("IngresoActivity", "Error al eliminar el ingreso: ");
        }
    }

    /**
     * Busca un ingreso en la lista por su código único.
     * Recorre la lista de ingresos y devuelve el ingreso que coincide con el código proporcionado.
     *
     * @param codigo El código único del ingreso a buscar.
     * @return El objeto `Ingreso` correspondiente al código, o `null` si no se encuentra.
     */
    public Ingreso buscarIngresoPorCodigo(int codigo) {
        for (Ingreso ingreso : listaIngreso) {
            if (ingreso.getCodigoUnico() == codigo) {
                return ingreso;
            }
        }
        return null;
    }
}