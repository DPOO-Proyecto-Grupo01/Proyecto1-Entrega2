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
import LearningPaths.LearningPath;
import Usuarios.Estudiante;

public class CompletarActividad extends JPanel {

    private EstudianteInterfaz padre;
    private JComboBox txtNombreActividad;
    private JComboBox txtNombreLP;

    public CompletarActividad(EstudianteInterfaz elPadre) throws Exception {
    	
        this.padre = elPadre;
        this.setLayout(new BorderLayout());
        this.setBorder(new EmptyBorder(10, 10, 10, 10));
        this.setBackground(Color.WHITE);
        
    	Estudiante e = elPadre.estudianteActual;

    	List<String> learningPathsLista = new ArrayList<>();

    	for (String lp : e.getLearningPathsInscritos().keySet()) {
    	    learningPathsLista.add(lp);
    	}

    	String[] learningPaths = learningPathsLista.toArray(new String[0]);

    	txtNombreLP = new JComboBox<>(learningPaths);
    	
    	String lpSeleccionadoS = (String) txtNombreLP.getSelectedItem();
    	LearningPath lpSeleccionado = e.getLearningPathsInscritos().get(lpSeleccionadoS);
    	
    	List<String> actividadesLista = new ArrayList<>();
    	Map <String, Actividad> actividadesMapa = new HashMap<>();
    	
    	for (Actividad actividad : e.actividadesDisponibles(lpSeleccionadoS)) {
    		actividadesLista.add(actividad.getActividadID());
    		actividadesMapa.put(actividad.getActividadID(), actividad);
    	}
    	
    	String[] actividades = actividadesLista.toArray(new String[0]);

    	txtNombreActividad = new JComboBox<>(actividades);
    	
    	Actividad actividadSeleccionada = actividadesMapa.get((String) txtNombreActividad.getSelectedItem());
    
    	   
    	JPanel panelEscoger= new JPanel(new BorderLayout());
    	panelEscoger.setBackground(Color.WHITE);
    	panelEscoger.setBorder(new TitledBorder("Escoger Actividad"));
    	panelEscoger.add(new JLabel("Indique el Learning Path en el que desea trabajar: "), BorderLayout.NORTH);
    	panelEscoger.add(txtNombreLP, BorderLayout.CENTER);
    	panelEscoger.add(new JLabel("Indique la actividad que desea completar: "), BorderLayout.SOUTH);
    	panelEscoger.add(txtNombreActividad, BorderLayout.CENTER);
    	
    	this.add(panelEscoger, BorderLayout.NORTH);
    	
    	List<String>actividadesPrevias=e.actividadesSinCompletar(actividadSeleccionada, lpSeleccionado);
    		if (actividadesPrevias != null) {
    		JOptionPane.showMessageDialog(null, "No a completado las actividades: "+actividadesPrevias, "Actividades Previas", JOptionPane.WARNING_MESSAGE);
    		}
    		
    	if (actividadSeleccionada instanceof Quiz) {
    		completarQuiz((Quiz) actividadSeleccionada, lpSeleccionado, e);
    	}
		else if (actividadSeleccionada instanceof Tarea) {
			completarTarea((Tarea) actividadSeleccionada, lpSeleccionado, e);

		} else if (actividadSeleccionada instanceof Examen) {
			completarExamen((Examen) actividadSeleccionada, lpSeleccionado, e);

		}
		else if (actividadSeleccionada instanceof RecursoEducativo) {
			completarRecurso((RecursoEducativo) actividadSeleccionada, lpSeleccionado, e);

		}
		else if (actividadSeleccionada instanceof Encuesta) {
			completarEncuesta((Encuesta) actividadSeleccionada, lpSeleccionado, e);

		} 
    	
		JButton botonCompletar = new JButton("Completar Actividad");
		botonCompletar.addActionListener((ActionListener) this);
		this.add(botonCompletar, BorderLayout.SOUTH);
     
}

	private void completarEncuesta(Encuesta actividadSeleccionada, LearningPath LearningPathSeleccionado, Estudiante e) throws Exception {
		JPanel panelEncuesta = new JPanel(new BorderLayout());
		panelEncuesta.setBackground(Color.WHITE);
		panelEncuesta.setBorder(new TitledBorder("Encuesta: "));
		panelEncuesta.add(new JLabel("Preguntas: "), BorderLayout.NORTH);
		List<String> preguntas = actividadSeleccionada.getPreguntas();
		for (String pregunta : preguntas) {
			panelEncuesta.add(new JLabel(pregunta), BorderLayout.CENTER);
			panelEncuesta.add(new JTextField(), BorderLayout.CENTER);
		}
		this.add(panelEncuesta, BorderLayout.CENTER);
		e.completarActividad(actividadSeleccionada.getActividadID(), LearningPathSeleccionado.getLearningPathID());
		JOptionPane.showMessageDialog(null, "Se le recomienda realizar las actividades: "+ actividadSeleccionada.getActividadesSeguimiento(), "Actividades Previas", JOptionPane.INFORMATION_MESSAGE);
		
	}

