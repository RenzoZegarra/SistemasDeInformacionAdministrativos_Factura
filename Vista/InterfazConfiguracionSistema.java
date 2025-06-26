package SistemasDeInformacionAdministrativos_Factura.Vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class InterfazConfiguracionSistema {

    private JFrame frame;
    private JTextField nombreSistemaField, rucField, direccionField, impuestoField;
    private JTextArea descripcionArea;
    private JComboBox<String> monedaCombo;
    private JSpinner fechaSpinner;
    private JList<String> usuarioList;
    private DefaultListModel<String> usuarioListModel;

    public InterfazConfiguracionSistema() {
        frame = new JFrame("Configuración del Sistema");
        frame.setSize(600, 500);
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Panel principal
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(10, 2, 10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Nombre del sistema
        mainPanel.add(new JLabel("Nombre del Sistema:"));
        nombreSistemaField = new JTextField();
        mainPanel.add(nombreSistemaField);

        // Descripción
        mainPanel.add(new JLabel("Descripción:"));
        descripcionArea = new JTextArea(2, 20);
        mainPanel.add(new JScrollPane(descripcionArea));

        // RUC
        mainPanel.add(new JLabel("RUC:"));
        rucField = new JTextField();
        mainPanel.add(rucField);

        // Dirección
        mainPanel.add(new JLabel("Dirección:"));
        direccionField = new JTextField();
        mainPanel.add(direccionField);

        // Moneda
        mainPanel.add(new JLabel("Moneda:"));
        String[] monedas = {"USD", "PEN", "EUR", "CLP"};
        monedaCombo = new JComboBox<>(monedas);
        mainPanel.add(monedaCombo);

        // Fecha
        mainPanel.add(new JLabel("Fecha:"));
        fechaSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(fechaSpinner, "dd/MM/yyyy");
        fechaSpinner.setEditor(dateEditor);
        mainPanel.add(fechaSpinner);

        // Impuesto
        mainPanel.add(new JLabel("Porcentaje de Impuestos (%):"));
        impuestoField = new JTextField();
        mainPanel.add(impuestoField);

        // Lista de usuarios
        mainPanel.add(new JLabel("Usuarios:"));
        usuarioListModel = new DefaultListModel<>();
        usuarioListModel.addElement("Admin");
        usuarioListModel.addElement("Usuario1");
        usuarioList = new JList<>(usuarioListModel);
        JScrollPane usuarioScroll = new JScrollPane(usuarioList);
        mainPanel.add(usuarioScroll);

        // Botón editar usuario
        mainPanel.add(new JLabel());
        JButton editarUsuarioBtn = new JButton("Editar Usuario");
        mainPanel.add(editarUsuarioBtn);

        // Botones inferiores
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout());

        JButton volverBtn = new JButton("Volver al Menú principal");
        JButton crearUsuarioBtn = new JButton("Crear Usuario");
        JButton guardarBtn = new JButton("Guardar Cambios");

        bottomPanel.add(volverBtn);
        bottomPanel.add(crearUsuarioBtn);
        bottomPanel.add(guardarBtn);

        // Añadir paneles a la ventana
        frame.add(mainPanel, BorderLayout.CENTER);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        // Acción de "Volver"
        volverBtn.addActionListener(e -> {
            frame.dispose();
            new InterfazPrincipal();
        });

        // Acción de "Crear Usuario"
        crearUsuarioBtn.addActionListener(e -> {
            String nuevoUsuario = JOptionPane.showInputDialog(frame, "Ingrese nombre del nuevo usuario:");
            if (nuevoUsuario != null && !nuevoUsuario.trim().isEmpty()) {
                usuarioListModel.addElement(nuevoUsuario.trim());
            }
        });

        // Acción de "Editar Usuario"
        editarUsuarioBtn.addActionListener(e -> {
            String seleccionado = usuarioList.getSelectedValue();
            if (seleccionado != null) {
                String nuevoNombre = JOptionPane.showInputDialog(frame, "Editar nombre del usuario:", seleccionado);
                if (nuevoNombre != null && !nuevoNombre.trim().isEmpty()) {
                    int index = usuarioList.getSelectedIndex();
                    usuarioListModel.setElementAt(nuevoNombre.trim(), index);
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Seleccione un usuario para editar.");
            }
        });

        // Acción de "Guardar Cambios"
        guardarBtn.addActionListener(e -> {
            String nombreSistema = nombreSistemaField.getText();
            String descripcion = descripcionArea.getText();
            String ruc = rucField.getText();
            String direccion = direccionField.getText();
            String moneda = (String) monedaCombo.getSelectedItem();
            String fecha = dateEditor.getFormat().format(fechaSpinner.getValue());
            String impuesto = impuestoField.getText();

            // Aquí puedes guardar los datos en un archivo o mostrar por consola
            System.out.println("=== Datos Guardados ===");
            System.out.println("Nombre del Sistema: " + nombreSistema);
            System.out.println("Descripción: " + descripcion);
            System.out.println("RUC: " + ruc);
            System.out.println("Dirección: " + direccion);
            System.out.println("Moneda: " + moneda);
            System.out.println("Fecha: " + fecha);
            System.out.println("Impuestos: " + impuesto + "%");
            System.out.println("Usuarios: " + java.util.Collections.list(usuarioListModel.elements()));

            JOptionPane.showMessageDialog(frame, "Cambios guardados correctamente.");
        });

        frame.setLocationRelativeTo(null); // Centrado
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(InterfazConfiguracionSistema::new);
    }
}
