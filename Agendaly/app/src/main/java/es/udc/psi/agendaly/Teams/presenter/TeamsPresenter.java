package es.udc.psi.agendaly.Teams.presenter;

import es.udc.psi.agendaly.Teams.model.Teams;

public interface TeamsPresenter {
	public void getTeams();
	public void getTeamsCreatedByUser(String mail);
	public void addTeam(Teams team);
	public void removeTeam(Teams team);
}
