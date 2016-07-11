package utiles.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Ejecutor de sentecias select
 * @author jberjano
 */
public class EjecutorSentenciaSelect {
    private final GestorConexiones gestorConexiones;
    private int numeroTotalEntidades; // Solo se usa al obteber páginas de entidades 

                
    public EjecutorSentenciaSelect(GestorConexiones gestorConexiones) {
        this.gestorConexiones = gestorConexiones;
    }    
    
    public <T> T obtenerEntidad(String sentencia, ConstructorEntidad<T> constructor) throws SQLException {
                
        Connection conexion = gestorConexiones.obtenerConexion();
        if (conexion == null) {
            return null;
        }
        
        T entidad = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;        
        try {           
            statement = conexion.prepareStatement(sentencia);
            resultSet = statement.executeQuery();
            entidad = constructor.obtenerEntidad(resultSet);
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
            gestorConexiones.liberarConexion(conexion);
        }
        
        return entidad;
    }
    
    public <T> List<T> obtenerListaEntidades(String sentencia, ConstructorEntidad<T> constructor) throws SQLException {
        List<T> lista = null;
        
        Connection conexion = gestorConexiones.obtenerConexion();
        if (conexion == null) {
            return lista;
        }
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {           
            statement = conexion.prepareStatement(sentencia);
            resultSet = statement.executeQuery();
            lista = constructor.obtenerListaEntidades(resultSet);
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
            gestorConexiones.liberarConexion(conexion);
        }
        
        return lista;
    }
    
    public <T> List<T> obtenerPaginaEntidades(String sentencia, ConstructorEntidad<T> constructor,
            int indicePrimerElemento, int numeroElementos) throws SQLException {
        List<T> lista = null;
        
        Connection conexion = gestorConexiones.obtenerConexion();
        if (conexion == null) {
            return lista;
        }
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {           
            statement = conexion.prepareStatement(sentencia, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            resultSet = statement.executeQuery();
            lista = constructor.obtenerPaginaEntidades(resultSet, indicePrimerElemento, numeroElementos);
            
            resultSet.last();
            numeroTotalEntidades = resultSet.getRow();
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
            gestorConexiones.liberarConexion(conexion);
        }
        
        return lista;
    }

    public int getNumeroTotalEntidades() {
        return numeroTotalEntidades;
    }
    
    
}
