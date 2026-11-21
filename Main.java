import modelo.*;
import enums.*;
import servicio.*;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        Administrador admin = new Administrador(1, "Tony", "admin@ejemplo.com", "claveDemostracion");
        Videojuego juego = new Videojuego(1, "Valorant", "Shooter táctico", "Riot Games");
        GestionTorneos gestion = new GestionTorneos(admin);
        Torneo torneo = gestion.crearTorneo(1, "Copa Universitaria", LocalDate.now().plusDays(1),
                LocalDate.now().plusDays(2), 8, Modalidad.INDIVIDUAL,
                FormatoTorneo.ELIMINACION_DIRECTA, juego);

        Jugador jugador1 = new Jugador(1, "Ana", "Perú", "AnaPro", "Duelista", 20);
        Jugador jugador2 = new Jugador(2, "Luis", "Perú", "LuisFPS", "Centinela", 21);
        Inscripcion ins1 = new Inscripcion(1, jugador1, torneo);
        Inscripcion ins2 = new Inscripcion(2, jugador2, torneo);
        GestionInscripciones inscripciones = new GestionInscripciones();
        inscripciones.registrar(ins1);
        inscripciones.registrar(ins2);
        inscripciones.confirmarPendientes(admin, torneo);
        torneo.iniciarTorneo();

        Ronda ronda = torneo.generarCalendario();
        Partido partido = ronda.getPartidos().get(0);
        partido.iniciarPartido();
        partido.registrarResultado(new Resultado(1, 13, 9));
        partido.finalizarPartido();
        torneo.actualizarClasificacion();
        torneo.finalizarTorneo();

        System.out.println("Videojuego: " + juego.consultarInformacion());
        System.out.println("Campeón: " + torneo.obtenerCampeon().getNombre());
        System.out.println(torneo.getClasificacion().mostrarClasificacion());
        System.out.println(jugador1.consultarEstadisticas());
    }
}
