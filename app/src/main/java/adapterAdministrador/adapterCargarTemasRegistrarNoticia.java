package adapterAdministrador;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.asus.retamev2.R;

import java.util.ArrayList;
import java.util.List;

import Datos.Categorias;
import Datos.Temas;

/**
 * Created by ASUS on 25/01/2018.
 */

public class adapterCargarTemasRegistrarNoticia extends ArrayAdapter<Temas> {
    private List<Temas> temasList;
    private Activity contextTemas;
    private int resourceCategoria;

    public adapterCargarTemasRegistrarNoticia(Activity context, int resource, List<Temas> temas) {
        super(context, resource, temas);
        temasList = new ArrayList<Temas>();
        this.contextTemas = context;
        temasList = temas;
        resourceCategoria = resource;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if(view == null){
            LayoutInflater layoutInflater = contextTemas.getLayoutInflater();
            view = layoutInflater.inflate(R.layout.adapter_cargarcategoria_registrarnoticia,parent, false);
        }
        String nombreTema = temasList.get(position).getNombre_Tema().toString();
        if(nombreTema != null){
            TextView textViewNombreCategoria = (TextView)view.findViewById(R.id.TXV_NombreCategoria_AdapterRegistrarNoticia);
            textViewNombreCategoria.setText(nombreTema);
        }
        return view;
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if(view == null){
            LayoutInflater layoutInflater = contextTemas.getLayoutInflater();
            view = layoutInflater.inflate(R.layout.adapter_cargarcategoria_registrarnoticia, parent,false);
        }
        String nombreTema = temasList.get(position).getNombre_Tema();
        if(nombreTema != null){
            TextView textViewNombreCategoria = (TextView)view.findViewById(R.id.TXV_NombreCategoria_AdapterRegistrarNoticia);
            textViewNombreCategoria.setText(nombreTema);
        }
        return view;
    }
}

