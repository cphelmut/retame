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
import adapter.buscarNoticiasAdapterReciclerView;
import interfaces.IAppManager;
import service.IMService;

public class buscarNoticia extends AppCompatActivity {
    protected IAppManager imService;
    public List<Noticias> noticiasBusquedaList;
    public buscarNoticiasAdapterReciclerView buscarNoticiasAdapter;
    protected Logica.Noticias objNoticias;
    protected RecyclerView recyclerViewBuscarNoticias;
    private String strBusqueda;
    MessageReceiver messageReceiver;


    public class MessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundleExtras = intent.getExtras();
            if(bundleExtras != null){
                if(intent.getStringExtra("JsonBusqueda").equals("1")) {
                    noticiasBusquedaList = imService.getListNoticiaBusqueda();
                    if(noticiasBusquedaList.equals(null)){
                        Log.e("Hola Jhon ", " Estamos en Null");
                        Snackbar.make(null, "Sin resultados", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }else {
                        Log.e("Hola Jhon ", " Estamos bien");
                        buscarNoticiasAdapter.setBuscarnoticiaList(noticiasBusquedaList);
                        recyclerViewBuscarNoticias.setAdapter(buscarNoticiasAdapter);
                        buscarNoticiasAdapter.notifyDataSetChanged();
                    }
                }
            }
        }
    }

    private ServiceConnection mConnection = new ServiceConnection(){
        @Override
        public void onServiceConnected(ComponentName name, IBinder iBinder) {
            imService = ((IMService.IMBinder)iBinder).getService();
            Log.e("Hola Jhon", "Estamos en service "+strBusqueda);
            Buscar_Noticias(strBusqueda);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            imService = null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_noticia);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        noticiasBusquedaList = new ArrayList<Noticias>();
        objNoticias = new Logica.Noticias();
        messageReceiver = new MessageReceiver();

        recyclerViewBuscarNoticias = (RecyclerView)findViewById(R.id.RCV_BuscarNoticias_ATVBuscarNoticias);
        Bundle bundleBuscar = getIntent().getExtras();
        if (bundleBuscar != null) {
            Log.e("Hola Jhon ", "Esta es la palabra clave "+bundleBuscar.get("Parametro_Busqueda"));
            strBusqueda = bundleBuscar.get("Parametro_Busqueda").toString();
        }
        buscarNoticiasAdapter = new buscarNoticiasAdapterReciclerView(this);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewBuscarNoticias.setLayoutManager(layoutManager);
        recyclerViewBuscarNoticias.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public void onPause() {
        super.onPause();
        unregisterReceiver(messageReceiver);
        unbindService(mConnection);
    }


    @Override
    public void onResume() {
        super.onResume();
        bindService(new Intent(getApplicationContext(), IMService.class), mConnection, Context.BIND_AUTO_CREATE);
        registerReceiver( messageReceiver ,  new IntentFilter("Buscar_Noticia"));
    }


    public void Buscar_Noticias(final String strBusqueda){
        final String strBusquedaNoticias = strBusqueda;

        Thread threadBuscarNoticia = new Thread(new Runnable() {
            @Override
            public void run() {
                imService.Buscar_Noticias(strBusquedaNoticias);
                Log.e("Hola Jhon", " Estamos buscando"+strBusqueda);
            }
        });
        threadBuscarNoticia.start();
    }

    public void Asignar_Adapter(){
        noticiasBusquedaList = imService.getListNoticiaBusqueda();
        if(noticiasBusquedaList.equals(null)){
            Log.e("Hola Jhon ", " Estamos en Null");
            Snackbar.make(null, "Sin resultados", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }else {
            Log.e("Hola Jhon ", " Estamos bien");
            buscarNoticiasAdapter.setBuscarnoticiaList(noticiasBusquedaList);
            recyclerViewBuscarNoticias.setAdapter(buscarNoticiasAdapter);
            buscarNoticiasAdapter.notifyDataSetChanged();
        }
    }

}
