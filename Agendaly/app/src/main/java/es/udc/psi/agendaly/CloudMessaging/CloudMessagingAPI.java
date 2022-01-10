package es.udc.psi.agendaly.CloudMessaging;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface CloudMessagingAPI {
	public static final String BASE_URL = "https://fcm.googleapis.com";
	public static final String ENDPOINT = "fcm/send";
	@Headers(
			{
					"Content-Type:application/json",
					"Authorization:key=AAAAzRfng7I:APA91bFEiaJO8m_eHIOzQNtTfCt1wF9Zk8Tl8LvKtdS6yq8gioT_YD5FyUkV-uMHsLOzyJ_2tErZP-8WQmRUkG3dmodmMiQ33b_Lb8q9K4lRY3ZvYj8NmTIVWuQQ0L3sBj2xvzUBtKdF"
			}
	)

	@POST(ENDPOINT)
	Call<CloudMessagingResponse> sendNotification(@Body CloudMessagingSender body);
}
