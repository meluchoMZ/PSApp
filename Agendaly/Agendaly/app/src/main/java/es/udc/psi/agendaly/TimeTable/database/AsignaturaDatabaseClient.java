package es.udc.psi.agendaly.TimeTable.database;

import android.content.Context;

import androidx.room.Room;


public class AsignaturaDatabaseClient {

    private Context mCtx;
    private static AsignaturaDatabaseClient mInstance;
    private AsignaturaDatabase asignaturaDatabase; //our app database object

    private AsignaturaDatabaseClient(Context mCtx) {
        this.mCtx = mCtx;
        //creating the app database with Room database builder
        asignaturaDatabase = Room.databaseBuilder(mCtx,
                AsignaturaDatabase.class, "AsignaturaDatabase.db").build();
        // see also option .allowMainThreadQueries() (NOT recommended)
    }
    public static synchronized AsignaturaDatabaseClient getInstance(Context mCtx) {
        if (mInstance == null)
            mInstance = new AsignaturaDatabaseClient(mCtx);
        return mInstance;
    }

    public AsignaturaDatabase getAsignaturaDatabase() {
        return asignaturaDatabase;
    }


}
