package org.ayed.gta.mapa;

/**
 * Representa una celda individual en el mapa de la ciudad.
 * Cada celda tiene una posición (fila, columna), un tipo, y posiblemente datos adicionales.
 */
public class Celda {
    private final int fila;
    private final int columna;
    private TipoCelda tipo;
    private TipoRecompensa recompensa;
    private boolean recompensaRecolectada;

    /**
     * Constructor
     *
     * @param fila Fila en la matriz del mapa
     * @param columna Columna en la matriz del mapa
     * @param tipo Tipo de celda inicial
     */
    public Celda(int fila, int columna, TipoCelda tipo) {
        this.fila = fila;
        this.columna = columna;
        this.tipo = tipo;
        this.recompensa = null;
        this.recompensaRecolectada = false;
    }

    /**
     * Constructor para celdas con recompensa.
     *
     * @param fila Fila en la matriz
     * @param columna Columna en la matriz
     * @param tipoRecompensa Tipo de recompensa que contiene
     */
    public Celda(int fila, int columna, TipoRecompensa tipoRecompensa) {
        this(fila, columna, TipoCelda.RECOMPENSA);
        this.recompensa = tipoRecompensa;
    }

    // === GETTERS ===

    public int getFila() {
        return fila;
    }

    public int getColumna() {
        return columna;
    }

    public TipoCelda getTipo() {
        return tipo;
    }

    public TipoRecompensa getRecompensa() {
        return recompensa;
    }

    public boolean isRecompensaRecolectada() {
        return recompensaRecolectada;
    }

    // === SETTERS ===

    /**
     * Cambia el tipo de esta celda.
     */
    public void setTipo(TipoCelda tipo) {
        this.tipo = tipo;
    }

    /**
     * Establece una recompensa en esta celda.
     */
    public void setRecompensa(TipoRecompensa recompensa) {
        this.tipo = TipoCelda.RECOMPENSA;
        this.recompensa = recompensa;
        this.recompensaRecolectada = false;
    }

    /**
     * Marca la recompensa como recolectada y convierte la celda en calle normal.
     */
    public void recolectarRecompensa() {
        if (tipo == TipoCelda.RECOMPENSA && !recompensaRecolectada) {
            recompensaRecolectada = true;
            tipo = TipoCelda.CALLE;
        }
    }

    // === MÉTODOS DE UTILIDAD ===

    /**
     * Obtiene el costo de tránsito de esta celda.
     * Tiene en cuenta si la recompensa ya fue recogida.
     *
     * @return el costo de la celda
     */
    public int getCostoTransito() {
        if (tipo == TipoCelda.RECOMPENSA && recompensaRecolectada) {
            return TipoCelda.CALLE.getCostoTransito();
        }
        return tipo.getCostoTransito();
    }

    /**
     * Evalúa si esta celda es transitable.
     */
    public boolean esTransitable() {
        return tipo.esTransitable();
    }

    /**
     * Obtiene el símbolo de esta celda para renderizado.
     */
    public char getSimbolo() {
        // Si es una recompensa ya recogida, mostrar como calle
        if (tipo == TipoCelda.RECOMPENSA && recompensaRecolectada) {
            return TipoCelda.CALLE.getSimbolo();
        }
        return tipo.getSimbolo();
    }
}