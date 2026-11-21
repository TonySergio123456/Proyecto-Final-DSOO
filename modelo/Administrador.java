package modelo;

import enums.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Administrador {
    private final int idAdministrador;
    private String nombre;
    private String correo;
    // Demostración académica: una aplicación real no debe guardar contraseñas en texto plano.
    private String contrasena;
    private final List<Torneo> torneos = new ArrayList<>();

    public Administrador(int idAdministrador, String nombre, String correo, String contrasena) {
        this.idAdministrador = idAdministrador;
        this.nombre = Validaciones.texto(nombre, "nombre");
        this.correo = Validaciones.texto(correo, "correo");
        this.contrasena = Validaciones.texto(contrasena, "contraseña");
    }
    public List<Torneo> getTorneos() { return Collections.unmodifiableList(torneos); }
    public Torneo crearTorneo(int id, String nombre, LocalDate inicio, LocalDate fin,
                             int maxParticipantes, Modalidad modalidad, FormatoTorneo formato, Videojuego videojuego) {
        if (torneos.stream().anyMatch(t -> t.getIdTorneo() == id)) throw new IllegalArgumentException("ID de torneo repetido");
        Torneo torneo = new Torneo(id, nombre, inicio, fin, maxParticipantes, modalidad, formato, videojuego);
        torneos.add(torneo);
        torneo.crearTorneo();
        return torneo;
    }
    public void modificarTorneo(Torneo torneo, String nombre, LocalDate inicio, LocalDate fin) {
        Validaciones.condicion(torneos.contains(torneo), "Torneo ajeno al administrador");
        torneo.modificarDatos(nombre, inicio, fin);
    }
    public void eliminarTorneo(Torneo torneo) {
        Validaciones.condicion(torneos.contains(torneo), "Torneo ajeno al administrador");
        torneo.cancelarTorneo();
        torneos.remove(torneo);
    }
    public void gestionarInscripciones(Torneo torneo) {
        Validaciones.condicion(torneos.contains(torneo), "Torneo ajeno al administrador");
        for (Inscripcion inscripcion : torneo.getInscripciones()) {
            if (inscripcion.getEstado() == EstadoInscripcion.PENDIENTE) inscripcion.confirmar();
        }
    }
}
