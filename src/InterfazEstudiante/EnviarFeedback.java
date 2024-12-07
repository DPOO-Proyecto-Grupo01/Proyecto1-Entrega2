package InterfazEstudiante;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import Actividades.Actividad;
import Actividades.Encuesta;
import Actividades.Examen;
import Actividades.Pregunta;
import Actividades.Quiz;
import Actividades.RecursoEducativo;
import Actividades.Tarea;
import Consola.ConsolaEstudiante;
import Exceptions.LearningPathNoInscrito;
import LearningPaths.LearningPath;
import Usuarios.Estudiante;

public class EnviarFeedback extends JPanel {

    private EstudianteInterfaz padre;
    private JComboBox txtNombreActividad;
    private JComboBox txtNombreLP;
    private JComboBox calificación = new JComboBox(new String[]{"1", "2", "3", "4", "5"});
    private JTextField txtFeedback=new JTextField();

    public EnviarFeedback(EstudianteInterfaz elPadre) throws Exception {

    	    this.padre = elPadre;
    	    this.setLayout(new BorderLayout());
    	    this.setBorder(new EmptyBorder(10, 10, 10, 10));
    	    this.setBackground(Color.WHITE);

    	    Estudiante e = elPadre.estudianteActual;


    	    List<String> learningPathsLista = new ArrayList<>(e.getLearningPathsInscritos().keySet());
    	    String[] learningPaths = learningPathsLista.toArray(new String[0]);

    	    txtNombreLP = new JComboBox<>(learningPaths);


    	    JPanel panelEscoger = new JPanel(new BorderLayout());
    	    panelEscoger.setBackground(Color.WHITE);
    	    panelEscoger.setBorder(new TitledBorder("Ver Progreso: "));
    	    panelEscoger.add(new JLabel("Indique el Learning Path para el que quiere enviar FeedBack: "), BorderLayout.NORTH);
    	    panelEscoger.add(txtNombreLP, BorderLayout.CENTER);

    	    String lpSeleccionadoS = (String) txtNombreLP.getSelectedItem();
    	    
    	    JPanel panelFeedback = new JPanel(new BorderLayout());
    	    panelFeedback.setBackground(Color.WHITE);
    	    panelFeedback.setBorder(new TitledBorder("Enviar Feedback: "));
    	    panelFeedback.add(new JLabel("Ingrese su calificación para este Learning Path: "), BorderLayout.NORTH);
    	    panelFeedback.add(calificación);
    	    panelFeedback.add(new JLabel("Ingrese su feedback para este Learning Path: "), BorderLayout.CENTER);
    	    panelFeedback.add(txtFeedback, BorderLayout.SOUTH);
    	    
    	    int calificacion = Integer.parseInt((String) calificación.getSelectedItem());
    	    String feedback = txtFeedback.getText();
    	    
    	    ConsolaEstudiante.enviarFeedback(lpSeleccionadoS, feedback, calificacion);

    	    JButton enviarFeedback = new JButton("Enviar");
  
    	    txtNombreLP.addActionListener(event -> {
    	   	    });

    	    // Agregar paneles al JFrame
    	    this.add(panelEscoger, BorderLayout.NORTH);
    	    this.add(panelFeedback, BorderLayout.CENTER);

    }
	public void actionPerformed(ActionEvent e) {
		
	}




}
