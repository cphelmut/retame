package adapterAdministrador;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.asus.retamev2.R;
import com.example.asus.retamev2.materiaAdministrador;

import java.util.ArrayList;
import java.util.List;

import Datos.Materias;

/**
 * Created by ASUS on 2/02/2018.
 */

public class adapterAdMateriaRecyclerView extends RecyclerView.Adapter<adapterAdMateriaRecyclerView.MyViewHolder> {
    private List<Materias> materiasList;
    private Context contextMaterias;
    private materiaAdministrador frgMateria;

    public adapterAdMateriaRecyclerView(Context context){
        setMateriasList(new ArrayList<Materias>());
        this.setContextMaterias(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_materia_administrador, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Materias objMaterias = getMateriasList().get(position);
        holder.textViewNombreMateria.setText(objMaterias.getNombre_Materia());
        holder.textViewDescripcionMateria.setText(objMaterias.getDescripcion_Materia());

    }

    @Override
    public int getItemCount() {
        return getMateriasList().size();
    }

    public List<Materias> getMateriasList() {
        return materiasList;
    }

    public void setMateriasList(List<Materias> materiasList) {
        this.materiasList = materiasList;
    }

    public Context getContextMaterias() {
        return contextMaterias;
    }

    public void setContextMaterias(Context contextMaterias) {
        this.contextMaterias = contextMaterias;
    }

    public materiaAdministrador getFrgMateria() {
        return frgMateria;
    }

    public void setFrgMateria(materiaAdministrador frgMateria) {
        this.frgMateria = frgMateria;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView textViewNombreMateria, textViewDescripcionMateria;
        Button buttonOpciones;

        public MyViewHolder(View itemView) {
            super(itemView);
            textViewNombreMateria = (TextView)itemView.findViewById(R.id.TXV_NombreMateria_AdapterMateriaAdministrador);
            textViewDescripcionMateria = (TextView)itemView.findViewById(R.id.TXV_DescripcionMateria_AdapterMateriaAdministrador);
            //buttonOpciones = (Button)itemView.findViewById(R.id.BTN_)
        }
    }
}
