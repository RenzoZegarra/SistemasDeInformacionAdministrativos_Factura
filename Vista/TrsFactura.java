package SistemasDeInformacionAdministrativos_Factura.Vista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;

public class TrsFactura extends JFrame {
    private JTextField txtCliente, txtVendedor, txtFecha, txtModoPago, txtIDFactura, txtDescuento;
    private JTextField txtNroFactura, txtPuntoVenta, txtEmisor, txtPrecioUnitario, txtSubtotal, txtIVA;
    private JTextField txtBuscarID, txtTotal;
    private JTable tabla;
    private DefaultTableModel modeloTabla;

    public TrsFactura() {
        setTitle("Registro de Factura");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
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
        JPanel panelForm = new JPanel(new GridLayout(7, 4, 5, 5));
        panelForm.setBorder(BorderFactory.createTitledBorder("Datos de la Factura"));

        txtCliente = new JTextField();
        txtVendedor = new JTextField();
        txtFecha = new JTextField();
        txtModoPago = new JTextField();
        txtIDFactura = new JTextField();
        txtDescuento = new JTextField();
        txtNroFactura = new JTextField();
        txtPuntoVenta = new JTextField();
        txtEmisor = new JTextField();
        txtPrecioUnitario = new JTextField();
        txtSubtotal = new JTextField();
        txtIVA = new JTextField();

        panelForm.add(new JLabel("Cliente:"));
        panelForm.add(txtCliente);
        panelForm.add(new JLabel("Vendedor:"));
        panelForm.add(txtVendedor);
        panelForm.add(new JLabel("Fecha (dd/mm/yyyy):"));
        panelForm.add(txtFecha);
        panelForm.add(new JLabel("Modo de Pago:"));
        panelForm.add(txtModoPago);
        panelForm.add(new JLabel("ID Factura:"));
        panelForm.add(txtIDFactura);
        panelForm.add(new JLabel("Descuento:"));
        panelForm.add(txtDescuento);
        panelForm.add(new JLabel("Nro de Factura:"));
        panelForm.add(txtNroFactura);
        panelForm.add(new JLabel("Punto de Venta:"));
        panelForm.add(txtPuntoVenta);
        panelForm.add(new JLabel("Emisor:"));
        panelForm.add(txtEmisor);
        panelForm.add(new JLabel("Precio Unitario:"));
        panelForm.add(txtPrecioUnitario);
        panelForm.add(new JLabel("Subtotal:"));
        panelForm.add(txtSubtotal);
        panelForm.add(new JLabel("IVA:"));
        panelForm.add(txtIVA);

        add(panelForm, BorderLayout.WEST);
    }

    private void agregarTabla() {
        String[] columnas = {"ID Factura", "Cliente", "Vendedor", "Fecha", "Modo Pago", "Nro Factura",
                "Pto Venta", "Emisor", "Precio Unitario", "Subtotal", "IVA", "Descuento"};
        modeloTabla = new DefaultTableModel(columnas, 0);
        tabla = new JTable(modeloTabla);
        JScrollPane scroll = new JScrollPane(tabla);
        add(scroll, BorderLayout.CENTER);
    }

    private void agregarPanelInferior() {
        JPanel panelInferior = new JPanel(new BorderLayout());

        // Botones de acción
        JPanel panelBotones = new JPanel();
        JButton btnAgregar = new JButton("Agregar Producto");
        JButton btnEliminar = new JButton("Eliminar Producto");
        JButton btnVolver = new JButton("Volver al Menú Principal");

        btnAgregar.addActionListener(e -> agregarFactura());
        btnEliminar.addActionListener(e -> eliminarFactura());
        btnVolver.addActionListener(e -> {
            dispose();
            new InterfazPrincipal();
        });

        panelBotones.add(btnAgregar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnVolver);

        // Panel derecho con búsqueda y total
        JPanel panelBuscarYTotal = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        txtBuscarID = new JTextField(10);
        JButton btnBuscar = new JButton("Buscar por ID");
        btnBuscar.addActionListener(e -> buscarFactura());

        txtTotal = new JTextField(10);
        txtTotal.setEditable(false);
        JButton btnCalcular = new JButton("Calcular Total");
        btnCalcular.addActionListener(e -> calcularTotal());

        panelBuscarYTotal.add(new JLabel("Buscar ID:"));
        panelBuscarYTotal.add(txtBuscarID);
        panelBuscarYTotal.add(btnBuscar);
        panelBuscarYTotal.add(Box.createHorizontalStrut(20));
        panelBuscarYTotal.add(new JLabel("Total:"));
        panelBuscarYTotal.add(txtTotal);
        panelBuscarYTotal.add(btnCalcular);

        panelInferior.add(panelBotones, BorderLayout.WEST);
        panelInferior.add(panelBuscarYTotal, BorderLayout.EAST);

        add(panelInferior, BorderLayout.SOUTH);
    }

    private void agregarFactura() {
        if (txtIDFactura.getText().isEmpty() || txtCliente.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor complete todos los campos requeridos.");
            return;
        }

        String[] fila = {
            txtIDFactura.getText(), txtCliente.getText(), txtVendedor.getText(), txtFecha.getText(),
            txtModoPago.getText(), txtNroFactura.getText(), txtPuntoVenta.getText(), txtEmisor.getText(),
            txtPrecioUnitario.getText(), txtSubtotal.getText(), txtIVA.getText(), txtDescuento.getText()
        };

        modeloTabla.addRow(fila);
        limpiarCampos();
    }

    private void eliminarFactura() {
        int fila = tabla.getSelectedRow();
        if (fila >= 0) {
            modeloTabla.removeRow(fila);
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione una fila para eliminar.");
        }
    }

    private void buscarFactura() {
        String idBuscar = txtBuscarID.getText().trim();
        for (int i = 0; i < modeloTabla.getRowCount(); i++) {
            if (modeloTabla.getValueAt(i, 0).toString().equalsIgnoreCase(idBuscar)) {
                tabla.setRowSelectionInterval(i, i);
                return;
            }
        }
        JOptionPane.showMessageDialog(this, "Factura no encontrada.");
    }

    private void calcularTotal() {
        double total = 0.0;
        for (int i = 0; i < modeloTabla.getRowCount(); i++) {
            try {
                double subtotal = Double.parseDouble(modeloTabla.getValueAt(i, 9).toString());
                double iva = Double.parseDouble(modeloTabla.getValueAt(i, 10).toString());
                double descuento = Double.parseDouble(modeloTabla.getValueAt(i, 11).toString());
                total += (subtotal + iva - descuento);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al calcular total en fila " + (i + 1));
            }
        }
        txtTotal.setText(String.format("%.2f", total));
    }

    private void limpiarCampos() {
        txtCliente.setText("");
        txtVendedor.setText("");
        txtFecha.setText("");
        txtModoPago.setText("");
        txtIDFactura.setText("");
        txtDescuento.setText("");
        txtNroFactura.setText("");
        txtPuntoVenta.setText("");
        txtEmisor.setText("");
        txtPrecioUnitario.setText("");
        txtSubtotal.setText("");
        txtIVA.setText("");
        tabla.clearSelection();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TrsFactura::new);
    }
}
