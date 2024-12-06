package InterfazProfesor;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import Consola.ConsolaProfesor;

class RegistroProfesor extends JFrame {

    public RegistroProfesor() {
        setTitle("Registro - Profesor");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel panelCentral = new JPanel(new GridLayout(4, 2, 10, 10));

        JLabel lblNombre = new JLabel("Nombre:");
        JTextField txtNombre = new JTextField();
        JLabel lblID = new JLabel("ID:");
        JTextField txtID = new JTextField();
        JLabel lblCorreo = new JLabel("Correo:");
        JTextField txtCorreo = new JTextField();
        JLabel lblContrasena = new JLabel("Contraseña:");
        JPasswordField txtContrasena = new JPasswordField();

        panelCentral.add(lblNombre);
        panelCentral.add(txtNombre);
        panelCentral.add(lblID);
        panelCentral.add(txtID);
        panelCentral.add(lblCorreo);
        panelCentral.add(txtCorreo);
        panelCentral.add(lblContrasena);
        panelCentral.add(txtContrasena);

        add(panelCentral, BorderLayout.CENTER);

        JPanel panelInferior = new JPanel();
        JButton btnRegistrar = new JButton("Registrar");
        panelInferior.add(btnRegistrar);

        add(panelInferior, BorderLayout.SOUTH);

        btnRegistrar.addActionListener(e -> {
            String nombre = txtNombre.getText();
            String id = txtID.getText();
            String correo = txtCorreo.getText();
            String contrasena = new String(txtContrasena.getPassword());

            if (validarDatos(nombre, id, correo, contrasena)) {
            	ConsolaProfesor.registrarse(id, nombre, contrasena, correo);
                JOptionPane.showMessageDialog(this, "Registro exitoso", "Información", JOptionPane.INFORMATION_MESSAGE);
                dispose();
                InterfazProfesor interfaz = new InterfazProfesor();
                interfaz.setVisible(true);
                
            } else {
                JOptionPane.showMessageDialog(this, "Por favor complete todos los campos", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private boolean validarDatos(String nombre, String id, String correo, String contrasena) {
        return !nombre.isEmpty() && !id.isEmpty() && !correo.isEmpty() && !contrasena.isEmpty();
    }
}
