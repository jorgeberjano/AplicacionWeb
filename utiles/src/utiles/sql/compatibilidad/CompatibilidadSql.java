package utiles.sql.compatibilidad;

/**
 *
 * @author jberjano
 */
public class CompatibilidadSql {
    
    private static FormateadorOracle formateadorOracle;
    private static FormateadorH2 formateadorH2;
    private static FormateadorDerby formateadorDerby;
    private static FormateadorXml formateadorXml;

    public static FormateadorSql getFormateador(String driver) {
        if (driver == null) {
            return null;
        }
        driver = driver.trim();
        if (driver.startsWith("oracle.jdbc")) {
            return getFormateadorOracle();
        } else if (driver.startsWith("org.h2")) {
            return getFormateadorH2();
        } else if (driver.startsWith("org.apache.derby.jdbc")) {
            return getFormateadorDerby();
        } else if (driver.startsWith("jstels.jdbc.xml")) {
            return getFormateadorXml();
        }
        return getFormateadorPorDefecto();
    }
    
    private static FormateadorSql getFormateadorPorDefecto() {
        return getFormateadorOracle();
    }
    
    public static FormateadorOracle getFormateadorOracle() {
        if (formateadorOracle == null) {
            formateadorOracle = new FormateadorOracle();
        }
        return formateadorOracle;
    }
    
    public static FormateadorH2 getFormateadorH2() {
        if (formateadorH2 == null) {
            formateadorH2 = new FormateadorH2();
        }
        return formateadorH2;
    }
    
    public static FormateadorDerby getFormateadorDerby() {
        if (formateadorDerby == null) {
            formateadorDerby = new FormateadorDerby();
        }
        return formateadorDerby;
    }
    
    public static FormateadorXml getFormateadorXml() {
        if (formateadorXml == null) {
            formateadorXml = new FormateadorXml();
        }
        return formateadorXml;
    }
}

