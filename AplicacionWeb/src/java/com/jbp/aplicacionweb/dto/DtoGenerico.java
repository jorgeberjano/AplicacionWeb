package com.jbp.aplicacionweb.dto;

import com.jbp.aplicacionweb.dao.EntidadGes;
import com.jbp.ges.entidad.CampoGes;
import com.jbp.ges.entidad.ConsultaGes;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * DTO generico
 *
 * @author jberjano
 */
public final class DtoGenerico implements Serializable {

    private MapaDto mapa = new MapaDto();   

    public DtoGenerico() {
    }

    public DtoGenerico(EntidadGes entidad, ConsultaGes consulta) {
        setEntidad(entidad, consulta);
    }

    public MapaDto getMapa() {
        return mapa;
    }

    public void setMapa(MapaDto mapa) {
        this.mapa = mapa;
    }

    public void set(String nombreCampo, String valor) {
        
        mapa.put(nombreCampo, new ValorDto(valor));
    }

    
    public String get(String nombreCampo) {
        return mapa.get(nombreCampo).getValor();
    }

    public EntidadGes getEntidad(ConsultaGes consulta) {
        EntidadGes entidad = new EntidadGes();
        
        for (CampoGes campo : consulta.getListaCampos()) {
            String nombreCampo = campo.getNombre();
            String valorTexto = get(nombreCampo);
            Object valor = campo.convertirValor(valorTexto);
            if (campo.isClave()) {
                entidad.getClavePrimaria().set(nombreCampo, valor);
            } 
            entidad.set(nombreCampo, valor);            
        }
        
        return entidad;
    }

    public void setEntidad(EntidadGes entidad, ConsultaGes consulta) {
        mapa = new MapaDto();
        if (entidad == null) {
            return;
        }
        for (CampoGes campo : consulta.getListaCampos()) {
            String nombreCampo = campo.getNombre();
//        for (String nombre : entidad.getNombres()) {
            ValorDto valorDto = new ValorDto();
            Object valor = entidad.get(nombreCampo);
            valorDto.setValor(campo.formatearValorParaEdicion(valor));
            valorDto.setOpciones(campo.getOpciones());
            mapa.put(nombreCampo, valorDto);
        }
    }

//    public List<String> getOpciones(CampoGes campo) {
//        List<String> opciones = new ArrayList<>();
//        if (campo.getFormato().startsWith("#")) {
//            String[] arrayOpciones = campo.getFormato().split("#");
//            opciones = Arrays.asList(arrayOpciones);
//        }
//        return opciones;
//    }
}
