package servicio;

import modelo.Administrador;
import modelo.Inscripcion;
import modelo.Torneo;

/** Agrupa operaciones de inscripción que ya existían en las clases del modelo. */
public class GestionInscripciones {
    public void registrar(Inscripcion inscripcion) {
        if (inscripcion == null) throw new IllegalArgumentException("Inscripción requerida");
        inscripcion.registrar();
    }

    public void confirmarPendientes(Administrador administrador, Torneo torneo) {
        if (administrador == null || torneo == null) throw new IllegalArgumentException("Faltan datos");
        administrador.gestionarInscripciones(torneo);
    }
}
