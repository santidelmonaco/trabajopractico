package org.ayed.gta.mapa;

import java.io.*;
import java.util.Random;

/**
 * Representa el mapa de la ciudad donde se desarrollan las misiones.
 */
public class Mapa {
    private Celda[][] celdas;
    private int filas;
    private int columnas;
    private Celda celdaSalida;
    private Celda celdaDestino;
    private Random random;

    // Posición actual del jugador
    private int filaJugador;
    private int columnaJugador;

    // Probabilidades para generación aleatoria
    private static final double PROB_EDIFICIO = 0.20;
    private static final double PROB_CONGESTION = 0.10;
    private static final double PROB_RECOMPENSA = 0.05;
    private static final double PROB_EXOTICO = 0.02;

    // Tamaño por defecto
    private static final int FILAS_DEFAULT = 10;
    private static final int COLUMNAS_DEFAULT = 15;

    /**
     * Constructor vacío que crea un mapa con tamaño por defecto.
     * El mapa se inicializa con todas las celdas como calles.
     */
    public Mapa() {
        this.filas = FILAS_DEFAULT;
        this.columnas = COLUMNAS_DEFAULT;
        this.random = new Random();
        inicializarCeldas();
    }

    /**
     * Constructor que crea un mapa vacío de tamaño específico.
     * Inicialmente todas las celdas son calles.
     *
     * @param filas Número de filas (altura del mapa)
     * @param columnas Número de columnas (ancho del mapa)
     */
    public Mapa(int filas, int columnas) {
        if (filas < 5 || columnas < 5) {
            throw new IllegalArgumentException("El mapa debe ser al menos de 5x5");
        }

        this.filas = filas;
        this.columnas = columnas;
        this.random = new Random();
        inicializarCeldas();
    }

    /**
     * Inicializa todas las celdas del mapa como calles.
     */
    private void inicializarCeldas() {
        this.celdas = new Celda[filas][columnas];
        for (int f = 0; f < filas; f++) {
            for (int c = 0; c < columnas; c++) {
                celdas[f][c] = new Celda(f, c, TipoCelda.CALLE);
            }
        }
    }

    // === MÉTODOS DE ACCESO A CELDAS ===

    /**
     * Obtiene una celda específica del mapa.
     *
     * @param fila Fila de la celda
     * @param columna Columna de la celda
     * @return la celda en esa posición
     * @throws IllegalArgumentException si la posición está fuera de los límites
     */
    public Celda getCelda(int fila, int columna) {
        if (!estaDentro(fila, columna)) {
            throw new IllegalArgumentException(
                    "Posición fuera del mapa: (" + fila + "," + columna + ")"
            );
        }
        return celdas[fila][columna];
    }

    /**
     * Evalúa si una posición está dentro de los límites del mapa.
     */
    public boolean estaDentro(int fila, int columna) {
        return fila >= 0 && fila < filas && columna >= 0 && columna < columnas;
    }

    public int getFilas() {
        return filas;
    }

    public int getColumnas() {
        return columnas;
    }

    public Celda getCeldaSalida() {
        return celdaSalida;
    }

    public Celda getCeldaDestino() {
        return celdaDestino;
    }

    // === MÉTODOS DE POSICIÓN DEL JUGADOR ===

    /**
     * Obtiene la posición actual del jugador como string.
     *
     * @return Posición en formato "fila,columna"
     */
    public String getPosicionActual() {
        return filaJugador + "," + columnaJugador;
    }

    /**
     * Calcula la posición destino dada una dirección.
     *
     * @param origen Posición actual en formato "fila,columna"
     * @param direccion W/A/S/D para arriba/izquierda/abajo/derecha
     * @return Posición destino en formato "fila,columna", o null si es inválida
     */
    public String obtenerDestino(String origen, String direccion) {
        String[] partes = origen.split(",");
        int fila = Integer.parseInt(partes[0]);
        int columna = Integer.parseInt(partes[1]);

        switch (direccion.toUpperCase()) {
            case "W": fila--; break;
            case "S": fila++; break;
            case "A": columna--; break;
            case "D": columna++; break;
            default: return null;
        }

        if (!estaDentro(fila, columna)) {
            return null;
        }

        if (!celdas[fila][columna].esTransitable()) {
            return null;
        }

        return fila + "," + columna;
    }

