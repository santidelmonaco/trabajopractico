package org.ayed.tda.diccionario;

import org.ayed.tda.comparador.Comparador;
import org.ayed.tda.lista.Cola;
import org.ayed.tda.lista.Lista;
import org.ayed.tda.tupla.Tupla;

/**
 * Diccionario asociativo (clave, valor) que mantiene
 * el orden de los datos en base a su clave y al
 * comparador utilizado. Está implementado con un
 * árbol binario de búsqueda no balanceado.
 *
 * @param <C> El tipo de dato de la clave.
 *            Este tipo debe ser comparable.
 * @param <V> El tipo de dato del valor.
 *            Este tipo no necesita ser comparable obligatoriamente,
 *            pero puede ser útil para el usuario si se decide
 *            implementar métodos para consultar si un valor está
 *            en el diccionario.
 */
public class DiccionarioOrdenado<C, V> {
    private Nodo<C, V> raiz;
    private Comparador<C> comparador;
    private int cantidadDatos;

    /**
     * Constructor.
     *
     * @param comparador Comparador a utilizar.
     *                   No puede ser nulo.
     * @throws ExcepcionDiccionario si el comparador es nulo.
     */
    public DiccionarioOrdenado(Comparador<C> comparador) {
        if (comparador == null) {
            throw new ExcepcionDiccionario("El comparador no puede ser nulo.");
        }

        this.comparador = comparador;

        this.raiz = null;

        this.cantidadDatos = 0;
    }

    /**
     * Constructor de copia de DiccionarioOrdenado.
     * <p>
     * TIP: Implementar un método que clone un subárbol.
     *
     * @param diccionarioOrdenado Diccionario a copiar.
     *                            No puede ser nulo.
     * @throws ExcepcionDiccionario si el diccionario es nulo.
     */
    public DiccionarioOrdenado(DiccionarioOrdenado<C, V> diccionarioOrdenado) {
        if (diccionarioOrdenado == null) {
            throw new ExcepcionDiccionario("El diccionario no puede ser nulo.");
        }

        this.comparador = diccionarioOrdenado.comparador;

        this.raiz = null;

        this.cantidadDatos = diccionarioOrdenado.cantidadDatos;

        if (diccionarioOrdenado.raiz != null) {
            this.raiz = copiarSubarbol(diccionarioOrdenado.raiz, null);
        }
    }

    /**
     * Obtiene el sucesor inmediato del nodo.
     *
     * @param nodo Nodo inicial.
     * @return el sucesor inmediato.
     */
    private Nodo<C, V> obtenerSucesorInmediato(Nodo<C, V> nodo) {
        Nodo<C, V> sucesor = nodo.hijoDerecho;

        while (sucesor.hijoIzquierdo != null) {
            sucesor = sucesor.hijoIzquierdo;
        }

        return sucesor;
    }

    /**
     * Agrega un mapeo {clave, valor} al diccionario.
     * Si ya existía la clave en el diccionario, reemplaza
     * el valor anterior y lo devuelve.
     *
     * @param clave Clave a agregar.
     * @param valor Valor a agregar.
     * @return el valor anterior. Si no había un valor
     * anterior, devuelve null.
     */
    public V agregar(C clave, V valor) {
        // árbol vacío
        if (raiz == null) {
            raiz = new Nodo<>(clave, valor);
            cantidadDatos++;
            return null;
        }

        Nodo<C, V> actual = raiz;
        Nodo<C, V> padre = null;

        // busco la posición correcta
        while (actual != null) {
            padre = actual;

            int comparacion = comparador.comparar(clave, actual.clave);

            if (comparacion < 0) {
                // La clave nueva es menor, voy al subárbol izquierdo
                actual = actual.hijoIzquierdo;
            } else if (comparacion > 0) {
                // La clave nueva es mayor, voy al subárbol derecho
                actual = actual.hijoDerecho;
            } else {
                // Las claves son iguales, reemplazo el valor
                V valorAnterior = actual.valor;
                actual.valor = valor;
                return valorAnterior;
            }
        }

        // Si llego aca, encontró donde insertar el nuevo nodo
        Nodo<C, V> nuevoNodo = new Nodo<>(clave, valor, padre);

        // Determino si el nuevo nodo va a la izquierda o derecha del padre
        int comparacion = comparador.comparar(clave, padre.clave);
        if (comparacion < 0) {
            padre.hijoIzquierdo = nuevoNodo;
        } else {
            padre.hijoDerecho = nuevoNodo;
        }

        cantidadDatos++;
        return null;
    }

