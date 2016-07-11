package utiles.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import utiles.sql.EjecutorSentenciaGuardado.Campo;

/**
 *
 * @author jberjano
 */
public class EjecutorSentenciaSimple {
    
    protected final GestorConexiones gestorConexiones;
    
    public EjecutorSentenciaSimple(GestorConexiones gestorConexiones) {
        this.gestorConexiones = gestorConexiones;
    }
    
    public boolean ejecutar(String sql) throws SQLException {
        Connection conexion = gestorConexiones.obtenerConexion();
        if (conexion == null) {
            return false;
        }
        PreparedStatement statement = null;
        
        try {            
            statement = conexion.prepareStatement(sql);
            statement.executeUpdate();
        } finally {
            if (statement != null) {
                statement.close();
            }
            gestorConexiones.liberarConexion(conexion);
        }
        return true;
    }
}
