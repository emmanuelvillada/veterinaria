package com.mycompany.veterinaria;

import java.util.List;


public interface IVeterinarioDAO {
    List<Veterinario> obtenerTodos();
    void insertar(Veterinario veterinario);
    void eliminar(int id);
    void actualizar(Veterinario veterinario);
    
}
