package es.udc.psi.agendaly.Calendar.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import es.udc.psi.agendaly.TimeTable.Asignatura;

@Dao
public interface CalendarDao {
    // Escritura
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Event... events);

    @Query("DELETE FROM events WHERE event LIKE :evento")
    void delete(String evento);

     @Query("SELECT * FROM events WHERE day LIKE :day" )
     public List<Event> getDayEventbyDay(String day);

    @Query("SELECT * FROM events WHERE sw LIKE :sw" )
    public List<Event> getCheckedEvent(int sw);

    @Query("SELECT * FROM events" )
    public List<Event> getAll();

     @Query("DELETE FROM events")
    void deleteAll();

   /*
    // Consultas
    @Query("SELECT * FROM artists" )
    public List<Artist> getAllArtists();

    @Query("SELECT DISTINCT * FROM artists WHERE name LIKE :name" )
    public List<Artist> getArtist(String name);*/
}
