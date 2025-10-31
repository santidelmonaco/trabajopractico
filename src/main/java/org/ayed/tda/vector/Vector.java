package org.ayed.tda.vector;

public class Vector<T> {
    private T[] datos;
    private int tamanioFisico;
    private int tamanioLogico;

    // Constante para el tamaño inicial
    private static final int TAMANIO_INICIAL = 0;

    /**
     * Constructor de Vector.
     */
    @SuppressWarnings("unchecked")
    public Vector() {
        this.tamanioFisico = TAMANIO_INICIAL;
        this.tamanioLogico = 0;
        this.datos = (T[]) new Object[TAMANIO_INICIAL];
    }

    /**
     * Constructor de copia de Vector.
     *
     * @param vector Vector a copiar.
     *               No puede ser nulo.
     * @throws ExcepcionVector si el vector es nulo.
     */
    @SuppressWarnings("unchecked")
    public Vector(Vector<T> vector) {
        if (vector == null) {
            throw new ExcepcionVector("El vector no puede ser nulo.");
        }

        this.tamanioFisico = vector.tamanioFisico;
        this.tamanioLogico = vector.tamanioLogico;

        this.datos = (T[]) new Object[this.tamanioFisico];

        for (int i = 0; i < this.tamanioLogico; i++) {
            this.datos[i] = vector.datos[i];
        }
    }

    /**
     * Agrega un dato al final del vector.
     *
     * @param dato Dato a agregar.
     */
    public void agregar(T dato) {
        // PASO 1: Verificar si necesitamos más espacio
        // Si el vector está lleno (tamanioLogico == tamanioFisico),
        // necesitamos redimensionar antes de agregar
        if (tamanioLogico >= tamanioFisico) {
            // Calculamos el nuevo tamaño usando la estrategia factor-of-two
            int nuevoTamanio;

            if (tamanioFisico == 0) {
                // Caso especial: si la capacidad actual es cero,
                // crecemos a uno (no podemos duplicar cero)
                nuevoTamanio = 1;
            } else {
                // Caso normal: duplicamos la capacidad
                nuevoTamanio = tamanioFisico * 2;
            }

            // Redimensionamos el array
            redimensionar(nuevoTamanio);
        }

        // PASO 2: Agregar el elemento
        // En este punto, GARANTIZAMOS que hay espacio disponible
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
     * @throws ExcepcionVector si el índice no es válido.
     */
    public void agregar(T dato, int indice) {
        if (indice < 0 || indice > tamanioLogico) {
            throw new ExcepcionVector("Índice inválido.");
        }

        if (tamanioLogico >= tamanioFisico) {
            int nuevoTamanio = (tamanioFisico == 0) ? 1 : tamanioFisico * 2;
            redimensionar(nuevoTamanio);
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
        datos[tamanioLogico] = null;

        // redimensionamos el vector
        if (tamanioFisico > 0 && tamanioLogico < tamanioFisico / 2) {
            int nuevoTamanio = tamanioFisico / 2;
            if (nuevoTamanio >= tamanioLogico) {
                redimensionar(nuevoTamanio);
            }
        }

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

        // redimensiono
        if (tamanioFisico > 0 && tamanioLogico < tamanioFisico / 2) {
            int nuevoTamanio = tamanioFisico / 2;
            if (nuevoTamanio >= tamanioLogico) {
                redimensionar(nuevoTamanio);
            }
        }

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
     * Obtiene el tamaño máximo del vector.
     * <p>
     * NOTA: Este método es únicamente para probar
     * el TDA.
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

    /****  HELPERS ****/

    /**
     * Redimensiona el array interno cuando es necesario.
     *
     * @param nuevoTamanio El nuevo tamaño físico deseado para el array.
     */
    @SuppressWarnings("unchecked")
    private void redimensionar(int nuevoTamanio) {
        T[] nuevoArray = (T[]) new Object[nuevoTamanio];

        int elementosACopiar = Math.min(tamanioLogico, nuevoTamanio);
        for (int i = 0; i < elementosACopiar; i++) {
            nuevoArray[i] = datos[i];
        }

        this.datos = nuevoArray;

        this.tamanioFisico = nuevoTamanio;
    }
}
