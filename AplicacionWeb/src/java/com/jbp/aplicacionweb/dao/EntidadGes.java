package com.jbp.aplicacionweb.dao;

import java.io.Serializable;

/**
 * Representa una entidad generica GES
 * @author Jorge
 */
public class EntidadGes extends MapaValores implements Serializable {
    private ClavePrimaria pk = new ClavePrimaria();
    
    public void setClavePrimaria(ClavePrimaria pk) {
        this.pk = pk;
    }
    
    public ClavePrimaria getClavePrimaria() {
        return pk;
    }

    boolean esNueva() {
        return pk == null || pk.isEmpty();
    }
}
