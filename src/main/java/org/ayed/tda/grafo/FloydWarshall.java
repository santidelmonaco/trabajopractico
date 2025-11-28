package org.ayed.tda.grafo;

import java.util.HashMap;
import java.util.Map;

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
                    // Distancia de un vértice a si mismo es 0
                    distancias.get(v).put(u, 0);
                } else if (grafo.obtenerAdyacentes(v).containsKey(u)) {
                    // Existe una arista directa
                    distancias.get(v).put(u, grafo.obtenerArista(v, u));
                    siguiente.get(v).put(u, u);
                } else {
                    // No hay conexion directa
                    distancias.get(v).put(u, Grafo.INFINITO);
                }
            }
        }
    }
    private void ejecutar() {
        // Para cada vértice intermedio k
        for (T k : grafo.adyacencias.keySet()) {
            // Para cada vértice origen i
            for (T i : grafo.adyacencias.keySet()) {
                // Para cada vértice destino j
                for (T j : grafo.adyacencias.keySet()) {

                    int distIK = distancias.get(i).get(k);
                    int distKJ = distancias.get(k).get(j);
                    int distIJ = distancias.get(i).get(j);

                    // Verificar que no haya infinitos antes de sumar
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

    /**
     *distancia mínima entre dos vértices.
     * 
     * @param origen Vértice origen. Debe existir en el grafo.
     * @param destino Vértice destino. Debe existir en el grafo.
     * @return La distancia mínima, o Grafo.INFINITO si no hay camino.
     * @throws ExcepcionGrafo si alguno de los vértices no existe.
     */
    public int distanciaMinima(T origen, T destino) {
        if (!distancias.containsKey(origen) || !distancias.containsKey(destino)) {
            throw new ExcepcionGrafo("Uno o ambos vértices no existen en el grafo.");
        }
        return distancias.get(origen).get(destino);
    }

    /**
     * Reconstruye el camino mínimo entre dos vértices.
     * Retorna un arreglo de objetos que debe ser casteado al tipo T.
     * 
     * @param origen Vértice origen. Debe existir en el grafo.
     * @param destino Vértice destino. Debe existir en el grafo.
     * @return Arreglo con el camino desde origen hasta destino.
     *         Arreglo vacío si no existe camino.
     * @throws ExcepcionGrafo si alguno de los vértices no existe.
     */
    @SuppressWarnings("unchecked")
    public T[] reconstruirCamino(T origen, T destino) {
        if (!distancias.containsKey(origen) || !distancias.containsKey(destino)) {
            throw new ExcepcionGrafo("Uno o ambos vértices no existen en el grafo.");
        }

        // Verificar si existe camino
        if (distancias.get(origen).get(destino) == Grafo.INFINITO) {
            return (T[]) new Object[0]; // Sin camino
        }

        if (!siguiente.get(origen).containsKey(destino)) {
            return (T[]) new Object[0]; // Sin camino
        }

        // Contar la longitud del camino
        int longitud = 1; // Incluye el origen
        T actual = origen;
        while (!actual.equals(destino)) {
            actual = siguiente.get(actual).get(destino);
            longitud++;
            
            // Protección contra ciclos infinitos
            if (actual == null || longitud > grafo.adyacencias.size()) {
                return (T[]) new Object[0];
            }
        }

        // Crear el arreglo con la longitud exacta
        T[] camino = (T[]) new Object[longitud];
        
        // Llenar el arreglo con el camino
        actual = origen;
        int indice = 0;
        while (!actual.equals(destino)) {
            camino[indice++] = actual;
            actual = siguiente.get(actual).get(destino);
        }
        camino[indice] = destino;
        
        return camino;
    }

    /**
     * Verifica si el grafo contiene ciclos negativos.
     * Un ciclo negativo existe si algún vértice tiene distancia
     * negativa hacia sí mismo después de ejecutar el algoritmo.
     * 
     * @return true si existe al menos un ciclo negativo, false en caso contrario.
     */
    public boolean tieneCicloNegativo() {
        for (T v : grafo.adyacencias.keySet()) {
            if (distancias.get(v).get(v) < 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * Verifica si existe un camino entre dos vértices.
     * 
     * @param origen Vértice origen. Debe existir en el grafo.
     * @param destino Vértice destino. Debe existir en el grafo.
     * @return true si existe camino, false en caso contrario.
     * @throws ExcepcionGrafo si alguno de los vértices no existe.
     */
    public boolean existeCamino(T origen, T destino) {
        if (!distancias.containsKey(origen) || !distancias.containsKey(destino)) {
            throw new ExcepcionGrafo("Uno o ambos vértices no existen en el grafo.");
        }
        return distancias.get(origen).get(destino) != Grafo.INFINITO;
    }

    /**
     * Devuelve la matriz completa de distancias.
     * 
     * @return Mapa con todas las distancias mínimas entre pares de vértices.
     */
    public Map<T, Map<T, Integer>> getDistancias() {
        return distancias;
    }

    /**
     * Imprime la matriz de distancias de forma legible.
     * Útil para debugging.
     */
    public void imprimirMatrizDistancias() {
        System.out.println("Matriz de distancias mínimas:");
        System.out.print("     ");
        
        // Imprimir encabezado
        for (T v : grafo.adyacencias.keySet()) {
            System.out.printf("%5s", v);
        }
        System.out.println();
        
        // Imprimir filas
        for (T i : grafo.adyacencias.keySet()) {
            System.out.printf("%5s", i);
            for (T j : grafo.adyacencias.keySet()) {
                int dist = distancias.get(i).get(j);
                if (dist == Grafo.INFINITO) {
                    System.out.print("  INF");
                } else {
                    System.out.printf("%5d", dist);
                }
            }
            System.out.println();
        }
    }
}