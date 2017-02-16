package utiles.sql;

import java.sql.Connection;
import java.sql.SQLException;
import utiles.conversion.Propiedades;
import utiles.hilos.EjecutorAsincrono;
import utiles.hilos.EjecutorAsincrono.Proceso;
import utiles.sql.compatibilidad.FormateadorSql;
import utiles.tiempo.FechaHora;

/**
 * 
 * @author jberjano
 */
public class GestorConexionesRemotaLocal implements GestorConexiones {

    protected GestorConexionUnica gestorConexionRemota;
    protected GestorConexionUnica gestorConexionLocal;
    protected GestorConexionUnica gestorConexionActual;
    protected boolean conexionRemotaCorrecta = true;
    protected boolean conmutacionAutomatica = true;  
    protected boolean primeraComprobacion = true;
    protected int comprobarConexionRemotaCadaMs = 5000;
    
    protected EjecutorAsincrono comprobadorConexionRemota;

    public GestorConexionesRemotaLocal() {
    }

    public void configurar(Propiedades propiedades) {

        crearConexionRemota(
                propiedades.getString("bd.remota.driver"),
                propiedades.getString("bd.remota.conexion"),
                propiedades.getString("bd.remota.usuario"),
                propiedades.getString("bd.remota.clave"));

        crearConexionLocal(
                propiedades.getString("bd.local.driver"),
                propiedades.getString("bd.local.conexion"),
                propiedades.getString("bd.local.usuario"),
                propiedades.getString("bd.local.clave"));

        activarModoRemoto();
    }
    
    public void crearConexionRemota(String driver, String cadenaConexion, String usuario, String clave) {
        gestorConexionRemota = new GestorConexionUnica(
                driver,
                cadenaConexion,
                usuario,
                clave,
                false);
    }
    
    public void crearConexionLocal(String driver, String cadenaConexion, String usuario, String clave) {
        gestorConexionLocal = new GestorConexionUnica(
                driver,
                cadenaConexion,
                usuario,
                clave,
                true);
    }
    
    @Override
    public void inicializar() throws ClassNotFoundException {
        if (gestorConexionRemota != null) {
            gestorConexionRemota.inicializar();
        }
        if (gestorConexionLocal != null) {
            gestorConexionLocal.inicializar();
        }
        
        if (comprobarConexionRemotaCadaMs > 0) {
            comprobadorConexionRemota = new EjecutorAsincrono();
            comprobadorConexionRemota.setLatencia(comprobarConexionRemotaCadaMs);
            comprobadorConexionRemota.ejecutar(new Proceso() {
                @Override
                public void procesar() {
                    conexionRemotaCorrecta = gestorConexionRemota.getFechaHora() != null;
                }
            });
        }
    }

    /**
     * Activa o desactiva la seleccion automatica de conexion
     *
     * @param conmutacionAutomatica
     */
    public void setConmutacionAutomatica(boolean conmutacionAutomatica) {
        this.conmutacionAutomatica = conmutacionAutomatica;
    }

    public void activarModoRemoto() {
        activarGestorConexiones(gestorConexionRemota);
    }

    public void activarModoLocal() {
        activarGestorConexiones(gestorConexionLocal);
    }

    @Override
    public Connection obtenerConexion() throws SQLException {
        if (gestorConexionActual == null) {
            return null;
        } 
        return gestorConexionActual.obtenerConexion();
    }
    
    public Connection obtenerConexionLocal() throws SQLException {
        if (gestorConexionLocal == null) {
            return null;
        } 
        return gestorConexionLocal.obtenerConexion();
    }
    
    public Connection obtenerConexionRemota() throws SQLException {
        if (gestorConexionRemota == null) {
            return null;
        } 
        return gestorConexionRemota.obtenerConexion();
    }

    @Override
    public void liberarConexion(Connection conexion) {
        if (gestorConexionActual == null) {
            return;
        }
        gestorConexionActual.liberarConexion(conexion);
    }

    public void procesarExcepcion(SQLException ex) {
        if (gestorConexionActual == null) {
            return; 
        }
        gestorConexionActual.procesarExcepcion(ex);
    }
    
    public boolean hayConexionRemota() {
        return conexionRemotaCorrecta;
    }

    public boolean comporbarConexionRemota() {

        if (!conexionRemotaCorrecta) {            
            if (gestorConexionActual == gestorConexionRemota && conmutacionAutomatica) {
                activarModoLocal();
            }
        } else {
            if (gestorConexionActual == gestorConexionLocal && conmutacionAutomatica) {
                activarModoRemoto();
            }
        }
        
        primeraComprobacion = false;
        return conexionRemotaCorrecta;
    }

    @Override
    public boolean esConexionLocal() {
        if (gestorConexionActual == null) {
            return false; 
        }
        return gestorConexionActual == gestorConexionLocal;
    }

    @Override
    public FechaHora getFechaHora() {
        if (gestorConexionActual == null) {
            return null; 
        }
        return gestorConexionActual.getFechaHora();
    }

    @Override
    public FormateadorSql getFormateadorSql() {
        if (gestorConexionActual == null) {
            return null; 
        }
        return gestorConexionActual.getFormateadorSql();
    }

    private void activarGestorConexiones(GestorConexionUnica gestorConexion) {
        if (gestorConexionActual != gestorConexion) {
            if (gestorConexionActual != null) {
                gestorConexionActual.cerrarConexion();
            }
            gestorConexionActual = gestorConexion;
            PlantillaSql.setFormateadorSql(getFormateadorSql());
        }
    }

    @Override
    public String getDriver() {
        if (gestorConexionActual == null) {
            return ""; 
        }
        return gestorConexionActual.getDriver();
    }
}
