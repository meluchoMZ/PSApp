package es.udc.psi.agendaly.TimeTable.presenter;

import android.content.Context;
import android.os.AsyncTask;

import androidx.room.Update;

import java.util.ArrayList;
import java.util.List;

import es.udc.psi.agendaly.TimeTable.Asignatura;
import es.udc.psi.agendaly.TimeTable.database.AsignaturaDatabaseClient;
import es.udc.psi.agendaly.TimeTable.viewmodel.AsignaturaViewModel;
import es.udc.psi.agendaly.TimeTable.viewmodel.AsignaturaViewModelMapper;

public class AsignaturaDatabaseImp implements AsignaturaPresenter{

    private Context mContext;
    private AsignaturaView mView;

    private List<AsignaturaViewModel> mAsignaturaViewModels;


    public AsignaturaDatabaseImp(AsignaturaView mView,Context mContex){
        this.mView=mView;
        this.mContext =mContex;

    }


    @Override
    public void searchByDay(String dayWeek){
        new GetAsignaturasbyDay(dayWeek).execute();
    }




    @Override
    public void insert(Asignatura asignatura) {
        insertAsignaturaDB(asignatura);
    }

    @Override
    public void clearAll() {
        clearAll();
    }

    @Override
    public void deleteClass(String asignatura) {
        deleteAsignaturaDB(asignatura);
    }


    @Override
    public void getAll() {
        getAsignaturasBD();
    }

    @Override
    public void notifyDay(String day) {
        new NotifyDay(day).execute();
    }

    @Override
    public void update(String horaNotificacion, int notificar) {
        class Update extends AsyncTask<Void, Void, Void> { // clase interna
            @Override
            public Void doInBackground(Void... voids) {
                    AsignaturaDatabaseClient.getInstance(mContext)
                            .getAsignaturaDatabase()
                            .getAsignaturaDao()
                            .update(horaNotificacion,notificar); // Sustituir por la función necesaria

                return null;
            }

            @Override
            protected void onPostExecute (Void aVoid) {
                super.onPostExecute(aVoid);

            }
        }
        Update gf = new Update(); // Crear una instancia y ejecutar
        gf.execute();
    }

    @Override
    public void getNotificationChecked(String day) {
        class NotificationChecked extends AsyncTask<Void, Void, List<Asignatura>> {

            @Override
            protected List<Asignatura> doInBackground(Void... voids) {
                List<Asignatura> asignaturaList =
                        AsignaturaDatabaseClient.getInstance(mContext)
                                .getAsignaturaDatabase()
                                .getAsignaturaDao()
                                .getNotificationChecked(day,1);
                return asignaturaList;
            }

            @Override
            protected void onPostExecute(List<Asignatura> list) {
                mAsignaturaViewModels= getAsignaturaViewModel(list);
                mView.sendNotification(mAsignaturaViewModels);

            }
        }

        NotificationChecked gf = new NotificationChecked();
        gf.execute();
    }

    private List<AsignaturaViewModel> getAsignaturaViewModel(List<Asignatura> asignaturas) {

        mAsignaturaViewModels = new AsignaturaViewModelMapper(asignaturas).map();
        return mAsignaturaViewModels;
    }


    public void insertAsignaturaDB(Asignatura asignatura) {
        class InsertAsignatura extends AsyncTask<Void, Void, Void> { // clase interna
            @Override
            public Void doInBackground(Void... voids) {
                if(asignatura != null) {
                    AsignaturaDatabaseClient.getInstance(mContext)
                            .getAsignaturaDatabase()
                            .getAsignaturaDao()
                            .insert(asignatura); // Sustituir por la función necesaria
                }
                return null;
            }

            @Override
            protected void onPostExecute (Void aVoid) {
                super.onPostExecute(aVoid);

            }
        }
        InsertAsignatura gf = new InsertAsignatura(); // Crear una instancia y ejecutar
        gf.execute();
    }

