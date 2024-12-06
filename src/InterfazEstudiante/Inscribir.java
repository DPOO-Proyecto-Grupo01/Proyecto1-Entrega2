package InterfazEstudiante;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

public class Inscribir extends JPanel {

    private JComboBox<String> comboProfesores;
    private JComboBox<String> comboLearningPaths;
    private EstudianteInterfaz padre;

    public Inscribir(EstudianteInterfaz elPadre) {
        padre = elPadre;

        // Configurar diseño principal
        this.setLayout(new GridLayout(3, 1, 10, 10));
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

        List<String> profesores = padre.profesores();
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
        
        JButton btnInscribir = new JButton("Inscribirse");
        btnInscribir.setFont(new Font("Arial", Font.PLAIN, 14));
		btnInscribir.addActionListener(e -> {
			JOptionPane.showMessageDialog(this, "Inscripción exitosa", "Inscripción", JOptionPane.INFORMATION_MESSAGE);
			
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

        
        String[] learningPaths;
        switch (profesor) {
            case "Profesor 1":
                learningPaths = new String[]{"LP1 - Matemáticas", "LP2 - Física"};
                break;
            case "Profesor 2":
                learningPaths = new String[]{"LP3 - Química", "LP4 - Biología"};
                break;
            case "Profesor 3":
                learningPaths = new String[]{"LP5 - Historia", "LP6 - Literatura"};
                break;
            default:
                learningPaths = new String[]{};
        }

        // Agregar los Learning Paths al JComboBox
        for (String lp : learningPaths) {
            comboLearningPaths.addItem(lp);
        }
    }
}
