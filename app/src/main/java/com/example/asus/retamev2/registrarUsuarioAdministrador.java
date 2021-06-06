package com.example.asus.retamev2;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import Logica.Usuarios;

public class registrarUsuarioAdministrador extends AppCompatActivity implements Button.OnClickListener {
    private TextView textViewNombreUsuario, textViewContrasenia, textViewNombres, textViewApellidos, textViewCelular;
    private Spinner spinnerTipoRol;
    private Button buttonRegistrar;
    private Usuarios objUsuarios;
    private String opcionTipo;
    private List<Datos.Usuarios> usuarioList;
    private int intId_Usuario, intIdPersona, intResult;

    private Bundle bundleTipo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_usuario_administrador);
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
        textViewNombres = (TextView)findViewById(R.id.EDT_Nombres_ATVRegistrarUsuarioAdministrador);
        textViewApellidos = (TextView)findViewById(R.id.EDT_Apellidos_ATVRegistrarUsuarioAdministrador);
        textViewCelular = (TextView)findViewById(R.id.EDT_Celular_ATVRegistrarUsuarioAdministrador);
        textViewNombreUsuario = (TextView)findViewById(R.id.EDT_NombreUsuario_ATVRegistrarUsuarioAdministrador);
        textViewContrasenia = (TextView)findViewById(R.id.EDT_Contrasenia_ATVRegistrarUsuarioAdministrador);
        spinnerTipoRol = (Spinner)findViewById(R.id.SPN_TipoRol_ATVRegistrarUsuarioAdministrador);
        buttonRegistrar = (Button)findViewById(R.id.BTN_RegistrarUsuario_ATVRegistrarUsuarioAdministrador);
        buttonRegistrar.setOnClickListener(this);
        objUsuarios = new Usuarios();

         bundleTipo = getIntent().getExtras();
        if (bundleTipo != null) {
            opcionTipo = bundleTipo.get("opcion").toString();
            if(opcionTipo.equals("2") && bundleTipo.get("Rol").equals("Administrador")){
                intId_Usuario = Integer.parseInt(bundleTipo.get("Id_Usuario").toString());
                Cargar_Informacion();
              //  Cargar_InformacionAdminsitrador();
            }else {
                if(opcionTipo.equals("2") && bundleTipo.get("Rol").equals("Estandar")){
                    intId_Usuario = Integer.parseInt(bundleTipo.get("Id_Usuario").toString());
                    Cargar_Informacion();
                  //  Cargar_InformacionEstandar();
                }else
                    if(opcionTipo.equals("1") && bundleTipo.get("Rol").equals("Estandar")){
                        spinnerTipoRol.setEnabled(false);
                        spinnerTipoRol.setVisibility(View.GONE);
                    }
            }

        }
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.rol_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTipoRol.setAdapter(adapter);

    }


    @Override
    public void onClick(final View v) {
        if(opcionTipo.equals("2")){
            Log.e("Hola Jhon ", "entramos a Actualizar "+opcionTipo);
            Actualizar_Usuario(v);
        }else {
            Log.e("Hola Jhon ", "entramos a Registrar "+opcionTipo);
            Registrar_Usuario(v);
        }
    }


    public void Registrar_Usuario(final View v){

        Thread threadRegistrarUsuario = new Thread(){
            Handler handlerRegistrarUsuario = new Handler();
            @Override
            public void run() {

                if(bundleTipo.get("Rol").equals("Administrador")) {
                    intResult = objUsuarios.Registrar_Usuario(textViewNombres.toString(),textViewApellidos.toString(),textViewCelular.toString(), textViewNombreUsuario.toString(), textViewContrasenia.toString(),spinnerTipoRol.getSelectedItem().toString());
                }else {
                    intResult = objUsuarios.Registrar_Usuario(textViewNombres.toString(),textViewApellidos.toString(),textViewCelular.toString(), textViewNombreUsuario.toString(), textViewContrasenia.toString(), "Estandar");
                }
                handlerRegistrarUsuario.post(new Runnable() {
                    @Override
                    public void run() {
                        if(intResult == 1){
                            Snackbar.make(v, "El numero del movil se encuentra registrado", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        }else {
                            if(intResult == 2){
                                Snackbar.make(v, "El usuario se encuentra registrado", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                            }else {
                                if (intResult == 4 || intResult == 3){
                                    Snackbar.make(v, "Error en la actualizacion", Snackbar.LENGTH_LONG)
                                            .setAction("Action", null).show();
                                }else {
                                    Snackbar.make(v, "Datos Actualizados", Snackbar.LENGTH_LONG)
                                            .setAction("Action", null).show();
                                }
                            }
                        }
                    }
                });
            }
        };threadRegistrarUsuario.start();
    }

    public void Actualizar_Usuario(final View v){
        Thread threadActualizarUsuario = new Thread(){
            Handler handlerActualizarUsuario = new Handler();
            @Override
            public void run() {
                if(bundleTipo.get("Rol").equals("Administrador")) {
                    intResult = objUsuarios.Actualizar_Usuario(intId_Usuario, intIdPersona, textViewNombres.getText().toString(), textViewApellidos.getText().toString(), textViewCelular.getText().toString(), textViewNombreUsuario.getText().toString(), spinnerTipoRol.getSelectedItem().toString());
                }else {
                    intResult = objUsuarios.Actualizar_Usuario(intId_Usuario, intIdPersona, textViewNombres.getText().toString(), textViewApellidos.getText().toString(), textViewCelular.getText().toString(), textViewNombreUsuario.getText().toString(), "Estandar");
                }
                handlerActualizarUsuario.post(new Runnable() {
                    @Override
                    public void run() {
                        if(intResult == 1){
                            Snackbar.make(v, "El numero del movil se encuentra registrado", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        }else {
                            if(intResult == 2){
                                Snackbar.make(v, "El usuario se encuentra registrado", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                            }else {
                                if (intResult == 4 || intResult == 3){
                                    Snackbar.make(v, "Error en la actualizacion", Snackbar.LENGTH_LONG)
                                            .setAction("Action", null).show();
                                }else {
                                    Snackbar.make(v, "Datos Actualizados", Snackbar.LENGTH_LONG)
                                            .setAction("Action", null).show();
                                }
                            }
                        }
                    }
                });
            }
        };threadActualizarUsuario.start();
    }

    public void Cargar_Informacion(){
        usuarioList = new ArrayList<Datos.Usuarios>();
        Thread threadCargarInformacion = new Thread(){
            Handler handlerCargarInformacion = new Handler();
            @Override
            public void run() {
                usuarioList = objUsuarios.Cargar_Informacion(intId_Usuario);
                handlerCargarInformacion.post(new Runnable() {
                    @Override
                    public void run() {
                        if(opcionTipo.equals("2") && bundleTipo.get("Rol").equals("Administrador")){
                            Cargar_InformacionAdminsitrador();
                        }else {
                            if (opcionTipo.equals("2") && bundleTipo.get("Rol").equals("Estandar")) {
                                intId_Usuario = Integer.parseInt(bundleTipo.get("Id_Usuario").toString());
                                Cargar_InformacionEstandar();
                            }
                        }
                    }
                });
            }
        };threadCargarInformacion.start();
    }

    public void Cargar_InformacionEstandar(){

        if(usuarioList.equals(null)){
            Log.e("Hola Jhon", " El Usuario No se encontro");
        }else{
            intId_Usuario = usuarioList.get(0).getId_Usuario();
            intIdPersona = usuarioList.get(0).getObjPersona().getId_Persona();
            textViewNombreUsuario.setText(usuarioList.get(0).getNombre_Usuario());
            textViewNombres.setText(usuarioList.get(0).getObjPersona().getNombres());
            textViewApellidos.setText(usuarioList.get(0).getObjPersona().getApellidos());
            textViewCelular.setText(usuarioList.get(0).getObjPersona().getCelular());
            textViewContrasenia.setVisibility(View.GONE);
            textViewContrasenia.setEnabled(false);
            textViewNombreUsuario.setEnabled(false);
            textViewCelular.setEnabled(false);
            textViewNombreUsuario.setEnabled(false);
            spinnerTipoRol.setEnabled(false);
            spinnerTipoRol.setVisibility(View.GONE);
        }

    }

    public void Cargar_InformacionAdminsitrador(){
        if(usuarioList.equals(null)){
            Log.e("Hola Jhon", " El Usuario No se encontro");
        }else{
            intId_Usuario = usuarioList.get(0).getId_Usuario();
            intIdPersona = usuarioList.get(0).getObjPersona().getId_Persona();
            textViewNombreUsuario.setText(usuarioList.get(0).getNombre_Usuario());
            textViewNombres.setText(usuarioList.get(0).getObjPersona().getNombres());
            textViewApellidos.setText(usuarioList.get(0).getObjPersona().getApellidos());
            textViewCelular.setText(usuarioList.get(0).getObjPersona().getCelular());
            textViewContrasenia.setVisibility(View.GONE);
            textViewContrasenia.setEnabled(false);
        }

    }

}
