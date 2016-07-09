package com.jbp.aplicacionweb.dto;

import com.jbp.aplicacionweb.Global;
import com.jbp.aplicacionweb.dao.ClavePrimaria;
import com.jbp.aplicacionweb.dao.EntidadGes;
import com.jbp.aplicacionweb.dao.RepositorioGes;
import com.jbp.ges.entidad.CampoGes;
import com.jbp.ges.entidad.ConsultaGes;
import java.util.ArrayList;
import java.util.List;
import utiles.conversion.Conversion;

/**
 *
 * @author jberjano
 */
public class ServicioPaginacionGenerico implements IServicioPaginacion {

    private final RepositorioGes repositorio;
    private final boolean usarCache = true;
    private List<DtoGenerico> cacheDto;

    private final ConsultaGes consulta;

    public ServicioPaginacionGenerico(ConsultaGes consulta) {
        this.consulta = consulta;
        repositorio = new RepositorioGes(Global.getInstancia().getGestorConexion(), consulta);
    }

    public DtoGenerico getDto(String pk) {
        ClavePrimaria clavePrimaria = parsearClavePrimaria(pk);
        EntidadGes entidad = repositorio.getEntidad(clavePrimaria);
        return new DtoGenerico(entidad, consulta);
    }
    
    public List<DtoGenerico> getListaDto(FiltroDtoGenerico filtro, String campoOrden, boolean ordenDescendente) {

        List<DtoGenerico> lista = new ArrayList();
        List<EntidadGes> listaEntidades = repositorio.getListaFiltrada(filtro, campoOrden, ordenDescendente);
        if (listaEntidades == null) {
            return null;
        }
        for (EntidadGes entidad : listaEntidades) {
            lista.add(new DtoGenerico(entidad, consulta));
        }
        return lista;
    }
    
    public DtoGenerico crearDto() {
        DtoGenerico dto = new DtoGenerico();
        
        for (CampoGes campo : consulta.getListaCampos()) {
            String valorPorDefecto = campo.getValorPorDefecto();
            dto.set(campo.getNombre(), valorPorDefecto);
        }
        return dto;
    }
    
    public String guardarDto(String pk, DtoGenerico dto) {
        if (dto == null || consulta == null) {
            return null;
        }
        ClavePrimaria clavePrimaria = parsearClavePrimaria(pk);
        EntidadGes entidad = dto.getEntidad(consulta);
        entidad.setClavePrimaria(clavePrimaria);
        boolean ok = repositorio.guardar(entidad);
        if (ok) {
            limpiarCache();
        }        
        
        return entidad.getClavePrimaria().toString();
    }

    public boolean borrarDto(String pk) {
        ClavePrimaria clavePrimaria = parsearClavePrimaria(pk);
        boolean ok = repositorio.borrar(clavePrimaria);
        if (ok) {
            limpiarCache();
        }
        return ok;
    }

    public ClavePrimaria parsearClavePrimaria(String texto) {
        if (Conversion.isBlank(texto)) {
            return new ClavePrimaria(); 
        }
        ClavePrimaria clave = ClavePrimaria.crearDeCadena(texto, consulta);
        return clave;
    }

    public synchronized boolean actualizarPaginaConCache(DatosSesionTabla datosPagina) {

        if (cacheDto == null) {
            FiltroDtoGenerico filtro = datosPagina.getFiltro() instanceof FiltroDtoGenerico
                    ? (FiltroDtoGenerico) datosPagina.getFiltro() : null;
            List<EntidadGes> listaEntidades = repositorio.getListaFiltrada(
                    filtro, datosPagina.getCampoOrden(), datosPagina.isOrdenDescendente());
            if (listaEntidades == null) {
                return false;
            }
            cacheDto = new ArrayList();
            for (EntidadGes entidad : listaEntidades) {
                cacheDto.add(new DtoGenerico(entidad, consulta));
            }
        }
        datosPagina.setNumeroTotalElementos(cacheDto.size());

        List listaDto = getPaginaResultados(cacheDto, datosPagina.getElementosPorPagina(), datosPagina.getPaginaActual());
        datosPagina.setPaginaElementos(listaDto);
        return true;
    }

