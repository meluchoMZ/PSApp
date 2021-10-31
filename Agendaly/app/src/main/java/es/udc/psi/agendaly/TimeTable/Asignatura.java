package es.udc.psi.agendaly.TimeTable;

import android.os.Parcel;
import android.os.Parcelable;

public class Asignatura implements Parcelable{
    String nombre, horario, clase, day;

    public Asignatura(String nombre, String horario, String clase, String day) {
        this.nombre = nombre;
        this.horario = horario;
        this.clase = clase;
        this.day = day;
    }
    protected Asignatura(Parcel in) {
        nombre = in.readString();
        horario = in.readString();
        clase = in.readString();
        day = in.readString();
    }

    public static final Creator<Asignatura> CREATOR = new Creator<Asignatura>() {
        @Override
        public Asignatura createFromParcel(Parcel in) {
            return new Asignatura(in);
        }

        @Override
        public Asignatura[] newArray(int size) {
            return new Asignatura[size];
        }
    };

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public void setClase(String clase) {
        this.clase = clase;
    }

    public String getNombre() {
        return nombre;
    }

    public String getHorario() {
        return horario;
    }

    public String getClase() {
        return clase;
    }


    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.nombre);
        dest.writeString(this.horario);
        dest.writeString(this.clase);
        dest.writeString(this.day);
    }
}
