package adapterAdministrador;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.asus.retamev2.R;
import com.example.asus.retamev2.usuarioAdministrador;

import java.util.ArrayList;
import java.util.List;
import Datos.Usuarios;


/**
 * Created by ASUS on 12/01/2018.
 */

public class adapterAdUsuariosRecyclerView extends RecyclerView.Adapter<adapterAdUsuariosRecyclerView.MyViewHolder> {
    private List<Usuarios> usariosList;
    private Context contextReciclerView;
    private usuarioAdministrador fragmentUsuarioAdmin;
    private Bitmap bitmapGustarN;


    public adapterAdUsuariosRecyclerView(Context context){
        this.setContextReciclerView(context);
        setUsariosList(new ArrayList<Usuarios>());
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_usuarios_administrador, parent,false);
        return new adapterAdUsuariosRecyclerView.MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder,int position) {
        Usuarios objUsuario = getUsariosList().get(position);
        holder.textViewNombreUsuario.setText(""+objUsuario.getNombre_Usuario());
        holder.textViewNombreCompleto.setText(objUsuario.getObjPersona().getNombres()+ " " +objUsuario.getObjPersona().getApellidos());
        Glide.with(getContextReciclerView()).load(objUsuario.getImagen_Usuario())
                .thumbnail(0.5f)
                .crossFade()
                .into(holder.imageViewUsuario);


        holder.imageButtonAbrirOpcion.setOnClickListener(new onMyClick(position, 5, holder));
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
        return getUsariosList().size();
    }

    public List<Usuarios> getUsariosList() {
        return usariosList;
    }

    public void setUsariosList(List<Usuarios> usariosList) {
        this.usariosList = usariosList;
    }

    public Context getContextReciclerView() {
        return contextReciclerView;
    }

    public void setContextReciclerView(Context contextReciclerView) {
        this.contextReciclerView = contextReciclerView;
    }

    public usuarioAdministrador getFragmentUsuarioAdmin() {
        return fragmentUsuarioAdmin;
    }

    public void setFragmentUsuarioAdmin(usuarioAdministrador fragmentUsuarioAdmin) {
        this.fragmentUsuarioAdmin = fragmentUsuarioAdmin;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewNombreUsuario, textViewNombreCompleto;
        public ImageView imageViewUsuario;
        public ImageButton imageButtonAbrirOpcion;
        public MyViewHolder(View view) {
            super(view);
            textViewNombreUsuario = (TextView)view.findViewById(R.id.TXV_Nombre_Completo_UsuariosAdapter);
            imageViewUsuario = (ImageView) view.findViewById(R.id.IMV_Imagen_Usuario_UsuariosAdapter);
            textViewNombreCompleto = (TextView)view.findViewById(R.id.TXV_Nombre_Usuario_UsuariosAdapter);
            imageButtonAbrirOpcion = (ImageButton)view.findViewById(R.id.IMB_Opciones_UsuariosAdapter);
        }
    }



    public class onMyClick implements View.OnClickListener {

        private final int pos;
        private final int opcion;
        private final adapterAdUsuariosRecyclerView.MyViewHolder holder;
        public onMyClick(int pos, int op, adapterAdUsuariosRecyclerView.MyViewHolder holder) {
            this.pos = pos;
            this.opcion = op;
            this.holder = holder;
        }

        @Override
        public void onClick(View v) {
            final Usuarios objUsuarios = getUsariosList().get(pos);
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
                      getFragmentUsuarioAdmin().Menu_Noticia(this.holder.imageButtonAbrirOpcion, pos);
                    break;
            }
        }
    }
}
