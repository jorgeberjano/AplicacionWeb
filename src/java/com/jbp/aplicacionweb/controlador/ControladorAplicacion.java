package com.jbp.aplicacionweb.controlador;

import com.jbp.aplicacionweb.Global;
import com.jbp.ges.entidad.ConsultaGes;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Jorge
 */
@Controller
@RequestMapping("/")
public class ControladorAplicacion {
    
    @RequestMapping(method=RequestMethod.GET)
    public String inicio(ModelMap model) {
        
        List<ConsultaGes> listaConsultas = Global.getInstancia().getConsultas();
        model.addAttribute("consultas", listaConsultas);
        
        return "aplicacion";
    }
    
//    @RequestMapping(value="menu", method = RequestMethod.GET)
//    public ModelAndView menu(ModelMap model) {
//        model.addAttribute("atributo", "Hello World Again, from Spring 4 MVC");
//        return new ModelAndView("aplicacion", model);
//    }
    
    @RequestMapping(value="mostrar/{nombre}", method = RequestMethod.GET)
    public ModelAndView mostrar(ModelMap model, @PathVariable(value="nombre") final String nombre) {
        model.addAttribute("atributo", nombre);
        return new ModelAndView("aplicacion", model);
    }
    
    
    
}
