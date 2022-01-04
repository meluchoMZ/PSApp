package es.udc.psi.agendaly.Calendar;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.ArrayList;

import es.udc.psi.agendaly.R;


public class CalendarReceiver extends BroadcastReceiver{
    String action = "send.events";

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onReceive(@Nullable Context context, @Nullable Intent intent) {
        //Log.d("_TAG_R", "HO");
        assert intent != null;
        Bundle bundle = new Bundle();
        createNotificationChannel(context);
        int notificationId = 0;

        ArrayList<String> eventsList;
        if (intent.getAction().equals(action)) {
            Log.d("_TAG_R", "HEY");
            bundle = intent.getExtras();
            if (bundle != null) {
                eventsList = bundle.getStringArrayList("list_event");
                Log.d("_TAG_R", "HEY2");
                if (eventsList != null) {
                    int i = 0;
                    for (String a : eventsList) {
                        String[] parts = a.split("/");
                        String p0 = parts[0]; // asignatura
                        String p1 = parts[1]; // horario y aula

                        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,
                                "CHANNEL_ID")
                                .setSmallIcon(R.drawable.baseline_event_white_20)
                                .setContentTitle(p0)
                                .setContentText(p1)
                                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                                .setAutoCancel(true);
                        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
                        notificationManager.notify(++notificationId, builder.build());

                    }

                }
            }
        }

    }

    private void createNotificationChannel(Context ctx) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "CHANNEL_NAME";
            String description = "DESCRIPTION";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("CHANNEL_ID",
                    name, importance);
            channel.setDescription(description);
            NotificationManager notifManager = ctx.getSystemService(NotificationManager.class);
            notifManager.createNotificationChannel(channel);
        }
    }
}