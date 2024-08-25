package com.example.poo_p2_g01_android;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.File;
import java.time.LocalDate;
import java.util.Calendar;

import modelo.banco.Banco;
import modelo.cuenta.CuentaFinanciera;
import modelo.cuenta.CuentaxCobrar;
import modelo.cuenta.CuentaxPagar;
import modelo.persona.Persona;

/**
 * Esta clase gestiona el registro de cuentas financieras, tanto cuentas por cobrar como cuentas por pagar.
 * Dependiendo del tipo de cuenta, los campos disponibles para el usuario cambiarán.
 *
 * @author Grupo1
 */
public class RegistrarCuentasFinancieras extends AppCompatActivity implements View.OnFocusChangeListener, View.OnClickListener {

    private TextView txtFechaPrestamo;
    private TextView txtFechaInicioPago;
    private TextView txtFechaFinPago;
    private Button btnFechaPrestamo;
    private Button btnFechaInicioPago;
    private Button btnFechaFinPago;
    private int dia, mes, anio;
    private Dialog dialogFecha, dialogRegistrarPB;
    private TextView editFechaPrestamo, editFechaInicio, editFechaFin;
    private EditText editIntereses, editIdentificacion, editCantidad, editDescripcion, editCuota;
    private Button btnRegistrarPersonaBanco, btnRegistrarCF;
    public static boolean esAcreedor = false;
    private final Context context = this;
    private ColorStateList color_mal;
    private ColorStateList color_bien;
    private final boolean[] comprobacion = {false, false, false, false, false, false, false};

    /**
     * Método que se ejecuta al crear la actividad.
     * Configura la interfaz de usuario y los valores iniciales.
     *
     * @param savedInstanceState Estado guardado de la instancia anterior de la actividad.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registrar_cuentas_financieras);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.vistaRegistrarCuentaFinanciera), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        color_mal = ColorStateList.valueOf(this.getColor(R.color.md_theme_errorContainer_mediumContrast));
        color_bien = ColorStateList.valueOf(this.getColor(R.color.md_theme_secondaryFixed_highContrast));
        visualizarBotonPersonaBanco(false);
        cambiarCuenta();
        dialogFechaFin();
        dialogRegistro();
        colocarFecha();
    }

    /**
     * Método que se ejecuta al reanudar la actividad.
     * Configura y verifica los datos necesarios.
     */
    @Override
    protected void onResume() {
        super.onResume();
        obtenerNombreObjeto();
        registrarDatos();
        regresar();
        verificarDatos();
        estadoBoton(comprobacion);
    }

    /**
     * Obtiene el nombre del objeto desde el intent y lo establece en el campo de identificación.
     */
    private void obtenerNombreObjeto(){
        Intent intent = getIntent();
        String nombreObjetoRecuperado = intent.getStringExtra("nombre");
        editIdentificacion.setText(nombreObjetoRecuperado);
        if(nombreObjetoRecuperado != null){
            comprobacion[0] = true;
            editIdentificacion.setBackgroundTintList(color_bien);
        }
    }

    /**
     * Configura la vista y los elementos de la interfaz según el tipo de cuenta (acreedor o deudor).
     */
    private void cambiarCuenta(){
        TextView txtviewIdentificacion = findViewById(R.id.txtviewIdentificacionCF);
        editIdentificacion = findViewById(R.id.txtIdentificacionObjeto);
        TextView txtviewInteres = findViewById(R.id.txtviewInteres);
        editIntereses = findViewById(R.id.txtInteres);
        btnRegistrarPersonaBanco = findViewById(R.id.btnRegistrarPersonaBanco);
        if(!esAcreedor){
            txtviewIdentificacion.setText(R.string.texto_deudor);
            editIdentificacion.setHint(R.string.hint_deudor);
            txtviewInteres.setVisibility(View.GONE);
            editIntereses.setVisibility(View.GONE);
            btnRegistrarPersonaBanco.setText(R.string.btn_deudor);
        }else{
            txtviewIdentificacion.setText(R.string.texto_acreedor);
            editIdentificacion.setHint(R.string.hint_acreedor);
            txtviewInteres.setVisibility(View.VISIBLE);
            editIntereses.setVisibility(View.VISIBLE);
            btnRegistrarPersonaBanco.setText(R.string.btn_acreedor);
        }
    }

