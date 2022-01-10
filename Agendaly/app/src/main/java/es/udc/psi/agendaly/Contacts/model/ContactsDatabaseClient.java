package es.udc.psi.agendaly.Contacts.model;

import android.content.Context;

import androidx.room.Room;

public class ContactsDatabaseClient {
	private Context context;
	private static ContactsDatabaseClient instance;
	private ContactsDatabase database;

	private ContactsDatabaseClient(Context context) {
		this.context = context;
		this.database = Room.databaseBuilder(context,
				ContactsDatabase.class,
				"ContactsDatabase.db").build();
	}

	public static synchronized ContactsDatabaseClient getInstance(Context context) {
		if (instance == null) {
			instance = new ContactsDatabaseClient(context);
		}
		return instance;
	}

	public ContactsDatabase getContactsDatabase() {
		return this.database;
	}
}
