package com.example.asus.retamev2;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import Datos.Noticias;
import Datos.Usuarios;
import adapter.noticiasAdapterReciclerView;
import adapterAdministrador.adapterAdUsuariosRecyclerView;
import interfaces.IAppManager;


public class usuarioAdministrador extends Fragment implements SearchView.OnQueryTextListener, SearchView.OnCloseListener {
    private adapterAdUsuariosRecyclerView usuariosAdapter;
    public IAppManager imService;
    private SwipeRefreshLayout swipeRefreshLayout;
    public RecyclerView recyclerViewUsuariosFragment;
    private List<Usuarios> usuariosList;
    private Logica.Usuarios objUsuarios;
    private SearchView searchViewUsuario;
    protected PopupMenu popup = null;
    public usuarioAdministrador() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_usuario_administrador, container, false);
        recyclerViewUsuariosFragment = (RecyclerView)view.findViewById(R.id.RCV_ListarUsuarios_FRGUsuarioAdministrador);
        searchViewUsuario = (SearchView)view.findViewById(R.id.SCV_BuscarUsuario_FRGUsuarioAdministrador);
        searchViewUsuario.setOnQueryTextListener(this);
        searchViewUsuario.setOnCloseListener(this);
        usuariosList = new ArrayList<Usuarios>();
        objUsuarios = new Logica.Usuarios();
        usuariosAdapter = new adapterAdUsuariosRecyclerView(getContext().getApplicationContext());
        usuariosAdapter.setFragmentUsuarioAdmin(this);
        Listar_Usuarios();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerViewUsuariosFragment.setLayoutManager(layoutManager);
        recyclerViewUsuariosFragment.setItemAnimator(new DefaultItemAnimator());
        return view;
    }

    public void Listar_Usuarios(){
        final Thread threadListarUsuarios = new Thread() {
            Handler handlerListarUsuario = new Handler();

            @Override
            public void run() {
                usuariosList = objUsuarios.Listar_Usuarios();
                handlerListarUsuario.post(new Runnable() {
                    @Override
                    public void run() {
                        if(usuariosList.equals(null)){
                            Log.e("Hola Jhon", "Tenemos Problemas Lista nula");
                        }else {

                            usuariosAdapter.setUsariosList(usuariosList);
                            recyclerViewUsuariosFragment.setAdapter(usuariosAdapter);
                            usuariosAdapter.notifyDataSetChanged();
                        }
                    }
                });
            }
        };
        threadListarUsuarios.start();
    }


    public void Menu_Noticia(View view, int position) {
        popup = new PopupMenu(view.getContext(), view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_usuario_administrador, popup.getMenu());
        popup.setOnMenuItemClickListener(new MenuClickListener(position));
        popup.show();
        Datos.Usuarios objUsuario = usuariosAdapter.getUsariosList().get(position);
        if (objUsuario.getEstado() == 1) {
            popup.getMenu().getItem(1).setTitle("Bloquear");
        }else {
            popup.getMenu().getItem(1).setTitle("Desbloquear");
        }
    }

    public void Cambiar_Estado(final int intIdUsuario, final int intEstado){
        Thread threadCambiarEstado = new Thread(){
            Handler handlerCambiarEstado = new Handler();
            int intResult;

            @Override
            public void run() {
                intResult = objUsuarios.Cambiar_Estado(intIdUsuario, intEstado );
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


    public void Buscar_Usuarios(final String strBusqueda){

        final Thread threadBuscarUsuarios = new Thread() {
            Handler handlerBuscarUsuario = new Handler();

            @Override
            public void run() {
                usuariosList = objUsuarios.Buscar_Usuarios(strBusqueda);
                handlerBuscarUsuario.post(new Runnable() {
                    @Override
                    public void run() {
                        if(usuariosList.equals(null)){
                            Log.e("Hola Jhon", "Tenemos Problemas Lista nula");
                        }else {

                            usuariosAdapter.setUsariosList(usuariosList);
                            recyclerViewUsuariosFragment.setAdapter(usuariosAdapter);
                            usuariosAdapter.notifyDataSetChanged();
                        }
                    }
                });
            }
        };
        threadBuscarUsuarios.start();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        Buscar_Usuarios(query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if(newText.equals("")){
            Listar_Usuarios();
        }
        return false;
    }

    @Override
    public boolean onClose() {
      //  Log.e("Hola Jhon ", " Estamos en close");
       // Listar_Usuarios();
        return false;
    }

    class MenuClickListener implements PopupMenu.OnMenuItemClickListener{
        protected int pos;
        public MenuClickListener(int position){
            this.pos = position;
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            Datos.Usuarios objUsuarios = usuariosAdapter.getUsariosList().get(pos);
            switch (item.getItemId()){
                case R.id.ITM_ModificarUsuario_UsuarioAministrador:
                    if(objUsuarios.getId_Usuario() != 0){
                        Bundle bundleTipo = new Bundle();
                        bundleTipo.putInt("opcion", 2);
                        bundleTipo.putInt("Id_Usuario", objUsuarios.getId_Usuario());
                        bundleTipo.putString("Rol", objUsuarios.Obtener_VariableSesion(getContext().getApplicationContext(), "Rol"));
                        Intent intentTipo = new Intent(getActivity(), registrarUsuarioAdministrador.class);
                        intentTipo.putExtras(bundleTipo);
                        startActivity(intentTipo);
                    }else{
                        Snackbar.make(getView(), "Hola Jhon el PDF no está disponible", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                    break;
                case R.id.ITM_CambiarEstadoUsuario_UsuarioAministrador:
                    if(objUsuarios.getId_Usuario() != 0) {
                        if (objUsuarios.getEstado() == 0) {
                            Cambiar_Estado(objUsuarios.getId_Usuario(), 1);
                            objUsuarios.setEstado(1);

                        } else {
                            Cambiar_Estado(objUsuarios.getId_Usuario(), 0);
                            objUsuarios.setEstado(0);
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
