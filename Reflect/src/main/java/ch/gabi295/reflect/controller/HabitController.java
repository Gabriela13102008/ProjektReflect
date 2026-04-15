package ch.gabi295.reflect.controller;

import ch.gabi295.reflect.dto.HabitRequest;
import ch.gabi295.reflect.model.Habit;
import ch.gabi295.reflect.service.HabitService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/habits")
public class HabitController {

    private final HabitService habitService;

    public HabitController(HabitService habitService) {
        this.habitService = habitService;
    }

    @GetMapping
    public List<Habit> getHabits() {
        return habitService.getHabitsForCurrentUser();
    }

    @GetMapping("/today")
    public List<Habit> getToday() {
        return habitService.getHabitsForToday();
    }

    @GetMapping("/completed")
    public List<Habit> getCompleted() {
        return habitService.getCompletedHabits();
    }

    @GetMapping("/open")
    public List<Habit> getOpen() {
        return habitService.getOpenHabits();
    }

    @PostMapping
    public Habit create(@RequestBody HabitRequest request) {
        return habitService.createHabit(request);
    }

    @PutMapping("/{habitId}")
    public Habit update(@PathVariable Long habitId, @RequestBody HabitRequest request) {
        return habitService.updateHabit(habitId, request);
    }

    @PatchMapping("/{habitId}/complete")
    public Habit complete(@PathVariable Long habitId) {
        return habitService.markHabitAsCompleted(habitId);
    }

    @DeleteMapping("/{habitId}")
    public void delete(@PathVariable Long habitId) {
        habitService.deleteHabit(habitId);
    }
}