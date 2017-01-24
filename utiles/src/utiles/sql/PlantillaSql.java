package utiles.sql;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import utiles.sql.compatibilidad.FormateadorOracle;
import utiles.sql.compatibilidad.FormateadorSql;
import utiles.tiempo.Fecha;
import utiles.tiempo.FechaHora;

/**
 *
 * @author jberjano
 */
public class PlantillaSql {

    private static FormateadorSql formateadorSql = new FormateadorOracle();

    private StringBuilder buffer;
    private final Map<String, Object> mapaParametros = new HashMap<String, Object>();

    public PlantillaSql(String cadena) {
        buffer = new StringBuilder(cadena);
    }

    public PlantillaSql(URL url) throws IOException {
        this(url.openStream());
    }

    public static FormateadorSql getFormateadorSql() {
        return formateadorSql;
    }

    public static void setFormateadorSql(FormateadorSql formateadorSql) {
        PlantillaSql.formateadorSql = formateadorSql;
    }

    public PlantillaSql(InputStream inputStream) throws IOException {
        buffer = new StringBuilder();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line = null;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
                buffer.append("\n");
            }
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
            if (reader != null) {
                reader.close();
            }
        }
    }

    public void definirParametro(String parametro, Object valor) {
        mapaParametros.put(parametro, valor);
    }

    public Object obtenerParametro(String parametro) {
        return mapaParametros.get(parametro);
    }

    public String getResultado() {
        
        StringBuilder bufferActual = buffer;
        
        // Se ordenan los parametros de mayor a menor para que los parametros 
        // que sean subcadenas de otros no sustituyan simbolos parcialmente
        List<String> listaParametros = new ArrayList();
        listaParametros.addAll(mapaParametros.keySet());
        Collections.sort(listaParametros, new Comparator<String>(){
            @Override
            public int compare(String o1, String o2) {                
                return o2.length() - o1.length();
            }
        });
                
        // Se sustituyen los parametros
        for (String parametro : listaParametros) {
            Object valor = obtenerParametro(parametro);
            String textoActual = reemplazarSimbolo(bufferActual, parametro, valor);
            bufferActual = new StringBuilder(textoActual);
        }

        StringBuilder bufferResultado = new StringBuilder();
        // Se procesan las directivas #ifdef #else y #endif
        Stack<Boolean> pilaCondiciones = new Stack<Boolean>();
        boolean condicionActual = true;
        BufferedReader reader = new BufferedReader(new StringReader(bufferActual.toString()));
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("#ifdef")) {
                    String parametro = line.substring(6).trim();
                    Object valor = obtenerParametro(parametro);
                    boolean condicion = valor != null;
                    pilaCondiciones.push(condicionActual);
                    condicionActual = condicion;
                    continue;
                } else if (line.startsWith("#else")) {
                    condicionActual = !condicionActual;
                    continue;
                } else if (line.startsWith("#endif")) {
                    condicionActual = pilaCondiciones.pop();
                    continue;
                }
                if (condicionActual) {
                    bufferResultado.append(line);
                    bufferResultado.append("\n");
                }
            }
        } catch (IOException ex) {
            // Esta excepcion no puede ocurrir porque se lee de un String
        }

        return bufferResultado.toString();
    }

    private String reemplazarSimbolo(StringBuilder builder, String parametro, Object valor) {

        Pattern pattern = Pattern.compile(":" + parametro);
        Matcher matcher = pattern.matcher(builder);
        String valorTexto = formatearValor(valor);
        return matcher.replaceAll(valorTexto);
    }

    private String formatearValor(Object valor) {
        if (valor == null) {
            return "";
        }
        // TODO: formatear segun tipo

        if (valor instanceof Integer
                || valor instanceof Long
                || valor instanceof Float
                || valor instanceof Double) {
            return valor.toString();
        }
        if (valor instanceof Enum) {
            return Integer.toString(((Enum) valor).ordinal());
        } else if (valor instanceof Fecha) {
            return formateadorSql.getFechaSql((Fecha) valor);
        } else if (valor instanceof FechaHora) {
            return formateadorSql.getFechaHoraSql((FechaHora) valor);
        } else if (valor instanceof Boolean) {
            return formateadorSql.getBooleanSql((Boolean) valor);
        } else if (valor instanceof Collection) {
            String resultado = "";
            Iterator iterador = ((Collection) valor).iterator();            
            while (iterador.hasNext()) {
                if (!resultado.isEmpty()) {
                    resultado += ", ";
                }
                resultado += formatearValor(iterador.next());
            }
            return resultado;
        }
        return "'" + valor.toString() + "'";
    }
}
