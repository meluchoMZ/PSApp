package es.udc.psi.agendaly.Teams.model;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Teams.class}, version = 1)
public abstract class TeamsDatabase extends RoomDatabase {
	public abstract TeamsDao getDao();
}
