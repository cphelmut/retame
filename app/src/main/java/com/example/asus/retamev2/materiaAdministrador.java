package com.example.asus.retamev2;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.MenuPopupWindow;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

import Datos.Materias;
import Datos.Temas;
import adapterAdministrador.adapterAdMateriaRecyclerView;

public class materiaAdministrador extends Fragment {
    private RecyclerView recyclerViewTemas;
    private adapterAdMateriaRecyclerView adapterMateria;
    private List<Materias> materiasList;
    private Logica.Materias objMaterias;
    private PopupMenu popup;


    public materiaAdministrador() {
        // Required empty public constructor
    }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_materia_administrador, container, false);
        recyclerViewTemas = (RecyclerView)view.findViewById(R.id.RCV_ListarMaterias_FRGMateriaAdministrador);
        adapterMateria = new adapterAdMateriaRecyclerView(getContext());
        adapterMateria.setFrgMateria(this);
        materiasList = new ArrayList<Materias>();
        objMaterias = new Logica.Materias();
        Listar_Materias();

        return view;
    }

    public void Listar_Materias(){
        Thread threadListarMaterias = new Thread(){
            Handler handlerListarMaterias = new Handler();

            @Override
            public void run() {
                materiasList = objMaterias.Listar_Materias(1);
                handlerListarMaterias.post(new Runnable() {
                    @Override
                    public void run() {
                        if(materiasList.equals(null)){

                        }else {
                            adapterMateria.setMateriasList(materiasList);
                            recyclerViewTemas.setAdapter(adapterMateria);
                            adapterMateria.notifyDataSetChanged();
                        }
                    }
                });
            }
        };threadListarMaterias.start();
    }


    public void Cambiar_Estado(final int intIdMateria, final int intEstado){
        Thread threadCambiarEstado = new Thread(){
            Handler handlerCambiarEstado = new Handler();
            int intResult;
            @Override
            public void run() {
                intResult = objMaterias.Cambiar_Estado(intIdMateria, intEstado);
                handlerCambiarEstado.post(new Runnable() {
                    @Override
                    public void run() {
                        if(intResult == 0){
                            if(intEstado == 0) {
                                Snackbar.make(getView(), "El usuario Bloqueado", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                            }else {
                                Snackbar.make(getView(), "El usuario Desbloqueado", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                            }
                        }
                    }
                });
            }
        }; threadCambiarEstado.start();
    }

    public void Menu_Noticia(View view, int position) {
        popup = new PopupMenu(view.getContext(), view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_tema_administrador, popup.getMenu());
        popup.setOnMenuItemClickListener(new MenuClickListener(position));
        popup.show();
        Materias objMaterias = adapterMateria.getMateriasList().get(position);

        if (objMaterias.getEstado_Materia() == 1) {
            popup.getMenu().getItem(1).setTitle("Bloquear");
        } else {
            popup.getMenu().getItem(1).setTitle("Desbloquear");
        }
    }

    class MenuClickListener implements PopupMenu.OnMenuItemClickListener {
        protected int pos;

        public MenuClickListener(int position) {
            this.pos = position;
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            Materias objMaterias = adapterMateria.getMateriasList().get(pos);
            switch (item.getItemId()) {
                case R.id.ITM_ModificarTema_TemaAministrador:
                    if (objMaterias.getId_Materia() != 0) {
                        Bundle bundleTipo = new Bundle();
                        bundleTipo.putInt("opcion", 2);
                        bundleTipo.putInt("Id_Materia", objMaterias.getId_Materia());
                        Intent intentTipo = new Intent(getActivity(), registrarMateriaAdministrador.class);
                        intentTipo.putExtras(bundleTipo);
                        startActivity(intentTipo);
                    } else {
                        Snackbar.make(getView(), "Hola Jhon el PDF no está disponible", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                    break;
                case R.id.ITM_CambiarEstadoTema_TemaAministrador:
                    if (objMaterias.getId_Materia() != 0) {
                        if (objMaterias.getEstado_Materia() == 0) {
                            Cambiar_Estado(objMaterias.getId_Materia(), 1);
                            objMaterias.setEstado_Materia(1);
                        } else {
                            Cambiar_Estado(objMaterias.getId_Materia(), 0);
                            objMaterias.setEstado_Materia(0);
                        }
                    }
                    break;
                default:
                    Log.e("No ", "Valido MEnu");
                    break;
            }
            return false;
        }
    }
}