    /**
     * Configura el botón de regreso para cerrar la actividad cuando se presiona.
     */
    private void regresar(){
        ImageButton btnRegresar = findViewById(R.id.btnRegresarRegistroCF);
        btnRegresar.setOnClickListener(v -> finish());
    }

    /**
     * Configura el diálogo para seleccionar la fecha de fin de pago.
     */
    @SuppressLint("UseCompatLoadingForDrawables")
    private void dialogFechaFin(){
        dialogFecha = new Dialog(RegistrarCuentasFinancieras.this);
        dialogFecha.setContentView(R.layout.dialog_seleccion_fecha);
        dialogFecha.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialogFecha.getWindow().setBackgroundDrawable(getDrawable(R.drawable.dialog_style));
        dialogFecha.setCancelable(false);

        Button btnNingunaFecha = dialogFecha.findViewById(R.id.btnNinguno);
        Button btnAbrirCalendario = dialogFecha.findViewById(R.id.btnCalendario);

        btnNingunaFecha.setOnClickListener(v -> {
            txtFechaFinPago.setText("Sin Fecha");
            dialogFecha.dismiss();
        });
        btnAbrirCalendario.setOnClickListener(v -> {
            ventanaFecha(txtFechaFinPago);
            dialogFecha.dismiss();
        });
    }

    /**
     * Establece las fechas predeterminadas para el préstamo, inicio de pago, y fin de pago.
     */
    private void colocarFecha(){
        txtFechaPrestamo = findViewById(R.id.txtFechaPrestamo);
        txtFechaInicioPago = findViewById(R.id.txtFechaInicioPago);
        txtFechaFinPago = findViewById(R.id.txtFechaFinPago);
        txtFechaPrestamo.setText(LocalDate.now().toString());
        txtFechaInicioPago.setText(LocalDate.now().toString());
        txtFechaFinPago.setText(LocalDate.now().plusDays(7).toString());

        btnFechaPrestamo = findViewById(R.id.btnFechaPrestamo);
        btnFechaInicioPago = findViewById(R.id.btnFechaInicioPago);
        btnFechaFinPago = findViewById(R.id.btnFechaFinPago);
        btnFechaPrestamo.setOnClickListener(this);
        btnFechaInicioPago.setOnClickListener(this);
        btnFechaFinPago.setOnClickListener(this);
    }

