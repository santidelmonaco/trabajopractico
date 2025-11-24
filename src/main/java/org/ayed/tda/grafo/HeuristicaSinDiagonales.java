package org.ayed.tda.grafo;

import org.ayed.gta.mapa.Celda;

public class HeuristicaSinDiagonales implements Heuristica<Celda> {

    public int calcularPuntaje(Celda origen, Celda destino) {
        int filaOrigen = origen.getFila();
        int colOrigen = origen.getColumna();
        int filaDestino = destino.getFila();
        int colDestino = destino.getColumna();
        return Math.abs(filaOrigen - filaDestino) + Math.abs(colOrigen - colDestino);
    }
}
