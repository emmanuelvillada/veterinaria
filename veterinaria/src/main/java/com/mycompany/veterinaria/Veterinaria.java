package com.mycompany.veterinaria;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.awt.event.ActionEvent;

public class Veterinaria {
    private JFrame frame;

    // DAO
    private IMascotaDAO mascotaDAO = new MascotaDAO();
    private IClienteDAO clienteDAO = new ClienteDAO();
    private IServicioDAO servicioDAO = new ServicioDAO();
    private IConsultaDAO consultaDAO = new ConsultaDAO();
    private List<Consulta> listaConsultas;
    private JTextField txtIdMascotaConsulta, txtIdVeterinarioConsulta, txtFechaConsulta, txtMotivoConsulta;
    private JTable tableConsultas;
    private DefaultTableModel tableModelConsultas;

    private JTextField txtNombreServicio, txtCostoServicio;
    private JTable tableServicios;
    private DefaultTableModel tableModelServicios;
    private List<Servicio> listaServicios;

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
        frame.setBounds(100, 100, 1100, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JTabbedPane tabbedPane = new JTabbedPane();

        tabbedPane.addTab("Mascotas", crearPanelMascotas());
        tabbedPane.addTab("Clientes", crearPanelClientes());
        tabbedPane.addTab("Veterinarios", crearPanelVeterinario());
        tabbedPane.addTab("Citas", crearPanelCitas());
        tabbedPane.addTab("Historias Clínicas", crearPanelHistorias());
        tabbedPane.addTab("Tratamientos", crearPanelTratamientos());
        tabbedPane.addTab("Servicios", crearPanelServicios());
        tabbedPane.addTab("Consultas", crearPanelConsultas());
        tabbedPane.setFont(new Font("Arial", Font.PLAIN, 16));
        tabbedPane.setPreferredSize(new Dimension(800, 600));

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
        JButton btnGenerarPDF = new JButton("Generar PDF");

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
        botones.add(btnGenerarPDF);
        form.add(botones);

        tableModelMascotas = new DefaultTableModel(new String[] { "Nombre", "Especie", "Raza" }, 0);
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
                        txtRaza.getText()));
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

        btnGenerarPDF.addActionListener(e -> {
            List<Mascota> listaMascotas = mascotaDAO.obtenerTodas();
            if (!listaMascotas.isEmpty()) {
                for (Mascota mascota : listaMascotas) {
                    String nombreMascota = mascota.getNombre(); // Asumiendo que tienes un método getId() en Mascota
                    List<Consulta> consultas = consultaDAO.obtenerConsultasPorMascota(nombreMascota);

                    GeneradorPDF.generarHistoriaClinica(mascota, consultas,
                            "historia_clinica_" + mascota.getNombre() + ".pdf");
                    JOptionPane.showMessageDialog(null, "PDF generado exitosamente.");
                }
            }
        });

        cargarDatosMascotas();
        return panel;
    }

    private void cargarDatosMascotas() {
        listaMascotas = mascotaDAO.obtenerTodas();
        tableModelMascotas.setRowCount(0);
        for (Mascota m : listaMascotas) {
            tableModelMascotas.addRow(new Object[] { m.getNombre(), m.getEspecie(), m.getRaza() });
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
        txtTelefonoCliente = new JTextField(); // nuevo campo
        JButton btnAgregar = new JButton("Agregar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnActualizar = new JButton("Actualizar");

        form.add(new JLabel("Nombre:"));
        form.add(txtNombreCliente);
        form.add(new JLabel("Documento:"));
        form.add(txtDniCliente);
        form.add(new JLabel("Teléfono:")); // nuevo label
        form.add(txtTelefonoCliente); // nuevo campo

        JPanel botones = new JPanel(new FlowLayout());
        botones.add(btnAgregar);
        botones.add(btnActualizar);
        botones.add(btnEliminar);
        form.add(botones);

        // Agregamos columna Teléfono a la tabla
        tableModelClientes = new DefaultTableModel(new String[] { "Nombre", "DNI", "Teléfono" }, 0);
        tableClientes = new JTable(tableModelClientes);
        JScrollPane scroll = new JScrollPane(tableClientes);

        panel.add(form, BorderLayout.NORTH);
        panel.add(scroll, BorderLayout.CENTER);

        btnAgregar.addActionListener(e -> {
            clienteDAO.insertar(new Cliente(
                    // parsear a int el dni
                    Integer.parseInt(txtDniCliente.getText()),
                    txtNombreCliente.getText(),
                    // parsear a int el dni
                    txtTelefonoCliente.getText()));
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
                        txtTelefonoCliente.getText()));
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
            tableModelClientes.addRow(new Object[] { c.getNombre(), c.getId(), c.getTelefono() });
        }
        txtNombreCliente.setText("");
        txtDniCliente.setText("");
        txtTelefonoCliente.setText("");
    }

    // panel para veterinario
    private IVeterinarioDAO veterinarioDAO = new VeterinarioDAO();
    private JTextField txtNombreVeterinario, txtEspecialidad, txtTelefonoVeterinario;
    private JTable tableVeterinarios;
    private DefaultTableModel tableModelVeterinarios;
    private List<Veterinario> listaVeterinarios;

    private JPanel crearPanelVeterinario() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));

        listaVeterinarios = veterinarioDAO.obtenerTodos();

        // Formulario
        JPanel form = new JPanel(new GridLayout(4, 2, 5, 5));
        txtNombreVeterinario = new JTextField();
        txtEspecialidad = new JTextField();
        txtTelefonoVeterinario = new JTextField();

        form.setBorder(BorderFactory.createTitledBorder("Datos del Veterinario"));

        form.add(new JLabel("Nombre:"));
        form.add(txtNombreVeterinario);
        form.add(new JLabel("Especialidad:"));
        form.add(txtEspecialidad);
        form.add(new JLabel("Teléfono:"));
        form.add(txtTelefonoVeterinario);

        // Botones
        JPanel botones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        JButton btnAgregar = new JButton("Agregar");
        JButton btnActualizar = new JButton("Actualizar");
        JButton btnEliminar = new JButton("Eliminar");
        botones.add(btnAgregar);
        botones.add(btnActualizar);
        botones.add(btnEliminar);

        // Tabla
        tableModelVeterinarios = new DefaultTableModel(new String[] { "ID", "Nombre", "Especialidad", "Teléfono" }, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // para que no se puedan editar directamente
            }
        };
        tableVeterinarios = new JTable(tableModelVeterinarios);
        tableVeterinarios.getColumnModel().getColumn(0).setMinWidth(0);
        tableVeterinarios.getColumnModel().getColumn(0).setMaxWidth(0); // ocultar columna ID

        JScrollPane scroll = new JScrollPane(tableVeterinarios);

        // Estructura final
        JPanel top = new JPanel(new BorderLayout());
        top.add(form, BorderLayout.CENTER);
        top.add(botones, BorderLayout.SOUTH);

        panel.add(top, BorderLayout.NORTH);
        panel.add(scroll, BorderLayout.CENTER);

        // Eventos
        btnAgregar.addActionListener(e -> {
            veterinarioDAO.insertar(new Veterinario(
                    txtNombreVeterinario.getText(),
                    txtEspecialidad.getText(),
                    txtTelefonoVeterinario.getText()));
            cargarDatosVeterinarios();
        });

        btnEliminar.addActionListener(e -> {
            int row = tableVeterinarios.getSelectedRow();
            if (row != -1) {
                int id = (int) tableModelVeterinarios.getValueAt(row, 0);
                String nombre = (String) tableModelVeterinarios.getValueAt(row, 1);
                int confirm = JOptionPane.showConfirmDialog(frame,
                        "¿Eliminar al veterinario \"" + nombre + "\"?",
                        "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    veterinarioDAO.eliminar(id);
                    cargarDatosVeterinarios();
                }
            }
        });

        btnActualizar.addActionListener(e -> {
            int row = tableVeterinarios.getSelectedRow();
            if (row != -1) {
                int id = (int) tableModelVeterinarios.getValueAt(row, 0);
                veterinarioDAO.actualizar(new Veterinario(
                        id,
                        txtNombreVeterinario.getText(),
                        txtEspecialidad.getText(),
                        txtTelefonoVeterinario.getText()));
                cargarDatosVeterinarios();
            }
        });

        tableVeterinarios.getSelectionModel().addListSelectionListener(event -> {
            int row = tableVeterinarios.getSelectedRow();
            if (row >= 0) {
                txtNombreVeterinario.setText((String) tableModelVeterinarios.getValueAt(row, 1));
                txtEspecialidad.setText((String) tableModelVeterinarios.getValueAt(row, 2));
                txtTelefonoVeterinario.setText((String) tableModelVeterinarios.getValueAt(row, 3));
            }
        });

        cargarDatosVeterinarios();
        return panel;
    }

    private void cargarDatosVeterinarios() {
        listaVeterinarios = veterinarioDAO.obtenerTodos();
        tableModelVeterinarios.setRowCount(0);
        for (Veterinario v : listaVeterinarios) {
            tableModelVeterinarios
                    .addRow(new Object[] { v.getId(), v.getNombre(), v.getEspecialidad(), v.getTelefono() });
        }
        txtNombreVeterinario.setText("");
        txtEspecialidad.setText("");
        txtTelefonoVeterinario.setText("");
    }

    // Panel para citas
    private JPanel crearPanelCitas() {
        JPanel panel = new JPanel(new BorderLayout());

        // Tabla
        DefaultTableModel modelo = new DefaultTableModel(
                new String[] { "ID", "Fecha", "Hora", "Motivo", "ID Mascota", "ID Veterinario" }, 0);
        JTable tabla = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tabla);
        panel.add(scroll, BorderLayout.CENTER);

        // Panel de formulario
        JPanel formulario = new JPanel(new GridLayout(4, 4, 10, 5));
        JTextField txtFecha = new JTextField(10);
        JTextField txtHora = new JTextField(10);
        JTextField txtMotivo = new JTextField(10);
        JTextField txtIdMascota = new JTextField(10);
        JTextField txtIdVeterinario = new JTextField(10);

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
        formulario.add(new JLabel("")); // Espacio vacío
        formulario.add(new JLabel("")); // Espacio vacío

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
                modelo.addRow(new Object[] {
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

        // Botón Agregar
        btnAgregar.addActionListener(e -> {
            Cita c = new Cita(
                    txtFecha.getText(),
                    txtHora.getText(),
                    txtMotivo.getText(),
                    Integer.parseInt(txtIdMascota.getText()),
                    Integer.parseInt(txtIdVeterinario.getText()));
            dao.insertar(c);
            cargarDatos.run();
        });

        // Botón Actualizar
        btnActualizar.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila >= 0) {
                int id = (int) modelo.getValueAt(fila, 0);
                Cita c = new Cita(
                        id,
                        txtFecha.getText(),
                        txtHora.getText(),
                        txtMotivo.getText(),
                        Integer.parseInt(txtIdMascota.getText()),
                        Integer.parseInt(txtIdVeterinario.getText()));
                dao.actualizar(c);
                cargarDatos.run();
            }
        });

        // Botón Eliminar
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

    // panel para historia clinica

    private JPanel crearPanelHistorias() {
        JPanel panel = new JPanel(new BorderLayout());

        // Tabla
        DefaultTableModel modelo = new DefaultTableModel(
                new String[] { "ID", "Fecha", "Diagnóstico", "Tratamiento", "ID Mascota" }, 0);
        JTable tabla = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tabla);
        panel.add(scroll, BorderLayout.CENTER);

        // Panel de formulario
        JPanel formulario = new JPanel(new GridLayout(5, 2, 10, 10));
        JTextField txtFecha = new JTextField(10);
        JTextField txtDiagnostico = new JTextField(10);
        JTextField txtTratamiento = new JTextField(10);
        JTextField txtIdMascota = new JTextField(10);

        formulario.add(new JLabel("Fecha (YYYY-MM-DD):"));
        formulario.add(txtFecha);
        formulario.add(new JLabel("")); // Espacio vacío
        formulario.add(new JLabel("Diagnóstico:"));
        formulario.add(txtDiagnostico);
        formulario.add(new JLabel("")); // Espacio vacío
        formulario.add(new JLabel("Tratamiento:"));
        formulario.add(txtTratamiento);
        formulario.add(new JLabel("")); // Espacio vacío
        formulario.add(new JLabel("ID Mascota:"));
        formulario.add(txtIdMascota);
        formulario.add(new JLabel("")); // Espacio vacío

        JButton btnAgregar = new JButton("Agregar");
        JButton btnActualizar = new JButton("Actualizar");
        JButton btnEliminar = new JButton("Eliminar");

        formulario.add(btnAgregar);
        formulario.add(btnActualizar);
        formulario.add(btnEliminar);

        panel.add(formulario, BorderLayout.SOUTH);

        // DAO
        IHistoriaClinicaDAO dao = new HistoriaClinicaDAO();

        // Cargar datos
        Runnable cargarDatos = () -> {
            modelo.setRowCount(0);
            for (HistoriaCLinica h : dao.obtenerHistoriasClinicas()) {
                modelo.addRow(new Object[] {
                        h.getId(), h.getFecha(), h.getDiagnostico(), h.getTratamiento(), h.getIdMascota()
                });
            }
        };
        cargarDatos.run();

        // Evento tabla
        tabla.getSelectionModel().addListSelectionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila >= 0) {
                txtFecha.setText(modelo.getValueAt(fila, 1).toString());
                txtDiagnostico.setText(modelo.getValueAt(fila, 2).toString());
                txtTratamiento.setText(modelo.getValueAt(fila, 3).toString());
                txtIdMascota.setText(modelo.getValueAt(fila, 4).toString());
            }
        });

        // Eventos botones
        btnAgregar.addActionListener(e -> {
            HistoriaCLinica h = new HistoriaCLinica(
                    txtFecha.getText(), txtDiagnostico.getText(), txtTratamiento.getText(),
                    Integer.parseInt(txtIdMascota.getText()));
            dao.agregarHistoriaClinica(h);
            cargarDatos.run();
        });

        btnActualizar.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila >= 0) {
                int id = (int) modelo.getValueAt(fila, 0);
                HistoriaCLinica h = new HistoriaCLinica(
                        id, txtFecha.getText(), txtDiagnostico.getText(), txtTratamiento.getText(),
                        Integer.parseInt(txtIdMascota.getText()));
                dao.actualizarHistoriaClinica(h);
                cargarDatos.run();
            }
        });

        btnEliminar.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila >= 0) {
                int id = (int) modelo.getValueAt(fila, 0);
                dao.eliminarHistoriaClinica(id);
                cargarDatos.run();
            }
        });

        return panel;
    }
    // Panel para tratamiento

    private JPanel crearPanelTratamientos() {
        JPanel panel = new JPanel(new BorderLayout());

        // Tabla
        DefaultTableModel modelo = new DefaultTableModel(new String[] { "ID", "Nombre", "Costo" }, 0);
        JTable tabla = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tabla);
        panel.add(scroll, BorderLayout.CENTER);

        // Formulario
        JPanel formulario = new JPanel(new GridLayout(2, 2, 5, 5));
        JTextField txtNombre = new JTextField(10);
        JTextField txtCosto = new JTextField(10);

        formulario.add(new JLabel("Nombre:"));
        formulario.add(txtNombre);
        formulario.add(new JLabel("Costo:"));
        formulario.add(txtCosto);

        JButton btnAgregar = new JButton("Agregar");
        JButton btnActualizar = new JButton("Actualizar");
        JButton btnEliminar = new JButton("Eliminar");

        formulario.add(btnAgregar);
        formulario.add(btnActualizar);
        formulario.add(btnEliminar);

        panel.add(formulario, BorderLayout.SOUTH);

        // DAO
        TratamientoDAO dao = new TratamientoDAO();

        // Cargar datos
        Runnable cargarDatos = () -> {
            modelo.setRowCount(0);
            for (Tratamiento t : dao.obtenerTodos()) {
                modelo.addRow(new Object[] { t.getId(), t.getNombre(), t.getCosto() });
            }
        };
        cargarDatos.run();

        // Evento tabla
        tabla.getSelectionModel().addListSelectionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila >= 0) {
                txtNombre.setText(modelo.getValueAt(fila, 1).toString());
                txtCosto.setText(modelo.getValueAt(fila, 2).toString());
            }
        });

        // Botones
        btnAgregar.addActionListener(e -> {
            Tratamiento t = new Tratamiento(txtNombre.getText(), Double.parseDouble(txtCosto.getText()));
            dao.insertar(t);
            cargarDatos.run();
        });

        btnActualizar.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila >= 0) {
                int id = (int) modelo.getValueAt(fila, 0);
                Tratamiento t = new Tratamiento(id, txtNombre.getText(), Double.parseDouble(txtCosto.getText()));
                dao.actualizar(t);
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

    private JPanel crearPanelServicios() {
        JPanel panel = new JPanel(new BorderLayout());

        // Tabla y modelo
        tableModelServicios = new DefaultTableModel(new String[] { "ID", "Nombre", "Costo" }, 0);
        tableServicios = new JTable(tableModelServicios);
        JScrollPane scroll = new JScrollPane(tableServicios);
        panel.add(scroll, BorderLayout.CENTER);

        // Formulario
        JPanel formulario = new JPanel(new GridLayout(2, 2, 5, 5));
        txtNombreServicio = new JTextField(10);
        txtCostoServicio = new JTextField(10);

        formulario.add(new JLabel("Nombre del servicio:"));
        formulario.add(txtNombreServicio);
        formulario.add(new JLabel("Costo:"));
        formulario.add(txtCostoServicio);

        // Botones
        JButton btnAgregar = new JButton("Agregar");
        JButton btnActualizar = new JButton("Actualizar");
        JButton btnEliminar = new JButton("Eliminar");

        JPanel botones = new JPanel(new FlowLayout());
        botones.add(btnAgregar);
        botones.add(btnActualizar);
        botones.add(btnEliminar);

        // Panel inferior
        JPanel sur = new JPanel(new BorderLayout());
        sur.add(formulario, BorderLayout.CENTER);
        sur.add(botones, BorderLayout.SOUTH);

        panel.add(sur, BorderLayout.SOUTH);

        // DAO
        servicioDAO = new ServicioDAO(); // Asegúrate de inicializarlo si no lo tienes arriba

        // Cargar datos
        cargarDatosServicios();

        // Eventos tabla
        tableServicios.getSelectionModel().addListSelectionListener(e -> {
            int row = tableServicios.getSelectedRow();
            if (row >= 0) {
                txtNombreServicio.setText(tableModelServicios.getValueAt(row, 1).toString());
                txtCostoServicio.setText(tableModelServicios.getValueAt(row, 2).toString());
            }
        });

        // Botones
        btnAgregar.addActionListener(e -> {
            try {
                Servicio s = new Servicio(txtNombreServicio.getText(), Double.parseDouble(txtCostoServicio.getText()));
                servicioDAO.insertar(s);
                cargarDatosServicios();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(panel, "El costo debe ser un número.");
            }
        });

        btnActualizar.addActionListener(e -> {
            int row = tableServicios.getSelectedRow();
            if (row >= 0) {
                try {
                    int id = (int) tableModelServicios.getValueAt(row, 0);
                    Servicio s = new Servicio(id, txtNombreServicio.getText(),
                            Double.parseDouble(txtCostoServicio.getText()));
                    servicioDAO.actualizar(s);
                    cargarDatosServicios();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(panel, "El costo debe ser un número.");
                }
            }
        });

        btnEliminar.addActionListener(e -> {
            int row = tableServicios.getSelectedRow();
            if (row >= 0) {
                int id = (int) tableModelServicios.getValueAt(row, 0);
                servicioDAO.eliminar(id);
                cargarDatosServicios();
            }
        });

        return panel;
    }

    private void cargarDatosServicios() {
        listaServicios = servicioDAO.obtenerTodos();
        tableModelServicios.setRowCount(0);
        for (Servicio s : listaServicios) {
            tableModelServicios.addRow(new Object[] { s.getId(), s.getNombre(), s.getCosto() });
        }
        txtNombreServicio.setText("");
        txtCostoServicio.setText("");
    }

    private JPanel crearPanelConsultas() {
        JPanel panel = new JPanel(new BorderLayout());

        // Tabla y modelo
        tableModelConsultas = new DefaultTableModel(new String[] { "ID", "Mascota", "Veterinario", "Fecha", "Motivo" },
                0);
        tableConsultas = new JTable(tableModelConsultas);
        JScrollPane scroll = new JScrollPane(tableConsultas);
        panel.add(scroll, BorderLayout.CENTER);

        // Formulario
        JPanel formulario = new JPanel(new GridLayout(4, 2, 5, 5));
        txtIdMascotaConsulta = new JTextField(10);
        txtIdVeterinarioConsulta = new JTextField(10);
        txtFechaConsulta = new JTextField(10);
        txtMotivoConsulta = new JTextField(10);

        formulario.add(new JLabel("ID Mascota:"));
        formulario.add(txtIdMascotaConsulta);
        formulario.add(new JLabel("ID Veterinario:"));
        formulario.add(txtIdVeterinarioConsulta);
        formulario.add(new JLabel("Fecha (YYYY-MM-DD):"));
        formulario.add(txtFechaConsulta);
        formulario.add(new JLabel("Motivo:"));
        formulario.add(txtMotivoConsulta);

        // Botones
        JButton btnAgregar = new JButton("Agregar");
        JButton btnActualizar = new JButton("Actualizar");
        JButton btnEliminar = new JButton("Eliminar");

        JPanel botones = new JPanel(new FlowLayout());
        botones.add(btnAgregar);
        botones.add(btnActualizar);
        botones.add(btnEliminar);

        // Panel inferior que junta formulario y botones
        JPanel sur = new JPanel(new BorderLayout());
        sur.add(formulario, BorderLayout.CENTER);
        sur.add(botones, BorderLayout.SOUTH);
        panel.add(sur, BorderLayout.SOUTH);

        // DAO
        consultaDAO = new ConsultaDAO(); // Asegúrate que esté inicializado

        // Cargar datos iniciales
        cargarDatosConsultas();

        // Acciones de los botones
        btnAgregar.addActionListener(e -> {
            try {
                Consulta c = new Consulta(
                        Integer.parseInt(txtIdMascotaConsulta.getText()),
                        Integer.parseInt(txtIdVeterinarioConsulta.getText()),
                        txtFechaConsulta.getText(),
                        txtMotivoConsulta.getText());
                consultaDAO.insertar(c);
                cargarDatosConsultas();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(panel, "Los campos ID deben ser números.");
            }
        });

        btnActualizar.addActionListener(e -> {
            int row = tableConsultas.getSelectedRow();
            if (row >= 0) {
                try {
                    int id = (int) tableModelConsultas.getValueAt(row, 0);
                    Consulta c = new Consulta(
                            id,
                            Integer.parseInt(txtIdMascotaConsulta.getText()),
                            Integer.parseInt(txtIdVeterinarioConsulta.getText()),
                            txtFechaConsulta.getText(),
                            txtMotivoConsulta.getText());
                    consultaDAO.actualizar(c);
                    cargarDatosConsultas();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(panel, "Los campos ID deben ser números.");
                }
            }
        });

        btnEliminar.addActionListener(e -> {
            int row = tableConsultas.getSelectedRow();
            if (row >= 0) {
                int id = (int) tableModelConsultas.getValueAt(row, 0);
                consultaDAO.eliminar(id);
                cargarDatosConsultas();
            }
        });

        // Selección en la tabla
        tableConsultas.getSelectionModel().addListSelectionListener(e -> {
            int row = tableConsultas.getSelectedRow();
            if (row >= 0) {
                txtIdMascotaConsulta.setText(tableModelConsultas.getValueAt(row, 1).toString());
                txtIdVeterinarioConsulta.setText(tableModelConsultas.getValueAt(row, 2).toString());
                txtFechaConsulta.setText(tableModelConsultas.getValueAt(row, 3).toString());
                txtMotivoConsulta.setText(tableModelConsultas.getValueAt(row, 4).toString());
            }
        });

        return panel;
    }

    private void cargarDatosConsultas() {
        listaConsultas = consultaDAO.obtenerTodas();
        tableModelConsultas.setRowCount(0);
        for (Consulta c : listaConsultas) {
            tableModelConsultas.addRow(new Object[] {
                    c.getId(),
                    c.getIdMascota(),
                    c.getIdVeterinario(),
                    c.getFecha(),
                    c.getMotivo()
            });
        }
        txtIdMascotaConsulta.setText("");
        txtIdVeterinarioConsulta.setText("");
        txtFechaConsulta.setText("");
        txtMotivoConsulta.setText("");
    }

}