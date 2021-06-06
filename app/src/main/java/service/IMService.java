package service;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import Datos.Noticias;
import Datos.Temas;
import interfaces.IAppManager;
import operadorsocket.OperadorSocket;

/**
 * Created by USUARIO on 29/07/2016.
 */
public class IMService extends Service implements IAppManager {
    public static final String LISTA_ACTUALIZADA = "1";
    public static final String FRIEND_LIST_ACTUALIZADA = "take_friend_List";
    public static final String MESSAGE_LIST_UPDATE = "take_Message_List";
    public ConnectivityManager connectivityManager = null;
    private final int tiempoActualizacion = 800000;
    OperadorSocket operadorSocket = new OperadorSocket(this);
    private  List<Noticias> noticiaLista = new ArrayList<Noticias>();
    private List<Temas> temasLista = new ArrayList<Temas>();
    private List<Noticias> ejemplosLista = new ArrayList<Noticias>();
    private List<Noticias> noticiasBuscarList = new ArrayList<Noticias>();

    private final IBinder imBinder = new IMBinder();
    private boolean autenticacionUsuario = false;
    private boolean autenticacionNetwork = false;
    private Timer timer;
    private NotificationManager notificationManager;
    JSONObject jsonObjectListarUsuarios = null;
    String result = "";
    String urlServer = "http://192.168.43.254/retame/";
    //String urlServer = "https://retame.000webhostapp.com/retame/";

    public class IMBinder extends Binder {
        public IAppManager getService(){
            return IMService.this;
        }
    }

    @Override
    public void onCreate() {
        notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        connectivityManager = (ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);
        timer = new Timer();

        Thread thread = new Thread(){
            @Override
            public void run() {
                Random random = new Random();
                int cont = 0;
                while (operadorSocket.iniciarListenting(10000 + random.nextInt(20000)) == 0){
                    cont ++;
                    if(cont >10){
                        break;
                    }
                }
            }
        };
        thread.start();
        this.autenticacionNetwork = true;

    }

    @Override
    public IBinder onBind(Intent intent) {
        return imBinder;
    }


    @Override
    public void setListNoticias(List<Noticias> listaNoticia) {
        this.noticiaLista = listaNoticia;
    }

    @Override
    public List<Noticias> getListNoticias() {
        return this.noticiaLista;
    }

    @Override
    public void setListTemas(List<Temas> listaTemas) {
        this.temasLista = listaTemas;
    }

    @Override
    public List<Temas> getListTemas() {
        return temasLista;
    }

    @Override
    public void setListEjemplos(List<Noticias> listaEjemplos) {
        this.setListEjemplos(listaEjemplos);
    }

    @Override
    public List<Noticias> getListEjemplos() {
        return ejemplosLista;
    }

    @Override
    public void setListNoticiasBusqueda(List<Noticias> listaNoticiaBusqueda) {
        this.noticiasBuscarList = listaNoticiaBusqueda;
    }

    @Override
    public List<Noticias> getListNoticiaBusqueda() {
        return noticiasBuscarList;
    }

    @Override
    public boolean Conectado_Network() {
        return this.autenticacionNetwork;
    }

    @Override
    public void mensajeRecibido(String StrMensaje) {

    }


    public boolean UsuarioConectado() {
        return this.autenticacionUsuario;
    }

