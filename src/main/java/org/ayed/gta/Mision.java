package org.ayed.gta;
import java.util.Scanner;

public class Mision {
    private int tiempoLimite;
    private int recompensaDinero;
    private int recompensaCreditos;
    private Vehiculo vehiculoAsignado;
    private String dificultad;
    Scanner scanner = new Scanner(System.in);

    public Mision(int tiempoLimite, int recompensaCreditos, int recompensaDinero, Vehiculo vehiculoAsignado) {
        this.tiempoLimite = tiempoLimite;
        this.recompensaCreditos = recompensaCreditos;
        this.recompensaDinero = recompensaDinero;
        this.vehiculoAsignado = vehiculoAsignado;
    }

    public Mision(int tiempoLimite, int recompensaCreditos, int recompensaDinero) {
        this(tiempoLimite, recompensaCreditos, recompensaDinero, null);
    }

    public static Mision generarMisionFacil() {
        int tiempoLimite = 1000;
        int recompensaCreditos = 20;
        int recompensaDinero = 2500;
        Mision mision = new Mision(tiempoLimite, recompensaCreditos, recompensaDinero);
        mision.dificultad = "FÁCIL";
        return mision;
    }

    public static Mision generarMisionMedia(){
        int tiempoLimite = 500;
        int recompensaCreditos = 35;
        int recompensaDinero = 5000;
        Mision mision = new Mision(tiempoLimite, recompensaCreditos, recompensaDinero);
        mision.dificultad = "MEDIA";
        return mision;
    }

    public static Mision generarMisionDificil(){
        int tiempoLimite = 250;
        int recompensaCreditos = 50;
        int recompensaDinero = 10000;
        Mision mision = new Mision(tiempoLimite, recompensaCreditos, recompensaDinero);
        mision.dificultad = "DIFÍCIL";
        return mision;
    }

    public boolean asignarVehiculo(Garaje garaje) {
        System.out.println("\n=== SELECCIÓN DE VEHÍCULO PARA MISIÓN " + dificultad + " ===");
        
        mostrarRequisitosVehiculo();
        
        garaje.mostrarVehiculos();
        
        System.out.println("Ingrese el nombre del vehículo que desea usar:");
        String nombreVehiculo = scanner.nextLine();
        
        Vehiculo vehiculoSeleccionado = garaje.asignarVehiculo(nombreVehiculo);
        
        if (vehiculoSeleccionado == null) {
            System.out.println("Vehículo no encontrado.");
            return false;
        }
        
        if (validarVehiculoParaMision(vehiculoSeleccionado)) {
            this.vehiculoAsignado = vehiculoSeleccionado;
            System.out.println("Vehículo " + vehiculoSeleccionado.getNombre() + " asignado correctamente a la misión " + dificultad);
            return true;
        } else {
            System.out.println("Este vehículo no cumple con los requisitos para una misión " + dificultad);
            return false;
        }
    }

    private boolean validarVehiculoParaMision(Vehiculo vehiculo) {
        int precioVehiculo = vehiculo.getPrecio();
        
        switch (dificultad) {
            case "FÁCIL":
                return true;
                
            case "MEDIA":
                if (precioVehiculo > 15000) {
                    return true;
                } else {
                    System.out.println("Se requiere un vehículo de gama media (precio > $15,000)");
                    return false;
                }
                
            case "DIFÍCIL":
                if (precioVehiculo > 50000) {
                    return true;
                } else {
                    System.out.println("Se requiere un vehículo de alta gama (precio > $50,000)");
                    return false;
                }
            default:
                return false;
        }
    }

    private void mostrarRequisitosVehiculo() {
        System.out.println("Requisitos del vehículo:");
        switch (dificultad) {
            case "FÁCIL":
                System.out.println("Cualquier vehículo es aceptable");
                break;
            case "MEDIA":
                System.out.println("Vehículo de gama media (precio > $15,000)");
                break;
            case "DIFÍCIL":
                System.out.println("Vehículo de alta gama (precio > $50,000)");
                break;
        }
        System.out.println();
    }
}
