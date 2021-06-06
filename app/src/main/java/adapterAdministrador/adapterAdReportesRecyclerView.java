package adapterAdministrador;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.asus.retamev2.R;
import com.example.asus.retamev2.reporteAdministrador;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import Datos.Reportes;
import Datos.Temas;

/**
 * Created by ASUS on 31/01/2018.
 */

public class adapterAdReportesRecyclerView extends RecyclerView.Adapter<adapterAdReportesRecyclerView.myViewHolder>{
    private Context contextReportes;
    private reporteAdministrador frgReporte;
    private List<Reportes> reportesList;


    public adapterAdReportesRecyclerView(Context context) {
        setReportesList(new ArrayList<Reportes>());
        this.setContextReportes(context);
    }

    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext() ).inflate(R.layout.adapter_reportes_administrador, parent,false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(myViewHolder holder, int position) {
        final Reportes objReportes = getReportesList().get(position);
        holder.textViewNombreReporte.setText(objReportes.getNombre_Reporte());
        holder.textViewNombreUsuario.setText(objReportes.getObjUsuarios().getNombre_Usuario());
        holder.textViewNombreNoticia.setText(objReportes.getObjNoticias().getNombre_Noticia());
        holder.textViewDescripcion.setText(objReportes.getDescripcion_Reporte());
    }

    @Override
    public int getItemCount() {
        return getReportesList().size();
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }





    public Context getContextReportes() {
        return contextReportes;
    }

    public void setContextReportes(Context contextReportes) {
        this.contextReportes = contextReportes;
    }

    public reporteAdministrador getFrgReporte() {
        return frgReporte;
    }

    public void setFrgReporte(reporteAdministrador frgReporte) {
        this.frgReporte = frgReporte;
    }

    public List<Reportes> getReportesList() {
        return reportesList;
    }

    public void setReportesList(List<Reportes> reportesList) {
        this.reportesList = reportesList;
    }


    public class myViewHolder extends RecyclerView.ViewHolder {
        TextView textViewNombreReporte, textViewEstado, textViewDescripcion, textViewNombreUsuario, textViewNombreNoticia;

        public myViewHolder(View itemView) {
            super(itemView);
            textViewNombreReporte = (TextView)itemView.findViewById(R.id.TXV_NombreReporte_AdapterReportesAdministrador);
            textViewDescripcion = (TextView)itemView.findViewById(R.id.TXV_DescripcionReporte_AdapterReportesAdministrador);
            textViewNombreNoticia = (TextView)itemView.findViewById(R.id.TXV_NombreNoticia_AdapterReportesAdministrador);
            textViewNombreUsuario = (TextView)itemView.findViewById(R.id.TXV_NombreUsuario_AdapterReportesAdministrador);

        }
    }
}
