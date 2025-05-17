package com.mycompany.veterinaria;
import java.util.List;


public class HistoriaCLinica {
    private int id;
    private String fecha;
    private String diagnostico;
    private String tratamiento;
    private int idMascota;


    public HistoriaCLinica(int id, String fecha, String diagnostico, String tratamiento, int idMascota) {
        this.id = id;
        this.fecha = fecha;
        this.diagnostico = diagnostico;
        this.tratamiento = tratamiento;
        this.idMascota = idMascota;

    }

    public HistoriaCLinica(String fecha, String diagnostico, String tratamiento, int idMascota) {
        this(0, fecha, diagnostico, tratamiento, idMascota);

    }

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

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public String getTratamiento() {
        return tratamiento;
    }

    public void setTratamiento(String tratamiento) {
        this.tratamiento = tratamiento;
    }

    public int getIdMascota() {
        return idMascota;
    }

    public void setIdMascota(int idMascota) {
        this.idMascota = idMascota;
    }


    @Override
    public String toString() {
        return "HistoriaCLinica{" +
                "id=" + id +
                ", fecha='" + fecha + '\'' +
                ", diagnostico='" + diagnostico + '\'' +
                ", tratamiento='" + tratamiento + '\'' +
                ", idMascota=" + idMascota +
                '}';
    }
}