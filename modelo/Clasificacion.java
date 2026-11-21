package modelo;

import enums.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Collections;
import java.util.List;

public class Clasificacion {
    private final int idClasificacion;
    private final List<EntradaClasificacion> entradas = new ArrayList<>();
    private final List<Integer> partidosContabilizados = new ArrayList<>();
    public Clasificacion(int idClasificacion) { this.idClasificacion = idClasificacion; }
    public int getIdClasificacion() { return idClasificacion; }
    public List<EntradaClasificacion> getEntradas() { return Collections.unmodifiableList(entradas); }
    public void agregarParticipante(Participante participante) {
        if (entradas.stream().noneMatch(e -> e.getParticipante() == participante))
            entradas.add(new EntradaClasificacion(entradas.size() + 1, participante));
    }
    public void actualizarPosiciones(List<Partido> partidos) {
        for (Partido partido : partidos) {
            if (partido.getEstado() != EstadoPartido.FINALIZADO || partidosContabilizados.contains(partido.getIdPartido())) continue;
            agregarParticipante(partido.getParticipanteA());
            agregarParticipante(partido.getParticipanteB());
            Resultado r = partido.getResultado();
            entradaDe(partido.getParticipanteA()).actualizarEstadisticas(r.getPuntosA() > r.getPuntosB(), r.getPuntosA());
            entradaDe(partido.getParticipanteB()).actualizarEstadisticas(r.getPuntosB() > r.getPuntosA(), r.getPuntosB());
            partidosContabilizados.add(partido.getIdPartido());
        }
        calcularPosiciones();
    }
    private EntradaClasificacion entradaDe(Participante p) {
        return entradas.stream().filter(e -> e.getParticipante() == p).findFirst().orElseThrow();
    }
    public void calcularPosiciones() {
        entradas.sort(Comparator.comparingInt(EntradaClasificacion::getVictorias).reversed()
                .thenComparing(Comparator.comparingInt(EntradaClasificacion::getPuntos).reversed())
                .thenComparing(e -> e.getParticipante().getNombre()));
        for (int i = 0; i < entradas.size(); i++) entradas.get(i).setPosicion(i + 1);
    }
    public String mostrarClasificacion() {
        StringBuilder sb = new StringBuilder("CLASIFICACIÓN\n");
        for (EntradaClasificacion e : entradas) sb.append(e.getPosicion()).append(". ")
                .append(e.getParticipante().getNombre()).append(" | V: ").append(e.getVictorias())
                .append(" | D: ").append(e.getDerrotas()).append(" | Puntos: ").append(e.getPuntos()).append('\n');
        return sb.toString();
    }
}