    /**
     * Obtiene el costo de transitar entre dos celdas.
     *
     * @param origen Posición origen "fila,columna"
     * @param destino Posición destino "fila,columna"
     * @return Costo de la celda destino
     */
    public double obtenerCosto(String origen, String destino) {
        String[] partes = destino.split(",");
        int fila = Integer.parseInt(partes[0]);
        int columna = Integer.parseInt(partes[1]);

        return celdas[fila][columna].getCostoTransito();
    }

    /**
     * Evalua si el movimiento es posible de acuerdo a la configuracion del mapa
     * 
     * @param direccion Direccion solicitada para el movimiento
     * @return true si el movimiento fue válido
     */

    public boolean movimientoExitoso(String direccion){
        String origen = getPosicionActual();
        String destino = obtenerDestino(origen, direccion);
        
        if(destino == null){
            return false;
        }

        String[] partes = destino.split(",");
        int fila = Integer.parseInt(partes[0]);
        int columna = Integer.parseInt(partes[1]);
        return true;
    }

    /**
     *Evalua si en la celda actual se encuentra un vehiculo exotico
     */
    public boolean esVehiculoExotico(Celda actual){
        return actual.getRecompensa() == TipoRecompensa.VEHICULO_EXOTICO;
    }

    // === GENERACIÓN ALEATORIA ===

    /**
     * Genera el mapa aleatoriamente según las probabilidades definidas.
     * Este método crea edificios, congestiones y recompensas de forma aleatoria.
     */
    public void generarAleatorio() {
        // Primero genero el terreno básico
        for (int f = 0; f < filas; f++) {
            for (int c = 0; c < columnas; c++) {
                double rand = random.nextDouble();

                if (rand < PROB_EDIFICIO) {
                    celdas[f][c].setTipo(TipoCelda.EDIFICIO);
                } else if (rand < PROB_EDIFICIO + PROB_CONGESTION) {
                    celdas[f][c].setTipo(TipoCelda.CONGESTION);
                } else if (rand < PROB_EDIFICIO + PROB_CONGESTION + PROB_RECOMPENSA) {
                    // Determino el tipo de recompensa
                    TipoRecompensa tipoRecomp;
                    if (random.nextDouble() < PROB_EXOTICO) {
                        tipoRecomp = TipoRecompensa.VEHICULO_EXOTICO;
                    } else if (random.nextBoolean()) {
                        tipoRecomp = TipoRecompensa.DINERO;
                    } else {
                        tipoRecomp = TipoRecompensa.CREDITOS;
                    }
                    celdas[f][c].setRecompensa(tipoRecomp);
                }
            }
        }

        // Genero salida y destino
        generarSalidaYDestino();

        // Verifico que existe un camino entre salida y destino
        // Si no existe, regeneramos el mapa
        if (!existeCamino()) {
            System.out.println("No hay camino, regenerando mapa...");
            limpiarMapa();
            generarAleatorio();
        }
    }

    /**
     * Genera puntos de salida y destino aleatorios en celdas transitables.
     */
    private void generarSalidaYDestino() {
        // Limpio salida y destino anteriores si existen
        if (celdaSalida != null) {
            celdaSalida.setTipo(TipoCelda.CALLE);
        }
        if (celdaDestino != null) {
            celdaDestino.setTipo(TipoCelda.CALLE);
        }

        // Genero salida en una celda válida
        do {
            int f = random.nextInt(filas);
            int c = random.nextInt(columnas);
            celdaSalida = celdas[f][c];
        } while (!celdaSalida.getTipo().esValidaParaSpawn());

        celdaSalida.setTipo(TipoCelda.SALIDA);

        // Genero destino en una celda valida

        do {
            int f = random.nextInt(filas);
            int c = random.nextInt(columnas);
            celdaDestino = celdas[f][c];
        } while (!celdaDestino.getTipo().esValidaParaSpawn());

        celdaDestino.setTipo(TipoCelda.DESTINO);
    }

