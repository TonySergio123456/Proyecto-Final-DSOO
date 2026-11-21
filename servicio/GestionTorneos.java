package servicio;

import java.time.LocalDate;
import modelo.Administrador;
import modelo.Torneo;
import modelo.Videojuego;
import enums.Modalidad;
import enums.FormatoTorneo;

/** Agrupa las acciones de creación y consulta de torneos. */
public class GestionTorneos {
    private final Administrador administrador;

    public GestionTorneos(Administrador administrador) {
        if (administrador == null) throw new IllegalArgumentException("Se necesita un administrador");
        this.administrador = administrador;
    }

    public Torneo crearTorneo(int id, String nombre, LocalDate inicio, LocalDate fin,
                              int cupos, Modalidad modalidad, FormatoTorneo formato, Videojuego videojuego) {
        return administrador.crearTorneo(id, nombre, inicio, fin, cupos, modalidad, formato, videojuego);
    }

    public void cancelarTorneo(Torneo torneo) {
        administrador.eliminarTorneo(torneo);
    }
}
