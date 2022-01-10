package es.udc.psi.agendaly.Contacts.presenter;

import es.udc.psi.agendaly.Contacts.model.Contact;

public interface ContactsPresenter {
	public void getContacts();
	public void addContact(Contact contact);
	public void removeContact(Contact contact);
	public void getTokenFromUser(String user);
}
