package es.udc.psi.agendaly.Teams.presenter;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.loader.content.AsyncTaskLoader;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import es.udc.psi.agendaly.Auth.AuthUtils;
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

			@RequiresApi(api = Build.VERSION_CODES.O)
			@Override
			protected List<Teams> doInBackground(Void... voids) {
				List<Teams> queryResult =
						TeamsDatabaseClient.getInstance(context)
								.getDatabase().getDao().getAll();
				if (queryResult.isEmpty()) {
					FirebaseFirestore db = FirebaseFirestore.getInstance();
					db.collection("userTeams").document(
							AuthUtils.retrieveUser().getEmail()
					).get().addOnCompleteListener(task -> {
						Map<String, Object> m = null;
						if (task.getResult() != null &&
								task.getResult().getData() != null) {
							m = task.getResult().getData();
							m.forEach((key, value) -> {
								Teams t = new Teams(key, key.split("#")[1]);
								queryResult.add(t);
							});
							view.showTeams(queryResult);
							class InnerInsertion extends AsyncTask<Void, Void, Void> {
								@Override
								protected Void doInBackground(Void... voids) {
									queryResult.forEach((t) -> {
										TeamsDatabaseClient.getInstance(context)
												.getDatabase().getDao()
												.insert(t);
									});
									return null;
								}
							}
							InnerInsertion ii = new InnerInsertion();
							ii.execute();
						}
					});
				}
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

			@RequiresApi(api = Build.VERSION_CODES.O)
			@Override
			protected List<Teams> doInBackground(Void... avoid) {
				List<Teams> queryResult =
						TeamsDatabaseClient.getInstance(context)
								.getDatabase().getDao().getTeamsCreatedByUser(mail);
				if (queryResult.isEmpty()) {
					FirebaseFirestore db = FirebaseFirestore.getInstance();
					db.collection("userTeams").document(
							AuthUtils.retrieveUser().getEmail()
					).get().addOnCompleteListener(task -> {
						Map<String, Object> m = null;
						List<Teams> totalTeams = new LinkedList<>();
						if (task.getResult() != null &&
							task.getResult().getData() != null) {
							m = task.getResult().getData();
							m.forEach((key, value) -> {
								Teams t = new Teams(key, key.split("#")[1]);
								if (t.getUser().equals(AuthUtils.retrieveUser().getEmail())) {
									queryResult.add(t);
								}
								totalTeams.add(t);
							});
							view.showTeams(queryResult);
							class InnerInsertion extends AsyncTask<Void, Void, Void> {
								@Override
								protected Void doInBackground(Void... voids) {
									totalTeams.forEach((t) -> {
										TeamsDatabaseClient.getInstance(context)
												.getDatabase().getDao()
												.insert(t);
									});
									return null;
								}
							}
							InnerInsertion ii = new InnerInsertion();
							ii.execute();
						}
					});
				}
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
			}

			@RequiresApi(api = Build.VERSION_CODES.O)
			@Override
			protected Void doInBackground(Void... voids) {
				class BGDBCall extends AsyncTask<Void, Void, Void> {

					@Override
					protected Void doInBackground(Void... voids) {
						try {
							TeamsDatabaseClient.getInstance(context)
									.getDatabase().getDao().insert(team);
						} catch (Exception e) {
							view.showError("Cannot save team '"+team.getTeamID()+"'");
						} finally {
							teamsList = TeamsDatabaseClient.getInstance(context)
									.getDatabase().getDao().getAll();
						}
						return null;
					}

					@Override
					protected void onPostExecute(Void unused) {
						super.onPostExecute(unused);
						view.showTeams(teamsList);
					}
				}
				if (team == null) return null;
				teamsList = TeamsDatabaseClient.getInstance(context)
						.getDatabase().getDao().getAll();
				for (Teams t : teamsList) {
					if (t.getTeamID().equals(team.getTeamID())) {
						view.showError("Cannot create team: already created");
						return null;
					}
				}
				HashMap<String, String> map = new HashMap<>();
				map.put(team.getTeamID(), null);
				FirebaseFirestore db = FirebaseFirestore.getInstance();
				db.collection("userTeams").document(
						AuthUtils.retrieveUser().getEmail()
				).set(map, SetOptions.merge()).addOnCompleteListener(task -> {
					if (task.isSuccessful()) {
						BGDBCall call = new BGDBCall();
						call.execute();
					} else {
						view.showError("Cannot create team. Check internet connection");
					}
				});
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
