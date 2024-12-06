package InterfazProfesor;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import Consola.ConsolaProfesor;

public class PanelRevisarEstadodeActividad extends JPanel {
	
	public PanelRevisarEstadodeActividad(CardLayout cardLayout, JPanel panelCentral, JLabel lblBienvenida) {
		JPanel panel = new JPanel(new BorderLayout());

        JLabel label = new JLabel("Ver Estado Actividad", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(label, BorderLayout.NORTH);

        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel idLabel = new JLabel("ID de la Actividad");
        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(idLabel, gbc);

        JTextField txtId = new JTextField(15);
        gbc.gridx = 1;
        inputPanel.add(txtId, gbc);

        JLabel learningPathLabel = new JLabel("ID del Learning Path:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(learningPathLabel, gbc);

        JTextField txtLearningPath = new JTextField(15);
        gbc.gridx = 1;
        inputPanel.add(txtLearningPath, gbc);

        JButton btnBuscar = new JButton("Buscar");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        inputPanel.add(btnBuscar, gbc);

        JLabel progreso = new JLabel("Estado de la Actividad");
        gbc.gridy = 3;
        inputPanel.add(progreso, gbc);

        panel.add(inputPanel, BorderLayout.CENTER);

        JButton btnRegresar = new JButton("Regresar");
        btnRegresar.addActionListener(e -> {
            lblBienvenida.setText("Selecciona una opción");
            cardLayout.show(panelCentral, "Menu");
        });
        panel.add(btnRegresar, BorderLayout.SOUTH);

        add(panel, BorderLayout.CENTER);

        btnBuscar.addActionListener(e -> {
            String actividadID = txtId.getText();
            String learningPath = txtLearningPath.getText();

            if (!actividadID.isEmpty() && !learningPath.isEmpty()) {
            	ConsolaProfesor.revisarEstadoActividad(actividadID, learningPath);
                progreso.setText("El estado de la actividad " + actividadID + " es " );
            } else {
                progreso.setText("Por favor, completa ambos campos.");
            }
        });
    }

}
