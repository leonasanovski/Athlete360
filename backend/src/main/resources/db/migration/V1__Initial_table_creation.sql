/*  ALL ENUMERATIONS FOLLOWING:   */
CREATE TYPE Gender AS ENUM ('MALE', 'FEMALE');
CREATE TYPE SportsmanCategory AS ENUM ('RECREATION','AMATEUR', 'SEMI_PROFESSIONAL', 'PROFESSIONAL');
CREATE TYPE RecommendationType as ENUM ('TRAINING', 'DIET', 'SUPPLEMENT');
CREATE TYPE RestrictionLevel AS ENUM ('NORMAL','HARD','EXTREME');

--doctor entity
CREATE TABLE doctor
(
    doctor_id                BIGSERIAL  PRIMARY KEY,
    first_name               VARCHAR(50)        NOT NULL,
    last_name                VARCHAR(50)        NOT NULL,
    specialization           VARCHAR(100)       NOT NULL,
    email                    VARCHAR(50) UNIQUE NOT NULL
);

--patient entity
CREATE TABLE patient
(
    patient_id         BIGSERIAL PRIMARY KEY,
    doctor_id          BIGINT,
    first_name         VARCHAR(100)        NOT NULL,
    last_name          VARCHAR(100)        NOT NULL,
    date_of_birth      Date                NOT NULL,
    gender             Gender              NOT NULL,
    sportsman_category SportsmanCategory default 'RECREATION',
    email              VARCHAR(100) UNIQUE NOT NULL,

    CONSTRAINT fk_patient_doctor FOREIGN KEY (doctor_id) REFERENCES doctor(doctor_id) ON DELETE RESTRICT
);

--athlete report entity
CREATE TABLE athlete_report
(
    report_id                 BIGSERIAL PRIMARY KEY,
    doctor_id                 BIGINT NOT NULL,
    patient_id                BIGINT NOT NULL,
    created_at                TIMESTAMP DEFAULT current_timestamp,
    -- Cardiovascular Metrics
    vo2_max                   DECIMAL(5, 2) NOT NULL, -- maximal oxygen uptake capacity (ml/kg/min)
    resting_heart_rate        INTEGER NOT NULL ,       -- beats per minute
    under_pressure_heart_rate INTEGER NOT NULL ,       -- maximum heart rate during exercise
    -- Body Composition & Anthropometry
    body_fat_percentage       DECIMAL(4, 1) NOT NULL, -- body composition analysis (%)
    lean_muscle_mass          DECIMAL(5, 1), -- muscle tissue measurement (kg)
    bone_density              DECIMAL(4, 2) NOT NULL, -- skeletal health assessment (g/cm²)
    height                    DECIMAL(5, 1) NOT NULL, -- height in cm
    weight                    DECIMAL(5, 1) NOT NULL, -- weight in kg
    -- Strength & Power Metrics
    one_rep_max_bench         DECIMAL(5, 1), -- 1RM bench press (kg)
    one_rep_max_squat         DECIMAL(5, 1), -- 1RM squat (kg)
    one_rep_max_deadlift      DECIMAL(5, 1), -- 1RM deadlift (kg)
    jump_height               DECIMAL(4, 1), -- explosive power measurement (cm)
    average_run_per_kilometer DECIMAL(4, 2) NOT NULL, -- how many seconds are needed averagely to run 1 km (seconds)
    -- Flexibility & Stability
    shoulder_flexibility      INTEGER,       -- shoulder range of motion (degrees)
    hip_flexibility           INTEGER,       -- hip range of motion (degrees)
    balance_time              DECIMAL(4, 1) NOT NULL, -- stability assessment (seconds)
    reaction_time             DECIMAL(5, 3) NOT NULL, -- neuromuscular response speed (seconds)
    core_stability_score      INTEGER NOT NULL ,       -- trunk strength score (0-100)
    -- Blood Work & Biomarkers
    hemoglobin                DECIMAL(4, 1) NOT NULL, -- hemoglobin level (g/dL)
    glucose                   DECIMAL(5, 1) NOT NULL, -- blood glucose (mg/dL)
    creatinine                DECIMAL(4, 2) NOT NULL, -- kidney function marker (mg/dL)
    vitamin_d                 DECIMAL(4, 1) NOT NULL, -- vitamin D level (ng/mL)
    iron                      DECIMAL(5, 1) NOT NULL, -- serum iron (μg/dL)
    testosterone              DECIMAL(6, 1) NOT NULL, -- testosterone level (ng/dL)
    cortisol                  DECIMAL(4, 1) NOT NULL, -- stress hormone level (μg/dL)
    CONSTRAINT fk_report_doctor FOREIGN KEY (doctor_id) REFERENCES doctor (doctor_id) ON DELETE RESTRICT,
    CONSTRAINT fk_report_patient FOREIGN KEY (patient_id) REFERENCES patient (patient_id) ON DELETE RESTRICT,
    CONSTRAINT chk_body_fat_percentage CHECK (body_fat_percentage >= 0 AND body_fat_percentage <= 100),
    CONSTRAINT chk_core_stability CHECK (core_stability_score >= 0 AND core_stability_score <= 100)
);

--athlete report final summary entity
CREATE TABLE summary
(
    summary_id         BIGSERIAL PRIMARY KEY,
    report_id          BIGINT NOT NULL UNIQUE,
    summarized_content TEXT    NOT NULL, --the summarized context will be from the recommendations made for the report
    CONSTRAINT fk_summary_report FOREIGN KEY (report_id) REFERENCES athlete_report (report_id) ON DELETE RESTRICT
);

--entry mood text entity
CREATE TABLE mood
(
    mood_id          BIGSERIAL PRIMARY KEY,
    patient_id       BIGINT NOT NULL,
    mood_level       VARCHAR(50) NOT NULL,
    mood_description VARCHAR(200) NOT NULL,
    created_at       timestamp DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_mood_patient FOREIGN KEY (patient_id) REFERENCES patient (patient_id) ON DELETE RESTRICT
);

--recommendation table (supertype)
CREATE TABLE recommendation
(
    recommendation_id         BIGSERIAL PRIMARY KEY,
    report_id                 BIGINT            NOT NULL,
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