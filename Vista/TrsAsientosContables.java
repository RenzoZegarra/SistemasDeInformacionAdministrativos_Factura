package SistemasDeInformacionAdministrativos_Factura.Vista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;

public class TrsAsientosContables extends JFrame {
    private JTextField txtIDAsiento, txtCuenta, txtFecha, txtDescripcion;
    private JTextField txtDebe, txtHaber, txtCantidad, txtIDFacturaCompra;
    private JTable tabla;
    private DefaultTableModel modeloTabla;

    public TrsAsientosContables() {
        setTitle("Registro de Asientos Contables");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        agregarBotonesNavegacion();
        agregarFormulario();
        agregarTabla();
        agregarPanelInferior();

        setVisible(true);
    }

    private void agregarBotonesNavegacion() {
        JPanel panelNav = new JPanel(new FlowLayout(FlowLayout.LEFT));
        String[] modulos = {"Facturacion", "Compras", "AsientosContables", "LibroDiario"};

        for (String modulo : modulos) {
            JButton btn = new JButton(modulo);
            btn.addActionListener(e -> {
                dispose();
                switch (modulo) {
                    case "Facturacion": new TrsFactura(); break;
                    case "Compras": new TrsCompras(); break;
                    case "AsientosContables": new TrsAsientosContables(); break;
                    case "LibroDiario": new TrsLibroDiario(); break;
                }
            });
            panelNav.add(btn);
        }

        add(panelNav, BorderLayout.NORTH);
    }

    private void agregarFormulario() {
        JPanel panelForm = new JPanel(new GridLayout(4, 4, 5, 5));
        panelForm.setBorder(BorderFactory.createTitledBorder("Datos del Asiento Contable"));

        txtIDAsiento = new JTextField();
        txtCuenta = new JTextField();
        txtFecha = new JTextField();
        txtDescripcion = new JTextField();
        txtDebe = new JTextField();
        txtHaber = new JTextField();
        txtCantidad = new JTextField();
        txtIDFacturaCompra = new JTextField();

        panelForm.add(new JLabel("ID Asiento:"));
        panelForm.add(txtIDAsiento);
        panelForm.add(new JLabel("Cuenta Contable:"));
        panelForm.add(txtCuenta);
        panelForm.add(new JLabel("Fecha (dd/mm/yyyy):"));
        panelForm.add(txtFecha);
        panelForm.add(new JLabel("Descripción:"));
        panelForm.add(txtDescripcion);
        panelForm.add(new JLabel("Debe:"));
        panelForm.add(txtDebe);
        panelForm.add(new JLabel("Haber:"));
        panelForm.add(txtHaber);
        panelForm.add(new JLabel("Cantidad:"));
        panelForm.add(txtCantidad);
        panelForm.add(new JLabel("ID Factura/Compra:"));
        panelForm.add(txtIDFacturaCompra);

        add(panelForm, BorderLayout.WEST);
    }

    private void agregarTabla() {
        String[] columnas = {
            "ID Asiento", "Cuenta", "Fecha", "Descripción",
            "Debe", "Haber", "Cantidad", "ID Factura/Compra"
        };
        modeloTabla = new DefaultTableModel(columnas, 0);
        tabla = new JTable(modeloTabla);
        JScrollPane scroll = new JScrollPane(tabla);
        add(scroll, BorderLayout.CENTER);
    }

    private void agregarPanelInferior() {
        JPanel panelInferior = new JPanel();

        JButton btnAgregar = new JButton("Agregar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnVolver = new JButton("Volver al Menú Principal");

        btnAgregar.addActionListener(e -> agregarAsiento());
        btnEliminar.addActionListener(e -> eliminarAsiento());
        btnVolver.addActionListener(e -> {
            dispose();
            new InterfazPrincipal();
        });

        panelInferior.add(btnAgregar);
        panelInferior.add(btnEliminar);
        panelInferior.add(btnVolver);

        add(panelInferior, BorderLayout.SOUTH);
    }

    private void agregarAsiento() {
        if (txtIDAsiento.getText().isEmpty() || txtCuenta.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor complete los campos obligatorios.");
            return;
        }

        String[] fila = {
            txtIDAsiento.getText(), txtCuenta.getText(), txtFecha.getText(),
            txtDescripcion.getText(), txtDebe.getText(), txtHaber.getText(),
            txtCantidad.getText(), txtIDFacturaCompra.getText()
        };

        modeloTabla.addRow(fila);
        limpiarCampos();
    }

    private void eliminarAsiento() {
        int fila = tabla.getSelectedRow();
        if (fila >= 0) {
            modeloTabla.removeRow(fila);
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un registro para eliminar.");
        }
    }

    private void limpiarCampos() {
        txtIDAsiento.setText("");
        txtCuenta.setText("");
        txtFecha.setText("");
        txtDescripcion.setText("");
        txtDebe.setText("");
        txtHaber.setText("");
        txtCantidad.setText("");
        txtIDFacturaCompra.setText("");
        tabla.clearSelection();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TrsAsientosContables::new);
    }
}
