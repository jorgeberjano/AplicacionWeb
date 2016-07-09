package com.jbp.aplicacionweb.dao;

import com.jbp.ges.entidad.CampoGes;
import com.jbp.ges.entidad.ConsultaGes;
import java.sql.SQLException;
import java.util.List;
import utiles.sql.AdaptadorResultSet;
import utiles.sql.ConstructorEntidad;

/**
 *
 * @author jberjano
 */
public class ConstructorEntidadGes extends ConstructorEntidad<EntidadGes> {

    private ConsultaGes consulta;

    public ConstructorEntidadGes(ConsultaGes consulta) {
        this.consulta = consulta;
    }

    @Override
    protected EntidadGes construirEntidad(AdaptadorResultSet rs) throws SQLException {
        EntidadGes entidad = new EntidadGes();
        
        List<CampoGes> campos = consulta.getListaCampos();
        
        for (CampoGes campo : campos) {
            String nombreCampo = campo.getNombre();
            Object valor = rs.get(campo);
            if (campo.isClave()) {
                entidad.getClavePrimaria().set(nombreCampo, valor);
            }
            entidad.set(nombreCampo, valor);            
        }
       
        return entidad;
    }
}
