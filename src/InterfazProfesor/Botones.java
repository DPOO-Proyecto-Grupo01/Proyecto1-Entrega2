package InterfazProfesor;

import javax.swing.*;
import java.awt.*;

public class Botones {

	private final CardLayout cardLayout;
    private final JPanel panelCentral;
    private final InterfazProfesor interfaz;

    public Botones(CardLayout cardLayout, JPanel panelCentral, InterfazProfesor interfaz) {
        this.cardLayout = cardLayout;
        this.panelCentral = panelCentral;
        this.interfaz = interfaz;
    }

    public JPanel crearPanelOpciones() {
        JPanel panel = new JPanel(new GridLayout(7, 1, 10, 10));

        JButton btnCrearActividad = new JButton("Crear Actividad");
        JButton btnCrearLearningPath = new JButton("Crear Learning Path");
        JButton btnRevisarEstadoActividad = new JButton("Revisar Estado de Actividad");
        JButton btnVerProgresoEstudiante = new JButton("Ver Progreso de Estudiante");
        JButton btnRevisarFeedback = new JButton("Revisar Feedback");
        JButton btnCalcularRating = new JButton("Calcular Rating");
        JButton btnSalir = new JButton("Salir");

        panel.add(btnCrearActividad);
        panel.add(btnCrearLearningPath);
        panel.add(btnRevisarEstadoActividad);
        panel.add(btnVerProgresoEstudiante);
        panel.add(btnRevisarFeedback);
        panel.add(btnCalcularRating);
        panel.add(btnSalir);

        btnCrearActividad.addActionListener(e -> cardLayout.show(panelCentral, "CrearActividad"));
        btnCrearLearningPath.addActionListener(e -> cardLayout.show(panelCentral, "CrearLearningPath"));
        btnVerProgresoEstudiante.addActionListener(e -> cardLayout.show(panelCentral, "VerProgresoEstudiante"));
        btnRevisarEstadoActividad.addActionListener(e -> cardLayout.show(panelCentral, "RevisarEstadodeActividad"));
        btnRevisarFeedback.addActionListener(e -> cardLayout.show(panelCentral, "RevisarFeedback"));
        btnCalcularRating.addActionListener(e -> cardLayout.show(panelCentral, "CalcularRating"));
        btnSalir.addActionListener(e -> interfaz.salir());

        return panel;
    }
}
