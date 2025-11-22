package org.ayed.gta;

public class ResultadoMision{
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

    public ResultadoMision(Mision mision, boolean completada, boolean vehiculoExotico, Estado estado){
        this.completada = completada;
        this.dineroGanado = completada ? mision.getRecompensaDinero() : 0;
        this.creditosGanados = completada ? mision.getRecompensaCreditos() : 0;
        this.vehiculoExotico = vehiculoExotico;
        this.estado = estado;
    }

    public boolean estaCompletada(){
        return completada;
    }

    public int getDineroGanado(){
        return dineroGanado;
    }

    public int getCreditosGanados(){
        return creditosGanados;
    }

    public boolean obtenerVehiculoExotico(){
        return vehiculoExotico;
    }

    public Estado getEstado(){
        return estado;
    }
}

