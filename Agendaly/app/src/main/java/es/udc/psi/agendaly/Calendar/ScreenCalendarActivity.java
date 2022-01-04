package es.udc.psi.agendaly.Calendar;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import es.udc.psi.agendaly.BaseActivity;
import es.udc.psi.agendaly.Calendar.presenter.CalendarDatabaseImp;
import es.udc.psi.agendaly.Calendar.presenter.CalendarPresenter;
import es.udc.psi.agendaly.Calendar.presenter.CalendarView;
import es.udc.psi.agendaly.Calendar.viewmodel.EventViewModel;
import es.udc.psi.agendaly.R;
import es.udc.psi.agendaly.TimeTable.AddEvent;
import es.udc.psi.agendaly.TimeTable.DeleteEvent;
import es.udc.psi.agendaly.TimeTable.EventAdapter;
import es.udc.psi.agendaly.TimeTable.presenter.AsignaturaDatabaseImp;
import es.udc.psi.agendaly.TimeTable.presenter.AsignaturaPresenter;


@RequiresApi(api = Build.VERSION_CODES.O)
public class ScreenCalendarActivity extends BaseActivity implements CalendarView, EventsCalendarAdapter.EventListener  {
    EventsCalendarAdapter mAdapter;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");
    String day;
    ArrayList<String> send;
    @BindView(R.id.calendarRecyclerView)
    RecyclerView recyclerView;
    CalendarPresenter mPresenter;
    String action="send.events";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_calendar);
        mPresenter = new CalendarDatabaseImp(this, getBaseContext());
        setUp();
        Bundle parametros = this.getIntent().getExtras();
        if(parametros !=null){
            String date = parametros.getString("date");
            //Log.d("_TAG",date);
            //1 diciembre 2021
            day=date;
            search(date);
        }


    }

    public void setUp(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        mAdapter = new EventsCalendarAdapter(this,false);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_menu_calendar, menu);
        return true;

    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        int id = item.getItemId();
        if(id==R.id.icon_add){
            Bundle extras = new Bundle();
            String message = day;
            Intent intent = new Intent(getBaseContext(), AddCalendarEventActivity.class);
            extras.putString("date",message);
            intent.putExtras(extras);
            startActivity(intent);

        }

        if(id == R.id.icon_delete){
            Bundle extras = new Bundle();
            String message = day;
            Intent intent = new Intent(getBaseContext(), DeleteCalendarEventActivity.class);
            extras.putString("date",message);
            intent.putExtras(extras);
            startActivity(intent);
        }

        return true;
    }

    public void search(String day){
        Log.d("_TAGD",day);
        mPresenter.searchByDay(day);
    }


    @Override
    public void showEvents(List<EventViewModel> events) {
        mAdapter.setItems(events);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void notifyEvent(List<EventViewModel> events) {
        send = new ArrayList<>();
        for (EventViewModel a : events) {
            send.add("Mañana : " + a.getEvent() + "/" + " a las " +
                    a.getHour() + " " + a.getDescription());
        }

    }

    @Override
    public void updateEvent(EventViewModel event, int position) {

    }

    //notification
    @RequiresApi(api = Build.VERSION_CODES.O)
    public String getDayNotifyString(String eventDay, int i){
        LocalDate localDate1 = LocalDate.parse(eventDay, DateTimeFormatter.ofPattern("d MMMM yyyy"));
        localDate1 = localDate1.minusDays(i);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");
        return formatter.format(localDate1);


    }

    @Override
    public void checkedSwitch(boolean checked, int diaNoti, String eventDate) {
        mPresenter.searchDayAfter(eventDate);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMMM yyyy");
        //if(send!=null) Log.d("_TAGCH","checkedSwitch "+ eventDate+ ""+ diaNoti + ""+ checked);
        // dia que vamos a notificar
        String a = getDayNotifyString(eventDate,diaNoti);
        LocalDate d = LocalDate.parse(a, formatter);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, d.getDayOfMonth());
        calendar.set(Calendar.MONTH, d.getMonthValue());
        calendar.set(Calendar.DAY_OF_MONTH, d.getYear());
        if (checked) {
            // evento a notificar
            setAlarm(calendar,false);
            Toast.makeText(getBaseContext(), "Alarm set", Toast.LENGTH_SHORT).show();
        }else{
            setAlarm(calendar,true);
            Toast.makeText(getBaseContext(), "Alarm delete", Toast.LENGTH_SHORT).show();
        }
    }
    public void setAlarm( Calendar calendar, boolean cancel){
        //get instance of alarm manager
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        if(send !=null){
            Intent intentAlarm = new Intent(this, CalendarReceiver.class);
            intentAlarm.putExtra("list_event", send);
            intentAlarm.setAction(action);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this,
                    12, intentAlarm, 0);
            if(!cancel){
                alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                Log.d("_TAGAlarm", "alarmManager " + intentAlarm.getAction());

            }else{
                alarmManager.cancel(pendingIntent);
            }
        }
    }

}
