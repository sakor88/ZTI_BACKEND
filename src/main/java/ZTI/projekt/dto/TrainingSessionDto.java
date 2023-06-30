package ZTI.projekt.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Data Transfer Object (DTO) for TrainingSession entity.
 * This class is used to transport data between different parts of the application.
 * Lombok's @Data annotation is used for automatic generation of getters, setters, equals, hashCode and toString methods.
 */
@Data
public class TrainingSessionDto {
    /**
     * Unique identifier for the TrainingSession.
     */
    private Long training_id;

    /**
     * Name of the TrainingSession.
     */
    private String name;

    /**
     * Date of the TrainingSession.
     */
    private Date date;

    /**
     * List of exercises included in the TrainingSession.
     * Each Exercise is represented by an ExerciseDto object.
     */
    private List<ExerciseDto> exercises;

    /**
     * Boolean flag indicating whether the TrainingSession has been completed.
     */
    private boolean completed;
}