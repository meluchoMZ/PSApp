package es.udc.psi.agendaly.Calendar.viewmodel;

public class EventViewModel {


    private String event, hour, description, notificationDay, day;
    private int sw;
    public EventViewModel(String event,
                          String hour, String description, String notificationDay, int sw, String day) {


        this.event = event;
        this.hour=hour;
        this.description=description;
        this.notificationDay = notificationDay;
        this.sw=sw;
        this.day=day;
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

    public String getNotificationDay() {
        return notificationDay;
    }

    public int getSw() {
        return sw;
    }

    public void setSw(int sw) {
        this.sw = sw;
    }

    public String getDay() {
        return day;
    }
}
