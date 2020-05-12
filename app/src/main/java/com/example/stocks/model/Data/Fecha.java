package com.example.stocks.model.Data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

// clase creada para simplificar el uso de fechas usando formato long
// obtiene la hora y fecha actual al momento de inicializarla y tiene
// metodos para obtener estos de diferentes formatos
public class Fecha implements Comparable<Fecha>{

    private Calendar cal;
    private Date date;

    public Fecha(){
        cal = new GregorianCalendar();
        date = cal.getTime();
    }

    public Fecha(String stringFecha){
        cal = new GregorianCalendar();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        try {
            this.date = format.parse(stringFecha);
        }catch (ParseException e){

        }
    }

    public Fecha(long longFecha){
        cal = new GregorianCalendar();
        SimpleDateFormat formatLong = new SimpleDateFormat("yyyyMMddHHmm");
        try {
            this.date = formatLong.parse(String.valueOf(longFecha));
        }catch (ParseException e){

        }
    }

    @Override
    public int compareTo(Fecha fecha) {
        long aux = this.getLongAMDH() - fecha.getLongAMDH();
        return (int) aux;
    }

    public Date getDate(){
        return this.date;
    }

    public long getLongAMDH(){
        SimpleDateFormat formatoLong = new SimpleDateFormat("yyyyMMddHHmm");
        return Long.parseLong(formatoLong.format(date));
    }

    public String getStringDMAH(){
        SimpleDateFormat formatoStringDMAH = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        return formatoStringDMAH.format(date).toString();
    }

    public String getStringDMA(){
        SimpleDateFormat formatoStringDMA = new SimpleDateFormat("dd/MM/yy");
        return formatoStringDMA.format(date).toString();
    }

}
