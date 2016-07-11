package utiles.sql.esquema;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jberjano
 */
public class EsquemaBd {
    
    private List<TablaBd> tablas = new ArrayList<TablaBd>();

    public EsquemaBd() {
    }    

    public List<TablaBd> getTablas() {
        return tablas;
    }

    public void setTablas(List<TablaBd> tablas) {
        this.tablas = tablas;
    }
    
    public void agregarTabla(TablaBd tabla) {        
        this.tablas.add(tabla);
    }
    
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (TablaBd tabla : tablas) {
            builder.append(tabla.toString());
            builder.append("\n\n");
        }
        return builder.toString();
    }
}
