package es.udc.psi.agendaly.Teams.presenter;

import java.util.List;

import es.udc.psi.agendaly.Teams.model.Teams;

public interface TeamsView {
	public void showTeams(List<Teams> teamsList);
	public void updateView(Teams team, int position);
	public void showError(String msg);
}
