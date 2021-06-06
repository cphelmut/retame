package Datos;


import android.content.Context;
import android.content.SharedPreferences;
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

public class Usuarios {
    private int Id_Usuario;
    private String Nombre_Usuario;
    private String Contrasenia;
    private String Tiempo_Autenticacion;
    private String Tiempo_Ingreso;
    private String IP;
    private int Puerto;
    private String Imagen_Usuario;
    private int Estado;
    private String Rol;
    private Personas ObjPersona;
    String urlServer = "http://192.168.43.254/retame/";

    public int getId_Usuario() {
        return Id_Usuario;
    }

    public void setId_Usuario(int id_Usuario) {
        Id_Usuario = id_Usuario;
    }

    public String getNombre_Usuario() {
        return Nombre_Usuario;
    }

    public void setNombre_Usuario(String nombre_Usuario) {
        Nombre_Usuario = nombre_Usuario;
    }


    public String getTiempo_Autenticacion() {
        return Tiempo_Autenticacion;
    }

    public void setTiempo_Autenticacion(String tiempo_Autenticacion) {
        Tiempo_Autenticacion = tiempo_Autenticacion;
    }

    public String getTiempo_Ingreso() {
        return Tiempo_Ingreso;
    }

    public void setTiempo_Ingreso(String tiempo_Ingreso) {
        Tiempo_Ingreso = tiempo_Ingreso;
    }
    public String getIP() {
        return IP;
    }

    public void setIP(String IP) {
        this.IP = IP;
    }

    public int getPuerto() {
        return Puerto;
    }

    public void setPuerto(int puerto) {
        Puerto = puerto;
    }

    public String getImagen_Usuario() {
        return Imagen_Usuario;
    }

    public void setImagen_Usuario(String imagen_Usuario) {
        Imagen_Usuario = imagen_Usuario;
    }

    public Personas getObjPersona() {
        return ObjPersona;
    }

    public void setObjPersona(Personas objPersona) {
        ObjPersona = objPersona;
    }


    public int getEstado() {
        return Estado;
    }

    public void setEstado(int estado) {
        Estado = estado;
    }

    public String getRol() {
        return Rol;
    }

    public void setRol(String rol) {
        Rol = rol;
    }

    public String getContrasenia() {
        return Contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        Contrasenia = contrasenia;
    }

