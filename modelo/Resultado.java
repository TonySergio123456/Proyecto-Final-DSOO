package modelo;

import enums.*;

import java.time.LocalDateTime;

public class Resultado {
    private final int idResultado;
    private final int puntosA;
    private final int puntosB;
    private final LocalDateTime fechaRegistro;

    public Resultado(int idResultado, int puntosA, int puntosB) {
        if (puntosA < 0 || puntosB < 0) throw new IllegalArgumentException("Los puntos no pueden ser negativos");
        this.idResultado = idResultado;
        this.puntosA = puntosA;
        this.puntosB = puntosB;
        this.fechaRegistro = LocalDateTime.now();
    }
    public int getIdResultado() { return idResultado; }
    public int getPuntosA() { return puntosA; }
    public int getPuntosB() { return puntosB; }
    public LocalDateTime getFechaRegistro() { return fechaRegistro; }
    public boolean validarResultado() { return puntosA >= 0 && puntosB >= 0 && puntosA != puntosB; }
    public Participante determinarGanador(Partido partido) {
        if (!validarResultado()) throw new IllegalStateException("En este torneo de eliminación no se admiten empates");
        return puntosA > puntosB ? partido.getParticipanteA() : partido.getParticipanteB();
    }
}
