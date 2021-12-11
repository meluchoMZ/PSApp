package es.udc.psi.agendaly.TimeTable;

import android.os.Bundle;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import butterknife.BindView;
import es.udc.psi.agendaly.BaseActivity;
import es.udc.psi.agendaly.R;
import es.udc.psi.agendaly.TimeTable.presenter.AsignaturaPresenter;
import es.udc.psi.agendaly.TimeTable.presenter.AsignaturaView;
import es.udc.psi.agendaly.TimeTable.viewmodel.AsignaturaViewModel;

public class Horario extends BaseActivity {


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

    }

    @Override
    public void onBackPressed() {
    	finishAffinity();
    }

}