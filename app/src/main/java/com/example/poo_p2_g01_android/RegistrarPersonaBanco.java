package com.example.poo_p2_g01_android;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import modelo.banco.Banco;
import modelo.persona.Persona;

public class RegistrarPersonaBanco extends AppCompatActivity {

    private TextView viewTitulo, viewCodigo, viewNombre, viewNombreOficial, viewTelefonoOficial;
    private EditText editNombreOficial, editTelefonoOficial;
    public static boolean esPersona = false;
    private EditText editNombre, editCodigo, editEmail, editTelefono;
    private Button btnRegistrar;

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
        viewTitulo = findViewById(R.id.tituloRegistrar);
        viewCodigo = findViewById(R.id.txtviewCodigo);
        viewNombre = findViewById(R.id.txtviewNombreObjeto);
        viewNombreOficial = findViewById(R.id.txtviewNombreOficial);
        viewTelefonoOficial = findViewById(R.id.txtviewTelefonoOficial);
        editNombreOficial = findViewById(R.id.txtNombreOficial);
        editTelefonoOficial = findViewById(R.id.txtTelefonoOficial);

        if(esPersona){
            viewTitulo.setText(R.string.tituloRegistroPersona);
            viewCodigo.setText(R.string.texto_cedula);
            viewNombre.setText(R.string.texto_nombrePersona);
            viewNombreOficial.setVisibility(View.GONE);
            viewTelefonoOficial.setVisibility(View.GONE);
            editNombreOficial.setVisibility(View.GONE);
            editTelefonoOficial.setVisibility(View.GONE);
        }else {
            viewTitulo.setText(R.string.tituloRegistroBanco);
            viewCodigo.setText(R.string.texto_ruc);
            viewNombre.setText(R.string.texto_nombreBanco);
            viewNombreOficial.setVisibility(View.VISIBLE);
            viewTelefonoOficial.setVisibility(View.VISIBLE);
            editNombreOficial.setVisibility(View.VISIBLE);
            editTelefonoOficial.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        RegistrarDatos();
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
                    finish();
                }
            }
        });
    }
}