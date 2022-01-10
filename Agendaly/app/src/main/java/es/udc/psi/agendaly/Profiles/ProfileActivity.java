package es.udc.psi.agendaly.Profiles;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import butterknife.BindView;
import es.udc.psi.agendaly.Auth.AuthUtils;
import es.udc.psi.agendaly.Auth.AuthenticationActivity;
import es.udc.psi.agendaly.Auth.User;
import es.udc.psi.agendaly.BaseActivity;
import es.udc.psi.agendaly.Contacts.model.Contact;
import es.udc.psi.agendaly.Contacts.model.ContactsDatabaseClient;
import es.udc.psi.agendaly.Contacts.presenter.AddToTeamView;
import es.udc.psi.agendaly.Contacts.presenter.ContactsView;
import es.udc.psi.agendaly.GlobalApplication;
import es.udc.psi.agendaly.R;
import es.udc.psi.agendaly.Teams.model.Teams;
import es.udc.psi.agendaly.Teams.model.TeamsDatabaseClient;
import es.udc.psi.agendaly.Teams.presenter.TeamsView;

public class ProfileActivity extends BaseActivity {
	@BindView(R.id.profile_sign_out_button)
	public Button signOutButton;

	@BindView(R.id.user_mail)
	public TextView mail;

	@BindView(R.id.contacts_no)
	public TextView contacts;

	@BindView(R.id.teams_no)
	public TextView teams;

	@RequiresApi(api = Build.VERSION_CODES.O)
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.profile_activity);
		User user = AuthUtils.retrieveUser();
		mail.setText(user.getEmail());
		contacts.setText("0");
		FirebaseFirestore db = FirebaseFirestore.getInstance();
		db.collection("userContacts").document(AuthUtils.retrieveUser().getEmail())
				.get().addOnCompleteListener(task -> {
					if (task.isSuccessful() && task.getResult() != null && task.getResult().getData() != null) {
						contacts.setText(Integer.toString(task.getResult().getData().size()));
					}
		});
		teams.setText("0");
		db.collection("userTeams").document(AuthUtils.retrieveUser().getEmail())
				.get().addOnCompleteListener(task -> {
			if (task.isSuccessful() && task.getResult() != null && task.getResult().getData() != null) {
				teams.setText(Integer.toString(task.getResult().getData().size()));
			}
		});
		signOutButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// do sign out
				AuthUtils.removeUser();
				// removes data from local databases
				class RemoveAll extends AsyncTask<Void, Void, Void> {

					@Override
					protected Void doInBackground(Void... voids) {
						ContactsDatabaseClient.getInstance(GlobalApplication.getContext())
								.getContactsDatabase().getDao().removeAll();
						TeamsDatabaseClient.getInstance(GlobalApplication.getContext())
								.getDatabase().getDao().removeAll();
						return null;
					}
				}
				RemoveAll ra = new RemoveAll(); ra.execute();
				// return to sign in page
				Intent intent = new Intent(getApplicationContext(), AuthenticationActivity.class);
				startActivity(intent);
			}
		});
	}
}
