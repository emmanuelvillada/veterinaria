package com.mycompany.veterinaria;
import java.sql.*;
import java.util.*;
public class TratamientoDAO implements ITratamientoDAo{
    

    @Override
    public List<Tratamiento> obtenerTodos() {
        List<Tratamiento> tratamientos = new ArrayList<>();
        String sql = "SELECT * FROM tratamiento";
        try (Connection conn = DB.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                double costo = rs.getDouble("costo");
                tratamientos.add(new Tratamiento(id, nombre, costo));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tratamientos;
    }

    @Override
    public void insertar(Tratamiento t) {
        String sql = "INSERT INTO tratamiento (nombre, costo) VALUES (?, ?)";
        try (Connection conn = DB.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, t.getNombre());
            pstmt.setDouble(2, t.getCosto());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actualizar(Tratamiento t) {
        String sql = "UPDATE tratamiento SET nombre = ?, costo = ? WHERE id = ?";
        try (Connection conn = DB.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, t.getNombre());
            pstmt.setDouble(2, t.getCosto());
            pstmt.setInt(3, t.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
}
    }

    @Override
    public void eliminar(int id) {
        String sql = "DELETE FROM tratamiento WHERE id = ?";
        try (Connection conn = DB.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
