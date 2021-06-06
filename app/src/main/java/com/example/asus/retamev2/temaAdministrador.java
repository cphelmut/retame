package com.example.asus.retamev2;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.MenuPopupWindow;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import Datos.Categorias;
import Datos.Noticias;
import Datos.Personas;
import Datos.Temas;
import Datos.Usuarios;
import adapter.AdapterTemasReciclerView;
import adapterAdministrador.adapterAdTemaRecyclerView;
import interfaces.IAppManager;


public class temaAdministrador extends Fragment implements SearchView.OnQueryTextListener{
    private RecyclerView recyclerViewListarTemasAdministrador;
    private List<Temas> listTema;
    private adapterAdTemaRecyclerView adapterTemas;
    private Logica.Temas objTemas;
    private PopupMenu popup;
    private Usuarios objUsuarios;
    private SearchView searchViewTema;
    private int intIdTema, intEstado;

    public temaAdministrador() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tema_adminstrador, container, false);
        recyclerViewListarTemasAdministrador = (RecyclerView) view.findViewById(R.id.RCV_ListarTemas_FRGTemasAdministrador);
        searchViewTema = (SearchView)view.findViewById(R.id.SCV_BuscarTema_FRGTemasAdministrador);
        searchViewTema.setOnQueryTextListener(this);
        adapterTemas = new adapterAdTemaRecyclerView(getContext().getApplicationContext());
        adapterTemas.setFrgTemaAdministrador(this);
        objUsuarios = new Usuarios();
        listTema = new ArrayList<Temas>();
        objTemas = new Logica.Temas();
        Listar_Temas();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerViewListarTemasAdministrador.setLayoutManager(layoutManager);
        recyclerViewListarTemasAdministrador.setItemAnimator(new DefaultItemAnimator());
        return view;
    }


    public void Listar_Temas() {
        Thread threadListarTemas = new Thread() {
            Handler handlerListarTemas = new Handler();
            @Override
            public void run() {
                listTema = objTemas.Listar_Temas(1);
                handlerListarTemas.post(new Runnable() {
                    @Override
                    public void run() {
                        if (listTema.equals(null)) {
                            Log.e("Hola Jhon ", "La lista esta null");
                        } else {
                            adapterTemas.setTemasList(listTema);
                            recyclerViewListarTemasAdministrador.setAdapter(adapterTemas);
                            adapterTemas.notifyDataSetChanged();
                        }
                    }
                });
            }
        };
        threadListarTemas.start();
    }

    public void Menu_Noticia(View view, int position) {
        popup = new PopupMenu(view.getContext(), view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_tema_administrador, popup.getMenu());
        popup.setOnMenuItemClickListener(new MenuClickListener(position));
        popup.show();
        Temas objTemas = adapterTemas.getTemasList().get(position);

        if (objTemas.getEstado_Tema() == 1) {
            popup.getMenu().getItem(1).setTitle("Bloquear");
        } else {
            popup.getMenu().getItem(1).setTitle("Desbloquear");
        }
    }

    public void Cambiar_Estado(final int intIdUsuario, final int intEstado){
        Thread threadCambiarEstado = new Thread(){
            Handler handlerCambiarEstado = new Handler();
            int intResult;
            @Override
            public void run() {
                intResult = objTemas.Cambiar_Estado(intIdTema, intEstado );
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

    public void Buscar_Temas(final String strBusqueda) {
        Thread threadBuscarTemas = new Thread() {
            Handler handlerBuscarTemas = new Handler();
            @Override
            public void run() {
                listTema = objTemas.Buscar_Temas(strBusqueda);
                handlerBuscarTemas.post(new Runnable() {
                    @Override
                    public void run() {
                        if (listTema.equals(null)) {
                            Log.e("Hola Jhon ", "La lista esta null");
                        } else {
                            adapterTemas.setTemasList(listTema);
                            recyclerViewListarTemasAdministrador.setAdapter(adapterTemas);
                            adapterTemas.notifyDataSetChanged();
                        }
                    }
                });
            }
        };
        threadBuscarTemas.start();
    }




    @Override
    public boolean onQueryTextSubmit(String query) {
        Buscar_Temas(query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if(newText.equals("")){
            Listar_Temas();
        }
        return false;
    }

    class MenuClickListener implements PopupMenu.OnMenuItemClickListener {
        protected int pos;

        public MenuClickListener(int position) {
            this.pos = position;
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            Temas objTemas = adapterTemas.getTemasList().get(pos);
            switch (item.getItemId()) {
                case R.id.ITM_ModificarTema_TemaAministrador:
                    if (objTemas.getId_Tema() != 0) {
                        Bundle bundleTipo = new Bundle();
                        bundleTipo.putInt("opcion", 2);
                        bundleTipo.putInt("Id_Tema", objTemas.getId_Tema());
                    //    bundleTipo.putString("Rol", objUsuarios.Obtener_VariableSesion(getContext().getApplicationContext(), "Rol"));
                        Intent intentTipo = new Intent(getActivity(), registrarTemaAdministrador.class);
                        intentTipo.putExtras(bundleTipo);
                        startActivity(intentTipo);
                    } else {
                        Snackbar.make(getView(), "Hola Jhon el PDF no está disponible", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                    break;
                case R.id.ITM_CambiarEstadoTema_TemaAministrador:
                    if (objTemas.getId_Tema() != 0) {
                        if (objUsuarios.getEstado() == 0) {
                              Cambiar_Estado(objUsuarios.getId_Usuario(), 1);
                              objTemas.setEstado_Tema(1);
                        } else {
                            Cambiar_Estado(objUsuarios.getId_Usuario(), 0);
                            objTemas.setEstado_Tema(0);
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
