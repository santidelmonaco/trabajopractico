package org.ayed.gta;

public class ResultadoMision{
    protected boolean completada;
    protected int dineroGanado;
    protected int creditosGanados;
    protected boolean vehiculoExotico;

    public ResultadoMision(Mision mision, boolean completada, boolean vehiculoExotico){
        this.completada = completada;
        this.dineroGanado = mision.getRecompensaDinero();
        this.creditosGanados = mision.getRecompensaCreditos();
        this.vehiculoExotico = vehiculoExotico;
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
}

