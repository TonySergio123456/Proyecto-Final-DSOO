package modelo;

import enums.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Torneo {
    private final int idTorneo;
    private String nombre;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private final int maxParticipantes;
    private EstadoTorneo estado = EstadoTorneo.PENDIENTE;
    private final Modalidad modalidad;
    private final FormatoTorneo formato;
    private final Videojuego videojuego;
    private final List<Inscripcion> inscripciones = new ArrayList<>();
    private final List<Ronda> rondas = new ArrayList<>();
    private final Clasificacion clasificacion;

    public Torneo(int idTorneo, String nombre, LocalDate fechaInicio, LocalDate fechaFin, int maxParticipantes,
                  Modalidad modalidad, FormatoTorneo formato, Videojuego videojuego) {
        if (idTorneo <= 0 || maxParticipantes < 2 || fechaInicio == null || fechaFin == null || fechaFin.isBefore(fechaInicio)
                || modalidad == null || formato == null || videojuego == null)
            throw new IllegalArgumentException("Datos de torneo inválidos");
        this.idTorneo = idTorneo;
        this.nombre = Validaciones.texto(nombre, "nombre de torneo");
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.maxParticipantes = maxParticipantes;
        this.modalidad = modalidad;
        this.formato = formato;
        this.videojuego = videojuego;
        this.clasificacion = new Clasificacion(idTorneo);
    }
    public int getIdTorneo() { return idTorneo; }
    public String getNombre() { return nombre; }
    public EstadoTorneo getEstado() { return estado; }
    public Modalidad getModalidad() { return modalidad; }
    public FormatoTorneo getFormato() { return formato; }
    public Videojuego getVideojuego() { return videojuego; }
    public List<Inscripcion> getInscripciones() { return Collections.unmodifiableList(inscripciones); }
    public List<Ronda> getRondas() { return Collections.unmodifiableList(rondas); }
    public Clasificacion getClasificacion() { return clasificacion; }
    public void crearTorneo() { System.out.println("Torneo creado: " + nombre); }
    public void modificarDatos(String nombre, LocalDate fechaInicio, LocalDate fechaFin) {
        Validaciones.condicion(estado == EstadoTorneo.PENDIENTE, "No puede modificarse un torneo iniciado");
        if (fechaInicio == null || fechaFin == null || fechaFin.isBefore(fechaInicio))
            throw new IllegalArgumentException("Fechas inválidas");
        this.nombre = Validaciones.texto(nombre, "nombre del torneo");
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }
    public void cancelarTorneo() {
        Validaciones.condicion(estado == EstadoTorneo.PENDIENTE, "Solo puede cancelarse un torneo pendiente");
        estado = EstadoTorneo.CANCELADO;
    }
    public void inscribirParticipante(Inscripcion inscripcion) {
        Validaciones.condicion(estado == EstadoTorneo.PENDIENTE, "Inscripciones cerradas");
        if (inscripcion == null || inscripcion.getTorneo() != this || inscripcion.getEstado() != EstadoInscripcion.PENDIENTE)
            throw new IllegalArgumentException("Inscripción incorrecta");
        Participante p = inscripcion.getParticipante();
        if ((modalidad == Modalidad.EQUIPOS && !(p instanceof Equipo)) ||
                (modalidad == Modalidad.INDIVIDUAL && !(p instanceof Jugador)))
            throw new IllegalArgumentException("El tipo de participante no coincide con la modalidad");
        long activos = inscripciones.stream().filter(i -> i.getEstado() != EstadoInscripcion.CANCELADA).count();
        Validaciones.condicion(activos < maxParticipantes, "Cupos completos");
        if (inscripciones.stream().anyMatch(i -> i.getParticipante().getIdParticipante() == p.getIdParticipante()
                && i.getEstado() != EstadoInscripcion.CANCELADA))
            throw new IllegalArgumentException("Participante ya inscrito");
        inscripciones.add(inscripcion);
    }
    public List<Participante> getParticipantesConfirmados() {
        return inscripciones.stream().filter(i -> i.getEstado() == EstadoInscripcion.CONFIRMADA)
                .map(Inscripcion::getParticipante).toList();
    }
    public void iniciarTorneo() {
        Validaciones.condicion(estado == EstadoTorneo.PENDIENTE, "El torneo no está pendiente");
        Validaciones.condicion(getParticipantesConfirmados().size() >= 2, "Confirme al menos dos participantes");
        estado = EstadoTorneo.EN_CURSO;
        for (Participante p : getParticipantesConfirmados()) clasificacion.agregarParticipante(p);
        clasificacion.calcularPosiciones();
    }
    public Ronda generarCalendario() {
        Validaciones.condicion(estado == EstadoTorneo.EN_CURSO, "Primero debe iniciar el torneo");
        // La doble eliminación requiere dos llaves y reglas adicionales; se evita simularla incorrectamente.
        if (formato == FormatoTorneo.DOBLE_ELIMINACION)
            throw new UnsupportedOperationException("Calendario de doble eliminación no implementado; use ELIMINACION_DIRECTA");
        if (!rondas.isEmpty()) Validaciones.condicion(rondas.get(rondas.size()-1).verificarEliminacion(), "Finalice la ronda anterior");
        List<Participante> participantes = rondas.isEmpty() ? getParticipantesConfirmados() :
                rondas.get(rondas.size()-1).avanzarParticipantes();
        Validaciones.condicion(participantes.size() >= 2, "Ya hay un ganador; no quedan rondas");
        Ronda ronda = new Ronda(idTorneo * 100 + rondas.size() + 1, rondas.size() + 1, FaseRonda.PRINCIPAL);
        ronda.generarPartidos(participantes, fechaInicio.atTime(10, 0).plusDays(rondas.size()));
        rondas.add(ronda);
        return ronda;
    }
    public void actualizarClasificacion() {
        List<Partido> partidos = rondas.stream().flatMap(r -> r.getPartidos().stream()).toList();
        clasificacion.actualizarPosiciones(partidos);
    }
    public void finalizarTorneo() {
        Validaciones.condicion(estado == EstadoTorneo.EN_CURSO && !rondas.isEmpty(), "Torneo sin rondas");
        Ronda ultima = rondas.get(rondas.size()-1);
        Validaciones.condicion(ultima.verificarEliminacion() && ultima.avanzarParticipantes().size() == 1,
                "Aún falta determinar un campeón");
        actualizarClasificacion();
        estado = EstadoTorneo.FINALIZADO;
    }
    public Participante obtenerCampeon() {
        Validaciones.condicion(estado == EstadoTorneo.FINALIZADO, "El torneo no ha finalizado");
        return rondas.get(rondas.size()-1).avanzarParticipantes().get(0);
    }
}
