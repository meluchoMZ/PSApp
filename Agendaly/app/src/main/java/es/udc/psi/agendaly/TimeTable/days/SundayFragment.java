package es.udc.psi.agendaly.TimeTable.days;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import es.udc.psi.agendaly.R;
import es.udc.psi.agendaly.TimeTable.Asignatura;
import es.udc.psi.agendaly.TimeTable.AsignaturaAdapter;


public class SundayFragment extends Fragment {
    AsignaturaAdapter mAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        inflater.inflate(R.layout.activity_info_fragment, container, false);

        View rootView = inflater.inflate(R.layout.activity_info_fragment, container, false);
        RecyclerView recyclerView = rootView.findViewById(R.id.events_recyclerView);
        ArrayList<Asignatura> initialData = new ArrayList<>();
        Asignatura a = new Asignatura(getString(R.string.sunday),"  ", " ", getString(R.string.sunday));
        initialData.add(a);
        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(rootView.getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new AsignaturaAdapter(initialData);
        recyclerView.setAdapter(mAdapter);

        return rootView;

    }
}