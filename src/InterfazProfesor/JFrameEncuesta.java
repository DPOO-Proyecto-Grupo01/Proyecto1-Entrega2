package InterfazProfesor;

import Consola.ConsolaProfesor;
import javax.swing.*;
import Actividades.Actividad;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

public class JFrameEncuesta extends JFrame {

    public JFrameEncuesta() {
        setTitle("Crear Encuesta");
        setSize(600, 700); 
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout());

        JLabel label = new JLabel("Crear una nueva Encuesta", JLabel.CENTER);
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

        JLabel labelActividadesPrevias = new JLabel("Actividades Previas (separadas por comas):");
        gbc.gridx = 0;
        gbc.gridy = 11;
        gbc.gridwidth = 2;
        inputPanel.add(labelActividadesPrevias, gbc);

        JTextField actividadesPreviasField = new JTextField();
        gbc.gridy = 12;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        inputPanel.add(actividadesPreviasField, gbc);

        JLabel labelActividadesSeguimiento = new JLabel("Actividades de Seguimiento (separadas por comas):");
        gbc.gridx = 0;
        gbc.gridy = 13;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        inputPanel.add(labelActividadesSeguimiento, gbc);

        JTextField actividadesSeguimientoField = new JTextField();
        gbc.gridy = 14;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        inputPanel.add(actividadesSeguimientoField, gbc);

        JLabel labelPreguntas = new JLabel("Preguntas de la Encuesta (una por línea):");
        gbc.gridx = 0;
        gbc.gridy = 15;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        inputPanel.add(labelPreguntas, gbc);

        JTextArea preguntasArea = new JTextArea(5, 20);
        JScrollPane preguntasScrollPane = new JScrollPane(preguntasArea);
        gbc.gridy = 16;
        gbc.weightx = 1.0;
        gbc.weighty = 0.5;
        gbc.fill = GridBagConstraints.BOTH;
        inputPanel.add(preguntasScrollPane, gbc);

        JScrollPane scrollPane = new JScrollPane(inputPanel);
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton btnGuardar = new JButton("Guardar Encuesta");
        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String actividadID = actividadIDField.getText().trim();
                    String descripcion = descripcionField.getText().trim();
                    String objetivo = objetivoField.getText().trim();
                    int nivelDificultad = Integer.parseInt(nivelDificultadField.getText().trim());
                    int duracion = Integer.parseInt(duracionField.getText().trim());
                    boolean esObligatoria = obligatoriaCheck.isSelected();
                    Date fechaLimite = new SimpleDateFormat("dd/MM/yyyy").parse(fechaLimiteField.getText().trim());
                    String resenas = resenasField.getText().trim();
                    double resultado = Double.parseDouble(resultadoField.getText().trim());
                    int calificacion = Integer.parseInt(calificacionField.getText().trim());
                    String learningPathID = learningPathField.getText().trim();

                    String actividadesPreviasTexto = actividadesPreviasField.getText().trim();
                    List<String> actividadesPrevias = new ArrayList<>();
                    if (!actividadesPreviasTexto.isEmpty()) {
                        String[] actividadesArray = actividadesPreviasTexto.split(",");
                        for (String actividad : actividadesArray) {
                            String actividadTrim = actividad.trim();
                            if (!actividadTrim.isEmpty()) {
                                actividadesPrevias.add(actividadTrim);
                            }
                        }
                    }

                    String actividadesSeguimientoTexto = actividadesSeguimientoField.getText().trim();
                    List<String> actividadesSeguimiento = new ArrayList<>();
                    if (!actividadesSeguimientoTexto.isEmpty()) {
                        String[] actividadesArray = actividadesSeguimientoTexto.split(",");
                        for (String actividad : actividadesArray) {
                            String actividadTrim = actividad.trim();
                            if (!actividadTrim.isEmpty()) {
                                actividadesSeguimiento.add(actividadTrim);
                            }
                        }
                    }

                    String preguntasTexto = preguntasArea.getText().trim();
                    List<String> preguntas = new ArrayList<>();
                    if (!preguntasTexto.isEmpty()) {
                        String[] preguntasArray = preguntasTexto.split("\n");
                        for (String pregunta : preguntasArray) {
                            String preguntaTrim = pregunta.trim();
                            if (!preguntaTrim.isEmpty()) {
                                preguntas.add(preguntaTrim);
                            }
                        }
                    }

                    if (preguntas.isEmpty()) {
                        JOptionPane.showMessageDialog(JFrameEncuesta.this, "Debe ingresar al menos una pregunta para la encuesta.");
                        return;
                    }

                    HashMap<String, Object> parametrosEspecificos = new HashMap<>();
                    parametrosEspecificos.put("Preguntas", preguntas); 

                    ConsolaProfesor.crearActividad(
                            actividadID, descripcion, objetivo, nivelDificultad, duracion, esObligatoria,
                            fechaLimite, resenas, resultado, calificacion, "Encuesta", learningPathID,
                            actividadesPrevias, actividadesSeguimiento, parametrosEspecificos, ""
                    );

                    JOptionPane.showMessageDialog(JFrameEncuesta.this, "Encuesta guardada exitosamente.");
                    dispose();

                } catch (NumberFormatException | ParseException ex) {
                    JOptionPane.showMessageDialog(JFrameEncuesta.this, "Error: Verifique los datos ingresados.");
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(JFrameEncuesta.this, "Error al guardar la Encuesta.");
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
        gbc.gridwidth = 1;
        panel.add(new JLabel(labelText), gbc);

        JTextField textField = new JTextField(15);
        gbc.gridx = 1;
        panel.add(textField, gbc);

        return textField;
    }

    private void addCheckBox(JPanel panel, String labelText, JCheckBox checkBox, GridBagConstraints gbc, int row) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 1;
        panel.add(new JLabel(labelText), gbc);

        gbc.gridx = 1;
        panel.add(checkBox, gbc);
    }

    public static void main(String[] args) {
        JFrameEncuesta frameEncuesta = new JFrameEncuesta();
        frameEncuesta.setVisible(true);
    }
}
