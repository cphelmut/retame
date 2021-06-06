package Logica;

import java.util.List;

/**
 * Created by USUARIO on 09/09/2017.
 */

public class Noticias {

    public List<Datos.Noticias> Listar_Noticias(Datos.Noticias objNoticias){
        Datos.Noticias noticias = new Datos.Noticias();
        return noticias.Listar_Noticias(objNoticias);
    }
    public List<Datos.Noticias> Listar_Ejemplos(Datos.Noticias objNoticias){
        Datos.Noticias noticias = new Datos.Noticias();
        return noticias.Listar_Ejemplos(objNoticias);
    }
    public List<Datos.Noticias> Buscar_Noticias(String strBusqueda){
        Datos.Noticias noticias = new Datos.Noticias();
        return  noticias.Buscar_Noticias(strBusqueda);
    }
    public int Registrar_Noticia(int intIdUsuario, String strNombreNoticia, String strDescripcion, String strUrl){
        Datos.Noticias objNoticias = new Datos.Noticias();
        Datos.Usuarios objUsuario = new Datos.Usuarios();
        objUsuario.setId_Usuario(intIdUsuario);
        objNoticias.setNombre_Noticia(strNombreNoticia);
        objNoticias.setDescripcion_Noticia(strDescripcion);
        objNoticias.setURL(strUrl);
        objNoticias.setObjusuario(objUsuario);
        return objNoticias.Registrar_Noticia(objNoticias);
    }
}
