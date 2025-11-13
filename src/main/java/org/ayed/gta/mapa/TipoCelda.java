package org.ayed.gta.mapa;

/**
 * Tipos de celdas que puede contener el mapa de la ciudad.
 * Cada tipo tiene un símbolo y un costo de tránsito asociado.
 */
public enum TipoCelda {
    /**
     * Calle transitable normal.
     * Costo de tránsito: 1 unidad de tiempo.
     */
    CALLE('·', 1),

    /**
     * Edificio no transitable.
     * Bloquea completamente el paso.
     */
    EDIFICIO('#', Integer.MAX_VALUE),

    /**
     * Punto de salida de la misión.
     * Donde comienza el jugador.
     */
    SALIDA('S', 1),

    /**
     * Punto de destino de la misión.
     * Donde debe llegar el jugador para ganar.
     */
    DESTINO('D', 1),

    /**
     * Zona de congestión de tráfico.
     * Costo de tránsito: 5 veces el normal.
     */
    CONGESTION('X', 5),

    /**
     * Recompensa coleccionable.
     * El jugador puede recogerla.
     */
    RECOMPENSA('$', 1),

    /**
     * Posición actual del jugador.
     */
    JUGADOR('J', 1),

    /**
     * Parte del camino calculado por el GPS.
     */
    CAMINO_GPS('*', 1);

    private final char simbolo;
    private final int costoTransito;

    /**
     * Constructor
     *
     * @param simbolo Carácter que representa visualmente este tipo de celda
     * @param costoTransito Costo para atravesar esta celda
     */
    TipoCelda(char simbolo, int costoTransito) {
        this.simbolo = simbolo;
        this.costoTransito = costoTransito;
    }

    public char getSimbolo() {
        return simbolo;
    }

    public int getCostoTransito() {
        return costoTransito;
    }

    /**
     * Evalúa si esta celda es transitable (se puede manejar sobre ella).
     *
     * @return true si el jugador puede moverse a esta celda
     */
    public boolean esTransitable() {
        return this != EDIFICIO;
    }

    /**
     * Evalúa si esta celda es válida como punto de spawn (salida o destino).
     * Solo las calles normales son válidas para generar puntos aleatorios.
     *
     * @return true si se puede usar como punto de salida o destino
     */
    public boolean esValidaParaSpawn() {
        return this == CALLE;
    }
}