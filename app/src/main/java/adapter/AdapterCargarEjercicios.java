package adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.asus.retamev2.R;

import java.util.ArrayList;
import java.util.List;
import Datos.Noticias;
import interfaces.IAppManager;
import io.github.kexanie.library.MathView;

/**
 * Created by USUARIO on 15/06/2017.
 */

public class AdapterCargarEjercicios extends RecyclerView.Adapter<AdapterCargarEjercicios.MyViewHolder> {
    private List<Noticias> noticiaList;
    private Context contextAdapterCargarEjercicios;


    public AdapterCargarEjercicios(Context context){
        this.setContextAdapterCargarEjercicios(context);
        setNoticiaList(new ArrayList<Noticias>());
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_cargarejercicios, parent, false);
        return new MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Noticias objNoticia =  getNoticiaList().get(position);
        holder.textViewNombreNoticia.setText(objNoticia.getNombre_Noticia());
        holder.textViewDescripcion.setText(objNoticia.getDescripcion_Noticia());
        holder.textViewURL.setText(objNoticia.getURL());
        holder.textViewNombreTipo.setText(objNoticia.getObjcategoria().getNombre_Categoria());
        holder.textViewNombreTema.setText(objNoticia.getObjTema().getNombre_Tema());
        //holder.mathViewFormula.setText(objNoticia.getImagen_Noticia());
        Glide.with(contextAdapterCargarEjercicios).load(objNoticia.getImagen_Noticia())
                .thumbnail(0.5f)
                .crossFade()
                .centerCrop()
                .into(holder.imageViewImagenPublicacion);
        holder.textViewDescripcion.setOnClickListener(new onMyClick(position,4,holder));
        holder.textViewNombreNoticia.setOnClickListener(new onMyClick(position,4,holder));
        holder.imageViewOpciones.setOnClickListener(new onMyClick(position,5, holder));
        holder.imageViewImagenPublicacion.setOnClickListener(new onMyClick(position,4,holder));


    }


    @Override
    public int getItemCount() {
        return noticiaList.size();
    }

    public List<Noticias> getNoticiaList() {
        return noticiaList;
    }

    public void setNoticiaList(List<Noticias> noticiaList) {
        this.noticiaList = noticiaList;
    }

    public Context getContextAdapterCargarEjercicios() {
        return contextAdapterCargarEjercicios;
    }

    public void setContextAdapterCargarEjercicios(Context contextAdapterCargarEjercicios) {
        this.contextAdapterCargarEjercicios = contextAdapterCargarEjercicios;
    }



    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textViewNombreNoticia, textViewDescripcion, textViewURL, textViewNombreTema, textViewNombreTipo;
        MathView mathViewFormula;
        ImageView imageViewOpciones, imageViewGustar, imageViewImagenPublicacion;
        public MyViewHolder(View itemView) {
            super(itemView);
            textViewDescripcion = (TextView)itemView.findViewById(R.id.TXV_DescripcionNoticia_AdapterCargarEjercicio);
            textViewNombreNoticia =(TextView)itemView.findViewById(R.id.TXV_Nombre_Noticia_AdapterCargarEjercicio);
            imageViewOpciones = (ImageView)itemView.findViewById(R.id.IMV_Opciones_AdapterCargarEjercicio);
          //  mathViewFormula = (MathView)itemView.findViewById(R.id.MTV_Formula_AdapterCargarEjercicio);
            imageViewImagenPublicacion = (ImageView)itemView.findViewById(R.id.IMV_ImagenPublicacion_AdapterCargarEjercicio);
            textViewURL = (TextView)itemView.findViewById(R.id.TXV_URL_AdapterCargarEjercicio);
            textViewNombreTema = (TextView)itemView.findViewById(R.id.TXV_Nombre_Tema_AdapterCargarEjercicio);
            textViewNombreTipo = (TextView)itemView.findViewById(R.id.TXV_Nombre_Tipo_AdapterCargarEjercicio);
        }
    }

    public class onMyClick implements View.OnClickListener {

        private final int pos;
        private final int opcion;
        private final MyViewHolder holder;

        public onMyClick(int pos, int op, MyViewHolder holder) {
            this.pos = pos;
            this.opcion = op;
            this.holder = holder;
        }

        @Override
        public void onClick(View v) {
            final Noticias objNoticias = getNoticiaList().get(pos);
            switch (this.opcion) {
                case 1:

                    break;
                case 2:

                    break;
                case 3:

                    break;
                case 4:
                    //getFragmentAdapter().Abrir_Noticia(pos);
                    break;
                case 5:
                    ///getFragmentAdapter().Menu_Noticia(this.holder.imageViewOpciones, pos);
                    break;
            }
        }
    }
}
