package ch.gabi295.reflect.repository;

import ch.gabi295.reflect.model.Habit;
import ch.gabi295.reflect.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface HabitRepository extends JpaRepository<Habit, Long>  {
    List<Habit> findByUserOrderByDateAscCreatedAtDesc(User user);
    List<Habit> findByUserAndDateOrderByCreatedAtDesc(User user, LocalDate date);
    List<Habit> findByUserAndCompletedOrderByDateAscCreatedAtDesc(User user, boolean completed);
    Optional<Habit> findByIdAndUser(Long id, User user);
}
