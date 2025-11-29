package org.ayed.tda.grafo;

import java.util.HashMap;
import java.util.Map;

import org.ayed.tda.lista.Lista;

public class FloydWarshall<T> {

    private final Grafo<T> grafo;
    private final Map<T, Map<T, Integer>> distancias;
    private final Map<T, Map<T, T>> siguiente;

    public FloydWarshall(Grafo<T> grafo) {
        if (grafo == null) {
            throw new ExcepcionGrafo("El grafo no puede ser nulo.");
        }
        this.grafo = grafo;
        this.distancias = new HashMap<>();
        this.siguiente = new HashMap<>();
        inicializarMatrices();
        ejecutar();
    }

    private void inicializarMatrices() {
        for (T v : grafo.adyacencias.keySet()) {
            distancias.put(v, new HashMap<>());
            siguiente.put(v, new HashMap<>());

            for (T u : grafo.adyacencias.keySet()) {
                if (v.equals(u)) {
                    distancias.get(v).put(u, 0);
                } else if (grafo.obtenerAdyacentes(v).containsKey(u)) {
                    distancias.get(v).put(u, grafo.obtenerArista(v, u));
                    siguiente.get(v).put(u, u);
                } else {
                    distancias.get(v).put(u, Grafo.INFINITO);
                }
            }
        }
    }

    private void ejecutar() {
        for (T k : grafo.adyacencias.keySet()) {
            for (T i : grafo.adyacencias.keySet()) {
                for (T j : grafo.adyacencias.keySet()) {
                    int distIK = distancias.get(i).get(k);
                    int distKJ = distancias.get(k).get(j);
                    int distIJ = distancias.get(i).get(j);

                    if (distIK != Grafo.INFINITO && 
                        distKJ != Grafo.INFINITO && 
                        distIK + distKJ < distIJ) {
                        
                        distancias.get(i).put(j, distIK + distKJ);
                        siguiente.get(i).put(j, siguiente.get(i).get(k));
                    }
                }
            }
        }
    }

    public int distanciaMinima(T origen, T destino) {
        if (!distancias.containsKey(origen) || !distancias.containsKey(destino)) {
            throw new ExcepcionGrafo("Uno o ambos vértices no existen en el grafo.");
        }
        return distancias.get(origen).get(destino);
    }

    /**
     * Reconstruye el camino mínimo entre dos vértices.
     * 
     * @param origen Vértice origen. Debe existir en el grafo.
     * @param destino Vértice destino. Debe existir en el grafo.
     * @return Lista con el camino desde origen hasta destino.
     *         Lista vacía si no existe camino.
     * @throws ExcepcionGrafo si alguno de los vértices no existe.
     */
    public Lista<T> reconstruirCamino(T origen, T destino) {
        Lista<T> camino = new Lista<>();
        
        if (!distancias.containsKey(origen) || !distancias.containsKey(destino)) {
            throw new ExcepcionGrafo("Uno o ambos vértices no existen en el grafo.");
        }

        // Verificar si existe camino
        if (distancias.get(origen).get(destino) == Grafo.INFINITO) {
            return camino; 
        }

        if (!siguiente.get(origen).containsKey(destino)) {
            return camino; 
        }
        T actual = origen;
        camino.agregar(actual);
        
        int contador = 0;
        while (!actual.equals(destino)) {
            actual = siguiente.get(actual).get(destino);
            

            if (actual == null || contador > grafo.adyacencias.size()) {
                return new Lista<>(); // Retornar lista vacía en caso de error
            }
            
            camino.agregar(actual);
            contador++;
        }
        
        return camino;
    }

    public boolean tieneCicloNegativo() {
        for (T v : grafo.adyacencias.keySet()) {
            if (distancias.get(v).get(v) < 0) {
                return true;
            }
        }
        return false;
    }

    public boolean existeCamino(T origen, T destino) {
        if (!distancias.containsKey(origen) || !distancias.containsKey(destino)) {
            throw new ExcepcionGrafo("Uno o ambos vértices no existen en el grafo.");
        }
        return distancias.get(origen).get(destino) != Grafo.INFINITO;
    }

    public Map<T, Map<T, Integer>> getDistancias() {
        return distancias;
    }
}