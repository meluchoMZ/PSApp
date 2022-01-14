package es.udc.psi.agendaly.TimeTable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "asignaturas")
public class Asignatura {

    @PrimaryKey(autoGenerate=true)
    @ColumnInfo(name = "idDB")
    public int idBD;


    @ColumnInfo(name = "nombre")
    String nombre;

    @ColumnInfo(name = "aula")
    String aula;

    @ColumnInfo(name = "day")
    String day;

    @ColumnInfo(name = "inicio")
    String inicio;

    @ColumnInfo(name = "fin")
    String fin;

    @ColumnInfo(name = "horaNotificacion")
    String horaNotificacion; //("hh:mm");

    @ColumnInfo(name = "notificar")
    int notificar;


    public Asignatura() {

    }

    public Asignatura(String nombre, String inicio, String fin, String clase, String day) {
        this.nombre = nombre;
        this.inicio=inicio;
        this.fin=fin;
        this.aula = clase;
        this.day = day;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setAula(String aula) {
        this.aula = aula;
    }

    public String getNombre() {
        return nombre;
    }


    public String getAula() {
        return aula;
    }

    public void setInicio(String inicio) {
        this.inicio = inicio;
    }

    public void setFin(String fin) {
        this.fin = fin;
    }

    public String getInicio() {
        return inicio;
    }

    public String getFin() {
        return fin;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public int getIdBD() {
        return idBD;
    }

    public void setHoraNotificacion(String horaNotificacion) {
        this.horaNotificacion = horaNotificacion;
    }

    public void setNotificar(int notificar) {
        this.notificar = notificar;
    }

    public String getHoraNotificacion() {
        return horaNotificacion;
    }

    public int getNotificar() {
        return notificar;
    }
}

