package es.udc.psi.agendaly.CloudMessaging;

public class CloudMessagingSender {
	private CloudMessagingData data;
	private String to;

	public CloudMessagingSender(CloudMessagingData data, String to) {
		this.data = data;
		this.to = to;
	}

	public CloudMessagingData getData() {
		return this.data;
	}

	public String getAddressee() {
		return this.to;
	}
}
