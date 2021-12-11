package es.udc.psi.agendaly.Calendar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.icu.util.Calendar;
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
import es.udc.psi.agendaly.TimeTable.Asignatura;
import es.udc.psi.agendaly.TimeTable.EventAdapter;
import es.udc.psi.agendaly.TimeTable.presenter.AsignaturaDatabaseImp;
import es.udc.psi.agendaly.TimeTable.presenter.AsignaturaPresenter;

public class AddCalendarEventActivity extends BaseActivity implements CalendarView {
    Event event = new Event();
    EventsCalendarAdapter mAdapter;
    CalendarPresenter mPresenter;

    @BindView(R.id.butSave)
    Button butSave;

    @BindView(R.id.editTextNameEvent)
    EditText editTextNameEvent;

    @BindView(R.id.editTextDescription)
    EditText editTextDescription;

    @BindView(R.id.editTextEventHour)
    EditText editTextHour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_calendar_event);
        mPresenter = new CalendarDatabaseImp(this,getBaseContext());

        Bundle parametros = this.getIntent().getExtras();
        if(parametros !=null){
            String date = parametros.getString("date");
            event.setDay(date);
            Log.d("_TAGaDD",date);
        }
        saveEvent();
    }

    @Override
    public void showEvents(List<EventViewModel> events) {
        mAdapter.setItems(events);
    }

    @Override
    public void updateEvent(EventViewModel event, int position) {
        mAdapter.updateItem(event,position);
    }

    public void nameEvent(){
        String eT = editTextNameEvent.getText().toString();
        if(!eT.isEmpty()) {event.setEvent(eT);}
    }

    public void descriptionEvent(){
        String eT = editTextDescription.getText().toString();
        if(!eT.isEmpty()) {event.setDecription(eT);}
    }

    public void hourEvent(){

        String eT = editTextHour.getText().toString();
        if(!eT.isEmpty()) {event.setHour(eT);}
    }

    public void saveEvent(){
        butSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hourEvent();
                descriptionEvent();
                nameEvent();
                if(event !=null) {
                    mPresenter.insert(event);
                    Toast.makeText(getBaseContext(), "Evento añadido", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });

    }
}