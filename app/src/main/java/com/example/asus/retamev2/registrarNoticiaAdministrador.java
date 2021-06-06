package com.example.asus.retamev2;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Datos.Categorias;
import Datos.Noticias;
import Datos.Temas;
import Datos.Usuarios;
import adapterAdministrador.adapterCargarCategoriaRegistrarNoticia;
import adapterAdministrador.adapterCargarTemasRegistrarNoticia;

public class registrarNoticiaAdministrador extends AppCompatActivity implements Button.OnClickListener {
    private Spinner spinnerCategoria, spinnerTema;
    private List<Categorias> categoriasList;
    private List<Temas> temasList;
    private Logica.Noticias objNoticias;
    private Logica.Temas objTemas;
    private Logica.Categorias objCategorias;
    private Usuarios objUsuario;
    private adapterCargarCategoriaRegistrarNoticia adapterCategorias;
    private adapterCargarTemasRegistrarNoticia adapterTemas;
    private Bitmap bitmapImagenNoticia;
    private Boolean verificarBoolean = true;
    private Button buttonSeleccionarImagen, buttonRegistrarNoticia;
    private ProgressDialog progressDialogSubir;
    private ImageView imageViewSeleccionar;
    private EditText editTextNombreNoticia, editTextDescripcion;
    private String strNombreImagen, strUrl;
    private int intResult;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_noticia_administrador);
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
        editTextNombreNoticia = (EditText)findViewById(R.id.EDT_NombreNoticia_ATVRegistrarNoticiaAdministrador);
        editTextDescripcion = (EditText)findViewById(R.id.EDT_DescripcionNoticia_ATVRegistrarNoticiaAdministrador);
        imageViewSeleccionar = (ImageView)findViewById(R.id.IMV_ImagenNoticia_ATVRegistrarNoticiaAdministrador);
        buttonSeleccionarImagen = (Button)findViewById(R.id.BTN_AbrirGaleria_ATVRegistrarNoticiaAdministrador);
        buttonRegistrarNoticia = (Button)findViewById(R.id.BTN_RegistrarNoticia_ATVRegistrarNoticiaAdministrador);
        spinnerCategoria = (Spinner)findViewById(R.id.SPN_SeleccionarCategoria_ATVRegistrarNoticiaAdministrador);
        spinnerTema = (Spinner)findViewById(R.id.SPN_SeleccionarTema_ATVRegistrarNoticiaAdministrador);
        temasList = new ArrayList<Temas>();
        objNoticias = new Logica.Noticias();
        categoriasList = new ArrayList<Categorias>();
        objCategorias = new Logica.Categorias();
        objTemas = new Logica.Temas();
        objUsuario = new Usuarios();
        Listar_Temas();
        Listar_Categorias();
        buttonSeleccionarImagen.setOnClickListener(this);
        buttonRegistrarNoticia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Registrar_Noticia();
            }
        });
    }

    public void Listar_Categorias(){
        Thread threadListarCategoria = new Thread(){
            Handler handlerListarCategoria = new Handler();

            @Override
            public void run() {
                categoriasList = objCategorias.Listar_Categorias();
                handlerListarCategoria.post(new Runnable() {
                    @Override
                    public void run() {
                        if(categoriasList != null){
                            adapterCategorias = new adapterCargarCategoriaRegistrarNoticia(registrarNoticiaAdministrador.this, android.R.layout.simple_expandable_list_item_1, categoriasList);
                            spinnerCategoria.setAdapter(adapterCategorias);
                        }
                    }
                });
            }
        };threadListarCategoria.start();
    }

    public void Listar_Temas(){
        Thread threadListarTemas = new Thread(){
            Handler handlerListarTemas = new Handler();
            @Override
            public void run() {
                temasList = objTemas.Listar_Temas(0);
                handlerListarTemas.post(new Runnable() {
                    @Override
                    public void run() {
                        if(temasList != null){
                            adapterTemas = new adapterCargarTemasRegistrarNoticia(registrarNoticiaAdministrador.this, android.R.layout.simple_expandable_list_item_1, temasList);
                            spinnerTema.setAdapter(adapterTemas);
                        }
                    }
                });
            }
        };threadListarTemas.start();
    }


    public void Registrar_Noticia(){
        Thread threadRegistrarNoticia = new Thread(){
            Handler handlerRegistrarNoticia = new Handler();

            @Override
            public void run() {
                intResult = objNoticias.Registrar_Noticia(Integer.parseInt(objUsuario.Obtener_VariableSesion(getApplicationContext(), "Id_Usuario")), editTextNombreNoticia.getText().toString(), editTextNombreNoticia.getText().toString(), strUrl);
                handlerRegistrarNoticia.post(new Runnable() {
                    @Override
                    public void run() {
                        if(intResult == 1){
                            Log.e("Hola Jhon ", "Resgistrado");

                        }else {
                            Log.e("Hola Jhon ", " Error");
                        }
                    }
                });
            }
        }; threadRegistrarNoticia.start();
    }
    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        intent.setType("image/");
        intent.setAction(intent.ACTION_GET_CONTENT);
        startActivityForResult(intent.createChooser(intent, "Seleccionar Imagen"), 1);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == RESULT_OK && data.getData() != null){
            Uri uri = data.getData();
            try {

                bitmapImagenNoticia = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                imageViewSeleccionar.setImageBitmap(bitmapImagenNoticia);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            ImageUploadToServerFunction();
        }
    }

    public void ImageUploadToServerFunction(){
        ByteArrayOutputStream byteArrayOutputStreamObject;
        byteArrayOutputStreamObject = new ByteArrayOutputStream();
        bitmapImagenNoticia.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStreamObject);
        byte[] bytes = byteArrayOutputStreamObject.toByteArray();
        strUrl = Base64.encodeToString(bytes, Base64.DEFAULT);
    }
}
