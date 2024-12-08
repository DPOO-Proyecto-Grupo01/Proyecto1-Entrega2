package InterfazEstudiante;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import Exceptions.LearningPathNoInscrito;
import InterfazProfesor.IniciarSesion;
import Usuarios.Profesor;

public class Inscribir extends JPanel {

    private JComboBox<String> comboProfesores;
    public JComboBox<String> comboLearningPaths;
    private EstudianteInterfaz padre;
    private List<String> profesores;
    public String LearningPathTitulo;

    public Inscribir(EstudianteInterfaz elPadre) {
        padre = elPadre;

        // Configurar diseño principal
        this.setLayout(new GridLayout(4, 1, 10, 10)); // Ahora 4 filas (se agregó un nuevo panel)
        this.setBorder(new EmptyBorder(15, 15, 15, 15)); // Espaciado alrededor del panel
        this.setBackground(new Color(230, 240, 255));

        // Panel de selección de profesor
        JPanel panelProfesores = new JPanel(new BorderLayout());
        panelProfesores.setBackground(Color.WHITE);
        panelProfesores.setBorder(new TitledBorder("Seleccionar Profesor"));

        JLabel lblProfesores = new JLabel("Profesor: ");
        lblProfesores.setFont(new Font("Arial", Font.BOLD, 14));
        lblProfesores.setHorizontalAlignment(SwingConstants.CENTER);
        panelProfesores.add(lblProfesores, BorderLayout.NORTH);

        profesores = padre.profesores();
        comboProfesores = new JComboBox<>(profesores.toArray(new String[0]));
        comboProfesores.setFont(new Font("Arial", Font.PLAIN, 14));
        comboProfesores.addActionListener(new ProfesorSelectionListener());
        panelProfesores.add(comboProfesores, BorderLayout.CENTER);
        this.add(panelProfesores);

        // Panel de selección de Learning Path
        JPanel panelLearningPaths = new JPanel(new BorderLayout());
        panelLearningPaths.setBackground(Color.WHITE);
        panelLearningPaths.setBorder(new TitledBorder("Learning Paths del Profesor"));

        JLabel lblLearningPaths = new JLabel("Learning Path:");
        lblLearningPaths.setFont(new Font("Arial", Font.BOLD, 14));
        lblLearningPaths.setHorizontalAlignment(SwingConstants.CENTER);
        panelLearningPaths.add(lblLearningPaths, BorderLayout.NORTH);

        comboLearningPaths = new JComboBox<>();
        comboLearningPaths.setFont(new Font("Arial", Font.PLAIN, 14));
        actualizarLearningPaths("Profesor 1"); // Cargar datos iniciales
        panelLearningPaths.add(comboLearningPaths, BorderLayout.CENTER);
        this.add(panelLearningPaths);

        // Panel de intereses del estudiante
        JPanel panelIntereses = new JPanel(new BorderLayout());
        panelIntereses.setBackground(Color.WHITE);
        panelIntereses.setBorder(new TitledBorder("Intereses del Estudiante"));

        JLabel lblIntereses = new JLabel("Escriba sus intereses:");
        lblIntereses.setFont(new Font("Arial", Font.BOLD, 14));
        lblIntereses.setHorizontalAlignment(SwingConstants.CENTER);
        panelIntereses.add(lblIntereses, BorderLayout.NORTH);

        JTextArea textAreaIntereses = new JTextArea(4, 20);
        textAreaIntereses.setFont(new Font("Arial", Font.PLAIN, 14));
        textAreaIntereses.setLineWrap(true);
        textAreaIntereses.setWrapStyleWord(true);
        JScrollPane scrollIntereses = new JScrollPane(textAreaIntereses);
        panelIntereses.add(scrollIntereses, BorderLayout.CENTER);
        this.add(panelIntereses);
        
        LearningPathTitulo = "";
        if (comboLearningPaths.getItemCount() > 0) {
            Object selectedItem = comboLearningPaths.getSelectedItem();
            LearningPathTitulo = (selectedItem != null) ? selectedItem.toString() : "";
        }

        // Botón de inscripción
        JButton btnInscribir = new JButton("Inscribirse");
        btnInscribir.setFont(new Font("Arial", Font.PLAIN, 14));
        btnInscribir.addActionListener(e -> {
            String intereses = textAreaIntereses.getText().trim();
            if (intereses.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, introduzca sus intereses.", "Error", JOptionPane.WARNING_MESSAGE);
            } else {
                try {
                    String recomendaciones = padre.mostrarRecomendacionesYInscribirLearningPath(
                        (String) comboProfesores.getSelectedItem(),
                        intereses,
                        (String) comboLearningPaths.getSelectedItem()
                    );
                    JOptionPane.showMessageDialog(padre.getVentana(), recomendaciones, "Recomendaciones", JOptionPane.INFORMATION_MESSAGE);

                    // Obtener el título del Learning Path inscrito
                    String learningPathTitulo = (String) comboLearningPaths.getSelectedItem();
                    
                    padre.getCardLayout().show(padre.getVentana().getContentPane(), "Funcionalidades");
                    

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        this.add(btnInscribir);
        
        
        

        // Botón para regresar
        JButton btnRegresar = new JButton("Regresar");
        btnRegresar.setFont(new Font("Arial", Font.PLAIN, 14));
        btnRegresar.addActionListener(e -> {
            padre.getCardLayout().show(padre.getVentana().getContentPane(), "Funcionalidades");
        });
        this.add(btnRegresar);
    }


    // Listener para actualizar los Learning Paths según el profesor seleccionado
    private class ProfesorSelectionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String profesorSeleccionado = (String) comboProfesores.getSelectedItem();
            actualizarLearningPaths(profesorSeleccionado);
        }
    }

    // Método para actualizar los Learning Paths según el profesor
    private void actualizarLearningPaths(String profesor) {
        comboLearningPaths.removeAllItems(); 
        
        // Obtener los Learning Paths del profesor seleccionado
		for (Profesor p : padre.getProfesores()) {
			if (p.getNombre().equals(profesor)) {
				for (String lp : p.getLearningPathsCreados().keySet()) {
					comboLearningPaths.addItem(lp);
				}
				break;
			}
		}
       
    }

	public String getLearningPathTitulo() {
		return LearningPathTitulo;
	}
    
}
