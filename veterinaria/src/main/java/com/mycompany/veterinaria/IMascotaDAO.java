package com.mycompany.veterinaria;

import java.util.List;

public interface IMascotaDAO {
    List<Mascota> obtenerTodas();
    void insertar(Mascota mascota);
    void eliminar(String nombre);
    void actualizar(Mascota mascota);
}
