package com.example.asus.retamev2;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.TextView;


import Datos.Usuarios;
import adapter.tabsPagerAdapter;

public class menuAdministrador extends AppCompatActivity implements  NavigationView.OnNavigationItemSelectedListener {
    private ViewPager viewPager;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private DrawerLayout drawer;
    private tabsPagerAdapter pagerAdapter;
    private ActionBar actionBar;
    private Usuarios objUsuario;
    private int position;
    private FloatingActionButton fab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_administrador);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        viewPager = (ViewPager)findViewById(R.id.pager);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);

        fab = (FloatingActionButton) findViewById(R.id.fab_AppMenuAdministrador);

        objUsuario = new Usuarios();
        drawer = (DrawerLayout) findViewById(R.id.drawerMenuAdministrador);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View viewNavigationView  = navigationView.getHeaderView(0);
        TextView textViewUsuario = (TextView)viewNavigationView.findViewById(R.id.TXV_NombreUsuario_NavHeader);
        textViewUsuario.setText(""+objUsuario.Obtener_VariableSesion(getApplicationContext(), "user"));
    }

    private void setupViewPager(ViewPager viewPager) {
        tabsPagerAdapter adapter = new tabsPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new usuarioAdministrador(), "tab 1");
        adapter.addFragment(new categoriaAdministrador(), "tab 2");
        adapter.addFragment(new temaAdministrador(), "tab 3");
        adapter.addFragment(new reporteAdministrador(),"tab 4");
        adapter.addFragment(new materiaAdministrador(), "tab 5");
        viewPager.setAdapter(adapter);
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
        inflater.inflate(R.menu.activity_menu_inicio2_drawer, menu);
        if(objUsuario.Obtener_VariableSesion(getApplicationContext(), "Rol").equals("Administrador")) {
            Log.e("Hola Jhon", " ROl"+objUsuario.Obtener_VariableSesion(getApplicationContext(), "Rol").equals("Administrador"));
            menu.findItem(R.id.nav_Administrar).setVisible(true);
        }
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
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
            // Handle the camera action
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
            Intent intentAdministrar = new Intent(this, menuAdministrador.class);
            startActivity(intentAdministrar);
        }

        return true;
    }


    public void setFragment(int position) {
        FragmentManager fragmentManager;
        android.support.v4.app.FragmentTransaction fragmentTransaction;
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
}
