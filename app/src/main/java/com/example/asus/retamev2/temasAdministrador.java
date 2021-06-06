package com.example.asus.retamev2;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import Datos.Temas;
import adapter.AdapterTemasReciclerView;

public class temasAdministrador extends AppCompatActivity {
    private List<Temas> temasList;
    protected AdapterTemasReciclerView adapterTemas;
    private Logica.Temas objTemas;
    private RecyclerView recyclerViewListarTemas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temas_administrador);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        recyclerViewListarTemas = (RecyclerView)findViewById(R.id.RCV_ListarTemas_FRGTemasAdministrador);
        objTemas = new Logica.Temas();
        temasList = new ArrayList<Temas>();
        adapterTemas = new AdapterTemasReciclerView(getApplicationContext());

        Listar_temas();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }


    public void Listar_temas(){
        Thread threadListarTemas = new Thread(){
            Handler handlerListarTemas = new Handler();
            @Override
            public void run() {
               temasList = objTemas.Listar_Temas(1);
               handlerListarTemas.post(new Runnable() {
                   @Override
                   public void run() {
                       if(temasList.equals(null)){
                           Log.e("Hola Jhon", " Lista Nula");
                       }else{
                           adapterTemas.setTemasList(temasList);
                           recyclerViewListarTemas.setAdapter(adapterTemas);
                           adapterTemas.notifyDataSetChanged();
                       }
                   }
               });
            }
        };threadListarTemas.start();
    }
}
