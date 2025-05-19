package com.mycompany.veterinaria;

public class Consulta {
    private int id;
    private int idMascota;
    private int idVeterinario;
    private String fecha;
    private String motivo;

    public Consulta(int id, int idMascota, int idVeterinario, String fecha, String motivo) {
        this.id = id;
        this.idMascota = idMascota;
        this.idVeterinario = idVeterinario;
        this.fecha = fecha;
        this.motivo = motivo;
    }

    public Consulta(int idMascota, int idVeterinario, String fecha, String motivo) {
        this.idMascota = idMascota;
        this.idVeterinario = idVeterinario;
        this.fecha = fecha;
        this.motivo = motivo;
    }

    public int getId() { return id; }
    public int getIdMascota() { return idMascota; }
    public int getIdVeterinario() { return idVeterinario; }
    public String getFecha() { return fecha; }
    public String getMotivo() { return motivo; }

    public void setId(int id) { this.id = id; }
}
