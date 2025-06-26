package SistemasDeInformacionAdministrativos_Factura.Vista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;

public class FormularioProductos extends JFrame {

    private String tipoEntidad; // "Producto"
    private JTextField txtIDProducto, txtNombre, txtPrecioUnitario, txtIDCategoria, txtStock, txtDescripcion;
    private JTable tabla;
    private DefaultTableModel modeloTabla;

    public FormularioProductos(String tipoEntidad) {
        this.tipoEntidad = tipoEntidad;
        // Actualiza el título para que diga "Producto"
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
        String[] entidades = {"Cliente", "Vendedor", "Emisor", "Productos","Contables","PtoVenta","Categoria","ModoPago"}; // Incluí Producto

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
        JPanel panelForm = new JPanel(new GridLayout(7, 2, 5, 5)); // 7 filas para los 6 campos

        txtIDProducto = new JTextField();
        txtNombre = new JTextField();
        txtPrecioUnitario = new JTextField();
        txtIDCategoria = new JTextField();
        txtStock = new JTextField();
        txtDescripcion = new JTextField();

        // Campos del formulario
        panelForm.add(new JLabel("ID Producto:"));
        panelForm.add(txtIDProducto);
        panelForm.add(new JLabel("Nombre:"));
        panelForm.add(txtNombre);
        panelForm.add(new JLabel("Precio Unitario:"));
        panelForm.add(txtPrecioUnitario);
        panelForm.add(new JLabel("ID Categoria:"));
        panelForm.add(txtIDCategoria);
        panelForm.add(new JLabel("Stock:"));
        panelForm.add(txtStock);
        panelForm.add(new JLabel("Descripción:"));
        panelForm.add(txtDescripcion);

        add(panelForm, BorderLayout.WEST);
    }

    private void agregarTabla() {
        // Añadí las columnas de Producto
        String[] columnas = {"ID Producto", "Nombre", "Precio Unitario", "ID Categoria", "Stock", "Descripción"};
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
        // Registramos los datos del Producto
        String[] fila = {
            txtIDProducto.getText(),
            txtNombre.getText(),
            txtPrecioUnitario.getText(),
            txtIDCategoria.getText(),
            txtStock.getText(),
            txtDescripcion.getText()
        };

        modeloTabla.addRow(fila);
        limpiarCampos();
    }

    private void limpiarCampos() {
        txtIDProducto.setText("");
        txtNombre.setText("");
        txtPrecioUnitario.setText("");
        txtIDCategoria.setText("");
        txtStock.setText("");
        txtDescripcion.setText("");
        tabla.clearSelection();
    }

    private void cargarDatosSeleccionados() {
        int fila = tabla.getSelectedRow();
        if (fila >= 0) {
            txtIDProducto.setText(modeloTabla.getValueAt(fila, 0).toString());
            txtNombre.setText(modeloTabla.getValueAt(fila, 1).toString());
            txtPrecioUnitario.setText(modeloTabla.getValueAt(fila, 2).toString());
            txtIDCategoria.setText(modeloTabla.getValueAt(fila, 3).toString());
            txtStock.setText(modeloTabla.getValueAt(fila, 4).toString());
            txtDescripcion.setText(modeloTabla.getValueAt(fila, 5).toString());
        }
    }

    private void editarRegistro() {
        int fila = tabla.getSelectedRow();
        if (fila >= 0) {
            modeloTabla.setValueAt(txtIDProducto.getText(), fila, 0);
            modeloTabla.setValueAt(txtNombre.getText(), fila, 1);
            modeloTabla.setValueAt(txtPrecioUnitario.getText(), fila, 2);
            modeloTabla.setValueAt(txtIDCategoria.getText(), fila, 3);
            modeloTabla.setValueAt(txtStock.getText(), fila, 4);
            modeloTabla.setValueAt(txtDescripcion.getText(), fila, 5);
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
        SwingUtilities.invokeLater(() -> new FormularioProductos("Producto"));
    }
}
