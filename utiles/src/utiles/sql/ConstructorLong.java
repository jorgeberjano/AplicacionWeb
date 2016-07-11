package utiles.sql;

import java.sql.SQLException;

/**
 *
 * @author jfjara
 */
public class ConstructorLong extends ConstructorEntidad<Long> {
    
    private String campo;

    public ConstructorLong() {
    }
    
    public ConstructorLong(String campo) {
        this.campo = campo;
    }
        
    @Override
    protected Long construirEntidad(AdaptadorResultSet rs) throws SQLException {        
        if (campo != null) {
            return rs.getLong(campo);
        } else {
            return rs.getResulset().getLong(1);
        }
    }
    
}
