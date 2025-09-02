CREATE TYPE Gender AS ENUM ('MALE', 'FEMALE');
CREATE TYPE SportsmanCategory AS ENUM ('RECREATION','AMATEUR', 'SEMI_PROFESSIONAL', 'PROFESSIONAL');
CREATE TYPE RecommendationType as ENUM ('TRAINING', 'DIET', 'SUPPLEMENT');
CREATE TYPE RestrictionLevel AS ENUM ('NORMAL','HARD','EXTREME');
CREATE TYPE MoodProgress AS ENUM ('GOOD','BAD','STALL');
CREATE TYPE MoodEmotions AS ENUM ('EXCITED','HAPPY','NEUTRAL','TIRED','STRESSED','SAD');
CREATE TYPE AthleteReportStatus AS ENUM ('GOOD', 'IMPROVED', 'FOLLOWUP');
CREATE TYPE user_role AS ENUM ('PATIENT','DOCTOR','ADMIN','PENDING');

CREATE TABLE app_user
(
    user_id       BIGSERIAL PRIMARY KEY,
    first_name    VARCHAR(100)       NOT NULL,
    last_name     VARCHAR(100)       NOT NULL,
    embg          VARCHAR(13) UNIQUE NOT NULL,
    role          user_role DEFAULT 'PENDING',
    password_hash TEXT               NOT NULL,
    email         VARCHAR(150) UNIQUE,
    created_at    TIMESTAMP DEFAULT current_timestamp,

    CONSTRAINT chk_embg CHECK (
        embg ~ '^[0-9]{13}$'
            AND to_date(substring(embg from 1 for 7), 'DDMMYYY') IS NOT NULL
        )
);

CREATE TABLE doctor
(
    doctor_id      BIGSERIAL PRIMARY KEY,
    user_id        BIGINT       NOT NULL UNIQUE,
    specialization VARCHAR(100) NOT NULL,

    CONSTRAINT fk_doctor_user FOREIGN KEY (user_id) REFERENCES app_user (user_id) ON DELETE RESTRICT
);

CREATE TABLE patient
(
    patient_id             BIGSERIAL PRIMARY KEY,
    user_id                BIGINT NOT NULL UNIQUE,
    doctor_id              BIGINT,
    date_of_birth          DATE   NOT NULL,
    date_of_latest_checkup TIMESTAMP,
    gender                 Gender NOT NULL,
    sportsman_category     SportsmanCategory DEFAULT 'RECREATION',

    CONSTRAINT fk_patient_doctor FOREIGN KEY (doctor_id) REFERENCES doctor (doctor_id) ON DELETE RESTRICT,
    CONSTRAINT fk_patient_user FOREIGN KEY (user_id) REFERENCES app_user (user_id) ON DELETE RESTRICT
);

CREATE TABLE athlete_report
(
    report_id                 BIGSERIAL PRIMARY KEY,
    doctor_id                 BIGINT              NOT NULL,
    patient_id                BIGINT              NOT NULL,
    created_at                TIMESTAMP DEFAULT current_timestamp,
    status                    AthleteReportStatus NOT NULL,
    vo2_max                   DECIMAL(5, 2)       NOT NULL,
    resting_heart_rate        INTEGER             NOT NULL,
    under_pressure_heart_rate INTEGER             NOT NULL,
    body_fat_percentage       DECIMAL(4, 1)       NOT NULL,
    lean_muscle_mass          DECIMAL(5, 1),
    bone_density              DECIMAL(4, 2)       NOT NULL,
    height                    DECIMAL(5, 1)       NOT NULL,
    weight                    DECIMAL(5, 1)       NOT NULL,
    one_rep_max_bench         DECIMAL(5, 1),
    one_rep_max_squat         DECIMAL(5, 1),
    one_rep_max_deadlift      DECIMAL(5, 1),
    jump_height               DECIMAL(4, 1),
    average_run_per_kilometer DECIMAL(4, 2)       NOT NULL,

    shoulder_flexibility      INTEGER,
    hip_flexibility           INTEGER,
    balance_time              DECIMAL(4, 1)       NOT NULL,
    reaction_time             DECIMAL(5, 3)       NOT NULL,
    core_stability_score      INTEGER             NOT NULL,
    hemoglobin                DECIMAL(4, 1)       NOT NULL,
    glucose                   DECIMAL(5, 1)       NOT NULL,
    creatinine                DECIMAL(4, 2)       NOT NULL,
    vitamin_d                 DECIMAL(4, 1)       NOT NULL,
    iron                      DECIMAL(5, 1)       NOT NULL,
    testosterone              DECIMAL(6, 1)       NOT NULL,
    cortisol                  DECIMAL(4, 1)       NOT NULL,
    CONSTRAINT fk_report_doctor FOREIGN KEY (doctor_id) REFERENCES doctor (doctor_id) ON DELETE RESTRICT,
    CONSTRAINT fk_report_patient FOREIGN KEY (patient_id) REFERENCES patient (patient_id) ON DELETE RESTRICT,
    CONSTRAINT chk_body_fat_percentage CHECK (body_fat_percentage >= 0 AND body_fat_percentage <= 100),
    CONSTRAINT chk_core_stability CHECK (core_stability_score >= 0 AND core_stability_score <= 100)
);

CREATE TABLE summary
(
    summary_id         BIGSERIAL PRIMARY KEY,
    report_id          BIGINT NOT NULL UNIQUE,
    summarized_content TEXT   NOT NULL,
    CONSTRAINT fk_summary_report FOREIGN KEY (report_id) REFERENCES athlete_report (report_id) ON DELETE RESTRICT
);

CREATE TABLE mood
(
    mood_id                BIGSERIAL PRIMARY KEY,
    patient_id             BIGINT       NOT NULL,
    mood_progress          MoodProgress NOT NULL,
    mood_emotion           MoodEmotions NOT NULL,
    hours_slept_average    INT          NOT NULL,
    mood_description       TEXT         NOT NULL,
    mood_description_score INT          NOT NULL,
    created_at             timestamp DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_mood_patient FOREIGN KEY (patient_id) REFERENCES patient (patient_id) ON DELETE RESTRICT,
    CONSTRAINT chk_hours_slept_per_day CHECK ( hours_slept_average < 24 and hours_slept_average > 0 )
);

CREATE TABLE recommendation
(
    recommendation_id         BIGSERIAL PRIMARY KEY,
    report_id                 BIGINT             NOT NULL,
    type                      RecommendationType NOT NULL,
    restriction_level         RestrictionLevel   NOT NULL DEFAULT 'NORMAL',
    label                     VARCHAR(70)        NOT NULL,
    description               TEXT               NOT NULL,
    cost_per_month            INTEGER            NOT NULL,
    duration_weeks            INTEGER            NOT NULL,
    frequency_per_day         INTEGER                     default 0,
    target_goal               TEXT               NOT NULL,
    effectiveness_rating      INTEGER CHECK (effectiveness_rating BETWEEN 1 AND 10),
    doctor_personalized_notes TEXT               NOT NULL,
    CONSTRAINT fk_recommendation_report FOREIGN KEY (report_id) REFERENCES athlete_report (report_id) ON DELETE RESTRICT
);