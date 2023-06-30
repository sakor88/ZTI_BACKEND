package ZTI.projekt.controller;

import ZTI.projekt.model.Exercise;
import ZTI.projekt.repository.ExerciseRepository;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

/**
 * The ExerciseController class handles HTTP requests related to Exercise entities.
 */
@CrossOrigin(origins = "https://zti-frontend-w616.onrender.com/")
@RestController
@RequestMapping("/api/exercises")
public class ExerciseController {

    @Autowired
    private ExerciseRepository exerciseRepository;

    /**
     * Handles GET requests to retrieve all exercises.
     * @return List of all exercises.
     */
    @GetMapping("/")
    public List<Exercise> getAllExercises() {
        return exerciseRepository.findAll();
    }

    /**
     * Handles GET requests to retrieve a specific exercise.
     * @param id of the exercise to retrieve.
     * @return The exercise or null if not found.
     */
    @GetMapping("/{id}")
    public Exercise getExerciseById(@PathVariable Long id) {
        return exerciseRepository.findById(id).orElse(null);
    }

    /**
     * Handles POST requests to create an exercise.
     * @param exercise The exercise to be created.
     * @return The created exercise.
     */
    @PostMapping("/")
    public Exercise createExercise(@RequestBody Exercise exercise) {
        return exerciseRepository.save(exercise);
    }
}

