package adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.asus.retamev2.R;
import com.example.asus.retamev2.buscarNoticia;

import java.util.ArrayList;
import java.util.List;

import Datos.Noticias;
import io.github.kexanie.library.MathView;

/**
 * Created by ASUS on 5/01/2018.
 */

public class buscarNoticiasAdapterReciclerView extends RecyclerView.Adapter<buscarNoticiasAdapterReciclerView.MyviewHolder> {
    private List<Noticias> buscarnoticiaList;
    private Context contextReciclerView;
    private buscarNoticia activityBuscarNoticia;
    private Bitmap bitmapGustarN;

    public buscarNoticiasAdapterReciclerView(Context context){
        this.contextReciclerView = context;
        this.buscarnoticiaList = new ArrayList<Noticias>();
        Log.e("Hola Jhon", "Ya asignamos el context");
    }

    @Override
    public buscarNoticiasAdapterReciclerView.MyviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_buscar_noticias, parent,false);
        return new MyviewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyviewHolder holder, final int position) {
        Noticias objNoticia = getBuscarnoticiaList().get(position);
        Log.e("Hola Jhon ", "id noticia "+objNoticia.getNombre_Noticia());
        holder.textViewNombreNoticia.setText(objNoticia.getNombre_Noticia());
        holder.textViewDescripcion.setText(objNoticia.getDescripcion_Noticia());
        holder.textViewNombreTipo.setText(objNoticia.getObjcategoria().getNombre_Categoria());
        holder.textViewNombreTema.setText(objNoticia.getObjTema().getNombre_Tema());
        Glide.with(contextReciclerView).load(objNoticia.getImagen_Noticia())
                .thumbnail(0.5f)
                .crossFade()
                .into(holder.imageViewPublicacion);
        /// holder.mathViewDescripcion.setText(objNoticia.getImagen_Noticia());
        if (objNoticia.getURL() != "null"  ) {
            holder.textViewUrl.setText(Html.fromHtml("<a href=\"" + objNoticia.getURL() + "\">"
                    + objNoticia.getURL() + "</a> "));
            holder.textViewUrl.setMovementMethod(LinkMovementMethod.getInstance());
            holder.textViewUrl.setVisibility(View.VISIBLE);
        } else {
            holder.textViewUrl.setVisibility(View.GONE);
        }
        int loader = R.drawable.basedatosindex;
        if (!TextUtils.isEmpty(objNoticia.getDescripcion_Noticia())) {
            holder.textViewDescripcion.setText(objNoticia.getDescripcion_Noticia());
            holder.textViewDescripcion.setVisibility(View.VISIBLE);
        } else {
            holder.textViewDescripcion.setVisibility(View.GONE);
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
        return getBuscarnoticiaList().size();
    }

    public List<Noticias> getBuscarnoticiaList() {
        return buscarnoticiaList;
    }

    public void setBuscarnoticiaList(List<Noticias> buscarnoticiaList) {
        this.buscarnoticiaList = buscarnoticiaList;
    }

    public buscarNoticia getActivityBuscarNoticia() {
        return activityBuscarNoticia;
    }

    public void setActivityBuscarNoticia(buscarNoticia activityBuscarNoticia) {
        this.activityBuscarNoticia = activityBuscarNoticia;
    }


    public class MyviewHolder extends RecyclerView.ViewHolder {
        public TextView textViewNombreNoticia, textViewDescripcion, textViewTiempoPublicacion, textViewUrl, textViewNombreTema, textViewNombreTipo;
        public ImageView imageViewUsuario, imageViewPublicacion, imageViewPerfil,imageButtonGustar, imageviewMenu;
        public TextView buttonAbrirNoticia;
        public MathView mathViewDescripcion;
        public MyviewHolder(View itemView) {
            super(itemView);
            textViewNombreNoticia = (TextView)itemView.findViewById(R.id.TXV_Nombre_Noticias_BuscarNoticiasAdapter);
            //imageViewUsuario = (ImageView) view.findViewById(R.id.foto_perfil);
            textViewDescripcion = (TextView)itemView.findViewById(R.id.TXV_Descripcion_Noticia_BuscarNoticiasAdapter);
            //textViewTiempoPublicacion = (TextView)view.findViewById(R.id.tiempo_Publicacion);
            textViewUrl = (TextView)itemView.findViewById(R.id.TXV_URL_BuscarNoticiasAdapter);
            imageViewPublicacion = (ImageView) itemView.findViewById(R.id.IMV_Imagen_Noticia_BuscarNoticiasAdapter);
            //mathViewDescripcion = (MathView)view.findViewById(R.id.MTV_Formula_NoticiasAdapter);
            // imageViewPerfil = (ImageView)view.findViewById(R.id.IMV_Foto_Perfil_NoticiasAdapter);
            imageButtonGustar = (ImageView) itemView.findViewById(R.id.imageLike_BuscarNoticiasAdapter);
            //imageviewMenu = (ImageView)view.findViewById(R.id.IMV_Menu_Noticia_NoticiasAdapter);
            buttonAbrirNoticia = (TextView) itemView.findViewById(R.id.BTN_AbrirNoticia_BuscarNoticiasAdapter);
            textViewNombreTema = (TextView) itemView.findViewById(R.id.TXV_Nombre_Tema_BuscarNoticiasAdapter);
            textViewNombreTipo = (TextView) itemView.findViewById(R.id.TXV_Nombre_Tipo_BuscarNoticiasAdapter);
        }
    }
}
