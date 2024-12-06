package InterfazEstudiante;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import Exceptions.NombreRepetido;
import InterfazPrincipal.Interfaz;

public class IniciarSesión extends JPanel implements ActionListener {

    private static final long serialVersionUID = 1L;

    private JButton loginButton;
    private JButton registerButton;
    private JTextField loginUserText;
    private JPasswordField loginContraseña;
    private JTextField registerUserText;
    private JPasswordField registerContraseña;
    private JTextField registerNombre;
    private JTextField registerEmail;
    private JTextField intereses;
    private String usuarioID;
    private String contrasena;
    private String nombre;
    private String email;
    
    private JPanel loginPanel;
    private JPanel registerPanel;
    

    private EstudianteInterfaz padre;

    public IniciarSesión(EstudianteInterfaz elpadre) {
        padre = elpadre;

        // Configuración del layout general
        this.setLayout(new BorderLayout(10, 10));
        this.setBorder(new EmptyBorder(15, 15, 15, 15)); // Padding alrededor del panel

        // Panel de inicio de sesión
        loginPanel = crearPanelLogin();
        loginPanel.setBorder(new TitledBorder("Iniciar Sesión"));
        this.add(loginPanel, BorderLayout.NORTH);

        // Panel de registro
        registerPanel = crearPanelRegistro();
        registerPanel.setBorder(new TitledBorder("Registrar Nuevo Usuario"));
        this.add(registerPanel, BorderLayout.CENTER);

        // Fondo de color claro
        this.setBackground(new Color(230, 240, 255));
    }

    private JPanel crearPanelLogin() {
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBackground(new Color(255, 255, 255));

        loginUserText = new JTextField();
        loginContraseña = new JPasswordField();
        loginButton = new JButton("Iniciar sesión");
        loginButton.addActionListener(this);

        panel.add(new JLabel("Usuario:"));
        panel.add(loginUserText);
        panel.add(new JLabel("Contraseña:"));
        panel.add(loginContraseña);
        panel.add(new JLabel(""));
        panel.add(loginButton);

        return panel;
    }

    private JPanel crearPanelRegistro() {
        // Crear panel con GridLayout
        JPanel panel = new JPanel(new GridLayout(6, 2, 5, 5)); // Ajusta las filas/columnas si es necesario
        panel.setBackground(new Color(255, 255, 255));
        
        // Campos de texto
        registerUserText = new JTextField();
        registerUserText.setPreferredSize(new Dimension(100, 20)); // Ancho x Alto
        
        registerContraseña = new JPasswordField();
        registerContraseña.setPreferredSize(new Dimension(100, 20));
        
        registerNombre = new JTextField();
        registerNombre.setPreferredSize(new Dimension(100, 20));
        
        registerEmail = new JTextField();
        registerEmail.setPreferredSize(new Dimension(100, 20));
        
        intereses = new JTextField();
        intereses.setPreferredSize(new Dimension(100, 20));
        
        // Botón de registro
        registerButton = new JButton("Registrarse");
        registerButton.addActionListener(this);

        // Agregar componentes al panel
        panel.add(new JLabel("Usuario:"));
        panel.add(registerUserText);
        panel.add(new JLabel("Contraseña:"));
        panel.add(registerContraseña);
        panel.add(new JLabel("Nombre:"));
        panel.add(registerNombre);
        panel.add(new JLabel("Email:"));
        panel.add(registerEmail);
        panel.add(new JLabel("Intereses:"));
        panel.add(intereses);
        panel.add(new JLabel("")); // Espacio vacío
        panel.add(registerButton);

        // Retornar el panel
        return panel;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            loginUser();
        } else if (e.getSource() == registerButton) {
            try {
				registerUser();
			} catch (HeadlessException | NombreRepetido e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        }
    }

    private void loginUser() {
        usuarioID = loginUserText.getText();
        contrasena = new String(loginContraseña.getPassword());
		if (padre.IniciarSesion(usuarioID, contrasena)==true) {
			padre.getCardLayout().show(padre.getVentana().getContentPane(), "Funcionalidades");
			JOptionPane.showMessageDialog(null, "Usuario autenticado", "Inicio de sesión exitoso", JOptionPane.INFORMATION_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrecta", "Error", JOptionPane.ERROR_MESSAGE);
		}
        
        
        
        
    }

    private void registerUser() throws HeadlessException, NombreRepetido {
        usuarioID = registerUserText.getText();
        contrasena = new String(registerContraseña.getPassword());
        nombre = registerNombre.getText();
        email = registerEmail.getText();
        if (padre.registrarse(usuarioID, contrasena, nombre, email, intereses.getText())==true) {
			padre.getCardLayout().show(padre.getVentana().getContentPane(), "Funcionalidades");
			JOptionPane.showMessageDialog(null, "Usuario registrado", "Registro exitoso", JOptionPane.INFORMATION_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(null, "Usuario ya existe", "Error", JOptionPane.ERROR_MESSAGE);
        }
        
        
        
    }
    
	public String getUsuarioID() {
		return usuarioID;
	}
	
	public String getContrasena() {
		return contrasena;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public String getEmail() {
		return email;
	}
	
	public String getIntereses() {
		return intereses.getText();
	}
	
	
	
}

