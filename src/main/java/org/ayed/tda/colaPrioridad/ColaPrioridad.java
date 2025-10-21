package org.ayed.tda.colaPrioridad;

import org.ayed.tda.comparador.Comparador;
import org.ayed.tda.vector.Vector;

public class ColaPrioridad<T> {
    // NOTA: Implementar el vector con factor-of-two
    // para tener complejidades logarítmicas.
    private Vector<T> datos;
    private Comparador<T> comparador;

    /**
     * Constructor de ColaPrioridad.
     *
     * @param comparador Comparador a utilizar.
     *                   No puede ser nulo.
     * @throws ExcepcionColaPrioridad si el comparador es nulo.
     */
    public ColaPrioridad(Comparador<T> comparador) {
        // Implementar.
    }

    /**
     * Constructor de copia de ColaPrioridad.
     *
     * @param colaPrioridad Cola a copiar.
     *                      No puede ser nula.
     * @throws ExcepcionColaPrioridad si la cola es nula.
     */
    public ColaPrioridad(ColaPrioridad<T> colaPrioridad) {
        // Implementar.
    }

    /**
     * Reordena el Heap para mantener el invariante.
     * Desplaza datos hacia arriba, comparando el dato actual
     * con su padre, hasta cumplir con el invariante.
     * Inicia en el último dato del vector.
     */
    private void heapificarHaciaArriba() {
        // Implementar.
    }

    /**
     * Reordena el Heap para mantener el invariante.
     * Desplaza datos hacia abajo, comparando el dato actual
     * con el hijo con mayor prioridad, hasta cumplir con el
     * invariante. Inicia en el primer dato del vector.
     */
    private void heapificarHaciaAbajo() {
        // Implementar.
    }

    /**
     * Agrega el dato a la cola, manteniendo el invariante
     * del Heap.
     *
     * @param dato Dato a agregar.
     */
    public void agregar(T dato) {
        // Implementar.
    }

    /**
     * Elimina el siguiente dato de la cola (mayor prioridad),
     * manteniendo el invariante del Heap.
     *
     * @return el dato con mayor prioridad en la cola.
     * @throws ExcepcionColaPrioridad si la cola está vacía.
     */
    public T eliminar() {
        // Implementar.
        return (T) new Object();
    }

    /**
     * Obtiene el siguiente dato de la cola (mayor prioridad).
     *
     * @return el dato con mayor prioridad en la cola.
     * @throws ExcepcionColaPrioridad si la cola está vacía.
     */
    public T siguiente() {
        // Implementar.
        return (T) new Object();
    }

    /**
     * Obtiene el tamaño de la cola.
     *
     * @return el tamaño de la cola.
     */
    public int tamanio() {
        // Implementar.
        return 0;
    }

    /**
     * Evalúa si la cola está vacía.
     *
     * @return true si la cola está vacía.
     */
    public boolean vacio() {
        // Implementar.
        return true;
    }
}
