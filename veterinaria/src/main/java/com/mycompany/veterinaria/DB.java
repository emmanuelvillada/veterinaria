package com.mycompany.veterinaria;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DB {
        private static final String URL = "jdbc:postgresql://localhost:5432/veterinaria";
    private static final String USUARIO = "postgres";
    private static final String CLAVE = "emmanuelc1903";

    public static Connection conectar() throws SQLException {
        try{
            return DriverManager.getConnection(URL, USUARIO, CLAVE);
        } catch (SQLException e) {
            throw new SQLException("Error al conectar con la base de datos", e);
        }
        
}
}