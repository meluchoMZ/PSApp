package es.udc.psi.agendaly.CloudMessaging;

public class CloudMessagingData {
	private String title;
	private String message;

	public CloudMessagingData(String title, String message) {
		this.title = title;
		this.message = message;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
