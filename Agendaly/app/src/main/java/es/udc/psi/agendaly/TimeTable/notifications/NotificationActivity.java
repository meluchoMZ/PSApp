/*
package es.udc.psi.agendaly.TimeTable.notifications;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import es.udc.psi.agendaly.R;
import es.udc.psi.agendaly.TimeTable.presenter.AsignaturaDatabaseImp;
import es.udc.psi.agendaly.TimeTable.presenter.AsignaturaPresenter;
import es.udc.psi.agendaly.TimeTable.presenter.AsignaturaView;
import es.udc.psi.agendaly.TimeTable.viewmodel.AsignaturaViewModel;


public class NotificationActivity implements AsignaturaView {

    String todaySchedule = "todaySchedule";
    View rootView;
    Context context;
    boolean done=false;
    String currentDay = "";

    AsignaturaPresenter mPresenter;

    public NotificationActivity(View rootView, Context context){
        this.rootView=rootView;
        this.context=context;
    }


    public void notificar(){
        Log.d("_TAG", String.valueOf(done));

        mPresenter = new AsignaturaDatabaseImp(this, rootView.getContext());
        if(!currentDay.equals(CurrentDay(Calendar.getInstance().get(Calendar.DAY_OF_WEEK)))){
            done=false;
        }
        currentDay = CurrentDay(Calendar.getInstance().get(Calendar.DAY_OF_WEEK));
        Log.d("_TAG",currentDay +" h "+ CurrentDay(Calendar.getInstance().get(Calendar.DAY_OF_WEEK)));
        if(!done){done=true; notifyByDay(currentDay); }

    }

    @Override
    public void showAsignaturas(List<AsignaturaViewModel> asignaturas) {

    }

    public void notifyByDay(String day){
        mPresenter.notifyDay(day);
    }

    @Override
    public void sendNotification(List<AsignaturaViewModel> asignaturas) {
        Intent intent = new Intent(rootView.getContext(), MyReceiver.class);
        //new AsignaturaViewModel(a.getName(),a.getHora(),a.getAula())
        ArrayList<String> sendName = new ArrayList<>();
        for (AsignaturaViewModel a: asignaturas) {
            sendName.add("Hoy tienes : "+ a.getName() +"/"+" Horario " +
                    a.getHora() + " en el aula " + a.getAula());
        }
        intent.putExtra("asignatura", sendName);
        intent.setAction(todaySchedule);
        rootView.getContext().sendBroadcast(intent);

    }

    @Override
    public void showError() {

    }

    @Override
    public void updateAsignatura(AsignaturaViewModel asignatura, int position) {

    }

    public String CurrentDay(int nD) {
        String dia = "";
        switch (nD) {
            case 1:
                return context.getString(R.string.sunday);

            case 2:
                return context.getString(R.string.monday);

            case 3:
                return context.getString(R.string.tuesday);


            case 4:
                return context.getString(R.string.wednesday);


            case 5:
                return context.getString(R.string.thrusday);

            case 6:
                return context.getString(R.string.friday);


            case 7:
                return context.getString(R.string.saturday);


        }
        return dia;
    }
}
*/
