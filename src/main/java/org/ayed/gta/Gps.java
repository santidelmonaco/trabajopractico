package org.ayed.gta;

import org.ayed.gta.mapa.Celda;
import org.ayed.gta.mapa.Mapa;
import org.ayed.tda.grafo.Grafo;

public class Gps {
    private Mapa mapa;
    private Grafo<Celda> grafo;

    public Gps(Mapa mapa) {
        this.mapa = mapa;
        this.grafo = new Grafo<>();
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
}