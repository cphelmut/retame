package Conexion;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Base64;

/**
 * Created by USUARIO on 10/09/2017.
 */

public class Conexion {
    public JSONObject HttpRequest(String Stringurl){
        String json = "";
        JSONObject jObj = null;
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

    public String HttpRequestString(String Stringurl){
        String strReturn = "";
        JSONObject jObj = null;
        HttpURLConnection urlConnection = null;
        String jsonString = Stringurl.replace(" ", "%20");
        StringBuilder sb = null;
        try {
            URL url = new URL(jsonString);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setDoOutput(true);
            urlConnection.connect();
            BufferedReader br=new BufferedReader(new InputStreamReader(url.openStream()));
            char[] buffer = new char[1024];
            sb = new StringBuilder();

            String line ="";
            while ((line = br.readLine()) != null && line != "  ") {
                sb.append(line);
            }
            br.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        if(sb.toString().equals("0") || sb.toString().equals("1")){
            return sb.toString();
        }else{
            return sb.deleteCharAt(0).toString();
        }
    }


}
