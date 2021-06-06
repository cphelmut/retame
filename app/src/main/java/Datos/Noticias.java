package Datos;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import Conexion.Conexion;



/**
 * Created by USUARIO on 09/09/2017.
 */

public class Noticias {
    public int Id_Noticia;
    public String Nombre_Noticia;
    public String Imagen_Noticia;
    public String Descripcion_Noticia;
    private String Fecha_Registro;
    private String URL;
    private String Archivo_Noticia;
    private Usuarios Objusuario;
    private Categorias Objcategoria;
    private Temas objTema;
    private Materias objMateria;
    String urlServer = "http://192.168.43.254/retame/";


    public int getId_Noticia() {
        return Id_Noticia;
    }

    public void setId_Noticia(int id_Noticia) {
        Id_Noticia = id_Noticia;
    }

    public String getNombre_Noticia() {
        return Nombre_Noticia;
    }

    public void setNombre_Noticia(String nombre_Noticia) {
        Nombre_Noticia = nombre_Noticia;
    }

    public String getImagen_Noticia() {
        return Imagen_Noticia;
    }

    public void setImagen_Noticia(String imagen_Noticia) {
        Imagen_Noticia = imagen_Noticia;
    }

    public String getDescripcion_Noticia() {
        return Descripcion_Noticia;
    }

    public void setDescripcion_Noticia(String descripcion_Noticia) { Descripcion_Noticia = descripcion_Noticia; }

    public String getFecha_Registro() {
        return Fecha_Registro;
    }

    public void setFecha_Registro(String fecha_Registro) {
        Fecha_Registro = fecha_Registro;
    }

    public Usuarios getObjusuario() {
        return Objusuario;
    }

    public void setObjusuario(Usuarios objusuario) {
        Objusuario = objusuario;
    }
    public Categorias getObjcategoria() {
        return Objcategoria;
    }

