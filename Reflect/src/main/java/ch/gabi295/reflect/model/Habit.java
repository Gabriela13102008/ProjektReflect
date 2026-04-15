package ch.gabi295.reflect.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "habits")
public class Habit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String title;

    @Enumerated(EnumType.STRING)
    private HabitCategory category;

    @Column(length = 255)
    private String description;
    private boolean completed;

    @Column(name = "target_value")
    private BigDecimal targetValue;

    @Column(name = "current_value")
    private BigDecimal currentValue;

    @Column(length = 50)
    private String unit;

    @Column(nullable = false)
    private LocalDate date;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;

    public Habit() {}

    public Long getId() {
        return id;
    }

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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public User getUser() {
        return user;
    }

    public void setId(Long id) {
        this.id = id;
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

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @PrePersist
    public void prePersist() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
        if (date == null) {
            date = LocalDate.now();
        }
    }
}
