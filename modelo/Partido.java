package modelo;

import enums.*;

import java.time.LocalDateTime;

public class Partido {
    private final int idPartido;
    private LocalDateTime fecha;
    private EstadoPartido estado;
    private final Participante participanteA;
    private final Participante participanteB;
    private Resultado resultado;

    public Partido(int idPartido, Participante participanteA, Participante participanteB) {
        if (participanteA == null || participanteB == null || participanteA == participanteB)
            throw new IllegalArgumentException("El partido necesita dos participantes diferentes");
        this.idPartido = idPartido;
        this.participanteA = participanteA;
        this.participanteB = participanteB;
        this.estado = EstadoPartido.PROGRAMADO;
    }
    public int getIdPartido() { return idPartido; }
    public LocalDateTime getFecha() { return fecha; }
    public EstadoPartido getEstado() { return estado; }
    public Participante getParticipanteA() { return participanteA; }
    public Participante getParticipanteB() { return participanteB; }
    public Resultado getResultado() { return resultado; }
    public void programarPartido(LocalDateTime fecha) {
        Validaciones.condicion(estado == EstadoPartido.PROGRAMADO, "No puede reprogramarse un partido iniciado");
        if (fecha == null) throw new IllegalArgumentException("Fecha requerida");
        this.fecha = fecha;
    }
    public void iniciarPartido() {
        Validaciones.condicion(estado == EstadoPartido.PROGRAMADO && fecha != null, "Primero programe el partido");
        estado = EstadoPartido.EN_CURSO;
    }
    public void registrarResultado(Resultado resultado) {
        Validaciones.condicion(estado == EstadoPartido.EN_CURSO, "El partido no está en curso");
        if (resultado == null || !resultado.validarResultado()) throw new IllegalArgumentException("Resultado inválido o empate");
        this.resultado = resultado;
    }
    public void finalizarPartido() {
        Validaciones.condicion(estado == EstadoPartido.EN_CURSO && resultado != null, "El partido requiere un resultado");
        estado = EstadoPartido.FINALIZADO;
        if (participanteA instanceof Jugador j) j.registrarPartida(resultado.getPuntosA() > resultado.getPuntosB());
        if (participanteB instanceof Jugador j) j.registrarPartida(resultado.getPuntosB() > resultado.getPuntosA());
    }
    public Participante obtenerGanador() {
        Validaciones.condicion(estado == EstadoPartido.FINALIZADO, "El partido aún no terminó");
        return resultado.determinarGanador(this);
    }
}
