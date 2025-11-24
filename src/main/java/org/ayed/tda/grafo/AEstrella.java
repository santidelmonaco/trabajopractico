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
    private Heuristica<T> heuristica;
        
    public AEstrella(
        Nodo<T> nodoInicial,
        Nodo<T> nodoObjetivo,
        Comparador<Nodo<T>> comparador,
        Grafo<T> grafo,
        Heuristica<T> heuristica) {
    
        this.sa = new ColaPrioridad<>(comparador);
        this.sc = new ArrayList<>();
        this.nodoInicial = nodoInicial;
        this.nodoObjetivo = nodoObjetivo;
        this.grafo = grafo;
        this.heuristica = heuristica;
    
        sa.agregar(nodoInicial);
    }
    
    public List<T> recorrer() {
        while (!sa.vacio()) {
            Nodo<T> actual = sa.eliminar();
            
            if (actual.equals(nodoObjetivo)) {
                return reconstruirCamino(actual);
            }
    
            sc.add(actual);

            for (Nodo<T> vecino : actual.obtenerVecinos(grafo)) {

                if (sc.contains(vecino)) continue;

                double costoArista = grafo.obtenerArista(
                    actual.obtenerDato(),
                    vecino.obtenerDato()
                );

                double nuevoCosto = actual.obtenerCostoAcumulado() + costoArista;

                if (!sa.contiene(vecino) || nuevoCosto < vecino.obtenerCostoAcumulado()) {
                    vecino.costoAcumulado(nuevoCosto);
                    vecino.heuristica(
                        heuristica.calcularPuntaje(vecino.obtenerDato(), nodoObjetivo.obtenerDato())
                    );
                    vecino.predecesor(actual);
                    if (!sa.contiene(vecino)) {
                        sa.agregar(vecino);
                    }
                }
            }
        }
        return new ArrayList<>();
    }
    
    private List<T> reconstruirCamino(Nodo<T> nodo) {
        List<T> camino = new ArrayList<>();
        Nodo<T> actual = nodo;
        
        while (actual != null) {
            camino.add(0, actual.obtenerDato());
            actual = actual.obtenerPredecesor();
        }
        return camino;
    }
}
