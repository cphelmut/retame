package Logica;

import java.util.List;

/**
 * Created by ASUS on 2/02/2018.
 */

public class Materias {
    public List<Datos.Materias> Listar_Materias(int intOpcion){
        Datos.Materias objMaterias = new Datos.Materias();
        return objMaterias.Listar_Materias(intOpcion);
    }

    public int Cambiar_Estado(int intIdMateria, int intEstado){
        Datos.Materias objMaterias = new Datos.Materias();
        objMaterias.setId_Materia(intIdMateria);
        objMaterias.setEstado_Materia(intEstado);
        return objMaterias.Cambiar_Estado(objMaterias);
    }

    public List<Datos.Materias> Cargar_Materia(int intIdMateria){
        Datos.Materias objMaterias = new Datos.Materias();
        objMaterias.setId_Materia(intIdMateria);
        return objMaterias.Cargar_Materia(objMaterias);
    }

    public int Registrar_Materia(String strNombreMateria, String strDescripcionMateria){
        Datos.Materias objMaterias = new Datos.Materias();
        objMaterias.setNombre_Materia(strNombreMateria);
        objMaterias.setDescripcion_Materia(strDescripcionMateria);
        return objMaterias.Registrar_Materia(objMaterias);
    }

    public int Actualizar_Materia(int intIdMateria, String strNombreMateria, String strDescripcionMateria){
        Datos.Materias objMaterias = new Datos.Materias();
        objMaterias.setId_Materia(intIdMateria);
        objMaterias.setNombre_Materia(strNombreMateria);
        objMaterias.setDescripcion_Materia(strDescripcionMateria);
        return objMaterias.Actualizar_Materia(objMaterias);
    }
}
