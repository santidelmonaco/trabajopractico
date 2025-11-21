package org.ayed.gta;
import org.ayed.gta.mapa.*;

public class ControlMision{
    protected Vehiculo vehiculo;
    protected Jugador jugador;
    protected Mapa mapa;
    protected Mision mision;
    protected double tiempoRestante;
    protected boolean enCurso;
    protected boolean completada;
    protected ResultadoMision resultadoMision;

    public ControlMision(Vehiculo vehiculo, Jugador jugador, Mapa mapa, Mision mision){
        this.vehiculo = vehiculo;
        this.jugador = jugador;
        this.mapa = mapa;
        this.mision = mision;
        this.tiempoRestante = mision.getTiempoLimite();
        this.enCurso = false;
        this.completada = false;
        this.resultadoMision = new ResultadoMision(mision, false, false, ResultadoMision.estado.EN_CURSO);
    }

    public void iniciarMision(){
        enCurso = true;
        resultadoMision = new ResultadoMision(mision, false, false, ResultadoMision.estado.EN_CURSO);
    }

    public void moverVehiculo(String direccion){
        if(!enCurso) return;

        if(verificarGasolina()) return;
        if(!verificarDestino(direccion)) return;

        moverYActualizar(direccion);
        verificarTiempo();
        verificarCompletada();

    }

    private boolean verificarGasolina(){
        if(vehiculo.getGasolinaActual() <= 0){
            fallarmision(ResultadoMision.estado.FALLO_GASOLINA);
            return true;
        }
        return false;
    }

    private boolean verificarDestino(String direccion){
        String origen = mapa.getPosicionActual();
        String destino = mapa.obtenerDestino(origen, direccion);
        if(destino == null){
            fallarmision(ResultadoMision.estado.FALLO_MOVIMIENTO);
            return false;
        }

        boolean movimientoExitoso = mapa.moverVehiculo(direccion);
        if(!movimientoExitoso){
            fallarmision(ResultadoMision.estado.FALLO_MOVIMIENTO);
            return false;
        }
        return true;
    }

    private void moverYActualizar(String direccion){
        vehiculo.consumirGasolina(1);
        vehiculo.sumarKilometraje(1);

        String origen = mapa.getPosicionActual();
        String destino = mapa.obtenerDestino(origen, direccion);
        double costo = mapa.obtenerCosto(origen, destino);
        double tiempoCalle = costo/vehiculo.getVelocidadMaxima();
        tiempoRestante -= tiempoCalle;
    }

    private void verificarTiempo(){
        if(tiempoRestante <= 0){
            fallarmision(ResultadoMision.estado.FALLO_TIEMPO);
        }
    }

    private void verificarCompletada(){
        if (mision.verificarCompletada(mapa.getPosicionActual())) {
            completada = true;
            enCurso = false;
            boolean vehiculoExotico = mision.esVehiculoExotico();
            resultadoMision = new ResultadoMision(mision, true, vehiculoExotico, ResultadoMision.Estado.EXITO);
        }
    }

    private void fallarMision(ResultadoMision.Estado estado) {
        enCurso = false;
        completada = false;
        resultadoMision = new ResultadoMision(
            mision,
            false,
            false,
            estado
        );
    }


    public Vehiculo getVehiculo(){
        return vehiculo;
    }

    public Jugador getJugador(){
        return jugador;
    }

    public Mapa getMapa(){
        return mapa;
    }

    public Mision getMision(){
        return mision;
    }

    public double getTiempoRestante(){
        return tiempoRestante;
    }

    public boolean estaEnCurso(){
        return enCurso;
    }

    public boolean estaCompletada(){
        return completada;
    }

    public ResultadoMision getResultado(){
        return resultadoMision;
    }
}
