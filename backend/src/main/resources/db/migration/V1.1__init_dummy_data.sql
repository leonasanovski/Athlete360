insert into doctor(first_name, last_name, specialization, email) values
                                                                     ('Daze', 'Tristan', 'Physiotherapist', 'daze@gmail.com'),
                                                                     ('Gege', 'Landovski', 'Sports medicine doctor', 'gege@yahoo.com');


insert into patient(doctor_id, first_name, last_name, date_of_birth, gender, sportsman_category, email) values
                                                                                                            (1, 'Pancho', 'Ribarski', '1999-09-09', 'MALE', 'RECREATION', 'pamcho@gmail.gov'),
                                                                                                            (2, 'Bancho', 'Tubarski', '1997-09-09', 'FEMALE', 'AMATEUR', 'pbancho@gmail.gov');


insert into mood(patient_id, mood_progress, mood_emotion, hours_slept_average, mood_description, created_at) values
                                                                                                                 (1, 'GOOD', 'NEUTRAL', 7, 'I love my life', now()),
                                                                                                                 (1, 'STALL', 'TIRED', 7, 'I love my life', now()-interval '2 weeks');

INSERT INTO athlete_report (
    doctor_id, patient_id, created_at, status,
    vo2_max, resting_heart_rate, under_pressure_heart_rate,
    body_fat_percentage, lean_muscle_mass, bone_density,
    height, weight, one_rep_max_bench,
    one_rep_max_squat, one_rep_max_deadlift, jump_height,
    average_run_per_kilometer, shoulder_flexibility, hip_flexibility,
    balance_time, reaction_time, core_stability_score,
    hemoglobin, glucose, creatinine,
    vitamin_d, iron, testosterone, cortisol
) VALUES
      (1, 1, '2025-08-05 14:18:10', 'GOOD',
       58.50, 45, 165,
       12.5, 75.2, 1.35,
       175.0, 72.0, 125.0,
       180.0, 220.0, 65.0,
       4.15, 30, 25,
       45.0, 0.25, 85,
       14.5, 90.0, 1.10,
       30.0, 50.0, 500.0, 350.0),

      (1, 2, '2025-07-05 15:22:45', 'FOLLOWUP',
       42.30, 68, 145,
       18.2, 65.8, 1.18,
       168.0, 75.5,  85.0,
       110.0, 140.0, 42.0,
       5.45, 28, 22,
       38.0, 0.30, 78,
       13.2, 95.0, 1.25,
       28.0, 45.0, 320.0, 420.0),

      (2, 1, '2025-08-01 16:10:33', 'IMPROVED',
       32.80, 85, 175,
       28.5, 45.2, 0.95,
       175.0, 92.0,  60.0,
       85.0, 100.0, 28.0,
       7.20, 24, 20,
       30.0, 0.40, 72,
       12.8,105.0, 1.45,
       25.0, 40.0, 280.0, 520.0),

      (2, 2, '2025-08-02 17:45:12', 'FOLLOWUP',
       38.20, 75, 155,
       22.0, 82.5, 1.42,
       183.0, 95.0, 160.0,
       220.0, 280.0, 38.0,
       6.30, 26, 24,
       40.0, 0.35, 88,
       16.5, 88.0, 1.35,
       32.0, 55.0, 520.0, 380.0),

      (1, 1, '2025-08-05 18:30:55', 'IMPROVED',
       62.10, 42, 138,
       8.5, 58.8, 1.15,
       175.0, 68.0,  75.0,
       95.0, 125.0, 55.0,
       3.45, 32, 30,
       50.0, 0.20, 90,
       15.2, 82.0, 1.05,
       24.0, 60.0, 380.0, 280.0),

      (1, 2, '2025-08-05 19:15:20', 'IMPROVED',
       35.70, 78, 152,
       25.8, 52.3, 0.88,
       168.0, 78.0,  70.0,
       90.0, 115.0, 32.0,
       6.15, 29, 27,
       48.0, 0.28, 82,
       13.5, 98.0, 1.55,
       26.0, 42.0, 220.0, 480.0);