package com.example.asus.retamev2;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.os.Handler;
import java.util.List;
import android.support.v7.widget.PopupMenu;
import Datos.Noticias;
import Datos.Usuarios;
import adapter.DialogoSeleccion;
import adapter.noticiasAdapterReciclerView;
import interfaces.IAppManager;
import service.IMService;

public class principal extends Fragment implements   SwipeRefreshLayout.OnRefreshListener {
    private noticiasAdapterReciclerView noticiasAdapter;
    private List<Noticias> noticiaList;
    public IAppManager imService;
    private SwipeRefreshLayout swipeRefreshLayout;
    MessageReceiver myReceiver = null;
    public RecyclerView recyclerViewNoticiaFragment, recyclerViewOtros;
    private AlertDialog.Builder builderSingle;

    public class MessageReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle extras = intent.getExtras();
            if (extras != null) {
                if (intent.getStringExtra("JsonNoticias").equals("1")) {
                    noticiaList = imService.getListNoticias();
                    if (noticiaList.equals(null)) {
                        Log.e("Hola Jhon Lista", " recuperada");
                    } else {
                        noticiasAdapter.setNoticiaList(noticiaList);
                        recyclerViewNoticiaFragment.setAdapter(noticiasAdapter);
                        noticiasAdapter.notifyDataSetChanged();
                    }
                } else {
                    Toast.makeText(getContext(), "No se puede actualizar las noticias", Toast.LENGTH_LONG);
                }
            }
        }
    }

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            imService = ((IMService.IMBinder) iBinder).getService();
            Log.e("Hola Jhon", "Estamos en on connection");
            Listar_Noticias(2);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            imService = null;
        }
    };

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);
        noticiaList.clear();
        Listar_Noticias(1);
        swipeRefreshLayout.setRefreshing(false);
    }


    public principal() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_principal, container, false);
        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_refresh_layout);
        // serviceConnection = new Serviceconnection();
        // imService  = serviceConnection.getImService();
        swipeRefreshLayout.setOnRefreshListener(this);
        myReceiver = new MessageReceiver();
        recyclerViewNoticiaFragment = (RecyclerView) rootView.findViewById(R.id.recycler_view_fragment);
        noticiasAdapter = new noticiasAdapterReciclerView(getContext().getApplicationContext());
        noticiasAdapter.setFragmentPrincipal(this);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        final GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 6);
        recyclerViewNoticiaFragment.setLayoutManager(layoutManager);
        recyclerViewNoticiaFragment.setItemAnimator(new DefaultItemAnimator());
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
        getActivity().registerReceiver(myReceiver, new IntentFilter("ListaNoticias"));
    }


    public void imagepos(int i) {
        //onImage click comes here
        Log.e("Image pos", "position" + i);
        //Intent intent = new Intent(getContext(), Profile.class);
        //startActivity(intent);
    }

    public void Registrar_Gustar(int i) {
        final int Id_Noticia = i;
        final Thread threadLogin = new Thread() {
            Handler handerLogin = new Handler();

            @Override
            public void run() {
                boolean booleanRegGus = imService.Registrar_Gustar(Id_Noticia);
                if (booleanRegGus = true) {
                    Log.e("Hola Jhon", " Like Registrado");
                    Listar_Noticias(1);
                } else {
                    Log.e("Error ", "Registrar Like");
                }

            }
        };
        threadLogin.start();
    }


    public void Abrir_Noticia(int position) {
        Datos.Noticias objNoticia = noticiasAdapter.getNoticiaList().get(position);
        Log.e("Hola Jhon", "El pdf es " + objNoticia.getArchivo_Noticia());
        if (objNoticia.getArchivo_Noticia() != null) {
            Bundle bundleCargarNoticia = new Bundle();
            bundleCargarNoticia.putInt("Id_Noticia", objNoticia.getId_Noticia());
            bundleCargarNoticia.putString("Nombre_Noticia", objNoticia.getNombre_Noticia());
            bundleCargarNoticia.putString("Descripcion", objNoticia.getDescripcion_Noticia());
            bundleCargarNoticia.putString("Nombre_Usuario", objNoticia.getObjusuario().getNombre_Usuario());
            bundleCargarNoticia.putInt("Id_Usuario", objNoticia.getObjusuario().getId_Usuario());
            bundleCargarNoticia.putString("PDF", objNoticia.getArchivo_Noticia());
            Intent intentCargarNoticia = new Intent(getActivity(), cargarNoticia.class);
            intentCargarNoticia.putExtras(bundleCargarNoticia);
            startActivity(intentCargarNoticia);
        } else {
            Snackbar.make(getView(), "Hola Jhon el PDF no está disponible", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    }

    public void Listar_Noticias(final int intOpcion) {
        Thread threadNoticias = new Thread() {
            Handler handerNoticias = new Handler();

            @Override
            public void run() {
                String strresult = imService.Listar_Noticias(intOpcion);
            }
        };
        threadNoticias.start();
    }


    public void Menu_Noticia(View view, int position) {
        Usuarios usuario = new Usuarios();
        Datos.Noticias objNoticia = noticiasAdapter.getNoticiaList().get(position);
        if (objNoticia.getObjusuario().getId_Usuario() != Integer.parseInt(usuario.Obtener_VariableSesion(getContext().getApplicationContext(), "Id"))) {
            PopupMenu popup = new PopupMenu(view.getContext(), view);
            MenuInflater inflater = popup.getMenuInflater();
            inflater.inflate(R.menu.menu_cardviewusuario, popup.getMenu());
            popup.setOnMenuItemClickListener(new MenuClickListener(position));
            popup.show();
        } else {
            PopupMenu popup = new PopupMenu(view.getContext(), view);
            MenuInflater inflater = popup.getMenuInflater();
            inflater.inflate(R.menu.menu_cardviewpersonal, popup.getMenu());
            popup.setOnMenuItemClickListener(new MenuClickListener(position));
            popup.show();
        }
    }


    class MenuClickListener implements PopupMenu.OnMenuItemClickListener {
        protected int pos;

        public MenuClickListener(int position) {
            this.pos = position;
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.ITM_ReportarNoticia_Principal:
                    FragmentManager fragmentManager = getActivity().getFragmentManager();
                    DialogoSeleccion dialogoSelelccion = new DialogoSeleccion();
                    Bundle bundleRegistrarReporte = new Bundle();
                    Datos.Noticias objNoticia = noticiasAdapter.getNoticiaList().get(pos);
                    Usuarios objUsuario = new Usuarios();
                    bundleRegistrarReporte.putInt("Id_Noticia", objNoticia.getId_Noticia());
                    bundleRegistrarReporte.putInt("Id_Usuario", Integer.parseInt(objUsuario.Obtener_VariableSesion(getContext().getApplicationContext(), "Id")));
                    dialogoSelelccion.setArguments(bundleRegistrarReporte);
                    dialogoSelelccion.show(fragmentManager, "tagAlerta");

                    break;
                case R.id.No_interasted:
                    Log.e("Segundo ", "Menu");
                    break;
                default:
                    Log.e("No ", "Valido MEnu");
                    break;
            }

            return false;
        }
    }


}
