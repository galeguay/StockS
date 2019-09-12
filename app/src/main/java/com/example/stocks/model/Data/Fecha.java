package com.example.stocks.model.Data;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

// clase creada para simplificar el uso de fechas usando formato long
// obtiene la hora y fecha actual al momento de inicializarla y tiene
// metodos para obtener estos de diferentes formatos
public class Fecha {

    private Calendar cal;
    private Date date;

    public Fecha(){
        cal = new GregorianCalendar();
        date = cal.getTime();
    }
    //formato usado para nombre de backup
    public long getLong(){
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