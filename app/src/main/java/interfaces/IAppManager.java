package interfaces;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import Datos.Noticias;
import Datos.Temas;


/**
 * Created by USUARIO on 29/07/2016.
 */
public interface IAppManager {
    public void setListNoticias(List<Noticias> listaNoticia);
    public List<Noticias> getListNoticias();
    public void setListTemas(List<Temas> listaTemas);
    public List<Temas> getListTemas();
    public void setListEjemplos(List<Noticias> listaNoticia);
    public List<Noticias> getListEjemplos();
    public void setListNoticiasBusqueda(List<Noticias> listaNoticiaBusqueda);
    public List<Noticias> getListNoticiaBusqueda();

    public boolean Conectado_Network();
    public boolean UsuarioConectado();
    public void mensajeRecibido(String StrMensaje);
    public JSONObject Login(String strUsuario, String strPassword);
    public String Listar_Noticias(int Opcion);
    public boolean Enviar_Mensaje(String strUsuario, int strId_Usuario, String strMensaje, int intPuerto, String strIP);
    public boolean Actualizar_IPuerto(int intId_Usuario);
    public boolean Registrar_Gustar(int intId_Noticia);
    public Boolean Registrar_Publicacion(String strdata, String strNombrePublicacion, String strDescripcion, String strUrl);
    public String Listar_Temas();
    public String Listar_Ejemplos();
    public String Buscar_Noticias(String strBusqueda);
    public String Cargar_Archivo(String strURL);
}
