package es.udc.psi.agendaly.Calendar;


import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
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
    int dias;
    ArrayList<String> send;
    String action="send.events";
    View v;
    String day;

    private EventListener eventListener;
    private boolean auto;
    public EventsCalendarAdapter(EventListener eventListener, boolean auto) {
        mItems = new ArrayList<>();
        this.eventListener=eventListener;
        this.auto=auto;
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

    //public EventsCalendarAdapter() {

        //mItems = new ArrayList<>();
    //}

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
                /*if (isChecked) {
                    // Si esta activo.
                    if(mItems.get(holder.getAdapterPosition()).getNotificationDay()!=null) {
                        day=mItems.get(holder.getAdapterPosition()).getDay();

                        Log.d("_TAG", "holder "+ day);
                        eventListener.checkedSwitch(true,
                                Integer.parseInt(mItems.get(holder.getAdapterPosition()).getNotificationDay()),
                                day);
                    } else{
                        Toast.makeText(v.getContext(), "No days set, delete the event", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Si no esta activo
                    mItems.get(holder.getAdapterPosition()).setSw(0);
                    Log.d("_TAG", String.valueOf(mItems.get(holder.getAdapterPosition()).getSw()));
                    if(mItems.get(holder.getAdapterPosition()).getNotificationDay()!=null) {
                        eventListener.checkedSwitch(false,
                                Integer.parseInt(mItems.get(holder.getAdapterPosition()).getNotificationDay()),
                                mItems.get(holder.getAdapterPosition()).getDay());
                    }
                }*/
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
        public TextView event, hour, description, notification;
        public SwitchCompat sw;

        public MyViewHolder(View view) {
            super(view);
            event = view.findViewById(R.id.tvEventName);
            hour = view.findViewById(R.id.tvHour);
            description = view.findViewById(R.id.tvDescription);
            notification = view.findViewById(R.id.tvNotification);
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
