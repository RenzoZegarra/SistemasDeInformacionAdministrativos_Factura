package SistemasDeInformacionAdministrativos_Factura.Vista;

import SistemasDeInformacionAdministrativos_Factura.Vista.FormularioVendedor;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;

public class FormularioEmisor extends JFrame {

    private String tipoEntidad; // "Emisor"
    private JTextField txtID, txtNombres, txtApellidos, txtRUC, txtDireccion, txtTelefono, txtCorreo, txtNroDoc, txtDescripcion;
    private JComboBox<String> cmbTipoDoc;
    private JTable tabla;
    private DefaultTableModel modeloTabla;

    public FormularioEmisor(String tipoEntidad) {
        this.tipoEntidad = tipoEntidad;
        // Actualiza el título para que diga "Emisor"
        setTitle("Generación de Reporte: " + tipoEntidad + "s");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        agregarBotonesNavegacion();
        agregarFormulario();
        agregarTabla();
        agregarBotonesInferiores();

        setVisible(true);
    }

    private void agregarBotonesNavegacion() {
        JPanel panelNav = new JPanel(new FlowLayout(FlowLayout.LEFT));
        String[] entidades = {"Cliente", "Vendedor", "Emisor","Productos","Contables","PtoVenta","Categoria","ModoPago"};

        for (String entidad : entidades) {
            JButton btn = new JButton(entidad);
            btn.addActionListener(e -> {
                dispose(); // Cerrar ventana actual
                if (entidad.equals("Cliente")) {
                    new FormularioMaestro("Cliente");
                } else if (entidad.equals("Vendedor")) {
                    new FormularioVendedor("Vendedor");
                } else if (entidad.equals("Emisor")) {
                    new FormularioEmisor("Emisor");
                } else if (entidad.equals("Productos")) {
                    new FormularioProductos("Productos");
                } else if (entidad.equals("Contables")) {
                    new FormularioContables("Contables");
                } else if (entidad.equals("PtoVenta")) {
                    new FormularioPtoVenta("PtoVenta");
                } else if (entidad.equals("Categoria")){
                    new FormularioCategoria("Categoria");
                } else if(entidad.equals("ModoPago")){
                    new FormularioModoPago("ModoPago");
                }
            });
            panelNav.add(btn);
        }

        add(panelNav, BorderLayout.NORTH);
    }

    private void agregarFormulario() {
        JPanel panelForm = new JPanel(new GridLayout(11, 2, 5, 5)); // Cambié el número de filas a 11

        txtID = new JTextField();
        txtNombres = new JTextField();
        txtApellidos = new JTextField();
        txtRUC = new JTextField();
        txtDireccion = new JTextField();
        txtTelefono = new JTextField();
        txtCorreo = new JTextField();
        txtNroDoc = new JTextField();
        txtDescripcion = new JTextField();
        cmbTipoDoc = new JComboBox<>(new String[]{"DNI", "Pasaporte", "Carnet Extranjería"});

        // Cambiar el "ID" para que se ajuste a "ID Emisor"
        panelForm.add(new JLabel("ID " + tipoEntidad + ":"));
        panelForm.add(txtID);
        panelForm.add(new JLabel("Nombres:"));
        panelForm.add(txtNombres);
        panelForm.add(new JLabel("Apellidos:"));
        panelForm.add(txtApellidos);
        panelForm.add(new JLabel("RUC:"));
        panelForm.add(txtRUC);
        panelForm.add(new JLabel("Dirección Fiscal:"));  // Nuevo campo para Dirección Fiscal
        panelForm.add(txtDireccion);  // Asocia el campo de texto
        panelForm.add(new JLabel("Teléfono:"));
        panelForm.add(txtTelefono);
        panelForm.add(new JLabel("Correo:"));
        panelForm.add(txtCorreo);
        panelForm.add(new JLabel("Nro Documento:"));
        panelForm.add(txtNroDoc);
        panelForm.add(new JLabel("Descripción:"));
        panelForm.add(txtDescripcion);
        panelForm.add(new JLabel("Tipo de Documento:"));
        panelForm.add(cmbTipoDoc);

        add(panelForm, BorderLayout.WEST);
    }

