package ch.gabi295.reflect.service;

import ch.gabi295.reflect.dto.HabitRequest;
import ch.gabi295.reflect.model.Habit;
import ch.gabi295.reflect.model.User;
import ch.gabi295.reflect.repository.HabitRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class HabitService {

    private final HabitRepository habitRepository;
    private final CurrentUserService currentUserService;

    public HabitService(HabitRepository habitRepository, CurrentUserService currentUserService) {
        this.habitRepository = habitRepository;
        this.currentUserService = currentUserService;
    }

    public List<Habit> getHabitsForCurrentUser() {
        User user = currentUserService.getCurrentUser();
        return habitRepository.findByUserOrderByDateAscCreatedAtDesc(user);
    }

    public Habit createHabit(HabitRequest request) {
        User user = currentUserService.getCurrentUser();
        Habit habit = new Habit();
        applyRequest(habit, request);
        habit.setUser(user);
        return habitRepository.save(habit);
    }

    public Habit updateHabit(Long habitId, HabitRequest request) {
        User user = currentUserService.getCurrentUser();
        Habit habit = habitRepository.findByIdAndUser(habitId, user).orElseThrow();
        applyRequest(habit, request);
        return habitRepository.save(habit);
    }

    public void deleteHabit(Long habitId) {
        User user = currentUserService.getCurrentUser();
        Habit habit = habitRepository.findByIdAndUser(habitId, user).orElseThrow();
        habitRepository.delete(habit);
    }

    public List<Habit> getHabitsForToday() {
        User user = currentUserService.getCurrentUser();
        return habitRepository.findByUserAndDateOrderByCreatedAtDesc(user, LocalDate.now());
    }

    public List<Habit> getCompletedHabits() {
        User user = currentUserService.getCurrentUser();
        return habitRepository.findByUserAndCompletedOrderByDateAscCreatedAtDesc(user, true);
    }

    public List<Habit> getOpenHabits() {
        User user = currentUserService.getCurrentUser();
        return habitRepository.findByUserAndCompletedOrderByDateAscCreatedAtDesc(user, false);
    }

    public Habit markHabitAsCompleted(Long habitId) {
        User user = currentUserService.getCurrentUser();
        Habit habit = habitRepository.findByIdAndUser(habitId, user).orElseThrow();
        habit.setCompleted(true);
        if (habit.getTargetValue() != null && habit.getCurrentValue() == null) {
            habit.setCurrentValue(habit.getTargetValue());
        }
        return habitRepository.save(habit);
    }

    private void applyRequest(Habit habit, HabitRequest request) {
        habit.setTitle(request.getTitle());
        habit.setCategory(request.getCategory());
        habit.setDescription(request.getDescription());
        habit.setCompleted(request.isCompleted());
        habit.setTargetValue(request.getTargetValue());
        habit.setCurrentValue(request.getCurrentValue() != null ? request.getCurrentValue() : BigDecimal.ZERO);
        habit.setUnit(request.getUnit());
        habit.setDate(request.getDate() != null ? request.getDate() : LocalDate.now());
    }
}
