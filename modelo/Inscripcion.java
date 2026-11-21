package modelo;

import enums.*;

import java.time.LocalDate;

public class Inscripcion {
    private final int idInscripcion;
    private final LocalDate fechaInscripcion;
    private EstadoInscripcion estado;
    private final Participante participante;
    private final Torneo torneo;

    public Inscripcion(int idInscripcion, Participante participante, Torneo torneo) {
        if (participante == null || torneo == null) throw new IllegalArgumentException("Datos de inscripción incompletos");
        this.idInscripcion = idInscripcion;
        this.participante = participante;
        this.torneo = torneo;
        this.fechaInscripcion = LocalDate.now();
        this.estado = EstadoInscripcion.PENDIENTE;
    }
    public int getIdInscripcion() { return idInscripcion; }
    public LocalDate getFechaInscripcion() { return fechaInscripcion; }
    public EstadoInscripcion getEstado() { return estado; }
    public Participante getParticipante() { return participante; }
    public Torneo getTorneo() { return torneo; }
    public void registrar() { torneo.inscribirParticipante(this); }
    public void confirmar() {
        Validaciones.condicion(estado == EstadoInscripcion.PENDIENTE, "Solo puede confirmarse una inscripción pendiente");
        Validaciones.condicion(torneo.getEstado() == EstadoTorneo.PENDIENTE, "El torneo ya comenzó");
        Validaciones.condicion(torneo.getInscripciones().contains(this), "Primero debe registrar la inscripción");
        estado = EstadoInscripcion.CONFIRMADA;
    }
    public void cancelar() {
        Validaciones.condicion(torneo.getEstado() == EstadoTorneo.PENDIENTE, "El torneo ya comenzó");
        Validaciones.condicion(estado != EstadoInscripcion.CANCELADA, "Inscripción ya cancelada");
        estado = EstadoInscripcion.CANCELADA;
    }
}
