package org.ayed.tda.lista;

public class Cola<T> {
    private Nodo<T> primero;
    private Nodo<T> ultimo;
    private int cantidadDatos;

    /**
     * Constructor de Cola.
     */
    public Cola() {
        this.primero = null;
        this.ultimo = null;
        this.cantidadDatos = 0;
    }

    /**
     * Constructor de copia de Cola.
     *
     * @param cola Cola a copiar.
     *             No puede ser nula.
     * @throws ExcepcionLista si la cola es nula.
     */
    public Cola(Cola<T> cola) {
        if (cola == null) {
            throw new ExcepcionLista("La cola no puede ser nula.");
        }

        this.primero = null;
        this.ultimo = null;
        this.cantidadDatos = 0;

        Nodo<T> cursor = cola.primero;
        while (cursor != null) {
            this.agregar(cursor.dato);
            cursor = cursor.siguiente;
        }
    }

    /**
     * Agrega el dato al final de la cola.
     *
     * @param dato Dato a agregar.
     */
    public void agregar(T dato) {
        Nodo<T> nuevoNodo = new Nodo<>(dato, ultimo, null);

        if (primero == null) {
            primero = nuevoNodo;
            ultimo = nuevoNodo;
        } else {
            ultimo.siguiente = nuevoNodo;
            ultimo = nuevoNodo;
        }

        cantidadDatos++;
    }

    /**
     * Elimina el siguiente dato de la cola (FIFO).
     *
     * @return el siguiente dato de la cola.
     * @throws ExcepcionLista si la cola está vacía.
     */
    public T eliminar() {
        if (primero == null) {
            throw new ExcepcionLista("La cola está vacía.");
        }

        T datoEliminado = primero.dato;

        primero = primero.siguiente;

        if (primero == null) {
            ultimo = null;
        } else {
            primero.anterior = null;
        }

        cantidadDatos--;

        return datoEliminado;
    }

    /**
     * Obtiene el siguiente dato de la cola (FIFO).
     *
     * @return el siguiente dato de la cola.
     * @throws ExcepcionLista si la cola está vacía.
     */
    public T siguiente() {
        if (primero == null) {
            throw new ExcepcionLista("La cola está vacía.");
        }

        return primero.dato;
    }

    /**
     * Obtiene el tamaño de la cola.
     *
     * @return el tamaño de la cola.
     */
    public int tamanio() {
        return cantidadDatos;
    }

    /**
     * Evalúa si la cola está vacía.
     *
     * @return true si la cola está vacía.
     */
    public boolean vacio() {
        return cantidadDatos == 0;
    }
}
