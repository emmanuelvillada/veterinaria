package com.mycompany.veterinaria;

import java.util.List;

public interface ITratamientoDAo {
    List<Tratamiento> obtenerTodos();
    void insertar(Tratamiento t);
    void actualizar(Tratamiento t);
    void eliminar(int id);
}
