package es.udc.psi.agendaly.TimeTable.presenter;

import es.udc.psi.agendaly.TimeTable.Asignatura;

public interface AsignaturaPresenter {

    void searchByDay(String day);

    void insert(Asignatura asignatura);

    void clearAll();

    void deleteClass(String asignatura);

    void getAll();
}
