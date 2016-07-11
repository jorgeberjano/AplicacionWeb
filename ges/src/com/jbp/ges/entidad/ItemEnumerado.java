package com.jbp.ges.entidad;

import java.io.Serializable;

/**
 *
 * @author jberjano
 */
public class ItemEnumerado<T> implements Serializable {
    private T valor;
    private String texto;

    public T getValor() {
        return valor;
    }

    public void setValor(T valor) {
        this.valor = valor;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }
}
