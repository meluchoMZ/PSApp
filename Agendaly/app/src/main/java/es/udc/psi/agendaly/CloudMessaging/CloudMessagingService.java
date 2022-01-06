package es.udc.psi.agendaly.CloudMessaging;

import android.app.NotificationManager;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

public class CloudMessagingService extends FirebaseMessagingService {
	private Map<String, String> data;
	@Override
	public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
		super.onMessageReceived(remoteMessage);
		data = remoteMessage.getData();

		NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext())
				.setContentTitle(data.get("title"))
				.setContentText(data.get("message"));
		NotificationManager manager = (NotificationManager) getSystemService(
				Context.NOTIFICATION_SERVICE);
		manager.notify(0, builder.build());
	}

	@Override
	public void onNewToken(@NonNull String s) {
		super.onNewToken(s);
	}
}
