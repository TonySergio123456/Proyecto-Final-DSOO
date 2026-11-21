package modelo;

import enums.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Equipo extends Participante {
    private String tag;
    private final List<Jugador> integrantes = new ArrayList<>();

    public Equipo(int idParticipante, String nombre, String pais, String tag) {
        super(idParticipante, nombre, pais);
        this.tag = Validaciones.texto(tag, "tag");
    }
    public String getTag() { return tag; }
    public void agregarJugador(Jugador jugador) {
        if (jugador == null) throw new IllegalArgumentException("Jugador requerido");
        if (jugador.getEquipo() != null && jugador.getEquipo() != this)
            throw new IllegalStateException("El jugador ya pertenece a otro equipo");
        if (integrantes.contains(jugador) || integrantes.stream().anyMatch(j -> j.getIdParticipante() == jugador.getIdParticipante()))
            throw new IllegalArgumentException("Jugador duplicado");
        integrantes.add(jugador);
        jugador.asignarEquipo(this);
    }
    public void eliminarJugador(Jugador jugador) {
        if (integrantes.remove(jugador)) jugador.asignarEquipo(null);
    }
    public List<Jugador> consultarIntegrantes() { return Collections.unmodifiableList(integrantes); }
    @Override public String consultarDatos() {
        return "Equipo " + getIdParticipante() + ": " + getNombre() + " [" + tag + "] (" + integrantes.size() + " jugadores)";
    }
}
