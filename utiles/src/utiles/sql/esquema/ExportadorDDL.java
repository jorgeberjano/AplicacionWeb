package utiles.sql.esquema;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import utiles.sql.compatibilidad.FormateadorSql;
import utiles.xml.CreadorXml;

/**
 *
 * @author jberjano
 */
public class ExportadorDDL {
    
    private FormateadorSql formateadorSql;

    public ExportadorDDL(FormateadorSql formateadorSql) {
        this.formateadorSql = formateadorSql;
    }
    
    public void exportarXML(EsquemaBd esquemaBd, String rutaArchivo) throws IOException {
        exportar(getXml(esquemaBd), rutaArchivo);
    }
    
    public void exportarSQL(EsquemaBd esquemaBd, String rutaArchivo) throws IOException {
        exportar(getSqlCreacion(esquemaBd), rutaArchivo);
    }
    
    protected void exportar(String contenido, String rutaArchivo) throws IOException {
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(new FileWriter(rutaArchivo));
            writer.write(contenido);
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }
    
    public String getSqlCreacion(EsquemaBd esquemaBd) {
        
        StringBuilder builderTablas = new StringBuilder();
        
        // TODO: exportar las claves ajenas
        //StringBuilder builderClavesAjenas = new StringBuilder();
        
        List<TablaBd> tablas = esquemaBd.getTablas();
        for (TablaBd tabla : tablas) {  
            
            String clavePrimaria = null;
            builderTablas.append("CREATE TABLE ");
            builderTablas.append(tabla.getNombre());
            builderTablas.append("\n(");
            List<CampoBd> campos = tabla.getCampos();
            boolean primero = true;
            for (CampoBd campo : campos) {
                if (!primero) {
                    builderTablas.append(",");
                } else {
                    primero = false;
                }
                builderTablas.append("\n\t");
                builderTablas.append(campo.getNombreSql());
                builderTablas.append(" ");
                builderTablas.append(formateadorSql.getNombreTipoSql(campo));                
                if (campo.isNoNulo()) {
                    builderTablas.append(" NOT NULL");
                }
                // TODO: permitir claves primarias multiples
                if (campo.isClavePrimaria()) {
                    clavePrimaria = "PRIMARY KEY (" + campo.getNombreSql() + ")";
                }               
            }
            if (clavePrimaria != null) {
                builderTablas.append(",\n\t");
                builderTablas.append(clavePrimaria);
            }
            
            builderTablas.append("\n);\n\n");
        }
        
        return builderTablas.toString();
    }
    
    public String getSqlBorrado(EsquemaBd esquemaBd) {
        StringBuilder builderTablas = new StringBuilder();
        
        List<TablaBd> tablas = esquemaBd.getTablas();
        for (TablaBd tabla : tablas) {            
            builderTablas.append("DROP TABLE ");
            builderTablas.append(tabla.getNombre());
            builderTablas.append(";\n");
        }        
        return builderTablas.toString();
    }
    
    
    public String getXml(EsquemaBd esquemaBd) {
        CreadorXml creadorXml = new CreadorXml();
        
        creadorXml.abrirTag("schema");
        
        List<TablaBd> tablas = esquemaBd.getTablas();
        for (TablaBd tabla : tablas) {
            creadorXml.abrirTag("table");
            creadorXml.atributo("name", tabla.getNombre());            
            List<CampoBd> campos = tabla.getCampos();
            for (CampoBd campo : campos) {
                creadorXml.abrirTag("column");
                creadorXml.atributo("name", campo.getNombreSql());
                creadorXml.atributo("type", campo.getTipoDato().toString());
                creadorXml.cerrarTag();
            }
            creadorXml.cerrarTag();
                
        }
        creadorXml.cerrarTag();        
        return creadorXml.toString();
    }
}
