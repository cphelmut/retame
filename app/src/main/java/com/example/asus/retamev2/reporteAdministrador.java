package com.example.asus.retamev2;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import Datos.Reportes;
import adapterAdministrador.adapterAdReportesRecyclerView;


public class reporteAdministrador extends Fragment {
    private RecyclerView recyclerViewReportes;
    private List<Reportes> reportesList;
    private adapterAdReportesRecyclerView adapterReportes;
    private Logica.Reportes objReportes;



    public reporteAdministrador() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_reporte_administrador, container, false);
        recyclerViewReportes = (RecyclerView)view.findViewById(R.id.RCV_ListarReportes_FRGReportesAdministrador);
        reportesList = new ArrayList<Reportes>();
        objReportes = new Logica.Reportes();
        adapterReportes = new adapterAdReportesRecyclerView(getContext().getApplicationContext());
        adapterReportes.setFrgReporte(this);
        Listar_Reportes();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerViewReportes.setLayoutManager(layoutManager);
        recyclerViewReportes.setItemAnimator(new DefaultItemAnimator());
        return view;
    }


    public void Listar_Reportes(){

        final Thread threadListarReportes = new Thread(){
            Handler handlerListarReportes = new Handler();
            @Override
            public void run() {
                reportesList = objReportes.Listar_Reportes();
                handlerListarReportes.post(new Runnable() {
                    @Override
                    public void run() {
                        if(reportesList.equals(null)){

                        }else {
                            adapterReportes.setReportesList(reportesList);
                            recyclerViewReportes.setAdapter(adapterReportes);
                            adapterReportes.notifyDataSetChanged();
                        }
                    }
                });
            }
        };threadListarReportes.start();
    }

}
