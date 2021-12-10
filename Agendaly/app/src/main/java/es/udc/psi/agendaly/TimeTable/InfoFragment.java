package es.udc.psi.agendaly.TimeTable;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import es.udc.psi.agendaly.R;
import es.udc.psi.agendaly.TimeTable.presenter.AsignaturaDatabaseImp;
import es.udc.psi.agendaly.TimeTable.presenter.AsignaturaPresenter;
import es.udc.psi.agendaly.TimeTable.presenter.AsignaturaView;
import es.udc.psi.agendaly.TimeTable.viewmodel.AsignaturaViewModel;

public class InfoFragment extends Fragment implements AsignaturaView {
    AsignaturaView mView;
    private AsignaturaPresenter mPresenter;
    RecyclerView recyclerView;
    View rootView;
    EventAdapter mAdapter;
    String day;
    InfoFragment(String day){
        this.day=day;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView= inflater.inflate(R.layout.activity_info_fragment, container, false);
        recyclerView= rootView.findViewById(R.id.events_recyclerView);
        mPresenter = new AsignaturaDatabaseImp(this, rootView.getContext());
        setUpView();
        searchbyName(day);
        return rootView;
    }

    public void searchbyName(String day){
        mPresenter.initFlow(day);
    }


    @Override
    public void showAsignaturas(List<AsignaturaViewModel> artists) {
        mAdapter.setItems(artists);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void showEmptyView() {

    }

    @Override
    public void showError() {

    }

    @Override
    public void updateAsignatura(AsignaturaViewModel artist, int position) {

    }

    private void setUpView() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        mAdapter = new EventAdapter();
        recyclerView.setAdapter(mAdapter);
    }
}