package org.ayed.tda.lista;

import org.ayed.tda.iterador.Iterador;

public class Lista<T> {
    Nodo<T> primero;
    Nodo<T> ultimo;
    int cantidadDatos;

    /**
     * Constructor de Lista.
     */
    public Lista() {
        this.primero = null;
        this.ultimo = null;
        this.cantidadDatos = 0;
    }

    /**
     * Constructor de copia de Lista.
     *
     * @param lista Lista a copiar.
     *              No puede ser nula.
     * @throws ExcepcionLista si la lista es nula.
     */
    public Lista(Lista<T> lista) {
        if (lista == null) {
            throw new ExcepcionLista("La lista no puede ser nula.");
        }

        this.primero = null;
        this.ultimo = null;
        this.cantidadDatos = 0;

        // Recorro la lista original y copio cada dato
        Nodo<T> cursor = lista.primero;
        while (cursor != null) {
            this.agregar(cursor.dato);
            cursor = cursor.siguiente;
        }
    }

    /**
     * Agrega un dato al final de la lista.
     *
     * @param dato Dato a agregar.
     */
    public void agregar(T dato) {
        Nodo<T> nuevoNodo = new Nodo<>(dato, ultimo, null);

        // La lista está vacía
        if (primero == null) {
            primero = nuevoNodo;
            ultimo = nuevoNodo;
        } else {
            // La lista tiene al menos un nodo
            ultimo.siguiente = nuevoNodo;
            ultimo = nuevoNodo;
        }

        cantidadDatos++;
    }

    /**
     * Agrega un dato a la lista en el índice indicado.
     * <p>
     * Ejemplo:
     * <pre>
     * {@code
     * >> 0 -> 1 -> 5 -> 3
     * agregar(4, 1);
     * >> 0 -> 4 -> 1 -> 5 -> 3
     * }
     * </pre>
     *
     * @param dato   Dato a agregar.
     * @param indice Índice en el que se inserta el dato.
     *               No puede ser negativo.
     *               No puede ser mayor que el tamaño de la lista.
     * @throws ExcepcionLista si el índice no es válido.
     */
    public void agregar(T dato, int indice) {
        if (indice < 0 || indice > cantidadDatos) {
            throw new ExcepcionLista("Índice inválido.");
        }

        // Inserto al final
        if (indice == cantidadDatos) {
            agregar(dato);
            return;
        }

        // Inserto al principio
        if (indice == 0) {
            Nodo<T> nuevoNodo = new Nodo<>(dato, null, primero);
            // Si la lista no estaba vacía, actualizo el anterior del viejo primero
            if (primero != null) {
                primero.anterior = nuevoNodo;
            }
            primero = nuevoNodo;
            // Si era el único nodo, también es el último
            if (ultimo == null) {
                ultimo = nuevoNodo;
            }
            cantidadDatos++;
            return;
        }

        // Inserto en medio
        Nodo<T> cursor = primero;
        for (int i = 0; i < indice - 1; i++) {
            cursor = cursor.siguiente;
        }

        // Ahora cursor apunta al nodo anterior de donde quiero insertar
        Nodo<T> nuevoNodo = new Nodo<>(dato, cursor, cursor.siguiente);

        // Actualizo las referencias de los nodos vecinos
        if (cursor.siguiente != null) {
            cursor.siguiente.anterior = nuevoNodo;
        }
        // El nodo antes del nuevo debe apuntar hacia adelante al nuevo
        cursor.siguiente = nuevoNodo;

        cantidadDatos++;
    }

    /**
     * Elimina el último dato de la lista
     *
     * @return el dato eliminado.
     * @throws ExcepcionLista si la lista está vacía.
     */
    public T eliminar() {
        if (cantidadDatos == 0) {
            throw new ExcepcionLista("La lista está vacía.");
        }

        T datoEliminado = ultimo.dato;

        // La lista tiene solo un elemento
        if (primero == ultimo) {
            primero = null;
            ultimo = null;
        } else {
            // La lista tiene múltiples elementos
            ultimo = ultimo.anterior;
            ultimo.siguiente = null;
        }

        cantidadDatos--;

        return datoEliminado;
    }

