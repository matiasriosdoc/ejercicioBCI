package com.globalogic.bci.ejercicioapi;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EjercicioApiApplicationTest {
    String [] argumentos = new String[] {"argumento1", "argumento2"};

    @Test
    void testMainMethod() {
        EjercicioApiApplication.main(argumentos);
    }
}