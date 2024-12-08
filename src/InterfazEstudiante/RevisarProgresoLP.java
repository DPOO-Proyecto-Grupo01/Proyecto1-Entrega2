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
import Exceptions.LearningPathNoInscrito;
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


    	    List<String> learningPathsLista = new ArrayList<>(e.getLearningPathsInscritos().keySet());
    	    String[] learningPaths = learningPathsLista.toArray(new String[0]);

    	    txtNombreLP = new JComboBox<>(learningPaths);


    	    JPanel panelEscoger = new JPanel(new BorderLayout());
    	    panelEscoger.setBackground(Color.WHITE);
    	    panelEscoger.setBorder(new TitledBorder("Ver Progreso: "));
    	    panelEscoger.add(new JLabel("Indique el Learning Path para el que quiere revisar su progreso: "), BorderLayout.NORTH);
    	    panelEscoger.add(txtNombreLP, BorderLayout.CENTER);

    	    JPanel panelProgreso = new JPanel(new BorderLayout());
    	    panelProgreso.setBackground(Color.WHITE);

  
    	    txtNombreLP.addActionListener(event -> {
    	    	
	        String lpSeleccionadoS = (String) txtNombreLP.getSelectedItem();
	        if (lpSeleccionadoS != null) {
	            double progreso = 10000.000;
				try {
					progreso = e.getProgresoLearningPath(lpSeleccionadoS)+30;
				} catch (LearningPathNoInscrito e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

	            panelProgreso.removeAll(); // Limpiar contenido anterior

	            if (progreso == 1) {
	                JOptionPane.showMessageDialog(this, "¡Felicidades! Has completado el Learning Path: " + lpSeleccionadoS);
	            } else if (progreso == 10000.000) {
	                JOptionPane.showMessageDialog(this, "No se ha encontrado un progreso para este Learning Path", "Progreso", JOptionPane.WARNING_MESSAGE);
	            } else {
	                panelProgreso.add(new JLabel("Tu progreso en el Learning Path " + lpSeleccionadoS + " es de: " + progreso + "%"), BorderLayout.CENTER);
	            }

	            // Actualizar la vista
	            panelProgreso.revalidate();
	            panelProgreso.repaint();
	        }
	    });

    	    // Agregar paneles al JFrame
    	    this.add(panelEscoger, BorderLayout.NORTH);
    	    this.add(panelProgreso, BorderLayout.CENTER);
    	    
    	    JButton btnRegresar = new JButton("Regresar");
            btnRegresar.setFont(new Font("Arial", Font.PLAIN, 14));
            btnRegresar.setBackground(new Color(230, 240, 255));
            btnRegresar.setSize(100, 50);
            btnRegresar.addActionListener(event -> {
                padre.getCardLayout().show(padre.getVentana().getContentPane(), "Funcionalidades");
            });
            
            this.add(btnRegresar, BorderLayout.SOUTH);


    }
	public void actionPerformed(ActionEvent e) {
		
	}

	



}
