package es.udc.psi.agendaly.Calendar.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDate;

@Entity(tableName = "events")
public class Event {

    public Event(){

    }

    public Event(String event, String day, String hour, String description, String notificationDay, int sw){
        this.event=event;
        this.day=day;
        this.hour=hour;
        this.decription=description;
        this.notificationDay = notificationDay;
        this.sw = sw;

        //this.date=selectdate;
    }

    @PrimaryKey(autoGenerate=true)
    @ColumnInfo(name = "idDB")
    public int idBD;

    @ColumnInfo(name = "event")
    String event;

    @ColumnInfo(name = "day")
    String day;

    @ColumnInfo(name = "hour")
    String hour;


    @ColumnInfo(name = "decription")
    String decription;

    @ColumnInfo(name = "notificationDay")
    String notificationDay;

    @ColumnInfo(name = "sw")
    int sw;

    public int getIdBD() {
        return idBD;
    }


    public String getDay() {
        return day;
    }

    public String getEvent() {
        return event;
    }

    public String getHour() {
        return hour;
    }

    public String getDecription() {
        return decription;
    }

    public String getNotificationDay() { return notificationDay;}

    public int getSw(){return sw;}


    public void setEvent(String event) {
        this.event = event;
    }

    public void setDecription(String decription) {
        this.decription = decription;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public void setNotificationDay(String notificationDay) {
        this.notificationDay = notificationDay;
    }

    public void setSw(int sw) {
        this.sw= sw;
    }


}
