package org.ayed.tda.vector;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VectorTest {
    private Vector<Integer> vector;
    private Integer dato;

    @BeforeEach
    void setUp() {
        vector = new Vector<>();
    }

    @Test
    void Vector_constructor_de_copia() {
        for (int i = 1; i <= 10; i++) {
            vector.agregar(i);
        }

        Vector<Integer> vectorCopia = new Vector<>(vector);

        assertEquals(vector.tamanio(), vectorCopia.tamanio());
        for (int i = 0; i < vector.tamanio(); i++) {
            assertEquals(vector.dato(i), vectorCopia.dato(i));
        }
    }

    @Test
    void Vector_vacio_constructor_de_copia() {
        Vector<Integer> vectorCopia = new Vector<>(vector);

        assertEquals(vector.tamanio(), vectorCopia.tamanio());
        for (int i = 0; i < vector.tamanio(); i++) {
            assertEquals(vector.dato(i), vectorCopia.dato(i));
        }
    }

    @Test
    void Vector_nulo_no_puede_copiarse() {
        vector = null;
        assertThrows(ExcepcionVector.class, () -> new Vector<>(vector));
    }

    @Test
    void Vector_agregar() {
        assertDoesNotThrow(() -> vector.agregar(1));
    }

    @Test
    void Vector_agregar_indice() {
        assertDoesNotThrow(() -> vector.agregar(2, 0));
    }

    @Test
    void Vector_no_puede_agregar_indice_si_el_indice_es_negativo() {
        assertThrows(ExcepcionVector.class, () -> vector.agregar(1, -1));
    }

    @Test
    void Vector_no_puede_agregar_indice_si_el_indice_es_mayor_al_tamanio() {
        assertThrows(ExcepcionVector.class, () -> vector.agregar(1, 1));
    }

    @Test
    void Vector_eliminar() {
        vector.agregar(1);
        assertDoesNotThrow(() -> dato = vector.eliminar());
        assertEquals(1, dato);
    }

    @Test
    void Vector_no_puede_eliminar_si_esta_vacio() {
        vector.agregar(1);
        assertDoesNotThrow(() -> vector.eliminar());
        assertThrows(ExcepcionVector.class, () -> vector.eliminar());
    }

    @Test
    void Vector_eliminar_indice() {
        vector.agregar(1);
        vector.agregar(2);
        assertDoesNotThrow(() -> dato = vector.eliminar(0));
        assertEquals(1, dato);
    }

    @Test
    void Vector_no_puede_eliminar_indice_si_esta_vacio() {
        vector.agregar(1);
        assertDoesNotThrow(() -> vector.eliminar());
        assertThrows(ExcepcionVector.class, () -> vector.eliminar(0));
    }

    @Test
    void Vector_no_puede_eliminar_indice_si_el_indice_es_negativo() {
        vector.agregar(1);
        assertThrows(ExcepcionVector.class, () -> vector.eliminar(-1));
    }

    @Test
    void Vector_no_puede_eliminar_indice_si_el_indice_es_mayor_o_igual_al_tamanio() {
        vector.agregar(1);
        assertThrows(ExcepcionVector.class, () -> vector.eliminar(1));
    }

    @Test
    void Vector_vacio() {
        assertTrue(vector.vacio());
    }

    @Test
    void Vector_no_vacio() {
        vector.agregar(1);
        assertFalse(vector.vacio());
    }

    @Test
    void Vector_tamanio_vacio() {
        assertEquals(0, vector.tamanio());
    }

    @Test
    void Vector_tamanio_no_vacio() {
        vector.agregar(1);
        assertEquals(1, vector.tamanio());
    }

    @Test
    void Vector_tamanio_maximo_vacio() {
        assertEquals(0, vector.tamanioMaximo());
    }

    @Test
    void Vector_tamanio_maximo_no_vacio() {
        vector.agregar(1);
        assertEquals(1, vector.tamanioMaximo());
        vector.agregar(2);
        vector.agregar(3);
        assertTrue(3 <= vector.tamanioMaximo() && vector.tamanioMaximo() <= 4);
        for (int i = 4; i <= 13; i++) {
            vector.agregar(i);
        }
        assertTrue(13 <= vector.tamanioMaximo() && vector.tamanioMaximo() <= 16);
        for (int i = 0; i < 8; i++) {
            vector.eliminar();
        }
        assertTrue(5 <= vector.tamanioMaximo() && vector.tamanioMaximo() <= 8);
    }

    @Test
    void Vector_dato() {
        vector.agregar(1);
        assertEquals(1, vector.dato(0));
    }

    @Test
    void Vector_no_puede_obtener_dato_si_esta_vacio() {
        assertThrows(ExcepcionVector.class, () -> vector.dato(0));
    }

    @Test
    void Vector_no_puede_obtener_dato_si_el_indice_es_negativo() {
        vector.agregar(1);
        assertThrows(ExcepcionVector.class, () -> vector.dato(-1));
    }

    @Test
    void Vector_no_puede_obtener_dato_si_el_indice_es_mayor_o_igual_al_tamanio() {
        vector.agregar(1);
        assertThrows(ExcepcionVector.class, () -> vector.dato(1));
    }

    @Test
    void Vector_modificar_dato() {
        vector.agregar(1);
        vector.modificarDato(2, 0);
        assertEquals(2, vector.dato(0));
    }

    @Test
    void Vector_no_puede_modificar_dato_si_el_tamanio_es_cero() {
        assertThrows(ExcepcionVector.class, () -> vector.modificarDato(1, 0));
    }

    @Test
    void Vector_no_puede_modificar_dato_si_esta_vacio() {
        assertThrows(ExcepcionVector.class, () -> vector.modificarDato(1, 0));
    }

    @Test
    void Vector_no_puede_modificar_dato_si_el_indice_es_negativo() {
        vector.agregar(1);
        assertThrows(ExcepcionVector.class, () -> vector.modificarDato(2, -1));
    }

    @Test
    void Vector_no_puede_modificar_dato_si_el_indice_es_mayor_o_igual_al_tamanio() {
        vector.agregar(1);
        assertThrows(ExcepcionVector.class, () -> vector.modificarDato(2, 1));
    }

    @Test
    void Vector_caso_de_uso() {
        // El vector es [1, 2, 9, 3, 5, 6, 7, 10].
        Integer[] vectorEsperado = {1, 2, 9, 3, 5, 6, 7, 10};

        // [1, 2, 3, 4, 5, 6, 7, 8].
        for (int i = 1; i <= 8; i++) {
            vector.agregar(i);
        }
        // [1, 2, 9, 3, 4, 5, 6, 7, 8].
        vector.agregar(9, 2);
        // [1, 2, 9, 3, 5, 6, 7, 8].
        vector.eliminar(4);
        // [1, 2, 9, 3, 5, 6, 7].
        vector.eliminar();
        // [1, 2, 9, 3, 5, 6, 7, 10].
        vector.agregar(10);

        assertEquals(vectorEsperado.length, vector.tamanio());
        for (int i = 0; i < vectorEsperado.length; i++) {
            assertEquals(vectorEsperado[i], vector.dato(i));
        }
    }
}
