package es.udc.psi.agendaly.Calendar;


import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;

import butterknife.BindView;
import es.udc.psi.agendaly.Calendar.presenter.CalendarDatabaseImp;
import es.udc.psi.agendaly.Calendar.presenter.CalendarPresenter;
import es.udc.psi.agendaly.Calendar.presenter.CalendarView;
import es.udc.psi.agendaly.Calendar.viewmodel.EventViewModel;
import es.udc.psi.agendaly.R;

public class EventsCalendarAdapter extends RecyclerView.Adapter<EventsCalendarAdapter.MyViewHolder> implements CalendarView {
    @BindView(R.id.screen_sw)
    SwitchCompat switchCompat;

    CalendarPresenter mPresenter;
    ArrayList<String> send;
    View v;


    private EventListener eventListener;

    public EventsCalendarAdapter(EventListener eventListener, boolean auto) {
        mItems = new ArrayList<>();
        this.eventListener=eventListener;
    }

    public interface  EventListener {
        void checkedSwitch(boolean checked, int diaNoti, String eventDate);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.screen_calendar_detail, parent, false);

        mPresenter = new CalendarDatabaseImp(this, v.getContext());
        return new MyViewHolder(v);
    }
    private List<EventViewModel> mItems;

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
        holder.sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    String edit = holder.notification.getText().toString();
                    Log.d("_TAGEDIT",edit);
                    mPresenter.update(mItems.get(holder.getAdapterPosition()).getEvent(),
                            edit, 1);
                }else{
                    mPresenter.update(mItems.get(holder.getAdapterPosition()).getEvent(),
                            mItems.get(holder.getAdapterPosition()).getNotificationDay(),
                            0);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    @Override
    public void showEvents(List<EventViewModel> events) {

    }

    @Override
    public void updateEvent(EventViewModel event, int position) {

    }

    @Override
    public void notifyEvent(List<EventViewModel> events) {
        send = new ArrayList<>();
        for (EventViewModel a : events) {
            send.add("Evento : " + a.getEvent() + "/" + " a las " +
                    a.getHour() + " " + a.getDescription());
        }

    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView event, hour, description;
        EditText notification;
        public SwitchCompat sw;

        public MyViewHolder(View view) {
            super(view);
            event = view.findViewById(R.id.tvEventName);
            hour = view.findViewById(R.id.tvHour);
            description = view.findViewById(R.id.tvDescription);
            notification = view.findViewById(R.id.editTextNotificationScreen);
            sw = view.findViewById(R.id.screen_sw);
        }

        public void bind(EventViewModel eventViewModel) {
            event.setText(eventViewModel.getEvent());
            hour.setText(eventViewModel.getHour());
            description.setText(eventViewModel.getDescription());
            notification.setText(eventViewModel.getNotificationDay());

            boolean check;
            if(eventViewModel.getSw()==0){
                check = false;
            }else{check = true;}
            sw.setChecked(check);
        }


    }
}
