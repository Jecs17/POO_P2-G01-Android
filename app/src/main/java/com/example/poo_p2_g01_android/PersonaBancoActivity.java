package com.example.poo_p2_g01_android;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

import modelo.banco.Banco;
import modelo.persona.Persona;

public class PersonaBancoActivity extends AppCompatActivity {

    private Button btnPersona, btnBanco;
    private ImageButton btnRegresar;
    private ArrayList<Object> listaPersonasBancos;

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
        RegistrarPersonaBanco.clase = PersonaBancoActivity.class;
        registrarPersona();
        registrarBanco();
        regresar();
    }

    @Override
    protected void onResume() {
        super.onResume();
        llenarTabla();
    }

    private void regresar(){
        btnRegresar = findViewById(R.id.btnRegresarPersonaBanco);
        btnRegresar.setOnClickListener(v -> {
            finish();
        });
    }

    public void registrarPersona(){
        btnPersona = findViewById(R.id.btnRegistrarPersona);
        btnPersona.setOnClickListener(v -> {
            Intent i = new Intent(PersonaBancoActivity.this, RegistrarPersonaBanco.class);
            RegistrarPersonaBanco.esPersona = true;
            startActivity(i);
        });
    }

    public void registrarBanco(){
        btnBanco = findViewById(R.id.btnRegistrarBanco);
        btnBanco.setOnClickListener(v -> {
            Intent i = new Intent(PersonaBancoActivity.this, RegistrarPersonaBanco.class);
            RegistrarPersonaBanco.esPersona = false;
            startActivity(i);
        });
    }

    private ArrayList<Persona> cargarListaPersona(Context context){
        ArrayList<Persona> listaPersona = new ArrayList<>();
        try {
            listaPersona = Persona.cargarPersonas(context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS));
        } catch(Exception e){
            Log.d("ActivityPersonaBanco", "Error al cargar los datos de Persona"+ e.getMessage());
        }
        return listaPersona;
    }

    private ArrayList<Banco> cargarListaBanco(Context context){
        ArrayList<Banco> listaBanco = new ArrayList<>();
        try {
            listaBanco = Banco.cargarBanco(context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS));
        } catch(Exception e){
            Log.d("ActivityPersonaBanco", "Error al cargar los datos de Banco"+ e.getMessage());
        }
        return listaBanco;
    }

    private ArrayList<Object> agruparListas(){
        ArrayList<Persona> listaPersona = cargarListaPersona(this);
        ArrayList<Banco> listaBanco = cargarListaBanco(this);
        ArrayList<Object> listaGeneral = new ArrayList<>();
        listaGeneral.addAll(listaPersona);
        listaGeneral.addAll(listaBanco);
        return listaGeneral;
    }

    private  void llenarTabla() {
        listaPersonasBancos = agruparListas();

        TableLayout tabla = findViewById(R.id.tablaPersonaBanco);
        Log.d("ActivityPersonaBanco", listaPersonasBancos.toString());
        cleanTable(tabla);

        for (int i = 0; i < listaPersonasBancos.size(); i++) {
            Object objeto = listaPersonasBancos.get(i);
            if (objeto instanceof Persona) {
                Persona persona = (Persona) objeto;

                TableRow tr = new TableRow(this);

                TextView tvCodigo = new TextView(this);
                tvCodigo.setTextColor(ContextCompat.getColor(this, R.color.md_theme_background));
                tvCodigo.setPadding(8, 10, 8, 10);
                tvCodigo.setText(""+(i+1));

                TextView tvFechaRegistro = new TextView(this);
                tvFechaRegistro.setTextColor(ContextCompat.getColor(this, R.color.md_theme_background));
                tvFechaRegistro.setPadding(8, 10, 8, 10);
                tvFechaRegistro.setText(persona.getFechaRegistro().toString());

                TextView tvIdentificacion = new TextView(this);
                tvIdentificacion.setTextColor(ContextCompat.getColor(this, R.color.md_theme_background));
                tvIdentificacion.setPadding(8, 10, 8, 10);
                tvIdentificacion.setText(persona.getCedula());

                TextView tvNombre = new TextView(this);
                tvNombre.setTextColor(ContextCompat.getColor(this, R.color.md_theme_background));
                tvNombre.setPadding(8, 10, 8, 10);
                tvNombre.setText(persona.getNombre());

                TextView tvEmail = new TextView(this);
                tvEmail.setTextColor(ContextCompat.getColor(this, R.color.md_theme_background));
                tvEmail.setPadding(8, 10, 10, 10);
                tvEmail.setText(persona.getEmail());

                TextView tvOtrosDatos = new TextView(this);
                tvOtrosDatos.setTextColor(ContextCompat.getColor(this, R.color.md_theme_background));
                tvOtrosDatos.setPadding(8, 10, 8, 10);
                tvOtrosDatos.setText(persona.getTelefono());


                ImageButton btnEliminar = new ImageButton(this);
                btnEliminar.setAdjustViewBounds(true);
                btnEliminar.setMaxWidth(100);
                btnEliminar.setMaxHeight(100);
                btnEliminar.setBackgroundResource(R.drawable.buttonstyle);
                btnEliminar.setPadding(5, 10, 5, 10);
                btnEliminar.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.x_mark));

                //agregar al TableRow
                tr.addView(tvCodigo);
                tr.addView(tvFechaRegistro);
                tr.addView(tvIdentificacion);
                tr.addView(tvNombre);
                tr.addView(tvEmail);
                tr.addView(tvOtrosDatos);
                tr.addView(btnEliminar);
                tr.setPadding(0, 15, 0, 0);
                //agregar al Tableview
                tabla.addView(tr);

            } else if (objeto instanceof Banco) {
                Banco banco = (Banco) objeto;

                TableRow tr = new TableRow(this);

                TextView tvCodigo = new TextView(this);
                tvCodigo.setTextColor(ContextCompat.getColor(this, R.color.md_theme_background));
                tvCodigo.setPadding(8, 10, 8, 10);
                tvCodigo.setText(""+(i+1));

                TextView tvFechaRegistro = new TextView(this);
                tvFechaRegistro.setTextColor(ContextCompat.getColor(this, R.color.md_theme_background));
                tvFechaRegistro.setPadding(8, 10, 8, 10);
                tvFechaRegistro.setText(banco.getFechaRegistro().toString());

                TextView tvIdentificacion = new TextView(this);
                tvIdentificacion.setTextColor(ContextCompat.getColor(this, R.color.md_theme_background));
                tvIdentificacion.setPadding(8, 10, 8, 10);
                tvIdentificacion.setText(banco.getRuc());

                TextView tvNombre = new TextView(this);
                tvNombre.setTextColor(ContextCompat.getColor(this, R.color.md_theme_background));
                tvNombre.setPadding(8, 10, 8, 10);
                tvNombre.setText(banco.getEntidadBancaria());

                TextView tvEmail = new TextView(this);
                tvEmail.setTextColor(ContextCompat.getColor(this, R.color.md_theme_background));
                tvEmail.setPadding(8, 10, 10, 10);
                tvEmail.setText(banco.getEmail());

                TextView tvOtrosDatos = new TextView(this);
                tvOtrosDatos.setTextColor(ContextCompat.getColor(this, R.color.md_theme_background));
                tvOtrosDatos.setPadding(8, 10, 8, 10);
                tvOtrosDatos.setText(banco.toString());


                ImageButton btnEliminar = new ImageButton(this);
                btnEliminar.setAdjustViewBounds(true);
                btnEliminar.setMaxWidth(100);
                btnEliminar.setMaxHeight(100);
                btnEliminar.setBackgroundResource(R.drawable.buttonstyle);
                btnEliminar.setPadding(5, 10, 5, 10);
                btnEliminar.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.x_mark));

                //agregar al TableRow
                tr.addView(tvCodigo);
                tr.addView(tvFechaRegistro);
                tr.addView(tvIdentificacion);
                tr.addView(tvNombre);
                tr.addView(tvEmail);
                tr.addView(tvOtrosDatos);
                tr.addView(btnEliminar);
                tr.setPadding(0, 15, 0, 0);
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

    public static Persona buscarPersona(String identificacion, Context context) {
        ArrayList<Persona> listaPersona = new PersonaBancoActivity().cargarListaPersona(context);
        for (Persona persona : listaPersona) {
            if(persona != null){
                if (persona.getCedula().equals(identificacion) || persona.getNombre().equals(identificacion)) {
                    return persona;
                }
            }
        }
        return null;
    }

    public static Banco buscarBanco(String identificacion, Context context) {
        ArrayList<Banco> listaBanco = new PersonaBancoActivity().cargarListaBanco(context);
        for (Banco banco : listaBanco) {
            if (banco != null && (banco.getRuc().equals(identificacion) || banco.getEntidadBancaria().equals(identificacion))) {
                return banco;
            }
        }
        return null;
    }

}