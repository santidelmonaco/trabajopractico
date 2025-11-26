package org.ayed.gta;
import org.ayed.gta.mapa.*;

/**
 * Controlador de mision que administra el progreso, el movimiento del vehiculo y el estado final de la mision
 */
public class ControlMision{
    private Vehiculo vehiculo;
    private Jugador jugador;
    private Mapa mapa;
    private Mision mision;
    private double tiempoRestante;
    private boolean enCurso;
    private boolean completada;
    private ResultadoMision resultadoMision;

    public ControlMision(Vehiculo vehiculo, Jugador jugador, Mapa mapa, Mision mision){
        this.vehiculo = vehiculo;
        this.jugador = jugador;
        this.mapa = mapa;
        this.mision = mision;
        this.tiempoRestante = mision.getTiempoLimite();
        this.enCurso = false;
        this.completada = false;
        this.resultadoMision = new ResultadoMision(mision, false, false, ResultadoMision.Estado.EN_CURSO);
    }

    /**
     * Inicia la mision
     * Marca la mision como en curso, inicializa la posición del jugador y reinicia el resultado parcial dejando todo para arrancar
     */
    public void iniciarMision(){
        // Inicializo la posición del jugador en la salida del mapa
        mapa.inicializarPosicionJugador();
        enCurso = true;
        resultadoMision = new ResultadoMision(mision, false, false, ResultadoMision.Estado.EN_CURSO);
    }

    /**
     * Intenta mover el vehiculo a la direccion indicada
     * Este metodo coordina todo el flujo:
     * -Verifica que la mision este activa
     * -Verifica si hay gasolina disponible
     * -Verifica si el movimiento es posible en el mapa
     * -Actualiza gasolina, kilometraje y tiempo restante
     * -Verifica si hay tiempo restante
     * -Comprueba si el jugador alcanzo la celda destino
     *
     * @param direccion Direccion a la cual se intenta mover
     */
    public void moverVehiculo(String direccion){
        if(!enCurso) return;

        if(verificarGasolina()) return;
        if(!verificarDestino(direccion)) return;

        moverYActualizar(direccion);
        verificarYRecogerRecompensa();
        verificarTiempo();
        verificarCompletada();

    }

    /**
     * Verifica si el vehiculo tiene gasolina disponible
     * Si no tiene gasolina la mision se marca como fallida con el estado FALLO_GASOLINA
     *
     * @return true si la mision debe detenerse por falta de gasolina, false en caso contrario
     */
    private boolean verificarGasolina(){
        if(vehiculo.getGasolinaActual() <= 0){
            fallarMision(ResultadoMision.Estado.FALLO_GASOLINA);
            return true;
        }
        return false;
    }

    /**
     * Valida si existe un movimiento posible en esa direccion en el mapa
     * Si no puede moverse la mision se marca como fallida con el estado FALLO_MOVIMIENTO
     *
     * @param direccion Direccion a la cual se intenta mover
     * @return true si el movimiento es valido y fue realizado, false si falla
     */
    private boolean verificarDestino(String direccion){
        String origen = mapa.getPosicionActual();
        String destino = mapa.obtenerDestino(origen, direccion);
        if(destino == null){
            fallarMision(ResultadoMision.Estado.FALLO_MOVIMIENTO);
            return false;
        }

        boolean movimiento = mapa.movimientoExitoso(direccion);
        if(!movimiento){
            fallarMision(ResultadoMision.Estado.FALLO_MOVIMIENTO);
            return false;
        }
        return true;
    }

    /**
     * Actualiza el estado del vehiculo y el tiempo de la mision
     * -Consume 1 de gasolina
     * -Aumenta en 1 el kilometraje
     * -Calcula el tiempo consumido en base al costo de la calle y la velocidad del vehiculo
     *
     * @param direccion Direccion a la cual se mueve
     */
    private void moverYActualizar(String direccion){
        vehiculo.consumirGasolina(1);
        vehiculo.sumarKilometraje(1);

        String origen = mapa.getPosicionActual();
        String destino = mapa.obtenerDestino(origen, direccion);

        String[] partes = destino.split(",");
        int nuevaFila = Integer.parseInt(partes[0]);
        int nuevaColumna = Integer.parseInt(partes[1]);

        // Actualizo la posición del jugador en el mapa
        mapa.actualizarPosicionJugador(nuevaFila, nuevaColumna);

        double costo = mapa.obtenerCosto(origen, destino);
        double tiempoCalle = costo/vehiculo.getVelocidadMaxima();
        tiempoRestante -= tiempoCalle;
    }

