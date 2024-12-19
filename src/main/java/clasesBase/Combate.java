package clasesBase;

import java.time.LocalDate;

public class Combate {

    private LocalDate fecha;
    private long id;
    private int idTorneo;


    public Combate(LocalDate fecha, int idTorneo) {
        this.fecha = fecha;
        this.idTorneo = idTorneo;

    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public int getIdTorneo() {
        return idTorneo;
    }

    public void setIdTorneo(int idTorneo) {
        this.idTorneo = idTorneo;
    }
}
