package modelo;

import enums.*;

public class Videojuego {
    private final int idVideojuego;
    private String nombre;
    private String genero;
    private String desarrolladora;

    public Videojuego(int idVideojuego, String nombre, String genero, String desarrolladora) {
        this.idVideojuego = idVideojuego;
        this.nombre = Validaciones.texto(nombre, "nombre del videojuego");
        this.genero = Validaciones.texto(genero, "género");
        this.desarrolladora = Validaciones.texto(desarrolladora, "desarrolladora");
    }
    public int getIdVideojuego() { return idVideojuego; }
    public String getNombre() { return nombre; }
    public String consultarInformacion() {
        return nombre + " | Género: " + genero + " | Desarrolladora: " + desarrolladora;
    }
}
