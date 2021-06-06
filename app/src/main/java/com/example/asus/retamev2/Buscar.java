package com.example.asus.retamev2;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;

import Datos.Noticias;
import interfaces.IAppManager;
import service.IMService;

public class Buscar extends Fragment {
    protected IAppManager imService;
    public List<Noticias>noticiasBusquedaList;
    Logica.Noticias objNoticias;

    public class MessageReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundleExtras = intent.getExtras();
            if(bundleExtras != null){

            }
        }
    }

    private ServiceConnection mConnection = new ServiceConnection(){
        @Override
        public void onServiceConnected(ComponentName name, IBinder iBinder) {
            imService = ((IMService.IMBinder)iBinder).getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    public Buscar() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_buscar, container,false);
        noticiasBusquedaList = new ArrayList<Noticias>();
        objNoticias = new Logica.Noticias();
        Bundle bundleBuscar = getActivity().getIntent().getExtras();
        if (bundleBuscar != null) {
            Log.e("Hola Jhon ", "Esta es la palabra clave "+bundleBuscar);
        }
        return view;
    }


}
