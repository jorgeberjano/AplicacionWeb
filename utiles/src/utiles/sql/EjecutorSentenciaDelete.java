package utiles.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Clase base para los ejecutores de sentecias de borrado de entidades (delete)
 *
 * @author jberjano
 */
public class EjecutorSentenciaDelete extends EjecutorSentenciaGuardado {
  
    
    public EjecutorSentenciaDelete(GestorConexiones gestorConexiones) {
        super(gestorConexiones);
    }

    @Override
    public boolean ejecutar() throws SQLException {
        Connection conexion = gestorConexiones.obtenerConexion();
        if (conexion == null) {
            return false;
        }
        PreparedStatement statement = null;

        try {
            
            StringBuilder sql = new StringBuilder();
            sql.append("delete from ");
            sql.append(tabla);
            sql.append(" where ");
            
            boolean primero = true;
            for (Campo campo : listaPKs) {
                if (!primero) {
                    sql.append(" and ");
                } else {
                    primero = false;
                }
                sql.append(campo.nombre);
                sql.append(" = ");
                sql.append(campo.valor == null ? "null" : "?");
            }

            statement = conexion.prepareStatement(sql.toString());

            for (Campo campo : listaPKs) {
                asignarValor(statement, campo.valor);
            }
            statement.executeUpdate();
        } finally {
            if (statement != null) {
                statement.close();
            }
            gestorConexiones.liberarConexion(conexion);
            limpiar();
        }
        return true;
    }
}
