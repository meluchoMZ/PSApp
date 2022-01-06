package es.udc.psi.agendaly.Contacts.viewmodel;

import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;


import com.google.android.gms.auth.api.Auth;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import es.udc.psi.agendaly.Auth.AuthUtils;
import es.udc.psi.agendaly.BaseActivity;
import es.udc.psi.agendaly.CloudMessaging.CloudMessagingAPI;
import es.udc.psi.agendaly.CloudMessaging.CloudMessagingClient;
import es.udc.psi.agendaly.CloudMessaging.CloudMessagingData;
import es.udc.psi.agendaly.CloudMessaging.CloudMessagingResponse;
import es.udc.psi.agendaly.CloudMessaging.CloudMessagingSender;
import es.udc.psi.agendaly.Contacts.presenter.AddToTeamView;
import es.udc.psi.agendaly.Contacts.presenter.ContactsDatabaseImpl;
import es.udc.psi.agendaly.Contacts.presenter.ContactsPresenter;
import es.udc.psi.agendaly.GlobalApplication;
import es.udc.psi.agendaly.R;
import es.udc.psi.agendaly.Teams.model.Teams;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddToTeamAdapter extends RecyclerView.Adapter<AddToTeamAdapter.ViewHolder> implements AddToTeamView {
	private List<Teams> dataset;
	private String userMail;
	public static final String USER = "es.udc.psi.agendaly.USER";
	private AddToTeamAdapter self = this;
	private boolean canWrite;
	private String team;
	private CloudMessagingAPI api;

	public AddToTeamAdapter(List<Teams> dataset, String mail) {
		this.dataset = dataset;
		this.userMail = mail;
		canWrite = true;
		api = CloudMessagingClient.getClient(CloudMessagingAPI.BASE_URL).create(CloudMessagingAPI.class);
	}

	@NonNull
	@Override
	public AddToTeamAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View v = LayoutInflater.from(parent.getContext())
				.inflate(R.layout.team_tile, parent, false);
		return new ViewHolder(v);
	}

	@Override
	public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
		holder.bind(dataset.get(position));
		holder.name.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (canWrite) {
					canWrite = false;
					team = holder.name.getText().toString();
					ContactsPresenter presenter = new ContactsDatabaseImpl(self, v.getContext());
					presenter.getTokenFromUser(userMail);
				}
			}
		});
	}

	@Override
	public int getItemCount() {
		return dataset.size();
	}

	public void setItems(List<Teams> teamsList) {
		dataset = teamsList;
		notifyDataSetChanged();
	}

	public void updateItem(int position, Teams teams) {
		dataset.set(position, teams);
		notifyItemChanged(position);
	}

	@RequiresApi(api = Build.VERSION_CODES.O)
	@Override
	public void doNotify(String user, String token) {
		final String sender = AuthUtils.retrieveUser().getEmail();
		final String title = "Team invitation";
		final String message = sender
				+ " wants to add you to the team "+team;
		CloudMessagingData data = new CloudMessagingData(title, message, sender);
		CloudMessagingSender notifier = new CloudMessagingSender(data, token);
		api.sendNotification(notifier).enqueue(new Callback<CloudMessagingResponse>() {
			@Override
			public void onResponse(Call<CloudMessagingResponse> call, Response<CloudMessagingResponse> response) {
				if (response.code() == 200) {
					if (response.body().getCode() != 1) {
						Toast.makeText(GlobalApplication.getContext(), "invitation sent", Toast.LENGTH_SHORT).show();
					}
				}
			}

			@Override
			public void onFailure(Call<CloudMessagingResponse> call, Throwable t) {
				Toast.makeText(GlobalApplication.getContext(), "Cannot send invitation to join the team",
						Toast.LENGTH_SHORT). show();
			}
		});
		canWrite = true;
	}

	public static class ViewHolder extends RecyclerView.ViewHolder {
		public TextView name;

		public ViewHolder(@NonNull View itemView) {
			super(itemView);
			this.name = itemView.findViewById(R.id.teams_tile_name);
		}

		public void bind(Teams teams) {
			this.name.setText(teams.getTeamID());
		}
	}
}
