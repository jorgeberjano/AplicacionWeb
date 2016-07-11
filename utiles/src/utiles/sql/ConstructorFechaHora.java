package utiles.sql;

import java.sql.SQLException;
import utiles.tiempo.FechaHora;

/**
 *
 * @author jfjara
 */
public class ConstructorFechaHora extends ConstructorEntidad<FechaHora> {

    private String campo;

    public ConstructorFechaHora() {
    }

    public ConstructorFechaHora(String campo) {
        this.campo = campo;
    }

    @Override
    protected FechaHora construirEntidad(AdaptadorResultSet rs) throws SQLException {
        if (campo != null) {
            return rs.getFechaHora(campo);
        } else {
            return new FechaHora(rs.getResulset().getTimestamp(1));
        }
    }

}
