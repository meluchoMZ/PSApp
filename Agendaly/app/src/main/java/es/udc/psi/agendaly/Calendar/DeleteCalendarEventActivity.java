package es.udc.psi.agendaly.Calendar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import es.udc.psi.agendaly.BaseActivity;
import es.udc.psi.agendaly.Calendar.database.Event;
import es.udc.psi.agendaly.Calendar.presenter.CalendarDatabaseImp;
import es.udc.psi.agendaly.Calendar.presenter.CalendarPresenter;
import es.udc.psi.agendaly.Calendar.presenter.CalendarView;
import es.udc.psi.agendaly.Calendar.viewmodel.EventViewModel;
import es.udc.psi.agendaly.R;

public class DeleteCalendarEventActivity extends BaseActivity implements CalendarView {
    String event;
    EventsCalendarAdapter mAdapter;
    CalendarPresenter mPresenter;

    @BindView(R.id.butDelete)
    Button butDelete;

    @BindView(R.id.editTextNameEventDelete)
    EditText editTextNameEventDelete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_calendar_event);

        mPresenter = new CalendarDatabaseImp(this,getBaseContext());
        deleteEvent();

    }

    @Override
    public void showEvents(List<EventViewModel> events) {
        mAdapter.setItems(events);
    }

    @Override
    public void updateEvent(EventViewModel event, int position) {
        mAdapter.updateItem(event,position);
    }

    @Override
    public void notifyEvent(List<EventViewModel> events) {

    }

    public void nameEvent(){
        String eT = editTextNameEventDelete.getText().toString();
        if(!eT.isEmpty()) {event=eT;}
    }

    public void deleteEvent(){
        butDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nameEvent();
                if(event !=null) {
                    mPresenter.deleteEvent(event);
                    Toast.makeText(getBaseContext(), "Evento borrado", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });

    }
}