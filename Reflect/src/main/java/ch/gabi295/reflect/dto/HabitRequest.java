package ch.gabi295.reflect.dto;

import ch.gabi295.reflect.model.HabitCategory;

import java.math.BigDecimal;
import java.time.LocalDate;

public class HabitRequest {
    private String title;
    private HabitCategory category;
    private String description;
    private boolean completed;
    private BigDecimal targetValue;
    private BigDecimal currentValue;
    private String unit;
    private LocalDate date;

    public String getTitle() {
        return title;
    }

    public HabitCategory getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public boolean isCompleted() {
        return completed;
    }

    public BigDecimal getTargetValue() {
        return targetValue;
    }

    public BigDecimal getCurrentValue() {
        return currentValue;
    }

    public String getUnit() {
        return unit;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCategory(HabitCategory category) {
        this.category = category;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public void setTargetValue(BigDecimal targetValue) {
        this.targetValue = targetValue;
    }

    public void setCurrentValue(BigDecimal currentValue) {
        this.currentValue = currentValue;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
