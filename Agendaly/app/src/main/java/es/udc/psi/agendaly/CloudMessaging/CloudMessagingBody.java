package es.udc.psi.agendaly.CloudMessaging;

public class CloudMessagingBody {
	private String data;
	private String addressee;

	public CloudMessagingBody(String data, String addressee) {
		this.data = data;
		this.addressee = addressee;
	}

	public String getData() {
		return this.data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getAddressee() {
		return this.addressee;
	}

	public void setAddressee(String addressee) {
		this.addressee = addressee;
	}
}