    public void setObjcategoria(Categorias objcategoria) {
        Objcategoria = objcategoria;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public String getArchivo_Noticia() {
        return Archivo_Noticia;
    }

    public void setArchivo_Noticia(String archivo_Noticia) {
        Archivo_Noticia = archivo_Noticia;
    }

    public Temas getObjTema() {
        return objTema;
    }

    public void setObjTema(Temas objTema) {
        this.objTema = objTema;
    }

    public Materias getObjMateria() {
        return objMateria;
    }

    public void setObjMateria(Materias objMateria) {
        this.objMateria = objMateria;
    }





    public List<Noticias> Listar_Noticias(Noticias objNoticias){
        List<Noticias> listNoticias = new ArrayList<Noticias>();
        final String url_noticias = urlServer+"index_noticias.php?opcion=Listar_Noticias";
        Conexion con = new Conexion();
        JSONObject jsonObjectListarNoticias = con.HttpRequest(url_noticias);
        if(jsonObjectListarNoticias.equals(null)){
            return null;
        }else {
            try {
                JSONArray jsonArrayNoticias = jsonObjectListarNoticias.getJSONArray("Listar_Noticias");
                for (int i = 0; i < jsonArrayNoticias.length(); i++) {
                    Noticias objNoticiasD = new Noticias();
                    Usuarios objUsuarios = new Usuarios();
                    Personas objPersonas = new Personas();
                    Categorias objcategorias = new Categorias();
                    Temas objTemas = new Temas();
                    JSONObject jsonObjectFeed = (JSONObject) jsonArrayNoticias.get(i);
                    objUsuarios.setId_Usuario(jsonObjectFeed.getInt("Id_Usuario"));
                    objPersonas.setId_Persona(jsonObjectFeed.getInt("Id_Persona"));
                    objUsuarios.setObjPersona(objPersonas);
                    objNoticiasD.setObjusuario(objUsuarios);
                    objcategorias.setId_Categoria(jsonObjectFeed.getInt("Id_Categoria"));
                    objcategorias.setNombre_Categoria(jsonObjectFeed.getString("Nombre_Categoria"));
                    objNoticiasD.setObjcategoria(objcategorias);
                    objTemas.setId_Tema(jsonObjectFeed.getInt("Id_tema"));
                    objTemas.setNombre_Tema(jsonObjectFeed.getString("Nombre_Tema"));
                    objNoticiasD.Registrar_Noticia(jsonObjectFeed.getInt("Id_Noticia"), jsonObjectFeed.getString("Nombre_Noticia"), jsonObjectFeed.getString("Imagen_Noticia"), jsonObjectFeed.getString("Descripcion_Noticia"), jsonObjectFeed.getString("Fecha_Registro"), jsonObjectFeed.getString("url"), jsonObjectFeed.getString("Archivo_Noticia"), objcategorias, objUsuarios, objTemas);
                    listNoticias.add(objNoticiasD);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return listNoticias;
        }
    }

    public List<Noticias> Listar_Ejemplos(Noticias objNoticias){
        List<Noticias> listNoticias = new ArrayList<Noticias>();
        final String url_noticias = urlServer+"index_noticias.php?opcion=Listar_Ejemplos";
        Conexion con = new Conexion();
        JSONObject jsonObjectListarNoticias = con.HttpRequest(url_noticias);
        if(jsonObjectListarNoticias.equals(null)){
            return null;
        }else {
            try {
                JSONArray jsonArrayNoticias = jsonObjectListarNoticias.getJSONArray("Listar_Ejemplos");
                for (int i = 0; i < jsonArrayNoticias.length(); i++) {
                    Noticias objNoticiasD = new Noticias();
                    Usuarios objUsuarios = new Usuarios();
                    Personas objPersonas = new Personas();
                    Categorias objcategorias = new Categorias();
                    Temas objTemas = new Temas();
                    JSONObject jsonObjectFeed = (JSONObject) jsonArrayNoticias.get(i);
                    objUsuarios.setId_Usuario(jsonObjectFeed.getInt("Id_Usuario"));
                    objPersonas.setId_Persona(jsonObjectFeed.getInt("Id_Persona"));
                    objUsuarios.setObjPersona(objPersonas);
                    objNoticiasD.setObjusuario(objUsuarios);
                    objcategorias.setId_Categoria(jsonObjectFeed.getInt("Id_Categoria"));
                    objcategorias.setNombre_Categoria(jsonObjectFeed.getString("Nombre_Categoria"));
                    objNoticiasD.setObjcategoria(objcategorias);
                    objTemas.setId_Tema(jsonObjectFeed.getInt("Id_tema"));
                    objTemas.setNombre_Tema(jsonObjectFeed.getString("Nombre_Tema"));
                    objNoticiasD.Registrar_Noticia(jsonObjectFeed.getInt("Id_Noticia"), jsonObjectFeed.getString("Nombre_Noticia"), jsonObjectFeed.getString("Imagen_Noticia"), jsonObjectFeed.getString("Descripcion_Noticia"), jsonObjectFeed.getString("Fecha_Registro"), jsonObjectFeed.getString("url"), jsonObjectFeed.getString("Archivo_Noticia"), objcategorias, objUsuarios, objTemas);
                    listNoticias.add(objNoticiasD);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return listNoticias;
        }
    }

    public List<Noticias> Buscar_Noticias(String strBusqueda){
        List<Noticias> listNoticias = new ArrayList<Noticias>();
        String url_noticias = urlServer+"index_noticias.php?opcion=Buscar_Noticias&Busqueda="+strBusqueda;
        Conexion con = new Conexion();
        JSONObject jsonObjectListarNoticias = con.HttpRequest(url_noticias);
        if(jsonObjectListarNoticias.equals(null)){
            return null;
        }else {
            try {
                Log.e("Hola Jhon ", "Estamos en Try");
                JSONArray jsonArrayNoticias = jsonObjectListarNoticias.getJSONArray("Buscar_Noticias");
                for (int i = 0; i < jsonArrayNoticias.length(); i++) {
                    Noticias objNoticiasD = new Noticias();
                    Usuarios objUsuarios = new Usuarios();
                    Personas objPersonas = new Personas();
                    Categorias objcategorias = new Categorias();
                    Temas objTemas = new Temas();
                    JSONObject jsonObjectFeed = (JSONObject) jsonArrayNoticias.get(i);
                    objUsuarios.setId_Usuario(jsonObjectFeed.getInt("Id_Usuario"));
                    objPersonas.setId_Persona(jsonObjectFeed.getInt("Id_Persona"));
                    objUsuarios.setObjPersona(objPersonas);
                    objNoticiasD.setObjusuario(objUsuarios);
                    objcategorias.setId_Categoria(jsonObjectFeed.getInt("Id_Categoria"));
                    objcategorias.setNombre_Categoria(jsonObjectFeed.getString("Nombre_Categoria"));
                    objNoticiasD.setObjcategoria(objcategorias);
                    objTemas.setId_Tema(jsonObjectFeed.getInt("Id_tema"));
                    objTemas.setNombre_Tema(jsonObjectFeed.getString("Nombre_Tema"));
                    objNoticiasD.Registrar_Noticia(jsonObjectFeed.getInt("Id_Noticia"), jsonObjectFeed.getString("Nombre_Noticia"), jsonObjectFeed.getString("Imagen_Noticia"), jsonObjectFeed.getString("Descripcion_Noticia"), jsonObjectFeed.getString("Fecha_Registro"), jsonObjectFeed.getString("url"), jsonObjectFeed.getString("Archivo_Noticia"), objcategorias, objUsuarios, objTemas);
                    listNoticias.add(objNoticiasD);

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return listNoticias;
        }
    }

    public int Registrar_Noticia(Noticias objNoticia){
        String strUrl =  urlServer+"index_noticias.php?opcion=Registrar_Noticia&Id_Usuario="+objNoticia.getObjusuario().getId_Usuario()+"&Nombre_Noticia="+objNoticia.getNombre_Noticia()+"&Descripcion="+objNoticia.getDescripcion_Noticia()+"&url="+objNoticia.getURL();
        Conexion con = new Conexion();
        int intRes = Integer.parseInt(con.HttpRequestString(strUrl));
        return intRes;

    }

    public void Registrar_Noticia(int intId_Noticia, String strNombre_Noticia, String strImagen_Noticia, String strDescripcion_Noticia, String strTiempo_Publicacion, String strURL, String strPDF, Categorias objcategoria, Usuarios objusuario, Temas objTema){
        this.setId_Noticia(intId_Noticia);
        this.setNombre_Noticia(strNombre_Noticia);
        this.setImagen_Noticia(strImagen_Noticia);
        this.setDescripcion_Noticia(strDescripcion_Noticia);
        this.setFecha_Registro(strTiempo_Publicacion);
        this.setURL(strURL);
        this.setArchivo_Noticia(strPDF);
        this.setObjcategoria(objcategoria);
        this.setObjusuario(objusuario);
        this.setObjTema(objTema);
    }



}
