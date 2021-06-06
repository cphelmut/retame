package adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import Logica.Reportes;

/**
 * Created by ASUS on 2/02/2018.
 */

public class DialogoSeleccion extends DialogFragment {
        private int Id_Usuario;
        private int Id_Noticia;
        private Reportes objReportes;


        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            Id_Usuario = getArguments().getInt("Id_Usuario");
            Id_Noticia = getArguments().getInt("Id_Noticia");

            final String[] items = {"Errores en la informacion", "No pertenece a la materia indicada", "El texto es ambiguo"};

            AlertDialog.Builder builder =
                    new AlertDialog.Builder(getActivity());

            builder.setTitle("Selección")
                    .setItems(items, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int item) {
                            Log.i("Dialogos", "Opción elegida: " + items[item]+ " "+item);
                            Registrar_Reporte(item);
                        }
                    });
            return builder.create();
        }


        public void Registrar_Reporte(final int IdTipoReporte){
            Thread threadRegistrarReporte = new Thread(){
                Handler handlerRegistrarNoticia = new Handler();
                @Override
                public void run() {
                    objReportes = new Reportes();
                    final int result = objReportes.Registrar_Reportes(IdTipoReporte, Id_Usuario, Id_Noticia);
                    handlerRegistrarNoticia.post(new Runnable() {
                        @Override
                        public void run() {
                            if(result == 0){
                                Log.e("Hola Jhon ", "Se registro el reporte");
                            }else {
                                Log.e("Hola Jhon ", "Tenemos problemas");
                            }
                        }
                    });
                }
            };threadRegistrarReporte.start();
        }


    }
