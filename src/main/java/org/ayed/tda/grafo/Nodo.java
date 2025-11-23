package org.ayed.tda.grafo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

// Nodo para A*

public class Nodo<T> {
    private T dato;
    private Nodo<T> siguiente;
    private double g;
    private double h;
    private Nodo<T> predecesor;
    private boolean visitado;
    protected static final int INFINITO = 99999;

    public Nodo(T d) {
        dato = d;
        siguiente = null;
        this.g = INFINITO;
        this.h = 0;
        this.predecesor = null;
        this.visitado = false;
    }

    public double getG() {
        return g;
    }

    public void setG(double g) {
        this.g = g;
    }

    public double getH() {
        return h;
    }

    public void setH(double h) {
        this.h = h;
    }

    public double getF() {
        return g + h;
    }

    public Nodo<T> getPredecesor() {
        return predecesor;
    }

    public void setPredecesor(Nodo<T> predecesor) {
        this.predecesor = predecesor;
    }

    public boolean getVisitado() {
        return visitado;
    }

    public void setVisitado(boolean visitado) {
        this.visitado = visitado;
    }

    public List<Nodo<T>> getVecinos(Grafo<T> grafo) {
        List<Nodo<T>> vecinos = new ArrayList<>();

        try {
            Map<T, Integer> adyacentes = grafo.obtenerAdyacentes(this.dato);

            for (T vertice : adyacentes.keySet()) {
                Nodo<T> nodoVecino = new Nodo<>(vertice);
                vecinos.add(nodoVecino);
            }
        } catch (ExcepcionGrafo e) {

            return new ArrayList<>();
        }

        return vecinos;
    }

    public double calcularCosto(Nodo<T> vecino, Grafo<T> grafo) {
        try {
            return grafo.obtenerArista(this.dato, vecino.getDato());
        } catch (ExcepcionGrafo e) {
            return Grafo.SIN_PESO;
        }
    }

    public T getDato() {
        return dato;
    }

    public void cambiarDato(T dato) {
        this.dato = dato;
    }

    public Nodo<T> getSiguiente() {
        return siguiente;
    }

    public void cambiarSiguiente(Nodo<T> primero) {
        this.siguiente = primero;
    }
}
