
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
	    frame.setSize(500, 450); // Incrementamos el tamaño de la ventana para acomodar la imagen más grande
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.setLocationRelativeTo(null);
	    frame.setTitle("¡Bienvenido!");
	    
	    ImageIcon icon = new ImageIcon(getClass().getResource("/imagenesInterfaz/logo-universidad-de-los-andes.png"));
	    frame.setIconImage(icon.getImage());

	    JPanel panel = new JPanel();
	    panel.setLayout(null);

	    // Cargar y escalar la imagen a un tamaño más grande (350x200)
	    ImageIcon imagen = new ImageIcon(getClass().getResource("/imagenesInterfaz/logo.png"));
	    JLabel imagenLabel = new JLabel(
	            new ImageIcon(imagen.getImage().getScaledInstance(350, 200, java.awt.Image.SCALE_SMOOTH)));
	    
	    // Crear un panel para centrar la imagen
	    JPanel imagePanel = new JPanel();
	    imagePanel.add(imagenLabel);

	    // Agregar los componentes de selección de usuario
	    tipoUsuario = new JLabel("Tipo Usuario:");
	    tipoUsuario.setBounds(10, 80, 100, 25);
	    panel.add(tipoUsuario);

	    estudianteRadio = new JRadioButton("Estudiante");
	    estudianteRadio.setBounds(120, 80, 100, 25);
	    panel.add(estudianteRadio);

	    profesorRadio = new JRadioButton("Profesor");
	    profesorRadio.setBounds(250, 80, 100, 25);
	    panel.add(profesorRadio);

	    tipoGroup = new ButtonGroup();
	    tipoGroup.add(estudianteRadio);
	    tipoGroup.add(profesorRadio);

	    button = new JButton("Ingresar");
	    button.setBounds(180, 150, 120, 30); // Botón centrado con un tamaño adecuado
	    button.addActionListener(this);
	    panel.add(button);

	    // Configurar el diseño general del JFrame
	    frame.setLayout(new BorderLayout());
	    frame.add(imagePanel, BorderLayout.NORTH); // Imagen en la parte superior
	    frame.add(panel, BorderLayout.CENTER);    // Panel con controles en el centro
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
