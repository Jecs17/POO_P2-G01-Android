package com.example.poo_p2_g01_android;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
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

public class RegistrarCuentasFinancieras extends AppCompatActivity implements View.OnClickListener {

    private TextView txtFechaPrestamo, txtFechaInicioPago, txtFechaFinPago, txtviewInteres, txtviewIdentificacion;
    private Button btnFechaPrestamo, btnFechaInicioPago, btnFechaFinPago, btnNingunaFecha, btnAbrirCalendario, btnDPersona, btnDBanco;
    private int dia, mes, anio;
    private Dialog dialogFecha, dialogRegistrarPB;
    private TextView editFechaPrestamo, editFechaInicio, editFechaFin;
    private EditText editIntereses, editIdentificacion, editCantidad, editDescripcion, editCuota;
    private Button btnRegistrarPersonaBanco, btnRegistrarCF;
    public static boolean esAcreedor = false;
    private Context context = this;
    private String nombreObjetoRecuperado = "";

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
        visualizarBotonPersonaBanco(false);
        cambiarCuenta();
        dialogFechaFin();
        dialogRegistro();
        colocarFecha();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //TODO: REGRESO DEL VALOR DEL NOMBRE DEL OBJETO
        Intent intent = getIntent();
        nombreObjetoRecuperado = intent.getStringExtra("nombre");
        editIdentificacion.setText(nombreObjetoRecuperado);
        //
        comprobarIdentificacion();
        registrarDatos();
    }

    private void cambiarCuenta(){
        txtviewIdentificacion = findViewById(R.id.txtviewIdentificacionCF);
        editIdentificacion = findViewById(R.id.txtIdentificacionObjeto);
        txtviewInteres = findViewById(R.id.txtviewInteres);
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

    private void dialogFechaFin(){
        dialogFecha = new Dialog(RegistrarCuentasFinancieras.this);
        dialogFecha.setContentView(R.layout.dialog_seleccion_fecha);
        dialogFecha.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialogFecha.getWindow().setBackgroundDrawable(getDrawable(R.drawable.dialog_style));
        dialogFecha.setCancelable(false);

        btnNingunaFecha = dialogFecha.findViewById(R.id.btnNinguno);
        btnAbrirCalendario = dialogFecha.findViewById(R.id.btnCalendario);

        btnNingunaFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtFechaFinPago.setText("Sin Fecha");
                dialogFecha.dismiss();
            }
        });
        btnAbrirCalendario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ventanaFecha(txtFechaFinPago);
                dialogFecha.dismiss();
            }
        });
    }

    private void colocarFecha(){
        txtFechaPrestamo = findViewById(R.id.txtFechaPrestamo);
        txtFechaInicioPago = findViewById(R.id.txtFechaInicioPago);
        txtFechaFinPago = findViewById(R.id.txtFechaFinPago);
        txtFechaPrestamo.setText(LocalDate.now().toString());
        txtFechaInicioPago.setText(LocalDate.now().toString());
        txtFechaFinPago.setText(LocalDate.now().toString());

        btnFechaPrestamo = findViewById(R.id.btnFechaPrestamo);
        btnFechaInicioPago = findViewById(R.id.btnFechaInicioPago);
        btnFechaFinPago = findViewById(R.id.btnFechaFinPago);
        btnFechaPrestamo.setOnClickListener(this);
        btnFechaInicioPago.setOnClickListener(this);
        btnFechaFinPago.setOnClickListener(this);
    }

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

    //Registro
    private void comprobarIdentificacion(){
        editIdentificacion = findViewById(R.id.txtIdentificacionObjeto);
        editIdentificacion.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    String identificacion = editIdentificacion.getText().toString();
                    Persona persona = PersonaBancoActivity.buscarPersona(identificacion, context);
                    Log.i("Identificacion", "Valor de persona: " + persona);
                    Banco banco = PersonaBancoActivity.buscarBanco(identificacion, context);
                    Log.i("Identificacion", "Valor de Banco: " + banco);
                    if (persona == null && banco == null) {
                        visualizarBotonPersonaBanco(true);
                    } else if(persona != null || banco != null){
                        visualizarBotonPersonaBanco(false);
                    }
                }
            }
        });
    }

    private void dialogRegistro(){
        dialogRegistrarPB = new Dialog(RegistrarCuentasFinancieras.this);
        dialogRegistrarPB.setContentView(R.layout.dialog_registrar_persona_banco);
        dialogRegistrarPB.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialogRegistrarPB.getWindow().setBackgroundDrawable(getDrawable(R.drawable.dialog_style));
        dialogRegistrarPB.setCancelable(false);

        btnDPersona = dialogRegistrarPB.findViewById(R.id.btnPersonaDialogo);
        btnDBanco = dialogRegistrarPB.findViewById(R.id.btnBancoDialogo);

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
                btnRegistrarPersonaBanco.setOnClickListener(v -> {
                    dialogRegistrarPB.show();
                });
            }

        }
    }

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
            double valor = Double.valueOf(editCantidad.getText().toString());
            String descripcion = editDescripcion.getText().toString();
            double cuota = Double.valueOf(editCuota.getText().toString());
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
}