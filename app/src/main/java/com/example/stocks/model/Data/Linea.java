package com.example.stocks.model.Data;

public class Linea implements Comparable<Linea>{

    private long id;
    private String nombre;
    private int color;

    public Linea(long idLinea, String nombreLinea, int color){
        this.id = idLinea;
        this.nombre = nombreLinea;
        this.color = color;
    }
/*
    public Linea(long idLinea){
        this.id = idLinea;
    }
*/
    public Linea(String nombre){
        this.nombre = nombre;
    }

    @Override
    public int compareTo(Linea linea) {

        if (this.nombre.equals(linea.nombre)){

            return 0;

        }else return -1;

    }

    public boolean equals(Object o){
        if (o instanceof Linea){
            Linea otro = (Linea)o;
            if (this.nombre.equals(otro.nombre)){
                return true;
            }else return false;

        }else return false;

    }

    public long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
