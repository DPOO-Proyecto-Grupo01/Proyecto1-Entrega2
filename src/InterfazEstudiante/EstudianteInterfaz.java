
package InterfazEstudiante;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Consola.ConsolaEstudiante;
import Exceptions.ActividadNoPertenece;
import Exceptions.LearningPathNoInscrito;
import Exceptions.NombreRepetido;
import Exceptions.YaSeCompleto;
import InterfazPrincipal.Interfaz;
import Usuarios.Estudiante;
import Usuarios.Profesor;

public class EstudianteInterfaz extends Interfaz {

	private ConsolaEstudiante mundo;
	private JFrame ventana;
	private IniciarSesión panelInicial;
	private Inscribir panelInscribir;
	private CardLayout cardLayout;
	private panelBotones panelBotones;
	private CompletarActividad panelCompletarActividad;
	private ActividadesPorCompletar panelActividadesPorCompletar;
	private EnviarFeedback panelEnviarFeedback;
	private RevisarProgresoLP panelVerProgreso;
	private boolean sesionIniciada;
	public String LearningPathTitulo;
	protected Estudiante estudianteActual;

	public EstudianteInterfaz() throws NombreRepetido, LearningPathNoInscrito, ActividadNoPertenece, YaSeCompleto {
	    sesionIniciada = false;
	    mundo = new ConsolaEstudiante();

	    // Cargar datos necesarios
	    ConsolaEstudiante.cargarActividades();
	    ConsolaEstudiante.cargarLearningPaths();
	    ConsolaEstudiante.cargarProgresos();
	    ConsolaEstudiante.cargarActividadesLP();
	    ConsolaEstudiante.cargarProgresoLP();
	    ConsolaEstudiante.cargarProfesores();
	    ConsolaEstudiante.cargarFeedback();
	    ConsolaEstudiante.cargarFeedbackLP();
	    ConsolaEstudiante.cargarEstudiantes();
	    ConsolaEstudiante.cargarLpEstudiantes();
	    ConsolaEstudiante.cargarLpProfesoress();

	    // Configurar la ventana
	    ventana = new JFrame();
	    ventana.setSize(800, 800);
	    ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    ventana.setLocationRelativeTo(null);
	    ventana.setTitle("¡Bienvenido!");

	    cardLayout = new CardLayout();
	    ventana.setLayout(cardLayout);

	    // Crear el panel principal con BorderLayout
	    JPanel panelPrincipal = new JPanel(new BorderLayout());

	    // Añadir la imagen escalada
	    ImageIcon imagen = new ImageIcon(getClass().getResource("/imagenesInterfaz/icono2.png"));
	    JLabel imagenLabel = new JLabel(new ImageIcon(imagen.getImage().getScaledInstance(200, 150, java.awt.Image.SCALE_SMOOTH)));
	    JPanel imagePanel = new JPanel();
	    imagePanel.add(imagenLabel);
	    panelPrincipal.add(imagePanel, BorderLayout.NORTH);

	    // Inicializar y añadir el panel de botones principales
	    panelBotones = new panelBotones(this);
	    panelPrincipal.add(panelBotones, BorderLayout.CENTER);

	    // Crear y configurar el botón "Salir"
	    JButton salirButton = new JButton("Salir");
	    salirButton.setPreferredSize(new Dimension(100, 40)); // Tamaño más pequeño

	    JPanel salirPanel = new JPanel(); // Crear el panel
	    salirPanel.setLayout(new FlowLayout(FlowLayout.CENTER)); // Configurar el layout del panel
	    salirPanel.add(salirButton);

	    panelPrincipal.add(salirPanel, BorderLayout.SOUTH); // Añadir el panel al fondo del contenedor principal


	    // Acción del botón "Salir"
	    salirButton.addActionListener(e -> System.exit(0));

	    ventana.add(panelPrincipal, "Funcionalidades");

	    // Panel inicial
	    panelInicial = new IniciarSesión(this);
	    ventana.add(panelInicial, "IniciarSesion");

	    // Otros paneles
	    panelInscribir = new Inscribir(this);
	    ventana.add(panelInscribir, "Inscribir");

	    // Mostrar el panel inicial
	    cardLayout.show(ventana.getContentPane(), "IniciarSesion");

	    ventana.setVisible(true);
	}


	public void initializeStudentPanels() throws Exception {
		panelCompletarActividad = new CompletarActividad(this);
		ventana.add(panelCompletarActividad, "CompletarActividad");

		panelActividadesPorCompletar = new ActividadesPorCompletar(this);
		ventana.add(panelActividadesPorCompletar, "VerActividades");

		panelEnviarFeedback = new EnviarFeedback(this);
		ventana.add(panelEnviarFeedback, "EnviarFeedback");

		panelVerProgreso = new RevisarProgresoLP(this);
		ventana.add(panelVerProgreso, "VerProgreso");
	}

	public CardLayout getCardLayout() {
		return cardLayout;
	}

	public JFrame getVentana() {
		return ventana;
	}

	public boolean IniciarSesion(String usuario, String contrasena) {
		boolean inicio = ConsolaEstudiante.authenticar(usuario, contrasena);
		if (inicio) {
			estudianteActual = ConsolaEstudiante.getEstudianteActual();
		}
		return inicio;
	}

	public boolean registrarse(String usuario, String contrasena, String nombre, String email, String intereses)
			throws NombreRepetido {
		return ConsolaEstudiante.registrarse(usuario, contrasena, nombre, email, intereses);
	}

	public List<String> profesores() {
		List<String> profesores1 = ConsolaEstudiante.profesores.keySet().stream().toList();
		return profesores1;
	}

	public List<Profesor> getProfesores() {
		List<Profesor> profesores = new ArrayList<>();
		for (String profesor : ConsolaEstudiante.profesores.keySet()) {
			profesores.add(ConsolaEstudiante.profesores.get(profesor));
		}
		return profesores;
	}

	public String mostrarRecomendacionesYInscribirLearningPath(String profesorID, String intereses,
			String learningPathID) throws Exception {
		String result = ConsolaEstudiante.mostrarRecomendacionesYInscribirLearningPath(profesorID, intereses,
				learningPathID);
		initializeStudentPanels();
		return result;
	}

	public static void main(String[] args) {
		try {
			new EstudianteInterfaz();
		} catch (NombreRepetido | LearningPathNoInscrito | ActividadNoPertenece | YaSeCompleto e) {
			e.printStackTrace();
		}
	}

	public void salir() {
		ventana.dispose();
		System.exit(0);
	}

	public void iniciarSesion() {
		sesionIniciada = true;
	}

	public boolean isSesionIniciada() {
		return sesionIniciada;
	}

	public void cambiarPanel(String panel) {
		if (sesionIniciada || panel.equals("IniciarSesion")) {
			cardLayout.show(ventana.getContentPane(), panel);
		} else {
			JOptionPane.showMessageDialog(ventana, "Debe iniciar sesión antes de acceder a esta funcionalidad.",
					"Acceso denegado", JOptionPane.WARNING_MESSAGE);
		}
	}

	public Estudiante getEstudianteActual() {
		return estudianteActual;
	}

	public void setEstudianteActual(Estudiante estudiante) {
		this.estudianteActual = estudiante;
	}
}
