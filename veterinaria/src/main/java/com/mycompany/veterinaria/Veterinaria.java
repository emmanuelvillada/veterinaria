package com.mycompany.veterinaria;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Veterinaria {
    private JFrame frame;
    private JTextField txtNombre, txtEspecie, txtRaza;
    private JTable table;
    private DefaultTableModel tableModel;
    private List<Mascota> listaMascotas;
    private static final String ARCHIVO = "mascotas.txt";

    public static void main(String[] args) {
        Veterinaria window = new Veterinaria();
        window.frame.setVisible(true);
    }

    public Veterinaria() {
        listaMascotas = ArchivoPlano.cargarDesdeArchivo();
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Sistema de Gestión Veterinaria");
        frame.setBounds(100, 100, 800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BorderLayout(10, 10));

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        frame.getContentPane().add(mainPanel);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Datos de la Mascota"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        txtNombre = new JTextField(15);
        txtEspecie = new JTextField(15);
        txtRaza = new JTextField(15);
        JButton btnAgregar = new JButton("Agregar Mascota");
        JButton btnEliminar = new JButton("Eliminar Mascota");
        JButton btnActualizar = new JButton("Actualizar Mascota");

        addFormComponent(formPanel, new JLabel("Nombre:"), txtNombre, gbc, 0);
        addFormComponent(formPanel, new JLabel("Especie:"), txtEspecie, gbc, 1);
        addFormComponent(formPanel, new JLabel("Raza:"), txtRaza, gbc, 2);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(btnAgregar);
        buttonPanel.add(btnEliminar);
        buttonPanel.add(btnActualizar);
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        formPanel.add(buttonPanel, gbc);
        mainPanel.add(formPanel, BorderLayout.WEST);

        tableModel = new DefaultTableModel(new Object[][] {}, new String[] {"Nombre", "Especie", "Raza"});
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        btnAgregar.addActionListener(e -> agregarMascota());
        btnEliminar.addActionListener(e -> eliminarMascota());
        btnActualizar.addActionListener(e ->actualizarMascota());

        cargarDatosEnTabla();
    }

    private void addFormComponent(JPanel panel, JLabel label, JTextField field, GridBagConstraints gbc, int y) {
        gbc.gridy = y;
        gbc.gridx = 0;
        panel.add(label, gbc);
        gbc.gridx = 1;
        panel.add(field, gbc);
    }

    private void agregarMascota() {
        String nombre = txtNombre.getText().trim();
        String especie = txtEspecie.getText().trim();
        String raza = txtRaza.getText().trim();

        if (nombre.isEmpty() || especie.isEmpty() || raza.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Todos los campos son obligatorios", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        listaMascotas.add(new Mascota(nombre, especie, raza));
        ArchivoPlano.guardarEnArchivo(listaMascotas);
        cargarDatosEnTabla();
        limpiarFormulario();
    }

    private void eliminarMascota() {
        int filaSeleccionada = table.getSelectedRow();
        if (filaSeleccionada != -1) {
            listaMascotas.remove(filaSeleccionada);
            ArchivoPlano.guardarEnArchivo(listaMascotas);
            cargarDatosEnTabla();
        } else {
            JOptionPane.showMessageDialog(frame, "Seleccione una mascota para eliminar", "Aviso", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void actualizarMascota() {
        int filaSeleccionada = table.getSelectedRow();
        if (filaSeleccionada != -1) {
            String especie = txtEspecie.getText().trim();
            String raza = txtRaza.getText().trim();
            if (especie.isEmpty() || raza.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Los campos especie y raza son obligatorios", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            listaMascotas.get(filaSeleccionada).setEspecie(especie);
            listaMascotas.get(filaSeleccionada).setRaza(raza);
            ArchivoPlano.guardarEnArchivo(listaMascotas);
            cargarDatosEnTabla();
            limpiarFormulario();
        } else {
            JOptionPane.showMessageDialog(frame, "Seleccione una mascota para actualizar", "Aviso", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void limpiarFormulario() {
        txtNombre.setText("");
        txtEspecie.setText("");
        txtRaza.setText("");
    }

    //cargar datos en la tabla

    private void cargarDatosEnTabla() {
        tableModel.setRowCount(0);
        for (Mascota mascota : listaMascotas) {
            tableModel.addRow(new Object[] {mascota.getNombre(), mascota.getEspecie(), mascota.getRaza()});
        }
    }

}

