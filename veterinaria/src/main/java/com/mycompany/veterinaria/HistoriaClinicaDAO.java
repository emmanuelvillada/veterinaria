package com.mycompany.veterinaria;
import java.sql.*;
import java.util.*;
import java.util.List;
import java.util.ArrayList;

public class HistoriaClinicaDAO implements IHistoriaClinicaDAO {
    // Implementación de los métodos de la interfaz IHistoriaClinicaDAO
    @Override
    public void agregarHistoriaClinica(HistoriaCLinica historiaClinica) {
        // Lógica para agregar una historia clínica a la base de datos
        try {
            Connection conn = DB.conectar(); // Asumiendo que tienes clase de conexión
            PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO historia_clinica (fecha, diagnostico, tratamiento, id_mascota) VALUES (?, ?, ?, ?)"
            );
            ps.setString(1, historiaClinica.getFecha());
            ps.setString(2, historiaClinica.getDiagnostico());
            ps.setString(3, historiaClinica.getTratamiento());
            ps.setInt(4, historiaClinica.getIdMascota());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<HistoriaCLinica> obtenerHistoriasClinicas() {
        // Lógica para obtener todas las historias clínicas de la base de datos
        List<HistoriaCLinica> historiasClinicas = new ArrayList<>();
        try {
            Connection conn = DB.conectar(); // Asumiendo que tienes clase de conexión
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM historia_clinica");
            while (rs.next()) {
                HistoriaCLinica historiaClinica = new HistoriaCLinica(
                    rs.getInt("id"),
                    rs.getString("fecha"),
                    rs.getString("diagnostico"),
                    rs.getString("tratamiento"),
                    rs.getInt("id_mascota")
                );
                historiasClinicas.add(historiaClinica);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return historiasClinicas;
    }

    @Override
    public HistoriaCLinica obtenerHistoriaClinicaPorId(int id) {
        // Lógica para obtener una historia clínica por ID de la base de datos
        HistoriaCLinica historiaClinica = null;
        try {
            Connection conn = DB.conectar(); // Asumiendo que tienes clase de conexión
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM historia_clinica WHERE id = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                historiaClinica = new HistoriaCLinica(
                    rs.getInt("id"),
                    rs.getString("fecha"),
                    rs.getString("diagnostico"),
                    rs.getString("tratamiento"),
                    rs.getInt("id_mascota")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return historiaClinica;
    }

    @Override
    public void actualizarHistoriaClinica(HistoriaCLinica historiaClinica) {
        // Lógica para actualizar una historia clínica en la base de datos
        try {
            Connection conn = DB.conectar(); // Asumiendo que tienes clase de conexión
            PreparedStatement ps = conn.prepareStatement(
                "UPDATE historia_clinica SET fecha=?, diagnostico=?, tratamiento=?, id_mascota=? WHERE id=?"
            );
            ps.setString(1, historiaClinica.getFecha());
            ps.setString(2, historiaClinica.getDiagnostico());
            ps.setString(3, historiaClinica.getTratamiento());
            ps.setInt(4, historiaClinica.getIdMascota());
            ps.setInt(5, historiaClinica.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void eliminarHistoriaClinica(int id) {
        // Lógica para eliminar una historia clínica por ID de la base de datos
        try {
            Connection conn = DB.conectar(); // Asumiendo que tienes clase de conexión
            PreparedStatement ps = conn.prepareStatement("DELETE FROM historia_clinica WHERE id = ?");
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    
}
