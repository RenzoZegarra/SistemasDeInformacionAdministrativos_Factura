package SistemasDeInformacionAdministrativos_Factura.Vista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;

public class TrsLibroDiario extends JFrame {
    private JTextField txtIDEntrada, txtIDAsientoContable, txtFecha, txtGlosa;
    private JTable tabla;
    private DefaultTableModel modeloTabla;

    public TrsLibroDiario() {
        setTitle("Registro en Libro Diario");
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
        panelForm.setBorder(BorderFactory.createTitledBorder("Datos del Libro Diario"));

        txtIDEntrada = new JTextField();
        txtIDAsientoContable = new JTextField();
        txtFecha = new JTextField();
        txtGlosa = new JTextField();

        panelForm.add(new JLabel("ID Entrada:"));
        panelForm.add(txtIDEntrada);
        panelForm.add(new JLabel("ID Asiento Contable:"));
        panelForm.add(txtIDAsientoContable);
        panelForm.add(new JLabel("Fecha (dd/mm/yyyy):"));
        panelForm.add(txtFecha);
        panelForm.add(new JLabel("Glosa:"));
        panelForm.add(txtGlosa);

        add(panelForm, BorderLayout.WEST);
    }

    private void agregarTabla() {
        String[] columnas = {"ID Entrada", "ID Asiento Contable", "Fecha", "Glosa"};
        modeloTabla = new DefaultTableModel(columnas, 0);
        tabla = new JTable(modeloTabla);
        JScrollPane scroll = new JScrollPane(tabla);
        add(scroll, BorderLayout.CENTER);
    }

    private void agregarPanelInferior() {
        JPanel panelInferior = new JPanel();

        JButton btnIngresar = new JButton("Ingresar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnVolver = new JButton("Regresar al Menú Principal");

        btnIngresar.addActionListener(e -> ingresarRegistro());
        btnEliminar.addActionListener(e -> eliminarRegistro());
        btnVolver.addActionListener(e -> {
            dispose();
            new InterfazPrincipal();
        });

        panelInferior.add(btnIngresar);
        panelInferior.add(btnEliminar);
        panelInferior.add(btnVolver);

        add(panelInferior, BorderLayout.SOUTH);
    }

    private void ingresarRegistro() {
        if (txtIDEntrada.getText().isEmpty() || txtIDAsientoContable.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor complete los campos obligatorios.");
            return;
        }

        String[] fila = {
            txtIDEntrada.getText(),
            txtIDAsientoContable.getText(),
            txtFecha.getText(),
            txtGlosa.getText()
        };

        modeloTabla.addRow(fila);
        limpiarCampos();
    }

    private void eliminarRegistro() {
        int fila = tabla.getSelectedRow();
        if (fila >= 0) {
            modeloTabla.removeRow(fila);
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un registro para eliminar.");
        }
    }

    private void limpiarCampos() {
        txtIDEntrada.setText("");
        txtIDAsientoContable.setText("");
        txtFecha.setText("");
        txtGlosa.setText("");
        tabla.clearSelection();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TrsLibroDiario::new);
    }
}
