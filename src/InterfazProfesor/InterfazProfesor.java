package InterfazProfesor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InterfazProfesor extends JFrame {

    private JButton btnCrearActividad, btnCrearLearningPath, btnRevisarEstadoActividad;
    private JButton btnVerProgresoEstudiante, btnRevisarFeedback, btnCalcularRating, btnSalir;
    private JLabel lblBienvenida;

    public InterfazProfesor(String profesorNombre) {
        setTitle("Interfaz Profesor - Bienvenido ");
        setSize(1000, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel panelSuperior = new JPanel();
        lblBienvenida = new JLabel("Selecciona una opcion");
        lblBienvenida.setFont(new Font("Arial", Font.BOLD, 18));
        panelSuperior.add(lblBienvenida);
        add(panelSuperior, BorderLayout.NORTH);

        JPanel panelCentral = new JPanel();
        panelCentral.setLayout(new GridLayout(7, 1, 10, 10));

        btnCrearActividad = new JButton("Crear Actividad");
        btnCrearLearningPath = new JButton("Crear Learning Path");
        btnRevisarEstadoActividad = new JButton("Revisar Estado de Actividad");
        btnVerProgresoEstudiante = new JButton("Ver Progreso de Estudiante");
        btnRevisarFeedback = new JButton("Revisar Feedback");
        btnCalcularRating = new JButton("Calcular Rating");
        btnSalir = new JButton("Salir");

        panelCentral.add(btnCrearActividad);
        panelCentral.add(btnCrearLearningPath);
        panelCentral.add(btnRevisarEstadoActividad);
        panelCentral.add(btnVerProgresoEstudiante);
        panelCentral.add(btnRevisarFeedback);
        panelCentral.add(btnCalcularRating);
        panelCentral.add(btnSalir);

        add(panelCentral, BorderLayout.CENTER);

        btnCrearActividad.addActionListener(e -> crearActividad());
        btnCrearLearningPath.addActionListener(e -> crearLearningPath());
        btnRevisarEstadoActividad.addActionListener(e -> revisarEstadoActividad());
        btnVerProgresoEstudiante.addActionListener(e -> verProgresoEstudiante());
        btnRevisarFeedback.addActionListener(e -> revisarFeedback());
        btnCalcularRating.addActionListener(e -> calcularRating());
        btnSalir.addActionListener(e -> salir());
    }

    private void crearActividad() {
       
    }

    private void crearLearningPath() {
        
    }

    private void revisarEstadoActividad() {
        
    }

    private void verProgresoEstudiante() {
        
    }

    private void revisarFeedback() {
       
    }

    private void calcularRating() {
        
    }

    private void salir() {
        int confirm = JOptionPane.showConfirmDialog(this, "Salir", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            dispose();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            InterfazProfesor interfaz = new InterfazProfesor("Profesor Ejemplo");
            interfaz.setVisible(true);
        });
    }
}
