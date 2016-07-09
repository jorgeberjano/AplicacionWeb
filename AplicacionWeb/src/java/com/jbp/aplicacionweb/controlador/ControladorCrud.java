package com.jbp.aplicacionweb.controlador;

import com.jbp.aplicacionweb.Global;
import com.jbp.aplicacionweb.dao.ClavePrimaria;
import com.jbp.aplicacionweb.dto.DatosSesionTabla;
import com.jbp.aplicacionweb.dto.DtoGenerico;
import com.jbp.aplicacionweb.dto.FiltroDtoGenerico;
import com.jbp.aplicacionweb.dto.ServicioPaginacionGenerico;
import com.jbp.ges.entidad.ConsultaGes;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import utiles.conversion.Conversion;

/**
 * Controlador para el mantenimiento de una tabla de entidades.
 *
 * @author jberjano
 */
@Controller
public class ControladorCrud {
        
    
    private DatosSesionTabla obtenerDatosSesion(HttpServletRequest request, ConsultaGes consulta) {
        
        DatosSesionTabla datosSesionTabla;
        
        Object atributoSesion = request.getSession().getAttribute(consulta.getIdConsulta());
         
        if (atributoSesion instanceof DatosSesionTabla) {
            datosSesionTabla = (DatosSesionTabla) atributoSesion;
        } else {
            datosSesionTabla = new DatosSesionTabla();
            datosSesionTabla.setServicioPaginacion(new ServicioPaginacionGenerico(consulta));
            datosSesionTabla.setFiltro(new FiltroDtoGenerico());
            request.getSession().setAttribute(consulta.getIdConsulta(), datosSesionTabla);  
        }
        
        return datosSesionTabla;
    }
    
    /**
     * Carga inicial de la tabla
     */
    @RequestMapping(value = "/tabla/{idConsulta}", method = {RequestMethod.GET, RequestMethod.POST})    
    public ModelAndView tabla(HttpServletRequest request,
            @PathVariable String idConsulta,
            ModelMap model) {
        
        ConsultaGes consulta = Global.getInstancia().getConsultaPorId(idConsulta);
        
        DatosSesionTabla datosSesion = obtenerDatosSesion(request, consulta);
        boolean ok = datosSesion.actualizar();
        if (!ok) {
            model.put("mensaje_error", datosSesion.getMensajeError());
        }
       
        model.addAttribute("consulta", consulta);
        model.addAttribute("paginaActual", datosSesion.getPaginaActual());
        model.addAttribute("numeroPaginas", datosSesion.getNumeroPaginas());
        model.addAttribute("listaElementos", datosSesion.getListaElementosPagina());
        model.addAttribute("filtro", datosSesion.getFiltro());

        return new ModelAndView("crud/contenedor", model);
    }
    
    @RequestMapping(value = "/seleccionar/{idConsulta}", method = {RequestMethod.GET, RequestMethod.POST}) 
    public ModelAndView seleccionarPortador(HttpServletRequest request,
            @PathVariable String idConsulta,
            ModelMap model) {
        
        ConsultaGes consulta = Global.getInstancia().getConsultaPorId(idConsulta);
        String campoSeleccion = request.getParameter("campoSeleccion");
               
        DatosSesionTabla datosSesion = obtenerDatosSesion(request, consulta);
        if (!datosSesion.actualizar()) {
            model.put("mensaje_error", datosSesion.getMensajeError());
        }
       
        model.addAttribute("consulta", consulta);
        model.addAttribute("paginaActual", datosSesion.getPaginaActual());
        model.addAttribute("numeroPaginas", datosSesion.getNumeroPaginas());
        model.addAttribute("listaElementos", datosSesion.getListaElementosPagina());
        model.addAttribute("filtro", datosSesion.getFiltro());
        model.addAttribute("campoSeleccion", campoSeleccion);

        return new ModelAndView("crud/contenedor", model);
    }

    @RequestMapping(value = "/filtrar/{idConsulta}", method = RequestMethod.POST)
    public ModelAndView filtrar(HttpServletRequest request,
            @PathVariable String idConsulta,
            @ModelAttribute("filtro") FiltroDtoGenerico filtro,
            @ModelAttribute("campoSeleccion") String campoSeleccion,
            BindingResult result,
            ModelMap model) {

        ConsultaGes consulta = Global.getInstancia().getConsultaPorId(idConsulta);
        DatosSesionTabla datosSesion = obtenerDatosSesion(request, consulta);       
        datosSesion.setFiltro(filtro);        
        if (!datosSesion.actualizar()) {
            model.put("mensaje_error", datosSesion.getMensajeError());
        }        
        
        model.addAttribute("consulta", consulta);
        model.addAttribute("paginaActual", datosSesion.getPaginaActual());
        model.addAttribute("numeroPaginas", datosSesion.getNumeroPaginas());
        model.addAttribute("listaElementos", datosSesion.getListaElementosPagina());
        model.addAttribute("filtro", datosSesion.getFiltro());
        model.addAttribute("campoSeleccion", campoSeleccion);

        return new ModelAndView("crud/tabla", model);
    }
    
