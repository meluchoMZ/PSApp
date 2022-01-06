package es.udc.psi.agendaly.CloudMessaging;

public class CloudMessagingData {
	private String title;
	private String message;
	private String from;

	public CloudMessagingData(String title, String message, String from) {
		this.title = title;
		this.message = message;
		this.from = from;
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

	public String getFrom() {
		return this.from;
	}

	public void setFrom(String from) {
		this.from = from;
	}
}
