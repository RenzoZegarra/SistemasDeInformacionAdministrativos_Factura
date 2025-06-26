package SistemasDeInformacionAdministrativos_Factura.Vista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;

public class ReporteCompras extends JFrame {
    private JTextField txtDesde, txtHasta, txtProveedor;
    private JComboBox<String> comboModoPago;
    private JTable tabla;
    private DefaultTableModel modeloTabla;

    public ReporteCompras() {
        setTitle("Reporte de Compras");
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
        String[] modulos = {"ReporteVentas", "ReporteCompras", "LibroDiario"};

        for (String modulo : modulos) {
            JButton btn = new JButton(modulo);
            btn.addActionListener(e -> {
                dispose();
                switch (modulo) {
                    case "ReporteVentas": new ReporteVentas(); break;
                    case "ReporteCompras": new ReporteCompras(); break;
                    case "LibroDiario": new ReporteLibroDiario(); break;
                }
            });
            panelNav.add(btn);
        }

        add(panelNav, BorderLayout.NORTH);
    }

    private void agregarFormulario() {
        JPanel panelForm = new JPanel(new GridLayout(3, 2, 5, 5));
        panelForm.setBorder(BorderFactory.createTitledBorder("Datos del Reporte de Compras"));

        txtDesde = new JTextField();
        txtHasta = new JTextField();
        txtProveedor = new JTextField();
        comboModoPago = new JComboBox<>(new String[]{"Efectivo", "Tarjeta", "Transferencia", "Crédito"});

        panelForm.add(new JLabel("Desde (dd/mm/yyyy):"));
        panelForm.add(txtDesde);
        panelForm.add(new JLabel("Hasta (dd/mm/yyyy):"));
        panelForm.add(txtHasta);
        panelForm.add(new JLabel("Proveedor:"));
        panelForm.add(txtProveedor);
        panelForm.add(new JLabel("Modo de Pago:"));
        panelForm.add(comboModoPago);

        add(panelForm, BorderLayout.WEST);
    }

    private void agregarTabla() {
        String[] columnas = {"Desde", "Hasta", "Proveedor", "Modo de Pago"};
        modeloTabla = new DefaultTableModel(columnas, 0);
        tabla = new JTable(modeloTabla);
        JScrollPane scroll = new JScrollPane(tabla);
        add(scroll, BorderLayout.CENTER);
    }

    private void agregarPanelInferior() {
        JPanel panelInferior = new JPanel();

        JButton btnAgregar = new JButton("Agregar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnVolver = new JButton("Regresar al Menú Principal");

        btnAgregar.addActionListener(e -> agregarReporte());
        btnEliminar.addActionListener(e -> eliminarReporte());
        btnVolver.addActionListener(e -> {
            dispose();
            new InterfazPrincipal();
        });

        panelInferior.add(btnAgregar);
        panelInferior.add(btnEliminar);
        panelInferior.add(btnVolver);

        add(panelInferior, BorderLayout.SOUTH);
    }

    private void agregarReporte() {
        if (txtDesde.getText().isEmpty() || txtHasta.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Complete al menos las fechas para generar un reporte.");
            return;
        }

        String[] fila = {
            txtDesde.getText(),
            txtHasta.getText(),
            txtProveedor.getText(),
            comboModoPago.getSelectedItem().toString()
        };

        modeloTabla.addRow(fila);
        limpiarCampos();
    }

    private void eliminarReporte() {
        int fila = tabla.getSelectedRow();
        if (fila >= 0) {
            modeloTabla.removeRow(fila);
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un registro para eliminar.");
        }
    }

    private void limpiarCampos() {
        txtDesde.setText("");
        txtHasta.setText("");
        txtProveedor.setText("");
        comboModoPago.setSelectedIndex(0);
        tabla.clearSelection();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ReporteCompras::new);
    }
}
