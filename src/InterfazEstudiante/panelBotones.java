
package InterfazEstudiante;

import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.CardLayout;
import java.awt.GridLayout;

public class panelBotones extends JPanel {

	public static final String INSCRIBIR = "Inscribirse a un Learning Path";
	public static final String COMPLETAR_ACTIVIDAD = "Completar actividad";
	public static final String VER_PROGRESO = "Ver progreso de un learningPath";
	public static final String VER_ACTIVIDADES = "Ver actividades por completar";
	public static final String ENVIAR_FEEDBACK = "Enviar Feedback";
	public static final String SALIR = "Salir";
	public static final String INICIAR_SESION = "Iniciar Sesion";

	private JButton btnInscribir;
	private JButton btnCompletarActividad;
	private JButton btnVerProgreso;
	private JButton btnVerActividades;
	private JButton btnEnviarFeedback;
	private JButton btnSalir;
	private JButton btnIniciarSesion;

	private EstudianteInterfaz padre;

	public panelBotones(EstudianteInterfaz elPadre) {
		padre = elPadre;
		setLayout(new GridLayout(6, 1, 10, 10));
		
		btnIniciarSesion = new JButton(INICIAR_SESION);
		btnInscribir = new JButton(INSCRIBIR);
		btnCompletarActividad = new JButton(COMPLETAR_ACTIVIDAD);
		btnVerProgreso = new JButton(VER_PROGRESO);
		btnVerActividades = new JButton(VER_ACTIVIDADES);
		btnEnviarFeedback = new JButton(ENVIAR_FEEDBACK);
		btnSalir = new JButton(SALIR);

		add(btnIniciarSesion);
		add(btnInscribir);
		add(btnCompletarActividad);
		add(btnVerProgreso);
		add(btnVerActividades);
		add(btnEnviarFeedback);
		add(btnSalir);

		btnIniciarSesion.addActionListener(
				e -> padre.getCardLayout().show(padre.getVentana().getContentPane(), "IniciarSesion"));
		btnInscribir
				.addActionListener(e -> padre.getCardLayout().show(padre.getVentana().getContentPane(), "Inscribir"));
		btnCompletarActividad.addActionListener(
				e -> padre.getCardLayout().show(padre.getVentana().getContentPane(), "CompletarActividad"));
		btnVerProgreso
				.addActionListener(e -> padre.getCardLayout().show(padre.getVentana().getContentPane(), "VerProgreso"));
		btnVerActividades.addActionListener(
				e -> padre.getCardLayout().show(padre.getVentana().getContentPane(), "VerActividades"));
		btnEnviarFeedback.addActionListener(
				e -> padre.getCardLayout().show(padre.getVentana().getContentPane(), "EnviarFeedback"));
		btnSalir.addActionListener(e -> padre.salir());
	}
	
	
}