    @RequestMapping(value = "/ordenar/{idConsulta}", method = RequestMethod.POST)
    public ModelAndView ordenar(HttpServletRequest request,
            @PathVariable String idConsulta,
            @ModelAttribute("campoOrden") String campoOrden,
            @ModelAttribute("campoSeleccion") String campoSeleccion,
            BindingResult result,
            ModelMap model) {

        ConsultaGes consulta = Global.getInstancia().getConsultaPorId(idConsulta);
        DatosSesionTabla datosSesion = obtenerDatosSesion(request, consulta);        
        datosSesion.setCampoOrden(campoOrden);
        if (!datosSesion.actualizar()) {
            model.put("mensaje_error", datosSesion.getMensajeError());
        }
        
        model.addAttribute("consulta", consulta);
        model.addAttribute("paginaActual", datosSesion.getPaginaActual());
        model.addAttribute("numeroPaginas", datosSesion.getNumeroPaginas());
        model.addAttribute("listaElementos", datosSesion.getListaElementosPagina());
        model.addAttribute("filtro", datosSesion.getFiltro());
        model.addAttribute("campoFiltro", campoSeleccion);

        return new ModelAndView("crud/tabla", model);
    }

    @RequestMapping(value = "/volverATabla/{idConsulta}", method = RequestMethod.GET)
    public ModelAndView volverATabla(HttpServletRequest request,
            @PathVariable String idConsulta,
            ModelMap modelMap) {

        return new ModelAndView("redirect:tabla/" + idConsulta, modelMap);
    }
    
    /**
     * Realiza la paginacion de la tabla
     */
    @RequestMapping(value = "/paginar/{idConsulta}", method = RequestMethod.GET)
    public ModelAndView paginar(HttpServletRequest request,
            @PathVariable String idConsulta,
            @RequestParam(value = "accion", required = true) String accion,
            ModelMap model) {

        ConsultaGes consulta = Global.getInstancia().getConsultaPorId(idConsulta);
        DatosSesionTabla datosSesion = obtenerDatosSesion(request, consulta);
        datosSesion.paginar(accion);
        if (datosSesion.actualizar()) {
            model.put("mensaje_error", datosSesion.getMensajeError());
        }

        model.addAttribute("consulta", consulta);
        model.addAttribute("paginaActual", datosSesion.getPaginaActual());
        model.addAttribute("numeroPaginas", datosSesion.getNumeroPaginas());
        model.addAttribute("listaElementos", datosSesion.getListaElementosPagina());
        model.addAttribute("filtro", datosSesion.getFiltro());

        return new ModelAndView("crud/tabla", model);
    }

    @RequestMapping(value = "/mostrar/{idConsulta}/{pk}", method = RequestMethod.GET)
    public ModelAndView mostrarPortador(HttpServletRequest request,
            @PathVariable String idConsulta,
            @PathVariable String pk,
            ModelMap model) {

        ConsultaGes consulta = Global.getInstancia().getConsultaPorId(idConsulta);
        ServicioPaginacionGenerico servicio = new ServicioPaginacionGenerico(consulta);
//        Object beanEdicion = servicio.getBeanEdicion(pk);
        DtoGenerico dto = servicio.getDto(pk);

        model.addAttribute("consulta", consulta);
        model.addAttribute("titulo", "Mostrar " + consulta.getNombreEnSingular());
        model.addAttribute("pk", pk);
        model.addAttribute("elemento", dto);
        model.addAttribute("modo", "mostrar");
        model.addAttribute("mensaje_error", "");

        return new ModelAndView("crud/editor", model);
    }
   
   @RequestMapping(value = "/modificar/{idConsulta}/{pk}", method = RequestMethod.GET)
    public ModelAndView modificar(HttpServletRequest request,
            @PathVariable String idConsulta,
            @PathVariable String pk,
            ModelMap model) {

        ConsultaGes consulta = Global.getInstancia().getConsultaPorId(idConsulta);
        ServicioPaginacionGenerico servicio = new ServicioPaginacionGenerico(consulta);
        //Object beanEdicion = servicio.getBeanEdicion(pk);
        DtoGenerico dto = servicio.getDto(pk);
        model.addAttribute("consulta", consulta);
        model.addAttribute("titulo", "Modificar " + consulta.getNombreEnSingular());
        model.addAttribute("pk", pk);
        model.addAttribute("elemento", dto);
        model.addAttribute("modo", "editar");
        model.addAttribute("mensaje_error", "");

        return new ModelAndView("crud/editor", model);
    }

