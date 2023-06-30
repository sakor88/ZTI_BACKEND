package ZTI.projekt.controller;

import ZTI.projekt.dto.ExerciseDto;
import ZTI.projekt.dto.TrainingSessionDto;
import ZTI.projekt.model.Exercise;
import ZTI.projekt.model.TrainingSession;
import ZTI.projekt.repository.TrainingSessionRepository;
import ZTI.projekt.repository.ExerciseRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A REST controller that handles HTTP requests related to Training Sessions.
 * These requests include obtaining all training sessions, obtaining a training
 * session by id, creating a new training session and completing a training session.
 * This controller also handles the conversion between Exercise and TrainingSession
 * objects and their corresponding Data Transfer Objects.
 */
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/trainingsessions")
public class TrainingSessionController {

    @Autowired
    private TrainingSessionRepository trainingSessionRepository;

    @Autowired
    private ExerciseRepository exerciseRepository;


    /**
     * Handles the HTTP GET request for fetching all training sessions.
     * @return List of all Training Session DTOs.
     */
    @GetMapping("/")
    public List<TrainingSessionDto> getAllTrainingSessions() {
        return trainingSessionRepository.findAll().stream().map(this::convertToDto).collect(Collectors.toList());
    }


    /**
     * Converts a TrainingSession entity into a TrainingSessionDto.
     * @param trainingSession The TrainingSession entity to be converted.
     * @return TrainingSessionDto representing the given TrainingSession.
     */
    private TrainingSessionDto convertToDto(TrainingSession trainingSession) {
        TrainingSessionDto dto = new TrainingSessionDto();
        dto.setTraining_id(trainingSession.getTraining_id());
        dto.setName(trainingSession.getName());
        dto.setDate(trainingSession.getDate());
        dto.setCompleted(trainingSession.isCompleted());
        dto.setExercises(trainingSession.getExercises().stream()
                .map(this::convertExerciseToDto)
                .collect(Collectors.toList()));
        return dto;
    }

    /**
     * Converts an Exercise entity into an ExerciseDto.
     * @param exercise The Exercise entity to be converted.
     * @return ExerciseDto representing the given Exercise.
     */
    private ExerciseDto convertExerciseToDto(Exercise exercise) {
        ExerciseDto dto = new ExerciseDto();
        dto.setExercise_id(exercise.getExercise_id());
        dto.setName(exercise.getName());
        dto.setSets(exercise.getSets());
        dto.setRep_range(exercise.getRep_range());
        return dto;
    }


    /**
     * Handles the HTTP GET request for fetching a specific training session.
     * @param id The id of the training session.
     * @return The TrainingSession with the given id, or null if not found.
     */
    @GetMapping("/{id}")
    public TrainingSession getTrainingSessionById(@PathVariable Long id) {
        return trainingSessionRepository.findById(id).orElse(null);
    }

    /**
     * Handles the HTTP POST request for creating a new training session.
     * @param newTrainingSession The TrainingSessionDto representing the new training session.
     * @return The newly created TrainingSessionDto.
     */
    @PostMapping("/")
    public TrainingSessionDto createTrainingSession(@RequestBody TrainingSessionDto newTrainingSession) {
        List<Long> exerciseIds = newTrainingSession.getExercises().stream().map(ExerciseDto::getExercise_id).collect(Collectors.toList());

        List<Exercise> exercises = exerciseRepository.findAllById(exerciseIds);

        TrainingSession trainingSession = new TrainingSession();
        trainingSession.setName(newTrainingSession.getName());
        trainingSession.setDate(newTrainingSession.getDate());
        trainingSession.setExercises(new HashSet<>(exercises));

        TrainingSession savedTrainingSession = trainingSessionRepository.save(trainingSession);


        return convertToDto(savedTrainingSession);
    }


    /**
     * Handles the HTTP POST request for completing a training session.
     * @param id The id of the training session to be completed.
     * @return The completed TrainingSessionDto, or throws an exception if not found.
     */
    @PostMapping("/{id}/complete")
    public TrainingSessionDto completeTrainingSession(@PathVariable Long id) {
        return trainingSessionRepository.findById(id)
                .map(trainingSession -> {
                    trainingSession.setCompleted(true);
                    return convertToDto(trainingSessionRepository.save(trainingSession));
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

}
