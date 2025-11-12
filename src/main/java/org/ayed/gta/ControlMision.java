package org.ayed.gta;

public class ControlMision{
    protected Vehiculo vehiculo;
    protected Jugador jugador;
    protected Mapa mapa;
    protected Mision mision;
    protected double tiempoRestante;
    protected boolean enCurso;
    protected boolean completada;

    public ControlMision(Vehiculo vehiculo, Jugador jugador, Mapa mapa, Mision mision){
        this.vehiculo = vehiculo;
        this.jugador = jugador;
        this.mapa = mapa;
        this.mision = mision;
        this.tiempoRestante = mision.getTiempoLimite();
        this.enCurso = false;
        this.completada = false;
    }

    public void iniciarMision(){
        enCurso = true;
    }

    public void moverVehiculo(String direccion){
        if(!enCurso){
            return;
        }
        if (vehiculo.getGasolinaActual() <= 0){
            return;
        }

        String origen = mapa.getPosicionActual();
        String destino = mapa.obtenerDestino(origen, direccion);
        if(destino == null){
            return;
        }


        double costo = mapa.obtenerCosto(origen, destino);
        double tiempoCalle = costo/vehiculo.getVelocidadMaxima();

        boolean movimientoExitoso = mapa.moverVehiculo(direccion);
        if(movimientoExitoso){
            vehiculo.consumirGasolina(1);
            vehiculo.sumarKilometraje(1);
            tiempoRestante -= tiempoCalle;

            if(tiempoRestante <= 0){
                enCurso = false;
            } else if(mision.verificarCompletada(mapa.getPosicionActual())){
                completada = true;
                enCurso = false;
            }
        }
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
}
