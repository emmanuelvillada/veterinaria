/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.veterinaria;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Filthier
 */
public class ArchivoPlano {
    
      private static final String RUTA_ARCHIVO = "mascotas.txt"; // Archivo donde se guardarán los datos

    // Método para guardar la lista de estudiantes en un archivo
    public static void guardarEnArchivo(List<Mascota> listaMascotas) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(RUTA_ARCHIVO))) {
            for (Mascota mascota : listaMascotas) {
                // Guardamos los datos de cada estudiante separados por comas
                writer.write(mascota.getNombre() + "," + mascota.getEspecie() + "," + mascota.getRaza());
                writer.newLine(); // Escribimos una nueva línea por cada estudiante
            }
            System.out.println("Datos guardados correctamente en el archivo.");
        } catch (IOException e) {
            System.err.println("Error al guardar los datos en el archivo: " + e.getMessage());
        }
    }

    // Método para cargar los estudiantes desde el archivo y retornar una lista
    public static List<Mascota> cargarDesdeArchivo() {
        List<Mascota> listaMascotas = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(RUTA_ARCHIVO))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] datos = linea.split(","); // Dividimos la línea por comas
                String nombre = datos[0];
                String especie = datos[1];
                String raza = datos[2];
                // Creamos un nuevo estudiante con los datos obtenidos y lo agregamos a la lista
                Mascota estudiante = new Mascota(nombre, especie, raza);
                
                
                listaMascotas.add(estudiante);
            }
            System.out.println("Datos cargados correctamente desde el archivo.");
        } catch (IOException e) {
            System.err.println("Error al cargar los datos desde el archivo: " + e.getMessage());
        }
        return listaMascotas;
    }
    
    
    // Método para eliminar un estudiante del archivo por documento
    public static void eliminarDeArchivo(String nombre) {
        List<Mascota> listaMascotas = cargarDesdeArchivo();  // Cargar los estudiantes desde el archivo
        boolean eliminado = false;

        // Buscar y eliminar el estudiante con el documento especificado
        for (Mascota mascota : listaMascotas) {
            if (mascota.getNombre() == nombre) {
                listaMascotas.remove(mascota);
                eliminado = true;
                break;
            }
        }

        if (eliminado) {
            // Si se eliminó el estudiante, guardar los datos actualizados en el archivo
            guardarEnArchivo(listaMascotas);
            System.out.println("Mascota: " + nombre + " eliminada correctamente.");
        } else {
            System.out.println("No se encontró un estudiante con el documento: " + nombre);
        }
    }

    
    
    
}