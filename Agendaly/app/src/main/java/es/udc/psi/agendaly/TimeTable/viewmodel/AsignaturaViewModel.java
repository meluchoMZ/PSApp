package es.udc.psi.agendaly.TimeTable.viewmodel;

public class AsignaturaViewModel {


    private String name,aula,hora;

    public AsignaturaViewModel(String name,
                               String hora,String aula) {


        this.name = name;
        this.hora=hora;
        this.aula = aula;
    }


    public String getName() {

        return name;
    }


    public String getHora() {
        return hora;
    }

    public String getAula() {
        return aula;
    }
}
