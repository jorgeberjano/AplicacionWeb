package com.jbp.aplicacionweb.dto;

/**
 *
 * @author jberjano
 */
public interface IServicioPaginacion {
    
    public boolean actualizarPagina(DatosSesionTabla datosPagina); 
    public void limpiarCache();    
    public String getMensajeError();
}
