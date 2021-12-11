package es.udc.psi.agendaly.Calendar;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import es.udc.psi.agendaly.Calendar.viewmodel.EventViewModel;
import es.udc.psi.agendaly.R;

public class EventsCalendarAdapter extends RecyclerView.Adapter<EventsCalendarAdapter.MyViewHolder> {
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.screen_calendar_detail, parent, false);
        return new MyViewHolder(v);
    }
    private List<EventViewModel> mItems;

    public EventsCalendarAdapter() {

        mItems = new ArrayList<>();
    }

    public void setItems(List<EventViewModel> items){
        mItems=items;
        notifyDataSetChanged();
    }

    public void updateItem(EventViewModel item,
                           int position) {

        mItems.add(position, item);
        notifyItemChanged(position);
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.bind(mItems.get(position));
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView event, hour, description;

        public MyViewHolder(View view) {
            super(view);
            event = view.findViewById(R.id.tvEventName);
            hour = view.findViewById(R.id.tvHour);
            description = view.findViewById(R.id.tvDescription);
        }

        public void bind(EventViewModel eventViewModel) {
            event.setText(eventViewModel.getEvent());
            hour.setText(eventViewModel.getHour());
            description.setText(eventViewModel.getDescription());
        }


    }
}
