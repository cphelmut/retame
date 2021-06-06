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
import Datos.Personas;
import Datos.Temas;
import Datos.Usuarios;
import adapter.AdapterTemasReciclerView;
import interfaces.IAppManager;
import service.IMService;


public class temas extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private RecyclerView recyclerViewListarTemas;
    private List<Temas> listTema;
    private AdapterTemasReciclerView adapterTemasReciclerView;
    public IAppManager imService;
    protected Noticias objNoticias;
    protected Usuarios objUsuario;
    protected Categorias objcategoria;
    protected Personas objPersona;
    private SwipeRefreshLayout swipeRefreshLayout;
    MessageReceiver myReceiver = null;

    @Override
    public void onRefresh() {

    }


    public class MessageReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle extras = intent.getExtras();
            if(extras != null){
                String action = intent.getAction();
                if(intent.getStringExtra("JsonTemas").equals("1")){
                    listTema = imService.getListTemas();
                    if(listTema.equals(null)){
                        Log.e("Hola Jhon Lista", " recuperada");
                    }else{
                        adapterTemasReciclerView.setTemasList(listTema);
                        recyclerViewListarTemas.setAdapter(adapterTemasReciclerView);
                        adapterTemasReciclerView.notifyDataSetChanged();
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
            imService = ((IMService.IMBinder) iBinder).getService();
            Thread threadNoticias = new Thread(){
                Handler handerNoticias = new Handler();
                @Override
                public void run() {
                    String strresult = imService.Listar_Temas();
                }
            };
            threadNoticias.start();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            imService = null;
        }
    };

    public temas() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_temas, container, false);
        swipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.SWP_Temas);
        myReceiver = new MessageReceiver();
        recyclerViewListarTemas = (RecyclerView)view.findViewById(R.id.RCV_ListarTemas);
        listTema = new ArrayList<Temas>();
        adapterTemasReciclerView = new AdapterTemasReciclerView(getContext());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerViewListarTemas.setLayoutManager(layoutManager);
        recyclerViewListarTemas.setItemAnimator(new DefaultItemAnimator());
        swipeRefreshLayout.setOnRefreshListener(this);
        return  view;
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
        getActivity().registerReceiver( myReceiver ,  new IntentFilter("ListaTemas"));
    }




}
