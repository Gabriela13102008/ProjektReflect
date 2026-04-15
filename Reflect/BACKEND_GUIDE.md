# Habit Tracker / Journal Backend Guide

Dieses Backend ist ein Spring-Boot-Projekt und die Aufteilung ist schon fast richtig.
So soll der Code fuer euer Projekt ideal aussehen.

## 1. Projektstruktur

```text
src/main/java/ch/gabi295/reflect
|
+-- config
|   +-- SecurityConfig.java
|
+-- controller
|   +-- AuthController.java
|   +-- HabitController.java
|   +-- JournalController.java
|   +-- DashboardController.java
|
+-- dto
|   +-- RegisterRequest.java
|   +-- LoginRequest.java
|   +-- HabitRequest.java
|   +-- JournalRequest.java
|   +-- DashboardResponse.java        <- spaeter sinnvoll
|
+-- model
|   +-- User.java
|   +-- Habit.java
|   +-- HabitCategory.java
|   +-- JournalEntry.java
|   +-- Mood.java
|
+-- repository
|   +-- UserRepository.java
|   +-- HabitRepository.java
|   +-- JournalRepository.java
|
+-- service
|   +-- AuthService.java
|   +-- HabitService.java
|   +-- JournalService.java
|   +-- DashboardService.java
|
+-- security
|   +-- JwtService.java
|   +-- JwtFilter.java
|   +-- CustomUserDetailsService.java
|
+-- ReflectApplication.java
```

## 2. Was jede Schicht macht

### `model`
Hier sind die Datenbank-Tabellen als Java-Klassen.

Beispiele:
- `User` = Benutzerkonto
- `Habit` = Aufgabe / Gewohnheit
- `JournalEntry` = Journaleintrag

### `repository`
Hier redest du mit der Datenbank.

Beispiele:
- `findByUser(...)`
- `findByUserAndDate(...)`
- `findByUserOrderByEntryDateDesc(...)`

### `service`
Hier ist die eigentliche Logik.

Beispiele:
- Habit fuer einen User erstellen
- Nur heutige Aufgaben laden
- Fortschritt berechnen
- Eintrag loeschen

### `controller`
Hier kommen die HTTP-Requests vom Frontend an.

Beispiele:
- `POST /api/auth/register`
- `GET /api/habits/...`
- `POST /api/journal/...`

### `dto`
DTOs sind Request- oder Response-Klassen fuer die API.
Das ist sauberer als direkt Entities vom Frontend zu schicken.

## 3. Welche Daten du brauchst

### `User`

```java
private Long id;
private String username;
private String email;
private String password;
private LocalDateTime createdAt;
```

### `Habit`

```java
private Long id;
private String title;
private HabitCategory category;
private String description;
private boolean completed;
private BigDecimal targetValue;
private BigDecimal currentValue;
private String unit;
private LocalDate date;
private LocalDateTime createdAt;
private User user;
```

Das passt gut fuer:
- Wasser trinken
- Essen tracken
- Sport abhaken
- Lernen abhaken
- Schlaf tracken
- eigene Aufgaben

### `JournalEntry`

```java
private Long id;
private String title;
private String content;
private Mood mood;
private LocalDate entryDate;
private LocalDateTime createdAt;
private User user;
```

## 4. So sollen die API-Endpunkte aussehen

### Auth

```text
POST   /api/auth/register
POST   /api/auth/login
```

### Habits

Fuer deinen jetzigen Stand ist das okay:

```text
GET    /api/habits/{userId}
GET    /api/habits/{userId}/today
GET    /api/habits/{userId}/completed
GET    /api/habits/{userId}/open
POST   /api/habits/{userId}
PUT    /api/habits/{habitId}
DELETE /api/habits/{habitId}
```

Spaeter mit richtigem Login und JWT ist diese Version besser:

```text
GET    /api/habits
GET    /api/habits/today
GET    /api/habits/completed
GET    /api/habits/open
POST   /api/habits
PUT    /api/habits/{habitId}
PATCH  /api/habits/{habitId}/complete
DELETE /api/habits/{habitId}
```

Warum?
Dann nimmt das Backend den eingeloggten User direkt aus dem Token und nicht aus einer frei eingebbaren `userId`.

### Journal

Jetzt:

```text
GET    /api/journal/{userId}
POST   /api/journal/{userId}
DELETE /api/journal/{entryId}
```

Spaeter besser:

```text
GET    /api/journal
GET    /api/journal?date=2026-04-13
POST   /api/journal
DELETE /api/journal/{entryId}
```

### Dashboard

```text
GET    /api/dashboard/{userId}
```

Spaeter besser:

```text
GET    /api/dashboard
```

