package com.example.asus.retamev2;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import Datos.Categorias;
import Datos.Noticias;
import Datos.Usuarios;
import adapter.ejerciciosAdapterRecivlerView;
import interfaces.IAppManager;
import service.IMService;

public class ejemplos extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    protected Noticias objNoticias;
    protected Usuarios objUsuario;
    protected Categorias objcategoria;
    private RecyclerView recyclerViewFragmentEjercicio;
    private List<Noticias> ejemplosList;
    public IAppManager imService;
    RecyclerView recyclerViewNoticiaFragment;
    Boolean myReceiverIsRegistered = false;
    MessageReceiver myReceiver = null;
    SwipeRefreshLayout swipeRefreshLayout;
    ejerciciosAdapterRecivlerView ejerciciosAdapter;

    @Override
    public void onRefresh() {

    }


    public class MessageReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle extras = intent.getExtras();
            if(extras != null){
                String action = intent.getAction();
                if(intent.getStringExtra("JsonEjemplos").equals("1")){
                    ejemplosList = imService.getListEjemplos();
                    if(ejemplosList.equals(null)){
                        Log.e("Hola Jhon Lista", " recuperada");
                    }else{
                        ejerciciosAdapter.setNoticiaList(ejemplosList);
                        recyclerViewFragmentEjercicio.setAdapter(ejerciciosAdapter);
                        ejerciciosAdapter.notifyDataSetChanged();
                    }
                }else {
                    Toast.makeText(getContext(), "No se puede actualizar las noticias", Toast.LENGTH_LONG);
                }
            }
        }
    }

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            imService = ((IMService.IMBinder)iBinder).getService();
            Listar_Noticias(2);
        }
        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            imService = null;
        }
    };

    public ejemplos() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_ejemplos, container, false);
        recyclerViewFragmentEjercicio = (RecyclerView)rootView.findViewById(R.id.recycler_view_fragmentEjercicios);
        myReceiver = new MessageReceiver();
        ejemplosList = new ArrayList<Noticias>();
        swipeRefreshLayout = (SwipeRefreshLayout)rootView.findViewById(R.id.swipe_refresh_layoutEjercicios);
        ejerciciosAdapter = new ejerciciosAdapterRecivlerView(getContext().getApplicationContext());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerViewFragmentEjercicio.setLayoutManager(layoutManager);
        recyclerViewFragmentEjercicio.setItemAnimator(new DefaultItemAnimator());
        swipeRefreshLayout.setOnRefreshListener(this);
        return rootView;
    }


    @Override
    public void onPause() {
        super.onPause();
        getActivity().unregisterReceiver(myReceiver);
        getActivity().unbindService(mConnection);
    }


    @Override
    public void onResume() {
        super.onResume();
        getActivity().bindService(new Intent(getContext(), IMService.class), mConnection, Context.BIND_AUTO_CREATE);
        getActivity().registerReceiver( myReceiver ,  new IntentFilter("ListaEjemplos"));
    }

    public void Listar_Noticias(final int intOpcion){
        Thread threadNoticias = new Thread(){
            Handler handerNoticias = new Handler();
            @Override
            public void run() {
                String strresult = imService.Listar_Ejemplos();
            }
        };
        threadNoticias.start();
    }


}
