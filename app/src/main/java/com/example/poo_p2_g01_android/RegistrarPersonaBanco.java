package com.example.poo_p2_g01_android;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class RegistrarPersonaBanco extends AppCompatActivity {

    private TextView viewTitulo, viewCodigo, viewNombre, viewNombreOficial, viewTelefonoOficial;
    private EditText editNombreOficial, editTelefonoOficial;
    public static boolean esPersona = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registrar_persona_banco);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
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
}