package InterfazProfesor;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class PanelCrearActividad extends JPanel {

    public PanelCrearActividad(CardLayout cardLayout, JPanel panelCentral, JLabel lblBienvenida) {

        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new BorderLayout());

        JLabel label = new JLabel("Seleccione el tipo de actividad", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(label, BorderLayout.NORTH);

        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblTipoActividad = new JLabel("Tipo de Actividad:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(lblTipoActividad, gbc);

        String[] tiposDeActividad = {"Quiz", "Examen", "Tarea", "Encuesta", "Recurso Educativo"};
        JComboBox<String> comboTipoActividad = new JComboBox<>(tiposDeActividad);
        gbc.gridx = 1;
        inputPanel.add(comboTipoActividad, gbc);

        panel.add(inputPanel, BorderLayout.CENTER);

        JButton btnRegresar = new JButton("Regresar");
        btnRegresar.addActionListener(e -> {
            lblBienvenida.setText("Selecciona una opción");
            cardLayout.show(panelCentral, "Menu");
        });
        panel.add(btnRegresar, BorderLayout.SOUTH);

        JButton btnSiguiente = new JButton("Siguiente");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        inputPanel.add(btnSiguiente, gbc);

        btnSiguiente.addActionListener(e -> {
            String seleccion = (String) comboTipoActividad.getSelectedItem();

            switch (seleccion) {
                case "Quiz":
                    abrirFrameQuiz();
                    break;
                case "Examen":
                    abrirFrameExamen();
                    break;
                case "Tarea":
                    abrirFrameTarea();
                    break;
                case "Encuesta":
                    abrirFrameEncuesta();
                    break;
                case "Recurso Educativo":
                    abrirFrameRecursoEducativo();
                    break;
                default:
                    System.out.println("Selección no válida");
            }
        });

        add(panel, BorderLayout.CENTER);
    }

    private void abrirFrameQuiz() {
        JFrameQuiz frameQuiz = new JFrameQuiz();
        frameQuiz.setVisible(true);
    }

    private void abrirFrameExamen() {
        JFrameExamen frameExamen = new JFrameExamen();
        frameExamen.setVisible(true);
    }

    private void abrirFrameTarea() {
        JFrameTarea frameTarea = new JFrameTarea();
        frameTarea.setVisible(true);
    }

    private void abrirFrameEncuesta() {
        JFrameEncuesta frameEncuesta = new JFrameEncuesta();
        frameEncuesta.setVisible(true);
    }

    private void abrirFrameRecursoEducativo() {
        JFrameRecursoEducativo frameRecursoEducativo = new JFrameRecursoEducativo();
        frameRecursoEducativo.setVisible(true);
    }
}
