package utiles.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import utiles.sql.sentencia.SentenciaSql;

/**
 * Ejecutor de sentecias inserción de entidades
 *
 * @author jberjano
 */
public class EjecutorSentenciaInsert extends EjecutorSentenciaGuardado {

    public EjecutorSentenciaInsert(GestorConexiones gestorConexiones) {
        super(gestorConexiones);
    }

    @Override
    public boolean ejecutar() throws SQLException {
        Connection conexion = gestorConexiones.obtenerConexion();
        if (conexion == null) {
            return false;
        }

        int filasAfectadas = 0;
        PreparedStatement statement = null;
        try {
            SentenciaSql sentencia = new SentenciaSql();
            sentencia.insert(tabla);
            
            List<Campo> camposAsignables = new ArrayList<Campo>();
            List<Campo> listaTodosCampos = new ArrayList<Campo>();
            listaTodosCampos.addAll(listaPKs);
            listaTodosCampos.addAll(listaCampos);
            for (Campo campo : listaTodosCampos) {
                String valorSql = getSqlValor(campo);
                if (valorSql.equals("?")) {
                    sentencia.asignarLuego(campo.nombre);
                    camposAsignables.add(campo);
                } else {
                    sentencia.asignarValorSql(campo.nombre, valorSql);
                }
            }
            statement = conexion.prepareStatement(sentencia.toString());

            for (Campo campo : camposAsignables) {
                asignarValor(statement, campo.valor);
            }
            filasAfectadas = statement.executeUpdate();
        } finally {
            if (statement != null) {
                statement.close();
            }
            gestorConexiones.liberarConexion(conexion);
            limpiar();
        }
        return filasAfectadas > 0;
    }

}
