package com.example.poo_p2_g01_android;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
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

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import modelo.movimiento.Gasto;
import modelo.movimiento.Ingreso;
import modelo.movimiento.Movimiento;

public class GastoActivity extends AppCompatActivity {

    private List<Gasto> listaGasto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_gasto);

        vistaRegistrarGasto();
        vistaFinalizarGasto();
        retroceso();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.vistaGasto), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    /**
     * Configura el evento del botón de retroceso para finalizar la actividad.
     */
    private void retroceso() {
        ImageButton backButton = findViewById(R.id.btnRegresarGasto);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void vistaRegistrarGasto() {
        Button btnRegistroGasto = findViewById(R.id.btnRegistrarGasto);

        btnRegistroGasto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(GastoActivity.this, RegistrarGastos.class);
                startActivity(i);
            }
        });
    }


    private void vistaFinalizarGasto() {
        Button btnFinalizarGasto = findViewById(R.id.btnFinalizarGasto);

        btnFinalizarGasto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrarDialogoFechaFinGasto();
            }
        });
    }

    private void mostrarDialogoFechaFinGasto() {
        LayoutInflater layoutInflater = getLayoutInflater();
        View dialogCodigoView = layoutInflater.inflate(R.layout.dialog_ingresar_codigo, null);

        TextInputEditText txtCodigo = dialogCodigoView.findViewById(R.id.textCodigoUnico);

        AlertDialog alertDialog = new MaterialAlertDialogBuilder(GastoActivity.this)
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
                            int codigoGasto = Integer.parseInt(codigoTexto);
                            List<Gasto> gastos = cargarListaGastos();

                            if (verificarCodigoExiste(codigoGasto, gastos)) {

                                procesarGastoSeleccionado(codigoGasto);
                                dialogInterface.dismiss();
                            } else {
                                Toast.makeText(GastoActivity.this, "Código no encontrado", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(GastoActivity.this, "Debe ingresar un código", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        alertDialog.show();
    }

    private boolean verificarCodigoExiste(int codigoGasto, List<Gasto> gastos) {
        for (Gasto gasto : gastos) {
            if (gasto.getCodigoUnico() == codigoGasto) {
                return true;
            }
        }
        return false;
    }

    private void procesarGastoSeleccionado(int codigoSeleccionado) {
        Gasto gastoSeleccionado = buscarGastoPorCodigo(codigoSeleccionado);
        if (gastoSeleccionado != null) {
            mostrarDialogoDatosGasto(gastoSeleccionado);
        } else {
            Toast.makeText(GastoActivity.this, "Gasto no encontrado", Toast.LENGTH_SHORT).show();
        }
    }

    private void mostrarDialogoDatosGasto(Gasto gastoSeleccionado) {
        LinearLayout linearLayout = crearLayoutDatosGasto(gastoSeleccionado);

        AlertDialog alertDialog1 = new MaterialAlertDialogBuilder(GastoActivity.this)
                .setTitle("Datos del gasto")
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
                        boolean esFechaMayor = esFechaMayor(gastoSeleccionado,linearLayout);
                        if(esFechaMayor) {
                            AlertDialog alertDialog = new MaterialAlertDialogBuilder(GastoActivity.this)
                                    .setTitle("ALERTA")
                                    .setMessage("Si modifica el gasto no se tomará en cuenta para fecha posteriores en el reporte")
                                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            modificarGasto(gastoSeleccionado, linearLayout);
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

    private boolean esFechaMayor(Gasto gastoSeleccionado, LinearLayout linearLayout) {
        boolean esMayor;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        TextView textFechaFin = linearLayout.findViewById(R.id.txtFechaFinLayout);
        LocalDate fechaFinSeleccionada = LocalDate.parse(textFechaFin.getText().toString(), formatter);
        LocalDate fechaInicio = gastoSeleccionado.getFechaInicio();
        LocalDate fechaFinOriginal = LocalDate.parse(gastoSeleccionado.getFechaFin(), formatter);
        if (!fechaFinSeleccionada.equals(fechaFinOriginal)) {
            if (fechaFinSeleccionada.isAfter(fechaInicio)) {
                esMayor = true;
            } else {
                esMayor = false;
                Toast.makeText(GastoActivity.this, "Fecha fin debe ser mayor a la fecha inicio", Toast.LENGTH_SHORT).show();
            }
        } else {
            esMayor = false;
            Toast.makeText(GastoActivity.this, "Ingrese la fecha fin nueva", Toast.LENGTH_SHORT).show();
        }
        return esMayor;
    }


    private LinearLayout crearLayoutDatosGasto(Gasto gastoSeleccionado) {
        LinearLayout linearLayout = new LinearLayout(GastoActivity.this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setPadding(70, 40, 40, 40);

        TextView codigo = crearTextView("Código: " + gastoSeleccionado.getCodigoUnico());
        TextView valorNeto = crearTextView("Valor Neto: " + gastoSeleccionado.getValorNeto());
        TextView descripcion = crearTextView("Descripción: " + gastoSeleccionado.getDescripcion());
        TextView repeticion = crearTextView("Repetición: " + gastoSeleccionado.getRepeticion());
        TextView fechaInicio = crearTextView("Fecha Inicio: " + gastoSeleccionado.getFechaInicio());
        TextView fechaFin = crearTextView("Fecha Fin: " + gastoSeleccionado.getFechaFin());

        LinearLayout linearLayout1 = new LinearLayout(GastoActivity.this);
        linearLayout1.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout1.setPadding(0, 20, 40, 20);

        LayoutInflater inflater = getLayoutInflater();
        View viewBtn = inflater.inflate(R.layout.dialog_button_fecha_fin, null);

        Button btnFechaFin = viewBtn.findViewById(R.id.btnFinLayout);
        TextView textFechaFin = viewBtn.findViewById(R.id.txtFechaFinLayout);
        textFechaFin.setText(gastoSeleccionado.getFechaFin());

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

    private TextView crearTextView(String texto) {
        TextView textView = new TextView(GastoActivity.this);
        textView.setText(texto);
        textView.setPadding(0, 20, 0, 20);
        return textView;
    }

    private void modificarGasto(Gasto gastoSeleccionado, LinearLayout linearLayout) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        TextView textFechaFin = linearLayout.findViewById(R.id.txtFechaFinLayout);
        LocalDate fechaFinSeleccionada = LocalDate.parse(textFechaFin.getText().toString(), formatter);

        gastoSeleccionado.setFechaFin(fechaFinSeleccionada);
        boolean actualizado = Movimiento.actualizarMovimiento(GastoActivity.this.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), gastoSeleccionado);
        if (actualizado) {
            llenarTabla();
            Toast.makeText(GastoActivity.this, "Gasto modificado con éxito", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(GastoActivity.this, "Error al modificar el gasto", Toast.LENGTH_SHORT).show();
        }
    }

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


    @Override
    protected void onResume() {
        super.onResume();
        llenarTabla();
    }

    private List<Gasto> cargarListaGastos() {
        List<Gasto> listaGasto = new ArrayList<>();
        try {
            List<Movimiento> listaMovimiento = Movimiento.cargarMovimientos(this.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS));
            Log.d("Gasto Activity", listaMovimiento.toString());
            for(Movimiento movimiento: listaMovimiento) {
                if(movimiento instanceof Gasto) {
                    Gasto gasto = (Gasto) movimiento;
                    listaGasto.add(gasto);
                }
            }
        } catch(Exception e){
            e.printStackTrace();
        }
        return listaGasto;
    }

    private void cleanTable(TableLayout table) {
        int childCount = table.getChildCount();
        if (childCount > 1) {
            table.removeViews(1, childCount - 1);
        }
    }

    private void llenarTabla() {
        listaGasto = cargarListaGastos();
        Log.d("Gasto Activity", "Número de gastos cargados: " + listaGasto.size());
        TableLayout tableLayout = findViewById(R.id.tablaGasto);
        cleanTable(tableLayout);

        for (Gasto gasto: listaGasto) {
            TableRow tr = new TableRow(this);
            tr.setPadding(0, 15, 0, 10);

            TextView tvCodigo = new TextView(this);
            tvCodigo.setTextColor(ContextCompat.getColor(this, R.color.md_theme_background));
            tvCodigo.setPadding(45,15,15,10);
            tvCodigo.setText(gasto.getCodigoUnico()+"");

            TextView fechaInicio = new TextView(this);
            fechaInicio.setTextColor(ContextCompat.getColor(this, R.color.md_theme_background));
            fechaInicio.setPadding(30,15,15,10);
            fechaInicio.setText(gasto.getFechaInicio().toString());

            TextView categoria = new TextView(this);
            categoria.setTextColor(ContextCompat.getColor(this, R.color.md_theme_background));
            categoria.setText(gasto.getCategoria().toString());
            categoria.setPadding(45,15,15,10);

            TextView valor = new TextView(this);
            valor.setTextColor(ContextCompat.getColor(this, R.color.md_theme_background));
            valor.setText(String.valueOf(gasto.getValorNeto()));
            valor.setPadding(66,15,45,10);

            TextView descripcion = new TextView(this);
            descripcion.setTextColor(ContextCompat.getColor(this, R.color.md_theme_background));
            descripcion.setText(gasto.getDescripcion());
            descripcion.setPadding(60,15,90,10);

            TextView textViewFinalizar = new TextView(this);
            textViewFinalizar.setTextColor(ContextCompat.getColor(this,R.color.md_theme_background));
            textViewFinalizar.setText(gasto.getFechaFin());
            textViewFinalizar.setPadding(0,15,15,10);

            TextView tipoRepeticion = new TextView(this);
            tipoRepeticion.setTextColor(ContextCompat.getColor(this, R.color.md_theme_background));
            tipoRepeticion.setText(gasto.getRepeticion());
            tipoRepeticion.setPadding(100,15,15,10);

            ImageButton btnEliminar = new ImageButton(this);
            btnEliminar.setAdjustViewBounds(true);
            btnEliminar.setMaxWidth(100);
            btnEliminar.setMaxHeight(100);
            btnEliminar.setPadding(5, 10, 5, 10);
            btnEliminar.setBackgroundResource(R.drawable.buttonstyle);
            btnEliminar.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.x_mark));

            btnEliminar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog alertDialog = new MaterialAlertDialogBuilder(GastoActivity.this)
                            .setTitle("ALERTA")
                            .setMessage("¿Seguro de eliminar el gasto?")
                            .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    eliminarGasto(gasto);
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

    private void eliminarGasto(Gasto gasto) {
        boolean eliminado = Movimiento.eliminarMovimiento(this.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), gasto);
        if (eliminado) {
            listaGasto.remove(gasto); //elimina el ingreso usando el metodo equals lo elimina por codigo único
            Log.d("Gasto Activity", "Gasto eliminado: ");
        } else {
            Log.d("Gasto Activity", "Error al eliminar el gasto: ");
        }
    }

    public Gasto buscarGastoPorCodigo(int codigo) {
        for (Gasto gasto : listaGasto) {
            if (gasto.getCodigoUnico() == codigo) {
                return gasto;
            }
        }
        return null;
    }
}