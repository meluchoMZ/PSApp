package es.udc.psi.agendaly.Calendar.database;

import android.content.Context;

import androidx.room.Room;


public class CalendarDatabaseClient {

    private Context mCtx;
    private static CalendarDatabaseClient mInstance;
    private CalendarDatabase calendarDatabase; //our app database object

    private CalendarDatabaseClient(Context mCtx) {
        this.mCtx = mCtx;
        //creating the app database with Room database builder
        calendarDatabase = Room.databaseBuilder(mCtx,
                CalendarDatabase.class, "CalendarDatabase.db").build();
        // see also option .allowMainThreadQueries() (NOT recommended)
    }
    public static synchronized CalendarDatabaseClient getInstance(Context mCtx) {
        if (mInstance == null)
            mInstance = new CalendarDatabaseClient(mCtx);
        return mInstance;
    }

    public CalendarDatabase getCalendarDatabase() {
        return calendarDatabase;
    }


}
