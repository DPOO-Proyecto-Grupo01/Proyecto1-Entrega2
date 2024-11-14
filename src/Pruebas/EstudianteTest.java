package Pruebas;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Actividades.*;
import Exceptions.*;
import LearningPaths.*;
import Usuarios.*;

class EstudianteTest {

    private Estudiante estudiante;
    private Profesor profesor;
    private LearningPath learningPath;

    @BeforeEach
    void setUp() throws NombreRepetido {
        estudiante = new Estudiante("U105", "Juan Perez", "1234", "J.perez@uniandes.edu.co", "Estudiante");
        profesor = new Profesor("P105", "Carlos Perez", "1234", "C.perez@uniandes.edu.co", "Profesor");

        ArrayList<String> actividadesID = new ArrayList<>();
        actividadesID.add("A101");

        ArrayList<String> intereses = new ArrayList<>();
        intereses.add("Java");

        learningPath = new LearningPath("LP106", "Aprendiendo a programar en Java", "Descripcion", "Objetivos", 3, 120, "P105", actividadesID, intereses);
        profesor.getLearningPathsCreados().put("LP106", learningPath);

        estudiante.inscribirProfesor(profesor, "P105");
    }

    
    @Test
    void testInscribirLearningPath() throws LearningPathNoInscrito {
        estudiante.inscribirLearningPath("LP106", "P105");
        
        Map<String, LearningPath> learningPaths = estudiante.getLearningPathsInscritos();
        
        String expectedLearningPathID = "LP106_" + estudiante.getUsuarioID();
        
        assertTrue(learningPaths.containsKey(expectedLearningPathID));
    }


    @Test
    void testInscribirLearningPathInvalido() {
        assertThrows(LearningPathNoInscrito.class, () -> {
            estudiante.inscribirLearningPath("LP999", "P105");
        });
    }

    @Test
    void testCompletarActividad() throws Exception {
        // Ensure activity is added to the learning path
        Quiz actividad = new Quiz("A101", "Descripcion", "Objetivo", 3, 120, true, new Date(), "reseña", 0, 0, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), 0.5, "A");
        learningPath.getActividades().put("A101", actividad);
        
        estudiante.inscribirLearningPath("LP106", "P105");

        Actividad resultado = estudiante.completarActividad("A101_U105", "LP106_U105");
        assertNotNull(resultado, "Activity result should not be null");
        assertEquals("Exitoso", resultado.getEstado());
    }

    @Test
    void testCompletarActividadYaCompletada() throws Exception {
        Quiz actividad = new Quiz("A101", "Descripcion", "Objetivo", 3, 120, true, new Date(), "reseña", 0, 0, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), 0.5, "A");
        actividad.setEstado("Exitoso");
        learningPath.getActividades().put("A101", actividad);
        
        estudiante.inscribirLearningPath("LP106", "P105");

        assertThrows(YaSeCompleto.class, () -> {
            estudiante.completarActividad("A101_U105", "LP106_U105");
        });
    }

    @Test
    void testEnviarFeedback() throws LearningPathNoInscrito {
        estudiante.inscribirLearningPath("LP106", "P105");
        estudiante.enviarFeedback("LP106", "Excelente curso", 5, "FB001");

        Feedback feedback = learningPath.getFeedback().get(0);
        assertEquals("Excelente curso", feedback.getComentario());
        assertEquals(5, feedback.getCalificacion());
    }

    @Test
    void testObtenerRecomendacion() {
        String recomendacion = estudiante.obtenerRecomendacion("Java", "P105");
        assertTrue(recomendacion.contains("Aprendiendo a programar en Java"));
    }

    @Test
    void testGetProgresoLearningPath() throws LearningPathNoInscrito {
        estudiante.inscribirLearningPath("LP106", "P105");
        double progreso = estudiante.getProgresoLearningPath("LP106_U105");
        System.out.println(progreso);
        assertEquals(0.0, progreso);
    }

    @Test
    void testActividadesDisponibles() throws LearningPathNoInscrito {
        estudiante.inscribirLearningPath("LP106", "P105");
        Quiz actividad = new Quiz("A101", "Descripcion", "Objetivo", 3, 120, true, new Date(), "reseña", 0, 0, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), 0.5, "A");
        learningPath.getActividades().put("A101", actividad);

        List<Actividad> actividadesDisponibles = estudiante.actividadesDisponibles("LP106_U105");
        assertEquals(1, actividadesDisponibles.size(), "There should be one available activity");
        assertEquals("A101", actividadesDisponibles.get(0).getActividadID());
    }
    
}	