package es.udc.psi.agendaly.Teams.viewmodel;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import es.udc.psi.agendaly.R;
import es.udc.psi.agendaly.Teams.model.Teams;

public class TeamsAdapter extends RecyclerView.Adapter<TeamsAdapter.ViewHolder> {
	private List<Teams> dataset;

	public TeamsAdapter(List<Teams> dataset) {
		this.dataset = dataset;
	}

	@NonNull
	@Override
	public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View v = LayoutInflater.from(parent.getContext())
				.inflate(R.layout.team_tile, parent, false);
		return new ViewHolder(v);
	}

	@Override
	public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
		holder.bind(dataset.get(position));
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
