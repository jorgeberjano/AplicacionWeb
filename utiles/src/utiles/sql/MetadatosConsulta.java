package utiles.sql;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

/**
 * Utilidad para manejo de metadatos de un resultset.
 * @author jberjano
 */
public class MetadatosConsulta {
    private ResultSetMetaData metadata;
    
    public MetadatosConsulta(ResultSet rs) {
        try {
            this.metadata = rs.getMetaData();
        } catch (SQLException ex) {        
        }
    }

    public int getIndice(String campo, int numeroOcurrencia) throws SQLException {  
        if (metadata == null) {
            return -1;
        }
        
        int coincidencias = 0;
        for (int i = 1; i <= metadata.getColumnCount(); i++) {
            if (metadata.getColumnName(i).equals(campo)) {
                if (++coincidencias == numeroOcurrencia) {
                    return i;
                }
            }
        }
        return -1;
    }
}
