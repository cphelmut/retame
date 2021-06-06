package adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.asus.retamev2.R;

import java.util.ArrayList;
import java.util.List;

import Datos.Noticias;
import io.github.kexanie.library.MathView;

/**
 * Created by USUARIO on 30/05/2017.
 */

public class ejerciciosAdapterRecivlerView extends RecyclerView.Adapter<ejerciciosAdapterRecivlerView. MyViewHolder >  {

    private List<Noticias> noticiaList;
    private Context contextReciclerView;
    private Bitmap bitmapGustarN;
    private int id;
 //   private ImageLoader imageLoader;


    public ejerciciosAdapterRecivlerView(Context context){
        this.contextReciclerView = context;
        noticiaList = new ArrayList<Noticias>();
   ///     imageLoader = new ImageLoader(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_ejercicios, parent,false);

        return new MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Noticias objNoticia = getNoticiaList().get(position);
        holder.textViewNombreNoticia.setText(objNoticia.getNombre_Noticia());
        holder.textViewDescripcion.setText(objNoticia.getDescripcion_Noticia());
        holder.textViewNombreTema.setText(objNoticia.getObjTema().getNombre_Tema());
        holder.textViewNombreTipo.setText(objNoticia.getObjcategoria().getNombre_Categoria());
         Glide.with(contextReciclerView).load(objNoticia.getImagen_Noticia())
                .thumbnail(0.5f)
                .crossFade()
                .into(holder.imageViewPublicacion);


        //holder.mathViewDescripcion.setText(objNoticia.getImagen_Noticia());

        if (objNoticia.getURL() != "null"  ) {
            holder.textViewUrl.setText(Html.fromHtml("<a href=\"" + objNoticia.getURL() + "\">"
                    + objNoticia.getURL() + "</a> "));

            // Making url clickable
            holder.textViewUrl.setMovementMethod(LinkMovementMethod.getInstance());
            holder.textViewUrl.setVisibility(View.VISIBLE);
        } else {
            // url is null, remove from the view
            holder.textViewUrl.setVisibility(View.GONE);
        }
        int loader = R.drawable.basedatosindex;
/*
            Glide.with(contextReciclerView).load(objNoticia.getImagen_Noticia())
                    .thumbnail(0.5f)
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.imageViewPublicacion);
*/
        if (!TextUtils.isEmpty(objNoticia.getDescripcion_Noticia())) {
            holder.textViewDescripcion.setText(objNoticia.getDescripcion_Noticia());
            holder.textViewDescripcion.setVisibility(View.VISIBLE);
        } else {
            holder.textViewDescripcion.setVisibility(View.GONE);
        }

        if(id == 1){
            holder.imageButtonGustar.setImageBitmap(bitmapGustarN);
        }

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
        public TextView textViewNombreNoticia, textViewDescripcion, textViewNombreTema, textViewNombreTipo, textViewUrl;
        public ImageView imageViewUsuario, imageViewPublicacion, imageViewPerfil,imageButtonGustar;
        public TextView buttonAbrirNoticia;
        public MathView mathViewDescripcion;
        public Button buttonMenu;
        public MyViewHolder(View view) {
            super(view);
            textViewNombreNoticia = (TextView)view.findViewById(R.id.TXV_Nombre_Noticia_AdapterEjercicios);
            textViewDescripcion = (TextView)view.findViewById(R.id.TXV_Descripcion_AdapterEjercicios);
            textViewUrl = (TextView)view.findViewById(R.id.TXV_URL_AdapterEjercicios);
            imageViewPublicacion = (ImageView) view.findViewById(R.id.IMV_ImagenPublicacion_AdapterEjercicios);
       //     mathViewDescripcion = (MathView)view.findViewById(R.id.MTV_ImagenPublicacion_EjecrciciosAdapter);
           // imageButtonGustar = (ImageView) view.findViewById(R.id.imageLike_AdapterEjercicios);
            buttonMenu = (Button) view.findViewById(R.id.IMV_Menu_Noticia_AdapterEjercicios);
            buttonAbrirNoticia = (TextView) view.findViewById(R.id.BTN_AbrirNoticia_AdapterEjercicios);
            textViewNombreTema = (TextView)view.findViewById(R.id.TXV_Nombre_Tema_AdapterEjercicios);
            textViewNombreTipo = (TextView)view.findViewById(R.id.TXV_Nombre_Tipo_AdapterEjercicios);
        }
    }


}
