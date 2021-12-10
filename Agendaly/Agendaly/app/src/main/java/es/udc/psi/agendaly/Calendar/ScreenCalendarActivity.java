package es.udc.psi.agendaly.Calendar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

public class ScreenCalendarActivity extends BaseActivity implements CalendarView {
    EventsCalendarAdapter mAdapter;
    String day;
    @BindView(R.id.calendarRecyclerView)
    RecyclerView recyclerView;

    CalendarPresenter mPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_calendar);
        mPresenter = new CalendarDatabaseImp(this, getBaseContext());
        Bundle parametros = this.getIntent().getExtras();
        if(parametros !=null){
            String date = parametros.getString("date");
            Log.d("_TAG",date);
            day=date;
            search(date);
        }


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        mAdapter = new EventsCalendarAdapter();
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
    public void updateEvent(EventViewModel event, int position) {

    }
}
