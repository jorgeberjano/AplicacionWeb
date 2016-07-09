package utiles.sql.compatibilidad;

import java.util.HashSet;
import java.util.Set;
import utiles.sql.esquema.CampoBd;
import utiles.tiempo.Fecha;
import utiles.tiempo.FechaHora;

/**
 *
 * @author jberjano
 */
public class FormateadorOracle implements FormateadorSql {

    @Override
    public String getFechaSql(Fecha fecha) {
        if (fecha == null) {
            return "null";
        }
        return "TO_DATE('" + fecha.toString() + "', 'dd/MM/yyyy')";
    }

    @Override
    public String getFechaHoraSql(FechaHora fechaHora) {
        if (fechaHora == null) {
            return "null";
        }
        return "TO_DATE('" + fechaHora.toString() + "', 'dd/MM/yyyy hh24:mi:ss')";
    }

    @Override
    public String getBooleanSql(Boolean valor) {
        return valor ? "1" : "0";
    }

    @Override
    public String getNombreTipoSql(CampoBd campo) {
        switch (campo.getTipoDato()) {
            case CADENA:
                return "VARCHAR2(" + campo.getTamano() + ")";
            case ENTERO:
                return "NUMBER(" + campo.getTamano() + ")";
            case REAL:
                return "DECIMAL(" + campo.getTamano() + ", " + campo.getDecimales() + ")";
            case BOOLEANO:
                return "NUMBER(1)";
            case FECHA:
                return "DATE";
            case FECHA_HORA:
                return "TIMESTAMP";
            case BYTES:
                return "BLOB";
            default:
                return "";
        }
    }

    @Override
    public String getSelectConsultaFechaHora() {
        return "select SYSDATE from DUAL";
    }
    
    @Override
    public String getFucionNVL(String valor, String valorSiEsNulo) {
        return "NVL(" + valor + ", " + valorSiEsNulo + ")";
    }

    @Override
    public String getFechaHoraActual() {
        return "SYSDATE";
    }
    
    @Override
    public Set<Integer> getCodigosErrorDesconexion() {
        Set<Integer> codigos = new HashSet<Integer>();
        codigos.add(17002);
        codigos.add(17008);
        return codigos;
    }

}
