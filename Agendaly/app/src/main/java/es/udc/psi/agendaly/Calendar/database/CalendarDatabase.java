package es.udc.psi.agendaly.Calendar.database;
import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import es.udc.psi.agendaly.TimeTable.DateTypeConverter;

@Database(entities = {Event.class},version = 1)
@TypeConverters({DateTypeConverter.class})
public abstract class CalendarDatabase extends RoomDatabase {

    abstract public CalendarDao getCalendarDao();
}
