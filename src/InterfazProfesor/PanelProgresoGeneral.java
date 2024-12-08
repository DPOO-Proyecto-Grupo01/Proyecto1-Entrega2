package InterfazProfesor;

import javax.swing.*;

import InterfazEstudiante.CompletarActividad;

import java.awt.*;
import java.util.Calendar;

public class PanelProgresoGeneral extends JPanel {
    private int[][] actividades; // Matriz de actividades
    private static final int FILAS = 7; // Días de la semana
    private static final int COLUMNAS = 52; // Semanas del año
    private CompletarActividad completarActividad;

    public PanelProgresoGeneral(CardLayout cardLayout, JPanel panelCentral, JLabel lblBienvenida, CompletarActividad completarActividad) {
        this.actividades = new int[FILAS][COLUMNAS];
        this.completarActividad = completarActividad;

        setLayout(new BorderLayout());

        // Crear el panel para la matriz
        CasillasPanel casillasPanel = new CasillasPanel();
        add(casillasPanel, BorderLayout.CENTER);

        // Crear un panel superior con los meses
        JPanel mesesPanel = crearMesesPanel();
        add(mesesPanel, BorderLayout.NORTH);

        // Botones inferiores
        JPanel botonesPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton btnCompletar = new JButton("Completar Actividad");
        btnCompletar.addActionListener(e -> {
            completarActividadEnProgreso();
            casillasPanel.repaint(); // Actualizar el gráfico
        });
        JButton btnSalir = new JButton("Salir");
        btnSalir.addActionListener(e -> System.exit(0));
        botonesPanel.add(btnCompletar);
        botonesPanel.add(btnSalir);

        add(botonesPanel, BorderLayout.SOUTH);
    }

    private JPanel crearMesesPanel() {
        JPanel panelMeses = new JPanel(new GridLayout(1, 12)); // 12 meses
        panelMeses.setBackground(Color.WHITE);

        String[] meses = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};
        int[] semanasPorMes = {4, 4, 5, 4, 4, 5, 4, 4, 5, 4, 4, 5};

        for (int i = 0; i < meses.length; i++) {
            JLabel labelMes = new JLabel(meses[i], SwingConstants.CENTER);
            labelMes.setFont(new Font("Arial", Font.BOLD, 12));
            labelMes.setPreferredSize(new Dimension(semanasPorMes[i] * 20, 20));
            panelMeses.add(labelMes);
        }

        return panelMeses;
    }

    private void completarActividadEnProgreso() {
        // Completar actividad usando la clase `CompletarActividad`
        int dia = (int) (Math.random() * FILAS); // Día aleatorio
        int semana = (int) (Math.random() * COLUMNAS); // Semana aleatoria
        actividades[dia][semana]++;
    }

    // Panel personalizado para dibujar las casillas
    private class CasillasPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int anchoCasilla = 15;
            int altoCasilla = 15;
            int margen = 5;

            int inicioX = (getWidth() - ((COLUMNAS * (anchoCasilla + margen)) - margen)) / 2;
            int inicioY = 50;

            for (int fila = 0; fila < FILAS; fila++) {
                for (int columna = 0; columna < COLUMNAS; columna++) {
                    int x = inicioX + columna * (anchoCasilla + margen);
                    int y = inicioY + fila * (altoCasilla + margen);

                    int valor = actividades[fila][columna];
                    Color color = obtenerColor(valor);
                    g2d.setColor(color);
                    g2d.fillRect(x, y, anchoCasilla, altoCasilla);
                }
            }
        }

        private Color obtenerColor(int valor) {
            if (valor == 0) return Color.LIGHT_GRAY;
            else if (valor == 1) return new Color(144, 238, 144); // Verde claro
            else if (valor == 2) return new Color(60, 179, 113); // Verde medio
            else return new Color(34, 139, 34); // Verde oscuro
        }
    }
}
