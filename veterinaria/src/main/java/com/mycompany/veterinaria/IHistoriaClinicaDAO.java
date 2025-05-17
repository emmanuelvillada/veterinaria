package com.mycompany.veterinaria;
import java.util.List;

public interface IHistoriaClinicaDAO {

    // Método para agregar una historia clínica
    void agregarHistoriaClinica(HistoriaCLinica historiaClinica);

    // Método para obtener todas las historias clínicas
    List<HistoriaCLinica> obtenerHistoriasClinicas();

    // Método para obtener una historia clínica por ID
    HistoriaCLinica obtenerHistoriaClinicaPorId(int id);

    // Método para actualizar una historia clínica
    void actualizarHistoriaClinica(HistoriaCLinica historiaClinica);

    // Método para eliminar una historia clínica por ID
    void eliminarHistoriaClinica(int id);
    
}