## 5. Gute Controller-Regel

Controller sollen moeglichst duenn bleiben.
Sie sollen nur:
- Request annehmen
- Service aufrufen
- Response zurueckgeben

Beispiel:

```java
@RestController
@RequestMapping("/api/habits")
public class HabitController {

    private final HabitService habitService;

    public HabitController(HabitService habitService) {
        this.habitService = habitService;
    }

    @PostMapping("/{userId}")
    public Habit create(@PathVariable Long userId, @RequestBody HabitRequest request) {
        return habitService.createHabit(userId, request);
    }
}
```

Die Logik gehoert in den Service, nicht in den Controller.

## 6. Gute Service-Regel

Im Service passiert die echte Arbeit:

```java
public Habit createHabit(Long userId, HabitRequest request) {
    User user = userRepository.findById(userId).orElseThrow();

    Habit habit = new Habit();
    habit.setTitle(request.getTitle());
    habit.setCategory(request.getCategory());
    habit.setDescription(request.getDescription());
    habit.setCompleted(request.isCompleted());
    habit.setTargetValue(request.getTargetValue());
    habit.setCurrentValue(request.getCurrentValue());
    habit.setUnit(request.getUnit());
    habit.setDate(request.getDate());
    habit.setUser(user);

    return habitRepository.save(habit);
}
```

## 7. Was in deinem Projekt schon gut ist

- `controller`, `service`, `repository`, `model`, `dto` sind sauber getrennt
- `Habit`, `User`, `JournalEntry` passen gut zur App-Idee
- Requests fuer Register, Login, Habit und Journal sind schon als DTO gebaut
- Dashboard hat schon eine erste eigene Service-Schicht

## 8. Was du noch verbessern solltest

### 1. `createdAt` setzen
In `Habit` und `JournalEntry` wird `createdAt` im Service aktuell noch nicht gesetzt.

Beispiel:

```java
habit.setCreatedAt(LocalDateTime.now());
entry.setCreatedAt(LocalDateTime.now());
```

### 2. Eigene Daten schuetzen
Dein Ziel ist: User sieht nur eigene Habits und Journals.
Das ist mit `userId` in der URL noch nicht sicher genug.

Ideal:
- Login gibt JWT Token zurueck
- Frontend speichert Token
- Backend liest User aus dem Token
- Queries laufen nur fuer diesen User

### 3. Dashboard als DTO zurueckgeben
Statt `Map<String, Integer>` ist eine eigene Response-Klasse sauberer.

Beispiel:

```java
public class DashboardResponse {
    private int totalToday;
    private int completedToday;
    private int openToday;
    private int journalEntriesToday;
}
```

### 4. Markieren als erledigt
Fuer "Aufgaben als erledigt markieren" ist ein eigener Endpoint sinnvoll:

```text
PATCH /api/habits/{habitId}/complete
```

### 5. Journal nach Datum filtern
Du willst laut Aufgabenstellung ein Datum auswaehlen.
Darum solltest du spaeter auch so etwas haben:

```text
GET /api/journal?date=2026-04-13
```

## 9. Passendes Datenbank-Denken

Eure Tabellen sind im Kern:

### `users`
- `id`
- `username`
- `email`
- `password`
- `created_at`

### `habits`
- `id`
- `title`
- `category`
- `description`
- `completed`
- `target_value`
- `current_value`
- `unit`
- `date`
- `created_at`
- `user_id`

### `journal_entries`
- `id`
- `title`
- `content`
- `mood`
- `entry_date`
- `created_at`
- `user_id`

## 10. So sieht dein Backend insgesamt richtig aus

Kurz gesagt:

1. Frontend sendet Request
2. Controller nimmt Request an
3. Service verarbeitet Logik
4. Repository redet mit Datenbank
5. Daten gehen als JSON zurueck ans Frontend

Beispiel:

```text
Frontend -> HabitController -> HabitService -> HabitRepository -> MySQL
```

## 11. Empfehlung fuer deinen naechsten Schritt

Wenn du jetzt gerade nur Backend machst, dann ist diese Reihenfolge sinnvoll:

1. Auth fertig machen
2. Habits komplett machen
3. Journal komplett machen
4. Dashboard Response verbessern
5. JWT sauber anschliessen

## 12. Wichtig fuer deine Praesentation

Wenn dich jemand fragt "Wie ist euer Backend aufgebaut?", kannst du sagen:

> Wir haben ein Spring-Boot-Backend mit Controller-, Service-, Repository- und Model-Schicht.  
> Die User koennen Habits und Journal-Eintraege verwalten.  
> Jeder Eintrag gehoert zu genau einem User.  
> Die API liefert die Daten fuer Frontend, Dashboard und Login.
