package es.udc.psi.agendaly.TimeTable;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.model.CalendarEvent;
import devs.mulham.horizontalcalendar.utils.CalendarEventsPredicate;
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener;
import es.udc.psi.agendaly.R;
import es.udc.psi.agendaly.TimeTable.days.FridayFragment;
import es.udc.psi.agendaly.TimeTable.days.MondayFragment;
import es.udc.psi.agendaly.TimeTable.days.SaturdayFragment;
import es.udc.psi.agendaly.TimeTable.days.SundayFragment;
import es.udc.psi.agendaly.TimeTable.days.ThursdayFragment;
import es.udc.psi.agendaly.TimeTable.days.TuesdayFragment;
import es.udc.psi.agendaly.TimeTable.days.WednesdayFragment;


public class CalendarFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_calendar, container, false);

        /* starts before 1 month from now */
        Calendar startDate = Calendar.getInstance();
        startDate.add(Calendar.MONTH, -1);
        /* ends after 1 month from now */
        Calendar endDate = Calendar.getInstance();
        endDate.add(Calendar.MONTH, 1);
        // Default Date set to Today.
        final Calendar defaultSelectedDate = Calendar.getInstance();
        SelectDay(defaultSelectedDate.get(Calendar.DAY_OF_WEEK));

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
                return true;
            }

        });

        return rootView;

    }

    public void SelectDay(int nD){
        switch (nD) {

            case 1: SundayFragment fr1 = new SundayFragment();
            assert(getActivity()!=null);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.punto_anclaje_abajo, fr1)
                        .addToBackStack(null)
                        .commit();
                break;

            case 2: MondayFragment fr2 = new MondayFragment();
                assert(getActivity()!=null);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.punto_anclaje_abajo, fr2)
                        .addToBackStack(null)
                        .commit();
                break;
            case 3: TuesdayFragment fr3 = new TuesdayFragment();
                assert(getActivity()!=null);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.punto_anclaje_abajo, fr3)
                        .addToBackStack(null)
                        .commit();
                break;

            case 4: WednesdayFragment fr4 = new WednesdayFragment();
                assert(getActivity()!=null);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.punto_anclaje_abajo, fr4)
                        .addToBackStack(null)
                        .commit();
                break;

            case 5: ThursdayFragment fr5 = new ThursdayFragment();
                assert(getActivity()!=null);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.punto_anclaje_abajo, fr5)
                        .addToBackStack(null)
                        .commit();
                break;
            case 6: FridayFragment fr6 = new FridayFragment();
                assert(getActivity()!=null);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.punto_anclaje_abajo, fr6)
                        .addToBackStack(null)
                        .commit();
                break;

            case 7: SaturdayFragment fr7 = new SaturdayFragment();
                assert(getActivity()!=null);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.punto_anclaje_abajo, fr7)
                        .addToBackStack(null)
                        .commit();
                break;

        }
    }

}

