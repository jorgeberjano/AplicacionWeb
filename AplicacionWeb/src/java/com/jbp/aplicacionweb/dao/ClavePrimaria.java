package com.jbp.aplicacionweb.dao;

import com.jbp.ges.entidad.ConsultaGes;

/**
 *
 * @author Jorge
 */
public class ClavePrimaria extends MapaValores {
    
    public ClavePrimaria() {        
    }
            
    public static ClavePrimaria crearDeCadena(String cadena, ConsultaGes consulta) {
        ClavePrimaria clavePrimaria = new ClavePrimaria();
        clavePrimaria.parsear(cadena, consulta, "%", "=");
        return clavePrimaria;
    }
}
