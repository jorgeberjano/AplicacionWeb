package utiles.sql.compatibilidad;

import java.util.Set;
import utiles.sql.esquema.CampoBd;
import utiles.tiempo.Fecha;
import utiles.tiempo.FechaHora;

/**
 *
 * @author jberjano
 */
public interface FormateadorSql {

    String getFechaSql(Fecha fecha);
    
    String getFechaHoraSql(FechaHora fechaHora);
    
    String getBooleanSql(Boolean aBoolean);
    
    String getNombreTipoSql(CampoBd campo);

    String getSelectConsultaFechaHora();
        
    String getFucionNVL(String valor, String valorSiEsNulo);

    String getFechaHoraActual();
    
    Set<Integer> getCodigosErrorDesconexion();
}
