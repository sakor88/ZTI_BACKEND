package ZTI.projekt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ZTI.projekt.model.TrainingSession;

public interface TrainingSessionRepository extends JpaRepository<TrainingSession, Long> {
}