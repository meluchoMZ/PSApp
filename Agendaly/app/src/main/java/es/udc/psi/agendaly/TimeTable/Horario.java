package es.udc.psi.agendaly.TimeTable;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import butterknife.BindView;
import es.udc.psi.agendaly.BaseActivity;
import es.udc.psi.agendaly.Calendar.CalendarActivity;
import es.udc.psi.agendaly.R;
import es.udc.psi.agendaly.TimeTable.notifications.MyReceiver;

public class Horario extends BaseActivity {
    String todaySchedule = "todaySchedule";
    MyReceiver myReceiver;

    @BindView(R.id.bottomnav)
    BottomNavigationView  bm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horario);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        CalendarFragment fragmentCalendar = new CalendarFragment();
        InfoFragment infoFragment = new InfoFragment("");
        fragmentTransaction.replace(R.id.punto_anclaje, fragmentCalendar);
        fragmentTransaction.replace(R.id.punto_anclaje_abajo, infoFragment);
        fragmentTransaction.commit();

        myReceiver = new MyReceiver();
        setBroadcast();

        bm.setSelectedItemId(R.id.inicioAppBar);

        bm.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.inicioAppBar:
                        //Intent intentInicio = new Intent(getBaseContext(), Horario.class);
                        //startActivity(intentInicio);
                        break;
                    case R.id.calendarAppBar:
                        Intent intentCalendar = new Intent(getBaseContext(), CalendarActivity.class);
                        startActivity(intentCalendar);
                        break;
                    case R.id.infoAppBar:

                        break;
                }
                return true;
            }
        });



    }
    void setBroadcast(){
        IntentFilter filter = new IntentFilter();
        filter.addAction(todaySchedule);
        registerReceiver(myReceiver, filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(myReceiver);
    }

    @Override
    public void onBackPressed() {
    	finishAffinity();
    }





}