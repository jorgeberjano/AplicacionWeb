package utiles.web.dto;

import com.jbp.aplicacionweb.dto.DatosSesionTabla;

/**
 *
 * @author jberjano
 */
public interface IServicioPaginacion {
    
    public boolean actualizarPagina(DatosSesionTabla datosPagina); 
    public void limpiarCache();    
    public String getMensajeError();
}
