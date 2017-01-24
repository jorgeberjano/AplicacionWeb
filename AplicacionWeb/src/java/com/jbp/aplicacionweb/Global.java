package com.jbp.aplicacionweb;

import com.jbp.ges.entidad.ConsultaGes;
import com.jbp.ges.entidad.Ges;
import com.jbp.ges.serializacion.SerializadorGes;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;
import utiles.sql.GestorConexiones;

/**
 *
 * @author jberjano
 */
public class Global {
    
    private PoolConexiones gestorConexiones;

    public static Global instancia;
    
    private Ges gestor;
    
    static {
        getInstancia();        
    }

    private Global() {
        
        Properties prop = new Properties();
        try {
            InputStream in = getClass().getResourceAsStream("/config.properties");
            prop.load(in);
            in.close();
        } catch (IOException ex) {      
            ex.printStackTrace();
        }
              
        String driverClass = prop.getProperty("connection.driver_class", "jstels.jdbc.xml.XMLDriver2");
        String url = prop.getProperty("connection.url", "jdbc:jstels:xml:c:/iid/xml/iidlocal.xml");
        String username = prop.getProperty("connection.username", "iidlocal");
        String password = prop.getProperty("connection.password", "shs");        
        gestorConexiones = new PoolConexiones(driverClass, url, username, password, true);
        
        try {
            gestorConexiones.inicializar();
        } catch (ClassNotFoundException ex) {
        }
        
        gestor = new Ges();        
        SerializadorGes serializador = new SerializadorGes();
        gestor = serializador.deserializarRecurso("/com/jbp/aplicacionweb/xml/Bp.ges.xml");
    }
    
    public List<ConsultaGes> getConsultas() {
        return gestor.getListaConsultasPantalla();
    }
    
//    public List<String> getNombresConsultas() {
//        List<String> nombres = new ArrayList();
//        List<ConsultaGes> consultas = gestor.getListaConsultasPantalla();
//        for (ConsultaGes consulta : consultas) {
//            nombres.add(consulta.getNombre());
//        }
//        return nombres;
//    }
    
    public ConsultaGes getConsultaPorNombre(String nombreConsulta) {
        List<ConsultaGes> consultas = gestor.getListaConsultasPantalla();
        for (ConsultaGes consulta : consultas) {
            if (nombreConsulta.equals(consulta.getNombre())) {
                return consulta;
            }
        }
        return null;
    }
    
    public ConsultaGes getConsultaPorId(String idConsulta ){
        List<ConsultaGes> consultas = gestor.getListaConsultasPantalla();
        for (ConsultaGes consulta : consultas) {
            if (idConsulta.equals(consulta.getIdConsulta())) {
                return consulta;
            }
        }
        return null;
    }
    

    public static Global getInstancia() {
        if (instancia == null) {
            instancia = new Global();
        }
        return instancia;
    }

    public GestorConexiones getGestorConexiones() {
        return gestorConexiones;
    }
}
