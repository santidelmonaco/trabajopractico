package org.ayed.tda.grafo;


import org.ayed.tda.colaPrioridad.ColaPrioridad;
import org.ayed.tda.comparador.Comparador;
import org.ayed.tda.iterador.Iterador;
import org.ayed.tda.lista.Lista;

public class AEstrella<T> {
    private ColaPrioridad<Nodo<T>> sa;
    private Lista<Nodo<T>> sc;
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
        this.sc = new Lista<>();
        this.nodoInicial = nodoInicial;
        this.nodoObjetivo = nodoObjetivo;
        this.grafo = grafo;
        this.heuristica = heuristica;
    
        sa.agregar(nodoInicial);
    }
    
    public Lista<T> recorrer() {
        while (!sa.vacio()) {
            Nodo<T> actual = sa.eliminar();
            
            if (actual.equals(nodoObjetivo)) {
                return reconstruirCamino(actual);
            }
    
            sc.agregar(actual);

            Iterador<Nodo<T>> iterar = actual.obtenerVecinos(grafo).iterador();
            while (iterar.haySiguiente()) {
                Nodo<T> vecino = iterar.dato();
                iterar.siguiente();

                if (listaContiene(sc, vecino)) continue;
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
        return new Lista<>();
    }

    private boolean listaContiene(Lista<Nodo<T>> lista, Nodo<T> nodo) {
    Iterador<Nodo<T>> iterar = lista.iterador();
    while (iterar.haySiguiente()) {
        // Primero verifico el dato actual
        if (iterar.dato().equals(nodo)) {
            return true;
        }
        // Luego avanzo al siguiente
        iterar.siguiente();
    }
    return false;
}
    
    private Lista<T> reconstruirCamino(Nodo<T> nodo) {
        Lista<T> camino = new Lista<>();
        Nodo<T> actual = nodo;
        
        int contador = 0;
        while (actual != null) {
            contador++;
            actual = actual.obtenerPredecesor();
        }

        actual = nodo;
        for (int i = 0; i < contador; i++) {
            camino.agregar(actual.obtenerDato(), 0);
            actual = actual.obtenerPredecesor();
        }
        return camino;
    }
}
