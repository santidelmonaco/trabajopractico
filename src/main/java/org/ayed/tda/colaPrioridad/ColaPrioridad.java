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
        if (comparador == null) {
            throw new ExcepcionColaPrioridad("El comparador no puede ser nulo.");
        }

        this.comparador = comparador;

        this.datos = new Vector<>();
    }

    /**
     * Constructor de copia de ColaPrioridad.
     *
     * @param colaPrioridad Cola a copiar.
     *                      No puede ser nula.
     * @throws ExcepcionColaPrioridad si la cola es nula.
     */
    public ColaPrioridad(ColaPrioridad<T> colaPrioridad) {
        if (colaPrioridad == null) {
            throw new ExcepcionColaPrioridad("La cola de prioridad no puede ser nula.");
        }

        this.comparador = colaPrioridad.comparador;

        this.datos = new Vector<>(colaPrioridad.datos);
    }

    /**
     * Reordena el Heap para mantener el invariante.
     * Desplaza datos hacia arriba, comparando el dato actual
     * con su padre, hasta cumplir con el invariante.
     * Inicia en el último dato del vector.
     */
    private void heapificarHaciaArriba() {
        int indiceActual = datos.tamanio() - 1;

        // Mientras no este en la raíz y el elemento tenga mayor prioridad que su padre
        while (indiceActual > 0) {
            int indicePadre = obtenerIndicePadre(indiceActual);

            // Si el comparador devuelve positivo, el actual tiene mayor prioridad
            if (comparador.comparar(datos.dato(indiceActual), datos.dato(indicePadre)) > 0) {
                intercambiar(indiceActual, indicePadre);

                indiceActual = indicePadre;
            } else {
                // El padre tiene mayor o igual prioridad
                break;
            }
        }
    }

    /**
     * Reordena el Heap para mantener el invariante.
     * Desplaza datos hacia abajo, comparando el dato actual
     * con el hijo con mayor prioridad, hasta cumplir con el
     * invariante. Inicia en el primer dato del vector.
     */
    private void heapificarHaciaAbajo() {
        int indiceActual = 0;

        // Continuo mientras haya al menos un hijo izquierdo
        while (obtenerIndiceHijoIzquierdo(indiceActual) < datos.tamanio()) {
            int indiceHijoIzquierdo = obtenerIndiceHijoIzquierdo(indiceActual);
            int indiceHijoDerecho = obtenerIndiceHijoDerecho(indiceActual);

            // Determino cuál hijo tiene mayor prioridad
            int indiceHijoMayor = indiceHijoIzquierdo;

            if (indiceHijoDerecho < datos.tamanio() &&
                    comparador.comparar(datos.dato(indiceHijoDerecho), datos.dato(indiceHijoIzquierdo)) > 0) {
                indiceHijoMayor = indiceHijoDerecho;
            }

            // Comparamos el elemento actual con el hijo de mayor prioridad
            if (comparador.comparar(datos.dato(indiceHijoMayor), datos.dato(indiceActual)) > 0) {
                intercambiar(indiceActual, indiceHijoMayor);

                indiceActual = indiceHijoMayor;
            } else {
                // El elemento actual tiene mayor o igual prioridad que ambos hijos
                // El invariante está restaurado
                break;
            }
        }
    }

    /**
     * Agrega el dato a la cola, manteniendo el invariante
     * del Heap.
     *
     * @param dato Dato a agregar.
     */
    public void agregar(T dato) {
        datos.agregar(dato);

        // Restauro el invariante del heap
        heapificarHaciaArriba();
    }

    /**
     * Elimina el siguiente dato de la cola (mayor prioridad),
     * manteniendo el invariante del Heap.
     *
     * @return el dato con mayor prioridad en la cola.
     * @throws ExcepcionColaPrioridad si la cola está vacía.
     */
    public T eliminar() {
        if (datos.vacio()) {
            throw new ExcepcionColaPrioridad("La cola de prioridad está vacía.");
        }

        T elementoMaximo = datos.dato(0);

        if (datos.tamanio() == 1) {
            datos.eliminar();
            return elementoMaximo;
        }

        // Muevo el último elemento a la raíz
        datos.modificarDato(datos.dato(datos.tamanio() - 1), 0);

        // Elimino el último elemento
        datos.eliminar();

        // Restauro el invariante del heap
        heapificarHaciaAbajo();

        return elementoMaximo;
    }

    /**
     * Obtiene el siguiente dato de la cola (mayor prioridad).
     *
     * @return el dato con mayor prioridad en la cola.
     * @throws ExcepcionColaPrioridad si la cola está vacía.
     */
    public T siguiente() {
        if (datos.vacio()) {
            throw new ExcepcionColaPrioridad("La cola de prioridad está vacía.");
        }

        return datos.dato(0);
    }

    /**
     * Obtiene el tamaño de la cola.
     *
     * @return el tamaño de la cola.
     */
    public int tamanio() {
        return datos.tamanio();
    }

    /**
     * Evalúa si la cola está vacía.
     *
     * @return true si la cola está vacía.
     */
    public boolean vacio() {
        return datos.vacio();
    }

    /**** HELPERS ****/

    private int obtenerIndicePadre(int indice) {
        // El padre de un nodo en índice i está en (i - 1) / 2
        return (indice - 1) / 2;
    }

    private int obtenerIndiceHijoIzquierdo(int indice) {
        // El hijo izquierdo de un nodo en índice i está en 2*i + 1
        return 2 * indice + 1;
    }

    private int obtenerIndiceHijoDerecho(int indice) {
        // El hijo derecho de un nodo en índice i está en 2*i + 2
        return 2 * indice + 2;
    }

    private void intercambiar(int indice1, int indice2) {
        // Intercambio dos elementos en el vector
        T temporal = datos.dato(indice1);
        datos.modificarDato(datos.dato(indice2), indice1);
        datos.modificarDato(temporal, indice2);
    }
}
