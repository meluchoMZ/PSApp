package es.udc.psi.agendaly.TimeTable.presenter;

import java.util.List;

import es.udc.psi.agendaly.TimeTable.viewmodel.AsignaturaViewModel;

public interface AsignaturaView {

    void showAsignaturas(List<AsignaturaViewModel> asignaturas);

    void showEmptyView();

    void showError();

    void updateAsignatura(AsignaturaViewModel asignatura, int position);
}
