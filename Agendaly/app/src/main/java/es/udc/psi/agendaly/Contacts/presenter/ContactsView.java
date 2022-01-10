package es.udc.psi.agendaly.Contacts.presenter;

import java.util.List;

import es.udc.psi.agendaly.Contacts.model.Contact;

public interface ContactsView {
	public void showContacts(List<Contact> contactList);
	public void updateView(Contact contact, int position);
	public void showError(String message);
}
