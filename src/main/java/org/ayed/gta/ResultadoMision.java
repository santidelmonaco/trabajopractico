package org.ayed.gta;

public class ResultadoMision{
    public enum Estado{
        EN_CURSO,
        EXITO,
        FALLO_TIEMPO,
        FALLO_GASOLINA,
        FALLO_MOVIMIENTO
    }
    protected boolean completada;
    protected int dineroGanado;
    protected int creditosGanados;
    protected boolean vehiculoExotico;
    protected Estado estado;

    public ResultadoMision(Mision mision, boolean completada, boolean vehiculoExotico, Estado estado){
        this.completada = completada;
        this.dineroGanado = mision.getRecompensaDinero();
        this.creditosGanados = mision.getRecompensaCreditos();
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

