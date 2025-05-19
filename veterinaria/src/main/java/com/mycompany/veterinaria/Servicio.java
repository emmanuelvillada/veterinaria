package com.mycompany.veterinaria;

public class Servicio {
    private int id;
    private String nombre;
    private double costo;

    public Servicio() {}

    public Servicio(String nombre, double costo) {
        this.nombre = nombre;
        this.costo = costo;
    }

    public Servicio(int id, String nombre, double costo) {
        this.id = id;
        this.nombre = nombre;
        this.costo = costo;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public double getCosto() {
        return costo;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }
}

