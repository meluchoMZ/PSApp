package es.udc.psi.agendaly.Calendar.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDate;

@Entity(tableName = "events")
public class Event {

    public Event(){

    }

    public Event(String event, String day, String hour, String description){
        this.event=event;
        this.day=day;
        this.hour=hour;
        this.decription=description;
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


}
