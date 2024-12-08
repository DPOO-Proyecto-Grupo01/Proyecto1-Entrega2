package InterfazEstudiante;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import Consola.ConsolaEstudiante;
import Exceptions.LearningPathNoInscrito;
import LearningPaths.LearningPath;
import Usuarios.Estudiante;

public class EnviarFeedback extends JPanel {

    private EstudianteInterfaz padre;
    private JComboBox<String> txtNombreActividad;
    private JComboBox<String> txtNombreLP;
    private JComboBox<String> calificación = new JComboBox<>(new String[]{"1", "2", "3", "4", "5"});
    private JTextField txtFeedback = new JTextField();

    public EnviarFeedback(EstudianteInterfaz elPadre) throws Exception {

        this.padre = elPadre;
        this.setLayout(new BorderLayout());
        this.setBorder(new EmptyBorder(10, 10, 10, 10));
        this.setBackground(Color.WHITE);

        Estudiante e = elPadre.estudianteActual;

        ArrayList<String> learningPathsLista = new ArrayList<>(e.getLearningPathsInscritos().keySet());
        String[] learningPaths = learningPathsLista.toArray(new String[0]);

        txtNombreLP = new JComboBox<>(learningPaths);

        JPanel panelEscoger = new JPanel(new BorderLayout());
        panelEscoger.setBackground(Color.WHITE);
        panelEscoger.setBorder(new TitledBorder("Ver Progreso: "));
        panelEscoger.add(new JLabel("Indique el Learning Path para el que quiere enviar FeedBack: "), BorderLayout.NORTH);
        panelEscoger.add(txtNombreLP, BorderLayout.CENTER);

        JPanel panelFeedback = new JPanel(new GridLayout(2, 2, 10, 10)); // GridLayout para dividir en 2 filas
        panelFeedback.setBackground(Color.WHITE);
        panelFeedback.setBorder(new TitledBorder("Enviar Feedback: "));
        panelFeedback.add(new JLabel("Calificación: ")); // Etiqueta para calificación
        panelFeedback.add(calificación); // JComboBox para calificación
        panelFeedback.add(new JLabel("Feedback: ")); // Etiqueta para feedback
        panelFeedback.add(txtFeedback); // JTextField para feedback

        JButton enviarFeedback = new JButton("Enviar");

        enviarFeedback.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    enviarFeedback();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(EnviarFeedback.this, "Error al enviar feedback: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Agregar paneles al JFrame
        this.add(panelEscoger, BorderLayout.NORTH);
        this.add(panelFeedback, BorderLayout.CENTER);
        
        
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new FlowLayout(FlowLayout.RIGHT));
        panelBotones.setBackground(Color.WHITE);
        this.add(panelBotones, BorderLayout.SOUTH);
        
        
        
        JButton btnRegresar = new JButton("Regresar");
        btnRegresar.setFont(new Font("Arial", Font.PLAIN, 14));
        btnRegresar.addActionListener(event -> {
            padre.getCardLayout().show(padre.getVentana().getContentPane(), "Funcionalidades");
        });
        
        panelBotones.add(btnRegresar);
        panelBotones.add(enviarFeedback);
        
        this.add(panelBotones, BorderLayout.SOUTH);
        

    }

    private void enviarFeedback() throws Exception {
        String lpSeleccionadoS = (String) txtNombreLP.getSelectedItem();
        int calificacion = Integer.parseInt((String) this.calificación.getSelectedItem());
        String feedback = txtFeedback.getText().trim();

        if (!feedback.isEmpty()) {
            ConsolaEstudiante.enviarFeedback(lpSeleccionadoS, feedback, calificacion);
            JOptionPane.showMessageDialog(this, "Feedback enviado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese un feedback antes de enviar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
    }

    public void actionPerformed(ActionEvent e) {
        // Acciones adicionales si son necesarias
    }
}

