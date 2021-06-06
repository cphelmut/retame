package adapterAdministrador;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.asus.retamev2.R;

import java.util.ArrayList;
import java.util.List;

import Datos.Categorias;

/**
 * Created by ASUS on 23/01/2018.
 */

public class adapterCargarCategoriaRegistrarNoticia extends ArrayAdapter<Categorias> {
    private List<Categorias> categoriasList;
    private Activity contextCategorias;
    private int resourceCategoria;

    public adapterCargarCategoriaRegistrarNoticia(Activity context, int resource, List<Categorias> categorias) {
        super(context, resource, categorias);
        categoriasList = new ArrayList<Categorias>();
        this.contextCategorias = context;
        categoriasList = categorias;
        resourceCategoria = resource;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if(view == null){
            LayoutInflater layoutInflater = contextCategorias.getLayoutInflater();
            view = layoutInflater.inflate(R.layout.adapter_cargarcategoria_registrarnoticia,parent, false);
        }
        String nombreCategoria = categoriasList.get(position).getNombre_Categoria().toString();
        if(nombreCategoria != null){
            TextView textViewNombreCategoria = (TextView)view.findViewById(R.id.TXV_NombreCategoria_AdapterRegistrarNoticia);
            textViewNombreCategoria.setText(nombreCategoria);
        }
        return view;
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if(view == null){
            LayoutInflater layoutInflater = contextCategorias.getLayoutInflater();
            view = layoutInflater.inflate(R.layout.adapter_cargarcategoria_registrarnoticia, parent,false);
        }
        String nombreCategoria = categoriasList.get(position).getNombre_Categoria();
        if(nombreCategoria != null){
            TextView textViewNombreCategoria = (TextView)view.findViewById(R.id.TXV_NombreCategoria_AdapterRegistrarNoticia);
            textViewNombreCategoria.setText(nombreCategoria);
        }
        return view;
    }
}
