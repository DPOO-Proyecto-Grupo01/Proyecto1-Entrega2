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

public class JFrameExamen extends JFrame {

    public JFrameExamen() {
        setTitle("Crear Examen");
        setSize(600, 800); // Ajuste del tamaño de la ventana
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout());

        JLabel label = new JLabel("Crear un nuevo Examen", JLabel.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(label, BorderLayout.NORTH);

        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
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
        JTextField calificacionMinima = addField(inputPanel, "Calificación mínima:", gbc, 9);
        JTextField learningPathField = addField(inputPanel, "Learning Path ID:", gbc, 10);

        // Campo para Actividades Previas
        JLabel labelActividadesPrevias = new JLabel("Actividades Previas (separadas por comas):");
        gbc.gridx = 0;
        gbc.gridy = 12;
        gbc.gridwidth = 2;
        inputPanel.add(labelActividadesPrevias, gbc);

        JTextField actividadesPreviasField = new JTextField();
        gbc.gridy = 13;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        inputPanel.add(actividadesPreviasField, gbc);

        // Campo para Actividades de Seguimiento
        JLabel labelActividadesSeguimiento = new JLabel("Actividades de Seguimiento (separadas por comas):");
        gbc.gridx = 0;
        gbc.gridy = 14;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        inputPanel.add(labelActividadesSeguimiento, gbc);

        JTextField actividadesSeguimientoField = new JTextField();
        gbc.gridy = 15;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        inputPanel.add(actividadesSeguimientoField, gbc);

     
        JLabel labelCalificacionMinimaEspecifica = new JLabel("Calificación Mínima Específica:");
        gbc.gridx = 0;
        gbc.gridy = 20;
        gbc.gridwidth = 1;
        inputPanel.add(labelCalificacionMinimaEspecifica, gbc);

        JTextField calificacionMinimaEspecificaField = new JTextField(15);
        gbc.gridx = 1;
        inputPanel.add(calificacionMinimaEspecificaField, gbc);

        // Campo para Preguntas del Examen
        JLabel labelPreguntas = new JLabel("Preguntas del Examen (una por línea):");
        gbc.gridx = 0;
        gbc.gridy = 21;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        inputPanel.add(labelPreguntas, gbc);
        
        JTextArea preguntasArea = new JTextArea(5, 20);
        JScrollPane preguntasScrollPane = new JScrollPane(preguntasArea);
        gbc.gridy = 22;
        gbc.weightx = 1.0;
        gbc.weighty = 0.5;
        gbc.fill = GridBagConstraints.BOTH;
        inputPanel.add(preguntasScrollPane, gbc);

        JScrollPane scrollPane = new JScrollPane(inputPanel);
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton btnGuardar = new JButton("Guardar Examen");
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
                    int calificacion = Integer.parseInt(calificacionMinima.getText().trim());
                    String learningPathID = learningPathField.getText().trim();

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



                    // Obtener y procesar preguntas del examen
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
                        JOptionPane.showMessageDialog(JFrameExamen.this, "Debe ingresar al menos una pregunta para el examen.");
                        return;
                    }


                    // Obtener calificación mínima específica
                    String calificacionMinimaEspecificaStr = calificacionMinimaEspecificaField.getText().trim();
                    if (calificacionMinimaEspecificaStr.isEmpty()) {
                        JOptionPane.showMessageDialog(JFrameExamen.this, "Debe ingresar una calificación mínima específica.");
                        return;
                    }
                    int calificacionMinimaEspecifica = Integer.parseInt(calificacionMinimaEspecificaStr);

                    HashMap<String, Object> parametrosEspecificos = new HashMap<>();
                    parametrosEspecificos.put("preguntas", preguntas);
                    parametrosEspecificos.put("calificacionMinimaEspecifica", calificacionMinimaEspecifica);

                    ConsolaProfesor.crearActividad(
                            actividadID, descripcion, objetivo, nivelDificultad, duracion, esObligatoria,
                            fechaLimite, resenas, resultado, calificacion, "Examen", learningPathID,
                            actividadesPrevias, actividadesSeguimiento, parametrosEspecificos, ""
                    );

                    JOptionPane.showMessageDialog(JFrameExamen.this, "Examen guardado exitosamente.");
                    dispose();

                } catch (NumberFormatException | ParseException ex) {
                    JOptionPane.showMessageDialog(JFrameExamen.this, "Error: Verifique los datos ingresados.");
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(JFrameExamen.this, "Error al guardar el Examen.");
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
        SwingUtilities.invokeLater(() -> {
            JFrameExamen frameExamen = new JFrameExamen();
            frameExamen.setVisible(true);
        });
    }
}
