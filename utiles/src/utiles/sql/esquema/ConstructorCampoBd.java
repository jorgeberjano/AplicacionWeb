package utiles.sql.esquema;

import utiles.sql.TipoDato;
import java.sql.SQLException;
import utiles.sql.AdaptadorResultSet;
import utiles.sql.ConstructorEntidad;

/**
 * Contructor de entidades CampoBd.
 *
 * @author jberjano
 */
public class ConstructorCampoBd extends ConstructorEntidad<CampoBd> {
      
    @Override
    protected CampoBd construirEntidad(AdaptadorResultSet rs) throws SQLException {

        CampoBd entidad = new CampoBd();

        entidad.setNombreSql(rs.getString("COLUMN_NAME"));
        Integer decimales = rs.getInteger("DECIMAL_DIGITS");
        Integer sqlType = rs.getInteger("DATA_TYPE");
        entidad.setTipoDato(TipoDato.fromSqlType(sqlType, decimales));
        entidad.setNoNulo(!rs.getBoolean("NULLABLE"));    
        entidad.setTamano(rs.getInteger("COLUMN_SIZE"));
        entidad.setDecimales(decimales);            
        return entidad;
    }

}