    public JSONObject Login(String strUsuario, String strPassword){
        JSONObject jsonLogin = null;
        String url = urlServer+ "index_usuarios.php?opcion=Iniciar_Sesion&Nombre_Usuario="+strUsuario+"&Contrasenia="+strPassword;
        jsonLogin = operadorSocket.HttpRequest(url);
      /*  try {
            operadorSocket.main(url);
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        if(jsonLogin != null){
            return jsonLogin;
        }
        else {
            return null;
        }
    }

    public void  Listar_Usuarios(int TiempoActualizacion) {

    }

    @Override
    public String Listar_Noticias(int Opcion) {
        Noticias objNoticias = new Noticias();
        Logica.Noticias objNoticiasL = new Logica.Noticias();
        final Intent i = new Intent("ListaNoticias");
        JSONObject jsonObjectListarNoticia = null;
        if(Opcion == 1) {
            Log.e("Lista ", "Opcion 1");
            this.noticiaLista = objNoticiasL.Listar_Noticias(objNoticias);
            if(noticiaLista.equals(null)){
                result = "2";
            }else {
                i.putExtra("JsonNoticias", LISTA_ACTUALIZADA);
                sendBroadcast(i);
            }
        }else if(Opcion == 2){
            Log.e("Lista ", "Opcion 2");
            this.noticiaLista = objNoticiasL.Listar_Noticias(objNoticias);
            if(noticiaLista.equals(null)){
                result = "2";
            }else {
                i.putExtra("JsonNoticias", LISTA_ACTUALIZADA);
                sendBroadcast(i);
            }
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    Noticias objNoticias = new Noticias();
                    Logica.Noticias objNoticiasL = new Logica.Noticias();
                    noticiaLista = objNoticiasL.Listar_Noticias(objNoticias);
                    if(noticiaLista.equals(null)){
                        result = "2";
                    }else {
                        i.putExtra("JsonNoticias", LISTA_ACTUALIZADA);
                        sendBroadcast(i);
                    }
                }
            },tiempoActualizacion,tiempoActualizacion);

            }

        return result;
    }

    @Override
    public boolean Enviar_Mensaje(String strUsuario, int intId_Usuario, String strMensaje, int intPuerto, String strIP) {
        //String msg = objUsuario.getNombre_Usuario() +"="+ URLEncoder.encode(objsesion.getPreferences(.this, "user"));
        //String idUsuario =  (""+intId_Usuario);
        String msg = "Nombre_Usuario" +"=" + URLEncoder.encode(strUsuario) +
                "&" + "Id_Usuario" +"="+ URLEncoder.encode(""+intId_Usuario).toString() +
                "&" + "Mensaje" + "="+ URLEncoder.encode(strMensaje);

        return operadorSocket.Enviar_Mensaje(msg, intPuerto, strIP);
    }

    @Override
    public boolean Actualizar_IPuerto(int intId_Usuario) {
        JSONObject jsonObjectActualizar = null;
       // String url = "https://retame.000webhostapp.com/retame/index_usuarios.php?opcion=Actualizar_IP&Id_Usuario="+intId_Usuario+"&Puerto="+operadorSocket.getPuertoListening();
        //String url = "http://192.168.43.137/Retame/index_usuarios.php?opcion=Actualizar_IP&Id_Usuario="+intId_Usuario+"&Puerto="+operadorSocket.getPuertoListening();
        String url = urlServer+"index_usuarios.php?opcion=Actualizar_IP&Id_Usuario="+intId_Usuario+"&Puerto="+operadorSocket.getPuertoListening();

        jsonObjectActualizar = operadorSocket.HttpRequest(url);
        if(jsonObjectActualizar == null){
            Log.e("Error ", "Actualizar IPuerto");
            return false;
        }else {
            return true;
        }
    }

    @Override
    public boolean Registrar_Gustar(int intId_Noticia) {
//        String url = "http://192.168.43.137/Retame/index_gustar.php?opcion=Registrar_Gustar&Id_Usuario="+Integer.parseInt(objSesion.getPreferences(getApplicationContext(), "Id"))+"&Id_Noticia="+intId_Noticia;
       // String url = urlServer+"index_gustar.php?opcion=Registrar_Gustar&Id_Usuario="+Integer.parseInt(objUsuario.Obtener_VariableSesion(getApplicationContext(), "Id"))+"&Id_Noticia="+intId_Noticia;

        JSONObject jsonObjectActualizar = operadorSocket.HttpRequest(null);
        if(jsonObjectActualizar == null){
            Log.e("Error ", "Actualizar IPuerto");
            return false;
        }else {
            return true;
        }
    }

    public JSONObject Cargar_Informacion_Perfil(int intIdUsuario){
       // String url = "http://localhost/retame/index_usuarios.php?opcion=Cargar_Informacion&Id_Usuario="+intIdUsuario;
        String url = urlServer+"index_usuarios.php?opcion=Cargar_Informacion&Id_Usuario="+intIdUsuario;

        JSONObject jsonObjectCargar  = operadorSocket.HttpRequest(url);
        if(jsonObjectCargar != null){
            return jsonObjectCargar;
        }else {
            return null;
        }
    }

    @Override
    public Boolean Registrar_Publicacion( String strdata, String strNombrePublicacion, String strDescripcion, String strUrl) {
        Boolean boolReg = false;
     //   String url =  "http://192.168.43.137/retame/index_noticias.php?opcion=Registrar_Noticia&Id_Usuario="+objUsuario.Obtener_VariableSesion(getApplicationContext(), "Id")+"&Nombre_Noticia="+strNombrePublicacion+"&Descripcion="+strDescripcion+"&url="+strUrl;
        String strResult = operadorSocket.Subir_Imagen(null, strdata);
        if (strResult !=null){
            boolReg = true;
        }else {
            boolReg = false;
        }

        Log.e("Este es", "el result del registro"+strResult);
        return boolReg;
    }

    @Override
    public String Listar_Temas() {
        Logica.Temas objTemas = new Logica.Temas();
        Datos.Temas objTemasD = new Temas();
        Intent i = new Intent("ListaTemas");

        JSONObject jsonObjectListarNoticia = null;
        temasLista = objTemas.Listar_Temas(0);
        if(temasLista.equals(null)){
            result = "2";
        }else {
            i.putExtra("JsonTemas", LISTA_ACTUALIZADA);
            sendBroadcast(i);
        }
        return result;
    }

    @Override
    public String Listar_Ejemplos() {
        Logica.Noticias objEjemplos = new Logica.Noticias();
        Datos.Noticias objEjemplosD = new Datos.Noticias();
        Intent i = new Intent("ListaEjemplos");
        ejemplosLista = objEjemplos.Listar_Ejemplos(objEjemplosD);
        if(ejemplosLista.equals(null)){
            result = "2";
        }else {
            i.putExtra("JsonEjemplos", LISTA_ACTUALIZADA);
            sendBroadcast(i);
        }
        return result;
    }

       @Override
    public String Buscar_Noticias(String strBusqueda) {
           Logica.Noticias objNoticiaBuscar = new Logica.Noticias();
           Datos.Noticias objEjemplosD = new Datos.Noticias();
           Intent i = new Intent("Buscar_Noticia");
           noticiasBuscarList = objNoticiaBuscar.Buscar_Noticias(strBusqueda);
           if(noticiasBuscarList.equals(null)){
               result = "2";
           }else {
               i.putExtra("JsonBusqueda", LISTA_ACTUALIZADA);
               sendBroadcast(i);
           }
           return result;
    }

    @Override
    public String Cargar_Archivo(String strURL) {
        String strDato = operadorSocket.Abrir_Archivo(strURL);
        if(strDato.equals("") || strDato.equals(null)) {
            return null;
        }else {
            return strDato;
        }
    }
}

/*
    @Override
    public String Listar_Noticias(int Opcion) {
        String result = "";
        final String url_noticias = urlServer+"index_noticias.php?opcion=Listar_Noticias";
        final Intent i = new Intent("ListaNoticias");
        JSONObject jsonObjectListarNoticia = null;
        if(Opcion == 1) {
            Log.e("Lista ", "Opcion 1");
            jsonObjectListarNoticia = operadorSocket.HttpRequest(url_noticias);
            if (jsonObjectListarNoticia != null) {
                this.setStrJsonInformacion(jsonObjectListarNoticia.toString());
                i.putExtra("JsonNoticias", jsonObjectListarNoticia.toString());
                sendBroadcast(i);
            }
        }else if(Opcion == 2){
            Log.e("Lista ", "Opcion 2");
            jsonObjectListarNoticia = operadorSocket.HttpRequest(url_noticias);
            if (jsonObjectListarNoticia != null) {
                this.setStrJsonInformacion(jsonObjectListarNoticia.toString());
                i.putExtra("JsonNoticias", jsonObjectListarNoticia.toString());
                sendBroadcast(i);
            }

            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    JSONObject jsonObjectListarNoticia = operadorSocket.HttpRequest(url_noticias);
                    if (jsonObjectListarNoticia != null) {
                        setStrJsonInformacion(jsonObjectListarNoticia.toString());
                        i.putExtra("JsonNoticias", jsonObjectListarNoticia.toString());
                        sendBroadcast(i);
                    }
                }
            },tiempoActualizacion,tiempoActualizacion);

        }

        return result;
    }
*/
