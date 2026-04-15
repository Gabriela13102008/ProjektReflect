package ch.gabi295.reflect.dto;

import ch.gabi295.reflect.model.Mood;

import java.time.LocalDate;

public class JournalRequest {
    private String title;
    private String content;
    private Mood mood;
    private LocalDate entryDate;

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public Mood getMood() {
        return mood;
    }

    public LocalDate getEntryDate() {
        return entryDate;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setMood(Mood mood) {
        this.mood = mood;
    }

    public void setEntryDate(LocalDate entryDate) {
        this.entryDate = entryDate;
    }
}
