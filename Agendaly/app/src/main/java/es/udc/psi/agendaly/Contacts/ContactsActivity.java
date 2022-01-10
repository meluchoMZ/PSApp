package es.udc.psi.agendaly.Contacts;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import es.udc.psi.agendaly.BaseActivity;
import es.udc.psi.agendaly.Calendar.CalendarActivity;
import es.udc.psi.agendaly.Contacts.model.Contact;
import es.udc.psi.agendaly.Contacts.presenter.ContactsDatabaseImpl;
import es.udc.psi.agendaly.Contacts.presenter.ContactsPresenter;
import es.udc.psi.agendaly.Contacts.presenter.ContactsView;
import es.udc.psi.agendaly.Contacts.viewmodel.ContactsAdapter;
import es.udc.psi.agendaly.Profiles.ProfileActivity;
import es.udc.psi.agendaly.R;
import es.udc.psi.agendaly.Teams.TeamsActivity;
import es.udc.psi.agendaly.TimeTable.Horario;

public class ContactsActivity extends BaseActivity implements ContactsView {
	@BindView(R.id.bottomnav)
	BottomNavigationView bm;
	@BindView(R.id.contacts_rv)
	RecyclerView rv;
	private ContactsAdapter cAdapter;
	private ContactsPresenter cPresenter;

	@Override
	public void onCreate(Bundle onSavedInstanceState) {
		super.onCreate(onSavedInstanceState);

		cPresenter = new ContactsDatabaseImpl(this, getBaseContext());
		setContentView(R.layout.contacts_activity);
		bm.setSelectedItemId(R.id.contactsAppBar);

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
						break;
					case R.id.teamsAppBar:
						Intent intentTeams = new Intent(getBaseContext(), TeamsActivity.class);
						startActivity(intentTeams);
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
		cPresenter.getContacts();
	}

	private void initRV(List<Contact> contactList) {
		cAdapter = new ContactsAdapter(contactList);
		LinearLayoutManager llm = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
		rv.setLayoutManager(llm);
		rv.setAdapter(cAdapter);
		rv.addItemDecoration(new DividerItemDecoration(rv.getContext(), llm.getOrientation()));
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
			case R.id.contact_menu_add:
				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				builder.setTitle(getString(R.string.add_contact));
				View v = LayoutInflater.from(this).inflate(R.layout.add_contact_input_dialog, null);
				builder.setView(v);
				builder.setCancelable(false);
				builder.setPositiveButton(getString(R.string.add), new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						String newContactToAdd = ((EditText) v.findViewById(R.id.add_contact_email_input))
								.getText().toString();
						if (newContactToAdd != null && !newContactToAdd.isEmpty()) {
							cPresenter.addContact(new Contact(newContactToAdd));
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

	@Override
	public void showContacts(List<Contact> contactList) {
		cAdapter.setItems(contactList);
	}

	@Override
	public void updateView(Contact contact, int position) {
		cAdapter.updateItem(position, contact);
	}

	@Override
	public void showError(String message) {
		Snackbar s = Snackbar.make((ViewGroup)getWindow().getDecorView(),
				message, Snackbar.LENGTH_SHORT);
		View view = s.getView();
		FrameLayout.LayoutParams params = (FrameLayout.LayoutParams)view.getLayoutParams();
		params.gravity = Gravity.CENTER;
		view.setLayoutParams(params);
		s.show();
	}
}
