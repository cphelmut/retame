package com.example.asus.retamev2;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import interfaces.IAppManager;
import io.github.kexanie.library.MathView;
import service.IMService;

public class cargarNoticia extends AppCompatActivity {
    private MathView mathViewCargarNoticia;
    private IAppManager imService;
    private String urlNoticia;


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
        setContentView(R.layout.activity_cargar_noticia);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mathViewCargarNoticia = (MathView)findViewById(R.id.MTV_CargarNoticia_ATVCargarNoticia);

        Bundle bundleCargarPublicacion = getIntent().getExtras();
        if (bundleCargarPublicacion != null) {
            setTitle(bundleCargarPublicacion.getString("Nombre_Noticia"));
            urlNoticia = bundleCargarPublicacion.getString("PDF");
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
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
                String dato = imService.Cargar_Archivo(urlNoticia);
                mathViewCargarNoticia.setText(dato);
            }
        }); threadAbrir.start();
    }
}