	private void completarRecurso(RecursoEducativo actividadSeleccionada, LearningPath LearningPathSeleccionado, Estudiante e) throws Exception {
		JPanel panelRecurso = new JPanel(new BorderLayout());
		panelRecurso.setBackground(Color.WHITE);
		panelRecurso.setBorder(new TitledBorder("Recurso Educativo: "));
		panelRecurso.add(new JLabel("Recurso: " + actividadSeleccionada.getTipoRecurso()), BorderLayout.NORTH);
		panelRecurso.add(new JLabel(actividadSeleccionada.getLinkRecusro()), BorderLayout.CENTER);
		this.add(panelRecurso, BorderLayout.CENTER);
		e.completarActividad(actividadSeleccionada.getActividadID(), LearningPathSeleccionado.getLearningPathID());
		JOptionPane.showMessageDialog(null, "Se le recomienda realizar las actividades: "+ actividadSeleccionada.getActividadesSeguimiento(), "Actividades Previas", JOptionPane.INFORMATION_MESSAGE);
	}

	private void completarExamen(Examen actividadSeleccionada, LearningPath LearningPathSeleccionado, Estudiante e) throws Exception {
		JPanel panelExamen = new JPanel(new BorderLayout());
		panelExamen.setBackground(Color.WHITE);
		panelExamen.setBorder(new TitledBorder("Examen: "));
		panelExamen.add(new JLabel("Preguntas: "), BorderLayout.NORTH);
		List<Pregunta> preguntas = actividadSeleccionada.getPreguntas();
		for (Pregunta pregunta : preguntas) {
			panelExamen.add(new JLabel(pregunta.getPregunta()), BorderLayout.CENTER);
			panelExamen.add(new JTextField(), BorderLayout.CENTER);
		}
		this.add(panelExamen, BorderLayout.CENTER);
		e.completarActividad(actividadSeleccionada.getActividadID(), LearningPathSeleccionado.getLearningPathID());
		JOptionPane.showMessageDialog(null, "Se le recomienda realizar las actividades: "+ actividadSeleccionada.getActividadesSeguimiento(), "Actividades Previas", JOptionPane.INFORMATION_MESSAGE);
	}

	private void completarTarea(Tarea actividadSeleccionada, LearningPath LearningPathSeleccionado, Estudiante e) throws Exception {
		JPanel panelTarea = new JPanel(new BorderLayout());
		panelTarea.setBackground(Color.WHITE);
		panelTarea.setBorder(new TitledBorder("Tarea: "));
		panelTarea.add(new JLabel(actividadSeleccionada.getDescripcion()), BorderLayout.NORTH);
		panelTarea.add(new JTextField(), BorderLayout.CENTER);
		this.add(panelTarea, BorderLayout.CENTER);
		e.completarActividad(actividadSeleccionada.getActividadID(), LearningPathSeleccionado.getLearningPathID());
		JOptionPane.showMessageDialog(null, "Se le recomienda realizar las actividades: "+ actividadSeleccionada.getActividadesSeguimiento(), "Actividades Previas", JOptionPane.INFORMATION_MESSAGE);
	}

	private void completarQuiz(Quiz actividadSeleccionada, LearningPath LearningPathSeleccionado, Estudiante e) throws Exception {
		JPanel panelQuiz = new JPanel(new BorderLayout());
        panelQuiz.setBackground(Color.WHITE);
        panelQuiz.setBorder(new TitledBorder("Quiz: "));
        panelQuiz.add(new JLabel("Preguntas: "), BorderLayout.NORTH);
        List<Pregunta> preguntas = actividadSeleccionada.getPreguntas();
        for (Pregunta pregunta : preguntas) {
            panelQuiz.add(new JLabel(pregunta.getPregunta()), BorderLayout.CENTER);
			for (String opcion : pregunta.getOpciones()) {
				panelQuiz.add(new JRadioButton(opcion), BorderLayout.CENTER);
			}
        }
        this.add(panelQuiz, BorderLayout.CENTER);
        e.completarActividad(actividadSeleccionada.getActividadID(), LearningPathSeleccionado.getLearningPathID());
        JOptionPane.showMessageDialog(null, "Se le recomienda realizar las actividades: "+ actividadSeleccionada.getActividadesSeguimiento(), "Actividades Previas", JOptionPane.INFORMATION_MESSAGE);
		
	}
	
	public void actionPerformed(ActionEvent e) {
		
	}




}
