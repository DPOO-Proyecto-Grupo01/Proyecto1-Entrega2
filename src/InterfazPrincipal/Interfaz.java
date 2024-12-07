
package InterfazPrincipal;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import Exceptions.ActividadNoPertenece;
import Exceptions.LearningPathNoInscrito;
import Exceptions.NombreRepetido;
import Exceptions.YaSeCompleto;
import InterfazEstudiante.EstudianteInterfaz;
import InterfazProfesor.IniciarSesion;
import InterfazProfesor.InterfazProfesor;
import Usuarios.Authenticator;
import Usuarios.Usuario;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;

public class Interfaz implements ActionListener {

	private JButton button;
	private JTextField userText;
	private JPasswordField contraseña1;
	private JLabel label;
	private JLabel contraseña;
	private JLabel tipoUsuario;
	private JRadioButton estudianteRadio;
	private JRadioButton profesorRadio;
	private ButtonGroup tipoGroup;
	public String usuarioID;
	public String contrasena;
	public String tipoUsuario1;
	private JFrame frame;

	public Interfaz() {
		frame = new JFrame();
		frame.setSize(400, 350);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setTitle("¡Bienvenido!");
		ImageIcon icon = new ImageIcon(getClass().getResource("/imagenesInterfaz/logo-universidad-de-los-andes.png"));
		frame.setIconImage(icon.getImage());

		JPanel panel = new JPanel();
		panel.setLayout(null);

		ImageIcon imagen = new ImageIcon(getClass().getResource("/imagenesInterfaz/logo.png"));
		JLabel imagenLabel = new JLabel(
				new ImageIcon(imagen.getImage().getScaledInstance(200, 150, java.awt.Image.SCALE_SMOOTH)));
		JPanel imagePanel = new JPanel();
		imagePanel.add(imagenLabel);

		tipoUsuario = new JLabel("Tipo Usuario");
		tipoUsuario.setBounds(10, 80, 80, 25);
		panel.add(tipoUsuario);

		estudianteRadio = new JRadioButton("Estudiante");
		estudianteRadio.setBounds(100, 80, 100, 25);
		panel.add(estudianteRadio);

		profesorRadio = new JRadioButton("Profesor");
		profesorRadio.setBounds(200, 80, 100, 25);
		panel.add(profesorRadio);

		tipoGroup = new ButtonGroup();
		tipoGroup.add(estudianteRadio);
		tipoGroup.add(profesorRadio);

		button = new JButton();
		button.setBounds(120, 130, 10, 25);
		button.setSize(150, 25);
		button.setText("Ingresar");
		button.addActionListener(this);
		panel.add(button);

		frame.setLayout(new BorderLayout());
		frame.add(imagePanel, BorderLayout.NORTH);
		frame.add(panel, BorderLayout.CENTER);
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		new Interfaz();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == button) {
			tipoUsuario1 = "";
			if (estudianteRadio.isSelected()) {
				tipoUsuario1 = "Estudiante";
			} else if (profesorRadio.isSelected()) {
				tipoUsuario1 = "Profesor";
			}   
			if (tipoUsuario1.equals("Estudiante")) {
				this.frame.setVisible(false);
				try {
					EstudianteInterfaz estudiante = new EstudianteInterfaz();
					
					this.frame.setVisible(false);
				} catch (NombreRepetido | LearningPathNoInscrito | ActividadNoPertenece | YaSeCompleto e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
			} else if ( tipoUsuario1.equals("Profesor")) {
				this.frame.setVisible(false);
                SwingUtilities.invokeLater(() -> {
                    IniciarSesion inicioSesion = new IniciarSesion();
                    inicioSesion.setVisible(true);
                });

			} else {
				System.out.println("Usuario o contraseña incorrectos");
			}
		}
		
	}
}
