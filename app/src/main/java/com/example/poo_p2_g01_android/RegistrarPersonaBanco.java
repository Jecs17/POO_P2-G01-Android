package com.example.poo_p2_g01_android;

import android.content.Context;
import android.content.Intent;
import android.content.pm.LabeledIntent;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import modelo.banco.Banco;
import modelo.persona.Persona;

public class RegistrarPersonaBanco extends AppCompatActivity implements View.OnFocusChangeListener {

    private TextView viewTitulo, viewCodigo, viewNombre, viewNombreOficial, viewTelefonoOficial, viewTelefono;
    private EditText editNombreOficial, editTelefonoOficial;
    public static boolean esPersona = false;
    private EditText editNombre, editCodigo, editEmail, editTelefono;
    private Button btnRegistrar;
    private ImageButton btnRegreso;
    final boolean[] comprobacion = new boolean[]{false, false, false, false, false, false};
    private ColorStateList color_mal;
    private ColorStateList color_bien;
    boolean desdeCuenta = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registrar_persona_banco);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.vistaRegistrarPersonaBanco), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        color_mal = ColorStateList.valueOf(this.getColor(R.color.md_theme_errorContainer_mediumContrast));
        color_bien = ColorStateList.valueOf(this.getColor(R.color.md_theme_secondaryFixed_highContrast));
        regresar();
        cambiarVista();
        Intent intent = getIntent();
        desdeCuenta = intent.getBooleanExtra("desdeCuenta", false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        RegistrarDatos();
        verificarDatos();
        estadoBoton(comprobacion);
    }

    private void cambiarVista(){
        viewTitulo = findViewById(R.id.tituloRegistrar);
        viewCodigo = findViewById(R.id.txtviewCodigo);
        viewNombre = findViewById(R.id.txtviewNombreObjeto);
        viewTelefono = findViewById(R.id.textviewTelefono);
        viewNombreOficial = findViewById(R.id.txtviewNombreOficial);
        viewTelefonoOficial = findViewById(R.id.txtviewTelefonoOficial);
        editNombreOficial = findViewById(R.id.txtNombreOficial);
        editTelefonoOficial = findViewById(R.id.txtTelefonoOficial);
        editTelefono = findViewById(R.id.txtTelefono);

        if(esPersona){
            viewTitulo.setText(R.string.tituloRegistroPersona);
            viewCodigo.setText(R.string.texto_cedula);
            viewNombre.setText(R.string.texto_nombrePersona);
            viewNombreOficial.setVisibility(View.GONE);
            viewTelefonoOficial.setVisibility(View.GONE);
            viewTelefono.setVisibility(View.VISIBLE);
            editNombreOficial.setVisibility(View.GONE);
            editTelefonoOficial.setVisibility(View.GONE);
            editTelefono.setVisibility(View.VISIBLE);
        }else {
            viewTitulo.setText(R.string.tituloRegistroBanco);
            viewCodigo.setText(R.string.texto_ruc);
            viewNombre.setText(R.string.texto_nombreBanco);
            viewNombreOficial.setVisibility(View.VISIBLE);
            viewTelefonoOficial.setVisibility(View.VISIBLE);
            viewTelefono.setVisibility(View.GONE);
            editNombreOficial.setVisibility(View.VISIBLE);
            editTelefonoOficial.setVisibility(View.VISIBLE);
            editTelefono.setVisibility(View.GONE);
        }
    }

    private void regresar(){
        btnRegreso = findViewById(R.id.btnRegresarRegistroPB);
        btnRegreso.setOnClickListener(v -> {
            if(desdeCuenta){
                Intent intentCuenta = new Intent(this, RegistrarCuentasFinancieras.class);
                startActivity(intentCuenta);
            }
            finish();
        });
    }

    private void RegistrarDatos(){
        Context context = this;
        editCodigo = findViewById(R.id.txtCodigo);
        editNombre = findViewById(R.id.txtNombreObjeto);
        editEmail = findViewById(R.id.txtEmail);
        editTelefono = findViewById(R.id.txtTelefono);
        editNombreOficial = findViewById(R.id.txtNombreOficial);
        editTelefonoOficial = findViewById(R.id.txtTelefonoOficial);
        btnRegistrar = findViewById(R.id.btnActivityRegistrarPB);
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String codigo = editCodigo.getText().toString();
                String nombre = editNombre.getText().toString();
                String email = editEmail.getText().toString();
                String telefono = editTelefono.getText().toString();
                boolean guardado = false;
                if(esPersona){
                    Persona persona = new Persona(codigo,nombre,telefono,email);
                    guardado = Persona.guardarPersona(context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS),persona);

                }else {
                    String nombreOficial = editNombreOficial.getText().toString();
                    String telefonoOficial = editTelefonoOficial.getText().toString();
                    Persona oficial = new Persona(nombreOficial,telefonoOficial);
                    Banco banco = new Banco(nombre, codigo, email, oficial);
                    guardado = Banco.guardarBanco(context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), banco);
                }

                if(guardado){
                    Toast.makeText(getApplicationContext(),"Datos guardados", Toast.LENGTH_SHORT).show();
                    if(desdeCuenta){
                        Intent intentCuenta = new Intent(context, RegistrarCuentasFinancieras.class);
                        intentCuenta.putExtra("nombre",nombre);
                        startActivity(intentCuenta);
                    }
                    finish();
                }
            }
        });
    }

    private void verificarDatos(){
        // Colocando el modo del color
        editCodigo.setBackgroundTintMode(PorterDuff.Mode.ADD);
        editNombre.setBackgroundTintMode(PorterDuff.Mode.ADD);
        editTelefono.setBackgroundTintMode(PorterDuff.Mode.ADD);
        editEmail.setBackgroundTintMode(PorterDuff.Mode.ADD);
        editNombreOficial.setBackgroundTintMode(PorterDuff.Mode.ADD);
        editTelefonoOficial.setBackgroundTintMode(PorterDuff.Mode.ADD);

        //Añadiendo el evento de TextWatcher
        editCodigo.addTextChangedListener(textWatcher);
        editNombre.addTextChangedListener(textWatcher);
        editEmail.addTextChangedListener(textWatcher);
        editTelefono.addTextChangedListener(textWatcher);
        editNombreOficial.addTextChangedListener(textWatcher);
        editTelefonoOficial.addTextChangedListener(textWatcher);

        //Añadiendo el evento de onFocusChange
        editCodigo.setOnFocusChangeListener(this::onFocusChange);
        editNombre.setOnFocusChangeListener(this::onFocusChange);
        editEmail.setOnFocusChangeListener(this::onFocusChange);
        editTelefono.setOnFocusChangeListener(this::onFocusChange);
        editNombreOficial.setOnFocusChangeListener(this::onFocusChange);
        editTelefonoOficial.setOnFocusChangeListener(this::onFocusChange);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if(!hasFocus){
            if(!comprobacion[0] && v.equals(editCodigo)){
                if(esPersona) mensajeValidacion("Cédula Inválida");
                else mensajeValidacion("RUC Inválido");
                editCodigo.setBackgroundTintList(color_mal);
            } else if (!comprobacion[1] && v.equals(editNombre)) {
                mensajeValidacion("No puede dejar el campo vacío");
                editNombre.setBackgroundTintList(color_mal);
            } else if (!comprobacion[2] && v.equals(editEmail)) {
                mensajeValidacion("Email Inválido");
                editEmail.setBackgroundTintList(color_mal);
            } else if (!comprobacion[3] && v.equals(editTelefono)) {
                mensajeValidacion("Teléfono Inválido");
                editTelefono.setBackgroundTintList(color_mal);
            } else if (!comprobacion[4] && v.equals(editNombreOficial)) {
                mensajeValidacion("No puede dejar el campo vacío");
                editNombreOficial.setBackgroundTintList(color_mal);
            } else if (!comprobacion[5] && v.equals(editTelefonoOficial)) {
                mensajeValidacion("Teléfono Inválido");
                editTelefonoOficial.setBackgroundTintList(color_mal);
            }
        }
    }


    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            if(editCodigo.hasFocus()){
                String codigo = editCodigo.getText().toString();
                Persona persona = PersonaBancoActivity.buscarPersona(codigo, RegistrarPersonaBanco.this);
                Banco banco = PersonaBancoActivity.buscarBanco(codigo, RegistrarPersonaBanco.this);
                if(esPersona && (codigo.length() != 10 || persona != null)){
                    editCodigo.setBackgroundTintList(color_mal);
                    comprobacion[0] = false;
                }else if(!esPersona && (codigo.length() != 13 || banco != null)){
                    editCodigo.setBackgroundTintList(color_mal);
                    comprobacion[0] = false;
                }else {
                    editCodigo.setBackgroundTintList(color_bien);
                    comprobacion[0] = true;
                }
            }

            if(editNombre.hasFocus()){
                boolean nombreVacio = editNombre.getText().toString().isEmpty();
                if(nombreVacio){
                    editNombre.setBackgroundTintList(color_mal);
                    comprobacion[1] = false;
                }else {
                    editNombre.setBackgroundTintList(color_bien);
                    comprobacion[1] = true;
                }
            }

            if(editEmail.hasFocus()){
                String email = editEmail.getText().toString();
                boolean emailValido = email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}");
                if(emailValido){
                    editEmail.setBackgroundTintList(color_bien);
                    comprobacion[2] = true;
                }else{
                    editEmail.setBackgroundTintList(color_mal);
                    comprobacion[2] = false;
                }
            }

            if(editTelefono.hasFocus()){
                String telefono = editTelefono.getText().toString();
                boolean telefonoValido = telefono.matches("\\d{10}");
                if(telefonoValido){
                    editTelefono.setBackgroundTintList(color_bien);
                    comprobacion[3] = true;
                }else{
                    editTelefono.setBackgroundTintList(color_mal);
                    comprobacion[3] = false;
                }
            }

            if(editNombreOficial.hasFocus()){
                boolean nombreOficialVacio = editNombreOficial.getText().toString().isEmpty();
                if(nombreOficialVacio){
                    editNombreOficial.setBackgroundTintList(color_mal);
                    comprobacion[4] = false;
                }else {
                    editNombreOficial.setBackgroundTintList(color_bien);
                    comprobacion[4] = true;
                }
            }

            if(editTelefonoOficial.hasFocus()){
                String telefonoOficial = editTelefonoOficial.getText().toString();
                boolean telefonoOficialValido = telefonoOficial.matches("\\d{10}");
                if(telefonoOficialValido){
                    editTelefonoOficial.setBackgroundTintList(color_bien);
                    comprobacion[5] = true;
                }else{
                    editTelefonoOficial.setBackgroundTintList(color_mal);
                    comprobacion[5] = false;
                }
            }

            estadoBoton(comprobacion);
        }

        @Override
        public void afterTextChanged(Editable s) {}
    };

    private void estadoBoton(boolean[] valores){
        if(esPersona && valores[0] && valores[1] && valores[2] && valores[3]){
            btnRegistrar.setEnabled(true);
        }else if(!esPersona && valores[0] && valores[1] && valores[2] && valores[4] && valores[5]){
            btnRegistrar.setEnabled(true);
        } else{
            btnRegistrar.setEnabled(false);
        }
    }

    private void mensajeValidacion(String mensaje){
        Toast.makeText(this,mensaje,Toast.LENGTH_SHORT).show();
    }
}