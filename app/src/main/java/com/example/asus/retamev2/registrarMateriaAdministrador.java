package com.example.asus.retamev2;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import Datos.Usuarios;
import Logica.Materias;

public class registrarMateriaAdministrador extends AppCompatActivity implements Button.OnClickListener {
    private EditText editTextNombreMateria, editTextDescripcionMateria;
    private Button buttonRegistrarMateria;
    private Bundle bundleTipo;
    private String opcionTipo;
    private int intId_Materia, intResult;
    private Usuarios objUsuarios;
    private Materias objMaterias;
    private List<Datos.Materias> materiasList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_materia_administrador);
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
        editTextNombreMateria = (EditText) findViewById(R.id.EDT_NombreMateria_ATVRegistrarMateriaAdministrador);
        editTextNombreMateria = (EditText) findViewById(R.id.EDT_DescripcionMateria_ATVRegistrarMateriaAdministrador);
        buttonRegistrarMateria = (Button)findViewById(R.id.BTN_RegistrarMateria_ATVRegistrarMateriaAdministrador);
        objMaterias = new Materias();
        objUsuarios = new Usuarios();
        bundleTipo = getIntent().getExtras();
        if (bundleTipo != null) {
            opcionTipo = bundleTipo.get("opcion").toString();
            if (opcionTipo.equals("2") && objUsuarios.Obtener_VariableSesion(this, "Rol").equals("Administrador")) {
                intId_Materia = bundleTipo.getInt("Id_Materia");
                materiasList = new ArrayList<Datos.Materias>();
                Cargar_Materia(intId_Materia);
            }
        }
    }

    public void Cargar_Materia(final int intIdMateria){
        Thread threadCargarMateria = new Thread(){
            Handler handlerCargarMateria = new Handler();
            @Override
            public void run() {
                materiasList = objMaterias.Cargar_Materia(intIdMateria);
                handlerCargarMateria.post(new Runnable() {
                    @Override
                    public void run() {
                        if(materiasList.equals(null)){

                        }else {
                            editTextNombreMateria.setText(materiasList.get(0).getNombre_Materia());
                            editTextDescripcionMateria.setText(materiasList.get(0).getDescripcion_Materia());
                        }
                    }
                });
            }
        };threadCargarMateria.start();
    }

    @Override
    public void onClick(View v) {
        if(opcionTipo.equals("2")){
            Actualizar_Materia();
        }else {
            Registrar_Materia();
        }
    }

    public void Registrar_Materia(){
        Thread threadRegistrarMateria = new Thread(){
            Handler handlerRegistrarMateria = new Handler();

            @Override
            public void run() {
                intResult = objMaterias.Registrar_Materia(editTextNombreMateria.getText().toString(), editTextDescripcionMateria.getText().toString());
                handlerRegistrarMateria.post(new Runnable() {
                    @Override
                    public void run() {
                        if(intResult == 0){

                        }else {

                        }
                    }
                });
            }
        };threadRegistrarMateria.start();
    }


    public void Actualizar_Materia(){
        Thread threadActualizarMateria = new Thread(){
            Handler handlerActualizarMateria = new Handler();

            @Override
            public void run() {
                intResult = objMaterias.Actualizar_Materia(materiasList.get(0).getId_Materia() ,editTextNombreMateria.getText().toString(), editTextDescripcionMateria.getText().toString());
                handlerActualizarMateria.post(new Runnable() {
                    @Override
                    public void run() {
                        if(intResult == 0){

                        }else {

                        }
                    }
                });
            }
        };threadActualizarMateria.start();
    }
}
