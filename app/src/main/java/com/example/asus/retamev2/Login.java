package com.example.asus.retamev2;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import Datos.Usuarios;
import interfaces.IAppManager;
import service.IMService;

public class Login extends AppCompatActivity {
    protected static final int NOT_CONNECTED_TO_SERVICE = 0;
    protected static final int FILL_BOTH_USERNAME_AND_PASSWORD = 1;
    public static final String AUTHENTICATION_FAILED = "0";
    public static final String FRIEND_LIST = "FRIEND_LIST";
    protected static final int MAKE_SURE_USERNAME_AND_PASSWORD_CORRECT = 2 ;
    protected static final int NOT_CONNECTED_TO_NETWORK = 3;
    private IAppManager imService;
    Usuarios objUsuario = new Usuarios();
    Button btnLogin;
    EditText txtUsuario, txtPassword;
    JSONObject jsonObjectLogin = null;



    private ServiceConnection mConnection = new ServiceConnection(){
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            imService = ((IMService.IMBinder)iBinder).getService();
            if(imService.UsuarioConectado() == true){
                Intent intent = new Intent(Login.this, menuInicio2.class);
                startActivity(intent);
                Login.this.finish();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            imService = null;
            Toast.makeText(Login.this, "Servicio Detenido",
                    Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
       // startService(new Intent(Login.this, IMService.class));
        btnLogin = (Button)findViewById(R.id.btn_login);
        txtUsuario = (EditText)findViewById(R.id.txt_usuario);
        txtPassword = (EditText)findViewById(R.id.txt_password);

        String id =  objUsuario.Obtener_VariableSesion(Login.this, "Id");
        if(id != ""){
            Intent intent = new Intent(Login.this, menuInicio2.class);
            startActivity(intent);
        }
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(imService == null){
                    Toast.makeText(Login.this, "No conectado al servicio",
                            Toast.LENGTH_SHORT).show();
                }
                else if(imService.Conectado_Network() == false){
                    Toast.makeText(Login.this, "No conectado al network",
                            Toast.LENGTH_SHORT).show();
                }else
                if(txtPassword.length() > 0 && txtUsuario.length() > 0){
                    Thread threadLogin = new Thread(){
                        Handler handerLogin = new Handler();
                        @Override
                        public void run() {

                            jsonObjectLogin = imService.Login(txtUsuario.getText().toString(), txtPassword.getText().toString());
                            if(jsonObjectLogin != null){
                                handerLogin.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            JSONArray jsonArrayUsuario = jsonObjectLogin.getJSONArray("itemLog");
                                            for (int i = 0; i < jsonArrayUsuario.length(); i++){
                                                JSONObject jsonObjectFeed = (JSONObject) jsonArrayUsuario.get(i);
                                                objUsuario.Almacenar_VariableSesion(getApplicationContext(), jsonObjectFeed.getString("Id_Usuario"), jsonObjectFeed.getString("Nombre_Usuario"), jsonObjectFeed.getString("Rol"), jsonObjectFeed.getString("Imagen_Usuario"));
                                            }
                                        }catch(JSONException e){
                                            e.printStackTrace();
                                        }

                                        Intent intent = new Intent(Login.this, menuInicio2.class);
                                        startActivity(intent);
                                        Login.this.finish();

                                    }
                                });
                            }else {
                                Log.e("Hola Jhon ", "Problemas con el Login");
                            }
                        }
                    };
                    threadLogin.start();
                }
            }
        });
    }

    @Override
    protected void onPause() {
        unbindService(mConnection);
        super.onPause();
    }

    @Override
    protected void onResume() {
        bindService(new Intent(Login.this, IMService.class), mConnection, Context.BIND_AUTO_CREATE);
        super.onResume();
    }


}
