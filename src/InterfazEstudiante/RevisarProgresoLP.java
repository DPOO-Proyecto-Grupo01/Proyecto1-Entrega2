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

public class RevisarProgresoLP extends JPanel {

    private EstudianteInterfaz padre;
    private JComboBox txtNombreActividad;
    private JComboBox txtNombreLP;

    public RevisarProgresoLP(EstudianteInterfaz elPadre) throws Exception {
    	
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
    	   
    	JPanel panelEscoger= new JPanel(new BorderLayout());
    	panelEscoger.setBackground(Color.WHITE);
    	panelEscoger.setBorder(new TitledBorder("Ver Progreso: "));
    	panelEscoger.add(new JLabel("Indique el Learning Path para el que quiere revisar su progreso: "), BorderLayout.NORTH);
    	panelEscoger.add(txtNombreLP, BorderLayout.CENTER);
    	
    	JPanel panelProgreso = new JPanel(new BorderLayout());
    	double progreso = e.getProgresoLearningPath(lpSeleccionadoS);
    	
		if (progreso == 1) {
			JOptionPane.showMessageDialog(null, "¡Felicidades! Has completado el Learning Path: " + lpSeleccionadoS);
		}
		else if (progreso == 10000.000) {
			JOptionPane.showMessageDialog(null, "No se a encontrado un progreso para este larningPath", "Progreso", JOptionPane.WARNING_MESSAGE);
		}

		else {
            panelProgreso.add(new JLabel("Tu progreso en el Learning Path " + lpSeleccionadoS + " es de: " + progreso + "%"), BorderLayout.CENTER);
            this.add(panelProgreso, BorderLayout.CENTER);
        }
		

    

    }
	public void actionPerformed(ActionEvent e) {
		
	}




}
