
package InterfazEstudiante;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
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

	    // Inicializar paneles básicos
	    panelBotones = new panelBotones(this);
	    ventana.add(panelBotones, "Funcionalidades");

	    panelInicial = new IniciarSesión(this);
	    ventana.add(panelInicial, "IniciarSesion");

	    panelInscribir = new Inscribir(this);
	    ventana.add(panelInscribir, "Inscribir");
	    
	    try {
			panelCompletarActividad = new CompletarActividad(this);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    ventana.add(panelCompletarActividad, "CompletarActividad");

	    // Mostrar el panel inicial
	    cardLayout.show(ventana.getContentPane(), "IniciarSesion");
	    
	    

	    ventana.setVisible(true);
	}

	
	public CardLayout getCardLayout() {
        return cardLayout;
    }

    public JFrame getVentana() {
        return ventana;
    }
    
    public boolean IniciarSesion(String usuario, String contrasena) {
		boolean inicio = ConsolaEstudiante.authenticar(usuario, contrasena);
		estudianteActual = ConsolaEstudiante.getEstudianteActual();
		return inicio ;
	}
	
	public boolean registrarse(String usuario, String contrasena, String nombre, String email, String intereses) throws NombreRepetido {
		
		return ConsolaEstudiante.registrarse(usuario, contrasena, nombre, email, intereses);
	}
	
	public List<String> profesores(){
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
	
	public String mostrarRecomendacionesYInscribirLearningPath(String profesorID, String intereses, String learningPathID) throws LearningPathNoInscrito {
		return ConsolaEstudiante.mostrarRecomendacionesYInscribirLearningPath(profesorID, intereses, learningPathID);
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

    // Método para verificar si la sesión está iniciada
    public boolean isSesionIniciada() {
        return sesionIniciada;
    }

    // Cambiar a otro panel solo si la sesión está iniciada
    public void cambiarPanel(String panel) {
        if (sesionIniciada || panel.equals("IniciarSesion")) {
            cardLayout.show(ventana.getContentPane(), panel);
        } else {
            // Mensaje de advertencia si no se ha iniciado sesión
            javax.swing.JOptionPane.showMessageDialog(ventana, 
                "Debe iniciar sesión antes de acceder a esta funcionalidad.", 
                "Acceso denegado", 
                javax.swing.JOptionPane.WARNING_MESSAGE);
        }
    }
}
