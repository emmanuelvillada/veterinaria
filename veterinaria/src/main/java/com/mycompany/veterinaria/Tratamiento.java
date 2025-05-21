package com.mycompany.veterinaria;

public class Tratamiento {
    private int id;
    private String nombre;
    private double costo;

    // Constructor para insertar (sin id)
    public Tratamiento(String nombre, double costo) {
        this.nombre = nombre;
        this.costo = costo;
    }

    // Constructor para actualizar o mostrar
    public Tratamiento(int id, String nombre, double costo) {
        this.id = id;
        this.nombre = nombre;
        this.costo = costo;
    }

    // Getters y Setters
    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public double getCosto() { return costo; }

    public void setId(int id) { this.id = id; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setCosto(double costo) { this.costo = costo; }
}