    @RequestMapping(value = "/crear/{idConsulta}", method = RequestMethod.GET)
    public ModelAndView crear(HttpServletRequest request,
            @PathVariable String idConsulta,
            ModelMap model) {

        ConsultaGes consulta = Global.getInstancia().getConsultaPorId(idConsulta);
        ServicioPaginacionGenerico servicio = new ServicioPaginacionGenerico(consulta);
        DtoGenerico dto = servicio.crearDto();
        model.addAttribute("consulta", consulta);
        model.addAttribute("titulo", "Crear " + consulta.getNombreEnSingular());
        model.addAttribute("elemento", dto);
        model.addAttribute("modo", "editar");
        model.addAttribute("mensaje_error", "");

        return new ModelAndView("crud/editor", model);
    }
    
    @RequestMapping(value = "/guardar/{idConsulta}", method = RequestMethod.POST)
    public ModelAndView guardar(HttpServletRequest request,
            @PathVariable String idConsulta,
            @ModelAttribute("elemento") DtoGenerico dto,
            BindingResult result,
            ModelMap model) {
        
        ConsultaGes consulta = Global.getInstancia().getConsultaPorId(idConsulta);
             
//        if (result.hasErrors()) {
//            model.put("consulta", consulta);
//            model.addAttribute("beanEdicion", beanEdicion);
//            model.addAttribute("modo", "editar");
//            model.addAttribute("mensaje_error", "Se han producido errores");
//            return new ModelAndView("crud/formulario", model);
//        }

        ServicioPaginacionGenerico servicio = new ServicioPaginacionGenerico(consulta);
        String nuevaPk = servicio.guardarDto(null, dto);
                
        if (Conversion.isBlank(nuevaPk)) {
            model.addAttribute("mensaje_error", servicio.getMensajeError());
            model.addAttribute("url_retorno", "tabla/" + idConsulta);
            return new ModelAndView("crud/error", model);
        }
        return new ModelAndView("redirect:/mostrar/" + idConsulta + "/" + nuevaPk, model);
    }

    @RequestMapping(value = "/guardar/{idConsulta}/{pk}",
            method = RequestMethod.POST,
            produces = "text/plain;charset=UTF-8")
    public ModelAndView guardar(HttpServletRequest request,
            @PathVariable String idConsulta,
            @PathVariable String pk,
            @ModelAttribute("elemento") DtoGenerico dto,
            BindingResult result,
            ModelMap model) {
        
        ConsultaGes consulta = Global.getInstancia().getConsultaPorId(idConsulta);
             
//        if (result.hasErrors()) {
//            model.put("consulta", consulta);
//            model.addAttribute("beanEdicion", beanEdicion);
//            model.addAttribute("modo", "editar");
//            model.addAttribute("mensaje_error", "Se han producido errores");
//            return new ModelAndView("crud/formulario", model);
//        }

        ServicioPaginacionGenerico servicio = new ServicioPaginacionGenerico(consulta);
        String nuevaPk = servicio.guardarDto(pk, dto);
                
        if (nuevaPk == null || pk.isEmpty()) {
            model.addAttribute("mensaje_error", servicio.getMensajeError());
            if (Conversion.isBlank(nuevaPk)) {
                model.addAttribute("url_retorno", "tabla/" + idConsulta);
            } else {
                model.addAttribute("url_retorno", "mostrar/" + idConsulta + "/" + nuevaPk);                
            }
            return new ModelAndView("crud/error", model);            
        }
        return new ModelAndView("redirect:/mostrar/" + idConsulta + "/" + nuevaPk, model);
    }

    @RequestMapping(value = "/borrar/{idConsulta}/{pk}", method = RequestMethod.GET)
    public ModelAndView borrar(HttpServletRequest request,
            @PathVariable String idConsulta,
            @PathVariable String pk,
            ModelMap model) {

        ConsultaGes consulta = Global.getInstancia().getConsultaPorId(idConsulta);
        ServicioPaginacionGenerico servicio = new ServicioPaginacionGenerico(consulta);
        
        boolean ok = servicio.borrarDto(pk);
        if (!ok) {
            request.getSession().setAttribute("mensaje", servicio.getMensajeError()); 
            model.addAttribute("url_retorno", "mostrar/" + idConsulta + "/" + pk);
            return new ModelAndView("error", model);
        }
        return new ModelAndView("redirect:/tabla/" + consulta.getNombre(), model);

    }
    
}
