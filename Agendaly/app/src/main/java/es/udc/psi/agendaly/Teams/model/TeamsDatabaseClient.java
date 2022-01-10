package es.udc.psi.agendaly.Teams.model;

import android.content.Context;

import androidx.room.Room;

public class TeamsDatabaseClient {
	private Context context;
	private static TeamsDatabaseClient instance;
	private TeamsDatabase database;

	private TeamsDatabaseClient(Context context) {
		this.context = context;
		this.database = Room.databaseBuilder(
				context,
				TeamsDatabase.class,
				"TeamsXUsersDatabase.db"
		).build();
	}

	public static synchronized TeamsDatabaseClient getInstance(Context context) {
		if (instance == null) {
			instance = new TeamsDatabaseClient(context);
		}
		return instance;
	}

	public TeamsDatabase getDatabase() {
		return this.database;
	}
}
