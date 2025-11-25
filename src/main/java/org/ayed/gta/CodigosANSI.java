package org.ayed.gta;

/**
 * Contiene constantes para códigos de escape ANSI utilizados
 * en la visualización del mapa en la terminal.
 */
public class CodigosANSI {

    // Código para resetear todos los estilos y colores
    public static final String RESET = "\u001B[0m";

    // === COLORES DE TEXTO ===
    public static final String NEGRO_TEXTO = "\u001B[30m";
    public static final String ROJO_TEXTO = "\u001B[31m";
    public static final String VERDE_TEXTO = "\u001B[32m";
    public static final String AMARILLO_TEXTO = "\u001B[33m";
    public static final String AZUL_TEXTO = "\u001B[34m";
    public static final String MAGENTA_TEXTO = "\u001B[35m";
    public static final String CYAN_TEXTO = "\u001B[36m";
    public static final String BLANCO_TEXTO = "\u001B[37m";

    // === COLORES DE FONDO ===
    public static final String NEGRO_FONDO = "\u001B[40m";
    public static final String ROJO_FONDO = "\u001B[41m";
    public static final String VERDE_FONDO = "\u001B[42m";
    public static final String AMARILLO_FONDO = "\u001B[43m";
    public static final String AZUL_FONDO = "\u001B[44m";
    public static final String MAGENTA_FONDO = "\u001B[45m";
    public static final String CYAN_FONDO = "\u001B[46m";
    public static final String BLANCO_FONDO = "\u001B[47m";

    // === COLORES BRILLANTES ===
    public static final String VERDE_BRILLANTE_FONDO = "\u001B[102m";
    public static final String AMARILLO_BRILLANTE_FONDO = "\u001B[103m";
    public static final String NARANJA_FONDO = "\u001B[48;5;208m"; // Color personalizado

    // Constructor privado para prevenir instanciación
    private CodigosANSI() {
        throw new AssertionError("Esta clase no debe ser instanciada");
    }

    /**
     * Limpia la pantalla de la consola
     */
    public static void limpiarPantalla() {
        // El código \u001B[2J limpia toda la pantalla
        // El código \u001B[H mueve el cursor a la posición (0,0)
        System.out.print("\u001B[2J\u001B[H");
        System.out.flush();
    }
}