    public synchronized void limpiarCache() {
        cacheDto = null;
    }

    @Override
    public boolean actualizarPagina(DatosSesionTabla datosPagina) {

        if (usarCache) {
            return actualizarPaginaConCache(datosPagina);
        }

        FiltroDtoGenerico filtro = datosPagina.getFiltro() instanceof FiltroDtoGenerico
                ? (FiltroDtoGenerico) datosPagina.getFiltro() : null;

        int primerElemento = (datosPagina.getPaginaActual() - 1) * datosPagina.getElementosPorPagina();

        List<EntidadGes> lista = repositorio.getPagina(
                filtro, datosPagina.getCampoOrden(), datosPagina.isOrdenDescendente(),
                primerElemento, datosPagina.getElementosPorPagina());

        datosPagina.setNumeroTotalElementos(repositorio.getNumeroTotalEntidades());

        if (lista == null) {
            return false;
        }

        List listaDto = new ArrayList();
        for (EntidadGes p : lista) {
            listaDto.add(new DtoGenerico(p, consulta));
        }
        datosPagina.setPaginaElementos(listaDto);
        return true;
    }

    public static List<?> getPaginaResultados(List<?> resultados, int elementosPagina, int pagina) {
        if (resultados == null || resultados.size() == 0) {
            return null;
        }
        int posicionInicial = elementosPagina * (pagina - 1);
        int posicionFinal = elementosPagina * pagina;
        if (posicionInicial < 0) {
            posicionInicial = 0;
        }
        int total = resultados.size();
        if (posicionFinal > total) {
            posicionFinal = total;
        }

        List<Object> list = new ArrayList<>();
        for (int i = posicionInicial; i < posicionFinal; i++) {
            list.add(resultados.get(i));
        }

        return list;
    }

//    public ClavePrimaria parsearClavePrimaria(String cadena) {
//        return new ClavePrimaria(cadena);
//    }
    @Override
    public String getMensajeError() {
        return repositorio.getMensajeError();
    }

//    public Object getBeanEdicion(String pk) {
//        EntidadGes entidad = repositorio.getEntidad(parsearClavePrimaria(pk));
//        Object bean = crearBeanEdicion();
//        for (String nombreAtributo : entidad.getNombres()) {
//            TipoDao valor = entidad.get(nombreAtributo);
//            Reflexion.asignarValorAtributoSimple(bean, nombreAtributo, valor);
//        }
//        return bean;
//    }
//    public Object crearBeanEdicion() {
//
//        String nombreClase = Conversion.capitalizar(consulta.getIdConsulta());
//       
//        String codigoFuenteBean = construirCodigoFuente(nombreClase);
//
//        Class beanClass = Reflexion.crearClase(nombreClase, "d:/temp", codigoFuenteBean);
//        if (beanClass == null) {
//            return null;
//        }
//        try {
//            return beanClass.newInstance();
//        } catch (InstantiationException | IllegalAccessException ex) {
//            return null;
//        }
//    }
//    private String construirCodigoFuente(String nombreClase) {
//        
//        StringBuilder codigoFuenteBean = new StringBuilder();
//        codigoFuenteBean.append("public class " + nombreClase + " {\n");
//        for (CampoGes campo : consulta.getListaCampos()) {
//
//            String tipo = "String";//campo.getTipoDatoJava();
//            String nombreAtributo = campo.getNombre();
//            String nombreAtributoCapitalizado = Conversion.capitalizar(nombreAtributo);
//
//            codigoFuenteBean
//                    .append("   private " + tipo + " " + nombreAtributo + ";\n")
//                    .append("   public void set" + nombreAtributoCapitalizado + "(" + tipo + " " + nombreAtributo + ") {\n")
//                    .append("      this." + nombreAtributo + " = " + nombreAtributo + ";\n")
//                    .append("   }\n")
//                    .append("   public " + tipo + " get" + nombreAtributoCapitalizado + "() {\n")
//                    .append("      return this." + nombreAtributo + ";\n")
//                    .append("   }\n");
//
//        }
//        codigoFuenteBean.append("}");
//        return codigoFuenteBean.toString();
//    }


}
