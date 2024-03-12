package develhope.exercise.Exercise.fileuploadanddownload.repositories;

import develhope.exercise.Exercise.fileuploadanddownload.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> {
}
