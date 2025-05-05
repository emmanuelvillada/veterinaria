package com.mycompany.veterinaria;

import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;

public class VeterinarioDAO implements IVeterinarioDAO {
    @Override
    public void insertar(Veterinario veterinario) {
        // Implementación para insertar un veterinario en la base de datos
        String sql = "INSERT INTO veterinarios (nombre, telefono) VALUES (?, ?)";
        try (Connection conn = DB.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, veterinario.getNombre());
            stmt.setString(2, veterinario.getTelefono());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void eliminar(int id) {
        // Implementación para eliminar un veterinario de la base de datos
        String sql = "DELETE FROM veterinarios WHERE id = ?";
        try (Connection conn = DB.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actualizar(Veterinario veterinario) {
        // Implementación para actualizar un veterinario en la base de datos
        String sql = "UPDATE veterinarios SET nombre = ?, telefono = ? WHERE id = ?";
        try (Connection conn = DB.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, veterinario.getNombre());
            stmt.setString(2, veterinario.getTelefono());
            stmt.setInt(3, veterinario.getId()); // Asumiendo que Veterinario tiene un método getId()
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Veterinario> obtenerTodos() {
        // Implementación para obtener todos los veterinarios de la base de datos
        String sql = "SELECT * FROM veterinarios";
        List<Veterinario> lista = new ArrayList<>();
        try (Connection conn = DB.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                lista.add(new Veterinario(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("especialidad"),
                    rs.getString("telefono")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;   
    }
    
}
