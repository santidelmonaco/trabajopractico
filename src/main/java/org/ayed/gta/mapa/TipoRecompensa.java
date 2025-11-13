package org.ayed.gta.mapa;

/**
 * Tipos de recompensas que pueden aparecer en el mapa.
 */
public enum TipoRecompensa {
    /**
     * Dinero en efectivo.
     */
    DINERO,

    /**
     * Créditos para mejorar el garaje.
     */
    CREDITOS,

    /**
     * Vehículo exótico.
     * Solo aparece en misiones difíciles con baja probabilidad.
     */
    VEHICULO_EXOTICO;

    /**
     * Genera una cantidad aleatoria para esta recompensa.
     *
     * @return cantidad generada
     */
    public int generarCantidad() {
        switch (this) {
            case DINERO:
                return 500 + (int)(Math.random() * 1500); // Entre 500 y 2000
            case CREDITOS:
                return 5 + (int)(Math.random() * 16); // Entre 5 y 20
            case VEHICULO_EXOTICO:
                return 1;
            default:
                return 0;
        }
    }

    /**
     * Obtiene una descripción de la recompensa.
     */
    public String getDescripcion(int cantidad) {
        switch (this) {
            case DINERO:
                return "$" + cantidad + " en efectivo";
            case CREDITOS:
                return cantidad + " créditos de garaje";
            case VEHICULO_EXOTICO:
                return "¡Un vehículo exótico!";
            default:
                return "Recompensa desconocida";
        }
    }
}