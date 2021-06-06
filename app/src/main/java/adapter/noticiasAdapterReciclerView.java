package adapter;

import android.content.Context;
import android.graphics.Bitmap;
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
import com.example.asus.retamev2.principal;

import java.util.ArrayList;
import java.util.List;
import Datos.Noticias;
import io.github.kexanie.library.MathView;

/**
 * Created by USUARIO on 03/01/2017.
 */
public class noticiasAdapterReciclerView extends RecyclerView.Adapter<noticiasAdapterReciclerView.MyViewHolder> {
    private List<Noticias> noticiaList;
    private Context contextReciclerView;
    private principal fragmentPrincipal;
    private Bitmap bitmapGustarN;
    //private ImageLoader imageLoader;


    public noticiasAdapterReciclerView(Context context){
        this.contextReciclerView = context;
        setNoticiaList(new ArrayList<Noticias>());
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_noticias, parent,false);
        return new MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        Noticias objNoticia = getNoticiaList().get(position);
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

        //holder.imageviewMenu.setOnClickListener(new onMyClick(position,5,holder));
        holder.imageButtonGustar.setOnClickListener(new onMyClick(position, 2, holder));
        holder.buttonAbrirNoticia.setOnClickListener(new onMyClick(position,4,holder));
        holder.imageViewPublicacion.setOnClickListener(new onMyClick(position,4,holder));
        holder.textViewDescripcion.setOnClickListener(new onMyClick(position,4,holder));
        holder.textViewNombreNoticia.setOnClickListener(new onMyClick(position,3,holder));
        holder.buttonOpciones.setOnClickListener(new onMyClick(position, 5, holder));

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
        return getNoticiaList().size();
    }


    public List<Noticias> getNoticiaList() {
        return noticiaList;
    }

    public void setNoticiaList(List<Noticias> noticiaList) {
        this.noticiaList = noticiaList;
    }

    public principal getFragmentPrincipal() {
        return fragmentPrincipal;
    }

    public void setFragmentPrincipal(principal fragmentPrincipal) {
        this.fragmentPrincipal = fragmentPrincipal;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewNombreNoticia, textViewDescripcion, textViewTiempoPublicacion, textViewUrl, textViewNombreTema, textViewNombreTipo;
        public ImageView imageViewUsuario, imageViewPublicacion, imageViewPerfil,imageButtonGustar, imageviewMenu;
        public TextView buttonAbrirNoticia;
        public Button buttonOpciones;
        public MyViewHolder(View view) {
            super(view);
            textViewNombreNoticia = (TextView)view.findViewById(R.id.TXV_Nombre_Noticias_NoticiasAdapter);
          //imageViewUsuario = (ImageView) view.findViewById(R.id.foto_perfil);
            textViewDescripcion = (TextView)view.findViewById(R.id.TXV_Descripcion_Noticia_NoticiasAdapter);
            //textViewTiempoPublicacion = (TextView)view.findViewById(R.id.tiempo_Publicacion);
            textViewUrl = (TextView)view.findViewById(R.id.TXV_URL_NoticiasAdapter);
            imageViewPublicacion = (ImageView) view.findViewById(R.id.IMV_Imagen_Noticia_NoticiasAdapter);
          //mathViewDescripcion = (MathView)view.findViewById(R.id.MTV_Formula_NoticiasAdapter);
            // imageViewPerfil = (ImageView)view.findViewById(R.id.IMV_Foto_Perfil_NoticiasAdapter);
            imageButtonGustar = (ImageView) view.findViewById(R.id.imageLike);
            buttonOpciones = (Button)view.findViewById(R.id.BTN_Opciones_NoticiasAdapter);
            buttonAbrirNoticia = (TextView) view.findViewById(R.id.BTN_AbrirNoticia_NoticiasAdapter);
            textViewNombreTema = (TextView) view.findViewById(R.id.TXV_Nombre_Tema_NoticiasAdapter);
            textViewNombreTipo = (TextView) view.findViewById(R.id.TXV_Nombre_Tipo_NoticiasAdapter);
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
            switch (this.opcion){
                case 1:
                   getFragmentPrincipal().imagepos(objNoticias.getId_Noticia());
                    break;
                case 2:
                    getFragmentPrincipal().Registrar_Gustar(objNoticias.getId_Noticia());
                    holder.imageButtonGustar.setImageBitmap(bitmapGustarN);
                    break;
                case 3:

                    break;
                case 4:
                    getFragmentPrincipal().Abrir_Noticia(pos);
                    break;
                case 5:
                    getFragmentPrincipal().Menu_Noticia(this.holder.buttonOpciones, pos);
        break;
        }
        }
    }
}
