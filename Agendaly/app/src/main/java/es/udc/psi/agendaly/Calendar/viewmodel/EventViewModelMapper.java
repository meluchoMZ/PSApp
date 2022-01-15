package es.udc.psi.agendaly.Calendar.viewmodel;

import java.util.ArrayList;
import java.util.List;

import es.udc.psi.agendaly.Calendar.database.Event;
import es.udc.psi.agendaly.TimeTable.Asignatura;

public class EventViewModelMapper {

    private List<Event> mEvent;

    public EventViewModelMapper(List<Event> events) {

        mEvent = events;
    }

    public List<EventViewModel> map() {

        List<EventViewModel> events = new ArrayList<>();
        for (Event event : mEvent) {
            events.add(new EventViewModel(event.getEvent(),
                    event.getHour(), event.getDecription(), event.getNotificationDay(),
                    event.getSw(),event.getDay()));
        }
        return events;
    }
}
