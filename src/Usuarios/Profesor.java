package Usuarios;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import Actividades.Actividad;
import Actividades.Encuesta;
import Actividades.Examen;
import Actividades.Quiz;
import Actividades.RecursoEducativo;
import Actividades.Tarea;
import Actividades.Pregunta;
import LearningPaths.Feedback;
import LearningPaths.LearningPath;
import LearningPaths.Progreso;
import main.Central;

import static main.Central.actividades;

public class Profesor extends Usuario {
    public Profesor(String UsuarioID, String nombre, String contraseña, String email, String tipoUsuario) {
        super(UsuarioID, nombre, contraseña, email, tipoUsuario);
        // TODO Auto-generated constructor stub
    }

    public Map<String, List<LearningPath>> RecomendacionesProfesor;
    public Map<String, LearningPath> learningPathsCreados;
    public String profesor = "Profesor";

    @Override
    public String getTipoUsuario() {
        return this.profesor;

    }


    public Map<String, List<LearningPath>> getRecomendacionesProfesor() {
        return RecomendacionesProfesor;
    }


    public void setRecomendacionesProfesor(Map<String, List<LearningPath>> recomendacionesProfesor) {
        RecomendacionesProfesor = recomendacionesProfesor;
    }

    public Map<String, LearningPath> getLearningPathsCreados() {
        return learningPathsCreados;
    }


    public void setLearningPathsCreados(Map<String, LearningPath> learningPathsCreados) {
        this.learningPathsCreados = learningPathsCreados;
    }


    public String getProfesor() {
        return profesor;
    }


    public void revisarEstadoActividad(Actividad actividad, String estado) {
        // Revisa el estado de una actividad
        if (actividad.getTipoActividad().equals("Tarea") || actividad.getTipoActividad().equals("Examen")) {
            if (actividad.getEstado().equals("Enviado")) {
                actividad.setEstado("Exitoso");
            } else {
                actividad.setEstado("Fallido");
            }
        } else if (actividad.getTipoActividad().equals("RecursoEducativo") || actividad.getTipoActividad().equals("Encuesta")) {
            actividad.setEstado("Exitoso");
        } else if (actividad.getTipoActividad().equals("Quiz")) {
            if (actividad.getCalificacion() >= ((Quiz) actividad).getCalificacionMinima()) {
                actividad.setEstado("Exitoso");
            } else {
                actividad.setEstado("Fallido");
            }


        }
    }

    public void crearActividad(String actividadID, String descripcion, String objetivo, int nivelDificultad,
                               int duracionEsperada, boolean esObligatoria, Date fechaLimite, String resenas, double calificacion,
                               int resultado, String tipo, String learningPathID, List<String> actividadesPrevia, List<String> actividadesSeguimiento,
                               HashMap<String, Object> parametrosEspecificos) {
        // Crea una actividad

        Actividad actividad = null;

        if (tipo.equals("Quiz")) {
            double calificacionMinima = (Double) parametrosEspecificos.get("calificacionMinima");
            List<Pregunta> preguntas = (List<Pregunta>) parametrosEspecificos.get("preguntas");
            String respuestaCorrecta = (String) parametrosEspecificos.get("respuestaCorrecta");
            Quiz quiz = new Quiz(actividadID, descripcion, objetivo, nivelDificultad, duracionEsperada,
                    esObligatoria, fechaLimite, resenas, calificacion, resultado, actividadesPrevia,
                    actividadesSeguimiento, preguntas, calificacionMinima, respuestaCorrecta);

            actividad = quiz;

        } else if (tipo.equals("Examen")) {
            double calificacionMinima = (Double) parametrosEspecificos.get("calificacionMinima");
            List<Pregunta> preguntas = (List<Pregunta>) parametrosEspecificos.get("preguntas");
            Examen examen = new Examen(actividadID, descripcion, objetivo, nivelDificultad, duracionEsperada,
                    esObligatoria, fechaLimite, resenas, calificacion, resultado, actividadesPrevia, actividadesSeguimiento, preguntas, calificacionMinima);

            actividad = examen;
        } else if (tipo.equals("Encuesta")) {
            List<String> preguntas = (List<String>) parametrosEspecificos.get("preguntas");
            Encuesta encuesta = new Encuesta(actividadID, descripcion, objetivo, nivelDificultad, duracionEsperada,
                    esObligatoria, fechaLimite, resenas, calificacion, resultado, actividadesPrevia, actividadesSeguimiento, preguntas);
            actividad = encuesta;
        } else if (tipo.equals("Recurso Educativo")) {
            String tipoRecurso = (String) parametrosEspecificos.get("tipoRecurso");
            String linkRecurso = (String) parametrosEspecificos.get("linkRecurso");
            RecursoEducativo recurso = new RecursoEducativo(actividadID, descripcion, objetivo, nivelDificultad, duracionEsperada,
                    esObligatoria, fechaLimite, resenas, calificacion, resultado, actividadesPrevia, actividadesSeguimiento, tipoRecurso, linkRecurso);
            actividad = recurso;
        } else if (tipo.equals("Tarea")) {
            List<Pregunta> preguntas = (List<Pregunta>) parametrosEspecificos.get("preguntas");
            String instrucciones = (String) parametrosEspecificos.get("instrucciones");
            String estado = (String) parametrosEspecificos.get("estado");
            Tarea tarea = new Tarea(actividadID, descripcion, objetivo, nivelDificultad, duracionEsperada,
                    esObligatoria, fechaLimite, resenas, calificacion, resultado, actividadesPrevia, actividadesSeguimiento, preguntas, instrucciones, estado);

            actividad = tarea;
        }


        LearningPath lp = learningPathsCreados.get(learningPathID);

        lp.setActividades(actividad);
        actividades.add(actividad);


    }

    public void CalificacionMinima(Actividad actividad, double calificacionMinima) {
        if (actividad.getTipoActividad().equals("Quiz")) {
            ((Quiz) actividad).setCalificacionMinima(calificacionMinima);
        } else {
            if (actividad.getTipoActividad().equals("Examen")) {
                ((Examen) actividad).setCalificacionMinima(calificacionMinima);
            }
        }


    }


    public void crearLearningPath(String LearningPathID, String titulo, String descripcion, String objetivos,
                                  int nivelDificultad, int duracion, String profesorID, List<String> actividadesID, List<String> intereses) {
        LearningPath learningPath = new LearningPath(LearningPathID, titulo, descripcion, objetivos, nivelDificultad, duracion, profesorID, actividadesID, intereses);
        learningPathsCreados.put(LearningPathID, learningPath);
    }


    public void agregarRecomendacion(Map<String, List<LearningPath>> recomendaciones, String interes, LearningPath learningPath) {
        List<LearningPath> learningpaths = recomendaciones.get(interes);
        learningpaths.add(learningPath);
    }

    // ver el progreso de un estudiante
    public Map<String, String> verProgresoEstudiante(String estudianteID, LearningPath learningPath) {
        Progreso progreso = learningPath.obtenerProgresoEstudiante(estudianteID);
        return progreso.mostrarProgreso();
    }
}