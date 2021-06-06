package adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.asus.retamev2.R;


import java.util.ArrayList;
import java.util.List;

import Datos.Noticias;
import io.github.kexanie.library.MathView;

/**
 * Created by USUARIO on 08/06/2017.
 */

public class AdapterCargarTema extends RecyclerView.Adapter<AdapterCargarTema. MyViewHolder >  {
    private List<Noticias> noticiaList;
    private Context contextReciclerView;
    private Bitmap bitmapGustarN;

    private int id;


    public AdapterCargarTema(Context context){
        this.contextReciclerView = context;
        noticiaList = new ArrayList<Noticias>();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_cargartema, parent,false);
        return new MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Noticias objNoticia = getNoticiaList().get(position);
        holder.textViewNombreNoticia.setText(objNoticia.getNombre_Noticia());
        holder.textViewDescripcion.setText(objNoticia.getDescripcion_Noticia());
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
        return noticiaList.size();
    }

    public List<Noticias> getNoticiaList() {
        return noticiaList;
    }

    public void setNoticiaList(List<Noticias> noticiaList) {
        this.noticiaList = noticiaList;
    }




    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewNombreNoticia, textViewDescripcion, textViewTiempoPublicacion;
        public ImageView imageViewUsuario,imageButtonGustar, imageviewMenu;
        public TextView buttonAbrirNoticia;
        public MathView mathViewDescripcion;
        public MyViewHolder(View view) {
            super(view);
            textViewNombreNoticia = (TextView)view.findViewById(R.id.TXV_Nombre_Noticia_AdapterCargarTema);
            textViewDescripcion = (TextView)view.findViewById(R.id.TXV_DescripcionNoticia_AdapterCargarTema);
          //  mathViewDescripcion = (MathView)view.findViewById(R.id.MTV_Formula_AdapterCargarTema);
            // imageViewPerfil = (ImageView)view.findViewById(R.id.IMV_Foto_Perfil_NoticiasAdapter);
            imageButtonGustar = (ImageView) view.findViewById(R.id.imageLike_AdapterCargarTema);
            imageviewMenu = (ImageView)view.findViewById(R.id.IMV_Opciones_AdapterCargarTema);
            buttonAbrirNoticia = (TextView) view.findViewById(R.id.BTN_AbrirNoticia_AdapterCargarTema);
        }
    }


/*
    public class onMyClick implements View.OnClickListener {

        private final int pos;
        private final int opcion;
        private final noticiasAdapterReciclerView.MyViewHolder holder;
        public onMyClick(int pos, int op, noticiasAdapterReciclerView.MyViewHolder holder) {
            this.pos = pos;
            this.opcion = op;
            this.holder = holder;
        }

        @Override
        public void onClick(View v) {
            final Noticia objNoticias = getNoticiaList().get(pos);
            switch (this.opcion){
                case 1:
                    getActivityAdapter().imagepos(objNoticias.getId_Noticia());
                    break;
                case 2:
                    // getFragmentAdapter().Registrar_Gustar(objNoticias.getId_Noticia());
                    holder.imageButtonGustar.setImageBitmap(bitmapGustarN);
                    break;
                case 3:

                    break;
                case 4:
                    getActivityAdapter().Abrir_Noticia(pos);
                    break;
                case 5:
                    getActivityAdapter().Menu_Noticia(this.holder.imageviewMenu, pos);
                    break;
            }
        }
    }*/
}

