package Logica;

import java.util.List;

import Datos.*;

/**
 * Created by USUARIO on 09/09/2017.
 */

public class Usuarios {

    public List<Datos.Usuarios> Listar_Usuarios(){
        Datos.Usuarios objUsuario = new Datos.Usuarios();
        return objUsuario.Listar_Usuarios();
    }

    public List<Datos.Usuarios> Buscar_Usuarios(String strBusqueda){
        Datos.Usuarios objUsuario = new Datos.Usuarios();
        return objUsuario.Buscar_Usuarios(strBusqueda);
    }

    public int Registrar_Usuario(String strNombres, String strApellidos, String strCelular, String strNombreUsuario, String strContrasenia, String strRol){
        Datos.Usuarios objUsuario = new Datos.Usuarios();
        Datos.Personas objPersonas = new Datos.Personas();
        objPersonas.setNombres(strNombres);
        objPersonas.setApellidos(strApellidos);
        objPersonas.setCelular(strCelular);
        objUsuario.setObjPersona(objPersonas);
        objUsuario.setNombre_Usuario(strNombreUsuario);
        objUsuario.setContrasenia(strContrasenia);
        objUsuario.setRol(strRol);
        return objUsuario.Registrar_Usuario(objUsuario);
    }

    public int Actualizar_Usuario(int intIdUsuario, int intIdPersona, String strNombres, String strApellidos, String strCelular, String strNombreUsuario,  String strRol){
        Datos.Usuarios objUsuario = new Datos.Usuarios();
        Datos.Personas objPersonas = new Datos.Personas();
        objPersonas.setId_Persona(intIdPersona);
        objPersonas.setNombres(strNombres);
        objPersonas.setApellidos(strApellidos);
        objPersonas.setCelular(strCelular);
        objUsuario.setObjPersona(objPersonas);
        objUsuario.setId_Usuario(intIdUsuario);
        objUsuario.setNombre_Usuario(strNombreUsuario);
        objUsuario.setRol(strRol);
        return objUsuario.Actualizar_Usuario(objUsuario);
    }
    public List<Datos.Usuarios> Cargar_Informacion(int intIdUsuario){
        Datos.Usuarios objUsuario = new Datos.Usuarios();
        objUsuario.setId_Usuario(intIdUsuario);
        return objUsuario.Cagar_Informacion(objUsuario);
    }

    public int Cambiar_Estado(int intIdUsuario, int intEstado){
        Datos.Usuarios objUsuario = new Datos.Usuarios();
        objUsuario.setId_Usuario(intIdUsuario);
        objUsuario.setEstado(intEstado);
        return objUsuario.Cambiar_Estado(objUsuario);
    }


}
