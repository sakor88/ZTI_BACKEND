package ZTI.projekt.dto;

import lombok.Data;

/**
 * Data Transfer Object (DTO) for Exercise entity.
 * This class is used to transport data between different parts of the application.
 * Lombok's @Data annotation is used for automatic generation of getters, setters, equals, hashCode and toString methods.
 */
@Data
public class ExerciseDto {
    /**
     * Unique identifier for the Exercise.
     */
    private Long exercise_id;

    /**
     * Name of the Exercise.
     */
    private String name;

    /**
     * Number of sets for the Exercise.
     */
    private int sets;

    /**
     * Repetition range for the Exercise. This field can include different types of values
     * such as a single integer (indicating a fixed number of repetitions),
     * a range (indicating a range of acceptable repetitions), etc.
     */
    private String rep_range;
}
