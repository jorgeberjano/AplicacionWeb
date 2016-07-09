package com.jbp.aplicacionweb.dto;

import java.util.HashMap;

/**
 *
 * @author Jorge
 */
public class MapaDto extends HashMap<String, ValorDto> {

    @Override
    public ValorDto get(Object key) {
        ValorDto valor = super.get(key);
        if (valor == null) {
            valor = new ValorDto(); 
            valor.setValor("autogenerado");
            put(key.toString(), valor);
        }
        return valor;
    }
}
