package utiles.sql;

import utiles.sql.compatibilidad.FormateadorSql;

/**
 * Las clases que implementan esta interface sirven para definir como se asigna
 * el valor de una clave primaria numerica para que tome valores secuenciales.
 *
 * @author Jorge Berjano
 */
public abstract class Secuencia {
    protected Long valorGenerado;
    protected FormateadorSql formateadorSql;

    public void setFormateadorSql(FormateadorSql formateadorSql) {
        this.formateadorSql = formateadorSql;
    }
    
    public Long getValorGenerado() {
        return valorGenerado;
    }

    public void setValorGenerado(Long valorGenerado) {
        this.valorGenerado = valorGenerado;
    }
    
    
    public abstract String getSql(String nombreTabla, String nombreCampo);        
}
