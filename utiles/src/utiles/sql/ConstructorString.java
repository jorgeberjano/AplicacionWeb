package utiles.sql;

import java.sql.SQLException;

/**
 *
 * @author jfjara
 */
public class ConstructorString extends ConstructorEntidad<String> {
    
    private String campo;

    public ConstructorString(String campo) {
        this.campo = campo;
    }
        
    @Override
    protected String construirEntidad(AdaptadorResultSet rs) throws SQLException {        
        return rs.getString(campo);
    }
    
}
