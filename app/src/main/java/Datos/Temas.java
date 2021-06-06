package Datos;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import Conexion.Conexion;

/**
 * Created by USUARIO on 10/09/2017.
 */

public class Temas {
    private int Id_Tema;
    private String Nombre_Tema;
    private String Descripcion_Tema;
    private int Estado_Tema;
    private Materias objMateria;
    String urlServer = "http://192.168.43.254/retame/";

    public Temas(){}

    public Temas(int intIdTema, String strNombreTema, String strDescripcionTema, int intEstadoTemas){

        this.Id_Tema = intIdTema;
        this.Nombre_Tema = strNombreTema;
        this.Descripcion_Tema = strDescripcionTema;
        this.Estado_Tema = intEstadoTemas;
    }

    public int getId_Tema() {
        return Id_Tema;
    }

    public void setId_Tema(int id_Tema) {
        Id_Tema = id_Tema;
    }

    public String getNombre_Tema() {
        return Nombre_Tema;
    }

    public void setNombre_Tema(String nombre_Tema) {
        Nombre_Tema = nombre_Tema;
    }

    public String getDescripcion_Tema() {
        return Descripcion_Tema;
    }

    public void setDescripcion_Tema(String descripcion_Tema) {
        Descripcion_Tema = descripcion_Tema;
    }

    public int getEstado_Tema() {
        return Estado_Tema;
    }

    public void setEstado_Tema(int estado_Tema) {
        Estado_Tema = estado_Tema;
    }

    public Materias getObjMateria() {
        return objMateria;
    }

    public void setObjMateria(Materias objMateria) {
        this.objMateria = objMateria;
    }

    public List<Temas> Listar_Temas(int intOpcion){
        List<Temas> listTemas = new ArrayList<Temas>();
        String url_noticias = "";
        if(intOpcion ==1){
            url_noticias = urlServer + "index_tema.php?opcion=Listar_TemasAd";
        }else {
            url_noticias = urlServer + "index_tema.php?opcion=Listar_Temas";
        }
        Conexion con = new Conexion();
        JSONObject jsonObjectListarTemas = con.HttpRequest(url_noticias);
        if(jsonObjectListarTemas.equals(null)){
            return null;
        }else {
            try {
                JSONArray jsonArrayTemas = jsonObjectListarTemas.getJSONArray("Listar_Temas");
                for (int i = 0; i < jsonArrayTemas.length(); i++) {
                    Temas objTema = new Temas();
                    JSONObject jsonObjectFeed = (JSONObject) jsonArrayTemas.get(i);
                    objTema.setId_Tema(jsonObjectFeed.getInt("Id_Tema"));
                    objTema.setNombre_Tema(jsonObjectFeed.getString("Nombre_Tema"));
                    objTema.setDescripcion_Tema(jsonObjectFeed.getString("Descripcion_Tema"));
                    objTema.setEstado_Tema(jsonObjectFeed.getInt("Estado_Tema"));
                    listTemas.add(objTema);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return listTemas;
        }
    }

    public List<Temas> Cargar_Tema(Temas objTemas){
        List<Temas> listTemas = new ArrayList<Temas>();
        final String url_noticias = urlServer+"index_tema.php?opcion=Cargar_Tema&Id_Tema="+objTemas.getId_Tema();
        Conexion con = new Conexion();
        JSONObject jsonObjectCargarTema = con.HttpRequest(url_noticias);

        if(jsonObjectCargarTema.equals(null)){
            return null;
        }else {
            try {
                JSONArray jsonArrayTema = jsonObjectCargarTema.getJSONArray("Cargar_Tema");
                for (int i = 0; i < jsonArrayTema.length(); i++) {
                    Temas objTema = new Temas();
                    JSONObject jsonObjectFeed = (JSONObject) jsonArrayTema.get(i);
                    objTema.setId_Tema(jsonObjectFeed.getInt("Id_Tema"));
                    objTema.setNombre_Tema(jsonObjectFeed.getString("Nombre_Tema"));
                    objTema.setDescripcion_Tema(jsonObjectFeed.getString("Descripcion_Tema"));
                    objTema.setEstado_Tema(jsonObjectFeed.getInt("Estado_Tema"));
                    listTemas.add(objTema);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return listTemas;
        }
    }

    public List<Temas> Buscar_Temas(String strBusqueda){
        List<Temas> listTemas = new ArrayList<Temas>();
        String url_noticias = urlServer + "index_tema.php?opcion=Buscar_Temas&Busqueda="+strBusqueda;
        Conexion con = new Conexion();
        JSONObject jsonObjectListarTemas = con.HttpRequest(url_noticias);
        if(jsonObjectListarTemas.equals(null)){
            return null;
        }else {
            try {
                JSONArray jsonArrayTemas = jsonObjectListarTemas.getJSONArray("Buscar_Temas");
                for (int i = 0; i < jsonArrayTemas.length(); i++) {
                    Temas objTema = new Temas();
                    JSONObject jsonObjectFeed = (JSONObject) jsonArrayTemas.get(i);
                    objTema.setId_Tema(jsonObjectFeed.getInt("Id_Tema"));
                    objTema.setNombre_Tema(jsonObjectFeed.getString("Nombre_Tema"));
                    objTema.setDescripcion_Tema(jsonObjectFeed.getString("Descripcion_Tema"));
                    objTema.setEstado_Tema(jsonObjectFeed.getInt("Estado_Tema"));
                    listTemas.add(objTema);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return listTemas;
        }
    }


    public int Registrar_Tema(Temas objTemas){
        String strURL = urlServer+"index_tema.php?opcion=Registrar_Tema&Nombre_Tema="+objTemas.getNombre_Tema()+"&Descripcion_Tema="+objTemas.getDescripcion_Tema();
        Conexion con = new Conexion();
        int intRes = Integer.parseInt(con.HttpRequestString(strURL));
        return intRes;
    }

    public int Actualizar_Tema(Temas objTemas){
        String strURL = urlServer+"index_tema.php?opcion=Actualizar_Tema&Id_Tema="+objTemas.getId_Tema()+"&Nombre_Tema="+objTemas.getNombre_Tema()+"&Descripcion_Tema="+objTemas.getDescripcion_Tema();
        Conexion con = new Conexion();
        int intRes = Integer.parseInt(con.HttpRequestString(strURL));
        return intRes;
    }

    public int Cambiar_Estado(Temas objTemas){
        String strURL = urlServer+"index_tema.php?opcion=Cambiar_Estado&Id_Tema="+objTemas.getId_Tema()+"&Estado_Tema="+objTemas.getEstado_Tema();
        Conexion con = new Conexion();
        int intRes = Integer.parseInt(con.HttpRequestString(strURL));
        return intRes;
    }
}
