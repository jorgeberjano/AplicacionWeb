package utiles.sql;

import java.sql.Connection;
import java.sql.SQLException;
import utiles.sql.compatibilidad.FormateadorSql;
import utiles.tiempo.FechaHora;

/**
 * Contrato que deben cumplir los gestores de conexiones.
 * @author jberjano
 */
public interface GestorConexiones {
    void inicializar() throws ClassNotFoundException;
    Connection obtenerConexion() throws SQLException;
    void liberarConexion(Connection conexion);
    boolean esConexionLocal();
    FechaHora getFechaHora();
    FormateadorSql getFormateadorSql();
    String getDriver();
}
