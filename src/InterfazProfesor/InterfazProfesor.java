package InterfazProfesor;

import javax.swing.*;
import Consola.ConsolaProfesor;
import java.awt.*;

public class InterfazProfesor extends JFrame {

    private JLabel lblBienvenida;
    private JPanel panelCentral;
    private CardLayout cardLayout;
    private ConsolaProfesor mundo;
    private String usuarioID;

    public InterfazProfesor(String usuarioID) {
        this.usuarioID = usuarioID; 

        mundo = new ConsolaProfesor();
        
        ConsolaProfesor.cargarActividades();
        ConsolaProfesor.cargarLearningPaths();
        ConsolaProfesor.cargarProgresos();
        ConsolaProfesor.cargarProgresoLP();
        ConsolaProfesor.cargarProfesores();
        ConsolaProfesor.cargarLpProfesoress();
        ConsolaProfesor.cargarActividadesLP();
        ConsolaProfesor.cargarEstudiantes();

        setTitle("Interfaz Profesor - Bienvenido " + usuarioID);
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel panelSuperior = new JPanel();
        lblBienvenida = new JLabel("Selecciona una opción");
        lblBienvenida.setFont(new Font("Arial", Font.BOLD, 18));
        panelSuperior.add(lblBienvenida);
        add(panelSuperior, BorderLayout.NORTH);

        cardLayout = new CardLayout();
        panelCentral = new JPanel(cardLayout);

        Botones botonManager = new Botones(cardLayout, panelCentral, this);

        panelCentral.add(botonManager.crearPanelOpciones(), "Menu");
        panelCentral.add(new PanelCrearActividad(cardLayout, panelCentral, lblBienvenida), "CrearActividad");
        panelCentral.add(new PanelCrearLearningPath(cardLayout, panelCentral, lblBienvenida), "CrearLearningPath");
        panelCentral.add(new PanelRevisarEstadodeActividad(cardLayout, panelCentral, lblBienvenida), "RevisarEstadodeActividad");
        panelCentral.add(new PanelVerProgresoEstudiante(cardLayout, panelCentral, lblBienvenida), "VerProgresoEstudiante");
        panelCentral.add(new PanelRevisarFeedback(cardLayout, panelCentral, lblBienvenida), "RevisarFeedback");
        panelCentral.add(new PanelCalcularRating(cardLayout, panelCentral, lblBienvenida), "CalcularRating");

        add(panelCentral, BorderLayout.CENTER);
    }

    public void cambiarMensaje(String mensaje) {
        lblBienvenida.setText(mensaje);
    }

    public void salir() {
        int confirm = JOptionPane.showConfirmDialog(this, "Salir", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            dispose();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            IniciarSesion inicioSesion = new IniciarSesion();
            inicioSesion.setVisible(true);
        });
    }
}