    /**
     * Abre un diálogo de selección de fecha y establece la fecha seleccionada en el TextView correspondiente.
     *
     * @param txtview El TextView en el que se establecerá la fecha seleccionada.
     */
    @SuppressLint("UseCompatTextViewDrawableApis")
    private void ventanaFecha(TextView txtview){
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
     * Maneja los eventos de clic en los botones de selección de fecha.
     *
     * @param v La vista que se ha clicado.
     */
    @Override
    public void onClick(View v){
        final Calendar c = Calendar.getInstance();
        dia = c.get(Calendar.DAY_OF_MONTH);
        mes = c.get(Calendar.MONTH);
        anio = c.get(Calendar.YEAR);
        if(v.equals(btnFechaPrestamo)){
            ventanaFecha(txtFechaPrestamo);
        } else if (v.equals(btnFechaInicioPago)) {
            ventanaFecha(txtFechaInicioPago);
        } else if (v.equals(btnFechaFinPago)) {
            dialogFecha.show();
        }
    }

    /**
     * Configura el diálogo para registrar una persona o un banco, según la opción seleccionada.
     */
    @SuppressLint("UseCompatLoadingForDrawables")
    private void dialogRegistro(){
        dialogRegistrarPB = new Dialog(RegistrarCuentasFinancieras.this);
        dialogRegistrarPB.setContentView(R.layout.dialog_registrar_persona_banco);
        dialogRegistrarPB.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialogRegistrarPB.getWindow().setBackgroundDrawable(getDrawable(R.drawable.dialog_style));

        Button btnDPersona = dialogRegistrarPB.findViewById(R.id.btnPersonaDialogo);
        Button btnDBanco = dialogRegistrarPB.findViewById(R.id.btnBancoDialogo);

        btnDPersona.setOnClickListener((v) -> {
            Intent intent = new Intent( RegistrarCuentasFinancieras.this, RegistrarPersonaBanco.class);
            RegistrarPersonaBanco.esPersona = true;
            intent.putExtra("desdeCuenta",true);
            startActivity(intent);
            dialogRegistrarPB.dismiss();
            editIdentificacion.setText("");
            finish();
        });

        btnDBanco.setOnClickListener((v) -> {
            Intent intent = new Intent( RegistrarCuentasFinancieras.this, RegistrarPersonaBanco.class);
            RegistrarPersonaBanco.esPersona = false;
            intent.putExtra("desdeCuenta",true);
            startActivity(intent);
            dialogRegistrarPB.dismiss();
            editIdentificacion.setText("");
            finish();
        });

    }

    /**
     * Muestra u oculta el botón de registrar persona/banco dependiendo del valor del parámetro.
     *
     * @param ver Indica si se debe mostrar o no el botón.
     */
    private void visualizarBotonPersonaBanco(boolean ver){
        btnRegistrarPersonaBanco = findViewById(R.id.btnRegistrarPersonaBanco);
        if(!ver){
            btnRegistrarPersonaBanco.setVisibility(View.GONE);
        }else {
            btnRegistrarPersonaBanco.setVisibility(View.VISIBLE);
            if(!esAcreedor){
                btnRegistrarPersonaBanco.setOnClickListener(v -> {
                    Intent intent = new Intent( RegistrarCuentasFinancieras.this, RegistrarPersonaBanco.class);
                    RegistrarPersonaBanco.esPersona = true;
                    intent.putExtra("desdeCuenta",true);
                    startActivity(intent);
                    editIdentificacion.setText("");
                    finish();
                });
            } else {
                btnRegistrarPersonaBanco.setOnClickListener(v -> dialogRegistrarPB.show());
            }

        }
    }

    /**
     * Configura los datos ingresados y guarda la cuenta financiera cuando el usuario presiona el botón de registro.
     */
    private void registrarDatos(){
        editIdentificacion = findViewById(R.id.txtIdentificacionObjeto);
        editCantidad = findViewById(R.id.txtCantidad);
        editDescripcion = findViewById(R.id.txtDescripcion);
        editCuota = findViewById(R.id.txtCuota);
        editIntereses = findViewById(R.id.txtInteres);
        editFechaPrestamo = findViewById(R.id.txtFechaPrestamo);
        editFechaInicio = findViewById(R.id.txtFechaInicioPago);
        editFechaFin = findViewById(R.id.txtFechaFinPago);
        btnRegistrarCF = findViewById(R.id.btnRegistrarCF);

        btnRegistrarCF.setOnClickListener(v -> {
            double valor = Double.parseDouble(editCantidad.getText().toString());
            String descripcion = editDescripcion.getText().toString();
            double cuota = Double.parseDouble(editCuota.getText().toString());
            LocalDate fechaPrestamo = LocalDate.parse(editFechaPrestamo.getText().toString());
            LocalDate fechaInicio = LocalDate.parse(editFechaInicio.getText().toString());
            String stringFechaFin = editFechaFin.getText().toString();
            LocalDate fechaFin = null;
            if(!stringFechaFin.equals("Sin Fecha")){
                fechaFin = LocalDate.parse(editFechaFin.getText().toString());
            }
            String identificacion = editIdentificacion.getText().toString();
            boolean guardado = false;
            File file = context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);
            if(!esAcreedor){
                Persona deudor = PersonaBancoActivity.buscarPersona(editIdentificacion.getText().toString(), context);
                if(deudor != null){
                    CuentaxCobrar cuenta = new CuentaxCobrar(valor,descripcion,fechaPrestamo,cuota,fechaInicio,fechaFin,deudor);
                    guardado = CuentaFinanciera.guardarCuenta(file,cuenta);
                }
            }else{
                double interes = Double.valueOf(editIntereses.getText().toString());
                Persona acreedorP = PersonaBancoActivity.buscarPersona(identificacion, context);
                Banco acreedorB = PersonaBancoActivity.buscarBanco(identificacion, context);
                CuentaxPagar cuenta = null;
                if(acreedorP != null){
                    cuenta = new CuentaxPagar(valor,descripcion,fechaPrestamo,cuota,fechaInicio,fechaFin,interes,acreedorP);
                }else if(acreedorB != null){
                    cuenta = new CuentaxPagar(valor,descripcion,fechaPrestamo,cuota,fechaInicio,fechaFin,interes,acreedorB);
                }
                guardado = CuentaFinanciera.guardarCuenta(file, cuenta);
            }

            if(guardado){
                Toast.makeText(getApplicationContext(),"Datos guardados", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    /**
     * Verifica la validez de los datos ingresados en los campos de texto y establece los colores de fondo correspondientes.
     */
    private void verificarDatos(){
        editIdentificacion.setBackgroundTintMode(PorterDuff.Mode.ADD);
        editCantidad.setBackgroundTintMode(PorterDuff.Mode.ADD);
        editDescripcion.setBackgroundTintMode(PorterDuff.Mode.ADD);
        editCuota.setBackgroundTintMode(PorterDuff.Mode.ADD);
        editIntereses.setBackgroundTintMode(PorterDuff.Mode.ADD);

        editIdentificacion.setOnFocusChangeListener(this);
        editCantidad.setOnFocusChangeListener(this);
        editDescripcion.setOnFocusChangeListener(this);
        editCuota.setOnFocusChangeListener(this);
        editIntereses.setOnFocusChangeListener(this);

        editCantidad.addTextChangedListener(textWatcher);
        editDescripcion.addTextChangedListener(textWatcher);
        editCuota.addTextChangedListener(textWatcher);
        editIntereses.addTextChangedListener(textWatcher);

        editFechaPrestamo.addTextChangedListener(textWatcher);
        editFechaInicio.addTextChangedListener(textWatcher);
        editFechaFin.addTextChangedListener(textWatcher);
    }

    /**
     * Se activa cuando el foco de un campo de texto cambia, y valida el campo que ha perdido el foco.
     *
     * @param v La vista que ha perdido el foco.
     * @param hasFocus Indica si la vista tiene el foco.
     */
    @Override
    public void onFocusChange(View v, boolean hasFocus){
        if(!hasFocus){
            if(v.equals(editIdentificacion)){
                String identificacion = editIdentificacion.getText().toString();
                Persona persona = PersonaBancoActivity.buscarPersona(identificacion, context);
                Log.i("Identificacion", "Valor de persona: " + persona);
                Banco banco = PersonaBancoActivity.buscarBanco(identificacion, context);
                Log.i("Identificacion", "Valor de Banco: " + banco);
                if (persona == null && banco == null) {
                    comprobacion[0] = false;
                    mensajeValidacion(esAcreedor? "Persona o Banco no Registrado": "Persona no Registrada");
                    editIdentificacion.setBackgroundTintList(color_mal);
                    visualizarBotonPersonaBanco(true);
                } else if(persona != null || banco != null){
                    editIdentificacion.setBackgroundTintList(color_bien);
                    visualizarBotonPersonaBanco(false);
                    comprobacion[0] = true;
                }
            }
            if (!comprobacion[1] && v.equals(editCantidad)) {
                mensajeValidacion("Cantidad Vacia");
                editCantidad.setBackgroundTintList(color_mal);
            } else if (!comprobacion[2] && v.equals(editDescripcion)) {
                mensajeValidacion("Descripción Vacia");
                editDescripcion.setBackgroundTintList(color_mal);
            } else if (!comprobacion[3] && v.equals(editCuota)) {
                mensajeValidacion("Cuota Vacia");
                editCuota.setBackgroundTintList(color_mal);
            } else if (!comprobacion[4] && v.equals(editIntereses)) {
                mensajeValidacion("Interes Vacio");
                editIntereses.setBackgroundTintList(color_mal);
            }
        }
    }

    /**
     * Verifica y actualiza el estado de los campos de texto en función de los cambios de texto.
     */
    private final TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            if(editCantidad.hasFocus()){
                boolean nombreVacio = editCantidad.getText().toString().isEmpty();
                if(nombreVacio){
                    editCantidad.setBackgroundTintList(color_mal);
                    comprobacion[1] = false;
                }else {
                    editCantidad.setBackgroundTintList(color_bien);
                    comprobacion[1] = true;
                }
            }

            if(editDescripcion.hasFocus()){
                boolean nombreVacio = editDescripcion.getText().toString().isEmpty();
                if(nombreVacio){
                    editDescripcion.setBackgroundTintList(color_mal);
                    comprobacion[2] = false;
                }else {
                    editDescripcion.setBackgroundTintList(color_bien);
                    comprobacion[2] = true;
                }
            }

            if(editCuota.hasFocus()){
                boolean nombreVacio = editCuota.getText().toString().isEmpty();
                if(nombreVacio){
                    editCuota.setBackgroundTintList(color_mal);
                    comprobacion[3] = false;
                }else {
                    editCuota.setBackgroundTintList(color_bien);
                    comprobacion[3] = true;
                }
            }

            if(editIntereses.hasFocus()){
                boolean nombreVacio = editIntereses.getText().toString().isEmpty();
                if(nombreVacio){
                    editIntereses.setBackgroundTintList(color_mal);
                    comprobacion[4] = false;
                }else {
                    editIntereses.setBackgroundTintList(color_bien);
                    comprobacion[4] = true;
                }
            }

            estadoBoton(comprobacion);
        }

        @SuppressLint("UseCompatTextViewDrawableApis")
        @Override
        public void afterTextChanged(Editable s) {
            ColorStateList color_normal = ColorStateList.valueOf(getColor(R.color.md_theme_onPrimary));
            LocalDate fechaPrestamo = LocalDate.parse(editFechaPrestamo.getText().toString());
            LocalDate fechaInicio = LocalDate.parse(editFechaInicio.getText().toString());
            LocalDate fechaFin = null;
            if(fechaPrestamo.isBefore(fechaInicio) || fechaPrestamo.isEqual(fechaInicio)){
                comprobacion[5] = true;
                btnFechaPrestamo.setCompoundDrawableTintList(color_normal);
            }else{
                comprobacion[5] = false;
                btnFechaPrestamo.setCompoundDrawableTintList(color_mal);
                mensajeValidacion("Fecha de Préstamo o Inicio de Pago Invalido");
            }
            if(!editFechaFin.getText().toString().equals("Sin Fecha")){
                fechaFin = LocalDate.parse(editFechaFin.getText().toString());
                if(fechaFin.isAfter(fechaInicio)){
                    comprobacion[6] = true;
                    btnFechaFinPago.setCompoundDrawableTintList(color_normal);
                }
                else {
                    comprobacion[6] = false;
                    btnFechaFinPago.setCompoundDrawableTintList(color_mal);
                    mensajeValidacion("Fecha de Fin de Pago Invalida");
                }
            }
            estadoBoton(comprobacion);
        }
    };

    /**
     * Habilita o deshabilita el botón de registro de cuentas financieras en función del estado de validación de los campos.
     *
     * @param valores Un array booleano que indica el estado de validación de los campos.
     */
    private void estadoBoton(boolean[] valores){
        if(esAcreedor && valores[0] && valores[1] && valores[2] && valores[3] && valores[5] && valores[6]){
            btnRegistrarCF.setEnabled(true);
        }else if(!esAcreedor && valores[0] && valores[1] && valores[2] && valores[3] && valores[4] && valores[5] && valores[6]){
            btnRegistrarCF.setEnabled(true);
        } else{
            btnRegistrarCF.setEnabled(false);
        }
    }

    /**
     * Muestra un mensaje de validación como un Toast en la pantalla.
     *
     * @param mensaje El mensaje de validación a mostrar.
     */
    private void mensajeValidacion(String mensaje){
        Toast.makeText(this,mensaje,Toast.LENGTH_SHORT).show();
    }
}