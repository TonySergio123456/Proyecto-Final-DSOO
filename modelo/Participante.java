package modelo;

import enums.*;

public abstract class Participante {
    private final int idParticipante;
    private String nombre;
    private String pais;

    protected Participante(int idParticipante, String nombre, String pais) {
        if (idParticipante <= 0) throw new IllegalArgumentException("El ID debe ser positivo");
        this.idParticipante = idParticipante;
        setNombre(nombre);
        setPais(pais);
    }
    public int getIdParticipante() { return idParticipante; }
    public String getNombre() { return nombre; }
    public String getPais() { return pais; }
    public void setNombre(String nombre) { this.nombre = Validaciones.texto(nombre, "nombre"); }
    public void setPais(String pais) { this.pais = Validaciones.texto(pais, "país"); }
    public abstract String consultarDatos();
    @Override public String toString() { return consultarDatos(); }
}
