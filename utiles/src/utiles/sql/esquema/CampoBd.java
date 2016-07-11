package utiles.sql.esquema;

import utiles.sql.TipoDato;
import utiles.sql.Campo;

/**
 *
 * @author jberjano
 */
public class CampoBd implements Campo {
    private String nombreSql;
    private TipoDato tipoDato;
    private String claveAjena;
    private Boolean clavePrimaria = false;
    private Boolean noNulo = false;
    private Integer tamano;
    private Integer decimales;

    public String getNombreSql() {
        return nombreSql;
    }

    public void setNombreSql(String nombreSql) {
        this.nombreSql = nombreSql;
    }

    public String getClaveAjena() {
        return claveAjena;
    }

    public void setClaveAjena(String claveAjena) {
        this.claveAjena = claveAjena;
    }

    public Boolean isClavePrimaria() {
        return clavePrimaria;
    }

    public void setClavePrimaria(Boolean clavePrimaria) {
        this.clavePrimaria = clavePrimaria;
    }

    public Boolean isNoNulo() {
        return noNulo;
    }

    public void setNoNulo(Boolean noNulo) {
        this.noNulo = noNulo;
    }

    public TipoDato getTipoDato() {
        return tipoDato;
    }

    public void setTipoDato(TipoDato tipoDato) {
        this.tipoDato = tipoDato;
    }

    public Integer getTamano() {
        return tamano;
    }

    public void setTamano(Integer tamano) {
        this.tamano = tamano;
    }

    public Integer getDecimales() {
        return decimales;
    }

    public void setDecimales(Integer decimales) {
        this.decimales = decimales;
    }

    @Override
    public String toString() {
        return nombreSql;
    }
}
