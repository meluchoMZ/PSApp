package es.udc.psi.agendaly.Calendar.viewmodel;

public class EventViewModel {


    private String event, hour, description;

    public EventViewModel(String event,
                          String hour, String description) {


        this.event = event;
        this.hour=hour;
        this.description=description;
    }


    public String getEvent() {
        return event;
    }

    public String getHour() {
        return hour;
    }


    public String getDescription() {
        return description;
    }
}
