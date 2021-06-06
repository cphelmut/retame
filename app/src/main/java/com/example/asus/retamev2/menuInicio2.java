package com.example.asus.retamev2;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.MenuInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import android.widget.TextView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import org.json.JSONObject;


import Datos.Noticias;
import Datos.Usuarios;
import interfaces.IAppManager;

public class menuInicio2 extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,  SearchView.OnQueryTextListener,    FloatingActionButton.OnClickListener  {
    private static final String Lista_Amigos  ="Lista Amigos";
    private static final String Noticias = "Noticia";
    private static final String Otro = "Otro";
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    public IAppManager imService;
    private int posicion;
    Usuarios objUsuario;

    DrawerLayout drawer;
    SearchView searchViewBusqueda;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_inicio2);
      // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);
        objUsuario = new Usuarios();
        if(objUsuario.Obtener_VariableSesion(getApplicationContext(), "Rol") != "Administrador") {
            fab.setVisibility(View.VISIBLE);
        }

        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        searchViewBusqueda = (SearchView)findViewById(R.id.SCV_Busqueda_MenuIncio);
        //searchViewBusqueda.
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View viewNavigationView  = navigationView.getHeaderView(0);
        TextView textViewUsuario = (TextView)viewNavigationView.findViewById(R.id.TXV_NombreUsuario_NavHeader);
        textViewUsuario.setText(""+objUsuario.Obtener_VariableSesion(getApplicationContext(), "user"));
        setFragment(0);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchViewBusqueda.setSearchableInfo(searchManager.
                getSearchableInfo(getComponentName()));
        searchViewBusqueda.setSubmitButtonEnabled(true);
        searchViewBusqueda.setOnQueryTextListener(this);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if(id == R.id.nav_Incicio) {
            item.setChecked(true);
            setFragment(0);
            drawer.closeDrawer(GravityCompat.START);
        }else if (id == R.id.nav_Temas) {
            item.setChecked(true);
            setFragment(1);
            drawer.closeDrawer(GravityCompat.START);

        }else if (id == R.id.nav_Ejemplos) {
            item.setChecked(true);
            setFragment(2);
            drawer.closeDrawer(GravityCompat.START);
        }  else if (id == R.id.nav_Teoria) {
            item.setChecked(true);
            setFragment(3);
            drawer.closeDrawer(GravityCompat.START);
        } else if (id == R.id.nav_Buscar) {

        } else if (id == R.id.nav_Configuaracion) {
            item.setChecked(true);
            Intent intentAdministrar = new Intent(this, menuAdministrador.class);
            startActivity(intentAdministrar);
        } else if (id == R.id.nav_Administrar) {
            item.setChecked(true);
            Intent intentAdministrar = new Intent(this, menuAdministrador.class);
            startActivity(intentAdministrar);
        }

        return true;
    }


    public void setFragment(int position) {
        FragmentManager fragmentManager;
        FragmentTransaction fragmentTransaction;
        switch (position) {
            case 0:
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                principal fragmenP = new principal();
                fragmentTransaction.replace(R.id.fragment, fragmenP);
                fragmentTransaction.commit();
                break;
            case 1:
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                temas fragmentT = new temas();
                fragmentTransaction.replace(R.id.fragment, fragmentT);
                fragmentTransaction.commit();
                break;
            case 2:
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                ejemplos fragmentEj = new ejemplos();
                fragmentTransaction.replace(R.id.fragment, fragmentEj);
                fragmentTransaction.commit();
                break;
            case 3:
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                //frg_cargarEjercicios fragmentEx = new frg_cargarEjercicios();
                //fragmentTransaction.replace(R.id.fragment, fragmentEx);
                fragmentTransaction.commit();
                break;
        }
    }






    @Override
    public boolean onQueryTextSubmit(String s) {
        Log.e("Hola Jhon ", "El texto en oQueryTextSubmit "+s);
        Buscar_Noticias(s);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        Log.e("Hola Jhon ", "El texto en oQueryTextChange "+s);
        return false;
    }

    public void Buscar_Noticias(String strBusqueda){
        Bundle bundleBuscarNoticia = new Bundle();
        bundleBuscarNoticia.putString("Parametro_Busqueda", strBusqueda);
        Intent intentBuscarNoticia = new Intent(getApplicationContext(), buscarNoticia.class);
        intentBuscarNoticia.putExtras(bundleBuscarNoticia);
        startActivity(intentBuscarNoticia);
    }

    @Override
    public void onClick(View v) {
        Intent intentRegistrarNoticia = new Intent(this,registrarNoticiaAdministrador.class);
        startActivity(intentRegistrarNoticia);
    }
}
