/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package org.ayed.gta; 
/**
 *
 * @author delmo
 */
import java.util.Scanner; 

public class Juego { // Clase principal que maneja la interacción del usuario

    private Garaje garaje; 
    private Scanner sc;    

    // Constantes de menú para mejorar la legibilidad del código
    private static final int AGREGAR_VEHICULO = 1;
    private static final int MOSTRAR_VEHICULOS = 2;
    private static final int ELIMINAR_VEHICULO = 3;
    private static final int MEJORAR_GARAJE = 4;
    private static final int AGREGAR_CREDITOS = 5;
    private static final int MOSTRAR_VALOR_TOTAL = 6;
    private static final int MOSTRAR_COSTO = 7;
    private static final int EXPORTAR = 8;
    private static final int CARGAR = 9;
    private static final int SALIR = 0;

    // Constructor
    public Juego() {
        this.garaje = new Garaje(); 
        this.sc = new Scanner(System.in); 
    }

    // Método principal que ejecuta el bucle del juego
    public void ejecutar() {
        System.out.println("Bienvenido al garaje");
        System.out.println("Tenés capacidad para " + garaje.getCapacidadMaxVehiculos() + " vehículos");

        int opcion;
        do {
            mostrarMenu(); 
            opcion = leerEntero("Ingrese opción: "); 

            // Llama al método de Garaje segun la opcion
            switch (opcion) {
                case AGREGAR_VEHICULO:
                    agregarVehiculo();
                    break;
                case MOSTRAR_VEHICULOS:
                    garaje.mostrarVehiculos(); // Llama al método de Garaje
                    break;
                case ELIMINAR_VEHICULO:
                    eliminarVehiculo();
                    break;
                case MEJORAR_GARAJE:
                    mejorarGaraje();
                    break;
                case AGREGAR_CREDITOS:
                    agregarCreditos();
                    break;
                case MOSTRAR_VALOR_TOTAL:
                    mostrarValorTotal();
                    break;
                case MOSTRAR_COSTO:
                    mostrarCosto();
                    break;
                case EXPORTAR:
                    garaje.exportarACSV();
                    break;
                case CARGAR:
                    garaje.cargarDesdeCSV();
                    break;
                case SALIR:
                    System.out.println("Adios");
                    break;
                default:
                    System.out.println("Opción inválida.");
                    break;
            }

        } while (opcion != SALIR); 
    }

    
    private void mostrarMenu() {
        System.out.println("\nMENU PRINCIPAL:");
        System.out.println("1) Agregar vehículo");
        System.out.println("2) Mostrar vehículos");
        System.out.println("3) Eliminar vehículo por nombre");
        System.out.println("4) Mejorar garaje (+1 espacio)");
        System.out.println("5) Agregar créditos");
        System.out.println("6) Mostrar valor total del garaje");
        System.out.println("7) Mostrar costo diario de mantenimiento");
        System.out.println("8) Exportar garaje a archivo");
        System.out.println("9) Cargar garaje desde archivo");
        System.out.println("0) Salir");
    }

    // --- Métodos auxiliares para interactuar con Garaje ---

    // Solicita los datos de un vehículo y lo agrega al garaje
    private void agregarVehiculo() {
        String nombre = leerTexto("Ingrese nombre del vehículo: ");
        String marca = leerTexto("Ingrese marca: ");
        int precio = leerEntero("Ingrese precio: ");
        TipoVehiculo tipo = leerTipo("Ingrese tipo (AUTO/MOTO): ");
        int capacidadGas = leerEntero("Ingrese capacidad de gasolina: ");
        int velocidadMax = leerEntero("Ingrese velocidad máxima: ");

        Vehiculo v = null;
        if (tipo == TipoVehiculo.AUTO) {
            v = new Auto(nombre, marca, precio, capacidadGas, velocidadMax);
        } else if (tipo == TipoVehiculo.MOTO) {
            v = new Moto(nombre, marca, precio, capacidadGas, velocidadMax);
        }

        try {
            garaje.agregarVehiculo(v); // Llama al método de Garaje para agregar vehículo
            System.out.println("Vehículo agregado correctamente.");
        } catch (ExcepcionGaraje e) { // Captura errores (garaje lleno o vehículo nulo)
            System.out.println("⚠️ " + e.getMessage());
        }
    }

    // Solicita nombre de vehículo y lo elimina del garaje
    private void eliminarVehiculo() {
        String nombre = leerTexto("Ingrese nombre del vehículo a eliminar: ");
        try {
            garaje.eliminarVehiculo(nombre); // Llama al método de Garaje
        } catch (ExcepcionGaraje e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    
    private void mejorarGaraje() {
        try {
            garaje.mejorarGaraje(); 
        } catch (ExcepcionGaraje e) {
            System.out.println("Error al mejorar el garaje: " + e.getMessage());
        }
    }

    
    private void agregarCreditos() {
        int creditos = leerEntero("Ingrese cantidad de créditos a agregar: ");
        try {
            garaje.agregarCreditos(creditos); // Llama a Garaje
            System.out.println("Créditos agregados correctamente.");
        } catch (ExcepcionGaraje e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    
    private void mostrarValorTotal() {
        System.out.println("Valor total del garaje: $" + garaje.obtenerValorTotal());
    }

    // Muestra el costo total de mantenimiento de los vehículos
    private void mostrarCosto() {
        System.out.println("Costo de mantenimiento diario: $" + garaje.obtenerCostoMantenimiento());
    }

    // --- Métodos de lectura y validación ---

    
    private String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return sc.nextLine();
    }

    // Lee un número entero desde consola y valida la entrada
    private int leerEntero(String mensaje) {
        System.out.print(mensaje);
        while (!sc.hasNextInt()) {
            sc.next(); // descarta entrada inválida
            System.out.print("Entrada inválida. " + mensaje);
        }
        int valor = sc.nextInt();
        sc.nextLine(); // limpia buffer
        return valor;
    }

    // Lee y valida el tipo de vehículo (AUTO o MOTO)
    private TipoVehiculo leerTipo(String mensaje) {
        while (true) {
            String tipoStr = leerTexto(mensaje).toUpperCase();
            if (tipoStr.equals("AUTO")) return TipoVehiculo.AUTO;
            if (tipoStr.equals("MOTO")) return TipoVehiculo.MOTO;
            System.out.println("Tipo inválido. Debe ser AUTO o MOTO.");
        }
    }
}
