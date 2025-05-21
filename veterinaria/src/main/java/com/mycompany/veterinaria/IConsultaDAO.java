package com.mycompany.veterinaria;

import java.util.List;

public interface IConsultaDAO {
    void insertar(Consulta consulta);
    void actualizar(Consulta consulta);
    void eliminar(int id);
    List<Consulta> obtenerTodas();
    List<Consulta> obtenerConsultasPorMascota(String idMascota);
}
