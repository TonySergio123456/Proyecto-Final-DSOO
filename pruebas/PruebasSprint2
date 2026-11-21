package pruebas;

import modelo.*;
import enums.*;

import java.time.LocalDate;

/** Pruebas adicionales del segundo avance, ejecutables sin librerías externas. */
public class PruebasSprint2 {
    private static int correctas = 0;
    private static void verificar(boolean condicion, String mensaje) {
        if (!condicion) throw new AssertionError(mensaje);
        correctas++;
        System.out.println("[OK] " + mensaje);
    }
    private static void esperarError(Runnable accion, String mensaje) {
        boolean error = false;
        try { accion.run(); } catch (IllegalArgumentException | IllegalStateException ex) { error = true; }
        verificar(error, mensaje);
    }
    public static void main(String[] args) {
        Videojuego juego = new Videojuego(1, "Valorant", "Shooter", "Riot Games");
        Torneo torneo = new Torneo(2, "Copa de 4 jugadores", LocalDate.of(2026, 11, 22),
                LocalDate.of(2026, 11, 25), 4, Modalidad.INDIVIDUAL,
                FormatoTorneo.ELIMINACION_DIRECTA, juego);
        Jugador[] j = {
            new Jugador(1,"Ana","Perú","AnaPro","Duelista",20),
            new Jugador(2,"Luis","Perú","LuisFPS","Centinela",21),
            new Jugador(3,"María","Perú","MariaG","Iniciadora",19),
            new Jugador(4,"Pedro","Perú","PedroG","Controlador",20)
        };
        for (int i = 0; i < j.length; i++) {
            Inscripcion ins = new Inscripcion(i+1, j[i], torneo);
            ins.registrar(); ins.confirmar();
        }
        verificar(torneo.getParticipantesConfirmados().size() == 4, "Cuatro inscripciones confirmadas");
        esperarError(() -> new Inscripcion(5,j[0],torneo).registrar(), "Se rechaza inscripción duplicada");
        torneo.iniciarTorneo();
        Ronda semifinal = torneo.generarCalendario();
        verificar(semifinal.getPartidos().size() == 2, "Semifinal con dos partidos");
        Partido p1=semifinal.getPartidos().get(0), p2=semifinal.getPartidos().get(1);
        p1.iniciarPartido();
        esperarError(() -> p1.registrarResultado(new Resultado(1,5,5)), "Se rechaza un empate");
        p1.registrarResultado(new Resultado(2,13,9)); p1.finalizarPartido();
        p2.iniciarPartido(); p2.registrarResultado(new Resultado(3,10,13)); p2.finalizarPartido();
        Ronda finalRonda=torneo.generarCalendario();
        verificar(finalRonda.getPartidos().size()==1,"Final con los ganadores de semifinales");
        Partido finalPartido=finalRonda.getPartidos().get(0);
        finalPartido.iniciarPartido();
        finalPartido.registrarResultado(new Resultado(4,13,7)); finalPartido.finalizarPartido();
        torneo.finalizarTorneo();
        verificar("Ana".equals(torneo.obtenerCampeon().getNombre()),"Campeona correcta: Ana");
        torneo.actualizarClasificacion();
        String tabla=torneo.getClasificacion().mostrarClasificacion();
        verificar(tabla.contains("Ana") && tabla.contains("Pedro"),"Clasificación incluye participantes");
        System.out.println("\nTOTAL: " + correctas + " verificaciones correctas");
        System.out.println(tabla);
    }
}
