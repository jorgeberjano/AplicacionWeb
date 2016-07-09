package utiles.web.dto;

import java.util.List;

/**
 *
 * @author jberjano
 */
public class Seleccion {

    private List<String> opciones;
    private Integer indiceSeleccionado;

    public List<String> getOpciones() {
        return opciones;
    }

    public void setOpciones(List<String> opciones) {
        this.opciones = opciones;
    }

    public int getNumeroOpciones() {
        return opciones == null ? 0 : opciones.size();
    }

    public String getValorSeleccionado() {
        return esIndiceValido(indiceSeleccionado) ? opciones.get(indiceSeleccionado) : null;
    }

    public void setValorSeleccionado(String valorSeleccionado) {        
        for (int i = 0, n = getNumeroOpciones(); i < n; i++) {
            if (valorSeleccionado.equals(opciones.get(i))) {
                indiceSeleccionado = i;
            }
        }
        indiceSeleccionado = 0;
    }

    public Integer getIndiceSeleccionado() {
        return indiceSeleccionado;
    }

    public void setIndiceSeleccionado(Integer indiceSeleccionado) {
        if (indiceSeleccionado == null || indiceSeleccionado < 0 || indiceSeleccionado > getNumeroOpciones()) {
            this.indiceSeleccionado = null;
        } else {
            this.indiceSeleccionado = indiceSeleccionado;
        }
    }

    private boolean esIndiceValido(Integer indice) {
        return indice != null && indice >= 0 && indice < getNumeroOpciones();
    }

    public void setOpcionSeleccionada(Integer opcion) {
        if (!esIndiceValido(opcion)) {
            opcion = 0;
        }
    }

    public void setOpcionSeleccionada(Boolean opcion) {
        setOpcionSeleccionada(opcion == null ? 0 : (opcion ? 1 : 2));
    }
}
