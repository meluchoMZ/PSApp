package es.udc.psi.agendaly.Calendar.presenter;

import es.udc.psi.agendaly.Calendar.database.Event;
import es.udc.psi.agendaly.TimeTable.Asignatura;

public interface CalendarPresenter {

    void searchByDay(String day);

    void searchDayAfter(String day);

    void insert(Event event);

    void clearAll();

    void deleteEvent(String event);

    void getAll();

    void checkedEvents();
}
