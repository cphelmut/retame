package Datos;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import Conexion.Conexion;

/**
 * Created by ASUS on 2/02/2018.
 */

public class  Materias {
    private int Id_Materia;
    private String Nombre_Materia;
    private String Descripcion_Materia;
    private int Estado_Materia;
    String urlServer = "http://192.168.43.254/retame/";

    public int getId_Materia() {
        return Id_Materia;
    }

    public void setId_Materia(int id_Materia) {
        Id_Materia = id_Materia;
    }

    public String getNombre_Materia() {
        return Nombre_Materia;
    }

    public void setNombre_Materia(String nombre_Materia) {
        Nombre_Materia = nombre_Materia;
    }

    public String getDescripcion_Materia() {
        return Descripcion_Materia;
    }

    public void setDescripcion_Materia(String descripcion_Materia) {
        Descripcion_Materia = descripcion_Materia;
    }

    public int getEstado_Materia() {
        return Estado_Materia;
    }

    public void setEstado_Materia(int estado_Materia) {
        Estado_Materia = estado_Materia;
    }

    public  List<Materias> Listar_Materias(int intOpcion){
        String url_noticias = "";
        List<Materias> listMaterias = new ArrayList<Materias>();
        if(intOpcion ==1){
            url_noticias = urlServer + "index_materia.php?opcion=Listar_MateriasAd";
        }else {
             url_noticias = urlServer + "index_materia.php?opcion=Listar_Materias";
        }
        Conexion con = new Conexion();
        JSONObject jsonObjectListarTemas = con.HttpRequest(url_noticias);
        if(jsonObjectListarTemas.equals(null)){
            return null;
        }else {
            try {
                JSONArray jsonArrayTemas = jsonObjectListarTemas.getJSONArray("Listar_Materias");
                for (int i = 0; i < jsonArrayTemas.length(); i++) {
                    Materias objMaterias = new Materias();
                    JSONObject jsonObjectFeed = (JSONObject) jsonArrayTemas.get(i);
                    objMaterias.setId_Materia(jsonObjectFeed.getInt("Id_Materia"));
                    objMaterias.setNombre_Materia(jsonObjectFeed.getString("Nombre_Materia"));
                    objMaterias.setDescripcion_Materia(jsonObjectFeed.getString("Descripcion_Materia"));
                    objMaterias.setEstado_Materia(jsonObjectFeed.getInt("Estado_Materia"));
                    listMaterias.add(objMaterias);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return listMaterias;
        }
    }

    public  List<Materias> Buscar_Materia(String strBusqueda){
        List<Materias> listMaterias = new ArrayList<Materias>();
        String url_noticias = urlServer + "index_materia.php?opcion=Buscar_Materia&Busqueda="+strBusqueda;
        Conexion con = new Conexion();
        JSONObject jsonObjectListarTemas = con.HttpRequest(url_noticias);
        if(jsonObjectListarTemas.equals(null)){
            return null;
        }else {
            try {
                JSONArray jsonArrayTemas = jsonObjectListarTemas.getJSONArray("Buscar_Materia");
                for (int i = 0; i < jsonArrayTemas.length(); i++) {
                    Materias objMaterias = new Materias();
                    JSONObject jsonObjectFeed = (JSONObject) jsonArrayTemas.get(i);
                    objMaterias.setId_Materia(jsonObjectFeed.getInt("Id_Materia"));
                    objMaterias.setNombre_Materia(jsonObjectFeed.getString("Nombre_Materia"));
                    objMaterias.setDescripcion_Materia(jsonObjectFeed.getString("Descripcion_Materia"));
                    objMaterias.setEstado_Materia(jsonObjectFeed.getInt("Estado_Materia"));
                    listMaterias.add(objMaterias);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return listMaterias;
        }
    }
    public  int Cambiar_Estado(Materias objMaterias){
        String strURL = urlServer+"index_materia.php?opcion=Cambiar_Estado&Id_Materia="+objMaterias.getId_Materia()+"&Estado_Materia="+objMaterias.getEstado_Materia();
        Conexion con = new Conexion();
        int intRes = Integer.parseInt(con.HttpRequestString(strURL));
        return intRes;
    }

    public List<Materias> Cargar_Materia(Materias objMaterias){
        List<Materias> listMaterias = new ArrayList<Materias>();
        String url_noticias = urlServer + "index_materia.php?opcion=Cargar_Materia&Id_Materia"+objMaterias.getId_Materia();
        Conexion con = new Conexion();
        JSONObject jsonObjectListarTemas = con.HttpRequest(url_noticias);
        if(jsonObjectListarTemas.equals(null)){
            return null;
        }else {
            try {
                JSONArray jsonArrayTemas = jsonObjectListarTemas.getJSONArray("Buscar_Temas");
                for (int i = 0; i < jsonArrayTemas.length(); i++) {
                    JSONObject jsonObjectFeed = (JSONObject) jsonArrayTemas.get(i);
                    objMaterias.setId_Materia(jsonObjectFeed.getInt("Id_Materia"));
                    objMaterias.setNombre_Materia(jsonObjectFeed.getString("Nombre_Materia"));
                    objMaterias.setDescripcion_Materia(jsonObjectFeed.getString("Descripcion_Materia"));
                    objMaterias.setEstado_Materia(jsonObjectFeed.getInt("Estado_Materia"));
                    listMaterias.add(objMaterias);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return listMaterias;
        }
    }

    public  int Registrar_Materia(Materias objMateria){
        String strURL = urlServer+"index_materia.php?opcion=Registrar_Materia&Id_Materia="+objMateria.getId_Materia()+"&Nombre_Materia="+objMateria.getNombre_Materia()+"&Descripcion_Materia="+objMateria.getDescripcion_Materia();
        Conexion con = new Conexion();
        int intRes = Integer.parseInt(con.HttpRequestString(strURL));
        return intRes;
    }

    public  int Actualizar_Materia(Materias objMateria){
        String strURL = urlServer+"index_materia.php?opcion=Actualizar_Materia&Id_Materia="+objMateria.getId_Materia()+"&Nombre_Materia="+objMateria.getNombre_Materia()+"&Descripcion_Materia="+objMateria.getDescripcion_Materia();
        Conexion con = new Conexion();
        int intRes = Integer.parseInt(con.HttpRequestString(strURL));
        return intRes;
    }
}
