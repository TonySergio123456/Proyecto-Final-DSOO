package modelo;

import enums.*;

final class Validaciones {
    private Validaciones() { }
    static String texto(String valor, String campo) {
        if (valor == null || valor.isBlank()) throw new IllegalArgumentException("Falta " + campo);
        return valor.trim();
    }
    static void condicion(boolean condicion, String mensaje) {
        if (!condicion) throw new IllegalStateException(mensaje);
    }
}
