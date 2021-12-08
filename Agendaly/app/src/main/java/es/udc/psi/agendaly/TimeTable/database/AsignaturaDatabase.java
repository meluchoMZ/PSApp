package es.udc.psi.agendaly.TimeTable.database;
import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import es.udc.psi.agendaly.TimeTable.Asignatura;
import es.udc.psi.agendaly.TimeTable.DateTypeConverter;

@Database(entities = {Asignatura.class},version = 1)
@TypeConverters({DateTypeConverter.class})
public abstract class AsignaturaDatabase extends RoomDatabase {

    abstract public AsignaturaDao getAsignaturaDao();
}
