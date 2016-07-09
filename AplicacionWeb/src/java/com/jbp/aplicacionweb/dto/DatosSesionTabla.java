package com.jbp.aplicacionweb.dto;

import com.jbp.aplicacionweb.Constantes;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jberjano
 */
public class DatosSesionTabla implements Serializable {
    private int paginaActual;
    private int numeroPaginas;
    private Object filtro;
    private String campoOrden;
    private boolean ordenDescendente;
    
    private List listaElementosPagina;
    private int numeroTotalElementos;
    private String mensajeError;
    private IServicioPaginacion servicioPaginacion;    

    public DatosSesionTabla() {
    //    listaElementosPagina = new ArrayList();
        paginaActual = 1;
    }

    public IServicioPaginacion getServicioPaginacion() {
        return servicioPaginacion;
    }

    public void setServicioPaginacion(IServicioPaginacion servicioPaginacion) {
        this.servicioPaginacion = servicioPaginacion;
    }

    public int getPaginaActual() {
        return paginaActual;
    }

    public void setPaginaActual(int paginaActual) {
        this.paginaActual = paginaActual;
    }

    public int getNumeroPaginas() {
        return numeroPaginas;
    }

    public Object getFiltro() {
        return filtro;
    }

    public void setFiltro(Object filtro) {
        this.filtro = filtro;
        limpiarCache();
    }
    
    public void setCampoOrden(String campoOrden) {
        if (this.campoOrden != null && this.campoOrden.equals(campoOrden)) {
            ordenDescendente = !ordenDescendente;
        } else {
            ordenDescendente = false;
        }
        this.campoOrden = campoOrden;
         limpiarCache();
    }

    public String getCampoOrden() {
        return campoOrden;
    }

    public boolean isOrdenDescendente() {
        return ordenDescendente;
    }

    public int getElementosPorPagina() {
        return Constantes.NUMERO_ELEMENTOS_PAGINA_TABLA;        
    }
    
    public List getListaElementosPagina() {
        return listaElementosPagina;
    }

    public String getMensajeError() {
        return mensajeError;
    }

    public void setMensajeError(String mensajeError) {
        this.mensajeError = mensajeError;
    }
    
    public boolean actualizar() {
              
        servicioPaginacion.actualizarPagina(this);
                
        if (listaElementosPagina == null) {
            mensajeError = servicioPaginacion.getMensajeError();
            return false;
        }        
                
       
        return listaElementosPagina != null;
    }

    public void paginar(String accion) {
                
        numeroPaginas = numeroTotalElementos == 0 ? 0 : (int) Math.ceil((double) numeroTotalElementos / (double) getElementosPorPagina());
        
        switch (accion) {
            case Constantes.PAGINATE_NEXT:
                paginaActual++;
                break;
            case Constantes.PAGINATE_PREVIOUS:
                paginaActual--;
                break;
            case Constantes.PAGINATE_FIRST:
                paginaActual = 1;
                break;
            case Constantes.PAGINATE_LAST:
                paginaActual = numeroPaginas;
                break;
        }
        if (paginaActual <= 0) {
            paginaActual = 1;
        } else if (paginaActual > numeroPaginas) {
            paginaActual = numeroPaginas;
        }        
        actualizar();
        
    }

    public void setPaginaElementos(List listaElementos) {
        listaElementosPagina = listaElementos;
    }

    public void setNumeroTotalElementos(int numeroTotalElementos) {
        this.numeroTotalElementos = numeroTotalElementos;
        numeroPaginas = numeroTotalElementos == 0 ? 0 : (int) Math.ceil((double) numeroTotalElementos / (double) getElementosPorPagina());
    }

    public void limpiarCache() {
        servicioPaginacion.limpiarCache();
    }
}
