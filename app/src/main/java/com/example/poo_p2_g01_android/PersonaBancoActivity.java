package com.example.poo_p2_g01_android;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.ColorInt;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

import modelo.persona.Persona;

public class PersonaBancoActivity extends AppCompatActivity {

    private Button btnPersona, btnBanco;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_persona_banco);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.vistaPersonaBanco), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        registrarPersona();
        registrarBanco();
        cargarDatos();
        //llenarTabla();
    }

    @Override
    protected void onResume() {
        super.onResume();
        llenarTabla();
    }

    public void registrarPersona(){
        btnPersona = findViewById(R.id.btnRegistrarPersona);
        btnPersona.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PersonaBancoActivity.this, RegistrarPersonaBanco.class);
                RegistrarPersonaBanco.esPersona = true;
                startActivity(i);
            }
        });
    }

    public void registrarBanco(){
        btnPersona = findViewById(R.id.btnRegistrarBanco);
        btnPersona.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PersonaBancoActivity.this, RegistrarPersonaBanco.class);
                RegistrarPersonaBanco.esPersona = false;
                startActivity(i);
            }
        });
    }

    private void cargarDatos(){
        boolean guardado = false;
        try {
            guardado = Persona.crearDatosIniciales(this.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS));
        } catch(Exception e){
            guardado = false;
            Log.d("ActivityPersonaBanco", "Error al cargar los datos iniciales"+ e.getMessage());
        }

        if(guardado){
            Log.d("ActivityPersonaBanco","Datos iniciales guardados");
        }
    }

    private  void llenarTabla(){
        ArrayList<Persona> lista = new ArrayList<>();
        try {
            lista = Persona.cargarPersonas(this.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS));
        } catch(Exception e){
            Log.d("ActivityPersonaBanco", "Error al cargar los datos"+ e.getMessage());
        }

        TableLayout tabla = findViewById(R.id.tablaPersonaBanco);
        Log.d("ActivityPersonaBanco", lista.toString());
        cleanTable(tabla);

        for(Persona persona: lista){

            if(!persona.getCedula().equals(" ")){
                TableRow tr = new TableRow(this);

                TextView tvCodigo = new TextView(this);
                tvCodigo.setTextColor(ContextCompat.getColor(this,R.color.md_theme_background));
                tvCodigo.setPadding(8,10,8,10);
                tvCodigo.setText(persona.getId()+"");

                TextView tvFechaRegistro = new TextView(this);
                tvFechaRegistro.setTextColor(ContextCompat.getColor(this,R.color.md_theme_background));
                tvFechaRegistro.setPadding(8,10,8,10);
                tvFechaRegistro.setText(persona.getFechaRegistro().toString());

                TextView tvIdentificacion = new TextView(this);
                tvIdentificacion.setTextColor(ContextCompat.getColor(this,R.color.md_theme_background));
                tvIdentificacion.setPadding(8,10,8,10);
                tvIdentificacion.setText(persona.getCedula());

                TextView tvNombre = new TextView(this);
                tvNombre.setTextColor(ContextCompat.getColor(this,R.color.md_theme_background));
                tvNombre.setPadding(8,10,8,10);
                tvNombre.setText(persona.getNombre());

                TextView tvEmail = new TextView(this);
                tvEmail.setTextColor(ContextCompat.getColor(this,R.color.md_theme_background));
                tvEmail.setPadding(8,10,10,10);
                tvEmail.setText(persona.getEmail());

                TextView tvOtrosDatos = new TextView(this);
                tvOtrosDatos.setTextColor(ContextCompat.getColor(this,R.color.md_theme_background));
                tvOtrosDatos.setPadding(8,10,8,10);
                tvOtrosDatos.setText(persona.getTelefono());


                ImageButton btnEliminar = new ImageButton(this);
                btnEliminar.setAdjustViewBounds(true);
                btnEliminar.setMaxWidth(100);
                btnEliminar.setMaxHeight(100);
                btnEliminar.setBackgroundResource(R.drawable.buttonstyle);
                btnEliminar.setPadding(5,10,5,10);
                btnEliminar.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.x_mark));

                //agregar al TableRow
                tr.addView(tvCodigo);
                tr.addView(tvFechaRegistro);
                tr.addView(tvIdentificacion);
                tr.addView(tvNombre);
                tr.addView(tvEmail);
                tr.addView(tvOtrosDatos);
                tr.addView(btnEliminar);
                tr.setPadding(0,15,0,0);
                //agregar al Tableview
                tabla.addView(tr);
            }
        }



    }

    private void cleanTable(TableLayout table) {

        int childCount = table.getChildCount();

        // Remove all rows except the first one
        if (childCount > 1) {
            table.removeViews(1, childCount - 1);
        }
    }

}