package org.ayed.tda.grafo;

import java.util.ArrayList;
import java.util.List;

import org.ayed.tda.colaPrioridad.ColaPrioridad;
import org.ayed.tda.comparador.Comparador;

public class AEstrella<T> {
    private ColaPrioridad<Nodo<T>> sa;
    private List<Nodo<T>> sc;
    private Nodo<T> nodoInicial;
    private Nodo<T> nodoObjetivo;
    private Grafo<T> grafo;
        
    public AEstrella(Nodo<T> nodoInicial,
        Nodo<T> nodoObjetivo,
        Comparador<Nodo<T>> comparador,
        Grafo<T> grafo) {
    
        this.sa = new ColaPrioridad<>(comparador);
        this.sc = new ArrayList<>();
        this.nodoInicial = nodoInicial;
        this.nodoObjetivo = nodoObjetivo;
        this.grafo = grafo;
    
        sa.agregar(nodoInicial);
        }
    
    public List<T> recorrer() {
        while (!sa.vacio()) {
            Nodo<T> actual = sa.eliminar();
            
            if (actual.equals(nodoObjetivo)) {
                reconstruirCamino(actual);
            }
    
            sc.add(actual);
    
            for (Nodo<T> vecino : actual.obtenerVecinos(grafo)) {
                if (sc.contains(vecino)) continue;

                double nuevoCosto = actual.obtenerCostoAcumulado() + actual.calcularCosto(vecino,grafo);

                if (!sa.contiene(vecino) || nuevoCosto < vecino.obtenerCostoAcumulado()){
                    vecino.costoAcumulado(nuevoCosto);
                    vecino.heuristica(vecino.calcularHeuristica(nodoObjetivo));
                    vecino.predecesor(actual);

                    if (!sa.contiene(vecino)) {
                        sa.agregar(vecino);
                    }
                }
            }
        }
        return new ArrayList<>();
    }
    
    private List<Nodo<T>> reconstruirCamino(Nodo<T> nodo) {
        List<Nodo<T>> camino = new ArrayList<>();
        Nodo<T> actual = nodo;
        
        while (actual != null) {
            camino.add(0, actual);
            actual = actual.obtenerPredecesor();
        }
        return camino;
    }
}
