package com.mycompany.veterinaria;

public class Cita {
    private int id;
    private String fecha;
    private String hora;
    private String motivo;
    private int idMascota;
    private int idVeterinario;

    public Cita(int id, String fecha, String hora, String motivo, int idMascota, int idVeterinario) {
        this.id = id;
        this.fecha = fecha;
        this.hora = hora;
        this.motivo = motivo;
        this.idMascota = idMascota;
        this.idVeterinario = idVeterinario;
    }

    public Cita(String fecha, String hora, String motivo, int idMascota, int idVeterinario) {
        this(0, fecha, hora, motivo, idMascota, idVeterinario);
    }

    // Getters y setters
    // ...

// Getters y setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getMotivo() {
        return motivo;
    }
    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }
    public int getIdMascota() {
        return idMascota;
    }
    public void setIdMascota(int idMascota) {
        this.idMascota = idMascota;
    }
    public int getIdVeterinario() {
        return idVeterinario;
    }
    public void setIdVeterinario(int idVeterinario) {
        this.idVeterinario = idVeterinario;
    }
    @Override
    public String toString() {
        return "Cita{" +
                "id=" + id +
                ", fecha='" + fecha + '\'' +
                ", hora='" + hora + '\'' +
                ", motivo='" + motivo + '\'' +
                ", idMascota=" + idMascota +
                ", idVeterinario=" + idVeterinario +
                '}';
    }
}
