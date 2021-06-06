package Logica;

import java.util.List;

import Datos.*;

/**
 * Created by ASUS on 28/12/2017.
 */

public class Temas {
    public List<Datos.Temas> Listar_Temas(int intOpcion){
        Datos.Temas temas = new Datos.Temas();
        return temas.Listar_Temas(intOpcion);
    }

    public List<Datos.Temas> Buscar_Temas(String strBusqueda){
        Datos.Temas temas = new Datos.Temas();
        return temas.Buscar_Temas(strBusqueda);
    }

    public List<Datos.Temas> Cargar_Tema(int intIdTema){
        Datos.Temas objTema = new Datos.Temas();
        objTema.setId_Tema(intIdTema);
        return objTema.Cargar_Tema(objTema);
    }

    public int Registrar_Tema(String strNombreTema, String strDescripcionTema){
        Datos.Temas objTema = new Datos.Temas();
        objTema.setNombre_Tema(strNombreTema);
        objTema.setDescripcion_Tema(strDescripcionTema);
        return objTema.Registrar_Tema(objTema);
    }

    public int Actualizar_Tema(int intIdTema, String strNombreTema, String strDescripcionTema){
        Datos.Temas objTema = new Datos.Temas();
        objTema.setId_Tema(intIdTema);
        objTema.setNombre_Tema(strNombreTema);
        objTema.setDescripcion_Tema(strDescripcionTema);
        return objTema.Actualizar_Tema(objTema);
    }

    public int Cambiar_Estado(int intIdTema, int intEstado){
        Datos.Temas objTema = new Datos.Temas();
        objTema.setId_Tema(intIdTema);
        objTema.setEstado_Tema(intIdTema);
        return objTema.Cambiar_Estado(objTema);
    }

}
