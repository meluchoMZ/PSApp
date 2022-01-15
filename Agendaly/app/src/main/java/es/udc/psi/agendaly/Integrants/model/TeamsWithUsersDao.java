package es.udc.psi.agendaly.Integrants.model;

import androidx.room.Query;

import java.util.List;

import es.udc.psi.agendaly.Teams.model.Teams;

public interface TeamsWithUsersDao {
	@Query("select * from integrants")
	public List<TeamWithUsers> getUsersFromTeam(Teams teams);
}
