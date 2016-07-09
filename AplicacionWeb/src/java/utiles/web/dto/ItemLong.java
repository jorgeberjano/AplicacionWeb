package utiles.web.dto;

import java.io.Serializable;

/**
 *
 * @author jberjano
 */
public class ItemLong implements Serializable {
    private Long valor;
    private String texto;

    public Long getValor() {
        return valor;
    }

    public void setValor(Long valor) {
        this.valor = valor;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }
}
