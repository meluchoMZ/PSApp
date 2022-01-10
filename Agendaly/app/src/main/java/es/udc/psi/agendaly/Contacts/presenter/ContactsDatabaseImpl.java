package es.udc.psi.agendaly.Contacts.presenter;


import android.content.AbstractThreadedSyncAdapter;
import android.content.AsyncQueryHandler;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.core.graphics.drawable.IconCompat;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import es.udc.psi.agendaly.Auth.AuthUtils;
import es.udc.psi.agendaly.CloudMessaging.CloudMessagingData;
import es.udc.psi.agendaly.Contacts.model.Contact;
import es.udc.psi.agendaly.Contacts.model.ContactsDatabaseClient;
import es.udc.psi.agendaly.Teams.model.TeamsDatabaseClient;

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
			@RequiresApi(api = Build.VERSION_CODES.O)
			@Override
			protected List<Contact> doInBackground(Void... voids) {
				List<Contact> queryResult =
				ContactsDatabaseClient.getInstance(context)
						.getContactsDatabase()
						.getDao()
						.getAll();
				if (queryResult.isEmpty()) {
					FirebaseFirestore db = FirebaseFirestore.getInstance();
					db.collection("userContacts").document(
							AuthUtils.retrieveUser().getEmail()
					).get().addOnCompleteListener(task -> {
						if (task.isSuccessful()) {
							Map<String, Object> m = null;
							if (task.getResult() != null) {
								m = task.getResult().getData();
								if (m != null) {
									m.forEach((key, value) -> {
										// key is the email of the contact
										Contact c = new Contact(key);
										// value is the token of the user
										c.setToken((String) value);
										queryResult.add(c);
									});
									view.showContacts(queryResult);
									class InnerInsertion extends AsyncTask<Void, Void, Void> {
										@Override
										protected Void doInBackground(Void... voids) {
											queryResult.forEach((t) -> {
												ContactsDatabaseClient.getInstance(context)
														.getContactsDatabase().getDao()
														.insert(t);
											});
											return null;
										}
									}
									InnerInsertion ii = new InnerInsertion();
									ii.execute();
								}
							}
						}
					});
				}
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
			@RequiresApi(api = Build.VERSION_CODES.O)
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
									HashMap<String, String> map = new HashMap<>();
									map.put(contact.getEmail(), token);
									db.collection("userContacts").document(
											AuthUtils.retrieveUser().getEmail()
									).set(map, SetOptions.merge()).addOnCompleteListener(task2 -> {
										if (task2.isSuccessful()) {
											BGDBCall call = new BGDBCall();
											call.execute(token);
										} else {
											view.showError("Cannot add contact. Please check internet connection");
										}
									});
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
				attView.doNotify(user);
			}
		}
		GetToken gt = new GetToken();
		gt.execute();
	}
}
