package SistemasDeInformacionAdministrativos_Factura.Vista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;

public class FormularioModoPago extends JFrame {

    private String tipoEntidad; // "Categoría"
    private JTextField txtIDPtoVenta, txtNombreCategoria, txtDescripcion;
    private JTable tabla;
    private DefaultTableModel modeloTabla;

    public FormularioModoPago(String tipoEntidad) {
        this.tipoEntidad = tipoEntidad;
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
        String[] entidades = {"Cliente", "Vendedor", "Emisor", "Productos", "Contables", "PtoVenta", "Categoria","ModoPago"};

        for (String entidad : entidades) {
            JButton btn = new JButton(entidad);
            btn.addActionListener(e -> {
                dispose();
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
        JPanel panelForm = new JPanel(new GridLayout(4, 2, 5, 5));

        txtIDPtoVenta = new JTextField();
        txtNombreCategoria = new JTextField();
        txtDescripcion = new JTextField();

        panelForm.add(new JLabel("ID Modo de Pago:"));
        panelForm.add(txtIDPtoVenta);
        panelForm.add(new JLabel("Nombre:"));
        panelForm.add(txtNombreCategoria);
        panelForm.add(new JLabel("Descripción:"));
        panelForm.add(txtDescripcion);

        add(panelForm, BorderLayout.WEST);
    }

    private void agregarTabla() {
        String[] columnas = {"ID Modo de Pago", "Nombre", "Descripción"};
        modeloTabla = new DefaultTableModel(columnas, 0);
        tabla = new JTable(modeloTabla);
        JScrollPane scroll = new JScrollPane(tabla);

        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabla.getSelectionModel().addListSelectionListener(e -> cargarDatosSeleccionados());

        JPanel panelTabla = new JPanel(new BorderLayout());
        panelTabla.add(scroll, BorderLayout.CENTER);

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
            txtIDPtoVenta.getText(),
            txtNombreCategoria.getText(),
            txtDescripcion.getText()
        };

        modeloTabla.addRow(fila);
        limpiarCampos();
    }

    private void limpiarCampos() {
        txtIDPtoVenta.setText("");
        txtNombreCategoria.setText("");
        txtDescripcion.setText("");
        tabla.clearSelection();
    }

    private void cargarDatosSeleccionados() {
        int fila = tabla.getSelectedRow();
        if (fila >= 0) {
            txtIDPtoVenta.setText(modeloTabla.getValueAt(fila, 0).toString());
            txtNombreCategoria.setText(modeloTabla.getValueAt(fila, 1).toString());
            txtDescripcion.setText(modeloTabla.getValueAt(fila, 2).toString());
        }
    }

    private void editarRegistro() {
        int fila = tabla.getSelectedRow();
        if (fila >= 0) {
            modeloTabla.setValueAt(txtIDPtoVenta.getText(), fila, 0);
            modeloTabla.setValueAt(txtNombreCategoria.getText(), fila, 1);
            modeloTabla.setValueAt(txtDescripcion.getText(), fila, 2);
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
        SwingUtilities.invokeLater(() -> new FormularioModoPago("Categoria"));
    }
}
