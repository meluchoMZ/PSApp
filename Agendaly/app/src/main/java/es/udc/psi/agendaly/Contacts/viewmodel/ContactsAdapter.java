package es.udc.psi.agendaly.Contacts.viewmodel;

import android.content.Intent;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import es.udc.psi.agendaly.Contacts.AddToTeamActivity;
import es.udc.psi.agendaly.Contacts.model.Contact;
import es.udc.psi.agendaly.R;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ViewHolder> {
	private List<Contact> dataset;

	public void setItems(List<Contact> contactList) {
		dataset = contactList;
		notifyDataSetChanged();
	}

	public void updateItem(int position, Contact contact) {
		dataset.set(position, contact);
		notifyItemChanged(position);
	}

	public static class ViewHolder extends RecyclerView.ViewHolder implements  View.OnCreateContextMenuListener {
		public TextView email, id;
		public ViewHolder(View view) {
			super(view);
			view.setOnCreateContextMenuListener(this);
			this.email = view.findViewById(R.id.contact_tile_email_tv);
			this.id = view.findViewById(R.id.contact_tile_id_tv);
		}
		public void bind(Contact contact) {
			this.email.setText(contact.getEmail());
		this.id.setText(contact.getToken()==null?"":contact.getToken().substring(0,30)+"...");
		}

		@Override
		public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
			int adapterPosition = this.getAdapterPosition();
			menu.add(adapterPosition, v.getId(), 0, R.string.add2team)
				.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
					@Override
					public boolean onMenuItemClick(MenuItem item) {
						// getting email
						String text = ((TextView) ((LinearLayout)((FrameLayout) v)
								.getChildAt(0)).getChildAt(0)).getText().toString();
						Toast.makeText(v.getContext(), text, Toast.LENGTH_SHORT).show();
						Intent intent = new Intent(itemView.getContext(), AddToTeamActivity.class);
						intent.putExtra(AddToTeamAdapter.USER, text);
						intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						v.getContext().getApplicationContext().startActivity(intent);
						return true;
					}
				});
		}


	}

	public ContactsAdapter(List<Contact> dataset) {
		this.dataset = dataset;
	}

	@NonNull
	@Override
	public ContactsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View v = LayoutInflater.from(parent.getContext())
				.inflate(R.layout.contact_tile, parent, false);
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
}

