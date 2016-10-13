package utiles.conversion;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import utiles.tiempo.Fecha;
import utiles.tiempo.FechaHora;

/**
 * Utilidades para conversión de datos.
 * @author jberjano
 */
public class Conversion {

    private static final Set conjuntoCadenasTrue;
    private static final Set conjuntoCadenasFalse;

    static {
        String[] cadenasTrue = {"si", "sí", "true", "verdadero", "t", "v"};
        conjuntoCadenasTrue = new HashSet(Arrays.asList(cadenasTrue));

        String[] cadenasFalse = {"no", "false", "falso", "f"};
        conjuntoCadenasFalse = new HashSet(Arrays.asList(cadenasFalse));
    }

    public static String toString(Object obj) {
        return obj == null ? null : obj.toString();
    }

    public static Integer toInteger(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof Integer) {
            return (Integer) obj;
        }
        if (obj instanceof Long) {
            return ((Long) obj).intValue();
        }
        if (obj instanceof Double) {
            return ((Double) obj).intValue();
        }
        if (obj instanceof Float) {
            return ((Float) obj).intValue();
        }

        Integer resultado = null;
        try {
            resultado = Integer.parseInt(obj.toString());
        } catch (Exception e) {
        }

        return resultado;
    }

    public static Long toLong(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof Long) {
            return (Long) obj;
        }
        if (obj instanceof Integer) {
            return ((Integer) obj).longValue();
        }
        if (obj instanceof Double) {
            return ((Double) obj).longValue();
        }
        if (obj instanceof Float) {
            return ((Float) obj).longValue();
        }

        Long resultado = null;
        try {
            resultado = Long.parseLong(obj.toString());
        } catch (Exception e) {
        }

        return resultado;
    }

    public static Boolean toBoolean(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof Boolean) {
            return (Boolean) obj;
        }

        String texto = obj.toString().trim().toLowerCase();

        if (conjuntoCadenasTrue.contains(texto)) {
            return true;
        }
        if (conjuntoCadenasFalse.contains(texto)) {
            return false;
        }

        Integer entero = toInteger(obj);
        if (entero != null) {
            return entero != 0;
        }
        return null;
    }

    public static Double toDouble(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof Double) {
            return (Double) obj;
        }
        if (obj instanceof Float) {
            return ((Float) obj).doubleValue();
        }

        Double resultado = null;
        try {
            resultado = Double.parseDouble(obj.toString());
        } catch (Exception e) {
        }

        return resultado;
    }

    public static byte[] hexToBytes(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }

    final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();

    public static String bytesToHex(byte[] bytes) {
        int length = bytes.length;
        char[] hexChars = new char[length * 2];
        for (int j = 0; j < length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

    public static String bytesToHex(byte[] bytes, int length) {
        char[] hexChars = new char[length * 2];
        for (int j = 0; j < length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

    public static String stringToHexString(String str) {
        StringBuilder hex = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            hex.append(Integer.toHexString(str.charAt(i)));
        }
        return hex.toString();
    }

    public static String hexStringToString(String str) {
        byte[] txtInByte = new byte[str.length() / 2];
        int j = 0;
        for (int i = 0; i < str.length(); i += 2) {
            txtInByte[j++] = Byte.parseByte(str.substring(i, i + 2), 16);
        }
        String result = new String(txtInByte);
        int endpos = result.indexOf('\0');
        if (endpos != -1) {
            result = result.substring(0, endpos);
        }

        return result;
    }

    public static byte[] cadenaConFormatoABytes(String str) {

        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        String[] tokens = str.trim().split("\\s");
        for (String token : tokens) {
            if (token.isEmpty()) {
                continue;
            }
            byte octeto = cadenaConFormatoAUnByte(token);
            stream.write(octeto);
        }

        return stream.toByteArray();
    }

    private static byte cadenaConFormatoAUnByte(String str) {
        try {
            if (str.startsWith("0x")) {
                int valor = Integer.parseInt(str.substring(2), 16);
                return (byte) valor;
            } else if (str.startsWith("'")) {
                return (byte) str.charAt(1);
            } else {
                return Byte.parseByte(str);
            }
        } catch (Exception ex) {
            return 0;
        }
    }

    public static boolean isBlank(String str) {
        return str == null || str.trim().isEmpty();
    }

    public static boolean equals(Object o1, Object o2) {
        if (o1 == o2) {
            return true;
        }

        if (o1 == null || o2 == null) {
            return false;
        }

        return o1.equals(o2);
    }

    public static long ipALong(String ip) {
        ip = ip.replace('/', ' ').trim();

        try {
            Scanner sc = new Scanner(ip).useDelimiter("\\.");
            return (sc.nextLong() << 24)
                    + (sc.nextLong() << 16)
                    + (sc.nextLong() << 8)
                    + (sc.nextLong());
        } catch (Exception e) {
            return 0;
        }
    }
    
    public static Boolean aBooleano(Object valor) {
        return toBoolean(valor);
    }

    public static String aCadena(Object valor) {
        if (valor == null) {
            return null;
        } else if (valor instanceof String) {
            return (String) valor;
        } else {
            return valor.toString();
        }
    }

    public static Long aEntero(Object valor) {
        return toLong(valor);
    }

    public static Double aReal(Object valor) {
        if (valor == null) {
            return null;
        } else if (valor instanceof Double) {
            return (Double) valor;
        } else if (valor instanceof Float) {
            return new Double((Float) valor);
        } else {
            try {
                return Double.parseDouble(valor.toString());
            } catch (Exception e) {
                return null;
            }
        }
    }

    public static byte[] aByteArray(Object valor) {
        if (valor == null) {
            return null;
        } else if (valor instanceof byte[]) {
            return (byte[]) valor;
        } else {
            return valor.toString().getBytes();
        }
    }

    public static Fecha aFecha(Object valor) {
        if (valor == null) {
            return null;
        } else if (valor instanceof Fecha) {
            return (Fecha) valor;
        } else if (valor instanceof FechaHora) {
            return ((FechaHora) valor).getFecha();
        } else {
            return new Fecha(valor.toString());
        }
    }

    public static FechaHora aFechaHora(Object valor) {
        if (valor == null) {
            return null;
        } else if (valor instanceof Fecha) {
            return new FechaHora((Fecha) valor);
        } else if (valor instanceof FechaHora) {
            return (FechaHora) valor;
        } else {
            return new FechaHora(valor.toString());
        }
    }

    public static String quitarAcentos(String input) {
        // Cadena de caracteres original a sustituir.
        String original = "áàäéèëíìïóòöúùuñÁÀÄÉÈËÍÌÏÓÒÖÚÙÜÑçÇ";
        // Cadena de caracteres ASCII que reemplazarán los originales.
        String ascii = "aaaeeeiiiooouuunAAAEEEIIIOOOUUUNcC";
        String output = input;
        for (int i = 0; i < original.length(); i++) {
            output = output.replace(original.charAt(i), ascii.charAt(i));
        }
        return output;
    }

    public static String capitalizar(String texto) {
        if (texto == null || texto.isEmpty()) {
            return texto;
        }
        String textoCapitalizado = texto.substring(0, 1).toUpperCase();
        if (texto.length() > 1) {
            textoCapitalizado += texto.substring(1);
        }
        return textoCapitalizado;
    }
}
