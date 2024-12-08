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
        ConsolaProfesor.cargarFeedback();
        ConsolaProfesor.cargarFeedbackLP();
        ConsolaProfesor.cargarLpProfesoress();
        ConsolaProfesor.cargarActividadesLP();
        ConsolaProfesor.cargarEstudiantes();

        setTitle("Interfaz Profesor - Bienvenido " + usuarioID);
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBackground(new Color(230, 240, 255));
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Panel superior para la imagen
        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setBackground(Color.WHITE);

        JLabel lblImagen = new JLabel();
        ImageIcon imagen = new ImageIcon(getClass().getResource("/imagenesInterfaz/iconoP.jpg"));
        lblImagen.setIcon(new ImageIcon(imagen.getImage().getScaledInstance(200, 150, Image.SCALE_SMOOTH)));
        lblImagen.setHorizontalAlignment(SwingConstants.CENTER);

        lblBienvenida = new JLabel("Selecciona una opción");
        lblBienvenida.setFont(new Font("Arial", Font.BOLD, 18));
        lblBienvenida.setHorizontalAlignment(SwingConstants.CENTER);

        panelSuperior.add(lblImagen, BorderLayout.NORTH);
        panelSuperior.add(lblBienvenida, BorderLayout.SOUTH);
        add(panelSuperior, BorderLayout.NORTH);

        // Panel central para el CardLayout
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
        panelCentral.add(new PanelProgresoGeneral(cardLayout, panelCentral, lblBienvenida), "ProgresoGeneral");

        add(panelCentral, BorderLayout.CENTER);

        // Panel inferior para los botones
        JPanel panelInferior = new JPanel();
        panelInferior.setLayout(new FlowLayout(FlowLayout.CENTER));
        panelInferior.setBackground(Color.WHITE);

        JButton btnSalir = new JButton("Salir");
        btnSalir.addActionListener(event -> salir());
        panelInferior.add(btnSalir);

        add(panelInferior, BorderLayout.SOUTH);
    }

    public void cambiarMensaje(String mensaje) {
        lblBienvenida.setText(mensaje);
    }

    public void salir() {
        int confirm = JOptionPane.showConfirmDialog(this, "¿Deseas salir?", "Confirmar salida", JOptionPane.YES_NO_OPTION);
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
