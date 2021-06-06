package Logica;

import java.util.List;

import Datos.*;
import Datos.Noticias;

/**
 * Created by ASUS on 31/01/2018.
 */

public class Reportes {

    public List<Datos.Reportes> Listar_Reportes(){
        Datos.Reportes objReportes = new Datos.Reportes();
        return objReportes.Listar_Reportes();
    }

    public int Registrar_Reportes(int intIdTipoReporte, int intIdUsuario, int intIdNoticia){
        Datos.Reportes objReportes = new Datos.Reportes();
        Datos.Noticias objNoticias = new Datos.Noticias();
        Datos.Usuarios objUsuarios = new Datos.Usuarios();
        objNoticias.setId_Noticia(intIdNoticia);
        objUsuarios.setId_Usuario(intIdUsuario);
        objReportes.setId_TipoReporte(intIdTipoReporte);
        objReportes.setObjUsuarios(objUsuarios);
        objReportes.setObjNoticias(objNoticias);
        return objReportes.Registrar_Reporte(objReportes);
    }
}
