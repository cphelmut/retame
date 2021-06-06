package com.example.asus.retamev2;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import Datos.Usuarios;
import interfaces.IAppManager;
import service.IMService;

public class MainActivity extends AppCompatActivity {
    protected static final int NOT_CONNECTED_TO_SERVICE = 0;
    protected static final int FILL_BOTH_USERNAME_AND_PASSWORD = 1;
    public static final String AUTHENTICATION_FAILED = "0";
    public static final String FRIEND_LIST = "FRIEND_LIST";
    protected static final int MAKE_SURE_USERNAME_AND_PASSWORD_CORRECT = 2 ;
    protected static final int NOT_CONNECTED_TO_NETWORK = 3;
    private IAppManager imService;
    Button Btn_Login;
    Context context;
    boolean isConnected;
    private final int SPLASH_DISPLAY_LENGTH = 2000;
    Usuarios objUsuario = new Usuarios();


    private ServiceConnection mConnection = new ServiceConnection(){
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            imService = ((IMService.IMBinder)iBinder).getService();

        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            imService = null;
            Toast.makeText(MainActivity.this, "Servicio Detenido",
                    Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startService(new Intent(MainActivity.this, IMService.class));
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Verificar_Sesion();
            }

        }, SPLASH_DISPLAY_LENGTH);
    }

    @Override
    protected void onPause() {
        unbindService(mConnection);
        super.onPause();
    }

    @Override
    protected void onResume() {
        bindService(new Intent(MainActivity.this, IMService.class), mConnection, Context.BIND_AUTO_CREATE);
        super.onResume();
    }

    private void Verificar_Sesion(){
        final Thread threadLogin = new Thread() {
            Handler handerLogin = new Handler();
            @Override
            public void run() {
                if (objUsuario.Obtener_VariableSesion(getApplicationContext(), "Id") != "") {
                    Intent mainIntent = new Intent(MainActivity.this, menuInicio2.class);
                    MainActivity.this.startActivity(mainIntent);
                    MainActivity.this.finish();
                }
                else{
                    Intent intentLogin = new Intent(MainActivity.this, Login.class);
                    startActivity(intentLogin);
                    MainActivity.this.finish();
                }
            }
        };
        threadLogin.start();
    }
}
