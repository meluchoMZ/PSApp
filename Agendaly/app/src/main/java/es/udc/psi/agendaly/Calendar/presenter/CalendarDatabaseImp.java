package es.udc.psi.agendaly.Calendar.presenter;

import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

import es.udc.psi.agendaly.Calendar.database.CalendarDatabaseClient;
import es.udc.psi.agendaly.Calendar.database.Event;
import es.udc.psi.agendaly.Calendar.viewmodel.EventViewModel;
import es.udc.psi.agendaly.Calendar.viewmodel.EventViewModelMapper;
import es.udc.psi.agendaly.TimeTable.Asignatura;
import es.udc.psi.agendaly.TimeTable.database.AsignaturaDatabaseClient;
import es.udc.psi.agendaly.TimeTable.viewmodel.AsignaturaViewModel;
import es.udc.psi.agendaly.TimeTable.viewmodel.AsignaturaViewModelMapper;

public class CalendarDatabaseImp implements CalendarPresenter {

    private Context mContext;
    private CalendarView mView;

    private List<EventViewModel> mEventsViewModels;


    public CalendarDatabaseImp(CalendarView mView, Context mContex){
        this.mView=mView;
        this.mContext =mContex;

    }

    @Override
    public void searchByDay(String day) {
        new GetEventsbyDay(day).execute();
    }

    @Override
    public void searchDayAfter(String day) { new GetNotification(day).execute(); }

    @Override
    public void insert(Event event) {
        insertEventaDB(event);
    }

    @Override
    public void clearAll() {
        clearAll();
    }

    @Override
    public void deleteEvent(String event) {
        deleteEventDB(event);
    }

    @Override
    public void getAll() {
        getEventsBD();
    }

    public void insertEventaDB(Event event) {
        class InsertCalendar extends AsyncTask<Void, Void, Void> { // clase interna
            @Override
            public Void doInBackground(Void... voids) {
                if(event != null) {
                    CalendarDatabaseClient.getInstance(mContext)
                            .getCalendarDatabase()
                            .getCalendarDao()
                            .insert(event); // Sustituir por la función necesaria
                }
                return null;
            }

            @Override
            protected void onPostExecute (Void aVoid) {
                super.onPostExecute(aVoid);

            }
        }
        InsertCalendar gf = new InsertCalendar(); // Crear una instancia y ejecutar
        gf.execute();
    }

    public void deleteEventDB(String event) {
        class DeleteEvent extends AsyncTask<Void, Void, Void> { // clase interna
            @Override
            public Void doInBackground(Void... voids) {
                if(event != null) {
                    CalendarDatabaseClient.getInstance(mContext)
                            .getCalendarDatabase()
                            .getCalendarDao()
                            .delete(event); // Sustituir por la función necesaria
                }
                return null;
            }

            @Override
            protected void onPostExecute (Void aVoid) {
                super.onPostExecute(aVoid);
            }
        }
        DeleteEvent gf = new DeleteEvent(); // Crear una instancia y ejecutar
        gf.execute();
    }

    public void deleteAllEventsDB() {
        class DeleteAllEvents extends AsyncTask<Void, Void, Void> { // clase interna
            @Override
            public Void doInBackground(Void... voids) {

                    CalendarDatabaseClient.getInstance(mContext)
                            .getCalendarDatabase()
                            .getCalendarDao()
                            .deleteAll(); // Sustituir por la función necesaria

                return null;
            }

            @Override
            protected void onPostExecute (Void aVoid) {
                super.onPostExecute(aVoid);
            }
        }
        DeleteAllEvents gf = new DeleteAllEvents(); // Crear una instancia y ejecutar
        gf.execute();
    }


    private List<EventViewModel> getEventsViewModel(List<Event> events) {

        mEventsViewModels = new EventViewModelMapper(events).map();
        return mEventsViewModels;
    }


    public class GetEventsbyDay extends AsyncTask<Void, Void, List<Event>> { // clase interna
            String day;

            public GetEventsbyDay(String day){
                this.day = day;

            }
            @Override
            public List<Event> doInBackground(Void... voids) {
                List<Event> eventList = CalendarDatabaseClient.getInstance(mContext)
                        .getCalendarDatabase()
                        .getCalendarDao()
                        .getDayEventbyDay(day);// Sustituir por la función necesaria
                return eventList;
            }

            @Override
            protected void onPostExecute (List<Event> list) {
                super.onPostExecute(list);
                mEventsViewModels=getEventsViewModel(list);
                mView.showEvents(mEventsViewModels);
            }

    }

    public class GetNotification extends AsyncTask<Void, Void, List<Event>> { // clase interna
        String day;

        public GetNotification(String day) {
            this.day = day;
        }

        @Override
        public List<Event> doInBackground(Void... voids) {
            List<Event> eventList = CalendarDatabaseClient.getInstance(mContext)
                    .getCalendarDatabase()
                    .getCalendarDao()
                    .getDayEventbyDay(day);// Sustituir por la función necesaria
            return eventList;
        }

        @Override
        protected void onPostExecute(List<Event> list) {
            super.onPostExecute(list);
            mEventsViewModels = getEventsViewModel(list);
            mView.notifyEvent(mEventsViewModels);
        }
    }



    private void getEventsBD () {
        class GetEvents extends AsyncTask<Void, Void, List<Event>> {
            @Override
            protected List<Event> doInBackground(Void... voids) {
                List<Event> eventList =
                        CalendarDatabaseClient.getInstance(mContext)
                                .getCalendarDatabase()
                                .getCalendarDao()
                                .getAll();
                return eventList;
            }

            @Override
            protected void onPostExecute(List<Event> result) {
                if(!result.isEmpty()){
                    mEventsViewModels = getEventsViewModel(result);
                if(!mEventsViewModels.isEmpty())
                {mView.showEvents(mEventsViewModels);}
                }

            }
        }
        GetEvents gf = new GetEvents(); // Crear una instancia y ejecutar
        gf.execute();
    }

}
