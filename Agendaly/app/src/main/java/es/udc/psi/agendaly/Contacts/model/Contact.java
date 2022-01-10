package es.udc.psi.agendaly.Contacts.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "contacts")
public class Contact {
	@PrimaryKey()
	@NonNull
	@ColumnInfo(name = "email")
	private String email;
	@ColumnInfo(name = "token")
	private String token;

	public Contact(@NonNull String email) {
		this.email = email;
	}

	public String getEmail() {
		return this.email;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
