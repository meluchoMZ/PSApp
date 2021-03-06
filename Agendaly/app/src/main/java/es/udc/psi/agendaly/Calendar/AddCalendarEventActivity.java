package es.udc.psi.agendaly.Calendar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

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

    @BindView(R.id.eTDiasAntes)
    EditText editTextNotification;

    @BindView(R.id.add_calendar_sw)
    SwitchCompat switchCompat;

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
    @Override
    public void notifyEvent(List<EventViewModel> events) {

    }

    public void nameEvent(){
        String eT = editTextNameEvent.getText().toString();
        if(!eT.isEmpty()) {
            if(editTextNameEvent.getText().toString().length() != 0 ){
                event.setEvent(eT);
            }
        }
    }

    public void descriptionEvent(){
        String eT = editTextDescription.getText().toString();
        if(!eT.isEmpty()) {event.setDecription(eT);}
    }

    public void hourEvent(){

        String eT = editTextHour.getText().toString();
        if(!eT.isEmpty()) {event.setHour(eT);}
    }

    public void notificationDay(){
        String eT = editTextNotification.getText().toString();
        if(!eT.isEmpty()) {event.setNotificationDay(eT);}
    }

    public void swChecked(){
        Boolean sw = switchCompat.isChecked();
        if(!sw){event.setSw(0);}
        else{event.setSw(1);}
    }


    public void saveEvent(){
        butSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hourEvent();
                descriptionEvent();
                nameEvent();
                notificationDay();
                swChecked();
                if(event !=null) {
                    if(event.getEvent()!=null) {
                        mPresenter.insert(event);
                        Toast.makeText(getBaseContext(), "Evento a??adido", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {Toast.makeText(getBaseContext(), "A??ada nombre", Toast.LENGTH_SHORT).show();};
                }
            }
        });

    }
}