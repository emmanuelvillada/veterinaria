/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.veterinaria;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author DELL
 */

public class Veterinaria {
    private JFrame frame;
    private JTextField txtNombre;
    private JTextField txtEspecie;
    private JTextField txtRaza;
    private JTable table;
    private DefaultTableModel tableModel;
    private List<Mascota> listaMascotas;

    public static void main(String[] args) {
        
            
                Veterinaria window = new Veterinaria();
                window.frame.setVisible(true);
            
       
    }

    public Veterinaria() {
        listaMascotas = new ArrayList<>();
        initialize();
    }

    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BorderLayout());

        // Panel de formulario
        JPanel panel = new JPanel();
        frame.getContentPane().add(panel, BorderLayout.NORTH);
        panel.setLayout(new GridLayout(4, 2));

        JLabel lblNombre = new JLabel("Nombre");
        panel.add(lblNombre);

        txtNombre = new JTextField();
        panel.add(txtNombre);
        txtNombre.setColumns(10);

        JLabel lblEspecie = new JLabel("Especie");
        panel.add(lblEspecie);

        txtEspecie = new JTextField();
        panel.add(txtEspecie);
        txtEspecie.setColumns(10);

        JLabel lblRaza = new JLabel("Raza");
        panel.add(lblRaza);

        txtRaza = new JTextField();
        panel.add(txtRaza);
        txtRaza.setColumns(10);

        JButton btnAgregar = new JButton("Agregar Mascota");
        btnAgregar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                agregarMascota();
            }
        });
        panel.add(btnAgregar);

        //boton de eliminar
        JButton btnEliminar = new JButton("Eliminar Mascota");
        btnEliminar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                eliminarMascota();
            }
        });
        panel.add(btnEliminar);

        // Panel de tabla
        JScrollPane scrollPane = new JScrollPane();
        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);

        tableModel = new DefaultTableModel(new Object[][] {}, new String[] {"Nombre", "Especie", "Raza"});
        table = new JTable(tableModel);
        scrollPane.setViewportView(table);

        // Cargar datos desde el archivo plano
        listaMascotas = ArchivoPlano.cargarDesdeArchivo();
        for (Mascota mascota : listaMascotas) {
            tableModel.addRow(new Object[] {mascota.getNombre(), mascota.getEspecie(), mascota.getRaza()});
        }

        
    }

    // Método para agregar una mascota

    private void agregarMascota() {
        String nombre = txtNombre.getText();
        String especie = txtEspecie.getText();
        String raza = txtRaza.getText();
        
        try{

        if (!nombre.isEmpty() && !especie.isEmpty() && !raza.isEmpty()) {
            Mascota nuevaMascota = new Mascota(nombre, especie, raza);
            listaMascotas.add(nuevaMascota);
            ArchivoPlano.guardarEnArchivo(listaMascotas);
            tableModel.addRow(new Object[] {nombre, especie, raza});
            limpiarFormulario();
        }} catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "Todos los campos son obligatorios", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Método para eliminar una mascota
    private void eliminarMascota() {
        int filaSeleccionada = table.getSelectedRow();
        if (filaSeleccionada != -1) {
            String nombre = (String) tableModel.getValueAt(filaSeleccionada, 0);
            ArchivoPlano.eliminarDeArchivo(nombre);
            tableModel.removeRow(filaSeleccionada);
        }
    }

    private void limpiarFormulario() {
        txtNombre.setText("");
        txtEspecie.setText("");
        txtRaza.setText("");
    }
}

