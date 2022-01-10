package es.udc.psi.agendaly.Calendar;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import es.udc.psi.agendaly.Calendar.presenter.CalendarDatabaseImp;
import es.udc.psi.agendaly.Calendar.presenter.CalendarPresenter;
import es.udc.psi.agendaly.Calendar.presenter.CalendarView;
import es.udc.psi.agendaly.Calendar.viewmodel.EventViewModel;
import es.udc.psi.agendaly.R;

@RequiresApi(api = Build.VERSION_CODES.O)
public class CalendarActivity extends AppCompatActivity implements CalendarView, CalendarAdapter.OnItemListener{
    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;
    private LocalDate selectedDate;
    private CalendarPresenter mPresenter;
    CalendarReceiver myReceiver;
    ArrayList<String> send;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");
    String action="send.events";

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        initWidgets();
        selectedDate = LocalDate.now();
        setMonthView();

        myReceiver = new CalendarReceiver();
        setBroadcast();

        selectedDate = LocalDate.now();
        mPresenter = new CalendarDatabaseImp(this, getBaseContext());
        mPresenter.checkedEvents();
        setMonthView();
        //Log.d("_TAG34",tomorrowDate());
        //notify(tomorrowDate());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(myReceiver);
    }

    void setBroadcast(){
        IntentFilter filter = new IntentFilter();
        filter.addAction(action);
        registerReceiver(myReceiver, filter);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public String tomorrowDate(){
        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plusDays(1);
        return formatter.format(tomorrow);
    }

    //public void notify(String day){
        //mPresenter.searchDayAfter(day);
    //}

    private void initWidgets()
    {
        calendarRecyclerView = findViewById(R.id.calendarRecyclerView);
        monthYearText = findViewById(R.id.monthYearTV);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setMonthView()
    {
        monthYearText.setText(monthYearFromDate(selectedDate));
        ArrayList<String> daysInMonth = daysInMonthArray(selectedDate);

        CalendarAdapter calendarAdapter = new CalendarAdapter(daysInMonth, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private ArrayList<String> daysInMonthArray(LocalDate date)
    {
        ArrayList<String> daysInMonthArray = new ArrayList<>();
        YearMonth yearMonth = YearMonth.from(date);

        int daysInMonth = yearMonth.lengthOfMonth();

        LocalDate firstOfMonth = selectedDate.withDayOfMonth(1);
        int dayOfWeek = firstOfMonth.getDayOfWeek().getValue();

        for(int i = 1; i <= 42; i++)
        {
            if(i <= dayOfWeek || i > daysInMonth + dayOfWeek)
            {
                daysInMonthArray.add("");
            }
            else
            {
                daysInMonthArray.add(String.valueOf(i - dayOfWeek));
            }
        }
        return  daysInMonthArray;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private String monthYearFromDate(LocalDate date)
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy");
        return date.format(formatter);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void previousMonthAction(View view)
    {
        selectedDate = selectedDate.minusMonths(1);
        setMonthView();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void nextMonthAction(View view)
    {
        selectedDate = selectedDate.plusMonths(1);
        setMonthView();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onItemClick(int position, String dayText)
    {
        if(!dayText.equals(""))
        {
            Bundle extras = new Bundle();
            String message = dayText + " " + monthYearFromDate(selectedDate);
            Intent intentScreen = new Intent(this, ScreenCalendarActivity.class);
            extras.putString("date",message);
            intentScreen.putExtras(extras);
            startActivity(intentScreen);

            //Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void showEvents(List<EventViewModel> events) {
    }

    @Override
    public void notifyEvent(List<EventViewModel> events) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMMM yyyy");
        Intent intentN = new Intent(this, CalendarReceiver.class);
        String today = selectedDate.format(formatter);
        send = new ArrayList<>();
        for (EventViewModel a: events) {
            Log.d("_TAGNOTE",today +" "+ getDayNotifyString(a.getDay(), Integer.parseInt(a.getNotificationDay())));
            if(today.equals(getDayNotifyString(a.getDay(), Integer.parseInt(a.getNotificationDay())))) {
                send.add("Event "+ a.getEvent() +" a las " +
                        a.getHour() + "/" + a.getDescription()+ a.getNotificationDay());
            }
        }
        intentN.putExtra("list_event", send);
        intentN.setAction(action);
        getBaseContext().sendBroadcast(intentN);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public String getDayNotifyString(String eventDay, int i){
        LocalDate localDate1 = LocalDate.parse(eventDay, DateTimeFormatter.ofPattern("d MMMM yyyy"));
        localDate1 = localDate1.minusDays(i);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMMM yyyy");
        return formatter.format(localDate1);
    }

    @Override
    public void updateEvent(EventViewModel event, int position) {

    }
}