package modelo;

import enums.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Ronda {
    private final int idRonda;
    private final int numero;
    private final FaseRonda faseRonda;
    private final List<Partido> partidos = new ArrayList<>();
    private final List<Participante> participantesClasificados = new ArrayList<>();

    public Ronda(int idRonda, int numero, FaseRonda faseRonda) {
        if (numero < 1) throw new IllegalArgumentException("Número de ronda inválido");
        this.idRonda = idRonda;
        this.numero = numero;
        this.faseRonda = faseRonda;
    }
    public int getIdRonda() { return idRonda; }
    public int getNumero() { return numero; }
    public FaseRonda getFaseRonda() { return faseRonda; }
    public List<Partido> getPartidos() { return Collections.unmodifiableList(partidos); }
    public List<Participante> getParticipantesClasificados() { return Collections.unmodifiableList(participantesClasificados); }
    // En número impar, el último participante pasa automáticamente a la ronda siguiente.
    public void generarPartidos(List<Participante> participantes, LocalDateTime fecha) {
        Validaciones.condicion(partidos.isEmpty() && participantesClasificados.isEmpty(), "La ronda ya tiene un calendario");
        if (participantes == null || participantes.size() < 2 || fecha == null)
            throw new IllegalArgumentException("Se requieren al menos dos participantes y una fecha");
        for (int i = 0; i + 1 < participantes.size(); i += 2) {
            Partido partido = new Partido(idRonda * 1000 + i / 2 + 1, participantes.get(i), participantes.get(i + 1));
            partido.programarPartido(fecha.plusHours(i / 2));
            partidos.add(partido);
        }
        if (participantes.size() % 2 != 0) participantesClasificados.add(participantes.get(participantes.size() - 1));
    }
    public boolean verificarEliminacion() {
        return partidos.stream().allMatch(p -> p.getEstado() == EstadoPartido.FINALIZADO);
    }
    public List<Participante> avanzarParticipantes() {
        Validaciones.condicion(verificarEliminacion(), "Todavía quedan partidos pendientes");
        List<Participante> ganadores = new ArrayList<>(participantesClasificados);
        for (Partido partido : partidos) ganadores.add(partido.obtenerGanador());
        return ganadores;
    }
}
