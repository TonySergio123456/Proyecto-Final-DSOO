package modelo;

import enums.*;

public class Jugador extends Participante {
    private String nickname;
    private String posicion;
    private int edad;
    private Equipo equipo;
    private int partidasJugadas;
    private int partidasGanadas;

    public Jugador(int idParticipante, String nombre, String pais, String nickname, String posicion, int edad) {
        super(idParticipante, nombre, pais);
        this.nickname = Validaciones.texto(nickname, "nickname");
        this.posicion = Validaciones.texto(posicion, "posición");
        if (edad < 0) throw new IllegalArgumentException("Edad inválida");
        this.edad = edad;
    }
    public String getNickname() { return nickname; }
    public String getPosicion() { return posicion; }
    public int getEdad() { return edad; }
    public Equipo getEquipo() { return equipo; }
    void asignarEquipo(Equipo equipo) { this.equipo = equipo; }
    public void registrarJugador() { System.out.println("Jugador registrado: " + nickname); }
    public void registrarPartida(boolean gano) {
        partidasJugadas++;
        if (gano) partidasGanadas++;
    }
    public String consultarEstadisticas() {
        return nickname + " | Partidas: " + partidasJugadas + " | Victorias: " + partidasGanadas;
    }
    @Override public String consultarDatos() {
        return "Jugador " + getIdParticipante() + ": " + nickname + " (" + getNombre() + ", " + getPais() + ")";
    }
}
