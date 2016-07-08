package utiles.web.dto;

import java.io.Serializable;

/**
 *
 * @author jberjano
 */
public class ItemBooleano implements Serializable {
    private Boolean valor;
    private String texto;

    public Boolean getValor() {
        return valor;
    }

    public void setValor(Boolean valor) {
        this.valor = valor;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }
}