    public List<Usuarios> Listar_Usuarios(){
        List<Usuarios> listUsuarios = new ArrayList<Usuarios>();
        String url_noticias = urlServer+"index_usuarios.php?opcion=Listar_Usuarios";
        Conexion con = new Conexion();
        JSONObject jsonObjectListarUsuarios = con.HttpRequest(url_noticias);
        if(jsonObjectListarUsuarios.equals(null)){
            return null;
        }else {
            try {
                JSONArray jsonArrayNoticias = jsonObjectListarUsuarios.getJSONArray("Listar_Usuarios");
                for (int i = 0; i < jsonArrayNoticias.length(); i++) {
                    Usuarios objUsuarios = new Usuarios();
                    Personas objPersonas = new Personas();
                    JSONObject jsonObjectFeed = (JSONObject) jsonArrayNoticias.get(i);
                    objUsuarios.setId_Usuario(jsonObjectFeed.getInt("Id_Usuario"));
                    objUsuarios.setImagen_Usuario(jsonObjectFeed.getString("Imagen_Usuario"));
                    objUsuarios.setNombre_Usuario(jsonObjectFeed.getString("Nombre_Usuario"));
                    objUsuarios.setTiempo_Autenticacion(jsonObjectFeed.getString("Fecha_Ultimo_Inicio"));
                    objUsuarios.setTiempo_Ingreso(jsonObjectFeed.getString("Fecha_Registro"));
                    objUsuarios.setEstado(jsonObjectFeed.getInt("Estado_Usuario"));
                    objUsuarios.setRol(jsonObjectFeed.getString("Nombre_Rol"));
                    objUsuarios.setIP(jsonObjectFeed.getString("IP"));
                    objPersonas.setId_Persona(jsonObjectFeed.getInt("Id_Persona"));
                    objPersonas.setNombres(jsonObjectFeed.getString("Nombres"));
                    objPersonas.setApellidos(jsonObjectFeed.getString("Apellidos"));
                    objPersonas.setCelular(jsonObjectFeed.getString("Movil"));
                    objUsuarios.setObjPersona(objPersonas);
                    listUsuarios.add(objUsuarios);

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return listUsuarios;
        }
    }

    public int Registrar_Usuario(Usuarios objUsuarios){
        String strURL = urlServer+"index_usuarios.php?opcion=Registrar_Usuario&Nombres="+objUsuarios.getObjPersona().getNombres()+"&Apellidos="+objUsuarios.getObjPersona().getApellidos()+"&Celular="+objUsuarios.getObjPersona().getCelular()+"&Nombre_Usuario="+objUsuarios.getNombre_Usuario()+"&Contrasenia="+objUsuarios.getContrasenia()+"&Rol="+objUsuarios.getRol();
        Conexion con = new Conexion();

        int intRes = Integer.parseInt(con.HttpRequestString(strURL));
        return intRes;
    }

    public int Actualizar_Usuario(Usuarios objUsuarios){
        String strURL = urlServer+"index_usuarios.php?opcion=Actualizar_Usuario&Id_Usuario="+objUsuarios.getId_Usuario()+"&Id_Persona="+objUsuarios.getObjPersona().getId_Persona()+"&Nombres="+objUsuarios.getObjPersona().getNombres()+"&Apellidos="+objUsuarios.getObjPersona().getApellidos()+"&Celular="+objUsuarios.getObjPersona().getCelular()+"&Nombre_Usuario="+objUsuarios.getNombre_Usuario()+"&Rol="+objUsuarios.getRol();
        Conexion con = new Conexion();
    //    con.Ejecutar(strURL);
        int intRes = Integer.parseInt(con.HttpRequestString(strURL));
        return intRes;
    }

    public List<Usuarios> Cagar_Informacion(Usuarios objUsuario){
        String strURL = urlServer+"index_usuarios.php?opcion=Cargar_Informacion&Id_Usuario="+objUsuario.getId_Usuario();
        List<Usuarios> listUsuarios = new ArrayList<Usuarios>();
        Conexion con = new Conexion();
        JSONObject jsonObjectListarUsuarios = con.HttpRequest(strURL);
        if(jsonObjectListarUsuarios.equals(null)){
            return null;
        }else {
            try {
                JSONArray jsonArrayNoticias = jsonObjectListarUsuarios.getJSONArray("Cargar_Informacion");
                for (int i = 0; i < jsonArrayNoticias.length(); i++) {
                    Usuarios objUsuarios = new Usuarios();
                    Personas objPersonas = new Personas();
                    JSONObject jsonObjectFeed = (JSONObject) jsonArrayNoticias.get(i);
                    objUsuarios.setId_Usuario(jsonObjectFeed.getInt("Id_Usuario"));
                    objUsuarios.setImagen_Usuario(jsonObjectFeed.getString("Imagen_Usuario"));
                    objUsuarios.setNombre_Usuario(jsonObjectFeed.getString("Nombre_Usuario"));
                    objUsuarios.setEstado(jsonObjectFeed.getInt("Estado"));
                    objUsuarios.setRol(jsonObjectFeed.getString("Nombre_Rol"));
                    objPersonas.setId_Persona(jsonObjectFeed.getInt("Id_Persona"));
                    objPersonas.setNombres(jsonObjectFeed.getString("Nombres"));
                    objPersonas.setApellidos(jsonObjectFeed.getString("Apellidos"));
                    objPersonas.setCelular(jsonObjectFeed.getString("Movil"));
                    objUsuarios.setObjPersona(objPersonas);
                    listUsuarios.add(objUsuarios);

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return listUsuarios;
        }
    }



    public int Cambiar_Estado(Usuarios objUsuario){
        String strURL = urlServer+"index_usuarios.php?opcion=Cambiar_Estado&Id_Usuario="+objUsuario.getId_Usuario()+"&Estado="+objUsuario.getEstado();
        Conexion con = new Conexion();
        int intRes = Integer.parseInt(con.HttpRequestString(strURL));
        return intRes;
    }


    public List<Usuarios> Buscar_Usuarios(String strBusqueda){
        List<Usuarios> listUsuarios = new ArrayList<Usuarios>();
        String url_noticias = urlServer+"index_usuarios.php?opcion=Buscar_Usuarios&Busqueda="+strBusqueda;
        Conexion con = new Conexion();
        JSONObject jsonObjectListarUsuarios = con.HttpRequest(url_noticias);
        if(jsonObjectListarUsuarios.equals(null)){
            return null;
        }else {
            try {
                JSONArray jsonArrayNoticias = jsonObjectListarUsuarios.getJSONArray("Buscar_Usuarios");
                for (int i = 0; i < jsonArrayNoticias.length(); i++) {
                    Usuarios objUsuarios = new Usuarios();
                    Personas objPersonas = new Personas();
                    JSONObject jsonObjectFeed = (JSONObject) jsonArrayNoticias.get(i);
                    objUsuarios.setId_Usuario(jsonObjectFeed.getInt("Id_Usuario"));
                    objUsuarios.setImagen_Usuario(jsonObjectFeed.getString("Imagen_Usuario"));
                    objUsuarios.setNombre_Usuario(jsonObjectFeed.getString("Nombre_Usuario"));
                    objUsuarios.setTiempo_Autenticacion(jsonObjectFeed.getString("Fecha_Ultimo_Inicio"));
                    objUsuarios.setTiempo_Ingreso(jsonObjectFeed.getString("Fecha_Registro"));
                    objUsuarios.setEstado(jsonObjectFeed.getInt("Estado_Usuario"));
                    objUsuarios.setRol(jsonObjectFeed.getString("Nombre_Rol"));
                    objUsuarios.setIP(jsonObjectFeed.getString("IP"));
                    objPersonas.setId_Persona(jsonObjectFeed.getInt("Id_Persona"));
                    objPersonas.setNombres(jsonObjectFeed.getString("Nombres"));
                    objPersonas.setApellidos(jsonObjectFeed.getString("Apellidos"));
                    objPersonas.setCelular(jsonObjectFeed.getString("Movil"));
                    objUsuarios.setObjPersona(objPersonas);
                    listUsuarios.add(objUsuarios);

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return listUsuarios;
        }
    }

    public void Almacenar_VariableSesion(Context context, String strId_Usuario, String strNombre_Usuario, String strRol, String strImagen) {
        SharedPreferences.Editor editorSesion = context.getSharedPreferences("Retame", Context.MODE_PRIVATE).edit();
        editorSesion.putString("Id", strId_Usuario);
        editorSesion.putString("user", strNombre_Usuario);
        editorSesion.putString("Rol", strRol);
        editorSesion.putString("Imagen", strImagen);
        editorSesion.commit();
    }

    public  String Obtener_VariableSesion(Context context, String key) {
        SharedPreferences sharedPreferencesSesion = context.getSharedPreferences("Retame",	Context.MODE_PRIVATE);
        String strId_Usuario = sharedPreferencesSesion.getString(key, "");
        return strId_Usuario;
    }





}
