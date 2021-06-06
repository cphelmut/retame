package service;

import android.content.ComponentName;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.content.ServiceConnection;

import interfaces.IAppManager;

/**
 * Created by USUARIO on 09/08/2017.
 */

public class Serviceconnection {
    private IAppManager imService;

    public ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            setImService(((IMService.IMBinder) iBinder).getService());
            Log.e("Hola Jhon", "Estamos en on connection");
            Listar_Noticias(2);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            setImService(null);
        }
    };

    public IAppManager getImService() {
        return imService;
    }

    public void setImService(IAppManager imService) {
        this.imService = imService;
    }

    public void Listar_Noticias(final int intOpcion){
        Thread threadNoticias = new Thread(){
            Handler handerNoticias = new Handler();
            @Override
            public void run() {
                String strresult = imService.Listar_Noticias(intOpcion);
            }
        };
        threadNoticias.start();
    }
}