    public void deleteAsignaturaDB(String asignatura) {
        class DeleteAsignatura extends AsyncTask<Void, Void, Void> { // clase interna
            @Override
            public Void doInBackground(Void... voids) {
                if(asignatura != null) {
                    AsignaturaDatabaseClient.getInstance(mContext)
                            .getAsignaturaDatabase()
                            .getAsignaturaDao()
                            .delete(asignatura); // Sustituir por la función necesaria
                }
                return null;
            }

            @Override
            protected void onPostExecute (Void aVoid) {
                super.onPostExecute(aVoid);
            }
        }
        DeleteAsignatura gf = new DeleteAsignatura(); // Crear una instancia y ejecutar
        gf.execute();
    }

    public void deleteAllAsignaturasDB() {
        class DeleteAllAsignatura extends AsyncTask<Void, Void, Void> { // clase interna
            @Override
            public Void doInBackground(Void... voids) {

                    AsignaturaDatabaseClient.getInstance(mContext)
                            .getAsignaturaDatabase()
                            .getAsignaturaDao()
                            .deleteAll(); // Sustituir por la función necesaria

                return null;
            }

            @Override
            protected void onPostExecute (Void aVoid) {
                super.onPostExecute(aVoid);
            }
        }
        DeleteAllAsignatura gf = new DeleteAllAsignatura(); // Crear una instancia y ejecutar
        gf.execute();
    }

    public class GetAsignaturasbyDay extends AsyncTask<Void, Void, List<Asignatura>> { // clase interna
            String day;

            public GetAsignaturasbyDay(String day){
                this.day = day;

            }
            @Override
            public List<Asignatura> doInBackground(Void... voids) {
                List<Asignatura> asignaturaList = AsignaturaDatabaseClient.getInstance(mContext)
                        .getAsignaturaDatabase()
                        .getAsignaturaDao()
                        .getDayAsignaturas(day);// Sustituir por la función necesaria
                return asignaturaList;
            }

            @Override
            protected void onPostExecute (List<Asignatura> list) {
                super.onPostExecute(list);
                mAsignaturaViewModels= getAsignaturaViewModel(list);
                mView.showAsignaturas(mAsignaturaViewModels);
            }

    }

    public class NotifyDay extends AsyncTask<Void, Void, List<Asignatura>> { // clase interna
        String day;

        public NotifyDay(String day){
            this.day = day;

        }
        @Override
        public List<Asignatura> doInBackground(Void... voids) {
            List<Asignatura> asignaturaList = AsignaturaDatabaseClient.getInstance(mContext)
                    .getAsignaturaDatabase()
                    .getAsignaturaDao()
                    .getDayAsignaturas(day);// Sustituir por la función necesaria
            return asignaturaList;
        }

        @Override
        protected void onPostExecute (List<Asignatura> list) {
            super.onPostExecute(list);
            mAsignaturaViewModels= getAsignaturaViewModel(list);
            mView.sendNotification(mAsignaturaViewModels);
        }

    }


    private void getAsignaturasBD () {

        class GetAsignaturas extends AsyncTask<Void, Void, List<Asignatura>> {

            private int mPosition;
            GetAsignaturas(int position) {

                mPosition = position;
            }

            @Override
            protected List<Asignatura> doInBackground(Void... voids) {
                List<Asignatura> asignaturaList =
                        AsignaturaDatabaseClient.getInstance(mContext)
                                .getAsignaturaDatabase()
                                .getAsignaturaDao()
                                .getAll();
                return asignaturaList;
            }

            @Override
            protected void onPostExecute(List<Asignatura> result) {
                if(!result.isEmpty()){
                mAsignaturaViewModels = getAsignaturaViewModel(result);
                if(!mAsignaturaViewModels.isEmpty())
                {mView.showAsignaturas(mAsignaturaViewModels);}
                }

            }
        }

    }


}
