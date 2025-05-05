package com.mycompany.veterinaria;


import java.util.List;

public interface IClienteDAO {
    List<Cliente> obtenerTodos();
    void insertar(Cliente cliente);
    void eliminar(int id);
    void actualizar(Cliente cliente);
}