    /**
     * Elimina un mapeo {clave, valor} del diccionario,
     * si existe. Si no existe, el diccionario queda en
     * el mismo estado.
     * <p>
     * NOTA: Para eliminar nodos interiores, se utiliza
     * el sucesor inmediato.
     *
     * @param clave Clave a eliminar.
     * @return el valor eliminado. Si no se eliminó
     * un valor, devuelve null.
     */
    public V eliminar(C clave) {
        Nodo<C, V> nodo = buscarNodo(raiz, clave);

        if (nodo == null) {
            return null;
        }

        V valorEliminado = nodo.valor;

        // El nodo es una hoja
        if (nodo.hijoIzquierdo == null && nodo.hijoDerecho == null) {
            eliminarHoja(nodo);
        }
        // El nodo tiene exactamente un hijo
        else if (nodo.hijoIzquierdo == null || nodo.hijoDerecho == null) {
            eliminarNodoConUnHijo(nodo);
        }
        // El nodo tiene dos hijos
        else {
            eliminarNodoConDosHijos(nodo);
        }

        cantidadDatos--;
        return valorEliminado;
    }

    /**
     * Obtiene un mapeo {clave, valor} del diccionario,
     * si existe.
     *
     * @param clave Clave a buscar.
     * @return el valor buscado. Si no existe, devuelve
     * null.
     */
    public V obtenerValor(C clave) {
        Nodo<C, V> nodo = buscarNodo(raiz, clave);

        if (nodo == null) {
            return null;
        }

        return nodo.valor;
    }

    /**
     * Devuelve el recorrido inorder del árbol.
     *
     * @return el recorrido.
     */
    public Lista<Tupla<C, V>> inorder() {
        Lista<Tupla<C, V>> resultado = new Lista<>();
        inorderRecursivo(raiz, resultado);
        return resultado;
    }

    /**
     * Devuelve el recorrido preorder del árbol.
     *
     * @return el recorrido.
     */
    public Lista<Tupla<C, V>> preorder() {
        Lista<Tupla<C, V>> resultado = new Lista<>();
        preorderRecursivo(raiz, resultado);
        return resultado;
    }

    /**
     * Devuelve el recorrido postorder del árbol.
     *
     * @return el recorrido.
     */
    public Lista<Tupla<C, V>> postorder() {
        Lista<Tupla<C, V>> resultado = new Lista<>();
        postorderRecursivo(raiz, resultado);
        return resultado;
    }

    /**
     * Devuelve el recorrido en ancho del árbol.
     *
     * @return el recorrido.
     */
    public Lista<Tupla<C, V>> ancho() {
        Lista<Tupla<C, V>> resultado = new Lista<>();

        if (raiz == null) {
            return resultado;
        }

        // Uso una cola para procesar los nodos nivel por nivel
        Cola<Nodo<C, V>> cola = new Cola<>();
        cola.agregar(raiz);

        while (!cola.vacio()) {
            Nodo<C, V> nodo = cola.eliminar();

            resultado.agregar(new Tupla<>(nodo.clave, nodo.valor));

            if (nodo.hijoIzquierdo != null) {
                cola.agregar(nodo.hijoIzquierdo);
            }
            if (nodo.hijoDerecho != null) {
                cola.agregar(nodo.hijoDerecho);
            }
        }

        return resultado;
    }

    /**
     * Obtiene el tamaño del diccionario.
     *
     * @return el tamaño del diccionario.
     */
    public int tamanio() {
        return cantidadDatos;
    }

    /**
     * Evalúa si el diccionario está vacío.
     *
     * @return true si el diccionario está vacío.
     */
    public boolean vacio() {
        return cantidadDatos == 0;
    }

    /**** HELPERS ****/

