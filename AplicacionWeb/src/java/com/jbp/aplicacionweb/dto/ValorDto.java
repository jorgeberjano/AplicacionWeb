package com.jbp.aplicacionweb.dto;

import java.util.List;

/**
 *
 * @author Jorge
 */
public class ValorDto {

    private String valor = "";
    private List<String> opciones;

    public ValorDto() {
    }

    public ValorDto(String valor) {
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public List<String> getOpciones() {
        return opciones;
    }

    public void setOpciones(List<String> opciones) {
        this.opciones = opciones;
    }
}
