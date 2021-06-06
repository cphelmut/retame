package adapterAdministrador;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.asus.retamev2.R;
import com.example.asus.retamev2.temaAdministrador;

import java.util.ArrayList;
import java.util.List;

import Datos.Temas;


/**
 * Created by ASUS on 22/01/2018.
 */

public class adapterAdTemaRecyclerView extends RecyclerView.Adapter<adapterAdministrador.adapterAdTemaRecyclerView.MyViewHolder > {
    private List<Temas> temasList;
    private Context contextReciclerView;
    private Bitmap bitmapGustarN;
    private temaAdministrador frgTemaAdministrador;


    public adapterAdTemaRecyclerView(Context context) {
        this.contextReciclerView = context;
        setTemasList(new ArrayList<Temas>());
    }

    @Override
    public adapterAdministrador.adapterAdTemaRecyclerView.MyViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {

        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_temas_administrador, parent, false);
        return new adapterAdministrador.adapterAdTemaRecyclerView.MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(adapterAdministrador.adapterAdTemaRecyclerView.MyViewHolder holder, int position) {
        final Temas objTema = getTemasList().get(position);
        holder.textViewNombreNoticia.setText(objTema.getNombre_Tema());
        holder.textViewDescripcion.setText(objTema.getDescripcion_Tema());

        holder.textViewNombreNoticia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Abrir_Tema(objTema.getId_Tema());
            }
        });

        holder.textViewDescripcion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Abrir_Tema(objTema.getId_Tema());
            }
        });
        holder.imageViewOpciones.setOnClickListener(new clickListener(position, 5, holder));
    }


    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemCount() {
        return getTemasList().size();
    }

    public List<Temas> getTemasList() {
        return temasList;
    }

    public void setTemasList(List<Temas> temasList) {
        this.temasList = temasList;
    }

    public void Abrir_Tema(int intId_Tema) {
        Bundle bundleTema = new Bundle();
        bundleTema.putString("Id_Tema", "" + intId_Tema);
        // Intent intentTema = new Intent(contextReciclerView,  cargarTemaNoticia.class);
        //intentTema.putExtras(bundleTema);
        //intentTema.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //contextReciclerView.startActivity(intentTema);
    }

    public temaAdministrador getFrgTemaAdministrador() {
        return frgTemaAdministrador;
    }

    public void setFrgTemaAdministrador(temaAdministrador frgTemaAdministrador) {
        this.frgTemaAdministrador = frgTemaAdministrador;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewNombreNoticia, textViewDescripcion, textViewTiempoPublicacion, textViewUrl;
        public TextView buttonAbrirNoticia;
        public RelativeLayout relativeLayoutAdapterTema;
        public ImageView imageViewOpciones;

        public MyViewHolder(View view) {
            super(view);
            textViewNombreNoticia = (TextView) view.findViewById(R.id.TXV_NombreTema_AdapterTemasAdministrador);
            textViewDescripcion = (TextView) view.findViewById(R.id.TXV_DescripcionTema_AdapterTemasAdministrador);
            imageViewOpciones = (ImageView) view.findViewById(R.id.IMB_Opciones_AdapterTemasAdministrador);
        }
    }

    public class clickListener implements View.OnClickListener {

        private final int pos;
        private final int opcion;
        private final MyViewHolder holder;
        public clickListener(int pos, int op, MyViewHolder holder) {
            this.pos = pos;
            this.opcion = op;
            this.holder = holder;
        }

        @Override
        public void onClick(View v) {
            final Temas objTemas = getTemasList().get(pos);
            switch (this.opcion){
                case 1:

                    break;
                case 2:

                    break;
                case 3:

                    break;
                case 4:

                    break;
                case 5:
                    getFrgTemaAdministrador().Menu_Noticia(this.holder.imageViewOpciones, pos);
                    break;
            }
        }
    }
}
