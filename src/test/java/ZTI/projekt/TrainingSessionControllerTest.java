package ZTI.projekt;

import ZTI.projekt.controller.TrainingSessionController;
import ZTI.projekt.dto.ExerciseDto;
import ZTI.projekt.dto.TrainingSessionDto;
import ZTI.projekt.model.Exercise;
import ZTI.projekt.model.TrainingSession;
import ZTI.projekt.repository.ExerciseRepository;
import ZTI.projekt.repository.TrainingSessionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TrainingSessionControllerTest {

    @Mock
    TrainingSessionRepository trainingSessionRepository;

    @Mock
    ExerciseRepository exerciseRepository;

    @InjectMocks
    TrainingSessionController trainingSessionController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getAllTrainingSessions() {
        List<TrainingSession> trainingSessions = new ArrayList<>();
        TrainingSession trainingSession = new TrainingSession();
        trainingSession.setExercises(new HashSet<>());
        trainingSessions.add(trainingSession);

        when(trainingSessionRepository.findAll()).thenReturn(trainingSessions);

        List<TrainingSessionDto> result = trainingSessionController.getAllTrainingSessions();

        assertFalse(result.isEmpty());
        verify(trainingSessionRepository, times(1)).findAll();
    }


    @Test
    void getTrainingSessionById() {
        TrainingSession trainingSession = new TrainingSession();
        when(trainingSessionRepository.findById(anyLong())).thenReturn(Optional.of(trainingSession));

        TrainingSession result = trainingSessionController.getTrainingSessionById(1L);

        assertNotNull(result);
        verify(trainingSessionRepository, times(1)).findById(anyLong());
    }

    @Test
    void createTrainingSession() {
        TrainingSessionDto newTrainingSession = new TrainingSessionDto();
        ExerciseDto exerciseDto = new ExerciseDto();
        exerciseDto.setExercise_id(1L);
        newTrainingSession.setExercises(List.of(exerciseDto));

        Exercise exercise = new Exercise();
        exercise.setExercise_id(1L);
        exercise.setName("ExerciseName");
        exercise.setSets(3);
        exercise.setRep_range("10-15");

        when(exerciseRepository.findAllById(anyList())).thenReturn(List.of(exercise));

        TrainingSession trainingSession = new TrainingSession();
        trainingSession.setExercises(new HashSet<>(List.of(exercise))); // Initialize exercises with the mock exercise

        when(trainingSessionRepository.save(any(TrainingSession.class))).thenReturn(trainingSession);

        TrainingSessionDto result = trainingSessionController.createTrainingSession(newTrainingSession);

        assertNotNull(result);
        verify(exerciseRepository, times(1)).findAllById(anyList());
        verify(trainingSessionRepository, times(1)).save(any(TrainingSession.class));
    }



    @Test
    void completeTrainingSession() {
        TrainingSession trainingSession = new TrainingSession();
        trainingSession.setExercises(new HashSet<>());

        when(trainingSessionRepository.findById(anyLong())).thenReturn(Optional.of(trainingSession));
        when(trainingSessionRepository.save(any(TrainingSession.class))).thenReturn(trainingSession);

        TrainingSessionDto result = trainingSessionController.completeTrainingSession(1L);

        assertNotNull(result);
        verify(trainingSessionRepository, times(1)).findById(anyLong());
        verify(trainingSessionRepository, times(1)).save(any(TrainingSession.class));
    }


    @Test
    void completeTrainingSessionNotFound() {
        when(trainingSessionRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> {
            trainingSessionController.completeTrainingSession(1L);
        });

        verify(trainingSessionRepository, times(1)).findById(anyLong());
    }

}
