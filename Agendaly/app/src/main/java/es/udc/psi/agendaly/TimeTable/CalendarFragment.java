package es.udc.psi.agendaly.TimeTable;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.model.CalendarEvent;
import devs.mulham.horizontalcalendar.utils.CalendarEventsPredicate;
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener;
import es.udc.psi.agendaly.Calendar.CalendarActivity;
import es.udc.psi.agendaly.MainActivity;
import es.udc.psi.agendaly.Profiles.ProfileActivity;
import es.udc.psi.agendaly.R;
import es.udc.psi.agendaly.TimeTable.notifications.MyReceiver;
import es.udc.psi.agendaly.TimeTable.presenter.AsignaturaDatabaseImp;
import es.udc.psi.agendaly.TimeTable.presenter.AsignaturaPresenter;
import es.udc.psi.agendaly.TimeTable.presenter.AsignaturaView;
import es.udc.psi.agendaly.TimeTable.viewmodel.AsignaturaViewModel;


public class CalendarFragment extends Fragment implements AsignaturaView {

    View rootView;
    private boolean done=false;
    private AsignaturaPresenter mPresenter;
    String todaySchedule = "todaySchedule";
    String currentDay;
    boolean notificar;

    String dia = "";
    SimpleDateFormat timeformat=new SimpleDateFormat("HH:mm");
    ArrayList<String> sendName;
    String horaNotificacion="";

