package es.udc.psi.agendaly.TimeTable;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import es.udc.psi.agendaly.R;

public class Horario extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horario);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        CalendarFragment fragmentCalendar = new CalendarFragment();
        InfoFragment infoFragment = new InfoFragment();

        fragmentTransaction.replace(R.id.punto_anclaje, fragmentCalendar);
        fragmentTransaction.replace(R.id.punto_anclaje_abajo, infoFragment);
        fragmentTransaction.commit();
    }



}