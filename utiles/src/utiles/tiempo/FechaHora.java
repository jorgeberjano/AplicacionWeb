package utiles.tiempo;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Clase que representa una fecha y una hora.
 *
 * @author jberjano
 */
public class FechaHora extends FechaAbstracta {

    private static final SimpleDateFormat formatoPorDefecto = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    private final static FechaHora fechaHoraPresente;

    static {
        fechaHoraPresente = new FechaHora();
        fechaHoraPresente.esPresente = true;
    }

    public static FechaHora presente() {
        return fechaHoraPresente;
    }
    
    public static FechaHora ahora() {
        return new FechaHora();
    }

    public FechaHora() {
        super();
    }
    
    public FechaHora(Fecha fecha) {
        this(fecha.getCalendar());
    }

    public FechaHora(Calendar calendar) {
        super(calendar);
    }

    public FechaHora(Date date) {
        super(date);
    }
    
    public FechaHora(long milisegundos) {        
        super(milisegundos);
    }

    public FechaHora(int dia, int mes, int anno, int horas, int min, int seg) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, anno);
        calendar.set(Calendar.MONTH, mes - 1);
        calendar.set(Calendar.DAY_OF_MONTH, dia);
        calendar.set(Calendar.HOUR_OF_DAY, horas);
        calendar.set(Calendar.MINUTE, min);
        calendar.set(Calendar.SECOND, seg);
        setCalendar(calendar);
    }

    public FechaHora(String texto) {
        setTexto(texto);
    }

    protected void normalizar() {
        if (this.getCalendar() != null) {
            this.getCalendar().set(Calendar.MILLISECOND, 0);
        }
    }
    
    @Override
    public DateFormat getFormatoPorDefecto() {
        return formatoPorDefecto;
    }

    public int compareTo(FechaHora otraFecha) {
        if (getCalendar() == null || otraFecha == null) {
            return 0;
        }
        return getCalendar().compareTo(otraFecha.getCalendar());
    }

    public Fecha getFecha() {
        Fecha fecha = new Fecha((Calendar) getCalendar().clone());
        return fecha;
    }

    public static long diferenciaEnMinutos(FechaHora fechaDesde, FechaHora fechaHasta) {
        return diferenciaEnMilisegundos(fechaDesde, fechaHasta) / 60000;
    }

    public static long diferenciaEnSegundos(FechaHora fechaDesde, FechaHora fechaHasta) {
        return diferenciaEnMilisegundos(fechaDesde, fechaHasta) / 1000;
    }

    public static long diferenciaEnMilisegundos(FechaHora fechaDesde, FechaHora fechaHasta) {
        long milisegundosFechaDesde = fechaDesde.toCalendar().getTimeInMillis();
        long milisegundosFechaActual = fechaHasta.toCalendar().getTimeInMillis();

        return Math.abs(milisegundosFechaDesde - milisegundosFechaActual);
    }

    public HoraMinuto getHoraMinuto() {
        return new HoraMinuto(getCalendar().get(Calendar.HOUR_OF_DAY), getCalendar().get(Calendar.MINUTE));
    }
}
