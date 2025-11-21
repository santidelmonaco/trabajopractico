package org.ayed.gta;
import org.ayed.tda.vector.Vector;


public class Jugador {
    private String nombre;
    private double dinero;
    private Garaje garaje;
    
    // Constructor con parámetros
    public Jugador(String nombre, double dinero, Garaje garaje) {
        this.nombre = nombre;
        this.dinero = dinero;
        this.garaje = garaje;
    }
    public String getNombre() {
        return nombre;
    }

    public double getDinero() {
        return dinero;
    }

    public Garaje getGaraje() {
        return garaje;
    }

    public Vector<Vehiculo> getVehiculos() {
        return garaje.getVehiculos();
    }

    public int cantidadVehiculos() {
        return garaje.getCantidadVehiculos();
    }

    public void sumarDinero(double monto) {
        if (monto < 0) return;
        dinero += monto;
    }

    public boolean restarDinero(double monto) {
        if (monto < 0) return false;
        if (dinero < monto) return false;

        dinero -= monto;
        return true;
    }


    public void agregarVehiculo(Vehiculo v) {
        if (v != null) {
            garaje.agregarVehiculo(v);
        }
    }

    @Override
    public String toString() {
        return "Jugador: " + nombre +
               " | Dinero: $" + dinero +
               " | Vehículos: " + cantidadVehiculos();
    }

    public boolean guardarPartida(String archivoGaraje) {
        return garaje.guardarEnCSV(archivoGaraje);
    }

    public boolean cargarPartida(String archivoGaraje) {
        return garaje.cargarDesdeCSV(archivoGaraje);
    }

    public void aplicarResultado(ResultadoMision r) {
        if (r.estaCompletada()) {

            sumarDinero(r.getDineroGanado());
            garaje.agregarCreditos(r.getCreditosGanados());

            if (r.obtenerVehiculoExotico()) {
                Vehiculo exo = CatalogoVehiculos.crearVehiculoExotico();
                garaje.agregarVehiculo(exo);
                System.out.println("Ganaste un vehiculo exotico");
            }
        }
    }

    public void mostrarInfoJugador() {
        System.out.println("=== Información del Jugador ===");
        System.out.println("Nombre: " + nombre);
        System.out.println("Dinero: $" + dinero);

        System.out.println("\n=== Información del Garaje ===");
        System.out.println("Créditos: " + garaje.getCreditos());
        System.out.println("Capacidad máxima: " + garaje.getCapacidadMaxVehiculos());

        Vector<Vehiculo> lista = garaje.getVehiculos();
        System.out.println("Vehículos en garaje (" + lista.tamanio() + "):");

        for (int i = 0; i < lista.tamanio(); i++) {
            Vehiculo v = lista.dato(i);
            System.out.println(" - " + v.getNombre() + " (" + v.getMarca() + ")");
        }
    }

}