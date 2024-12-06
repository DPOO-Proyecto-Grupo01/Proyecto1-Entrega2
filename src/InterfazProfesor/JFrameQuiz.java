package InterfazProfesor;

import javax.swing.*;
import Consola.ConsolaProfesor;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class JFrameQuiz extends JFrame {

    public JFrameQuiz() {
        setTitle("Crear Quiz");
        setSize(600, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout());

        JLabel label = new JLabel("Crear un nuevo Quiz", JLabel.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(label, BorderLayout.NORTH);

        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JTextField actividadIDField = addField(inputPanel, "ID de la actividad:", gbc, 0);
        JTextField descripcionField = addField(inputPanel, "Descripción:", gbc, 1);
        JTextField objetivoField = addField(inputPanel, "Objetivo:", gbc, 2);
        JTextField nivelDificultadField = addField(inputPanel, "Nivel de dificultad (0-10):", gbc, 3);
        JTextField duracionField = addField(inputPanel, "Duración (min):", gbc, 4);
        JCheckBox obligatoriaCheck = new JCheckBox();
        addCheckBox(inputPanel, "Obligatoria:", obligatoriaCheck, gbc, 5);
        JTextField fechaLimiteField = addField(inputPanel, "Fecha de entrega (dd/MM/yyyy):", gbc, 6);
        JTextField resenasField = addField(inputPanel, "Reseña:", gbc, 7);
        JTextField resultadoField = addField(inputPanel, "Resultado:", gbc, 8);
        JTextField calificacionField = addField(inputPanel, "Calificación mínima:", gbc, 9);
        JTextField learningPathField = addField(inputPanel, "Learning Path ID:", gbc, 10);

        JScrollPane scrollPane = new JScrollPane(inputPanel);
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton btnGuardar = new JButton("Guardar Quiz");
        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String actividadID = actividadIDField.getText();
                    String descripcion = descripcionField.getText();
                    String objetivo = objetivoField.getText();
                    int nivelDificultad = Integer.parseInt(nivelDificultadField.getText());
                    int duracion = Integer.parseInt(duracionField.getText());
                    boolean esObligatoria = obligatoriaCheck.isSelected();
                    Date fechaLimite = new SimpleDateFormat("dd/MM/yyyy").parse(fechaLimiteField.getText());
                    String resenas = resenasField.getText();
                    double resultado = Double.parseDouble(resultadoField.getText());
                    int calificacion = Integer.parseInt(calificacionField.getText());
                    String learningPathID = learningPathField.getText();

                    HashMap<String, Object> parametrosEspecificos = new HashMap<>();
                    parametrosEspecificos.put("Preguntas", new ArrayList<>()); 

                    ConsolaProfesor.crearActividad(
                            actividadID, descripcion, objetivo, nivelDificultad, duracion, esObligatoria,
                            fechaLimite, resenas, resultado, calificacion, "Quiz", learningPathID,
                            new ArrayList<>(), new ArrayList<>(), parametrosEspecificos, "Quiz"
                    );

                    JOptionPane.showMessageDialog(JFrameQuiz.this, "Quiz guardado exitosamente.");
                    dispose();

                } catch (NumberFormatException | ParseException ex) {
                    JOptionPane.showMessageDialog(JFrameQuiz.this, "Error: Verifique los datos ingresados.");
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(JFrameQuiz.this, "Error al guardar el Quiz.");
                }
            }
        });

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(e -> dispose());

        buttonPanel.add(btnGuardar);
        buttonPanel.add(btnCancelar);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        add(panel);
    }

    private JTextField addField(JPanel panel, String labelText, GridBagConstraints gbc, int row) {
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(new JLabel(labelText), gbc);

        JTextField textField = new JTextField(15);
        gbc.gridx = 1;
        panel.add(textField, gbc);

        return textField;
    }

    private void addCheckBox(JPanel panel, String labelText, JCheckBox checkBox, GridBagConstraints gbc, int row) {
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(new JLabel(labelText), gbc);

        gbc.gridx = 1;
        panel.add(checkBox, gbc);
    }

    public static void main(String[] args) {
        JFrameQuiz frameQuiz = new JFrameQuiz();
        frameQuiz.setVisible(true);
    }
}
