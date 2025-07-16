-- Creación de tablas (DDL)
CREATE TABLE IF NOT EXISTS players (
    id UUID PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    karma_points INTEGER NOT NULL CHECK (karma_points >= 0),
    total_xp INTEGER NOT NULL CHECK (total_xp >= 0),
    level INTEGER NOT NULL CHECK (level >= 0),
    best_infinite_score INTEGER NOT NULL CHECK (best_infinite_score >= 0),
    max_daily_streak INTEGER NOT NULL CHECK (max_daily_streak >= 0),
    active BOOLEAN NOT NULL
);

CREATE TABLE IF NOT EXISTS player_roles (
    player_id UUID NOT NULL REFERENCES players(id),
    role VARCHAR(50) NOT NULL,
    PRIMARY KEY (player_id, role)
);

CREATE TABLE IF NOT EXISTS questions (
    id UUID PRIMARY KEY,
    question VARCHAR(1000) NOT NULL UNIQUE,
    correct_answer VARCHAR(500) NOT NULL,
    category VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS question_options (
    question_id UUID NOT NULL REFERENCES questions(id),
    option_text VARCHAR(500) NOT NULL,
    PRIMARY KEY (question_id, option_text)
);

CREATE TABLE IF NOT EXISTS game_sessions (
    id UUID PRIMARY KEY,
    player_id UUID NOT NULL REFERENCES players(id),
    mode VARCHAR(50) NOT NULL,
    category VARCHAR(50) NOT NULL,
    current_round INTEGER NOT NULL,
    score INTEGER NOT NULL,
    created_at TIMESTAMP NOT NULL,
    status VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS game_session_used_questions (
    game_session_id UUID NOT NULL REFERENCES game_sessions(id),
    question_id UUID NOT NULL REFERENCES questions(id),
    PRIMARY KEY (game_session_id, question_id)
);

CREATE TABLE IF NOT EXISTS question_reports (
    id UUID PRIMARY KEY,
    question_id UUID NOT NULL REFERENCES questions(id),
    reported_by_player_id UUID NOT NULL REFERENCES players(id),
    reported_at TIMESTAMP NOT NULL,
    reviewed BOOLEAN NOT NULL
);

CREATE TABLE IF NOT EXISTS suggested_questions (
    id UUID PRIMARY KEY,
    suggested_by_player_id UUID NOT NULL REFERENCES players(id),
    question_text VARCHAR(1000) NOT NULL,
    correct_answer VARCHAR(500) NOT NULL,
    category VARCHAR(50) NOT NULL,
    approved BOOLEAN
);

CREATE TABLE IF NOT EXISTS suggested_question_options (
    suggested_question_id UUID NOT NULL REFERENCES suggested_questions(id),
    option_text VARCHAR(500) NOT NULL,
    PRIMARY KEY (suggested_question_id, option_text)
);