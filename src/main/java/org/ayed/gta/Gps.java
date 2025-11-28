package org.ayed.gta;

import org.ayed.gta.mapa.Celda;
import org.ayed.gta.mapa.Mapa;
import org.ayed.gta.mapa.TipoCelda;
import org.ayed.tda.comparador.Comparador;
import org.ayed.tda.grafo.*;
import org.ayed.tda.lista.Lista;

public class Gps {
    private Mapa mapa;
    private Grafo<Celda> grafo;
    private TipoAlgoritmo algoritmoActual;

    public Gps(Mapa mapa) {
        this.mapa = mapa;
        this.grafo = new Grafo<>();
        this.algoritmoActual = TipoAlgoritmo.A_ESTRELLA;
    }

    public void mapaAGrafo() {

        for (int fila = 0; fila < mapa.getFilas(); fila++) {
            for (int columna = 0; columna < mapa.getColumnas(); columna++) {
                Celda celdaActual = mapa.getCelda(fila, columna);
                if (celdaActual.esTransitable()) {
                    grafo.agregarVertice(celdaActual);
                }
            }
        }
        for (int fila = 0; fila < mapa.getFilas(); fila++) {
            for (int columna = 0; columna < mapa.getColumnas(); columna++) {

                Celda celdaActual = mapa.getCelda(fila, columna);
                if (!celdaActual.esTransitable())
                    continue;

                if (fila > 0) {
                    Celda celdaVecina = mapa.getCelda(fila - 1, columna);
                    if (celdaVecina.esTransitable()) {
                        String origenStr = celdaActual.getFila() + "," + celdaActual.getColumna();
                        String destinoStr = celdaVecina.getFila() + "," + celdaVecina.getColumna();
                        int peso = (int) mapa.obtenerCosto(origenStr, destinoStr);
                        grafo.agregarArista(celdaActual, celdaVecina, peso);
                    }
                }

                if (fila < mapa.getFilas() - 1) {
                    Celda celdaVecina = mapa.getCelda(fila + 1, columna);
                    if (celdaVecina.esTransitable()) {
                        String origenStr = celdaActual.getFila() + "," + celdaActual.getColumna();
                        String destinoStr = celdaVecina.getFila() + "," + celdaVecina.getColumna();
                        int peso = (int) mapa.obtenerCosto(origenStr, destinoStr);
                        grafo.agregarArista(celdaActual, celdaVecina, peso);
                    }
                }

                if (columna > 0) {
                    Celda celdaVecina = mapa.getCelda(fila, columna - 1);
                    if (celdaVecina.esTransitable()) {
                        String origenStr = celdaActual.getFila() + "," + celdaActual.getColumna();
                        String destinoStr = celdaVecina.getFila() + "," + celdaVecina.getColumna();
                        int peso = (int) mapa.obtenerCosto(origenStr, destinoStr);
                        grafo.agregarArista(celdaActual, celdaVecina, peso);
                    }
                }

                if (columna < mapa.getColumnas() - 1) {
                    Celda celdaVecina = mapa.getCelda(fila, columna + 1);
                    if (celdaVecina.esTransitable()) {
                        String origenStr = celdaActual.getFila() + "," + celdaActual.getColumna();
                        String destinoStr = celdaVecina.getFila() + "," + celdaVecina.getColumna();
                        int peso = (int) mapa.obtenerCosto(origenStr, destinoStr);
                        grafo.agregarArista(celdaActual, celdaVecina, peso);
                    }
                }

            }
        }
    }

    /**
     * Permite cambiar el algoritmo de búsqueda que usa el GPS.
     */
    public void establecerAlgoritmo(TipoAlgoritmo tipo) {
        this.algoritmoActual = tipo;
    }

    /**
     * Calcula el camino óptimo desde el origen hasta el destino
     *
     * @param origen Celda de origen
     * @param destino Celda de destino
     * @return Lista de celdas que forman el camino, o null si no existe
     */
    public Lista<Celda> calcularCamino(Celda origen, Celda destino) {
        switch (algoritmoActual) {
            case A_ESTRELLA:
                return calcularCaminoAEstrella(origen, destino);
            default:
                return null;
        }
    }

    /**
     * Calcula el camino usando A*.
     */
    private Lista<Celda> calcularCaminoAEstrella(Celda origen, Celda destino) {
        Heuristica<Celda> heuristica = new HeuristicaSinDiagonales();
        Comparador<Nodo<Celda>> comparador = new ComparadorNodoAEstrella<>();
        Nodo<Celda> inicio = new Nodo<>(origen);
        Nodo<Celda> objetivo = new Nodo<>(destino);
        AEstrella<Celda> aEstrella = new AEstrella<>(inicio, objetivo, comparador, grafo, heuristica);
        return aEstrella.recorrer();
    }

    /**
     * Actualiza la visualización del GPS en el mapa.
     * Marca todas las celdas del camino con el tipo CAMINO_GPS
     * para que se muestren en amarillo al imprimir el mapa.
     *
     * @param camino El camino calculado a visualizar
     */
    public void visualizarCaminoEnMapa(Lista<Celda> camino) {
        if (camino == null || camino.vacio()) {
            return;
        }

        limpiarVisualizacionGPS();

        // Marco cada celda del camino (excepto origen y destino)
        for (int i = 1; i < camino.tamanio() - 1; i++) {
            Celda celda = camino.dato(i);

            TipoCelda tipoOriginal = celda.getTipo();
            if (tipoOriginal == TipoCelda.CALLE ||
                    tipoOriginal == TipoCelda.CONGESTION ||
                    tipoOriginal == TipoCelda.RECOMPENSA) {

                celda.setTipo(TipoCelda.CAMINO_GPS);
            }
        }
    }

    /**
     * Limpia la visualización anterior del GPS en el mapa.
     * Restaura las celdas marcadas como CAMINO_GPS a su tipo original.
     */
    private void limpiarVisualizacionGPS() {
        for (int fila = 0; fila < mapa.getFilas(); fila++) {
            for (int columna = 0; columna < mapa.getColumnas(); columna++) {
                Celda celda = mapa.getCelda(fila, columna);

                if (celda.getTipo() == TipoCelda.CAMINO_GPS) {
                    celda.setTipo(TipoCelda.CALLE);
                }
            }
        }
    }

    /**
     * Calcula el costo total de un camino sumando los costos de todas sus aristas.
     */
    public double calcularCostoCamino(Lista<Celda> camino) {
        if (camino == null || camino.tamanio() < 2) {
            return 0;
        }

        double costoTotal = 0;

        for (int i = 0; i < camino.tamanio() - 1; i++) {
            Celda actual = camino.dato(i);
            Celda siguiente = camino.dato(i + 1);

            costoTotal += siguiente.getCostoTransito();
        }

        return costoTotal;
    }

    public Grafo<Celda> obtenerGrafo() {
        return grafo;
    }
}