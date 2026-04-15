package ch.gabi295.reflect.repository;

import ch.gabi295.reflect.model.JournalEntry;
import ch.gabi295.reflect.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface JournalRepository extends JpaRepository<JournalEntry, Long> {

    List<JournalEntry> findByUserOrderByEntryDateDesc(User user);

    List<JournalEntry> findByUserAndEntryDateOrderByCreatedAtDesc(User user, LocalDate entryDate);
    Optional<JournalEntry> findByIdAndUser(Long id, User user);
}
