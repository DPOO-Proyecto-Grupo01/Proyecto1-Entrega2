package InterfazProfesor;

import javax.swing.*;
import Consola.ConsolaProfesor;
import java.awt.*;

public class IniciarSesion extends JFrame {

    public IniciarSesion() {
    	
    	ConsolaProfesor.cargarActividades();
        ConsolaProfesor.cargarLearningPaths();
        ConsolaProfesor.cargarProgresos();
        ConsolaProfesor.cargarProgresoLP();
        ConsolaProfesor.cargarProfesores();
        ConsolaProfesor.cargarLpProfesoress();
        ConsolaProfesor.cargarActividadesLP();
        ConsolaProfesor.cargarEstudiantes();
        
        setTitle("Inicio de Sesión - Profesor");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel panelCentral = new JPanel(new GridLayout(3, 2, 10, 10));

        JLabel lblUsuario = new JLabel("Usuario:");
        JTextField txtUsuario = new JTextField();
        JLabel lblContrasena = new JLabel("Contraseña:");
        JPasswordField txtContrasena = new JPasswordField();
        JButton btnIniciarSesion = new JButton("Iniciar Sesión");

        panelCentral.add(lblUsuario);
        panelCentral.add(txtUsuario);
        panelCentral.add(lblContrasena);
        panelCentral.add(txtContrasena);
        add(panelCentral, BorderLayout.CENTER);

        JPanel panelInferior = new JPanel();
        JButton btnRegistrar = new JButton("Registrar");
        panelInferior.add(btnIniciarSesion);
        panelInferior.add(btnRegistrar);
        add(panelInferior, BorderLayout.SOUTH);

        btnIniciarSesion.addActionListener(e -> {
            String usuario = txtUsuario.getText();
            String contrasena = new String(txtContrasena.getPassword());

            if (autenticarUsuario(usuario, contrasena)) {
                dispose();
                SwingUtilities.invokeLater(() -> {
                    InterfazProfesor interfaz = new InterfazProfesor(usuario);
                    interfaz.setVisible(true);
                });
            } else {
                JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnRegistrar.addActionListener(e -> {
            SwingUtilities.invokeLater(() -> {
                RegistroProfesor registroProfesor = new RegistroProfesor();
                registroProfesor.setVisible(true);
            });
        });
    }

    private boolean autenticarUsuario(String usuario, String contrasena) {
        return ConsolaProfesor.authenticar(usuario, contrasena);
    }
}
