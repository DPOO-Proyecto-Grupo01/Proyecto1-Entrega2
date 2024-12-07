package InterfazProfesor;

import Consola.ConsolaProfesor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;


/**
 * Clase JFrameQuiz para crear un nuevo Quiz.
 */
public class JFrameQuiz extends JFrame {

    public JFrameQuiz() {
        setTitle("Crear Quiz");
        setSize(600, 900); // Ajuste del tamaño de la ventana
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout());

        JLabel label = new JLabel("Crear un nuevo Quiz", JLabel.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(label, BorderLayout.NORTH);

        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Campos existentes
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

        // Campo para Actividades Previas
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

        // Campo para Actividades de Seguimiento
        JLabel labelActividadesSeguimiento = new JLabel("Actividades de Seguimiento (separadas por comas):");
        gbc.gridx = 0;
        gbc.gridy = 13;
        gbc.gridwidth = 2;
        inputPanel.add(labelActividadesSeguimiento, gbc);

        JTextField actividadesSeguimientoField = new JTextField();
        gbc.gridy = 14;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        inputPanel.add(actividadesSeguimientoField, gbc);

        // Campos específicos para Quiz
        JLabel labelPreguntas = new JLabel("Preguntas del Quiz (una por línea):");
        gbc.gridx = 0;
        gbc.gridy = 15;
        gbc.gridwidth = 2;
        inputPanel.add(labelPreguntas, gbc);

        JTextArea preguntasArea = new JTextArea(5, 20);
        JScrollPane preguntasScrollPane = new JScrollPane(preguntasArea);
        gbc.gridy = 16;
        gbc.weightx = 1.0;
        gbc.weighty = 0.5;
        gbc.fill = GridBagConstraints.BOTH;
        inputPanel.add(preguntasScrollPane, gbc);

        JLabel labelRespuestas = new JLabel("Respuestas Correctas (una por línea):");
        gbc.gridx = 0;
        gbc.gridy = 17;
        gbc.gridwidth = 2;
        inputPanel.add(labelRespuestas, gbc);

        JTextArea respuestasArea = new JTextArea(5, 20);
        JScrollPane respuestasScrollPane = new JScrollPane(respuestasArea);
        gbc.gridy = 18;
        gbc.weightx = 1.0;
        gbc.weighty = 0.5;
        gbc.fill = GridBagConstraints.BOTH;
        inputPanel.add(respuestasScrollPane, gbc);

        JScrollPane scrollPane = new JScrollPane(inputPanel);
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton btnGuardar = new JButton("Guardar Quiz");
        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Obtener y validar datos de los campos
                    String actividadID = actividadIDField.getText().trim();
                    String descripcion = descripcionField.getText().trim();
                    String objetivo = objetivoField.getText().trim();
                    int nivelDificultad = Integer.parseInt(nivelDificultadField.getText().trim());
                    int duracion = Integer.parseInt(duracionField.getText().trim());
                    boolean esObligatoria = obligatoriaCheck.isSelected();
                    Date fechaLimite = new SimpleDateFormat("dd/MM/yyyy").parse(fechaLimiteField.getText().trim());
                    String resenas = resenasField.getText().trim();
                    double resultado = Double.parseDouble(resultadoField.getText().trim());
                    int calificacionMinima = Integer.parseInt(calificacionField.getText().trim());
                    String learningPathID = learningPathField.getText().trim();

                    // Validación de campos obligatorios
                    if (actividadID.isEmpty()) {
                        JOptionPane.showMessageDialog(JFrameQuiz.this, "Debe ingresar el ID de la actividad.");
                        return;
                    }
                    if (descripcion.isEmpty()) {
                        JOptionPane.showMessageDialog(JFrameQuiz.this, "Debe ingresar una descripción.");
                        return;
                    }
                    if (objetivo.isEmpty()) {
                        JOptionPane.showMessageDialog(JFrameQuiz.this, "Debe ingresar un objetivo.");
                        return;
                    }

                    // Obtener y procesar actividades previas
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

                    // Obtener y procesar actividades de seguimiento
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

                    // Obtener y procesar preguntas del quiz
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

                    // Obtener y procesar respuestas correctas
                    String respuestasTexto = respuestasArea.getText().trim();
                    List<String> respuestasCorrectas = new ArrayList<>();
                    if (!respuestasTexto.isEmpty()) {
                        String[] respuestasArray = respuestasTexto.split("\n");
                        for (String respuesta : respuestasArray) {
                            String respuestaTrim = respuesta.trim();
                            if (!respuestaTrim.isEmpty()) {
                                respuestasCorrectas.add(respuestaTrim);
                            }
                        }
                    }

                    if (preguntas.size() != respuestasCorrectas.size()) {
                        JOptionPane.showMessageDialog(JFrameQuiz.this, "El número de preguntas debe coincidir con el de respuestas correctas.");
                        return;
                    }

                    if (preguntas.isEmpty()) {
                        JOptionPane.showMessageDialog(JFrameQuiz.this, "Debe ingresar al menos una pregunta para el quiz.");
                        return;
                    }

                    HashMap<String, Object> parametrosEspecificos = new HashMap<>();
                    parametrosEspecificos.put("preguntas", preguntas);
                    parametrosEspecificos.put("respuestasCorrectas", respuestasCorrectas);
                    parametrosEspecificos.put("calificacionMinima", calificacionMinima);

                    // Crear el Quiz a través de ConsolaProfesor
                    ConsolaProfesor.crearActividad(
                            actividadID, descripcion, objetivo, nivelDificultad, duracion, esObligatoria,
                            fechaLimite, resenas, resultado, calificacionMinima, "Quiz", learningPathID,
                            actividadesPrevias, actividadesSeguimiento, parametrosEspecificos, ""
                    );

                    JOptionPane.showMessageDialog(JFrameQuiz.this, "Quiz guardado exitosamente.");
                    dispose();

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(JFrameQuiz.this, "Error: Verifique que los campos numéricos contengan valores válidos.");
                } catch (ParseException ex) {
                    JOptionPane.showMessageDialog(JFrameQuiz.this, "Error: Formato de fecha incorrecto. Use dd/MM/yyyy.");
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
        gbc.gridwidth = 1;
        panel.add(new JLabel(labelText), gbc);

        JTextField textField = new JTextField(20);
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
        SwingUtilities.invokeLater(() -> {
            JFrameQuiz frameQuiz = new JFrameQuiz();
            frameQuiz.setVisible(true);
        });
    }
}
