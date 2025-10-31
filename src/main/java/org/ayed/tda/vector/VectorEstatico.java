package org.ayed.tda.vector;

public class VectorEstatico<T> {
    private T[] datos;
    private int tamanioFisico;
    private int tamanioLogico;

    /**
     * Constructor de Vector Estático.
     *
     * @param tamanio Tamaño físico del vector.
     *                No puede ser negativo.
     * @throws ExcepcionVector si el tamaño físico es negativo.
     */
    @SuppressWarnings("unchecked")
    public VectorEstatico(int tamanio) {
        if (tamanio < 0) {
            throw new ExcepcionVector("El tamaño no puede ser negativo.");
        }

        this.tamanioFisico = tamanio;

        this.tamanioLogico = 0;

        this.datos = (T[]) new Object[tamanio];
    }

    /**
     * Constructor de copia de Vector Estático.
     *
     * @param vectorEstatico Vector a copiar.
     *                       No puede ser nulo.
     * @throws ExcepcionVector si el vector es nulo.
     */
    @SuppressWarnings("unchecked")
    public VectorEstatico(VectorEstatico<T> vectorEstatico) {
        if (vectorEstatico == null) {
            throw new ExcepcionVector("El vector no puede ser nulo.");
        }

        this.tamanioFisico = vectorEstatico.tamanioFisico;
        this.tamanioLogico = vectorEstatico.tamanioLogico;

        this.datos = (T[]) new Object[this.tamanioFisico];

        for (int i = 0; i < this.tamanioLogico; i++) {
            this.datos[i] = vectorEstatico.datos[i];
        }
    }

    /**
     * Agrega un dato al final del vector.
     *
     * @param dato Dato a agregar.
     * @throws ExcepcionVector si el vector está lleno.
     */
    public void agregar(T dato) {
        if (tamanioLogico >= tamanioFisico) {
            throw new ExcepcionVector("El vector está lleno.");
        }

        datos[tamanioLogico] = dato;

        tamanioLogico++;
    }

    /**
     * Agrega un dato al vector en el índice indicado.
     * <p>
     * Ejemplo:
     * <pre>
     * {@code
     * >> [1, 3, 2, 7, 0]
     * agregar(9, 2);
     * >> [1, 3, 9, 2, 7, 0]
     * }
     * </pre>
     *
     * @param dato   Dato a agregar.
     * @param indice Índice en el que se inserta el dato.
     *               No puede ser negativo.
     *               No puede ser mayor que el tamaño del vector.
     * @throws ExcepcionVector si el vector está lleno,
     *                         o si el índice no es válido.
     */
    public void agregar(T dato, int indice) {
        if (tamanioLogico >= tamanioFisico) {
            throw new ExcepcionVector("El vector está lleno.");
        }

        if (indice < 0 || indice > tamanioLogico) {
            throw new ExcepcionVector("Índice inválido.");
        }

        // Desplazo elementos hacia la derecha
        for (int i = tamanioLogico; i > indice; i--) {
            datos[i] = datos[i - 1];
        }

        datos[indice] = dato;

        tamanioLogico++;
    }

    /**
     * Elimina el último dato del vector.
     *
     * @return el dato eliminado.
     * @throws ExcepcionVector si el vector está vacío.
     */
    public T eliminar() {
        if (tamanioLogico == 0) {
            throw new ExcepcionVector("El vector está vacío.");
        }

        tamanioLogico--;

        T datoEliminado = datos[tamanioLogico];

        // Limpio la referencia
        datos[tamanioLogico] = null;

        return datoEliminado;
    }

    /**
     * Elimina el dato del vector en el índice indicado.
     * <p>
     * Ejemplo:
     * <pre>
     * {@code
     * >> [1, 3, 2, 7, 0]
     * eliminar(1);
     * >> [1, 2, 7, 0]
     * }
     * </pre>
     *
     * @param indice Índice del dato a eliminar.
     *               No puede ser negativo.
     *               No puede ser mayor o igual que el tamaño del vector.
     * @return el dato eliminado.
     * @throws ExcepcionVector si el vector está vacío,
     *                         o si el índice no es válido.
     */
    public T eliminar(int indice) {
        if (tamanioLogico == 0) {
            throw new ExcepcionVector("El vector está vacío.");
        }

        if (indice < 0 || indice >= tamanioLogico) {
            throw new ExcepcionVector("Índice inválido.");
        }

        T datoEliminado = datos[indice];

        // Desplazo elementos hacia la izquierda
        for (int i = indice; i < tamanioLogico - 1; i++) {
            datos[i] = datos[i + 1];
        }

        tamanioLogico--;

        datos[tamanioLogico] = null;

        return datoEliminado;
    }

    /**
     * Obtiene el dato del vector en el índice indicado.
     *
     * @param indice Índice del dato a obtener.
     *               No puede ser negativo.
     *               No puede ser mayor o igual que el tamaño del vector.
     * @return el dato en el índice indicado.
     * @throws ExcepcionVector si el índice no es válido.
     */
    public T dato(int indice) {
        if (tamanioLogico == 0) {
            throw new ExcepcionVector("El vector está vacío.");
        }

        if (indice < 0 || indice >= tamanioLogico) {
            throw new ExcepcionVector("Índice inválido.");
        }

        return datos[indice];
    }

    /**
     * Modifica el dato del vector en el índice indicado
     * por el dato indicado por parámetro.
     *
     * @param indice Índice del dato a modificar.
     *               No puede ser negativo.
     *               No puede ser mayor o igual que el tamaño del vector.
     * @throws ExcepcionVector si el índice no es válido.
     */
    public void modificarDato(T dato, int indice) {
        if (tamanioLogico == 0) {
            throw new ExcepcionVector("El vector está vacío.");
        }

        if (indice < 0 || indice >= tamanioLogico) {
            throw new ExcepcionVector("Índice inválido.");
        }

        datos[indice] = dato;
    }

    /**
     * Obtiene el tamaño del vector.
     *
     * @return el tamaño del vector.
     */
    public int tamanio() {
        return tamanioLogico;
    }

    /**
     * Obtiene el tamaño máximo de datos del vector.
     *
     * @return el tamaño máximo del vector.
     */
    public int tamanioMaximo() {
        return tamanioFisico;
    }

    /**
     * Evalúa si el vector está vacío.
     *
     * @return true si el vector está vacío.
     */
    public boolean vacio() {
        return tamanioLogico == 0;
    }
}
