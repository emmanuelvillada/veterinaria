package com.mycompany.veterinaria;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServicioDAO implements IServicioDAO {
    private Connection connection;

    public ServicioDAO() {
        try {
            connection = DB.conectar();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void insertar(Servicio servicio) {
        String sql = "INSERT INTO servicio(nombre, costo) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, servicio.getNombre());
            stmt.setDouble(2, servicio.getCosto());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actualizar(Servicio servicio) {
        String sql = "UPDATE servicio SET nombre = ?, costo = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, servicio.getNombre());
            stmt.setDouble(2, servicio.getCosto());
            stmt.setInt(3, servicio.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void eliminar(int id) {
        String sql = "DELETE FROM servicio WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Servicio> obtenerTodos() {
        List<Servicio> lista = new ArrayList<>();
        String sql = "SELECT * FROM servicio";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new Servicio(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getDouble("costo")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}

