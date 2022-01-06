package es.udc.psi.agendaly.Contacts.presenter;


import android.content.AbstractThreadedSyncAdapter;
import android.content.AsyncQueryHandler;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;

import androidx.core.graphics.drawable.IconCompat;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.LinkedList;
import java.util.List;

import es.udc.psi.agendaly.CloudMessaging.CloudMessagingData;
import es.udc.psi.agendaly.Contacts.model.Contact;
import es.udc.psi.agendaly.Contacts.model.ContactsDatabaseClient;

public class ContactsDatabaseImpl implements ContactsPresenter {
	private Context context;
	private ContactsView view;
	private AddToTeamView attView;

	public ContactsDatabaseImpl(ContactsView view, Context context) {
		this.view = view;
		this.context = context;
	}

	public ContactsDatabaseImpl(AddToTeamView view, Context context) {
		this.attView = view;
		this.context =context;
	}

	@Override
	public void getContacts() {
		class GetContacts extends AsyncTask<Void, Void, List<Contact>> {
			@Override
			protected List<Contact> doInBackground(Void... voids) {
				List<Contact> queryResult =
				ContactsDatabaseClient.getInstance(context)
						.getContactsDatabase()
						.getDao()
						.getAll();
				return queryResult;
			}

			@Override
			protected void onPostExecute(List<Contact> contacts) {
				super.onPostExecute(contacts);
				view.showContacts(contacts);
			}
		}
		GetContacts gc = new GetContacts();
		gc.execute();
	}

	@Override
	public void addContact(Contact contact) {
		class AddContact extends AsyncTask<Void, Void, Void> {
			List<Contact> contactList;
			@Override
			protected Void doInBackground(Void... voids) {
				class BGDBCall extends AsyncTask<String, Void, Void> {

					@Override
					protected Void doInBackground(String... strings) {
						contact.setToken(strings[0]);
						ContactsDatabaseClient.getInstance(context)
								.getContactsDatabase()
								.getDao()
								.insert(contact);
						contactList = ContactsDatabaseClient.getInstance(context)
								.getContactsDatabase().getDao().getAll();
						return null;
					}

					@Override
					protected void onPostExecute(Void unused) {
						super.onPostExecute(unused);
						view.showContacts(contactList);
					}
				}
				if (contact == null) return null;
				FirebaseFirestore db = FirebaseFirestore.getInstance();
				db.collection("userTokens")
						.document(contact.getEmail()).get()
						.addOnCompleteListener(task -> {
							if (task.isSuccessful()) {
								String token = (String) task.getResult().get("token");
								if (token != null) {
									BGDBCall call = new BGDBCall();
									call.execute(token);
								} else {
									view.showError("User '"+contact.getEmail()+"' does not exist");
								}
							} else {
								return;
							}
						});
				return null;
			}

			@Override
			protected void onPostExecute(Void unused) {
				super.onPostExecute(unused);
			}
		}
		AddContact ac = new AddContact();
		ac.execute();
	}

	@Override
	public void removeContact(Contact contact) {
		class RemoveContact extends AsyncTask<Void, Void, Void> {
			List<Contact> contactList;
			@Override
			protected Void doInBackground(Void... voids) {
				ContactsDatabaseClient.getInstance(context)
						.getContactsDatabase()
						.getDao()
						.delete(contact);
				contactList = ContactsDatabaseClient.getInstance(context)
						.getContactsDatabase().getDao().getAll();
				return null;
			}

			@Override
			protected void onPostExecute(Void unused) {
				super.onPostExecute(unused);
				view.showContacts(contactList);
			}
		}
		RemoveContact rc = new RemoveContact();
		rc.execute();
	}

	@Override
	public void getTokenFromUser(String user) {
		final String[] token = {null};
		class GetToken extends AsyncTask<Void, Void, Void> {
			@Override
			protected Void doInBackground(Void... voids) {
				token[0] = ContactsDatabaseClient.getInstance(context).getContactsDatabase().getDao()
						.getTokenFromUser(user);
				return null;
			}

			@Override
			protected void onPostExecute(Void unused) {
				super.onPostExecute(unused);
				attView.doNotify(user, token[0]);
			}
		}
		GetToken gt = new GetToken();
		gt.execute();
	}
}
