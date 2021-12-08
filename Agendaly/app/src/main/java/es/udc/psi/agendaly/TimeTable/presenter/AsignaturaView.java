package es.udc.psi.agendaly.TimeTable.presenter;

import java.util.List;

import es.udc.psi.agendaly.TimeTable.viewmodel.AsignaturaViewModel;

public interface AsignaturaView {

    void showAsignaturas(List<AsignaturaViewModel> artists);

    void showEmptyView();

    void showError();

    void updateAsignatura(AsignaturaViewModel artist, int position);
}
