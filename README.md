# Project Reflect – Habit Tracker & Journal

## Projektübersicht
**Project Reflect** ist eine Webapplikation, mit der Benutzer ihre täglichen Aufgaben tracken und Journal-Einträge erstellen können.  
Das Projekt wurde im Modul 295 umgesetzt und beinhaltet ein modernes Frontend(React) sowie ein REST Backend(Spring Boot) mit JWT Authentifizierung.

---

## Ziele des Projekts
- Aufbau einer Multi-User Webapplikation
- Sicheres Login/Register mit JWT
- CRUD-Funktionalität für Habits und Journal-Einträge
- Dashboard-Übersicht für tägliche Aufgaben und Fortschritt
- User kann seinen Account bearbeiten oder löschen

---

## Features

### User System
- Account registrieren
- Login / Logout
- JWT Token Authentifizierung
- User sieht nur eigene Habits & Journal-Einträge
- Username ändern
- Email ändern
- Passwort ändern
- Account löschen

### Aufgaben
- Aufgaben erstellen (z.B. Wasser trinken, Sport, Lernen, Schlaf)
- Aufgaben löschen
- Aufgaben als erledigt markieren
- Tagesansicht (heutige Aufgaben)

### Journal
- Journal Einträge erstellen
- Stimmung speichern (Mood)
- Einträge löschen

### Dashboard
- heutige Aufgaben anzeigen
- Fortschritt anzeigen
- letzte Journal-Einträge anzeigen

---


# Architektur

## Backend Layer Architektur

<img width="278" height="325" alt="image" src="https://github.com/user-attachments/assets/ad6a3270-22ca-4921-b17e-d750e4053e91" />


<img width="150" height="899" alt="image" src="https://github.com/user-attachments/assets/21960c84-c0db-4226-abbf-d098bba145d3" />


---

## Datenbank ER-Diagramm

<img width="780" height="777" alt="image" src="https://github.com/user-attachments/assets/bfe73378-80e7-45f1-9473-04e3097eaa7b" />


---

## JWT Authentifizierung Flow

<img width="455" height="541" alt="image" src="https://github.com/user-attachments/assets/93d7c378-bd45-436d-b5b5-b1657aa53f0f" />


---

# Technologie-Stack

## Backend
| Technologie | Version | Zweck |
|-----------|---------|------|
| Java | 17 | Backend Sprache |
| Spring Boot | 4.x | REST API |
| Spring Security | 7.x | Security & JWT |
| JPA / Hibernate | - | ORM / Datenbankzugriff |
| H2 Database | - | Relationale Datenbank |
| BCrypt | - | Passwort Hashing |
| Maven | - | Dependency Management |

Das Backend stellt REST-Endpunkte zur Verfügung. Spring Security schützt die API über JWT. Bei Login/Register wird ein Token generiert und im Frontend gespeichert. Jeder API Request sendet diesen Token mit und wird vom JwtFilter geprüft. Daten werden über JPA Repositories gespeichert.

---

## Frontend
| Technologie | Version | Zweck |
|-----------|---------|------|
| React | 18 | UI Framework |
| Vite | 5.x | Dev Server / Build |
| React Router | 6.x | Routing |
| Axios | 1.x | HTTP Requests |
| Context API | React | Auth State |

Das Frontend basiert auf React. React Router verwaltet Navigation zwischen Pages. Axios sendet REST Requests ans Backend. Ein Interceptor fügt automatisch den JWT Token hinzu. AuthContext speichert Userdaten global.

---

# REST API Endpoints

## Auth
| Methode | Endpoint | Beschreibung |
|--------|----------|--------------|
| POST | `/api/auth/register` | Registrierung |
| POST | `/api/auth/login` | Login |

## Habits
| Methode | Endpoint | Beschreibung |
|--------|----------|--------------|
| GET | `/api/habits` | alle Habits vom User |
| GET | `/api/habits/today` | heutige Habits |
| POST | `/api/habits` | Habit erstellen |
| PUT | `/api/habits/{id}` | Habit bearbeiten |
| PATCH | `/api/habits/{id}/complete` | Habit als erledigt markieren |
| DELETE | `/api/habits/{id}` | Habit löschen |

## Journal
| Methode | Endpoint | Beschreibung |
|--------|----------|--------------|
| GET | `/api/journal` | alle Einträge |
| GET | `/api/journal?date=YYYY-MM-DD` | Einträge nach Datum |
| POST | `/api/journal` | Eintrag erstellen |
| PUT | `/api/journal/{id}` | Eintrag bearbeiten |
| DELETE | `/api/journal/{id}` | Eintrag löschen |

## User Settings
| Methode | Endpoint | Beschreibung |
|--------|----------|--------------|
| PUT | `/api/user/username` | Username ändern |
| PUT | `/api/user/email` | Email ändern |
| PUT | `/api/user/password` | Passwort ändern |
| DELETE | `/api/user` | Account löschen |

---

# Sicherheitskonzept

## JWT Security
- `/api/auth/**` ist öffentlich erreichbar
- alle anderen Endpoints sind geschützt
- Token wird bei jedem Request geprüft

## Passwort Hashing
- Passwörter werden nicht im Klartext gespeichert
- BCrypt speichert einen sicheren Hash

## Multi-User Schutz
- Habits und Journal-Einträge haben immer eine `user_id`
- Abfragen nutzen `findByIdAndUser(...)` um fremde Daten zu blockieren

---

# Testing

## Backend Tests (Beispiele)
| Testfall | Erwartetes Resultat |
|---------|----------------------|
| Register User | HTTP 200 + Token |
| Login User | HTTP 200 + Token |
| GET Habits ohne Token | HTTP 401 |
| GET Habits mit Token | HTTP 200 |
| User löscht fremden Habit | HTTP 404/403 |

---

# User Stories

### User 1 Registrierung
Als neuer Benutzer möchte ich einen Account erstellen können damit ich meine Habits und Journal-Einträge speichern kann.

**Akzeptanzkriterien**
- Benutzer kann Username, Email und Passwort eingeben
- Passwort wird gehasht gespeichert (BCrypt)
- Nach Registrierung erhält Benutzer einen JWT Token

---

### User 2 Logout
Als eingeloggter Benutzer möchte ich mich ausloggen können damit niemand anderes Zugriff auf meinen Account hat.

**Akzeptanzkriterien**
- Token wird aus localStorage entfernt
- User wird aus dem AuthContext entfernt
- Benutzer wird auf Login-Seite weitergeleitet

---

### User 3 Journal-Eintrag löschen
Als Benutzer möchte ich einen Journal-Eintrag löschen können damit ich falsche Einträge entfernen kann.

**Akzeptanzkriterien**
- Eintrag wird aus DB gelöscht
- Nur der Besitzer darf löschen
- UI wird danach aktualisiert

---

# Startanleitung

## Backend starten
mvn spring-boot:run

Backend läuft auf:
http://localhost:8081

## Frontend starten
cd frontend
npm install
npm run dev

Frontend läuft auf:
http://localhost:5173
