/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jbp.ges.utilidades;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Jorge
 */
public class TestGestorSimbolos {

    public TestGestorSimbolos() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void sustituirSimbolos() {

        GestorSimbolos g = new GestorSimbolos();

        String cadena = "HOLA MUNDO {$AS} HELLO WORLD";
        String resultado = g.sustituirSimbolos(cadena);
        String esperado = "HOLA MUNDO AS HELLO WORLD";
        assertEquals(resultado, esperado);

    }
}