    private Nodo<C, V> copiarSubarbol(Nodo<C, V> nodoOriginal, Nodo<C, V> padre) {
        if (nodoOriginal == null) {
            return null;
        }

        Nodo<C, V> nodoCopia = new Nodo<>(nodoOriginal.clave, nodoOriginal.valor, padre);

        // copio el subárbol izquierdo
        nodoCopia.hijoIzquierdo = copiarSubarbol(nodoOriginal.hijoIzquierdo, nodoCopia);

        // copio el subárbol derecho
        nodoCopia.hijoDerecho = copiarSubarbol(nodoOriginal.hijoDerecho, nodoCopia);

        // Devuelvo la copia del subárbol
        return nodoCopia;
    }

    private Nodo<C, V> buscarNodo(Nodo<C, V> nodo, C clave) {
        if (nodo == null) {
            return null;
        }

        int comparacion = comparador.comparar(clave, nodo.clave);

        if (comparacion < 0) {
            // La clave está en el subárbol izquierdo
            return buscarNodo(nodo.hijoIzquierdo, clave);
        } else if (comparacion > 0) {
            // La clave está en el subárbol derecho
            return buscarNodo(nodo.hijoDerecho, clave);
        } else {
            // Encontro el nodo
            return nodo;
        }
    }

    private void eliminarHoja(Nodo<C, V> nodo) {
        // Si el nodo es la raíz, el árbol queda vacío
        if (nodo == raiz) {
            raiz = null;
        } else {
            // Desconecto el nodo de su padre
            if (nodo.padre.hijoIzquierdo == nodo) {
                nodo.padre.hijoIzquierdo = null;
            } else {
                nodo.padre.hijoDerecho = null;
            }
        }
    }

    private void eliminarNodoConUnHijo(Nodo<C, V> nodo) {
        // Determino cuál es el único hijo
        Nodo<C, V> hijo = (nodo.hijoIzquierdo != null) ? nodo.hijoIzquierdo : nodo.hijoDerecho;

        // Si el nodo es la raíz, el hijo se convierte en la nueva raíz
        if (nodo == raiz) {
            raiz = hijo;
            hijo.padre = null;
        } else {
            // Conecto el hijo con el padre del nodo eliminado
            hijo.padre = nodo.padre;

            // Actualizo la referencia del padre
            if (nodo.padre.hijoIzquierdo == nodo) {
                nodo.padre.hijoIzquierdo = hijo;
            } else {
                nodo.padre.hijoDerecho = hijo;
            }
        }
    }

    private void eliminarNodoConDosHijos(Nodo<C, V> nodo) {
        // Busco el sucesor inmediato
        Nodo<C, V> sucesor = obtenerSucesorInmediato(nodo);

        nodo.clave = sucesor.clave;
        nodo.valor = sucesor.valor;

        // Elimino el sucesor
        if (sucesor.hijoIzquierdo == null && sucesor.hijoDerecho == null) {
            eliminarHoja(sucesor);
        } else {
            eliminarNodoConUnHijo(sucesor);
        }
    }

    private void inorderRecursivo(Nodo<C, V> nodo, Lista<Tupla<C, V>> resultado) {
        if (nodo == null) {
            return;
        }

        // izquierda, raíz, derecha
        inorderRecursivo(nodo.hijoIzquierdo, resultado);
        resultado.agregar(new Tupla<>(nodo.clave, nodo.valor));
        inorderRecursivo(nodo.hijoDerecho, resultado);
    }

    private void preorderRecursivo(Nodo<C, V> nodo, Lista<Tupla<C, V>> resultado) {
        if (nodo == null) {
            return;
        }

        // raíz, izquierda, derecha
        resultado.agregar(new Tupla<>(nodo.clave, nodo.valor));
        preorderRecursivo(nodo.hijoIzquierdo, resultado);
        preorderRecursivo(nodo.hijoDerecho, resultado);
    }

    private void postorderRecursivo(Nodo<C, V> nodo, Lista<Tupla<C, V>> resultado) {
        if (nodo == null) {
            return;
        }

        // izquierda, derecha, raíz
        postorderRecursivo(nodo.hijoIzquierdo, resultado);
        postorderRecursivo(nodo.hijoDerecho, resultado);
        resultado.agregar(new Tupla<>(nodo.clave, nodo.valor));
    }
}
