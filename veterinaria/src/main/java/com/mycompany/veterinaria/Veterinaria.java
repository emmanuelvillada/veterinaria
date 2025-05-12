package com.mycompany.veterinaria;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class Veterinaria {
    private JFrame frame;

    // DAO
    private IMascotaDAO mascotaDAO = new MascotaDAO();
    private IClienteDAO clienteDAO = new ClienteDAO();

    // Componentes CRUD Mascotas
    private JTextField txtNombreMascota, txtEspecie, txtRaza;
    private JTable tableMascotas;
    private DefaultTableModel tableModelMascotas;
    private List<Mascota> listaMascotas;

    // Componentes CRUD Clientes
    private JTextField txtNombreCliente, txtDniCliente, txtTelefonoCliente;
    private JTable tableClientes;
    private DefaultTableModel tableModelClientes;
    private List<Cliente> listaClientes;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Veterinaria app = new Veterinaria();
            app.frame.setVisible(true);
        });
    }

    public Veterinaria() {
        frame = new JFrame("La Mascota Feliz - Veterinaria");
        frame.setBounds(100, 100, 850, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JTabbedPane tabbedPane = new JTabbedPane();

        tabbedPane.addTab("Mascotas", crearPanelMascotas());
        tabbedPane.addTab("Clientes", crearPanelClientes());
        tabbedPane.addTab("Veterinarios", crearPanelVeterinario());
        tabbedPane.addTab("Citas", crearPanelCitas());


        frame.add(tabbedPane, BorderLayout.CENTER);
    }

    // PANEL MASCOTAS
    private JPanel crearPanelMascotas() {
        JPanel panel = new JPanel(new BorderLayout());

        listaMascotas = mascotaDAO.obtenerTodas();

        JPanel form = new JPanel(new GridLayout(4, 2, 5, 5));
        txtNombreMascota = new JTextField();
        txtEspecie = new JTextField();
        txtRaza = new JTextField();
        JButton btnAgregar = new JButton("Agregar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnActualizar = new JButton("Actualizar");

        form.add(new JLabel("Nombre:"));
        form.add(txtNombreMascota);
        form.add(new JLabel("Especie:"));
        form.add(txtEspecie);
        form.add(new JLabel("Raza:"));
        form.add(txtRaza);

        JPanel botones = new JPanel(new FlowLayout());
        botones.add(btnAgregar);
        botones.add(btnActualizar);
        botones.add(btnEliminar);
        form.add(botones);

        tableModelMascotas = new DefaultTableModel(new String[]{"Nombre", "Especie", "Raza"}, 0);
        tableMascotas = new JTable(tableModelMascotas);
        JScrollPane scroll = new JScrollPane(tableMascotas);

        panel.add(form, BorderLayout.NORTH);
        panel.add(scroll, BorderLayout.CENTER);

        btnAgregar.addActionListener(e -> {
            mascotaDAO.insertar(new Mascota(txtNombreMascota.getText(), txtEspecie.getText(), txtRaza.getText()));
            cargarDatosMascotas();
        });

        btnEliminar.addActionListener(e -> {
            int row = tableMascotas.getSelectedRow();
            if (row != -1) {
                String nombre = (String) tableModelMascotas.getValueAt(row, 0);
                int confirm = JOptionPane.showConfirmDialog(frame,
                        "¿Estás seguro de que quieres eliminar a la mascota \"" + nombre + "\"?",
                        "Confirmar eliminación",
                        JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    mascotaDAO.eliminar(nombre);
                    cargarDatosMascotas();
                }
            }
        });

        btnActualizar.addActionListener(e -> {
            int row = tableMascotas.getSelectedRow();
            if (row != -1) {
                mascotaDAO.actualizar(new Mascota(
                        (String) tableModelMascotas.getValueAt(row, 0),
                        txtEspecie.getText(),
                        txtRaza.getText()
                ));
                cargarDatosMascotas();
            }
        });

        tableMascotas.getSelectionModel().addListSelectionListener(event -> {
            int row = tableMascotas.getSelectedRow();
            if (row >= 0) {
                txtNombreMascota.setText((String) tableModelMascotas.getValueAt(row, 0));
                txtEspecie.setText((String) tableModelMascotas.getValueAt(row, 1));
                txtRaza.setText((String) tableModelMascotas.getValueAt(row, 2));
            }
        });

        cargarDatosMascotas();
        return panel;
    }

    private void cargarDatosMascotas() {
        listaMascotas = mascotaDAO.obtenerTodas();
        tableModelMascotas.setRowCount(0);
        for (Mascota m : listaMascotas) {
            tableModelMascotas.addRow(new Object[]{m.getNombre(), m.getEspecie(), m.getRaza()});
        }
        txtNombreMascota.setText("");
        txtEspecie.setText("");
        txtRaza.setText("");
    }

// PANEL CLIENTES
private JPanel crearPanelClientes() {
    JPanel panel = new JPanel(new BorderLayout());

    listaClientes = clienteDAO.obtenerTodos();

    // Creamos el formulario con 4 campos ahora
    JPanel form = new JPanel(new GridLayout(4, 2, 5, 5));
    txtNombreCliente = new JTextField();
    txtDniCliente = new JTextField();
    txtTelefonoCliente = new JTextField();  // nuevo campo
    JButton btnAgregar = new JButton("Agregar");
    JButton btnEliminar = new JButton("Eliminar");
    JButton btnActualizar = new JButton("Actualizar");

    form.add(new JLabel("Nombre:"));
    form.add(txtNombreCliente);
    form.add(new JLabel("Documento:"));
    form.add(txtDniCliente);
    form.add(new JLabel("Teléfono:")); // nuevo label
    form.add(txtTelefonoCliente);      // nuevo campo

    JPanel botones = new JPanel(new FlowLayout());
    botones.add(btnAgregar);
    botones.add(btnActualizar);
    botones.add(btnEliminar);
    form.add(botones);

    // Agregamos columna Teléfono a la tabla
    tableModelClientes = new DefaultTableModel(new String[]{"Nombre", "DNI", "Teléfono"}, 0);
    tableClientes = new JTable(tableModelClientes);
    JScrollPane scroll = new JScrollPane(tableClientes);

    panel.add(form, BorderLayout.NORTH);
    panel.add(scroll, BorderLayout.CENTER);

    btnAgregar.addActionListener(e -> {
        clienteDAO.insertar(new Cliente(
            //parsear a int el dni
            Integer.parseInt(txtDniCliente.getText()),
            txtNombreCliente.getText(),
            //parsear a int el dni
            txtTelefonoCliente.getText()
        ));
        cargarDatosClientes();
    });

    btnEliminar.addActionListener(e -> {
        int row = tableClientes.getSelectedRow();
        if (row != -1) {
            String nombre = (String) tableModelClientes.getValueAt(row, 0);
            String dni = String.valueOf(tableModelClientes.getValueAt(row, 1));
            int confirm = JOptionPane.showConfirmDialog(frame,
                    "¿Estás seguro de que quieres eliminar al cliente \"" + nombre + "\" con DNI " + dni + "?",
                    "Confirmar eliminación",
                    JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                clienteDAO.eliminar(Integer.parseInt(dni));
                cargarDatosClientes();
            }
        }
    });

    btnActualizar.addActionListener(e -> {
        int row = tableClientes.getSelectedRow();
        if (row != -1) {
            clienteDAO.actualizar(new Cliente(
                    Integer.parseInt(txtDniCliente.getText()),
                    txtNombreCliente.getText(),
                    txtTelefonoCliente.getText()
            ));
            cargarDatosClientes();
        }
    });

    // Al seleccionar fila, rellenar los campos
    tableClientes.getSelectionModel().addListSelectionListener(event -> {
        int row = tableClientes.getSelectedRow();
        if (row >= 0) {
            txtNombreCliente.setText((String) tableModelClientes.getValueAt(row, 0));
            txtDniCliente.setText(String.valueOf(tableModelClientes.getValueAt(row, 1)));
            txtTelefonoCliente.setText((String) tableModelClientes.getValueAt(row, 2));
        }
    });

    cargarDatosClientes();
    return panel;
}

// Cargar datos de la lista a la tabla
private void cargarDatosClientes() {
    listaClientes = clienteDAO.obtenerTodos();
    tableModelClientes.setRowCount(0);
    for (Cliente c : listaClientes) {
        tableModelClientes.addRow(new Object[]{c.getNombre(), c.getId(), c.getTelefono()});
    }
    txtNombreCliente.setText("");
    txtDniCliente.setText("");
    txtTelefonoCliente.setText("");
}

//panel para veterinario
private IVeterinarioDAO veterinarioDAO = new VeterinarioDAO();
private JTextField txtNombreVeterinario, txtEspecialidad, txtTelefonoVeterinario;
private JTable tableVeterinarios;
private DefaultTableModel tableModelVeterinarios;
private List<Veterinario> listaVeterinarios;
// Crear el panel de veterinario
private JPanel crearPanelVeterinario() {
    JPanel panel = new JPanel(new BorderLayout());

    listaVeterinarios = veterinarioDAO.obtenerTodos();

    // Creamos el formulario con 4 campos ahora
    JPanel form = new JPanel(new GridLayout(4, 2, 5, 5));
    txtNombreVeterinario = new JTextField();
    txtEspecialidad = new JTextField();
    txtTelefonoVeterinario = new JTextField();  // nuevo campo
    JButton btnAgregar = new JButton("Agregar");
    JButton btnEliminar = new JButton("Eliminar");
    JButton btnActualizar = new JButton("Actualizar");

    form.add(new JLabel("Nombre:"));
    form.add(txtNombreVeterinario);
    form.add(new JLabel("Especialidad:"));
    form.add(txtEspecialidad);
    form.add(new JLabel("Teléfono:")); // nuevo label
    form.add(txtTelefonoVeterinario);      // nuevo campo

    JPanel botones = new JPanel(new FlowLayout());
    botones.add(btnAgregar);
    botones.add(btnActualizar);
    botones.add(btnEliminar);
    form.add(botones);

    // Agregamos columna Teléfono a la tabla
    tableModelVeterinarios = new DefaultTableModel(new String[]{"Nombre", "Especialidad", "Teléfono"}, 0);
    tableVeterinarios = new JTable(tableModelVeterinarios);
    JScrollPane scroll = new JScrollPane(tableVeterinarios);

    panel.add(form, BorderLayout.NORTH);
    panel.add(scroll, BorderLayout.CENTER);

    btnAgregar.addActionListener(e -> {
        veterinarioDAO.insertar(new Veterinario(
                txtNombreVeterinario.getText(),
                txtEspecialidad.getText(),
                txtTelefonoVeterinario.getText()
        ));
        cargarDatosVeterinarios();
    });

    btnEliminar.addActionListener(e -> {
        int row = tableVeterinarios.getSelectedRow();
        if (row != -1) {
            String nombre = (String) tableModelVeterinarios.getValueAt(row, 0);
            int confirm = JOptionPane.showConfirmDialog(frame,
                    "¿Estás seguro de que quieres eliminar al veterinario \"" + nombre + "\"?",
                    "Confirmar eliminación",
                    JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                veterinarioDAO.eliminar(Integer.parseInt(String.valueOf(tableModelVeterinarios.getValueAt(row, 1))));
                cargarDatosVeterinarios();
            }
        }
    });

    btnActualizar.addActionListener(e -> {
        int row = tableVeterinarios.getSelectedRow();
        if (row != -1) {
            veterinarioDAO.actualizar(new Veterinario(
                    Integer.parseInt(String.valueOf(tableModelVeterinarios.getValueAt(row, 1))),
                    txtNombreVeterinario.getText(),
                    txtEspecialidad.getText(),
                    txtTelefonoVeterinario.getText()
            ));
            cargarDatosVeterinarios();
        }
    });

    // Al seleccionar fila, rellenar los campos
    tableVeterinarios.getSelectionModel().addListSelectionListener(event -> {
        int row = tableVeterinarios.getSelectedRow();
        if (row >= 0) {
            txtNombreVeterinario.setText((String) tableModelVeterinarios.getValueAt(row, 0));
            txtEspecialidad.setText((String) tableModelVeterinarios.getValueAt(row, 1));
            txtTelefonoVeterinario.setText((String) tableModelVeterinarios.getValueAt(row, 2));
        }
    });

    cargarDatosVeterinarios();
    return panel;
}
// Cargar datos de la lista a la tabla
private void cargarDatosVeterinarios() {
    listaVeterinarios = veterinarioDAO.obtenerTodos();
    tableModelVeterinarios.setRowCount(0);
    for (Veterinario v : listaVeterinarios) {
        tableModelVeterinarios.addRow(new Object[]{v.getNombre(), v.getEspecialidad(), v.getTelefono()});
    }
    txtNombreVeterinario.setText("");
    txtEspecialidad.setText("");
    txtTelefonoVeterinario.setText("");
}
//Panel para citas
private JPanel crearPanelCitas() {
    JPanel panel = new JPanel(new BorderLayout());

    // Tabla
    DefaultTableModel modelo = new DefaultTableModel(new String[]{"ID", "Fecha", "Hora", "Motivo", "ID Mascota", "ID Veterinario"}, 0);
    JTable tabla = new JTable(modelo);
    JScrollPane scroll = new JScrollPane(tabla);
    panel.add(scroll, BorderLayout.CENTER);

    // Panel de formulario
    JPanel formulario = new JPanel(new GridLayout(6, 2, 5, 5));
    JTextField txtFecha = new JTextField();
    JTextField txtHora = new JTextField();
    JTextField txtMotivo = new JTextField();
    JTextField txtIdMascota = new JTextField();
    JTextField txtIdVeterinario = new JTextField();

    formulario.add(new JLabel("Fecha (YYYY-MM-DD):"));
    formulario.add(txtFecha);
    formulario.add(new JLabel("Hora (HH:MM):"));
    formulario.add(txtHora);
    formulario.add(new JLabel("Motivo:"));
    formulario.add(txtMotivo);
    formulario.add(new JLabel("ID Mascota:"));
    formulario.add(txtIdMascota);
    formulario.add(new JLabel("ID Veterinario:"));
    formulario.add(txtIdVeterinario);

    JButton btnAgregar = new JButton("Agregar");
    JButton btnActualizar = new JButton("Actualizar");
    JButton btnEliminar = new JButton("Eliminar");

    formulario.add(btnAgregar);
    formulario.add(btnActualizar);
    formulario.add(btnEliminar);

    panel.add(formulario, BorderLayout.SOUTH);

    // DAO
    CitaDAO dao = new CitaDAO();

    // Cargar datos
    Runnable cargarDatos = () -> {
        modelo.setRowCount(0);
        for (Cita c : dao.obtenerTodas()) {
            modelo.addRow(new Object[]{
                c.getId(), c.getFecha(), c.getHora(), c.getMotivo(), c.getIdMascota(), c.getIdVeterinario()
            });
        }
    };
    cargarDatos.run();

    // Evento tabla: seleccionar fila
    tabla.getSelectionModel().addListSelectionListener(e -> {
        int fila = tabla.getSelectedRow();
        if (fila >= 0) {
            txtFecha.setText(modelo.getValueAt(fila, 1).toString());
            txtHora.setText(modelo.getValueAt(fila, 2).toString());
            txtMotivo.setText(modelo.getValueAt(fila, 3).toString());
            txtIdMascota.setText(modelo.getValueAt(fila, 4).toString());
            txtIdVeterinario.setText(modelo.getValueAt(fila, 5).toString());
        }
    });

    // Eventos botones
    btnAgregar.addActionListener(e -> {
        Cita c = new Cita(
            txtFecha.getText(), txtHora.getText(), txtMotivo.getText(),
            Integer.parseInt(txtIdMascota.getText()), Integer.parseInt(txtIdVeterinario.getText())
        );
        dao.insertar(c);
        cargarDatos.run();
    });

    btnActualizar.addActionListener(e -> {
        int fila = tabla.getSelectedRow();
        if (fila >= 0) {
            int id = (int) modelo.getValueAt(fila, 0);
            Cita c = new Cita(
                id, txtFecha.getText(), txtHora.getText(), txtMotivo.getText(),
                Integer.parseInt(txtIdMascota.getText()), Integer.parseInt(txtIdVeterinario.getText())
            );
            dao.actualizar(c);
            cargarDatos.run();
        }
    });

    btnEliminar.addActionListener(e -> {
        int fila = tabla.getSelectedRow();
        if (fila >= 0) {
            int id = (int) modelo.getValueAt(fila, 0);
            dao.eliminar(id);
            cargarDatos.run();
        }
    });

    return panel;
}


}