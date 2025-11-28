package org.ayed.gta;

import java.io.IOException;
import java.util.Scanner;

import org.ayed.gta.mapa.Mapa;

public class Juego {

    private Jugador jugador;
    private Concesionario concesionario;
    private Mapa mapa;
    private ControlMision control;
    private boolean juegoActivo;
    private Scanner sc;

    public Juego() {
        this.sc = new Scanner(System.in);
        this.juegoActivo = false;
    }

    public void ejecutar() {
        mostrarMenuPrincipal();

        while (juegoActivo) {
            mostrarEstadoJugador();
            mostrarOpcionesPrincipal();

            int opcion = leerEntero("Seleccione una opción: ");

            switch (opcion) {
                case 1:
                    System.out.println("\n>> Entrando al concesionario...");
                    irAlConcesionario();
                    break;

                case 2:
                    System.out.println("\n>> Abriendo el garaje...");
                    administrarGaraje();
                    break;

                case 3:
                    System.out.println("\n>> Iniciando misión...");
                    iniciarMision();
                    break;

                case 4:
                    System.out.println("\n>> Guardando partida y saliendo del juego...");
                    guardarPartida();
                    juegoActivo = false;
                    break;

                default:
                    System.out.println("Opción inválida. Intente nuevamente.");
                    break;
            }
        }

        System.out.println("Fin del juego.");
    }

    private void mostrarMenuPrincipal() {
        System.out.println("=== MENU PRINCIPAL ===");
        System.out.println("1. Nueva partida");
        System.out.println("2. Cargar partida");

        int opcion = leerEntero("Seleccione una opción: ");

        if (opcion == 1) {
            // Crear nuevo garaje vacío
            Garaje nuevoGaraje = new Garaje();
            // Crear nuevo jugador con nombre ingresado
            String nombre = leerTexto("Ingrese el nombre del jugador: ");
            jugador = new Jugador(nombre, Jugador.DINERO_INICIAL, nuevoGaraje);
            System.out.println("Nueva partida iniciada para " + nombre + ".");
        } else if (opcion == 2) {
            String nombreArchivo = leerTexto("Ingrese el nombre del archivo de guardado: ");
            Garaje garajeCargado = new Garaje();
            jugador = new Jugador("Jugador", 0, garajeCargado);
            boolean cargado = jugador.cargarPartida(nombreArchivo);
            if (!cargado) {
                System.out.println("No se pudo cargar la partida. Iniciando nueva...");
                jugador = new Jugador("Jugador", Jugador.DINERO_INICIAL, new Garaje());
            }
        } else {
            System.out.println("Opción inválida, iniciando nueva partida.");
            jugador = new Jugador("Jugador", Jugador.DINERO_INICIAL, new Garaje());
        }

        concesionario = CatalogoVehiculos.crearConcesionarioCompleto();
        juegoActivo = true;
    }

    private void mostrarEstadoJugador() {
        System.out.println("Jugador: " + jugador.getNombre());
        System.out.println("Dinero disponible: $" + jugador.getDinero());
        System.out.println("Vehículos en garaje: " + jugador.getGaraje().getVehiculos().tamanio()
                + "/" + jugador.getGaraje().getCapacidadMaxVehiculos());
    }

    private void mostrarOpcionesPrincipal() {
        System.out.println("\n MENÚ PRINCIPAL ");
        System.out.println("1. Ir al Concesionario");
        System.out.println("2. Ver Garaje");
        System.out.println("3. Iniciar misión");
        System.out.println("4. Guardar y salir");
        System.out.print("Seleccione una opción: ");
    }

    private void irAlConcesionario() {
        boolean enConcesionario = true;

        while (enConcesionario) {
            System.out.println(concesionario.toString());
            System.out.println("1. Comprar vehículo");
            System.out.println("2. Buscar por nombre");
            System.out.println("3. Buscar por marca");
            System.out.println("4. Volver al menú principal");

            int opcion = leerEntero("Seleccione una opción: ");

            switch (opcion) {
                case 1: {
                    String nombreCompra = leerTexto("Ingrese el nombre exacto del vehículo que desea comprar: ");
                    Vehiculo vehiculoAComprar = concesionario.comprarVehiculo(nombreCompra);

                    if (vehiculoAComprar == null) {
                        System.out.println("No se encontró el vehículo \"" + nombreCompra + "\" en el concesionario.");
                    } else if (jugador.getDinero() < vehiculoAComprar.getPrecio()) {
                        System.out.println("No tienes suficiente dinero para comprar este vehículo.");
                        concesionario.agregarVehiculo(vehiculoAComprar);
                    } else {
                        jugador.restarDinero(vehiculoAComprar.getPrecio());
                        jugador.getGaraje().agregarVehiculo(vehiculoAComprar);
                        System.out.println("¡Has comprado el " + vehiculoAComprar.getNombre() + " por $"
                                + vehiculoAComprar.getPrecio() + "!");
                    }
                    break;
                }

                case 2: {
                    String busquedaNombre = leerTexto("Ingrese parte del nombre a buscar: ");
                    Vehiculo[] resultadosNombre = concesionario.buscarPorNombre(busquedaNombre);
                    mostrarResultadosBusqueda(resultadosNombre);
                    break;
                }

                case 3: {
                    String busquedaMarca = leerTexto("Ingrese parte de la marca a buscar: ");
                    Vehiculo[] resultadosMarca = concesionario.buscarPorMarca(busquedaMarca);
                    mostrarResultadosBusqueda(resultadosMarca);
                    break;
                }

                case 4:
                    enConcesionario = false;
                    break;

                default:
                    System.out.println("Opción inválida, intente nuevamente.");
                    break;
            }
        }
    }

