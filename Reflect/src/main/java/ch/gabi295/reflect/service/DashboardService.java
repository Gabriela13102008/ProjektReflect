package ch.gabi295.reflect.service;

import ch.gabi295.reflect.dto.DashboardResponse;
import ch.gabi295.reflect.model.Habit;
import ch.gabi295.reflect.model.JournalEntry;
import ch.gabi295.reflect.model.Mood;
import ch.gabi295.reflect.model.User;
import ch.gabi295.reflect.repository.HabitRepository;
import ch.gabi295.reflect.repository.JournalRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class DashboardService {

    private final HabitRepository habitRepository;
    private final JournalRepository journalRepository;
    private final CurrentUserService currentUserService;

    public DashboardService(
            HabitRepository habitRepository,
            JournalRepository journalRepository,
            CurrentUserService currentUserService
    ) {
        this.habitRepository = habitRepository;
        this.journalRepository = journalRepository;
        this.currentUserService = currentUserService;
    }

    public DashboardResponse getDashboard() {
        User user = currentUserService.getCurrentUser();
        LocalDate today = LocalDate.now();

        List<Habit> todaysHabits = habitRepository.findByUserAndDateOrderByCreatedAtDesc(user, today);
        List<Habit> completedHabits = todaysHabits.stream().filter(Habit::isCompleted).toList();
        List<JournalEntry> todayEntries = journalRepository.findByUserAndEntryDateOrderByCreatedAtDesc(user, today);
        List<JournalEntry> recentEntries = journalRepository.findByUserOrderByEntryDateDesc(user)
                .stream()
                .limit(5)
                .toList();

        int totalHabitsToday = todaysHabits.size();
        int completedHabitsToday = completedHabits.size();
        int openHabitsToday = totalHabitsToday - completedHabitsToday;
        int progressPercent = totalHabitsToday == 0 ? 0 : (completedHabitsToday * 100) / totalHabitsToday;
        Mood latestMood = recentEntries.isEmpty() ? null : recentEntries.get(0).getMood();

        return new DashboardResponse(
                today,
                totalHabitsToday,
                completedHabitsToday,
                openHabitsToday,
                todayEntries.size(),
                progressPercent,
                latestMood,
                todaysHabits,
                completedHabits,
                recentEntries
        );
    }
}
