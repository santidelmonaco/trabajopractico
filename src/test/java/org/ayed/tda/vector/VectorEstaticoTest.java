package org.ayed.tda.vector;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VectorEstaticoTest {
    private VectorEstatico<Integer> vectorEstatico;
    private Integer dato;

    @Test
    void Vector_estatico_no_puede_construirse_con_tamanio_negativo() {
        assertThrows(ExcepcionVector.class, () -> vectorEstatico = new VectorEstatico<>(-1));
    }

    @Test
    void Vector_estatico_puede_construirse_con_tamanio_cero() {
        assertDoesNotThrow(() -> vectorEstatico = new VectorEstatico<>(0));
    }

    @Test
    void Vector_estatico_constructor_de_copia() {
        vectorEstatico = new VectorEstatico<>(10);
        for (int i = 1; i <= 10; i++) {
            vectorEstatico.agregar(i);
        }

        VectorEstatico<Integer> vectorEstaticoCopia = new VectorEstatico<>(vectorEstatico);

        assertEquals(vectorEstatico.tamanio(), vectorEstaticoCopia.tamanio());
        for (int i = 0; i < vectorEstatico.tamanio(); i++) {
            assertEquals(vectorEstatico.dato(i), vectorEstaticoCopia.dato(i));
        }
    }

    @Test
    void Vector_estatico_vacio_constructor_de_copia() {
        vectorEstatico = new VectorEstatico<>(1);

        VectorEstatico<Integer> vectorEstaticoCopia = new VectorEstatico<>(vectorEstatico);

        assertEquals(vectorEstatico.tamanio(), vectorEstaticoCopia.tamanio());
        for (int i = 0; i < vectorEstatico.tamanio(); i++) {
            assertEquals(vectorEstatico.dato(i), vectorEstaticoCopia.dato(i));
        }
    }

    @Test
    void Vector_estatico_nulo_no_puede_copiarse() {
        vectorEstatico = null;
        assertThrows(ExcepcionVector.class, () -> new VectorEstatico<>(vectorEstatico));
    }

    @Test
    void Vector_estatico_agregar() {
        vectorEstatico = new VectorEstatico<>(1);
        assertDoesNotThrow(() -> vectorEstatico.agregar(1));
    }

    @Test
    void Vector_estatico_no_puede_agregar_si_el_tamanio_es_cero() {
        vectorEstatico = new VectorEstatico<>(0);
        assertThrows(ExcepcionVector.class, () -> vectorEstatico.agregar(1));
    }

    @Test
    void Vector_estatico_no_puede_agregar_si_esta_lleno() {
        vectorEstatico = new VectorEstatico<>(1);
        assertDoesNotThrow(() -> vectorEstatico.agregar(1));
        assertThrows(ExcepcionVector.class, () -> vectorEstatico.agregar(2));
    }

    @Test
    void Vector_estatico_agregar_indice() {
        vectorEstatico = new VectorEstatico<>(2);
        vectorEstatico.agregar(1);
        assertDoesNotThrow(() -> vectorEstatico.agregar(2, 0));
    }

    @Test
    void Vector_estatico_no_puede_agregar_indice_si_el_tamanio_es_cero() {
        vectorEstatico = new VectorEstatico<>(0);
        assertThrows(ExcepcionVector.class, () -> vectorEstatico.agregar(1, 0));
    }

    @Test
    void Vector_estatico_no_puede_agregar_indice_si_esta_lleno() {
        vectorEstatico = new VectorEstatico<>(1);
        assertDoesNotThrow(() -> vectorEstatico.agregar(1));
        assertThrows(ExcepcionVector.class, () -> vectorEstatico.agregar(2, 0));
    }

    @Test
    void Vector_estatico_no_puede_agregar_indice_si_el_indice_es_negativo() {
        vectorEstatico = new VectorEstatico<>(1);
        assertThrows(ExcepcionVector.class, () -> vectorEstatico.agregar(1, -1));
    }

    @Test
    void Vector_estatico_no_puede_agregar_indice_si_el_indice_es_mayor_al_tamanio() {
        vectorEstatico = new VectorEstatico<>(1);
        assertThrows(ExcepcionVector.class, () -> vectorEstatico.agregar(1, 1));
    }

    @Test
    void Vector_estatico_eliminar() {
        vectorEstatico = new VectorEstatico<>(1);
        vectorEstatico.agregar(1);
        assertDoesNotThrow(() -> dato = vectorEstatico.eliminar());
        assertEquals(1, dato);
    }

    @Test
    void Vector_estatico_no_puede_eliminar_si_el_tamanio_es_cero() {
        vectorEstatico = new VectorEstatico<>(0);
        assertThrows(ExcepcionVector.class, () -> vectorEstatico.eliminar());
    }

    @Test
    void Vector_estatico_no_puede_eliminar_si_esta_vacio() {
        vectorEstatico = new VectorEstatico<>(1);
        vectorEstatico.agregar(1);
        assertDoesNotThrow(() -> vectorEstatico.eliminar());
        assertThrows(ExcepcionVector.class, () -> vectorEstatico.eliminar());
    }

    @Test
    void Vector_estatico_eliminar_indice() {
        vectorEstatico = new VectorEstatico<>(2);
        vectorEstatico.agregar(1);
        vectorEstatico.agregar(2);
        assertDoesNotThrow(() -> dato = vectorEstatico.eliminar(0));
        assertEquals(1, dato);
    }

    @Test
    void Vector_estatico_no_puede_eliminar_indice_si_el_tamanio_es_cero() {
        vectorEstatico = new VectorEstatico<>(0);
        assertThrows(ExcepcionVector.class, () -> vectorEstatico.eliminar(0));
    }

    @Test
    void Vector_estatico_no_puede_eliminar_indice_si_esta_vacio() {
        vectorEstatico = new VectorEstatico<>(1);
        vectorEstatico.agregar(1);
        assertDoesNotThrow(() -> vectorEstatico.eliminar());
        assertThrows(ExcepcionVector.class, () -> vectorEstatico.eliminar(0));
    }

    @Test
    void Vector_estatico_no_puede_eliminar_indice_si_el_indice_es_negativo() {
        vectorEstatico = new VectorEstatico<>(1);
        vectorEstatico.agregar(1);
        assertThrows(ExcepcionVector.class, () -> vectorEstatico.eliminar(-1));
    }

    @Test
    void Vector_estatico_no_puede_eliminar_indice_si_el_indice_es_mayor_o_igual_al_tamanio() {
        vectorEstatico = new VectorEstatico<>(1);
        vectorEstatico.agregar(1);
        assertThrows(ExcepcionVector.class, () -> vectorEstatico.eliminar(1));
    }

    @Test
    void Vector_estatico_vacio() {
        vectorEstatico = new VectorEstatico<>(1);
        assertTrue(vectorEstatico.vacio());
    }

    @Test
    void Vector_estatico_no_vacio() {
        vectorEstatico = new VectorEstatico<>(1);
        vectorEstatico.agregar(1);
        assertFalse(vectorEstatico.vacio());
    }

    @Test
    void Vector_estatico_tamanio_vacio() {
        vectorEstatico = new VectorEstatico<>(1);
        assertEquals(0, vectorEstatico.tamanio());
    }

    @Test
    void Vector_estatico_tamanio_no_vacio() {
        vectorEstatico = new VectorEstatico<>(1);
        vectorEstatico.agregar(1);
        assertEquals(1, vectorEstatico.tamanio());
    }

    @Test
    void Vector_estatico_dato() {
        vectorEstatico = new VectorEstatico<>(1);
        vectorEstatico.agregar(1);
        assertEquals(1, vectorEstatico.dato(0));
    }

    @Test
    void Vector_estatico_no_puede_obtener_dato_si_el_tamanio_es_cero() {
        vectorEstatico = new VectorEstatico<>(0);
        assertThrows(ExcepcionVector.class, () -> vectorEstatico.dato(0));
    }

    @Test
    void Vector_estatico_no_puede_obtener_dato_si_esta_vacio() {
        vectorEstatico = new VectorEstatico<>(1);
        assertThrows(ExcepcionVector.class, () -> vectorEstatico.dato(0));
    }

    @Test
    void Vector_estatico_no_puede_obtener_dato_si_el_indice_es_negativo() {
        vectorEstatico = new VectorEstatico<>(1);
        vectorEstatico.agregar(1);
        assertThrows(ExcepcionVector.class, () -> vectorEstatico.dato(-1));
    }

    @Test
    void Vector_estatico_no_puede_obtener_dato_si_el_indice_es_mayor_o_igual_al_tamanio() {
        vectorEstatico = new VectorEstatico<>(1);
        vectorEstatico.agregar(1);
        assertThrows(ExcepcionVector.class, () -> vectorEstatico.dato(1));
    }

    @Test
    void Vector_estatico_modificar_dato() {
        vectorEstatico = new VectorEstatico<>(1);
        vectorEstatico.agregar(1);
        vectorEstatico.modificarDato(2, 0);
        assertEquals(2, vectorEstatico.dato(0));
    }

    @Test
    void Vector_estatico_no_puede_modificar_dato_si_el_tamanio_es_cero() {
        vectorEstatico = new VectorEstatico<>(0);
        assertThrows(ExcepcionVector.class, () -> vectorEstatico.modificarDato(1, 0));
    }

    @Test
    void Vector_estatico_no_puede_modificar_dato_si_esta_vacio() {
        vectorEstatico = new VectorEstatico<>(1);
        assertThrows(ExcepcionVector.class, () -> vectorEstatico.modificarDato(1, 0));
    }

    @Test
    void Vector_estatico_no_puede_modificar_dato_si_el_indice_es_negativo() {
        vectorEstatico = new VectorEstatico<>(1);
        vectorEstatico.agregar(1);
        assertThrows(ExcepcionVector.class, () -> vectorEstatico.modificarDato(2, -1));
    }

    @Test
    void Vector_estatico_no_puede_modificar_dato_si_el_indice_es_mayor_o_igual_al_tamanio() {
        vectorEstatico = new VectorEstatico<>(1);
        vectorEstatico.agregar(1);
        assertThrows(ExcepcionVector.class, () -> vectorEstatico.modificarDato(2, 1));
    }

    @Test
    void Vector_estatico_tamanio_maximo() {
        vectorEstatico = new VectorEstatico<>(10);
        assertEquals(10, vectorEstatico.tamanioMaximo());
    }

    @Test
    void Vector_estatico_caso_de_uso() {
        // El vector es [1, 2, 9, 3, 5, 6, 7, 10].
        Integer[] vectorEsperado = {1, 2, 9, 3, 5, 6, 7, 10};

        vectorEstatico = new VectorEstatico<>(10);
        // [1, 2, 3, 4, 5, 6, 7, 8].
        for (int i = 1; i <= 8; i++) {
            vectorEstatico.agregar(i);
        }
        // [1, 2, 9, 3, 4, 5, 6, 7, 8].
        vectorEstatico.agregar(9, 2);
        // [1, 2, 9, 3, 5, 6, 7, 8].
        vectorEstatico.eliminar(4);
        // [1, 2, 9, 3, 5, 6, 7].
        vectorEstatico.eliminar();
        // [1, 2, 9, 3, 5, 6, 7, 10].
        vectorEstatico.agregar(10);

        assertEquals(vectorEsperado.length, vectorEstatico.tamanio());
        for (int i = 0; i < vectorEsperado.length; i++) {
            assertEquals(vectorEsperado[i], vectorEstatico.dato(i));
        }
    }
}
