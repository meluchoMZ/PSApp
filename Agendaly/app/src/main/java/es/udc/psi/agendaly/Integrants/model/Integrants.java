package es.udc.psi.agendaly.Integrants.model;

import androidx.room.Entity;

@Entity(tableName = "integrants", primaryKeys = {"team", "user"})
public class Integrants {
	public String team;
	public String user;
}

