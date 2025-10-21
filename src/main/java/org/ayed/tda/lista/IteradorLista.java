package org.ayed.tda.lista;

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
        // Implementar.
    }

    /**
     * Constructor de Iter.
     *
     * @param lista  Lista a iterar.
     * @param indice Índice inicial del iterador.
     */
    IteradorLista(Lista<T> lista, int indice) {
        // Implementar.
    }

    @Override
    public T dato() {
        // Implementar.
        return (T) new Object();
    }

    @Override
    public boolean haySiguiente() {
        // Implementar.
        return true;
    }

    @Override
    public void siguiente() {
        // Implementar.
    }

    @Override
    public boolean hayAnterior() {
        // Implementar.
        return true;
    }

    @Override
    public void anterior() {
        // Implementar.
    }

    @Override
    public void agregar(T dato) {
        // Implementar.
    }

    @Override
    public void modificarDato(T dato) {
        // Implementar.
    }

    @Override
    public T eliminar() {
        // Implementar.
        return (T) new Object();
    }
}
