package utiles.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Ejecutor de sentecias modificacion de entidades
 * @author jberjano
 */
public class EjecutorSentenciaUpdate extends EjecutorSentenciaGuardado {
    
    public EjecutorSentenciaUpdate(GestorConexiones gestorConexiones) {
        super(gestorConexiones);
    }
        
    @Override
    public boolean ejecutar() throws SQLException {
        System.out.println("ejecutar");
        Connection conexion = gestorConexiones.obtenerConexion();
        if (conexion == null) {
            return false;
        }
        // TODO: ver si es util una transaccion
        //conexion.setAutoCommit(false);
        PreparedStatement statement = null;
        int afectados = 0;
        try {
            boolean primero = true;
            // TODO: usar un objeto SentenciaSql
            StringBuilder sql = new StringBuilder();
            sql.append("update ");
            sql.append(tabla);
            sql.append(" set ");
            for (Campo campo : listaCampos) {
                if (!primero) {
                    sql.append(", ");
                } else {
                    primero = false;
                }
                sql.append(campo.nombre);
                sql.append(" = ");
                sql.append(getSqlValor(campo));
            }
            sql.append(" where ");
            primero = true;
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
            
            System.out.println(sql);
            
            for (Campo campo : listaCampos) {
                asignarValor(statement, campo.valor);
            }
            for (Campo campo : listaPKs) {
                asignarValor(statement, campo.valor);
            }     
            
            afectados = statement.executeUpdate();
            //conexion.commit();
        } finally {
            limpiar();
            if (statement != null) {
                statement.close();
            }
            gestorConexiones.liberarConexion(conexion);            
        }
        
        return afectados == 1;
    }
}
