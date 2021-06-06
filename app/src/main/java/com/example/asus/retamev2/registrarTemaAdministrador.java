package com.example.asus.retamev2;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import Datos.Usuarios;
import Logica.Temas;


public class registrarTemaAdministrador extends AppCompatActivity implements Button.OnClickListener {
    private Button buttonRegistrarActualizarTema;
    private EditText editTextNombreTema, editTextDescripcionTema;
    private Temas objTemas;
    private Usuarios objUsuarios;
    private Bundle bundleTipo;
    private String opcionTipo;
    private List<Datos.Temas> cargarTemaList;
    private int intId_Tema, intResult;
    private View viewRegistrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_tema_administrador);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        editTextNombreTema = (EditText)findViewById(R.id.EDT_NombreTema_ATVRegistrarTemaAdministrador);
        editTextDescripcionTema = (EditText)findViewById(R.id.EDT_DescripcionTema_ATVRegistrarTemaAdministrador);
        buttonRegistrarActualizarTema = (Button)findViewById(R.id.BTN_RegistrarTema_ATVRegistrarTemaAdministrador);
        buttonRegistrarActualizarTema.setOnClickListener(this);
        objUsuarios = new Usuarios();
        objTemas = new Temas();
        cargarTemaList = new ArrayList<Datos.Temas>();
        bundleTipo = getIntent().getExtras();
        if (bundleTipo != null) {
            opcionTipo = bundleTipo.get("opcion").toString();
            if (opcionTipo.equals("2") && objUsuarios.Obtener_VariableSesion(this, "Rol").equals("Administrador")) {
                    intId_Tema = bundleTipo.getInt("Id_Tema");
                    Cargar_Tema(intId_Tema);
            }
        }
    }


    public void Cargar_Tema(final int intIdTema){
        Thread threadCargarTema = new Thread(){
            Handler handlerCargarTema = new Handler();
            @Override
            public void run() {
                cargarTemaList = objTemas.Cargar_Tema(intId_Tema);
                handlerCargarTema.post(new Runnable() {
                    @Override
                    public void run() {
                        if(cargarTemaList == null){
                            Log.e("Hola Jhon ", " La lista esta null");
                        }else {
                            if(intIdTema == cargarTemaList.get(0).getId_Tema()) {
                                editTextNombreTema.setText(cargarTemaList.get(0).getNombre_Tema());
                                editTextDescripcionTema.setText(cargarTemaList.get(0).getDescripcion_Tema());
                            }
                        }
                    }
                });
            }
        };threadCargarTema.start();
    }

    @Override
    public void onClick(View v) {
        if(opcionTipo.equals("2")){
            Actualizar_Tema();
        }else {
            Registrar_Tema();
        }
    }

    public void Actualizar_Tema(){
            Thread threadActualizarTema = new Thread(){
                Handler handlerActualizarTema = new Handler();
                @Override
                public void run() {
                    intResult = objTemas.Actualizar_Tema(intId_Tema, editTextNombreTema.getText().toString(), editTextDescripcionTema.getText().toString());
                    handlerActualizarTema.post(new Runnable() {
                        @Override
                        public void run() {
                            if(intResult == 0){
                                Log.e("Hola Jhon ", " Actualizacion Hecha");
                            }else{
                                Log.e("Hola Jhon ", " Hubo algun error");
                            }
                        }
                    });

                }
            };threadActualizarTema.start();
    }

    public void Registrar_Tema(){
        Thread threadRegistrarTema = new Thread(){
            Handler handlerRegistrarTema = new Handler();
            @Override
            public void run() {
                intResult = objTemas.Registrar_Tema(editTextNombreTema.getText().toString(), editTextDescripcionTema.getText().toString());
                handlerRegistrarTema.post(new Runnable() {
                    @Override
                    public void run() {
                        if(intResult == 0){
                            Log.e("Hola Jhon ", " Actualizacion Hecha");
                        }else{
                            Log.e("Hola Jhon ", " Hubo algun error");
                        }
                    }
                });

            }
        };threadRegistrarTema.start();
    }
}

