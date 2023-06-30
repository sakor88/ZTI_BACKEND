package ZTI.projekt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ZTI.projekt.model.Exercise;
import org.springframework.stereotype.Repository;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
}
