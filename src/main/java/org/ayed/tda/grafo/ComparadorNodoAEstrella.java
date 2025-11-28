package org.ayed.tda.grafo;

import org.ayed.tda.comparador.Comparador;

/**
 * Comparador de nodos para el algoritmo A*.
 * Compara nodos según su función de evaluación total.
 * El nodo con menor costo debe tener mayor prioridad.
 */
public class ComparadorNodoAEstrella<T> implements Comparador<Nodo<T>> {

    @Override
    public int comparar(Nodo<T> nodo1, Nodo<T> nodo2) {
        // Obtengo las evaluaciones totales de ambos nodos
        double evaluacion1 = nodo1.evaluacionTotal();
        double evaluacion2 = nodo2.evaluacionTotal();

        // Quiero que el nodo con menor evaluación tenga mayor prioridad
        // Por eso invierto la comparación: si nodo2 > nodo1, devuelvo positivo
        if (evaluacion2 > evaluacion1) {
            return 1;  // nodo1 tiene mayor prioridad (menor costo)
        } else if (evaluacion2 < evaluacion1) {
            return -1; // nodo2 tiene mayor prioridad (menor costo)
        } else {
            return 0;  // misma prioridad
        }
    }
}