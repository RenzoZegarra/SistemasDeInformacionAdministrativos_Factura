package SistemasDeInformacionAdministrativos_Factura.Vista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import SistemasDeInformacionAdministrativos_Factura.Vista.FormularioVendedor;

public class FormularioVendedor extends JFrame {
    private String tipoEntidad;
    private JTextField txtID, txtNombres, txtApellidos, txtCorreo, txtNroDoc, txtDescripcion;
    private JComboBox<String> cmbTipoDoc;
    private JTable tabla;
    private DefaultTableModel modeloTabla;

    public FormularioVendedor(String tipoEntidad) {
        this.tipoEntidad = tipoEntidad;
        setTitle("Generación de Reporte: Vendedores");
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
        String[] entidades = {"Cliente", "Vendedor","Emisor","Productos","Contables","PtoVenta","Categoria","ModoPago"};

        for (String entidad : entidades) {
            JButton btn = new JButton(entidad);
            btn.addActionListener(e -> {
                dispose(); // cerrar ventana actual
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
        JPanel panelForm = new JPanel(new GridLayout(8, 2, 5, 5));

        txtID = new JTextField();
        txtNombres = new JTextField();
        txtApellidos = new JTextField();
        txtCorreo = new JTextField();
        txtNroDoc = new JTextField();
        txtDescripcion = new JTextField();
        cmbTipoDoc = new JComboBox<>(new String[]{"DNI", "Pasaporte", "Carnet Extranjería"});

        // Campos para "Vendedor"
        panelForm.add(new JLabel("ID Vendedor:"));
        panelForm.add(txtID);
        panelForm.add(new JLabel("Nombres:"));
        panelForm.add(txtNombres);
        panelForm.add(new JLabel("Apellidos:"));
        panelForm.add(txtApellidos);
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
        String[] columnas = {"ID Vendedor", "Nombres", "Apellidos", "Correo", "Descripción"};
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
            System.out.println("Volviendo al Menú Principal...");
            new InterfazPrincipal();
        });

        panelInferior.add(btnLimpiar);
        panelInferior.add(btnRegistrar);
        panelInferior.add(btnVolver);

        add(panelInferior, BorderLayout.SOUTH);
    }

    private void registrarDatos() {
        String[] fila = {
            txtID.getText(),
            txtNombres.getText(),
            txtApellidos.getText(),
            txtCorreo.getText(),
            txtDescripcion.getText()
        };

        modeloTabla.addRow(fila);
        limpiarCampos();
    }

    private void limpiarCampos() {
        txtID.setText("");
        txtNombres.setText("");
        txtApellidos.setText("");
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
            txtNombres.setText(modeloTabla.getValueAt(fila, 1).toString());
            txtApellidos.setText(modeloTabla.getValueAt(fila, 2).toString());
            txtCorreo.setText(modeloTabla.getValueAt(fila, 3).toString());
            txtDescripcion.setText(modeloTabla.getValueAt(fila, 4).toString());
        }
    }

    private void editarRegistro() {
        int fila = tabla.getSelectedRow();
        if (fila >= 0) {
            modeloTabla.setValueAt(txtID.getText(), fila, 0);
            modeloTabla.setValueAt(txtNombres.getText(), fila, 1);
            modeloTabla.setValueAt(txtApellidos.getText(), fila, 2);
            modeloTabla.setValueAt(txtCorreo.getText(), fila, 3);
            modeloTabla.setValueAt(txtDescripcion.getText(), fila, 4);
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
        SwingUtilities.invokeLater(() -> new FormularioVendedor("Vendedor"));
    }
}
