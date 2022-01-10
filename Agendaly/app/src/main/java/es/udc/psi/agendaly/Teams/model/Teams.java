package es.udc.psi.agendaly.Teams.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "teams",
primaryKeys = {"tuid", "uuid"})
public class Teams {
	@ColumnInfo(name = "tuid")
	@NonNull
	private String teamID;
	@NonNull
	@ColumnInfo(name = "uuid")
	private String user;

	public Teams(String teamID, String user) {
		this.teamID = teamID;
		this.user = user;
	}

	public String getTeamID() {
		return this.teamID;
	}

	public void setTeamID(String id) {
		this.teamID = id;
	}

	public String getUser() {
		return this.user;
	}

	public void setUser(String user) {
		this.user = user;
	}
}
