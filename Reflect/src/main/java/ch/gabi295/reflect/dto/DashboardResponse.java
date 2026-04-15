package ch.gabi295.reflect.dto;

import ch.gabi295.reflect.model.Habit;
import ch.gabi295.reflect.model.JournalEntry;
import ch.gabi295.reflect.model.Mood;

import java.time.LocalDate;
import java.util.List;

public class DashboardResponse {

    private final LocalDate date;
    private final int totalHabitsToday;
    private final int completedHabitsToday;
    private final int openHabitsToday;
    private final int journalEntriesToday;
    private final int progressPercent;
    private final Mood latestMood;
    private final List<Habit> todaysHabits;
    private final List<Habit> completedHabits;
    private final List<JournalEntry> recentJournalEntries;

    public DashboardResponse(
            LocalDate date,
            int totalHabitsToday,
            int completedHabitsToday,
            int openHabitsToday,
            int journalEntriesToday,
            int progressPercent,
            Mood latestMood,
            List<Habit> todaysHabits,
            List<Habit> completedHabits,
            List<JournalEntry> recentJournalEntries
    ) {
        this.date = date;
        this.totalHabitsToday = totalHabitsToday;
        this.completedHabitsToday = completedHabitsToday;
        this.openHabitsToday = openHabitsToday;
        this.journalEntriesToday = journalEntriesToday;
        this.progressPercent = progressPercent;
        this.latestMood = latestMood;
        this.todaysHabits = todaysHabits;
        this.completedHabits = completedHabits;
        this.recentJournalEntries = recentJournalEntries;
    }

    public LocalDate getDate() {
        return date;
    }

    public int getTotalHabitsToday() {
        return totalHabitsToday;
    }

    public int getCompletedHabitsToday() {
        return completedHabitsToday;
    }

    public int getOpenHabitsToday() {
        return openHabitsToday;
    }

    public int getJournalEntriesToday() {
        return journalEntriesToday;
    }

    public int getProgressPercent() {
        return progressPercent;
    }

    public Mood getLatestMood() {
        return latestMood;
    }

    public List<Habit> getTodaysHabits() {
        return todaysHabits;
    }

    public List<Habit> getCompletedHabits() {
        return completedHabits;
    }

    public List<JournalEntry> getRecentJournalEntries() {
        return recentJournalEntries;
    }
}
