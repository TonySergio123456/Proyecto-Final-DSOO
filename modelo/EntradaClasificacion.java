package modelo;

import enums.*;

public class EntradaClasificacion {
    private final int idEntrada;
    private final Participante participante;
    private int victorias;
    private int derrotas;
    private int puntos;
    private int posicion;

    public EntradaClasificacion(int idEntrada, Participante participante) {
        if (participante == null) throw new IllegalArgumentException("Participante requerido");
        this.idEntrada = idEntrada;
        this.participante = participante;
    }
    public int getIdEntrada() { return idEntrada; }
    public Participante getParticipante() { return participante; }
    public int getVictorias() { return victorias; }
    public int getDerrotas() { return derrotas; }
    public int getPuntos() { return puntos; }
    public int getPosicion() { return posicion; }
    public void setPosicion(int posicion) { this.posicion = posicion; }
    public void actualizarEstadisticas(boolean gano, int puntosConseguidos) {
        if (puntosConseguidos < 0) throw new IllegalArgumentException("Puntos inválidos");
        if (gano) victorias++; else derrotas++;
        puntos += puntosConseguidos;
    }
}
