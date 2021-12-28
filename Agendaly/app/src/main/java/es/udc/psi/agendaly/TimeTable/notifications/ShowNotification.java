/*
package es.udc.psi.agendaly.TimeTable.notifications;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import es.udc.psi.agendaly.R;
import es.udc.psi.agendaly.TimeTable.Horario;

public class ShowNotification extends Service {
    private final static String TAG = "ShowNotification";

    @Override
    public void onCreate() {
        super.onCreate();
        Intent mainIntent = new Intent(this,
                Horario.class);
        NotificationManager notificationManager =
                (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification noti = new NotificationCompat.Builder(this)
                .setAutoCancel(true)
                .setContentIntent(PendingIntent.getActivity(this, 0,
                        mainIntent, PendingIntent.FLAG_UPDATE_CURRENT))
                .setContentTitle("HELLO " + System.currentTimeMillis())
                .setContentText("PLEASE CHECK WE HAVE UPDATED NEWS")
                .setDefaults(Notification.DEFAULT_ALL).setSmallIcon(R.drawable.ic_launcher_foreground)
                .setTicker("ticker message").setWhen(System.currentTimeMillis())
                .build();
        notificationManager.notify(0, noti);
        Log.i(TAG, "Notification created");
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub return null;
        return null;
    }
}
*/
