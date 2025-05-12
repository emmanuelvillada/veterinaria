package com.mycompany.veterinaria;

import java.util.List;

public interface ICitaDAO {
    void insertar(Cita cita);
    void actualizar(Cita cita);
    void eliminar(int id);
    List<Cita> obtenerTodas();
}
