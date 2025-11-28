package org.ayed.tda.grafo;

import java.util.Map;

import org.ayed.tda.lista.Lista;

// Nodo para A*

public class Nodo<T> {
    private T dato;
    private Nodo<T> siguiente;
    private double costoAcumulado;
    private double heuristica;
    private Nodo<T> predecesor;
    private boolean visitado;
    protected static final int INFINITO = 99999;

    public Nodo(T dato) {
        this.dato = dato;
        siguiente = null;
        this.costoAcumulado = INFINITO;
        this.heuristica = 0;
        this.predecesor = null;
        this.visitado = false;
    }

    public double obtenerCostoAcumulado() {
        return costoAcumulado;
    }

    public void costoAcumulado(double costoAcumulado) {
        this.costoAcumulado = costoAcumulado;
    }

    public double obtenerHeuristica() {
        return heuristica;
    }

    public void heuristica(double heuristica) {
        this.heuristica = heuristica;
    }

    public double evaluacionTotal() {
        return costoAcumulado + heuristica;
    }

    public Nodo<T> obtenerPredecesor() {
        return predecesor;
    }

    public void predecesor(Nodo<T> predecesor) {
        this.predecesor = predecesor;
    }

    public boolean obtenerVisitado() {
        return visitado;
    }

    public void visitado(boolean visitado) {
        this.visitado = visitado;
    }

    public Lista<Nodo<T>> obtenerVecinos(Grafo<T> grafo) {
        Lista<Nodo<T>> vecinos = new Lista<>();

        try {
            Map<T, Integer> adyacentes = grafo.obtenerAdyacentes(this.dato);

            for (T vertice : adyacentes.keySet()) {
                Nodo<T> nodoVecino = new Nodo<>(vertice);
                vecinos.agregar(nodoVecino);
            }
        } catch (ExcepcionGrafo e) {
            return new Lista<>();
        }

        return vecinos;
    }

    public double calcularCosto(Nodo<T> vecino, Grafo<T> grafo) {
        try {
            return grafo.obtenerArista(this.dato, vecino.obtenerDato());
        } catch (ExcepcionGrafo e) {
            return Grafo.SIN_PESO;
        }
    }

    public T obtenerDato() {
        return dato;
    }

    public void cambiarDato(T dato) {
        this.dato = dato;
    }

    public Nodo<T> obtenerSiguiente() {
        return siguiente;
    }

    public void cambiarSiguiente(Nodo<T> primero) {
        this.siguiente = primero;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Nodo<T> otroNodo = (Nodo<T>) obj;
        // Dos nodos son iguales si contienen el mismo dato (la misma Celda)
        if (dato == null) {
            return otroNodo.dato == null;
        }
        return dato.equals(otroNodo.dato);
    }

    @Override
    public int hashCode() {
        return dato != null ? dato.hashCode() : 0;
    }

}
