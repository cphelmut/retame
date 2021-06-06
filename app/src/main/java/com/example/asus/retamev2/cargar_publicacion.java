package com.example.asus.retamev2;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import Datos.Noticias;
import adapter.AdapterCargarEjercicios;
import adapter.ejerciciosAdapterRecivlerView;
import interfaces.IAppManager;
import io.github.kexanie.library.MathView;
import service.IMService;

public class cargar_publicacion extends AppCompatActivity {
    private IAppManager imService;
    RecyclerView recyclerViewNoticiasSimilares;
    ejerciciosAdapterRecivlerView adapterNoticiasSimiliares;
    List<Noticias> listNoticia;
    MathView textViewDescripcion;

        private ServiceConnection mConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                imService = ((IMService.IMBinder) iBinder).getService();
                Obtener_Info();
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {
                imService = null;
            }
        };


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_cargar_publicacion);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            textViewDescripcion = (MathView) findViewById(R.id.formula_one);
            recyclerViewNoticiasSimilares = (RecyclerView) findViewById(R.id.RCV_NoticiasRelacionasdas_CargarPublicacion);
            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            });
            listNoticia = new ArrayList<Noticias>();
            adapterNoticiasSimiliares = new ejerciciosAdapterRecivlerView(getApplicationContext());
            //Intent intentCargarPublicacion = this.getIntent();
            Bundle bundleCargarPublicacion = getIntent().getExtras();
            if (bundleCargarPublicacion != null) {
                setTitle(bundleCargarPublicacion.getString("Nombre_Noticia"));
                //textViewDescripcion.setText(bundleCargarPublicacion.getString("PDF"));
            }
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 1, GridLayoutManager.HORIZONTAL, false);
            recyclerViewNoticiasSimilares.setLayoutManager(gridLayoutManager);
            recyclerViewNoticiasSimilares.setItemAnimator(new DefaultItemAnimator());

        }

    @Override
    public void onPause() {
        super.onPause();
        unbindService(mConnection);
    }


    @Override
    public void onResume() {
        super.onResume();
        bindService(new Intent(getApplicationContext(), IMService.class), mConnection, Context.BIND_AUTO_CREATE);
        //getActivity().registerReceiver( myReceiver ,  new IntentFilter("ListaNoticias"));
    }

    public void Obtener_Info(){
        Thread threadAbrir = new Thread(new Runnable() {
            @Override
            public void run() {

            }
        }); threadAbrir.start();
    }
}
