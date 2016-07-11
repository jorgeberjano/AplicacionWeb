package utiles.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Set;
import utiles.sql.compatibilidad.CompatibilidadSql;
import utiles.sql.compatibilidad.FormateadorSql;
import utiles.tiempo.FechaHora;

/**
 *
 * @author jberjano
 */
public class GestorConexionUnica implements GestorConexiones {

    private final String driver; // "oracle.jdbc.OracleDriver";
    private final String cadenaConexion; // "jdbc:oracle:thin:@10.234.252.160:1521:meta4";
    private final String usuario; // "ebambi";
    private final String password; // "shs";
    private final boolean local;
    private Connection conexion;

    @Override
    public void finalize() {
        cerrarConexion();
    }

    public GestorConexionUnica(String driver, String cadenaConexion, String usuario, String password, boolean local) {
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
    public Connection obtenerConexion() throws SQLException {

        if (conexion != null) {
            return conexion;
        }
        conexion = DriverManager.getConnection(cadenaConexion, usuario, password);

        return conexion;
    }

    @Override
    public void liberarConexion(Connection conexion) {
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

    public void cerrarConexion() {
        if (conexion != null) {
            try {
                conexion.close();
            } catch (SQLException ex) {
            }
            conexion = null;
        }
    }

    public void procesarExcepcion(SQLException ex) {
        int error = ex.getErrorCode();

        Set<Integer> codigosErrorDesconexion = getFormateadorSql().getCodigosErrorDesconexion();
        if (codigosErrorDesconexion != null
                && codigosErrorDesconexion.contains(error)) {
            cerrarConexion();
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
