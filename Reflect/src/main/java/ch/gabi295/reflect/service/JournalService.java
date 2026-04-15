package ch.gabi295.reflect.service;

import ch.gabi295.reflect.dto.JournalRequest;
import ch.gabi295.reflect.model.JournalEntry;
import ch.gabi295.reflect.model.User;
import ch.gabi295.reflect.repository.JournalRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class JournalService {

    private final JournalRepository journalRepository;
    private final CurrentUserService currentUserService;

    public JournalService(JournalRepository journalRepository, CurrentUserService currentUserService) {
        this.journalRepository = journalRepository;
        this.currentUserService = currentUserService;
    }

    public List<JournalEntry> getEntries(LocalDate date) {
        User user = currentUserService.getCurrentUser();
        if (date != null) {
            return journalRepository.findByUserAndEntryDateOrderByCreatedAtDesc(user, date);
        }
        return journalRepository.findByUserOrderByEntryDateDesc(user);
    }

    public JournalEntry createEntry(JournalRequest request) {
        User user = currentUserService.getCurrentUser();
        JournalEntry entry = new JournalEntry();
        entry.setTitle(request.getTitle());
        entry.setContent(request.getContent());
        entry.setMood(request.getMood());
        entry.setEntryDate(request.getEntryDate() != null ? request.getEntryDate() : LocalDate.now());
        entry.setUser(user);
        return journalRepository.save(entry);
    }

    public JournalEntry updateEntry(Long entryId, JournalRequest request) {
        User user = currentUserService.getCurrentUser();
        JournalEntry entry = journalRepository.findByIdAndUser(entryId, user).orElseThrow();
        entry.setTitle(request.getTitle());
        entry.setContent(request.getContent());
        entry.setMood(request.getMood());
        entry.setEntryDate(request.getEntryDate() != null ? request.getEntryDate() : entry.getEntryDate());
        return journalRepository.save(entry);
    }

    public void deleteEntry(Long entryId) {
        User user = currentUserService.getCurrentUser();
        JournalEntry entry = journalRepository.findByIdAndUser(entryId, user).orElseThrow();
        journalRepository.delete(entry);
    }
}
