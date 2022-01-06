package es.udc.psi.agendaly.Teams;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import es.udc.psi.agendaly.Auth.AuthUtils;
import es.udc.psi.agendaly.BaseActivity;
import es.udc.psi.agendaly.Calendar.CalendarActivity;
import es.udc.psi.agendaly.Contacts.ContactsActivity;
import es.udc.psi.agendaly.Profiles.ProfileActivity;
import es.udc.psi.agendaly.R;
import es.udc.psi.agendaly.Teams.model.Teams;
import es.udc.psi.agendaly.Teams.presenter.TeamsDatabaseImpl;
import es.udc.psi.agendaly.Teams.presenter.TeamsPresenter;
import es.udc.psi.agendaly.Teams.presenter.TeamsView;
import es.udc.psi.agendaly.Teams.viewmodel.TeamsAdapter;
import es.udc.psi.agendaly.TimeTable.Horario;

public class TeamsActivity extends BaseActivity implements TeamsView {
	@BindView(R.id.bottomnav)
	BottomNavigationView bm;
	@BindView(R.id.teams_rv)
	RecyclerView rv;
	private TeamsAdapter tAdapter;
	private TeamsPresenter tPresenter;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.teams_activity);
		bm.setSelectedItemId(R.id.teamsAppBar);
		tPresenter = new TeamsDatabaseImpl(this, getBaseContext());
		bm.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
			@Override
			public boolean onNavigationItemSelected(@NonNull MenuItem item) {
				switch (item.getItemId()){
					case R.id.inicioAppBar:
						Intent intentInicio = new Intent(getBaseContext(), Horario.class);
						startActivity(intentInicio);
						break;
					case R.id.calendarAppBar:
						Intent intentCalendar = new Intent(getBaseContext(), CalendarActivity.class);
						startActivity(intentCalendar);
						break;
					case R.id.contactsAppBar:
						Intent intentContacts = new Intent(getBaseContext(), ContactsActivity.class);
						startActivity(intentContacts);
						break;
					case R.id.teamsAppBar:
						break;
					case R.id.infoAppBar:
						Intent intentInfo = new Intent(getBaseContext(), ProfileActivity.class);
						startActivity(intentInfo);
						break;
				}
				return true;
			}
		});
		initRV(new ArrayList<>());
		tPresenter.getTeams();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.contacts_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(@NonNull MenuItem item) {
		switch (item.getItemId()) {
			// recycling contact add layout
			case R.id.contact_menu_add:
				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				builder.setTitle(getString(R.string.add_team_input_dialog));
				View v = LayoutInflater.from(this).inflate(R.layout.add_contact_input_dialog, null);
				((EditText) v.findViewById(R.id.add_contact_email_input)).setHint(null);
				builder.setView(v);
				builder.setCancelable(false);
				builder.setPositiveButton(getString(R.string.add), new DialogInterface.OnClickListener() {
					@RequiresApi(api = Build.VERSION_CODES.O)
					@Override
					public void onClick(DialogInterface dialog, int which) {
						String newTeam = ((EditText) v.findViewById(R.id.add_contact_email_input))
								.getText().toString();
						if (newTeam != null && !newTeam.isEmpty()) {
							String user = AuthUtils.retrieveUser().getEmail();
							tPresenter.addTeam(
									new Teams(newTeam+"#"+user, user)
							);
						}
						return;
					}
				});
				builder.setNegativeButton(getString(R.string.cancel), null);
				builder.create().show();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	private void initRV(List<Teams> teamsList) {
		tAdapter = new TeamsAdapter(teamsList);
		LinearLayoutManager llm = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
		rv.setLayoutManager(llm);
		rv.setAdapter(tAdapter);
		rv.addItemDecoration(new DividerItemDecoration(rv.getContext(), llm.getOrientation()));
	}
	@Override
	public void showTeams(List<Teams> teamsList) {
		tAdapter.setItems(teamsList);
	}

	@Override
	public void updateView(Teams team, int position) {
		tAdapter.updateItem(position, team);
	}

	@Override
	public void showError(String msg) {
		Snackbar s = Snackbar.make((ViewGroup)getWindow().getDecorView(),
				msg, Snackbar.LENGTH_SHORT);
		View view = s.getView();
		FrameLayout.LayoutParams params = (FrameLayout.LayoutParams)view.getLayoutParams();
		params.gravity = Gravity.CENTER;
		view.setLayoutParams(params);
		s.show();
	}
}
