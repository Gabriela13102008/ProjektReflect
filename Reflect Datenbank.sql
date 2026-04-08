DROP DATABASE IF EXISTS habit_tracker_db;
CREATE DATABASE habit_tracker_db;
USE habit_tracker_db;

-- USERS
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- HABITS
CREATE TABLE habits (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(150) NOT NULL,
    category ENUM('WASSER', 'ESSEN', 'SPORT', 'LERNEN', 'SCHLAF', 'CUSTOM') NOT NULL,
    description VARCHAR(255),
    completed BOOLEAN DEFAULT FALSE,
    target_value DECIMAL(10,2),
    current_value DECIMAL(10,2),
    unit VARCHAR(50),
    date DATE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    user_id BIGINT NOT NULL,

    CONSTRAINT fk_habits_user
        FOREIGN KEY (user_id) REFERENCES users(id)
        ON DELETE CASCADE
);

-- JOURNAL ENTRIES
CREATE TABLE journal_entries (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(150) NOT NULL,
    content TEXT NOT NULL,
    mood ENUM('SEHR_GUT', 'GUT', 'OKAY', 'SCHLECHT', 'SEHR_SCHLECHT'),
    entry_date DATE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    user_id BIGINT NOT NULL,

    CONSTRAINT fk_journal_user
        FOREIGN KEY (user_id) REFERENCES users(id)
        ON DELETE CASCADE
);

-- HABIT LOGS (OPTIONAL)
CREATE TABLE habit_logs (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    habit_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    log_date DATE NOT NULL,
    completed BOOLEAN DEFAULT FALSE,
    current_value DECIMAL(10,2),
    note VARCHAR(255),

    CONSTRAINT fk_logs_habit
        FOREIGN KEY (habit_id) REFERENCES habits(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_logs_user
        FOREIGN KEY (user_id) REFERENCES users(id)
        ON DELETE CASCADE
);