    /**
     * Limpia el mapa convirtiéndolo todo en calles.
     */
    private void limpiarMapa() {
        for (int f = 0; f < filas; f++) {
            for (int c = 0; c < columnas; c++) {
                celdas[f][c].setTipo(TipoCelda.CALLE);
            }
        }
    }

    /**
     * Verifica si existe al menos un camino entre la salida y el destino.
     *
     * @return true si existe un camino
     */
    private boolean existeCamino() {
        // Por ahora retorno true y lo implemento después
        // cuando tenga los algoritmos de buscar camino listos
        return true;
    }

    // === CARGA DESDE ARCHIVO ===

    /**
     * Carga un mapa desde un archivo de texto.
     *
     * Formato del archivo:
     * Línea 1: filas columnas
     * Líneas siguientes: caracteres representando cada celda
     *
     * Caracteres válidos:
     * '.' = Calle
     * '#' = Edificio
     * 'S' = Salida
     * 'D' = Destino
     * 'X' = Congestión
     * '$' = Recompensa (se genera tipo aleatorio)
     *
     * @param nombreArchivo Ruta del archivo a cargar
     */
    public Mapa cargarDesdeArchivo(String nombreArchivo) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(nombreArchivo))) {

            String primeraLinea = reader.readLine();
            if (primeraLinea == null) {
                throw new IOException("Archivo vacío");
            }

            String[] dimensiones = primeraLinea.trim().split("\\s+");
            if (dimensiones.length < 2) {
                throw new IOException("Primera línea debe contener: filas columnas");
            }

            int filas = Integer.parseInt(dimensiones[0]);
            int columnas = Integer.parseInt(dimensiones[1]);

            Mapa mapa = new Mapa(filas, columnas);

            // Leo el mapa línea por línea
            for (int f = 0; f < filas; f++) {
                String linea = reader.readLine();
                if (linea == null) {
                    throw new IOException("El archivo tiene menos filas de las especificadas");
                }

                // Proceso cada carácter de la línea
                for (int c = 0; c < Math.min(columnas, linea.length()); c++) {
                    char simbolo = linea.charAt(c);
                    Celda celda = mapa.celdas[f][c];

                    switch (simbolo) {
                        case '.':
                            celda.setTipo(TipoCelda.CALLE);
                            break;
                        case '#':
                            celda.setTipo(TipoCelda.EDIFICIO);
                            break;
                        case 'S':
                            celda.setTipo(TipoCelda.SALIDA);
                            mapa.celdaSalida = celda;
                            break;
                        case 'D':
                            celda.setTipo(TipoCelda.DESTINO);
                            mapa.celdaDestino = celda;
                            break;
                        case 'X':
                            celda.setTipo(TipoCelda.CONGESTION);
                            break;
                        case '$':
                            // Genero un tipo de recompensa aleatorio
                            TipoRecompensa tipo = mapa.random.nextBoolean() ?
                                    TipoRecompensa.DINERO : TipoRecompensa.CREDITOS;
                            celda.setRecompensa(tipo);
                            break;
                        default:
                            // Carácter desconocido, lo trato como calle
                            celda.setTipo(TipoCelda.CALLE);
                    }
                }
            }

            // Verificamos que se hayan definido salida y destino
            if (mapa.celdaSalida == null || mapa.celdaDestino == null) {
                throw new IOException("El mapa debe tener una salida (S) y un destino (D)");
            }

            System.out.println("Mapa cargado: " + filas + "x" + columnas);
            return mapa;

        } catch (NumberFormatException e) {
            throw new IOException("Error al leer las dimensiones del mapa");
        }
    }

    /**
     * Guarda el mapa actual en un archivo de texto.
     *
     * @param nombreArchivo Ruta donde guardar el archivo
     */
    public void guardarEnArchivo(String nombreArchivo) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(nombreArchivo))) {

            // Escribo dimensiones
            writer.println(filas + " " + columnas);

            // Escribimos el mapa
            for (int f = 0; f < filas; f++) {
                for (int c = 0; c < columnas; c++) {
                    writer.print(celdas[f][c].getSimbolo());
                }
                writer.println();
            }

            System.out.println("Mapa guardado en: " + nombreArchivo);
        }
    }
}