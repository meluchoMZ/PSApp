package es.udc.psi.agendaly.Calendar.presenter;

import java.util.List;

import es.udc.psi.agendaly.Calendar.database.Event;
import es.udc.psi.agendaly.Calendar.viewmodel.EventViewModel;
import es.udc.psi.agendaly.TimeTable.viewmodel.AsignaturaViewModel;

public interface CalendarView {

    void showEvents(List<EventViewModel> events);

    void updateEvent(EventViewModel event, int position);

    void notifyEvent(List<EventViewModel> events);
}
