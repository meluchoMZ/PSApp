package es.udc.psi.agendaly.TimeTable.viewmodel;

import java.util.ArrayList;
import java.util.List;
import es.udc.psi.agendaly.TimeTable.Asignatura;

public class AsignaturaViewModelMapper {

    private List<Asignatura> mAsignatura;

    public AsignaturaViewModelMapper(List<Asignatura> asignaturas) {

        mAsignatura = asignaturas;
    }

    public List<AsignaturaViewModel> map() {

        List<AsignaturaViewModel> asignaturas = new ArrayList<>();
        for (Asignatura asignatura : mAsignatura) {
            asignaturas.add(new AsignaturaViewModel(asignatura.getNombre(),
                    asignatura.getInicio()+" - "+asignatura.getFin(),asignatura.getAula(),
                    asignatura.getHoraNotificacion()));
        }
        return asignaturas;
    }
}
