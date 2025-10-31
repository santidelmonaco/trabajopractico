package org.ayed.tda.lista;

import org.ayed.tda.iterador.ExcepcionNoHayDato;
import org.ayed.tda.iterador.Iterador;

class IteradorLista<T> implements Iterador<T> {
    private Lista<T> lista;
    private Nodo<T> cursor;
    private int indice;

    /**
     * Constructor de Iter.
     *
     * @param lista Lista a iterar.
     */
    IteradorLista(Lista<T> lista) {
        this.lista = lista;
        this.cursor = lista.primero;
        this.indice = 0;
    }

    /**
     * Constructor de Iter.
     *
     * @param lista  Lista a iterar.
     * @param indice Índice inicial del iterador.
     */
    IteradorLista(Lista<T> lista, int indice) {
        this.lista = lista;
        this.indice = indice;

        if (indice == lista.cantidadDatos) {
            this.cursor = null;
        } else {
            this.cursor = lista.primero;
            for (int i = 0; i < indice; i++) {
                this.cursor = cursor.siguiente;
            }
        }
    }

    @Override
    public T dato() {
        if (cursor == null) {
            throw new ExcepcionNoHayDato("No hay dato en la posición actual.");
        }

        return cursor.dato;
    }

    @Override
    public boolean haySiguiente() {
        return cursor != null;
    }

    @Override
    public void siguiente() {
        if (!haySiguiente()) {
            throw new ExcepcionNoHayDato("No hay elemento siguiente.");
        }

        cursor = cursor.siguiente;

        indice++;
    }

    @Override
    public boolean hayAnterior() {
        return indice > 0;
    }

    @Override
    public void anterior() {
        if (!hayAnterior()) {
            throw new ExcepcionNoHayDato("No hay elemento anterior.");
        }

        // si el cursor es null, estoy al final
        if (cursor == null) {
            cursor = lista.ultimo;
        } else {
            // muevo el cursor al nodo anterior
            cursor = cursor.anterior;
        }

        indice--;
    }

    @Override
    public void agregar(T dato) {
        Nodo<T> nuevoNodo;

        // Inserto al inicio
        if (indice == 0) {
            nuevoNodo = new Nodo<>(dato, null, lista.primero);

            // Si la lista no está vacía, actualizo el anterior del viejo primero
            if (lista.primero != null) {
                lista.primero.anterior = nuevoNodo;
            } else {
                // Si estaba vacía, el nuevo nodo también es el último
                lista.ultimo = nuevoNodo;
            }

            lista.primero = nuevoNodo;

            indice++;

        } else if (cursor == null) {
            // Inserto al final
            nuevoNodo = new Nodo<>(dato, lista.ultimo, null);

            // Conecto el viejo último con el nuevo
            if (lista.ultimo != null) {
                lista.ultimo.siguiente = nuevoNodo;
            } else {
                // Si la lista estaba vacía, el nuevo nodo también es el primero
                lista.primero = nuevoNodo;
            }

            lista.ultimo = nuevoNodo;

        } else {
            // Inserto en medio
            nuevoNodo = new Nodo<>(dato, cursor.anterior, cursor);

            // Actualizo las referencias de los nodos vecinos
            cursor.anterior.siguiente = nuevoNodo;
            cursor.anterior = nuevoNodo;

            indice++;
        }

        lista.cantidadDatos++;
    }

    @Override
    public void modificarDato(T dato) {
        if (cursor == null) {
            throw new ExcepcionNoHayDato("No hay dato en la posición actual.");
        }

        cursor.dato = dato;
    }

    @Override
    public T eliminar() {
        if (cursor == null) {
            throw new ExcepcionNoHayDato("No hay dato en la posición actual.");
        }

        T datoEliminado = cursor.dato;

        // Elimino el único elemento
        if (lista.primero == lista.ultimo) {
            lista.primero = null;
            lista.ultimo = null;
            cursor = null;
        } else if (cursor == lista.primero) {
            // Elimino el primer elemento
            lista.primero = cursor.siguiente;
            lista.primero.anterior = null;
            cursor = lista.primero;
        } else if (cursor == lista.ultimo) {
            // Elimino el último elemento
            lista.ultimo = cursor.anterior;
            lista.ultimo.siguiente = null;
            cursor = null;
        } else {
            // Elimino en medio
            cursor.anterior.siguiente = cursor.siguiente;
            cursor.siguiente.anterior = cursor.anterior;
            cursor = cursor.siguiente;
        }

        lista.cantidadDatos--;

        return datoEliminado;
    }
}
