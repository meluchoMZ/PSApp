package es.udc.psi.agendaly.Integrants.model;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.List;

import es.udc.psi.agendaly.Contacts.model.Contact;
import es.udc.psi.agendaly.Teams.model.Teams;

public class TeamWithUsers {
	@Embedded
	public Teams team;
	@Relation(
			parentColumn = "team",
			entityColumn = "user",
			associateBy = @Junction(Integrants.class)
	)
	public List<Contact> users;
}
