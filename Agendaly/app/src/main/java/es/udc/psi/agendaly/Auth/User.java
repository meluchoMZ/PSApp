package es.udc.psi.agendaly.Auth;

public class User {
	private String email;
	private String password;
	private String provider;

	public User(String email, String password, String provider) {
		this.email = email;
		this.password = password;
		this.provider = provider;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getProvider() {
		return this.provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}
}
