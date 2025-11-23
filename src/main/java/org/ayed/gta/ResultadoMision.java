package org.ayed.gta;


/**
 * Representa el resultado final o parcial de una mision
 * Contiene informacion de si la mision fue completada, las recompensas obtenidas,
 * si se obtuvo un vehiculo exotico y el estado especifico del final de la mision
 */
public class ResultadoMision{

    /**
     * Estados posibles de una mision
     * 
     * EN_CURSO             -> la mision todavia esta activa
     * EXITO                -> la mision termino con exito
     * FALLO_TIEMPO         ->el tiempo limite llego a 0
     * FALLO_GASOLINA       -> el jugador se quedo sin combustible
     * FALLO_MOVIMIENTO     ->movimiento invalido o intento de desplazamiento no permitido
     */
    public enum Estado{
        EN_CURSO,
        EXITO,
        FALLO_TIEMPO,
        FALLO_GASOLINA,
        FALLO_MOVIMIENTO
    }
    private boolean completada;
    private int dineroGanado;
    private int creditosGanados;
    private boolean vehiculoExotico;
    private Estado estado;

    /**
     * Crea un nuevo resultado de mision
     * Si la mision esta completada, las recompensas de dinero y credito se otorgan automaticamente,
     * a partir de los valores de la mision en caso contrario son 0
     * 
     * @param mision La mision cuya recompensa se utiliza al completarse
     * @param completada Indica si la mision fue completada exitosamente
     * @param vehiculoexotico true si el jugador obtiene un vehiculo exotico como recompensa
     * @param estado Estado final o parcial de la mision
     */

    public ResultadoMision(Mision mision, boolean completada, boolean vehiculoExotico, Estado estado){
        this.completada = completada;
        this.dineroGanado = completada ? mision.getRecompensaDinero() : 0;
        this.creditosGanados = completada ? mision.getRecompensaCreditos() : 0;
        this.vehiculoExotico = vehiculoExotico;
        this.estado = estado;
    }

    /**
     * Indica si la mision fue completada exitosamente
     * 
     * @return true si la mision se completo, false si fallo o sigue en curso
     */
    public boolean estaCompletada(){
        return completada;
    }

    /**
     * Devuelve la cantidad de dinero otorgado como recompensa
     * 
     * @return Dinero ganado si la mision fue completada, 0 en caso contrario
     */
    public int getDineroGanado(){
        return dineroGanado;
    }

    /**
     * Devuelve la cantidad de creditos otorgado como recompensa
     * 
     * @return Credito ganado si la mision fue completada, 0 en caso contrario
     */
    public int getCreditosGanados(){
        return creditosGanados;
    }

    /**
     * Indica si el jugador obtuvo un vehiculo exotico como recompensa especial
     * 
     * @return true si se otorga un vehiculo exotico, false en caso contrario
     */
    public boolean obtenerVehiculoExotico(){
        return vehiculoExotico;
    }

    /**
     * Indica el estado de la mision
     * 
     * @return Estado actual de la mision
     */
    public Estado getEstado(){
        return estado;
    }
}

