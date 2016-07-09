package utiles.sql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase base para los objetos encargados de contruir entidades a parir de 
 * resultados de consultas.
 * @author jberjano
 */
public abstract class ConstructorEntidad<T> {
    
    protected abstract T construirEntidad(AdaptadorResultSet rs) throws SQLException;    
    
    public T obtenerEntidad(AdaptadorResultSet rs) throws SQLException {        
        return construirEntidad(rs);
    }
    
    public T obtenerEntidadOpcional(AdaptadorResultSet rs) {
        try {            
            return construirEntidad(rs);            
        } catch (SQLException e) {
            return null;
        }
    }
    
    public T obtenerEntidad(AdaptadorResultSet rs, int numeroOcurrencia) throws SQLException {
        rs.setNumeroOcurrencia(numeroOcurrencia);
        T entidad = construirEntidad(rs);
        rs.setNumeroOcurrencia(1);
        return entidad;
    }
    
    public T obtenerEntidadOpcional(AdaptadorResultSet rs, int numeroOcurrencia) {
        rs.setNumeroOcurrencia(numeroOcurrencia);
        T entidad = null;
        try { 
            entidad = construirEntidad(rs);
        } catch (SQLException e) {           
        }
        rs.setNumeroOcurrencia(1);
        return entidad;
    }
    
    public T obtenerEntidad(ResultSet rs) throws SQLException {
        if (!rs.next()) {
            return null;
        }
        return construirEntidad(new AdaptadorResultSet(rs));
    }
    
    public List<T> obtenerListaEntidades(ResultSet rs) throws SQLException {
        List<T> resultado = new ArrayList();
        T entidad;
        do {
            entidad = obtenerEntidad(rs);
            if (entidad != null) {
                resultado.add(entidad);
            }
        } while (entidad != null);
        return resultado;
    }
    
    public List<T> obtenerPaginaEntidades(ResultSet rs, int indicePrimerElemento, int numeroElementos) throws SQLException {
        List<T> resultado = new ArrayList();
        T entidad;
        if (indicePrimerElemento > 0) {
            rs.absolute(indicePrimerElemento);
        }
        int i = 0;
        do {
            entidad = obtenerEntidad(rs);
            if (entidad != null) {
                resultado.add(entidad);
            }
            if (++i >= numeroElementos) {
                break;
            }
        } while (entidad != null);
        return resultado;
    }
}
