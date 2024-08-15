package com.example.poo_p2_g01_android;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.time.LocalDate;
import java.util.Calendar;

import modelo.banco.Banco;
import modelo.persona.Persona;

public class RegistrarCuentasFinancieras extends AppCompatActivity implements View.OnClickListener {

    private TextView txtFechaPrestamo, txtFechaInicioPago, txtFechaFinPago;
    private Button btnFechaPrestamo, btnFechaInicioPago, btnFechaFinPago, btnNingunaFecha, btnAbrirCalendario;
    private int dia, mes, anio;
    private Dialog dialog;
    private TextView txtviewInteres;
    private EditText editIntereses, editIdentificacion;
    private Button btnRegistrarPersonaBanco;
    public static boolean esAcreedor = false;

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
        colocarFecha();
    }

    @Override
    protected void onResume() {
        super.onResume();
        comprobarIdentificacion();
    }

    private void cambiarCuenta(){
        txtviewInteres = findViewById(R.id.txtviewInteres);
        editIntereses = findViewById(R.id.txtInteres);
        if(!esAcreedor){
            editIntereses.setVisibility(View.GONE);
            txtviewInteres.setVisibility(View.GONE);
        }else{
            txtviewInteres.setVisibility(View.VISIBLE);
            editIntereses.setVisibility(View.VISIBLE);
        }
    }

    private void dialogFechaFin(){
        dialog = new Dialog(RegistrarCuentasFinancieras.this);
        dialog.setContentView(R.layout.dialog_seleccion_fecha);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.dialog_style));
        dialog.setCancelable(false);

        btnNingunaFecha = dialog.findViewById(R.id.btnNinguno);
        btnAbrirCalendario = dialog.findViewById(R.id.btnCalendario);

        btnNingunaFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtFechaFinPago.setText("Sin Fecha");
                dialog.dismiss();
            }
        });
        btnAbrirCalendario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ventanaFecha(txtFechaFinPago);
                dialog.dismiss();
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
            dialog.show();
        }
    }

    //Registro
    private void comprobarIdentificacion(){
        Context context = this;
        editIdentificacion = findViewById(R.id.txtIdentificacionObjeto);
        editIdentificacion.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    String identificacion = editIdentificacion.getText().toString();
                    Persona persona = PersonaBancoActivity.buscarPersona(identificacion, context);
                    Log.i("Identificacion", "onFocusChange: " + persona);
                    Banco banco = PersonaBancoActivity.buscarBanco(identificacion, context);
                    Log.i("Identificacion", "onFocusChange: " + banco);
                    if (persona == null && banco == null) {
                        //TODO: Cambiar el valor de esPersona
                        visualizarBotonPersonaBanco(true, identificacion, true);
                    } else if(persona != null || banco != null){
                        visualizarBotonPersonaBanco(false);
                    }
                }
            }
        });
    }

    private void visualizarBotonPersonaBanco(boolean ver){
        btnRegistrarPersonaBanco = findViewById(R.id.btnRegistrarPersonaBanco);
        if(!ver){
            btnRegistrarPersonaBanco.setVisibility(View.GONE);
        }else {
            btnRegistrarPersonaBanco.setVisibility(View.VISIBLE);
        }
    }
    private void visualizarBotonPersonaBanco(boolean ver, String identificacion, boolean esPersona){
        btnRegistrarPersonaBanco = findViewById(R.id.btnRegistrarPersonaBanco);
        if(!ver){
            btnRegistrarPersonaBanco.setVisibility(View.GONE);
        }else {
            btnRegistrarPersonaBanco.setVisibility(View.VISIBLE);
            btnRegistrarPersonaBanco.setOnClickListener(v -> {
                Intent intent = new Intent( RegistrarCuentasFinancieras.this, RegistrarPersonaBanco.class);
                intent.putExtra("identificacion", identificacion);
                RegistrarPersonaBanco.esPersona = esPersona;
                startActivity(intent);
            });
        }

    }
}