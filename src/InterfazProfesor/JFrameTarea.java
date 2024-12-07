package InterfazProfesor;

import Consola.ConsolaProfesor;
import Actividades.Pregunta; // Asegúrate de tener esta clase en el paquete correcto
import Actividades.Tarea;     // Asegúrate de tener esta clase en el paquete correcto

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

public class JFrameTarea extends JFrame {

    public JFrameTarea() {
        setTitle("Crear Tarea");
        setSize(600, 800); // Ajuste del tamaño de la ventana
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout());

        JLabel label = new JLabel("Crear una nueva Tarea", JLabel.CENTER);
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
        JTextField duracionEsperadaField = addField(inputPanel, "Duración Esperada (min):", gbc, 4);
        JCheckBox obligatoriaCheck = new JCheckBox();
        addCheckBox(inputPanel, "Obligatoria:", obligatoriaCheck, gbc, 5);
        JTextField fechaLimiteField = addField(inputPanel, "Fecha de entrega (dd/MM/yyyy):", gbc, 6);
        JTextField resenasField = addField(inputPanel, "Reseña:", gbc, 7);
        JTextField resultadoField = addField(inputPanel, "Resultado:", gbc, 8);
        JTextField calificacionMinimaField = addField(inputPanel, "Calificación mínima:", gbc, 9);
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
        gbc.fill = GridBagConstraints.HORIZONTAL;
        inputPanel.add(labelActividadesSeguimiento, gbc);

        JTextField actividadesSeguimientoField = new JTextField();
        gbc.gridy = 14;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        inputPanel.add(actividadesSeguimientoField, gbc);

        // Campos específicos para Tarea
        JLabel labelInstrucciones = new JLabel("Instrucciones:");
        gbc.gridx = 0;
        gbc.gridy = 15;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        inputPanel.add(labelInstrucciones, gbc);

        JTextArea instruccionesArea = new JTextArea(5, 20);
        JScrollPane instruccionesScrollPane = new JScrollPane(instruccionesArea);
        gbc.gridy = 16;
        gbc.weightx = 1.0;
        gbc.weighty = 0.5;
        gbc.fill = GridBagConstraints.BOTH;
        inputPanel.add(instruccionesScrollPane, gbc);

        JLabel labelEstado = new JLabel("Estado:");
        gbc.gridx = 0;
        gbc.gridy = 17;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        inputPanel.add(labelEstado, gbc);

        String[] estados = {"Pendiente", "En Proceso", "Completada"};
        JComboBox<String> estadoComboBox = new JComboBox<>(estados);
        gbc.gridx = 1;
        inputPanel.add(estadoComboBox, gbc);

        // Campo para Preguntas de la Tarea
        JLabel labelPreguntas = new JLabel("Preguntas de la Tarea (una por línea):");
        gbc.gridx = 0;
        gbc.gridy = 18;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        inputPanel.add(labelPreguntas, gbc);
        
        JTextArea preguntasArea = new JTextArea(5, 20);
        JScrollPane preguntasScrollPane = new JScrollPane(preguntasArea);
        gbc.gridy = 19;
        gbc.weightx = 1.0;
        gbc.weighty = 0.5;
        gbc.fill = GridBagConstraints.BOTH;
        inputPanel.add(preguntasScrollPane, gbc);

        JScrollPane scrollPane = new JScrollPane(inputPanel);
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton btnGuardar = new JButton("Guardar Tarea");
        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Obtener y validar datos de los campos
                    String actividadID = actividadIDField.getText().trim();
                    String descripcion = descripcionField.getText().trim();
                    String objetivo = objetivoField.getText().trim();
                    int nivelDificultad = Integer.parseInt(nivelDificultadField.getText().trim());
                    int duracion = Integer.parseInt(duracionEsperadaField.getText().trim());
                    boolean esObligatoria = obligatoriaCheck.isSelected();
                    Date fechaLimite = new SimpleDateFormat("dd/MM/yyyy").parse(fechaLimiteField.getText().trim());
                    String resenas = resenasField.getText().trim();
                    double resultado = Double.parseDouble(resultadoField.getText().trim());
                    int calificacion = Integer.parseInt(calificacionMinimaField.getText().trim());
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

                    // Obtener y procesar preguntas de la tarea
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
                        JOptionPane.showMessageDialog(JFrameTarea.this, "Debe ingresar al menos una pregunta para la tarea.");
                        return;
                    }

                    String instrucciones = instruccionesArea.getText().trim();
                    String estado = (String) estadoComboBox.getSelectedItem();

                    // Preparar parámetros específicos para la Tarea
                    HashMap<String, Object> parametrosEspecificos = new HashMap<>();
                    parametrosEspecificos.put("preguntas", preguntas);
                    parametrosEspecificos.put("instrucciones", instrucciones);
                    parametrosEspecificos.put("estado", estado);

                    // Crear la Tarea a través de ConsolaProfesor
                    ConsolaProfesor.crearActividad(
                            actividadID, descripcion, objetivo, nivelDificultad, duracion, esObligatoria,
                            fechaLimite, resenas, resultado, calificacion, "Tarea", learningPathID,
                            actividadesPrevias, actividadesSeguimiento, parametrosEspecificos, ""
                    );

                    JOptionPane.showMessageDialog(JFrameTarea.this, "Tarea guardada exitosamente.");
                    dispose();

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(JFrameTarea.this, "Error: Verifique que los campos numéricos contengan valores válidos.");
                } catch (ParseException ex) {
                    JOptionPane.showMessageDialog(JFrameTarea.this, "Error: Formato de fecha incorrecto. Use dd/MM/yyyy.");
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(JFrameTarea.this, "Error al guardar la Tarea. Por favor, intente nuevamente.");
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
            JFrameTarea frameTarea = new JFrameTarea();
            frameTarea.setVisible(true);
        });
    }
}
