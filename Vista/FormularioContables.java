package SistemasDeInformacionAdministrativos_Factura.Vista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;

public class FormularioContables extends JFrame {

    private String tipoEntidad; // "Cuenta Contable"
    private JTextField txtIDCuentaContable, txtNombreCuenta, txtCodigoContable;
    private JComboBox<String> cmbTipoCuenta;
    private JTable tabla;
    private DefaultTableModel modeloTabla;

    public FormularioContables(String tipoEntidad) {
        this.tipoEntidad = tipoEntidad;
        // Actualiza el título para que diga "Cuenta Contable"
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
        String[] entidades = {"Cliente", "Vendedor", "Emisor", "Productos", "Contables","PtoVenta","Categoria","ModoPago"}; // Incluí Contables

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
        JPanel panelForm = new JPanel(new GridLayout(5, 2, 5, 5)); // 5 filas para los 4 campos

        txtIDCuentaContable = new JTextField();
        txtNombreCuenta = new JTextField();
        txtCodigoContable = new JTextField();
        cmbTipoCuenta = new JComboBox<>(new String[]{"Activo", "Pasivo", "Patrimonio", "Ingreso", "Gasto"});

        // Campos del formulario
        panelForm.add(new JLabel("ID Cuenta Contable:"));
        panelForm.add(txtIDCuentaContable);
        panelForm.add(new JLabel("Nombre de Cuenta:"));
        panelForm.add(txtNombreCuenta);
        panelForm.add(new JLabel("Código Contable:"));
        panelForm.add(txtCodigoContable);
        panelForm.add(new JLabel("Tipo de Cuenta:"));
        panelForm.add(cmbTipoCuenta);

        add(panelForm, BorderLayout.WEST);
    }

    private void agregarTabla() {
        // Añadí las columnas de Cuenta Contable
        String[] columnas = {"ID Cuenta Contable", "Nombre de Cuenta", "Código Contable", "Tipo de Cuenta"};
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
        // Registramos los datos de la Cuenta Contable
        String[] fila = {
            txtIDCuentaContable.getText(),
            txtNombreCuenta.getText(),
            txtCodigoContable.getText(),
            cmbTipoCuenta.getSelectedItem().toString()
        };

        modeloTabla.addRow(fila);
        limpiarCampos();
    }

    private void limpiarCampos() {
        txtIDCuentaContable.setText("");
        txtNombreCuenta.setText("");
        txtCodigoContable.setText("");
        cmbTipoCuenta.setSelectedIndex(0);
        tabla.clearSelection();
    }

    private void cargarDatosSeleccionados() {
        int fila = tabla.getSelectedRow();
        if (fila >= 0) {
            txtIDCuentaContable.setText(modeloTabla.getValueAt(fila, 0).toString());
            txtNombreCuenta.setText(modeloTabla.getValueAt(fila, 1).toString());
            txtCodigoContable.setText(modeloTabla.getValueAt(fila, 2).toString());
            cmbTipoCuenta.setSelectedItem(modeloTabla.getValueAt(fila, 3).toString());
        }
    }

    private void editarRegistro() {
        int fila = tabla.getSelectedRow();
        if (fila >= 0) {
            modeloTabla.setValueAt(txtIDCuentaContable.getText(), fila, 0);
            modeloTabla.setValueAt(txtNombreCuenta.getText(), fila, 1);
            modeloTabla.setValueAt(txtCodigoContable.getText(), fila, 2);
            modeloTabla.setValueAt(cmbTipoCuenta.getSelectedItem().toString(), fila, 3);
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
        SwingUtilities.invokeLater(() -> new FormularioContables("Contables"));
    }
}