    private static final int ALARM_REQUEST_CODE = 133;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_calendar, container, false);
        mPresenter = new AsignaturaDatabaseImp(this, rootView.getContext());
        setHasOptionsMenu(true);
        /* starts before 1 month from now */
        Calendar startDate = Calendar.getInstance();
        startDate.add(Calendar.MONTH, -1);
        /* ends after 1 month from now */
        Calendar endDate = Calendar.getInstance();
        endDate.add(Calendar.MONTH, 1);
        // Default Date set to Today.
        final Calendar defaultSelectedDate = Calendar.getInstance();
        //se guarda el dia en el string dia
        SelectDay(defaultSelectedDate.get(Calendar.DAY_OF_WEEK));
        //se guarda el dia actual
        currentDay = CurrentDay(Calendar.getInstance().get(Calendar.DAY_OF_WEEK));



        HorizontalCalendar horizontalCalendar = new HorizontalCalendar.Builder(rootView, R.id.calendarView)
                .range(startDate, endDate)
                .datesNumberOnScreen(5)
                .configure()
                .formatTopText("MMM")
                .formatMiddleText("dd")
                .formatBottomText("EEE")
                .showTopText(true)
                .showBottomText(true)
                .textColor(Color.LTGRAY, Color.BLUE)
                .colorTextMiddle(Color.LTGRAY, Color.parseColor("#ffd54f"))
                .end()
                .defaultSelectedDate(defaultSelectedDate)
                .addEvents(new CalendarEventsPredicate() {
                     @Override
                     public List<CalendarEvent> events(Calendar date) {
                         return new ArrayList<>();
                     }
                 })
                .build();



        horizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {
            @Override
            public void onDateSelected(Calendar date, int position) {
                int nD=date.get(Calendar.DAY_OF_WEEK);
                SelectDay(nD);
            }

            @Override
            public boolean onDateLongClicked(Calendar date, int position) {
                Intent intent = new Intent(getContext(), CalendarActivity.class);
                startActivity(intent);
                return true;
            }

        });

        //repetir la alarma programada
        if(horaNotificacion.equals("")) {
            mPresenter.searchByDay(currentDay);
        }
        notificar();
        ejecutar();
        return rootView;

    }

    public PendingIntent pendingIntent(){
        Intent alarmIntent = new Intent(getContext(), MyReceiver.class);
        alarmIntent.putExtra("asignatura", sendName);
        alarmIntent.setAction(todaySchedule);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(),
                ALARM_REQUEST_CODE, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        return pendingIntent;
    }

    //checkeamos actualizaciones
    private void ejecutar() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                notificar();
                if(notificar){
                    establecerAlarmaClick(getHorarioNotificacionCalendar(),true,false);
                    mPresenter.getNotificationChecked(currentDay);
                    establecerAlarmaClick(getHorarioNotificacionCalendar(),false,false);
                }
                handler.postDelayed(this, 30000);//se ejecutara cada minuto
            }
        }, 100);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.add_menu,menu);
        MenuItem item = menu.findItem(R.id.icon_notification);
        if(notificar){
            item.setIcon(ContextCompat.getDrawable(rootView.getContext(),
                    R.drawable.baseline_notifications_active_white_24));
        }else{
            item.setIcon(ContextCompat.getDrawable(rootView.getContext(),
                    R.drawable.baseline_notifications_white_24));
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        int id = item.getItemId();
        if(id==R.id.icon_add){
            Intent intent = new Intent(getActivity(),AddEvent.class);
            startActivity(intent);
        }
        //alarmas acyivadas o desactivadas
        if(id==R.id.icon_notification){
            if (notificar && !done){

                item.setIcon(ContextCompat.getDrawable(rootView.getContext(),
                        R.drawable.baseline_notifications_white_24));

                mPresenter.update(horarioFormat(),0);
                notificar();
                establecerAlarmaClick(getHorarioNotificacionCalendar(), true, true);
                Toast.makeText(getContext(), getString(R.string.notificaciones_desactivadas)
                        , Toast.LENGTH_SHORT).show();
                done=true;

            }else{
                mPresenter.update(horarioFormat(),1);
                notificar();
                item.setIcon(ContextCompat.getDrawable(rootView.getContext(),
                        R.drawable.baseline_notifications_active_white_24));
                Toast.makeText(getContext(), getString(R.string.notificaciones_activadas)
                        , Toast.LENGTH_SHORT).show();
                done=false;
            }

        }
        //alarma a la hora estipulada por el usuario
        if(id==R.id.icon_picker_hour){
            picker();
            mPresenter.update(horarioFormat(),1);
            notificar();
        }

        if(id==R.id.icon_delete){
            Intent intentdel = new Intent(getActivity(),DeleteEvent.class);
            startActivity(intentdel);
        }
        return true;
    }

    //elegimos la hora que queremos que nos notifique
    public void picker(){
        //("HH:mm");
        final Calendar getDate = Calendar.getInstance();
        TimePickerDialog.OnTimeSetListener onTimeSetListener =
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        horaNotificacion= hourOfDay+":"+ minute;
                        if(!done) {
                            mPresenter.update(horarioFormat(), 1);
                            notificar();
                            establecerAlarmaClick(getHorarioNotificacionCalendar(),
                                    false,true);
                        }else{
                            Toast.makeText(getContext(), getString(R.string.warning_activa_notificaciones),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }

                };
        TimePickerDialog timePickerDialog = new TimePickerDialog(
                getContext(), onTimeSetListener,
                getDate.get(Calendar.HOUR_OF_DAY),
                getDate.get(Calendar.MINUTE), false
        );

        timePickerDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                getDate.clear();
                getDate.set(Calendar.HOUR_OF_DAY, 0);
                getDate.set(Calendar.MINUTE, 0);
                notificar();
                establecerAlarmaClick(getDate,true, true);
            }
        });

        timePickerDialog.show();

    }


    public String horarioFormat(){
        String[] parts = horaNotificacion.split(":");
        String hora = parts[0];
        if(hora.length()<2){
            hora= "0"+hora;
        }
        String minuto = parts[1];
        if(minuto.length()<2){
            minuto= "0"+minuto;
        }
        horaNotificacion = hora+":"+minuto;
        return horaNotificacion;
    }

    public Calendar getHorarioNotificacionCalendar(){
        horaNotificacion = horarioFormat();
        String[] parts = horaNotificacion.split(":");
        String hora = parts[0];
        String minuto = parts[1];
        final Calendar getDate = Calendar.getInstance();
        getDate.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hora));
        getDate.set(Calendar.MINUTE, Integer.parseInt(minuto));
        getDate.set(Calendar.SECOND, 00);
        return getDate;
    }

    private void establecerAlarmaClick(Calendar calendar,boolean cancel, boolean show){
        if(sendName!=null) {
            AlarmManager alarmManager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
            long time = calendar.getTimeInMillis();
            if(time < Calendar.getInstance().getTimeInMillis())
            {
                time= time + System.currentTimeMillis();
            }

            if (!cancel) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, time, pendingIntent());
                } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    alarmManager.setExact(AlarmManager.RTC_WAKEUP, time, pendingIntent());
                } else {
                    alarmManager.set(AlarmManager.RTC_WAKEUP, time, pendingIntent());
                }

                if(show){Toast.makeText(getContext(), getString(R.string.notificacacion_a_las) + " "+
                        timeformat.format(calendar.getTime()), Toast.LENGTH_SHORT).show();}
            } else {
                alarmManager.cancel(pendingIntent());
                if(show){Toast.makeText(getContext(),
                        getString(R.string.alarma_cancelada), Toast.LENGTH_SHORT).show();}
            }
        }else {
            Log.d("_TAGSET", "No hay asignaturas");
            }

    }



    public void notificar(){
        if(currentDay.equals(dia)){
            mPresenter.getNotificationChecked(currentDay);
        }
    }

    public void SelectDay(int nD){
        switch (nD) {
            case 1:
                dia=getString(R.string.sunday);
                break;

            case 2: dia=getString(R.string.monday);
                break;
            case 3:dia=getString(R.string.tuesday);
                break;

            case 4:dia=getString(R.string.wednesday);
                break;

            case 5:dia=getString(R.string.thrusday);
                break;
            case 6: dia=getString(R.string.friday);
                break;

            case 7: dia=getString(R.string.saturday);
                break;

        }
        InfoFragment fragment = new InfoFragment(dia);
        assert(getActivity()!=null);
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.punto_anclaje_abajo, fragment)
                .addToBackStack(null)
                .commit();
    }



    public String CurrentDay(int nD) {
        String dia = "";
        switch (nD) {
            case 1:
                return getString(R.string.sunday);

            case 2:
                return getString(R.string.monday);

            case 3:
                return getString(R.string.tuesday);


            case 4:
                return getString(R.string.wednesday);


            case 5:
                return getString(R.string.thrusday);

            case 6:
                return getString(R.string.friday);


            case 7:
                return getString(R.string.saturday);


        }
        return dia;
    }

    @Override
    public void showAsignaturas(List<AsignaturaViewModel> asignaturas) {
        if(!asignaturas.isEmpty()) {
            if(horaNotificacion.equals("")){
            horaNotificacion= asignaturas.get(0).getNotificationHour();
                notificar();
                if(sendName!=null)establecerAlarmaClick(getHorarioNotificacionCalendar(), false, true);
            }
        }
    }

    @Override
    public void sendNotification(List<AsignaturaViewModel> asignaturas) {
        //todas las de ese día van a tener la misma hora
        if(!asignaturas.isEmpty()) {
            horaNotificacion= asignaturas.get(0).getNotificationHour();
            notificar=asignaturas.get(0).isNotificar();

        }
        sendName = new ArrayList<>();
        for (AsignaturaViewModel a: asignaturas) {
            sendName.add(getString(R.string.hoy_tienes) +" "+ a.getName() +"/"+getString(R.string.horario_noti) +
                    " "+ a.getHora() +  " "+ getString(R.string.en_el_aula) + a.getAula());

        }

    }

}

