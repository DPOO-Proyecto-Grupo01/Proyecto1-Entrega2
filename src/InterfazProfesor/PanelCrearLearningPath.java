package InterfazProfesor;

import javax.swing.*;

import Consola.ConsolaProfesor;
import Exceptions.NombreRepetido;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class PanelCrearLearningPath extends JPanel {

	
    public PanelCrearLearningPath(CardLayout cardLayout, JPanel panelCentral, JLabel lblBienvenida) {
        setLayout(new BorderLayout());

        JLabel label = new JLabel("Crear Learning Path", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 14));
        add(label, BorderLayout.NORTH);
        
        JLabel id = new JLabel("Id del Learning Path");
        JTextField txtId = new JTextField();
        JLabel titulo = new JLabel("Título del Learning Path");
        JTextField txtTitulo = new JTextField();
        JLabel descripcion = new JLabel("Descripción del Learning Path");
        JTextField txtDescripcion = new JTextField();
        JLabel objetivo = new JLabel("Objetivo del Learning Path");
        JTextField txtObjetivo = new JTextField();
        JLabel nivel = new JLabel("Nivel de dificultad del Learning Path");
        JTextField txtNivel = new JTextField();
        JLabel duracion = new JLabel("Duración del Learning Path");
        JTextField txtDuracion = new JTextField();
        JLabel idPro = new JLabel("Id del Profesor");
        JTextField txtIdPro = new JTextField();
        JLabel actividades = new JLabel("Actividades del Learning Path");
        JTextField txtActividades = new JTextField();
        JLabel intereses = new JLabel("Intereses del Learning Path");
        JTextField txtIntereses = new JTextField();
        
        JPanel panelCentral1 = new JPanel(new GridLayout(10, 2, 10, 10));
        panelCentral1.add(id);
        panelCentral1.add(txtId);
        panelCentral1.add(titulo);
        panelCentral1.add(txtTitulo);
        panelCentral1.add(descripcion);
        panelCentral1.add(txtDescripcion);
        panelCentral1.add(objetivo);
        panelCentral1.add(txtObjetivo);
        panelCentral1.add(nivel);
        panelCentral1.add(txtNivel);
        panelCentral1.add(duracion);
        panelCentral1.add(txtDuracion);
        panelCentral1.add(idPro);
        panelCentral1.add(txtIdPro);
        panelCentral1.add(actividades);
        panelCentral1.add(txtActividades);
        panelCentral1.add(intereses);
        panelCentral1.add(txtIntereses);
        
        add(panelCentral1, BorderLayout.CENTER);
        
        JButton btnCrear = new JButton("Crear");
        
        panelCentral1.add(btnCrear);
        
        btnCrear.addActionListener(e -> {
            String id1 = txtId.getText();
            String titulo1 = txtTitulo.getText();
            String descripcion1 = txtDescripcion.getText();
            String objetivo1 = txtObjetivo.getText();
            String nivel1 = txtNivel.getText();
            String duracion1 = txtDuracion.getText();
            String idPro1 = txtIdPro.getText();
            String actividades1 = txtActividades.getText();
            String intereses1 = txtIntereses.getText();

            try {
                int nivel2 = Integer.parseInt(nivel1);
                int duracion2 = Integer.parseInt(duracion1);

                List<String> actividadesList = Arrays.asList(actividades1.split(","));
                List<String> interesesList = Arrays.asList(intereses1.split(","));

                ConsolaProfesor.crearLearningPath(id1, titulo1, descripcion1, objetivo1,
                        nivel2, duracion2, idPro1, actividadesList, interesesList);

                JOptionPane.showMessageDialog(this, "Learning Path creado", "Información", JOptionPane.INFORMATION_MESSAGE);

                txtId.setText("");
                txtTitulo.setText("");
                txtDescripcion.setText("");
                txtObjetivo.setText("");
                txtNivel.setText("");
                txtDuracion.setText("");
                txtIdPro.setText("");
                txtActividades.setText("");
                txtIntereses.setText("");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Error: Nivel y duración deben ser números", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (NombreRepetido e1) {
                e1.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error: Nombre repetido", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        JButton btnRegresar = new JButton("Regresar");
        
        add(btnRegresar, BorderLayout.SOUTH);
        btnRegresar.addActionListener(e -> {
            lblBienvenida.setText("Selecciona una opción");
            cardLayout.show(panelCentral, "Menu"); 
        });

    }
}