package Datos;

/**
 * Created by USUARIO on 10/09/2017.
 */

public class Personas {
    private int Id_Persona;
    private  String Nombres;
    private String Apellidos;
    private String Celular;


    public Personas(){}


    public int getId_Persona() {
        return Id_Persona;
    }

    public void setId_Persona(int id_Persona) {
        Id_Persona = id_Persona;
    }

    public String getNombres() {
        return Nombres;
    }

    public void setNombres(String nombres) {
        Nombres = nombres;
    }

    public String getApellidos() {
        return Apellidos;
    }

    public void setApellidos(String apellidos) {
        Apellidos = apellidos;
    }

    public String getCelular() {
        return Celular;
    }

    public void setCelular(String celular) {
        Celular = celular;
    }
}
