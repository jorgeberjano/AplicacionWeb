package utiles.sql;

/**
 *
 * @author jberjano
 */
public class SecuenciaOracle extends Secuencia {
    
    private final String nombreSecuencia;

    public SecuenciaOracle(String nombreSecuencia) {
        this.nombreSecuencia = nombreSecuencia;
    }
    
    @Override
    public String getSql(String nombreTabla, String nombreCampo) {        
        return "SELECT " + nombreSecuencia + ".nextval from DUAL";
    }
}
