package es.udc.psi.agendaly.Teams.presenter;

import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

import es.udc.psi.agendaly.Teams.model.Teams;
import es.udc.psi.agendaly.Teams.model.TeamsDatabaseClient;

public class TeamsDatabaseImpl implements TeamsPresenter {
	private Context context;
	private TeamsView view;

	public TeamsDatabaseImpl(TeamsView view, Context context) {
		this.view = view;
		this.context = context;
	}

	@Override
	public void getTeams() {
		class GetTeams extends AsyncTask<Void, Void, List<Teams>> {
			@Override
			protected void onPostExecute(List<Teams> teamsXUsers) {
				super.onPostExecute(teamsXUsers);
				view.showTeams(teamsXUsers);
			}

			@Override
			protected List<Teams> doInBackground(Void... voids) {
				List<Teams> queryResult =
						TeamsDatabaseClient.getInstance(context)
								.getDatabase().getDao().getAll();
				return queryResult;
			}
		}
		GetTeams gt = new GetTeams();
		gt.execute();
	}

	@Override
	public void getTeamsCreatedByUser(String mail) {
		class GetUsersFromTeam extends AsyncTask<Void, Void, List<Teams>> {
			@Override
			protected void onPostExecute(List<Teams> teams) {
				super.onPostExecute(teams);
				view.showTeams(teams);
			}

			@Override
			protected List<Teams> doInBackground(Void... avoid) {
				List<Teams> queryResult =
						TeamsDatabaseClient.getInstance(context)
								.getDatabase().getDao().getTeamsCreatedByUser(mail);
				return queryResult;
			}
		}
		GetUsersFromTeam gc = new GetUsersFromTeam();
		gc.execute();
	}

	@Override
	public void addTeam(Teams team) {
		class AddTeam extends AsyncTask<Void, Void, Void> {
			List<Teams> teamsList;
			boolean failed = false;

			@Override
			protected void onPostExecute(Void unused) {
				super.onPostExecute(unused);
				if (failed) {
					view.showError("Cannot save team '"+team.getTeamID()+"'");
				} else {
					view.showTeams(teamsList);
				}
			}

			@Override
			protected Void doInBackground(Void... voids) {
				try {
					TeamsDatabaseClient.getInstance(context)
							.getDatabase().getDao().insert(team);
					teamsList = TeamsDatabaseClient.getInstance(context)
							.getDatabase().getDao().getAll();
				} catch (Exception e) {
					failed = true;
					teamsList = TeamsDatabaseClient.getInstance(context)
							.getDatabase().getDao().getAll();
				}
				return null;
			}
		}
		AddTeam at = new AddTeam();
		at.execute();
	}

	@Override
	public void removeTeam(Teams team) {
		class RemoveTeam extends AsyncTask<Void, Void, Void> {
			List<Teams> teamsList;
			@Override
			protected Void doInBackground(Void... voids) {
				TeamsDatabaseClient.getInstance(context)
						.getDatabase().getDao().delete(team);
				teamsList = TeamsDatabaseClient.getInstance(context)
						.getDatabase().getDao().getAll();
				return null;
			}

			@Override
			protected void onPostExecute(Void unused) {
				super.onPostExecute(unused);
			}
		}
		RemoveTeam rt = new RemoveTeam();
		rt.execute();
	}
}
