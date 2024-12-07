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

public class ActividadesPorCompletar extends JPanel {

    private EstudianteInterfaz padre;
    private JComboBox txtNombreActividad;
    private JComboBox txtNombreLP;

    public ActividadesPorCompletar(EstudianteInterfaz elPadre) throws Exception {

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
    	    panelEscoger.setBorder(new TitledBorder("Ver Actividades sin completar: "));
    	    panelEscoger.add(new JLabel("Indique el Learning Path para el que quiere revisar sus actividades pendientes: "), BorderLayout.NORTH);
    	    panelEscoger.add(txtNombreLP, BorderLayout.CENTER);

    	    String lpSeleccionadoS = (String) txtNombreLP.getSelectedItem();

    	    JPanel panelActividades= new JPanel(new BorderLayout());
    	    panelActividades.setBackground(Color.WHITE);
    	    
    	    List<Actividad> actividades = ConsolaEstudiante.verActividadesPorCompletar(lpSeleccionadoS);
  
    	    txtNombreLP.addActionListener(event -> {
    	    	
			for (Actividad a : actividades) {
				panelActividades.add(new JLabel(a.getActividadID()), BorderLayout.CENTER);
				}

	            // Actualizar la vista
	            panelActividades.revalidate();
	            panelActividades.repaint();
	        ;
	    });

    	    // Agregar paneles al JFrame
    	    this.add(panelEscoger, BorderLayout.NORTH);
    	    this.add(panelActividades, BorderLayout.CENTER);

    }





}