    private void agregarTabla() {
        // Añadí la columna de Dirección Fiscal
        String[] columnas = {"Código", "RUC", "Descripción", "Dirección Fiscal"};
        modeloTabla = new DefaultTableModel(columnas, 0);
        tabla = new JTable(modeloTabla);
        JScrollPane scroll = new JScrollPane(tabla);

        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabla.getSelectionModel().addListSelectionListener(e -> cargarDatosSeleccionados());

        JPanel panelTabla = new JPanel(new BorderLayout());
        panelTabla.add(scroll, BorderLayout.CENTER);

        // Botones Editar / Eliminar
        JPanel panelBotones = new JPanel();
        JButton btnEditar = new JButton("Editar");
        JButton btnEliminar = new JButton("Eliminar");

        btnEditar.addActionListener(e -> editarRegistro());
        btnEliminar.addActionListener(e -> eliminarRegistro());

        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);

        panelTabla.add(panelBotones, BorderLayout.SOUTH);

        add(panelTabla, BorderLayout.CENTER);
    }

    private void agregarBotonesInferiores() {
        JPanel panelInferior = new JPanel();

        JButton btnLimpiar = new JButton("Limpiar Todo");
        JButton btnRegistrar = new JButton("Registrar");
        JButton btnVolver = new JButton("Volver Menú Principal");

        btnLimpiar.addActionListener(e -> limpiarCampos());
        btnRegistrar.addActionListener(e -> registrarDatos());

        btnVolver.addActionListener(e -> {
            dispose();
            // Aquí iría tu clase para volver al menú
            System.out.println("Volviendo al Menú Principal...");
            new InterfazPrincipal();
        });

        panelInferior.add(btnLimpiar);
        panelInferior.add(btnRegistrar);
        panelInferior.add(btnVolver);

        add(panelInferior, BorderLayout.SOUTH);
    }

    private void registrarDatos() {
        // Ahora registramos también la dirección fiscal
        String[] fila = {
            txtID.getText(),
            txtRUC.getText(),
            txtDescripcion.getText(),
            txtDireccion.getText() // Dirección fiscal
        };

        modeloTabla.addRow(fila);
        limpiarCampos();
    }

    private void limpiarCampos() {
        txtID.setText("");
        txtNombres.setText("");
        txtApellidos.setText("");
        txtRUC.setText("");
        txtDireccion.setText(""); // Limpiar también la dirección
        txtTelefono.setText("");
        txtCorreo.setText("");
        txtNroDoc.setText("");
        txtDescripcion.setText("");
        cmbTipoDoc.setSelectedIndex(0);
        tabla.clearSelection();
    }

    private void cargarDatosSeleccionados() {
        int fila = tabla.getSelectedRow();
        if (fila >= 0) {
            txtID.setText(modeloTabla.getValueAt(fila, 0).toString());
            txtRUC.setText(modeloTabla.getValueAt(fila, 1).toString());
            txtDescripcion.setText(modeloTabla.getValueAt(fila, 2).toString());
            txtDireccion.setText(modeloTabla.getValueAt(fila, 3).toString()); // Cargar dirección fiscal
        }
    }

    private void editarRegistro() {
        int fila = tabla.getSelectedRow();
        if (fila >= 0) {
            modeloTabla.setValueAt(txtID.getText(), fila, 0);
            modeloTabla.setValueAt(txtRUC.getText(), fila, 1);
            modeloTabla.setValueAt(txtDescripcion.getText(), fila, 2);
            modeloTabla.setValueAt(txtDireccion.getText(), fila, 3); // Editar la dirección fiscal
            limpiarCampos();
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un registro para editar.");
        }
    }

    private void eliminarRegistro() {
        int fila = tabla.getSelectedRow();
        if (fila >= 0) {
            modeloTabla.removeRow(fila);
            limpiarCampos();
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un registro para eliminar.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FormularioEmisor("Emisor"));
    }
}
