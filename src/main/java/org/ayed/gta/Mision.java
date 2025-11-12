package org.ayed.gta;
import java.util.Scanner;

public class Mision {
    private int tiempoLimite;
    private int recompensaDinero;
    private int recompensaCreditos;
    private int tipoVehicuiloRequerido;
    private Vehiculo vehiculoAsignado;
    Scanner scanner = new Scanner(System.in);

    public Mision(int tiempoLimite, int recompensaCreditos, int recompensaDinero, int tipoVehicuiloRequerido) {
        this.tiempoLimite = tiempoLimite;
        this.recompensaCreditos = recompensaCreditos;
        this.recompensaDinero = recompensaDinero;
        this.tipoVehicuiloRequerido = tipoVehicuiloRequerido;
    }

    public Mision generarMisionFacil() {
        tiempoLimite = 1000;
        recompensaCreditos = 20;
        recompensaDinero = 2500;
        tipoVehicuiloRequerido = 0;
        return new Mision(tiempoLimite, recompensaCreditos, recompensaDinero, tipoVehicuiloRequerido);
    }

    public Mision generarMisionMedia(){
        tiempoLimite = 500;
        recompensaCreditos = 35;
        recompensaDinero = 5000;
        tipoVehicuiloRequerido = 1;
        return new Mision(tiempoLimite, recompensaCreditos, recompensaDinero, tipoVehicuiloRequerido);
    }

    public Mision generarMisionDificil(){
        tiempoLimite = 250;
        recompensaCreditos = 50;
        recompensaDinero = 10000;
        tipoVehicuiloRequerido = 2;
        return new Mision(tiempoLimite, recompensaCreditos, recompensaDinero, tipoVehicuiloRequerido);
    }

    public void asignarVehiculo(Garaje garaje) {
        garaje.mostrarVehiculos();
        System.out.println("Ingrese el nombre del vehículo que desea usar:");
        String nombreVehiculo = scanner.nextLine();
        this.vehiculoAsignado = garaje.asignarVehiculo(nombreVehiculo);
    }
}
