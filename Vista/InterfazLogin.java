package SistemasDeInformacionAdministrativos_Factura.Vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InterfazLogin 
{
    
    private JFrame frame;
    private JTextField userTextField;
    private JPasswordField passwordField;
    private JButton loginButton;
    
    public InterfazLogin() 
    {
        // Inicializamos la ventana principal
        frame = new JFrame("Inicio de Sesión");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);
        frame.setLayout(new BorderLayout());
        
        // Panel para los campos de usuario y contraseña
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2, 10, 10)); // Dos filas de campos
        
        // Etiquetas y campos
        JLabel userLabel = new JLabel("Usuario:");
        userTextField = new JTextField();
        JLabel passwordLabel = new JLabel("Contraseña:");
        passwordField = new JPasswordField();
        
        // Botón de iniciar sesión
        loginButton = new JButton("Iniciar sesión");
        
        // Agregamos los componentes al panel
        panel.add(userLabel);
        panel.add(userTextField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(new JLabel()); // Espacio vacío
        panel.add(loginButton);
        
        // Agregamos el panel a la ventana
        frame.add(panel, BorderLayout.CENTER);
        
        // Acción del botón
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleLogin();
            }
        });
        
        // Hacemos visible la ventana
        frame.setLocationRelativeTo(null); // Centra la ventana
        frame.setVisible(true);
    }
    
    // Método para manejar el inicio de sesión
    private void handleLogin() 
    {
        String usuario = userTextField.getText();
        String contrasena = new String(passwordField.getPassword());  // Convertir a String
        
        // Aquí podrías agregar la lógica para verificar el usuario y la contraseña
        if ("admin".equals(usuario) && "1234".equals(contrasena)) {
            JOptionPane.showMessageDialog(frame, "Inicio de sesión exitoso", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            // Aquí podrías abrir la siguiente interfaz o ventana.
        } 
        else 
        {
            JOptionPane.showMessageDialog(frame, "Usuario o contraseña incorrectos", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public static void main(String[] args) 
    {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() 
            {
                new InterfazLogin();
            }
        });
    }
}
