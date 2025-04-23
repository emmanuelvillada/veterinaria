package com.mycompany.veterinaria;


import java.sql.*;
import java.util.*;

public class MascotaDAO implements IMascotaDAO {

    // Método para obtener todas las mascotas de la base de datos
    @Override
    public  List<Mascota> obtenerTodas() {
        List<Mascota> lista = new ArrayList<>();
        try (Connection conn = DB.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM mascotas")) {
            while (rs.next()) {
                lista.add(new Mascota(
                    rs.getString("nombre"),
                    rs.getString("especie"),
                    rs.getString("raza")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public  void insertar(Mascota m) {
        String sql = "INSERT INTO mascotas (nombre, especie, raza) VALUES (?, ?, ?)";
        try (Connection conn = DB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, m.getNombre());
            stmt.setString(2, m.getEspecie());
            stmt.setString(3, m.getRaza());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para eliminar una mascota de la base de datos
    @Override
    public  void eliminar(String nombre) {
        String sql = "DELETE FROM mascotas WHERE nombre = ?";
        try (Connection conn = DB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nombre);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para actualizar los datos de una mascota en la base de datos
    @Override
    public  void actualizar(Mascota m) {
        String sql = "UPDATE mascotas SET especie = ?, raza = ? WHERE nombre = ?";
        try (Connection conn = DB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, m.getEspecie());
            stmt.setString(2, m.getRaza());
            stmt.setString(3, m.getNombre());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
