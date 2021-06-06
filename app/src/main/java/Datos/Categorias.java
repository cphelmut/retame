package Datos;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import Conexion.Conexion;

/**
 * Created by USUARIO on 10/09/2017.
 */

public class Categorias {
    private int Id_Categoria;
    private String Nombre_Categoria;
    String urlServer = "http://192.168.43.254/retame/";


    public int getId_Categoria() {
        return Id_Categoria;
    }

    public void setId_Categoria(int id_Categoria) {
        Id_Categoria = id_Categoria;
    }

    public String getNombre_Categoria() {
        return Nombre_Categoria;
    }

    public void setNombre_Categoria(String nombre_Categoria) {
        Nombre_Categoria = nombre_Categoria;
    }

    public List<Categorias> Listar_Categorias(){
        List<Categorias> listCategorias = new ArrayList<Categorias>();
        final String url_noticias = urlServer+"index_categoria.php?opcion=Listar_Categorias";
        Conexion con = new Conexion();
        JSONObject jsonObjectListarCategorias = con.HttpRequest(url_noticias);
        if(jsonObjectListarCategorias.equals(null)){
            return null;
        }else {
            try {
                JSONArray jsonArrayCategorias = jsonObjectListarCategorias.getJSONArray("Listar_Categorias");
                for (int i = 0; i < jsonArrayCategorias.length(); i++) {
                    Categorias objcategorias = new Categorias();
                    JSONObject jsonObjectFeed = (JSONObject) jsonArrayCategorias.get(i);
                    objcategorias.setId_Categoria(jsonObjectFeed.getInt("Id_Categoria"));
                    objcategorias.setNombre_Categoria(jsonObjectFeed.getString("Nombre_Categoria"));
                    listCategorias.add(objcategorias);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return listCategorias;
        }
    }
}
