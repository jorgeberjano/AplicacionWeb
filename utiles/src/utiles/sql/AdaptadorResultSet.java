package utiles.sql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import utiles.tiempo.Fecha;
import utiles.tiempo.FechaHora;

/**
 * Adaptador para un resulset con funciones para acceder a los valores de los
 * campos pudiendo devolver valores nulos.
 *
 * @author jberjano
 */
public class AdaptadorResultSet {

    private final ResultSet resultSet;
    private final MetadatosConsulta metadatos;
    private int numeroOcurrencia = 1;

    public AdaptadorResultSet(ResultSet resultSet) {
        this.resultSet = resultSet;
        metadatos = new MetadatosConsulta(resultSet);
    }

    /**
     * Asigna el numero de ocurrencia de los campos cuyos valores se van a
     * recuperar. Sirve para poder recuperar valores de campos que tienen el
     * mismo nombre debido a que hay una tabla relacionada doblemente con otra.
     *
     * @param numeroOcurrencia
     */
    public void setNumeroOcurrencia(int numeroOcurrencia) {
        this.numeroOcurrencia = numeroOcurrencia;
    }
    
    public Object getObject(String campo) throws SQLException {
        Object valor;
        if (numeroOcurrencia > 1) {
            int indice = metadatos.getIndice(campo, numeroOcurrencia);
            valor = resultSet.getObject(indice);
        } else {
            valor = resultSet.getObject(campo);
        }
        return resultSet.wasNull() ? null : valor;
    }
    
    public Object getObjectOpcional(String campo) {
        try {
            return getObject(campo);
        } catch (Exception e) {
            return null;
        }
    }

    public Long getLong(String campo) throws SQLException {
        Long valor;
        if (numeroOcurrencia > 1) {
            int indice = metadatos.getIndice(campo, numeroOcurrencia);
            valor = resultSet.getLong(indice);
        } else {
            valor = resultSet.getLong(campo);
        }
        return resultSet.wasNull() ? null : valor;
    }

    public Long getLongOpcional(String campo) {
        try {
            return getLong(campo);
        } catch (Exception e) {
            return null;
        }
    }

    public Integer getInt(String campo) throws SQLException {
        return getInteger(campo);
    }

    public Integer getInteger(String campo) throws SQLException {
        Integer valor;
        if (numeroOcurrencia > 1) {
            int indice = metadatos.getIndice(campo, numeroOcurrencia);
            valor = resultSet.getInt(indice);
        } else {
            valor = resultSet.getInt(campo);
        }
        return resultSet.wasNull() ? null : valor;
    }

    public Integer getIntegerOpcional(String campo) {
        try {
            return getInt(campo);
        } catch (Exception e) {
            return null;
        }
    }

    public Float getFloat(String campo) throws SQLException {
        Float valor;
        if (numeroOcurrencia > 1) {
            int indice = metadatos.getIndice(campo, numeroOcurrencia);
            valor = resultSet.getFloat(indice);
        } else {
            valor = resultSet.getFloat(campo);
        }
        return resultSet.wasNull() ? null : valor;
    }

    public Float getFloatOpcional(String campo) {
        try {
            return getFloat(campo);
        } catch (Exception e) {
            return null;
        }
    }

    public Double getDouble(String campo) throws SQLException {
        Double valor;
        if (numeroOcurrencia > 1) {
            int indice = metadatos.getIndice(campo, numeroOcurrencia);
            valor = resultSet.getDouble(indice);
        } else {
            valor = resultSet.getDouble(campo);
        }
        return resultSet.wasNull() ? null : valor;
    }

    public Double getDoubleOpcional(String campo) {
        try {
            return getDouble(campo);
        } catch (Exception e) {
            return null;
        }
    }

    public String getString(String campo) throws SQLException {
        String valor;
        if (numeroOcurrencia > 1) {
            int indice = metadatos.getIndice(campo, numeroOcurrencia);
            valor = resultSet.getString(indice);
        } else {
            valor = resultSet.getString(campo);
        }
        return resultSet.wasNull() ? null : valor;
    }

    public String getStringOpcional(String campo) {
        try {
            return getString(campo);
        } catch (Exception e) {
            return null;
        }
    }

    public Fecha getFecha(String campo) throws SQLException {
        Fecha valor;
        if (numeroOcurrencia > 1) {
            int indice = metadatos.getIndice(campo, numeroOcurrencia);
            valor = new Fecha(resultSet.getDate(indice));
        } else {
            valor = new Fecha(resultSet.getDate(campo));
        }
        return resultSet.wasNull() ? null : valor;
    }

    public Fecha getFechaOpcional(String campo) {
        try {
            return getFecha(campo);
        } catch (Exception e) {
            return null;
        }
    }

    public FechaHora getFechaHora(String campo) throws SQLException {
        FechaHora valor;
        if (numeroOcurrencia > 1) {
            int indice = metadatos.getIndice(campo, numeroOcurrencia);
            valor = new FechaHora(resultSet.getTimestamp(indice));
        } else {
            valor = new FechaHora(resultSet.getTimestamp(campo));
        }
        return resultSet.wasNull() ? null : valor;
    }

    public FechaHora getFechaHoraOpcional(String campo) {
        try {
            return getFechaHora(campo);
        } catch (Exception e) {
            return null;
        }
    }

    public Boolean getBoolean(String campo) throws SQLException {
        Boolean valor;        
        if (numeroOcurrencia > 1) {
            int indice = metadatos.getIndice(campo, numeroOcurrencia);
            valor = resultSet.getBoolean(indice);
        } else {
            valor = resultSet.getBoolean(campo);
        }
        return resultSet.wasNull() ? null : valor;
    }

    public Boolean getBooleanOpcional(String campo) {
        try {
            return getBoolean(campo);
        } catch (Exception e) {
            return null;
        }
    }

    public byte[] getBytes(String campo) throws SQLException {
        byte[] valor;
        
        if (numeroOcurrencia > 1) {
            int indice = metadatos.getIndice(campo, numeroOcurrencia);
            valor = resultSet.getBytes(indice);
        } else {
            valor = resultSet.getBytes(campo);
        }
        return resultSet.wasNull() ? null : valor;
    }

    public byte[] getBytesOpcional(String campo) {
        try {
            return getBytes(campo);
        } catch (Exception e) {
            return null;
        }
    }

    public Object get(Campo campo) throws SQLException {
        
        String nombreSqlCampo = campo.getNombreSql();
        switch (campo.getTipoDato()) {
            case CADENA:
                return getString(nombreSqlCampo);
            case ENTERO:
                return getInteger(nombreSqlCampo);
            case REAL:
                return getDouble(nombreSqlCampo);
            case BOOLEANO:
                return getBoolean(nombreSqlCampo);
            case FECHA:
                return getFecha(nombreSqlCampo);
            case FECHA_HORA:
                return getFechaHora(nombreSqlCampo);
            case BYTES:
                return getBytes(nombreSqlCampo);
            default:
                return null;
        }
    }

    public ResultSet getResulset() {
        return resultSet;
    }
}
