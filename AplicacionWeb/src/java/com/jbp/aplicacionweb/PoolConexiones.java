package com.jbp.aplicacionweb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import utiles.sql.ConstructorFechaHora;
import utiles.sql.EjecutorSentenciaSelect;
import utiles.sql.GestorConexiones;
import utiles.sql.compatibilidad.CompatibilidadSql;
import utiles.sql.compatibilidad.FormateadorSql;
import utiles.tiempo.FechaHora;

/**
 * Implementación de un gestor de conexiones como un pool.
 * @author jberjano
 */
public class PoolConexiones implements GestorConexiones {

    private List<Connection> pool = new ArrayList();

    private final String driver; // "oracle.jdbc.OracleDriver";
    private final String cadenaConexion; // "jdbc:oracle:thin:@10.234.252.160:1521:meta4";
    private final String usuario; // "ebambi";
    private final String password; // "shs";
    private final boolean local;

    @Override
    public void finalize() {
        cerrarConexiones();
    }

    public PoolConexiones(String driver, String cadenaConexion, String usuario, String password, boolean local) {
        this.driver = driver;
        this.cadenaConexion = cadenaConexion;
        this.usuario = usuario;
        this.password = password;
        this.local = local;
    }

    @Override
    public void inicializar() throws ClassNotFoundException {
        Class.forName(driver);
    }

    @Override
    synchronized public Connection obtenerConexion() throws SQLException {

        if (pool.isEmpty()) {
            return DriverManager.getConnection(cadenaConexion, usuario, password);
        }
        Connection conexion = pool.get(pool.size() - 1);
        pool.remove(conexion);
        return conexion;
    }

    @Override
    public void liberarConexion(Connection conexion) {
        pool.add(conexion);
        System.out.println("Conexiones: " + pool.size());
    }

    private ConstructorFechaHora constructorFechaHora = new ConstructorFechaHora();
    private EjecutorSentenciaSelect ejecutorSentenciaSelect = new EjecutorSentenciaSelect(this);

    @Override
    public FechaHora getFechaHora() {

        try {
            String sql = getFormateadorSql().getSelectConsultaFechaHora();
            return ejecutorSentenciaSelect.obtenerEntidad(sql, constructorFechaHora);
        } catch (SQLException ex) {
            procesarExcepcion(ex);
        }
        return null;
    }

    public void cerrarConexiones() {
        for (Connection conexion : pool) {
            try {
                conexion.close();
            } catch (SQLException ex) {
            }
        }
    }

    public void procesarExcepcion(SQLException ex) {
        int error = ex.getErrorCode();

        Set<Integer> codigosErrorDesconexion = getFormateadorSql().getCodigosErrorDesconexion();
        if (codigosErrorDesconexion != null
                && codigosErrorDesconexion.contains(error)) {
            cerrarConexiones();
        }
    }

    @Override
    public boolean esConexionLocal() {
        return local;
    }

    @Override
    public FormateadorSql getFormateadorSql() {
        return CompatibilidadSql.getFormateador(driver);
    }

    @Override
    public String getDriver() {
        return driver;
    }

}
