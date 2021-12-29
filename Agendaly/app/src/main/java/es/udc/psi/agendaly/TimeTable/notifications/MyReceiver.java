package es.udc.psi.agendaly.TimeTable.notifications;

import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import es.udc.psi.agendaly.MainActivity;
import es.udc.psi.agendaly.R;
import es.udc.psi.agendaly.TimeTable.Horario;
import es.udc.psi.agendaly.TimeTable.InfoFragment;
import es.udc.psi.agendaly.TimeTable.viewmodel.AsignaturaViewModel;

public class MyReceiver extends BroadcastReceiver {
    String todaySchedule = "todaySchedule";
    Context context;
    private String CHANNEL_ID = "CHANNEL_ID";
    private String GROUP_NAME = "TimeTable";

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onReceive(@Nullable Context context, @Nullable Intent intent) {

        assert intent != null;
        this.context=context;
        Bundle bundle = new Bundle();
        ArrayList<String> list;
        if (intent.getAction().equals(todaySchedule)) {
            bundle=intent.getExtras();
            if(bundle != null) {
                list = bundle.getStringArrayList("asignatura");
                if(list != null) {
                    int i=0;
                    for (String a : list) {
                        int m = list.size()+ i++;
                        String[] parts = a.split("/");
                        String part1 = parts[0]; // asignatura
                        String part2 = parts[1]; // horario y aula
                        Notificar(part1,part2,m,context);
                    }

                }
                }
            }

    }
    // Método que crea y envía la notificación
    public void Notificar(String titulo, String mensaje, int notID, Context context){
        NotificationCompat.Builder creador;
        String canalID = "MiCanal01";
        Context contexto = context;
        NotificationManager notificador = (NotificationManager) contexto.getSystemService(contexto.NOTIFICATION_SERVICE);
        creador = new NotificationCompat.Builder(contexto, canalID);
        Intent intent = new Intent(context, Horario.class);
        PendingIntent pi = PendingIntent.getActivity(context, 0, intent, 0);
        // Si nuestro dispositivo tiene Android 8 (API 26, Oreo) o superior
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            String canalNombre = "Mensajes";
            String canalDescribe = "Canal de mensajes";
            int importancia = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel miCanal = new NotificationChannel(canalID, canalNombre, importancia);
            miCanal.setDescription(canalDescribe);
            miCanal.enableLights(true);
            miCanal.setLightColor(Color.BLUE); // Esto no lo soportan todos los dispositivos
            miCanal.enableVibration(true);
            notificador.createNotificationChannel(miCanal);
            creador = new NotificationCompat.Builder(contexto, canalID);
        }
        Bitmap iconoNotifica = BitmapFactory.decodeResource(contexto.getResources(), R.drawable.baseline_event_white_20);
        int iconoSmall = R.drawable.baseline_event_white_20;
        creador.setSmallIcon(iconoSmall);
        creador.setLargeIcon(iconoNotifica);
        creador.setGroup("GROUP_ID_STRING").setAutoCancel(true);
        creador.setContentTitle(titulo);
        creador.setContentText(mensaje);
        creador.setContentIntent(pi);
        creador.setStyle(new NotificationCompat.BigTextStyle().bigText(mensaje));
        creador.setChannelId(canalID);
        notificador.notify("notification",notID, creador.build());
    }

}