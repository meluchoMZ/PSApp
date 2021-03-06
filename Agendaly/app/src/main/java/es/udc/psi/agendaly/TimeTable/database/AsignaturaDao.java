package es.udc.psi.agendaly.TimeTable.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import es.udc.psi.agendaly.Calendar.database.Event;
import es.udc.psi.agendaly.TimeTable.Asignatura;

@Dao
public interface AsignaturaDao {
    // Escritura
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Asignatura... asignaturas);

    @Query("DELETE FROM asignaturas WHERE nombre LIKE :asignatura")
    void delete(String asignatura);

     @Query("SELECT * FROM asignaturas WHERE day LIKE :search" )
     public List<Asignatura> getDayAsignaturas(String search);

    @Query("SELECT * FROM asignaturas" )
    public List<Asignatura> getAll();

     @Query("DELETE FROM asignaturas")
    void deleteAll();

    @Query("UPDATE asignaturas SET horaNotificacion=:horaNotificacion, notificar=:notificar")
    void update(String horaNotificacion, int notificar);

    @Query("SELECT * FROM asignaturas WHERE notificar LIKE :notificar AND day LIKE :day" )
    public List<Asignatura> getNotificationChecked(String day, int notificar);


}
