package org.ayed.tda.lista;

public class Pila<T> {
    private Nodo<T> ultimo;
    private int cantidadDatos;

    /**
     * Constructor de Pila.
     */
    public Pila() {
        this.ultimo = null;
        this.cantidadDatos = 0;
    }

    /**
     * Constructor de copia de Pila.
     *
     * @param pila Pila a copiar.
     *             No puede ser nula.
     * @throws ExcepcionLista si la pila es nula.
     */
    public Pila(Pila<T> pila) {
        if (pila == null) {
            throw new ExcepcionLista("La pila no puede ser nula.");
        }

        this.ultimo = null;
        this.cantidadDatos = 0;

        // Construyo la nueva pila desde el fondo hacia arriba
        if (pila.ultimo != null) {
            copiarDesdeNodo(pila.ultimo);
        }
    }

    /**
     * Agrega el dato al final de la pila.
     *
     * @param dato Dato a agregar.
     */
    public void agregar(T dato) {
        Nodo<T> nuevoNodo = new Nodo<>(dato, ultimo, null);

        ultimo = nuevoNodo;

        cantidadDatos++;
    }

    /**
     * Elimina el siguiente dato de la pila (LIFO).
     *
     * @return el siguiente dato de la pila.
     * @throws ExcepcionLista si la pila está vacía.
     */
    public T eliminar() {
        if (ultimo == null) {
            throw new ExcepcionLista("La pila está vacía.");
        }

        T datoEliminado = ultimo.dato;

        ultimo = ultimo.anterior;

        cantidadDatos--;

        return datoEliminado;
    }

    /**
     * Obtiene el siguiente dato de la pila (LIFO).
     *
     * @return el siguiente dato de la pila.
     * @throws ExcepcionLista si la pila está vacía.
     */
    public T siguiente() {
        if (ultimo == null) {
            throw new ExcepcionLista("La pila está vacía.");
        }

        return ultimo.dato;
    }

    /**
     * Obtiene el tamaño de la pila.
     *
     * @return el tamaño de la pila.
     */
    public int tamanio() {
        return cantidadDatos;
    }

    /**
     * Evalúa si la pila está vacía.
     *
     * @return true si la pila está vacía.
     */
    public boolean vacio() {
        return cantidadDatos == 0;
    }

    /**** HELPERS ****/

    private void copiarDesdeNodo(Nodo<T> nodo) {
        // primero copio todo lo que está debajo
        if (nodo.anterior != null) {
            copiarDesdeNodo(nodo.anterior);
        }
        // Luego apilo el dato actual
        this.agregar(nodo.dato);
    }
}
