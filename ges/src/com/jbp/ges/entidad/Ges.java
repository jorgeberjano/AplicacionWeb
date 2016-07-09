package com.jbp.ges.entidad;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jorge
 */
public class Ges {

    public final static int GES_ESTILO_MAXIMIZAR = 0x0001;
    public final static int GES_ESTILO_LETRA_GRANDE = 0x0002;
    public final static int GES_ESTILO_CONSULTAS_NO_MODALES = 0x0004;

    private int version;
    private String nombreBaseDatos;
    private int estilo;
    private List<ConsultaGes> listaConsultasPantalla = new ArrayList();
    private List<ConsultaGes> listaConsultasImpresora = new ArrayList();

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getNombreBaseDatos() {
        return nombreBaseDatos;
    }

    public void setNombreBaseDatos(String nombreBaseDatos) {
        this.nombreBaseDatos = nombreBaseDatos;
    }

    public int getEstilo() {
        return estilo;
    }

    public void setEstilo(int estilo) {
        this.estilo = estilo;
    }

    public List<ConsultaGes> getListaConsultasPantalla() {
        return listaConsultasPantalla;
    }

    public void setListaConsultasPantalla(List<ConsultaGes> listaConsultasPantalla) {
        this.listaConsultasPantalla = listaConsultasPantalla;
    }

    public List<ConsultaGes> getListaConsultasImpresora() {
        return listaConsultasImpresora;
    }

    public void setListaConsultasImpresora(List<ConsultaGes> listaConsultasImpresora) {
        this.listaConsultasImpresora = listaConsultasImpresora;
    }

}
