package com.example.asus.retamev2;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import Datos.Categorias;
import adapterAdministrador.adapterAdCategoriasRecyclerView;


public class categoriaAdministrador extends Fragment {
    private List<Categorias> categoriasList;
    protected adapterAdCategoriasRecyclerView categoriasAdapter;
    private RecyclerView recyclerViewCategoria;
    private Logica.Categorias objCategorias;

    public categoriaAdministrador() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_categoria_administrador, container, false);
        recyclerViewCategoria = (RecyclerView)view.findViewById(R.id.RCV_ListarCategorias_FRGCategoriaAdministrador);
        categoriasList = new ArrayList<Categorias>();
        objCategorias = new Logica.Categorias();
        Listar_Categorias();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerViewCategoria.setLayoutManager(layoutManager);
        recyclerViewCategoria.setItemAnimator(new DefaultItemAnimator());

        return view;
    }



    public void Listar_Categorias(){
        Thread threadListarCategorias = new Thread(){
            Handler handlerListarCategoria = new Handler();
            @Override
            public void run() {
                categoriasList = objCategorias.Listar_Categorias();
                handlerListarCategoria.post(new Runnable() {
                    @Override
                    public void run() {
                        if(categoriasList.equals(null)){
                            Log.e("Hola Jhon ", " Lista Nula");
                        }else{
                            Log.e("Hola Jhon ", " Vamos bien");
                            categoriasAdapter = new adapterAdCategoriasRecyclerView(getContext().getApplicationContext(), categoriasList);
                            recyclerViewCategoria.setAdapter(categoriasAdapter);
                            categoriasAdapter.notifyDataSetChanged();
                        }

                    }
                });
            }
        };threadListarCategorias.start();
    }
}
