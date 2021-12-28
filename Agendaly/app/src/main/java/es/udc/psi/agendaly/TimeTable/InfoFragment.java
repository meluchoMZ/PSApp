package es.udc.psi.agendaly.TimeTable;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import es.udc.psi.agendaly.BaseActivity;
import es.udc.psi.agendaly.R;
import es.udc.psi.agendaly.TimeTable.notifications.MyReceiver;
import es.udc.psi.agendaly.TimeTable.presenter.AsignaturaDatabaseImp;
import es.udc.psi.agendaly.TimeTable.presenter.AsignaturaPresenter;
import es.udc.psi.agendaly.TimeTable.presenter.AsignaturaView;
import es.udc.psi.agendaly.TimeTable.viewmodel.AsignaturaViewModel;

public class InfoFragment extends Fragment implements AsignaturaView {
    private AsignaturaPresenter mPresenter;
    RecyclerView recyclerView;
    View rootView;
    EventAdapter mAdapter;
    String day;

    public boolean done=false;
    String currentDay;

    InfoFragment(String day){
        this.day=day;
    }

    String todaySchedule = "todaySchedule";
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView= inflater.inflate(R.layout.activity_info_fragment, container, false);
        recyclerView= rootView.findViewById(R.id.events_recyclerView);
        swipeRefreshLayout=rootView.findViewById(R.id.swipeLayout);
        mPresenter = new AsignaturaDatabaseImp(this, rootView.getContext());
        currentDay = CurrentDay(Calendar.getInstance().get(Calendar.DAY_OF_WEEK));
        notificar();
        searchByDay(day);
        setUpView();
        refesh();

        return rootView;
    }

    public void refesh(){
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                searchByDay(day);
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    public void searchByDay(String day){
        mPresenter.searchByDay(day);
    }


    public void notificar(){
        Log.d("_TAG", String.valueOf(done));
        if(currentDay.equals(day)){
            if(!done){
                done=true;
                notifyByDay(currentDay);

            }
        }

    }

    public void notifyByDay(String day){
        mPresenter.notifyDay(day);
    }



    @Override
    public void showAsignaturas(List<AsignaturaViewModel> asignaturas) {
        mAdapter.setItems(asignaturas);
        recyclerView.setAdapter(mAdapter);
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

    private void setUpView() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        mAdapter = new EventAdapter();
        recyclerView.setAdapter(mAdapter);
    }


    public String CurrentDay(int nD) {
        String dia = "";
        switch (nD) {
            case 1:
                return getString(R.string.sunday);

            case 2:
                return getString(R.string.monday);

            case 3:
                return getString(R.string.tuesday);


            case 4:
                return getString(R.string.wednesday);


            case 5:
                return getString(R.string.thrusday);

            case 6:
                return getString(R.string.friday);


            case 7:
                return getString(R.string.saturday);


        }
        return dia;
    }





}