package SistemasDeInformacionAdministrativos_Factura.Vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class InterfazPrincipal 
{

    private JFrame frame;
    private JTextArea infoTextArea;
    private JButton btnGestionTablas, btnRegistroTransacciones, btnReportesContables, btnConfiguracion, btnCerrarSesion;

    public InterfazPrincipal() {
        // Inicializamos la ventana principal
        frame = new JFrame("Menú General: Sistema Contable");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new BorderLayout());
        
        // Panel para los botones
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 1, 10, 10)); // 5 botones verticales con un margen
        
        // Crear botones
        btnGestionTablas = new JButton("1. Gestión de Tablas Maestras");
        btnRegistroTransacciones = new JButton("2. Registro de Transacciones");
        btnReportesContables = new JButton("3. Reportes Contables");
        btnConfiguracion = new JButton("4. Configuración del sistema");
        btnCerrarSesion = new JButton("Cerrar sesión");

        // Añadir botones al panel
        buttonPanel.add(btnGestionTablas);
        buttonPanel.add(btnRegistroTransacciones);
        buttonPanel.add(btnReportesContables);
        buttonPanel.add(btnConfiguracion);
        buttonPanel.add(btnCerrarSesion);

        // Panel de información (Usuario, versión, universidad)
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BorderLayout());
        
        // Cuadro de texto con información del usuario
        infoTextArea = new JTextArea();
        infoTextArea.setText("Usuario: Admin\n- Versión 1.0\n- Universidad Católica de Santa María\n- Fecha 26/06/2025");
        infoTextArea.setEditable(false);
        infoTextArea.setBackground(new Color(240, 240, 240)); // Color suave de fondo
        infoTextArea.setFont(new Font("Arial", Font.PLAIN, 14));
        infoTextArea.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        // Agregar cuadro de texto al panel de información
        infoPanel.add(infoTextArea, BorderLayout.CENTER);

        // Añadir paneles a la ventana principal
        frame.add(buttonPanel, BorderLayout.CENTER);
        frame.add(infoPanel, BorderLayout.NORTH);

        btnConfiguracion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose(); // Cerramos la ventana actual
                new InterfazConfiguracionSistema(); // Abrimos la interfaz de configuración
            }
        });

        // Acción para abrir FormularioMaestro al hacer clic en Gestión de Tablas Maestras
        btnGestionTablas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose(); // Cierra la ventana actual
                new FormularioMaestro("Cliente"); // Abre el formulario maestro (puedes cambiar el tipo si es necesario)
            }
        });

         // Acción para abrir TrsFactura al hacer clic en Registro de Transacciones
        btnRegistroTransacciones.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose(); // Cierra la ventana actual
                new TrsFactura(); // Abre la interfaz de registro de transacciones
            }
        });

        // Acción para abrir ReporteVentas al hacer clic en Reportes Contables
        btnReportesContables.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose(); // Cierra la ventana actual
                new ReporteVentas(); // Abre la interfaz de ReporteVentas
            }
        });

        // Acción de cerrar sesión
        btnCerrarSesion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                cerrarSesion(); 
            }
        });

        // Hacemos visible la ventana
        frame.setLocationRelativeTo(null); // Centra la ventana
        frame.setVisible(true);
    }


    // Método para manejar el "Cerrar sesión" (regresa a la ventana de inicio de sesión)
    private void cerrarSesion() 
    {
        // Cerramos la ventana actual
        frame.dispose();
        
        // Abrimos la interfaz de inicio de sesión
        new InterfazLogin();
    }

    public static void main(String[] args) 
    {
        SwingUtilities.invokeLater(new Runnable() 
        {
            @Override
            public void run() 
            {
                new InterfazPrincipal();
            }
        });
    }
}
