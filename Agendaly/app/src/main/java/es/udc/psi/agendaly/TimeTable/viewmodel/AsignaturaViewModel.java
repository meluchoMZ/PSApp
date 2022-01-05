package es.udc.psi.agendaly.TimeTable.viewmodel;

import android.os.Parcel;
import android.os.Parcelable;

public class AsignaturaViewModel implements Parcelable {


    private String name,aula,hora,notificationHour;

    public AsignaturaViewModel(String name,
                               String hora,String aula, String notificationHour) {


        this.name = name;
        this.hora=hora;
        this.aula = aula;
        this.notificationHour=notificationHour;
    }

    public AsignaturaViewModel(Parcel in){
        this.name=in.readString();
        this.hora=in.readString();
        this.aula=in.readString();
    }

    public String getName() { return name; }

    public String getHora() {
        return hora;
    }

    public String getAula() {
        return aula;
    }

    public String getNotificationHour() {
        return notificationHour;
    }

    public void setNotificationHour(String notificationHour) {
        this.notificationHour = notificationHour;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int i) {
        dest.writeString(this.name);
        dest.writeString(this.hora);
        dest.writeString(this.aula);
    }

    @Override
    public String toString() {
        return "Hoy tienes: " +
                "" + name + '\'' +
                ", a las '" + hora + '\'' +
                ", en el aula '" + aula + '\'' +
                ' ';
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public AsignaturaViewModel createFromParcel(Parcel in) {
            return new AsignaturaViewModel(in);
        }

        public AsignaturaViewModel[] newArray(int size) {
            return new AsignaturaViewModel[size];
        }
    };
}
