package es.udc.psi.agendaly.TimeTable;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.model.CalendarEvent;
import devs.mulham.horizontalcalendar.utils.CalendarEventsPredicate;
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener;
import es.udc.psi.agendaly.Calendar.CalendarActivity;
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
    String dia = "";

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
        SelectDay(defaultSelectedDate.get(Calendar.DAY_OF_WEEK));

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

        return rootView;

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.add_menu,menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);

        int id = item.getItemId();
        if(id==R.id.icon_add){
            Intent intent = new Intent(getActivity(),AddEvent.class);
            startActivity(intent);
        }
        if(id==R.id.icon_notification){
            if (!done){
                item.setIcon(ContextCompat.getDrawable(rootView.getContext(),
                        R.drawable.baseline_notifications_active_white_24));

                done=true;
                notificar();

            }else{
                item.setIcon(ContextCompat.getDrawable(rootView.getContext(),
                        R.drawable.baseline_notifications_white_24));
                done=false;
            }
        }

        if(id==R.id.icon_delete){
            Intent intentdel = new Intent(getActivity(),DeleteEvent.class);
            startActivity(intentdel);
        }
        return true;
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


    @Override
    public void showAsignaturas(List<AsignaturaViewModel> asignaturas) {

    }

    @Override
    public void sendNotification(List<AsignaturaViewModel> asignaturas) {
        Intent intent = new Intent(rootView.getContext(), MyReceiver.class);
        //new AsignaturaViewModel(a.getName(),a.getHora(),a.getAula())
        ArrayList<String> sendName = new ArrayList<>();
        for (AsignaturaViewModel a: asignaturas) {
            sendName.add("Hoy tienes : "+ a.getName() +"/"+" Horario " +
                    a.getHora() + " en el aula " + a.getAula());
        }
        intent.putExtra("asignatura", sendName);
        intent.setAction(todaySchedule);
        rootView.getContext().sendBroadcast(intent);
    }

    public void notificar(){
        if(currentDay.equals(dia)){
                notifyByDay(currentDay);
        }
    }

    public void notifyByDay(String day){
        mPresenter.notifyDay(day);
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

}

