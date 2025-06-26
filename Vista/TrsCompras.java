package SistemasDeInformacionAdministrativos_Factura.Vista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;

public class TrsCompras extends JFrame {
    private JTextField txtIDCompra, txtFecha, txtProveedor, txtModoPago;
    private JTextField txtBuscarID, txtTotal;
    private JTable tabla;
    private DefaultTableModel modeloTabla;

    public TrsCompras() {
        setTitle("Registro de Compra");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
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
        JPanel panelForm = new JPanel(new GridLayout(4, 2, 5, 5));
        panelForm.setBorder(BorderFactory.createTitledBorder("Datos de la Compra"));

        txtIDCompra = new JTextField();
        txtFecha = new JTextField();
        txtProveedor = new JTextField();
        txtModoPago = new JTextField();

        panelForm.add(new JLabel("ID Compra:"));
        panelForm.add(txtIDCompra);
        panelForm.add(new JLabel("Fecha (dd/mm/yyyy):"));
        panelForm.add(txtFecha);
        panelForm.add(new JLabel("Proveedor:"));
        panelForm.add(txtProveedor);
        panelForm.add(new JLabel("Modo de Pago:"));
        panelForm.add(txtModoPago);

        add(panelForm, BorderLayout.WEST);
    }

    private void agregarTabla() {
        String[] columnas = {"ID Compra", "Fecha", "Proveedor", "Modo de Pago"};
        modeloTabla = new DefaultTableModel(columnas, 0);
        tabla = new JTable(modeloTabla);
        JScrollPane scroll = new JScrollPane(tabla);
        add(scroll, BorderLayout.CENTER);
    }

    private void agregarPanelInferior() {
        JPanel panelInferior = new JPanel(new BorderLayout());

        // Botones de acción
        JPanel panelBotones = new JPanel();
        JButton btnRegistrar = new JButton("Registrar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnVolver = new JButton("Volver al Menú Principal");

        btnRegistrar.addActionListener(e -> registrarCompra());
        btnEliminar.addActionListener(e -> eliminarCompra());
        btnVolver.addActionListener(e -> {
            dispose();
            new InterfazPrincipal();
        });

        panelBotones.add(btnRegistrar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnVolver);

        // Panel derecho con búsqueda y total
        JPanel panelBuscarYTotal = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        txtBuscarID = new JTextField(10);
        JButton btnBuscar = new JButton("Buscar por ID");
        btnBuscar.addActionListener(e -> buscarCompra());

        txtTotal = new JTextField(10);
        txtTotal.setEditable(false);
        JButton btnCalcular = new JButton("Calcular Total");
        btnCalcular.addActionListener(e -> calcularTotal());

        panelBuscarYTotal.add(new JLabel("Buscar ID:"));
        panelBuscarYTotal.add(txtBuscarID);
        panelBuscarYTotal.add(btnBuscar);
        panelBuscarYTotal.add(Box.createHorizontalStrut(20));
        panelBuscarYTotal.add(new JLabel("Total Compras:"));
        panelBuscarYTotal.add(txtTotal);
        panelBuscarYTotal.add(btnCalcular);

        panelInferior.add(panelBotones, BorderLayout.WEST);
        panelInferior.add(panelBuscarYTotal, BorderLayout.EAST);

        add(panelInferior, BorderLayout.SOUTH);
    }

    private void registrarCompra() {
        if (txtIDCompra.getText().isEmpty() || txtProveedor.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor complete todos los campos obligatorios.");
            return;
        }

        String[] fila = {
            txtIDCompra.getText(),
            txtFecha.getText(),
            txtProveedor.getText(),
            txtModoPago.getText()
        };

        modeloTabla.addRow(fila);
        limpiarCampos();
    }

    private void eliminarCompra() {
        int fila = tabla.getSelectedRow();
        if (fila >= 0) {
            modeloTabla.removeRow(fila);
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione una fila para eliminar.");
        }
    }

    private void buscarCompra() {
        String idBuscar = txtBuscarID.getText().trim();
        for (int i = 0; i < modeloTabla.getRowCount(); i++) {
            if (modeloTabla.getValueAt(i, 0).toString().equalsIgnoreCase(idBuscar)) {
                tabla.setRowSelectionInterval(i, i);
                return;
            }
        }
        JOptionPane.showMessageDialog(this, "Compra no encontrada.");
    }

    private void calcularTotal() {
        int total = modeloTabla.getRowCount();
        txtTotal.setText(String.valueOf(total));
    }

    private void limpiarCampos() {
        txtIDCompra.setText("");
        txtFecha.setText("");
        txtProveedor.setText("");
        txtModoPago.setText("");
        tabla.clearSelection();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TrsCompras::new);
    }
}
