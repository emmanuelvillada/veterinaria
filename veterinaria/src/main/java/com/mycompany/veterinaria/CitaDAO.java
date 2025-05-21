package com.mycompany.veterinaria;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CitaDAO implements ICitaDAO {
    private final Connection conn;

    public CitaDAO() {
        try {
            conn = DB.conectar(); // Asegúrate de tener implementada la clase DB con un método conectar()
        } catch (SQLException e) {
            throw new RuntimeException("Error al conectar con la base de datos", e);
        }
    }

    public void insertar(Cita c) {
        try {
            PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO cita (fecha, hora, motivo, id_mascota, id_veterinario) VALUES (?, ?, ?, ?, ?)"
            );
            ps.setString(1, c.getFecha());
            ps.setString(2, c.getHora());
            ps.setString(3, c.getMotivo());
            ps.setInt(4, c.getIdMascota());
            ps.setInt(5, c.getIdVeterinario());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void actualizar(Cita c) {
        try {
            PreparedStatement ps = conn.prepareStatement(
                "UPDATE cita SET fecha = ?, hora = ?, motivo = ?, id_mascota = ?, id_veterinario = ? WHERE id = ?"
            );
            ps.setString(1, c.getFecha());
            ps.setString(2, c.getHora());
            ps.setString(3, c.getMotivo());
            ps.setInt(4, c.getIdMascota());
            ps.setInt(5, c.getIdVeterinario());
            ps.setInt(6, c.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void eliminar(int id) {
        try {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM cita WHERE id = ?");
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Cita> obtenerTodas() {
        List<Cita> lista = new ArrayList<>();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM cita ORDER BY fecha, hora");
            while (rs.next()) {
                lista.add(new Cita(
                    rs.getInt("id"),
                    rs.getString("fecha"),
                    rs.getString("hora"),
                    rs.getString("motivo"),
                    rs.getInt("id_mascota"),
                    rs.getInt("id_veterinario")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}