    /**
     * Verifica si el tiempo de la mision esta en 0 o menos
     * En caso afirmativo, la mision se marca como falida con el estado FALLO_TIEMPO
     */
    private void verificarTiempo(){
        if(tiempoRestante <= 0){
            fallarMision(ResultadoMision.Estado.FALLO_TIEMPO);
        }
    }

    /**
     * Verifica si el jugador alcanzo la celda destino de la mision
     * Si se alcanza:
     * -La mision se marca como completada
     * -Se consulta en el mapa si se obtiene un vehiculo exotico
     * -Se genera un resultado mision con estado EXITO
     */
    private void verificarCompletada(){
        String posActual = mapa.getPosicionActual();
        Celda destino = mapa.getCeldaDestino();
        String posDestino = destino.getFila() + "," + destino.getColumna();

        if(posActual.equals(posDestino)){
            completada = true;
            enCurso = false;
            boolean vehiculoExotico = mapa.esVehiculoExotico(destino);
            resultadoMision = new ResultadoMision(mision, true, vehiculoExotico, ResultadoMision.Estado.EXITO);
        }
    }

    /**
     * Marca la mision como fallida, registrando el tipo de fallo
     * Detiene la mision inmediatamente y genera un resultado mision con el estado del fallo
     *
     * @param estado Motivo del fallo de la mision
     */
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

    /**
     * Obtiene el vehiculo utilizado en la mision
     *
     * @return Vehiculo usado
     */
    public Vehiculo getVehiculo(){
        return vehiculo;
    }

    /**
     * Devuelve el jugador que participa en la mision
     *
     * @return Jugador participante
     */
    public Jugador getJugador(){
        return jugador;
    }

    /**
     * Retorna el mapa sobre el cual se realiza la mision
     *
     * @return Mapa actual jugandose
     */
    public Mapa getMapa(){
        return mapa;
    }

    /**
     * Obtiene la mision que se esta ejecutando
     *
     * @return Mision jugandose
     */
    public Mision getMision(){
        return mision;
    }

    /**
     * Obtiene el tiempo restante de la mision
     *
     * @return Tiempo restante para completar la mision
     */
    public double getTiempoRestante(){
        return tiempoRestante;
    }

    /**
     * Indica si la mision esta actualmente en curso
     *
     * @return true si sigue en curso, false en caso contrario
     */
    public boolean estaEnCurso(){
        return enCurso;
    }

    /**
     * Indica si la mision fue completada exitosamente
     *
     * @return true si se completo, false en caso contrario
     */
    public boolean estaCompletada(){
        return completada;
    }

    /**
     * Devuelve el resultado actual o final de la mision
     *
     * @return Resultado de la mision con informacion del progreso finalizacion
     */
    public ResultadoMision getResultado(){
        return resultadoMision;
    }

    /**
     * Verifica y recoge cualquier recompensa en la celda actual del jugador.
     * Este método debe llamarse después de cada movimiento exitoso.
     */
    private void verificarYRecogerRecompensa() {
        Celda celdaActual = mapa.getCeldaJugador();

        // Solo proceso si la celda tiene una recompensa y aún no ha sido recolectada
        if (celdaActual.getTipo() == TipoCelda.RECOMPENSA &&
                !celdaActual.isRecompensaRecolectada()) {

            TipoRecompensa tipoRecompensa = celdaActual.getRecompensa();

            // Genero la cantidad apropiada según el tipo
            int cantidad = tipoRecompensa.generarCantidad();

            // Aplico la recompensa según su tipo
            switch (tipoRecompensa) {
                case DINERO:
                    jugador.sumarDinero(cantidad);
                    System.out.println("\nHas recogido $" + cantidad + " en efectivo!");
                    break;

                case CREDITOS:
                    jugador.getGaraje().agregarCreditos(cantidad);
                    System.out.println("\nHas recogido " + cantidad + " créditos de garaje!");
                    break;

                case VEHICULO_EXOTICO:
                    System.out.println("\nHas encontrado un vehículo exótico!");
                    System.out.println("Lo recibirás si completas la misión exitosamente.");
                    // El vehículo exótico solo se otorga al completar la misión
                    resultadoMision = new ResultadoMision(
                            mision,
                            false,
                            true,  // Marco que hay vehículo exótico disponible
                            ResultadoMision.Estado.EN_CURSO
                    );
                    break;
            }

            // Marco la recompensa como recogida
            celdaActual.recolectarRecompensa();
        }
    }
}
