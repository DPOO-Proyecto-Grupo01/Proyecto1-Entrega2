
package InterfazEstudiante;

import java.awt.BorderLayout;
import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Consola.ConsolaEstudiante;
import Exceptions.ActividadNoPertenece;
import Exceptions.LearningPathNoInscrito;
import Exceptions.NombreRepetido;
import Exceptions.YaSeCompleto;
import InterfazPrincipal.Interfaz;

public class EstudianteInterfaz extends Interfaz {

	private ConsolaEstudiante mundo;
	private JFrame ventana;
	private IniciarSesión panelInicial;
	private CardLayout cardLayout;
	private panelBotones panelBotones;
	private String usuario;
	private String contrasena;
	private String nombre;
	private String email;
	private String intereses;
	


	public EstudianteInterfaz() throws NombreRepetido, LearningPathNoInscrito, ActividadNoPertenece, YaSeCompleto {

		mundo = new ConsolaEstudiante();

		ConsolaEstudiante.cargarActividades();
        ConsolaEstudiante.cargarLearningPaths();
        ConsolaEstudiante.cargarProgresos();
        ConsolaEstudiante.cargarActividadesLP(); 
        ConsolaEstudiante.cargarProgresoLP();
        ConsolaEstudiante.cargarProfesores();
        ConsolaEstudiante.cargarEstudiantes();
        ConsolaEstudiante.cargarLpEstudiantes();
        ConsolaEstudiante.cargarLpProfesoress();

		ventana = new JFrame();
		ventana.setSize(800, 800);
		ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ventana.setLocationRelativeTo(null);
		ventana.setTitle("¡Bienvenido!");
		
		ventana.setLayout(new BorderLayout());
		ventana.setVisible(true);

		panelInicial = new IniciarSesión(this);
		if (panelInicial.getNombre() == null) {
			usuario=panelInicial.getUsuarioID();
			contrasena=panelInicial.getContrasena();
			IniciarSesion(usuario, contrasena);
		}
		else {
			nombre = panelInicial.getNombre();
			email = panelInicial.getEmail();
			intereses = panelInicial.getIntereses();
			registrarse(usuario, contrasena, nombre, email, intereses);
		}
		
		
		cardLayout = new CardLayout();
		
		ventana.setLayout(cardLayout);
		panelBotones = new panelBotones(this);
		ventana.add(panelBotones, "Funcionalidades");
		
		panelInicial = new IniciarSesión(this);
        ventana.add(panelInicial, "IniciarSesion");
		
	}
	
	public CardLayout getCardLayout() {
        return cardLayout;
    }

    public JFrame getVentana() {
        return ventana;
    }
    
	public boolean IniciarSesion(String usuario, String contrasena) {
		return ConsolaEstudiante.authenticar(usuario, contrasena);
		
	}
	
	public void registrarse(String usuario, String contrasena, String nombre, String email, String intereses) throws NombreRepetido {
		
		ConsolaEstudiante.registrarse(usuario, contrasena, nombre, email, intereses);
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
}
