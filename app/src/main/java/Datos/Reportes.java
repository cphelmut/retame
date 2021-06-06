package Datos;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Conexion.Conexion;

/**
 * Created by ASUS on 31/01/2018.
 */

public class Reportes {
    private int Id_Reporte;
    private String Nombre_Reporte;
    private String Descripcion_Reporte;
    private String Fecha_Registro;
    private int Estado_Reporte;
    private int Id_TipoReporte;
    private Usuarios objUsuarios;
    private Noticias objNoticias;
    String urlServer = "http://192.168.43.254/retame/";

    public int getId_Reporte() {
        return Id_Reporte;
    }

    public void setId_Reporte(int id_Reporte) {
        Id_Reporte = id_Reporte;
    }

    public String getNombre_Reporte() {
        return Nombre_Reporte;
    }

    public void setNombre_Reporte(String nombre_Reporte) {
        Nombre_Reporte = nombre_Reporte;
    }

    public String getDescripcion_Reporte() {
        return Descripcion_Reporte;
    }

    public void setDescripcion_Reporte(String descripcion_Reporte) {
        Descripcion_Reporte = descripcion_Reporte;
    }

    public Usuarios getObjUsuarios() {
        return objUsuarios;
    }

    public void setObjUsuarios(Usuarios objUsuarios) {
        this.objUsuarios = objUsuarios;
    }

    public Noticias getObjNoticias() {
        return objNoticias;
    }

    public void setObjNoticias(Noticias objNoticias) {
        this.objNoticias = objNoticias;
    }

    public String getFecha_Registro() {
        return Fecha_Registro;
    }

    public void setFecha_Registro(String fecha_Registro) {
        Fecha_Registro = fecha_Registro;
    }

    public int getEstado_Reporte() {
        return Estado_Reporte;
    }

    public void setEstado_Reporte(int estado_Reporte) {
        Estado_Reporte = estado_Reporte;
    }

    public List<Reportes> Listar_Reportes(){
        List<Reportes> listReportes = new ArrayList<Reportes>();
        String url_noticias = urlServer+"index_reportes.php?opcion=Listar_Reportes";
        Conexion con = new Conexion();
        JSONObject jsonObjectListarReportes = con.HttpRequest(url_noticias);
        if(jsonObjectListarReportes.equals(null)){
            return null;
        }else {
            try {
                JSONArray jsonArrayNoticias = jsonObjectListarReportes.getJSONArray("Listar_Reportes");
                for (int i = 0; i < jsonArrayNoticias.length(); i++) {
                    Noticias objNoticias = new Noticias();
                    Usuarios objUsuarios = new Usuarios();
                    Reportes objReportes =  new Reportes();
                    JSONObject jsonObjectFeed = (JSONObject) jsonArrayNoticias.get(i);
                    objReportes.setId_Reporte(jsonObjectFeed.getInt("Id_Reporte"));
                    objReportes.setNombre_Reporte(jsonObjectFeed.getString("Nombre_Tipo_Reporte"));
                    objReportes.setDescripcion_Reporte(jsonObjectFeed.getString("Descripcion_Reporte"));
                    objReportes.setFecha_Registro(jsonObjectFeed.getString("Fecha_Registro"));
                    objReportes.setEstado_Reporte(jsonObjectFeed.getInt("Estado_Reporte"));
                    objUsuarios.setId_Usuario(jsonObjectFeed.getInt("Id_Usuario"));
                    objUsuarios.setNombre_Usuario(jsonObjectFeed.getString("Nombre_Usuario"));
                    objNoticias.setId_Noticia(jsonObjectFeed.getInt("Id_Noticia"));
                    objNoticias.setNombre_Noticia(jsonObjectFeed.getString("Nombre_Noticia"));
                    objReportes.setObjNoticias(objNoticias);
                    objReportes.setObjUsuarios(objUsuarios);
                    listReportes.add(objReportes);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return listReportes;
        }
    }
    public int Registrar_Reporte(Reportes objReportes){
        String strURL = urlServer+"index_reportes.php?opcion=Registrar_Reporte&Id_Noticia="+objReportes.getObjNoticias().getId_Noticia()+"&Id_Usuario="+objReportes.getObjUsuarios().getId_Usuario()+"&Id_Tipo_Reporte="+objReportes.getId_TipoReporte();
        Conexion con = new Conexion();
        int intRes = Integer.parseInt(con.HttpRequestString(strURL));
        return intRes;
    }


    public int getId_TipoReporte() {
        return Id_TipoReporte;
    }

    public void setId_TipoReporte(int id_TipoReporte) {
        Id_TipoReporte = id_TipoReporte;
    }
}
