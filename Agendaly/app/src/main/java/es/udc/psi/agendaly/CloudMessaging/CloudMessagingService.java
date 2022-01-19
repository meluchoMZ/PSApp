package es.udc.psi.agendaly.CloudMessaging;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.service.autofill.FieldClassification;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Arrays;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import es.udc.psi.agendaly.GlobalApplication;
import es.udc.psi.agendaly.R;
import es.udc.psi.agendaly.Teams.TeamsActivity;
import es.udc.psi.agendaly.TimeTable.Horario;

public class CloudMessagingService extends FirebaseMessagingService {
	private Map<String, String> data;
	private static int pendingIntentId = 0;
	@RequiresApi(api = Build.VERSION_CODES.N)
	@Override
	public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
		super.onMessageReceived(remoteMessage);
		data = remoteMessage.getData();

		NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext())
				.setSmallIcon(R.drawable.ic_add)
				.setContentTitle(data.get("title"))
				.setContentText(data.get("message"));
		NotificationManager manager = (NotificationManager) getSystemService(
				Context.NOTIFICATION_SERVICE);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			String channelId = "es.udc.psi.Agendaly.CloudMessagingChannel";
			NotificationChannel channel = new NotificationChannel(channelId,
					"Agendaly notification channel", NotificationManager.IMPORTANCE_HIGH);
			manager.createNotificationChannel(channel);
			builder.setChannelId(channelId);
		}
		Intent intent = new Intent(this, TeamsActivity.class);
		// searches for pattern team#creator
		Pattern pattern = Pattern.compile("\\w+#\\w+@\\w+.\\w+");
		Matcher matcher = pattern.matcher(data.get("message"));
		matcher.find();
		String team = matcher.group();
		intent.putExtra("team", team);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
		pendingIntentId++;
		PendingIntent pendingIntent = PendingIntent.getActivity(this,
				pendingIntentId, intent, PendingIntent.FLAG_UPDATE_CURRENT) ;
		builder.setContentIntent(pendingIntent);
		builder.setAutoCancel(true);
		manager.notify(0, builder.build());
	}

	@Override
	public void onNewToken(@NonNull String s) {
		super.onNewToken(s);
	}
}
