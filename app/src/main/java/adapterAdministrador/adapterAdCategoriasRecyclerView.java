package adapterAdministrador;

import android.app.Fragment;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.asus.retamev2.R;

import java.util.ArrayList;
import java.util.List;

import Datos.Categorias;

/**
 * Created by ASUS on 13/01/2018.
 */

public class adapterAdCategoriasRecyclerView extends RecyclerView.Adapter<adapterAdCategoriasRecyclerView.viewHolder> {
    private Context contextCategoriaAdapter;
    private List<Categorias> categoriasList;
    private Fragment fragmentCategoriasAdapter;



    public adapterAdCategoriasRecyclerView(Context contextAdapter, List<Categorias> categoriasList){
        this.setContextCategoriaAdapter(contextAdapter);
        this.setCategoriasList(new ArrayList<Categorias>());
        this.setCategoriasList(categoriasList);
    }
    @Override
    public adapterAdCategoriasRecyclerView.viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_categorias_administrador, parent, false);
        return new adapterAdCategoriasRecyclerView.viewHolder(view);
    }

    @Override
    public void onBindViewHolder(adapterAdCategoriasRecyclerView.viewHolder holder, int position) {
        Categorias objCategorias = getCategoriasList().get(position);
        holder.textViewNombreCategoria.setText(objCategorias.getNombre_Categoria());

    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemCount() {
        return getCategoriasList().size();
    }

    public Context getContextCategoriaAdapter() {
        return contextCategoriaAdapter;
    }

    public void setContextCategoriaAdapter(Context contextCategoriaAdapter) {
        this.contextCategoriaAdapter = contextCategoriaAdapter;
    }

    public List<Categorias> getCategoriasList() {
        return categoriasList;
    }

    public void setCategoriasList(List<Categorias> categoriasList) {
        this.categoriasList = categoriasList;
    }

    public Fragment getFragmentCategoriasAdapter() {
        return fragmentCategoriasAdapter;
    }

    public void setFragmentCategoriasAdapter(Fragment fragmentCategoriasAdapter) {
        this.fragmentCategoriasAdapter = fragmentCategoriasAdapter;
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        public TextView textViewNombreCategoria;
        protected ImageView imageViewImagenCategoria;
        public viewHolder(View itemView) {
            super(itemView);
            textViewNombreCategoria = itemView.findViewById(R.id.TXV_NombreCategoria_CategoriasAdapter);
        }
    }
}
