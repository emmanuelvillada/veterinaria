package com.mycompany.veterinaria;

import java.util.List;

public interface IServicioDAO {
    void insertar(Servicio servicio);
    void actualizar(Servicio servicio);
    void eliminar(int id);
    List<Servicio> obtenerTodos();
}
