package utiles.tiempo;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author jberjano
 */
public final class Fecha extends FechaAbstracta {
    
    private static DateFormat formatoPorDefecto = new SimpleDateFormat("dd/MM/yyyy");

    private final static Fecha fechaPresente;
    
    static {        
        fechaPresente = new Fecha();
        fechaPresente.esPresente = true;
    }
    
    public static Fecha getFechaPresente() {
        return fechaPresente;        
    }   

    public Fecha() {
    }
    
    public Fecha(String texto) {
        setTexto(texto);
    }
        
    public Fecha(int dia, int mes, int anno) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, anno);
        calendar.set(Calendar.MONTH, mes - 1);
        calendar.set(Calendar.DAY_OF_MONTH, dia);
        setCalendar(calendar);
        normalizar();
    }
    
    public Fecha(Calendar calendar) {
       super(calendar);
    }
    
    public Fecha(Date date) {
        super(date);
    }
    
    public Fecha(Timestamp timestamp) {
        super(timestamp);
    }
    
    public static Fecha hoy() {
        return new Fecha();
    }
        
    @Override
    protected void normalizar() {        
        if (this.getCalendar() != null) {
            this.getCalendar().set(Calendar.HOUR_OF_DAY, 0);
            this.getCalendar().set(Calendar.MINUTE, 0);
            this.getCalendar().set(Calendar.SECOND, 0);
            this.getCalendar().set(Calendar.MILLISECOND, 0);            
        }
    }

    @Override
    public DateFormat getFormatoPorDefecto() {
        return formatoPorDefecto;
    }
}
