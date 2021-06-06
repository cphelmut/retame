package operadorsocket;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;


import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.Future;
import interfaces.IAppManager;

/**
 * Created by USUARIO on 29/07/2016.
 */
public class OperadorSocket {
    private int puertoListening = 0;
    private static final String SOLICITUD_HTTP_FALLIDA =null;
    private HashMap<InetAddress, Socket> socketHashMap = new HashMap<InetAddress, Socket>();
    private ServerSocket serverSocket = null;
    private boolean listening;
    private IAppManager appManager;
    static InputStream is = null;
    static JSONObject jObj = null;
    static String json = "";

    private class Recibir_Conexion extends Thread{
        Socket socketCliente = null;
        public Recibir_Conexion(Socket socket){
            this.socketCliente = socket;
            OperadorSocket .this.socketHashMap.put(socket.getInetAddress(), socket);
        }

        @Override
        public void run() {

            try {
                BufferedReader lect = new BufferedReader(new InputStreamReader(socketCliente.getInputStream()));
                String lineaInputStream;
                while ((lineaInputStream = lect.readLine())!= null){
                    if(lineaInputStream.equals("exit") == false){
                        appManager.mensajeRecibido(lineaInputStream);
                    }else {
                        socketCliente.shutdownInput();
                        socketCliente.shutdownOutput();
                        socketCliente.close();
                        OperadorSocket.this.socketHashMap.remove(socketCliente.getInetAddress());
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                Log.e("Con Recibida.Corriendo", "Cuando se recibe la conexion");
            }
        }
    }

    public OperadorSocket(IAppManager appManager){
        this.appManager = appManager;
    }

    public JSONObject HttpRequest(String Stringurl){
        HttpURLConnection urlConnection = null;
        String jsonString = new String();
        StringBuilder sb = null;
        try {
            URL url = new URL(Stringurl);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setDoOutput(true);
            urlConnection.connect();
            BufferedReader br=new BufferedReader(new InputStreamReader(url.openStream()));
            char[] buffer = new char[1024];
             sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line+"\n");
            }
            br.close();


        } catch (IOException e) {
            e.printStackTrace();
        }
        if (sb != null) {
            jsonString = sb.toString();
            try {
                jObj = new JSONObject(jsonString);
            } catch (JSONException e) {
                Log.e("JSON Parser", "Error parsing data " + e.toString());
            }

            // return JSON String
        }else {
            jObj = null;
        }

        return jObj;

    }

    public String Abrir_Archivo(String strUrl){
        HttpURLConnection urlConnection = null;
        String jsonString = new String();
        StringBuilder sb = null;
        try {
            URL url = new URL(strUrl);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setDoOutput(true);
            urlConnection.connect();
            BufferedReader br=new BufferedReader(new InputStreamReader(url.openStream()));
            char[] buffer = new char[1024];
            sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line+"\n");
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }


    public String Subir_Imagen(String strurl, String data){
        String Content;
        String Error = null;
      //  String data = "";
         BufferedReader reader;
        StringBuilder sb = null;

        HttpURLConnection connection = null;
        try {
            URL url = new URL(strurl);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            con.setRequestMethod("POST");
            con.setUseCaches(false);
            con.setDoInput(true);
            con.setDoOutput(true);
            con.setRequestProperty("Content-Length", "" + data.getBytes().length);
            con.setRequestProperty("Connection", "Keep-Alive");
            con.setDoOutput(true);

            OutputStream os = con.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));

            //make request
            writer.write(data);
            writer.flush();
            writer.close();
            reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }

            Content = sb.toString();
        } catch (Exception ex) {
            Error = ex.getMessage();
        }
        return sb.toString();
    }

    public boolean Enviar_Mensaje(String mensaje, int puerto, String IP){
        try {
            String[] str =  IP.split("\\.");
            byte[] ip = new byte[str.length];
            for (int i=0; i<str.length; i++){
                ip[i] = (byte) Integer.parseInt(str[i]);
            }
            Socket socket = getSocket(InetAddress.getByAddress(ip),puerto);
            if(socket == null){
                return false;
            }
            PrintWriter out = null;
            out = new PrintWriter(socket.getOutputStream(),true);
            out.println(mensaje);
        }catch (UnknownHostException e) {
            return false;
            //e.printStackTrace();
        } catch (IOException e) {
            return false;
            //e.printStackTrace();
        }
        return true;
    }

    public int iniciarListenting(int puerto){
        listening = true;
        try {
            serverSocket = new ServerSocket(puerto);
            this.puertoListening = puerto;
        }catch (IOException e){
            this.puertoListening = 0;
            return 0;
        }
        while (listening){
            try {
                new Recibir_Conexion(serverSocket.accept()).start();
            }catch (IOException e){
                return 2;
            }
        }
        try {
            serverSocket.close();

        }catch (IOException e){
            Log.e("Exception server socket", "Exception cuando cierre serversocket");
            return 3;
        }
        return 1;
    }

    private Socket getSocket(InetAddress address, int puerto){
        Socket socket=null;
        if(socketHashMap.containsKey(address) == true){
            socket = socketHashMap.get(address);
            if(socket.isConnected() == false || socket.isInputShutdown() == true || socket.isOutputShutdown() == true ||socket.getPort() != puerto){
                socketHashMap.remove(address);
                try {
                    socket.shutdownInput();
                    socket.shutdownOutput();
                    socket.close();
                    socket = new Socket(address, puerto);
                    socketHashMap.put(address,socket);
                }catch (IOException e){
                    Log.e("Obtenemos el puerto", "Cuando se cierre");
                }
            }
        }else {
            try {
                socket = new Socket(address, puerto);
                socketHashMap.put(address, socket);
            }catch (IOException e){
                Log.e("Obtener Puerto", "cuando se cree");
            }
        }
        return socket;
    }

    public void Detener_Listening(){
        this.listening = false;
    }

    public void exit(){
        for(Iterator<Socket> iterator = socketHashMap.values().iterator(); iterator.hasNext();){
            Socket socket = (Socket)iterator.next();
            try{
                socket.shutdownInput();
                socket.shutdownOutput();
                socket.close();
            }catch (IOException e){
            }
        }
        socketHashMap.clear();
        this.Detener_Listening();
        appManager = null;
    }

    public int getPuertoListening(){
        return this.puertoListening;
    }



        public void main(String url) throws IOException {
            CloseableHttpClient httpclient = HttpClients.createDefault();
            try {
                HttpGet httpget = new HttpGet(url);

                System.out.println("Executing request " + httpget.getRequestLine());

                // Create a custom response handler
                ResponseHandler<String> responseHandler = new ResponseHandler<String>() {

                    @Override
                    public String handleResponse(
                            final HttpResponse response) throws ClientProtocolException, IOException {
                        int status = response.getStatusLine().getStatusCode();
                        if (status >= 200 && status < 300) {
                            HttpEntity entity = response.getEntity();
                            return entity != null ? EntityUtils.toString(entity) : null;
                        } else {
                            throw new ClientProtocolException("Unexpected response status: " + status);
                        }
                    }

                };
                String responseBody = httpclient.execute(httpget, responseHandler);
                System.out.println("----------------------------------------");
                System.out.println(responseBody);
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                httpclient.close();
            }
        }

}