    private void mostrarResultadosBusqueda(Vehiculo[] resultados) {
        if (resultados == null || resultados.length == 0) {
            System.out.println("No se encontraron vehículos con ese criterio.");
            return;
        }

        System.out.println("=== Resultados de búsqueda ===");
        for (Vehiculo v : resultados) {
            System.out.printf("%-20s | Marca: %-15s | Precio: $%-8d | Tipo: %s\n",
                    v.getNombre(), v.getMarca(), v.getPrecio(), v.getClass().getSimpleName());
        }
    }

    private void administrarGaraje() {
        boolean volver = false;

        while (!volver) {
            System.out.println("\n MENÚ DEL GARAJE");
            System.out.println("1. Ver vehículos");
            System.out.println("2. Eliminar vehículo");
            System.out.println("3. Mejorar garaje");
            System.out.println("4. Cargar tanques al máximo");
            System.out.println("5. Cargar tanque individual");
            System.out.println("6. Ver créditos y capacidad");
            System.out.println("7. Volver al menú principal");

            int opcion = leerEntero("Seleccione una opción: ");
            Garaje garaje = jugador.getGaraje();

            switch (opcion) {
                case 1:
                    garaje.mostrarVehiculos();
                    break;

                case 2:
                    String nombre = leerTexto("Ingrese el nombre del vehículo a eliminar: ");
                    try {
                        garaje.eliminarVehiculo(nombre);
                    } catch (ExcepcionGaraje e) {
                        System.out.println(" " + e.getMessage());
                    }
                    break;

                case 3:
                    try {
                        garaje.mejorarGaraje();
                    } catch (ExcepcionGaraje e) {
                        System.out.println(" " + e.getMessage());
                    }
                    break;

                case 4:
                    garaje.cargarTanquesMaximo(jugador);
                    break;

                case 5:
                    garaje.mostrarVehiculos();
                    String nombre_2 = leerTexto("Elija el vehiculo al cual quiere cargarle combustible: ");
                    int cantidad = leerEntero("Cantidad a cargar: ");
                    try {
                        garaje.cargarTanque(jugador, nombre_2, cantidad);
                    } catch (ExcepcionGaraje e) {
                        System.out.println(" " + e.getMessage());
                    }
                    break;
                case 6:
                    System.out.println("Créditos: " + garaje.getCreditos());
                    System.out.println("Capacidad máxima: " + garaje.getCapacidadMaxVehiculos());
                    System.out.println("Valor total vehículos: " + garaje.obtenerValorTotal());
                    System.out.println("Costo de mantenimiento total: " + garaje.obtenerCostoMantenimiento());
                    break;

                case 7:
                    volver = true;
                    System.out.println("Volviendo al menú principal");
                    break;

                default:
                    System.out.println("Opción inválida. Intente nuevamente.");
            }
        }
    }

    public void iniciarMision() {
        boolean seleccionar = true;

        while (seleccionar) {
            mostrarMenuDificultad();

            int opcion = leerEntero("Seleccione una opción: ");
            Mision mision = seleccionarMision(opcion);

            if (mision == null) {
                return;
            }
            boolean asignado = mision.asignarVehiculo(jugador.getGaraje());
            if (!asignado) {
                continue;
            }

            mapa = crearMapaMision();

            System.out.println("Inicializando sistema GPS...");
            Gps gps = new Gps(mapa);
            gps.mapaAGrafo();
            System.out.println("GPS listo. Calculando ruta óptima...");

            control = new ControlMision(
                    mision.getVehiculoAsignado(),
                    jugador,
                    mapa,
                    mision,
                    gps);

            // Inicializo la posición del jugador en la salida del mapa
            mapa.inicializarPosicionJugador();
            control.iniciarMision();
            mapa.imprimirMapa();

            while (control.estaEnCurso()) {
                mostrarInformacionMision(control);
                char direccion = leerDireccionMovimiento();
                control.moverVehiculo(String.valueOf(direccion));

                // Actualizar posición del jugador y mostrar mapa actualizado
                String posicionActual = mapa.getPosicionActual(); // "3,4"
                String[] partes = posicionActual.split(",");

                int fila = Integer.parseInt(partes[0]);
                int columna = Integer.parseInt(partes[1]);

                mapa.actualizarPosicionJugador(fila, columna);
                mapa.imprimirMapa();

                verificarEventosDelMapa(control);
            }

            ResultadoMision resultado = control.getResultado();

            jugador.aplicarResultado(resultado);

            mostrarResumenFinal(resultado);

            aplicarMantenimientoDiario();

            seleccionar = false;
        }
    }

