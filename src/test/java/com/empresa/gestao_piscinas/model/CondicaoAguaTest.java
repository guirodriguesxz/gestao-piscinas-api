package com.empresa.gestao_piscinas.model;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CondicaoAguaTest {

    @ParameterizedTest(name = "pH {0}, cloro {1} -> {2}")
    @CsvSource({
            "7.4, 2.0, IDEAL",
            "7.2, 1.0, IDEAL",
            "7.8, 3.0, IDEAL",
            "7.0, 2.0, ATENCAO",
            "7.4, 0.8, ATENCAO",
            "8.0, 4.0, ATENCAO",
            "6.5, 2.0, CRITICA",
            "8.5, 2.0, CRITICA",
            "7.4, 0.2, CRITICA",
            "7.4, 6.0, CRITICA"
    })
    void classificaAgua(double ph, double cloro, CondicaoAgua esperada) {
        assertEquals(esperada, CondicaoAgua.avaliar(ph, cloro));
    }
}
