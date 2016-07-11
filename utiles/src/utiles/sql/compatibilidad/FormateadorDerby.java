package utiles.sql.compatibilidad;

import java.util.Set;
import utiles.sql.esquema.CampoBd;
import static utiles.sql.TipoDato.BOOLEANO;
import static utiles.sql.TipoDato.BYTES;
import static utiles.sql.TipoDato.CADENA;
import static utiles.sql.TipoDato.ENTERO;
import static utiles.sql.TipoDato.FECHA;
import static utiles.sql.TipoDato.FECHA_HORA;
import static utiles.sql.TipoDato.REAL;
import utiles.tiempo.Fecha;
import utiles.tiempo.FechaHora;

/**
 *
 * @author jberjano
 */
public class FormateadorDerby implements FormateadorSql {

    @Override
    public String getFechaSql(Fecha fecha) {
        return "DATE('" + fecha.formatear("yyyy-MM-dd") + "')";
    }

    @Override
    public String getFechaHoraSql(FechaHora fechaHora) {
        return "DATE('" + fechaHora.formatear("yyyy-MM-dd-HH.mm.ss") + "')";
    }
    
    @Override
    public String getBooleanSql(Boolean valor) {
        return valor ? "1" : "0";
    }

    @Override
    public String getNombreTipoSql(CampoBd campo) {
        switch (campo.getTipoDato()) {
            case CADENA:
                return "VARCHAR(" + campo.getTamano() + ")";
            case ENTERO:
                return "BIGINT";
            case REAL:
                return "DOUBLE";
            case BOOLEANO:
                return "SMALLINT";
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
        return "select CURRENT_TIMESTAMP from SYSIBM.SYSDUMMY1";
    }

    @Override
    public String getFucionNVL(String valor, String valorSiEsNulo) {
        return "case when " + valor + " is null then " + valorSiEsNulo + " else " + valor + " end";
    }

    @Override
    public String getFechaHoraActual() {
        return "CURRENT_TIMESTAMP";
    }

    @Override
    public Set<Integer> getCodigosErrorDesconexion() {
        return null;
    }
}