    private void mostrarMenuDificultad() {
        System.out.println("\n SELECCIONE LA DIFICULTAD DE LA MISIÓN");
        System.out.println("1. Misión fácil    (Tiempo: 1000s | Recompensa: $2500 | Créditos: 20)");
        System.out.println("2. Misión media   (Tiempo: 500s  | Recompensa: $5000 | Créditos: 35)");
        System.out.println("3. Misión difícil (Tiempo: 250s  | Recompensa: $10000| Créditos: 50)");
        System.out.println("4. Volver");
    }

    private Mision seleccionarMision(int opcion) {
        switch (opcion) {
            case 1:
                return Mision.generarMisionFacil();

            case 2:
                return Mision.generarMisionMedia();

            case 3:
                return Mision.generarMisionDificil();

            case 4:
                System.out.println("Volviendo al menú");
                return null;

            default:
                System.out.println("Opción inválida.");
                return null;
        }
    }

    private Mapa crearMapaMision() {
        System.out.println("CARGAR MAPA DE LA MISIÓN ");

        String nombreArchivo = leerTexto("Ingrese el nombre del archivo del mapa: ");

        try {
            Mapa mapa = new Mapa();
            mapa = mapa.cargarDesdeArchivo(nombreArchivo);
            System.out.println("Mapa cargado");
            return mapa;

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }

    private void mostrarInformacionMision(ControlMision control) {
        System.out.println("\n ESTADO DE LA MISIÓN");
        System.out.println("Dificultad: " + control.getMision().getDificultad());
        System.out.println("Tiempo restante: " + control.getTiempoRestante() + " segundos");

        Vehiculo v = control.getVehiculo();
        System.out.println("Vehículo: " + v.getNombre() +
                " | Combustible: " + v.getCombustibleActual() + "/" + v.getCombustibleMax());
        // Funcion para imprimir Mapa
    }

    private char leerDireccionMovimiento() {
        System.out.print("Mover (W/A/S/D): ");

        String input = sc.nextLine().trim().toUpperCase();

        while (input.length() != 1 || "WASD".indexOf(input.charAt(0)) == -1) {
            System.out.print("Entrada inválida. Use W/A/S/D: ");
            input = sc.nextLine().trim().toUpperCase();
        }

        return input.charAt(0);
    }

    private void verificarEventosDelMapa(ControlMision control) {
        ResultadoMision r = control.getResultado();
        ResultadoMision.Estado estado = r.getEstado();

        switch (estado) {
            case FALLO_MOVIMIENTO:
                System.out.println("Movimiento inválido.");
                break;

            case FALLO_GASOLINA:
                System.out.println("Sin gasolina.");
                break;

            case FALLO_TIEMPO:
                System.out.println("Se acabó el tiempo.");
                break;

            case EXITO:
                System.out.println("Objetivo alcanzado.");
                break;

            default:
                break;
        }
    }

    private void mostrarResumenFinal(ResultadoMision resultado) {
        System.out.println("\n RESULTADO DE LA MISIÓN");

        if (resultado.estaCompletada()) {
            System.out.println("Misión completada");
        } else {
            System.out.println("Misión fallida.");
        }

        System.out.println("Dinero ganado: $" + resultado.getDineroGanado());
        System.out.println("Créditos ganados: " + resultado.getCreditosGanados());

        if (resultado.obtenerVehiculoExotico()) {
            System.out.println("Vehículo exótico obtenido");
        }
    }

    private void guardarPartida() {
        String nombreArchivo = jugador.getNombre() + "_partida.txt";
        if (jugador.guardarPartida(nombreArchivo)) {
            System.out.println(">> Partida guardada correctamente en: " + nombreArchivo);
        } else {
            System.out.println(" Error al guardar la partida.");
        }
    }

    private String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return sc.nextLine();
    }

    private int leerEntero(String mensaje) {
        System.out.print(mensaje);
        while (!sc.hasNextInt()) {
            sc.next();
            System.out.print("Entrada inválida. " + mensaje);
        }
        int valor = sc.nextInt();
        sc.nextLine();
        return valor;
    }

    private void aplicarMantenimientoDiario(){
        int costo = jugador.getGaraje().obtenerCostoMantenimiento();

        System.out.println("Costo total de mantenimiento diario $" + costo);
        
        if(jugador.getDinero() < costo){
            System.out.println("\nNo tenes suficiente dinero para pagar el mantenimiento");
            System.out.println("Has perdido la partida");
            juegoActivo = false;
            return;
        }
        jugador.restarDinero(costo);
        System.out.println("Pago realizado correctamente");
        System.out.println("\nDinero restante: " + jugador.getDinero());
    }
}
