package org.ayed.tda.grafo;

import org.ayed.tda.colaPrioridad.ColaPrioridad;
import org.ayed.tda.comparador.Comparador;
import org.ayed.tda.diccionario.Diccionario;
import org.ayed.tda.lista.Lista;

public class Dijkstra<T> {

    private Grafo<T> grafo;

    public Dijkstra(Grafo<T> grafo) {
        this.grafo = grafo;
    }

    public Lista<T> caminoMinimo(T origen, T destino) {

        int n = grafo.adyacencias.size();
        Diccionario<T, Integer> dist = new Diccionario<>(n, 0.7);
        Diccionario<T, T> prev = new Diccionario<>(n, 0.7);

        Comparador<T> comparador = (a, b) -> {
            Integer da = dist.obtenerValor(a);
            Integer db = dist.obtenerValor(b);
            return Integer.compare(da, db);
        };

        ColaPrioridad<T> pq = new ColaPrioridad<>(comparador);

        for (T v : grafo.adyacencias.keySet()) {
            dist.agregar(v, Grafo.INFINITO);
            prev.agregar(v, null);
        }

        dist.agregar(origen, 0);
        pq.agregar(origen);

        while (!pq.vacio()) {
            T actual = pq.eliminar();

            if (actual.equals(destino)) {
                break;
            }

            for (T vecino : grafo.obtenerAdyacentes(actual).keySet()) {

                int peso = grafo.obtenerArista(actual, vecino);
                int nuevaDist = dist.obtenerValor(actual) + peso;

                if (nuevaDist < dist.obtenerValor(vecino)) {
                    dist.agregar(vecino, nuevaDist);
                    prev.agregar(vecino, actual);

                    pq.agregar(vecino);
                }
            }
        }

        return reconstruirCamino(prev, origen, destino);
    }

    private Lista<T> reconstruirCamino(Diccionario<T, T> prev, T origen, T destino) {

        Lista<T> camino = new Lista<>();
        T actual = destino;

        if (prev.obtenerValor(destino) == null && !destino.equals(origen)) {
            return camino;
        }

        while (actual != null) {
            camino.agregar(actual, 0); 
            if (actual.equals(origen)) {
                break;
            }
            actual = prev.obtenerValor(actual);
        }

        return camino;
    }
}