    /**
     * Elimina el dato de la lista en el índice indicado por parámetro.
     * <p>
     * Ejemplo:
     * <pre>
     * {@code
     * >> 0 -> 1 -> 5 -> 3
     * eliminar(1);
     * >> 0 -> 5 -> 3
     * }
     * </pre>
     *
     * @param indice Índice del dato a eliminar.
     *               No puede ser negativo.
     *               No puede ser mayor o igual que el tamaño de la lista.
     * @return el dato eliminado.
     */
    public T eliminar(int indice) {
        if (cantidadDatos == 0) {
            throw new ExcepcionLista("La lista está vacía.");
        }

        if (indice < 0 || indice >= cantidadDatos) {
            throw new ExcepcionLista("Índice inválido.");
        }

        T datoEliminado;

        // Elimino el primero
        if (indice == 0) {
            datoEliminado = primero.dato;

            primero = primero.siguiente;

            // Si la lista no quedó vacía, actualizo el anterior del nuevo primero
            if (primero != null) {
                primero.anterior = null;
            } else {
                // Si quedó vacía, último también debe ser null
                ultimo = null;
            }

            cantidadDatos--;
            return datoEliminado;
        }

        // Elimino el último
        if (indice == cantidadDatos - 1) {
            return eliminar();
        }

        // Elimino en medio
        Nodo<T> cursor = primero;
        for (int i = 0; i < indice; i++) {
            cursor = cursor.siguiente;
        }

        datoEliminado = cursor.dato;

        // Reconecto los nodos vecinos entre sí
        cursor.anterior.siguiente = cursor.siguiente;
        cursor.siguiente.anterior = cursor.anterior;

        cantidadDatos--;

        return datoEliminado;
    }

    /**
     * Obtiene el dato de la lista en el índice indicado.
     *
     * @param indice Índice del dato a obtener.
     *               No puede ser negativo.
     *               No puede ser mayor o igual que el tamaño de la lista.
     * @return el dato en el índice indicado.
     * @throws ExcepcionLista si el índice no es válido.
     */
    public T dato(int indice) {
        if (cantidadDatos == 0) {
            throw new ExcepcionLista("La lista está vacía.");
        }

        if (indice < 0 || indice >= cantidadDatos) {
            throw new ExcepcionLista("Índice inválido.");
        }

        Nodo<T> cursor = primero;
        for (int i = 0; i < indice; i++) {
            cursor = cursor.siguiente;
        }

        return cursor.dato;
    }

    /**
     * Modifica el dato de la lista en el índice indicado
     * por el dato indicado por parámetro.
     *
     * @param indice Índice del dato a modificar.
     *               No puede ser negativo.
     *               No puede ser mayor o igual que el tamaño de la lista.
     * @throws ExcepcionLista si el índice no es válido.
     */
    public void modificarDato(T dato, int indice) {
        if (cantidadDatos == 0) {
            throw new ExcepcionLista("La lista está vacía.");
        }

        if (indice < 0 || indice >= cantidadDatos) {
            throw new ExcepcionLista("Índice inválido.");
        }

        Nodo<T> cursor = primero;
        for (int i = 0; i < indice; i++) {
            cursor = cursor.siguiente;
        }

        cursor.dato = dato;
    }

    /**
     * Obtiene el tamaño de la lista.
     *
     * @return el tamaño de la lista.
     */
    public int tamanio() {
        return cantidadDatos;
    }

    /**
     * Evalúa si la lista está vacía.
     *
     * @return true si la lista está vacía.
     */
    public boolean vacio() {
        return cantidadDatos == 0;
    }

    /**
     * Obtiene un iterador bidireccional posicionado
     * en el primer dato de la lista.
     *
     * @return el iterador.
     * @see Iterador
     */
    public Iterador<T> iterador() {
        return new IteradorLista<>(this);
    }

    /**
     * Obtiene un iterador bidireccional posicionado
     * en el índice indicado por parámetro.
     *
     * @param indice Índice del nodo inicial del iterador.
     *               No puede ser negativo.
     *               No puede ser mayor que el tamaño de la lista.
     * @return el iterador.
     * @throws ExcepcionLista si el índice no es válido.
     * @see Iterador
     */
    public Iterador<T> iterador(int indice) {
        if (indice < 0 || indice > cantidadDatos) {
            throw new ExcepcionLista("Índice inválido.");
        }

        return new IteradorLista<>(this, indice);
    }
}
