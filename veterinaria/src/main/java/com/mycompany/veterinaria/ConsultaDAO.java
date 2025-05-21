package com.mycompany.veterinaria;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ConsultaDAO implements IConsultaDAO {
    private Connection conexion;

    public ConsultaDAO() {
        try {
            conexion = DB.conectar();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void insertar(Consulta c) {
        try {
            PreparedStatement ps = conexion.prepareStatement("INSERT INTO consulta (id_mascota, id_veterinario, fecha, motivo) VALUES (?, ?, ?, ?)");
            ps.setInt(1, c.getIdMascota());
            ps.setInt(2, c.getIdVeterinario());
            ps.setString(3, c.getFecha());
            ps.setString(4, c.getMotivo());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actualizar(Consulta c) {
        try {
            PreparedStatement ps = conexion.prepareStatement("UPDATE consulta SET id_mascota=?, id_veterinario=?, fecha=?, motivo=? WHERE id=?");
            ps.setInt(1, c.getIdMascota());
            ps.setInt(2, c.getIdVeterinario());
            ps.setString(3, c.getFecha());
            ps.setString(4, c.getMotivo());
            ps.setInt(5, c.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void eliminar(int id) {
        try {
            PreparedStatement ps = conexion.prepareStatement("DELETE FROM consulta WHERE id=?");
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Consulta> obtenerTodas() {
        List<Consulta> lista = new ArrayList<>();
        try {
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM consulta");
            while (rs.next()) {
                lista.add(new Consulta(
                    rs.getInt("id"),
                    rs.getInt("id_mascota"),
                    rs.getInt("id_veterinario"),
                    rs.getString("fecha"),
                    rs.getString("motivo")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public List<Consulta> obtenerConsultasPorMascota(String nombreMascota) {
        List<Consulta> lista = new ArrayList<>();
        try {
            PreparedStatement ps = conexion.prepareStatement("SELECT c.* FROM consulta c JOIN mascota m ON c.id_mascota = m.id WHERE m.nombre = ?");
            ps.setString(1, nombreMascota);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(new Consulta(
                    rs.getInt("id"),
                    rs.getInt("id_mascota"),
                    rs.getInt("id_veterinario"),
                    rs.getString("fecha"),
                    rs.getString("motivo")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}
