package org.ayed.tda.vector;

public class ExcepcionVector extends RuntimeException {
    /**
     * Constructor de ExcepcionVector.
     * Esta clase es para el uso exclusivo de Vector.
     *
     * @param mensaje Mensaje de error.
     * @see Vector
     */
    public ExcepcionVector(String mensaje) {
        super(mensaje);
    }
}
