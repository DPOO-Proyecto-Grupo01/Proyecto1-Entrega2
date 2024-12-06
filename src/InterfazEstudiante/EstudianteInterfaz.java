
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

public class EstudianteInterfaz extends Interfaz {

	private ConsolaEstudiante mundo;
	private JFrame ventana;
	private IniciarSesión panelInicial;
	private Inscribir panelInscribir;
	private CardLayout cardLayout;
	private panelBotones panelBotones;
	private boolean sesionIniciada;
	

	public EstudianteInterfaz() throws NombreRepetido, LearningPathNoInscrito, ActividadNoPertenece, YaSeCompleto {
		
		sesionIniciada = false;
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
		
		ImageIcon icon = new ImageIcon(getClass().getResource("/imagenesInterfaz/icono2.png"));

		cardLayout = new CardLayout();
		
		ventana.setLayout(cardLayout);
		panelBotones = new panelBotones(this);
		ventana.add(panelBotones, "Funcionalidades");
		
		panelInicial = new IniciarSesión(this);
        ventana.add(panelInicial, "IniciarSesion");
        
     
        cardLayout.show(ventana.getContentPane(), "IniciarSesion");
        
        panelInscribir = new Inscribir(this);
        ventana.add(panelInscribir, "Inscribir");
		
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
	
	public boolean registrarse(String usuario, String contrasena, String nombre, String email, String intereses) throws NombreRepetido {
		
		return ConsolaEstudiante.registrarse(usuario, contrasena, nombre, email, intereses);
	}
	
	public List<String> profesores(){
		List<String> profesores1 = ConsolaEstudiante.profesores.keySet().stream().toList();
		String mensaje = "";
		for (String profesor : profesores1) {
			mensaje += profesor + "\n";
		}
		JOptionPane.showMessageDialog(null, mensaje, "Profesores", JOptionPane.INFORMATION_MESSAGE);
		return profesores1;
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
