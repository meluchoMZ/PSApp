package es.udc.psi.agendaly.Contacts;

import android.os.Build;
import android.os.Bundle;


import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import es.udc.psi.agendaly.Auth.AuthUtils;
import es.udc.psi.agendaly.BaseActivity;
import es.udc.psi.agendaly.CloudMessaging.CloudMessagingAPI;
import es.udc.psi.agendaly.CloudMessaging.CloudMessagingClient;
import es.udc.psi.agendaly.CloudMessaging.CloudMessagingData;
import es.udc.psi.agendaly.Contacts.presenter.AddToTeamView;
import es.udc.psi.agendaly.Contacts.viewmodel.AddToTeamAdapter;
import es.udc.psi.agendaly.R;
import es.udc.psi.agendaly.Teams.model.Teams;
import es.udc.psi.agendaly.Teams.presenter.TeamsDatabaseImpl;
import es.udc.psi.agendaly.Teams.presenter.TeamsPresenter;
import es.udc.psi.agendaly.Teams.presenter.TeamsView;

public class AddToTeamActivity extends BaseActivity implements TeamsView {
	private String user;
	@BindView(R.id.add_team_recyclerView)
	public RecyclerView recyclerView;
	private AddToTeamAdapter adapter;
	private TeamsPresenter presenter;

	@RequiresApi(api = Build.VERSION_CODES.O)
	@Override
	public void onCreate(Bundle onSavedInstanceState) {
		super.onCreate(onSavedInstanceState);
		Bundle bundle = getIntent().getExtras();
		user = (String) bundle.get(AddToTeamAdapter.USER);
		setContentView(R.layout.add_to_team_activity);
		presenter = new TeamsDatabaseImpl(this, getBaseContext());
		initRV(new LinkedList<>());
		presenter.getTeamsCreatedByUser(AuthUtils.retrieveUser().getEmail());
	}

	private void initRV(List<Teams> dataset) {
		adapter = new AddToTeamAdapter(dataset, user);
		LinearLayoutManager llm = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
		recyclerView.setLayoutManager(llm);
		recyclerView.setAdapter(adapter);
		recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), llm.getOrientation()));
	}


	@Override
	public void showTeams(List<Teams> teamsList) {
		adapter.setItems(teamsList);
	}

	@Override
	public void updateView(Teams team, int position) {

	}

	@Override
	public void showError(String msg) {

	}
}
