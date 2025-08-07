insert into doctor(first_name, last_name, specialization, email) values
         ('Daze', 'Tristan', 'Physiotherapist', 'daze@gmail.com'),
         ('Gege', 'Landovski', 'Sports medicine doctor', 'gege@yahoo.com');


insert into patient(doctor_id, first_name, last_name, date_of_birth, gender, sportsman_category, email) values
        (1, 'Pancho', 'Ribarski', '1999-09-09', 'MALE', 'RECREATION', 'pamcho@gmail.gov'),
        (2, 'Bancho', 'Tubarski', '1997-09-09', 'FEMALE', 'AMATEUR', 'pbancho@gmail.gov');


insert into mood(patient_id, mood_progress, mood_emotion, hours_slept_average, mood_description, mood_description_score, created_at) values
         (1, 'GOOD', 'NEUTRAL', 7, 'I love my life', 8, now()),
         (1, 'STALL', 'TIRED', 7, 'I love my life', 5, now()-interval '2 weeks');

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

insert into recommendation(
    report_id, type, restriction_level,
    label, description, cost_per_month, duration_weeks, frequency_per_day,
    target_goal, effectiveness_rating, doctor_personalized_notes
) values

      -- Report 1
      (1, 'TRAINING', 'HARD',
       'HIIT Fat-Burn Plan',
       'Three high-intensity interval sessions per week to accelerate fat loss.',
       0, 8, 3,
       'Reduce body fat to ~12%', 4,
       'Keep your heart rate in zone 4–5!'),

      (1, 'DIET', 'NORMAL',
       'Balanced Calorie-Deficit Diet',
       'Daily 500 kcal deficit with lean proteins, veggies, and whole grains.',
       200, 8, 3,
       'Achieve BMI ~21', 3,
       'Track macros closely.'),

      (1, 'SUPPLEMENT', 'NORMAL',
       'Omega-3 Fish Oil',
       '1 g fish oil daily to support metabolism and joint health.',
       15, 8, 1,
       'Improve recovery & inflammation', 3,
       'Take with meals.'),

      -- Report 2
      (2, 'TRAINING', 'EXTREME',
       'Ultra-Endurance Running',
       'Long-distance runs (20–25 km) twice weekly for stamina build-up.',
       0, 10, 2,
       'Prepare for endurance events', 5,
       'Stay hydrated—plan routes with water stops.'),

      (2, 'DIET', 'EXTREME',
       'Ketogenic Meal Plan',
       'High-fat, very low-carb diet to train fat adaptation.',
       150, 10, 3,
       'Enter nutritional ketosis', 4,
       'Monitor ketone levels daily.'),

      (2, 'SUPPLEMENT', 'EXTREME',
       'Electrolyte Replacement',
       'Electrolyte powder twice daily to prevent cramps during long runs.',
       25, 10, 2,
       'Maintain electrolyte balance', 4,
       'Mix 1 scoop in 500 ml water.'),

      -- Report 3
      (3, 'TRAINING', 'NORMAL',
       'Strength Builder Routine',
       'Four weekly sessions of compound lifts (squat, deadlift, bench).',
       0, 12, 4,
       'Increase overall strength', 4,
       'Focus on progressive overload.'),

      (3, 'DIET', 'HARD',
       'High-Protein Diet',
       '1.8 g protein per kg bodyweight using lean meats and legumes.',
       100, 12, 5,
       'Support muscle gain', 4,
       'Distribute protein evenly across meals.'),

      (3, 'SUPPLEMENT', 'NORMAL',
       'Creatine Monohydrate',
       '5 g creatine daily to enhance strength performance.',
       20, 12, 1,
       'Boost power output', 5,
       'Take post-workout with carbs.'),

      -- Report 4
      (4, 'TRAINING', 'NORMAL',
       'Daily Flexibility & Mobility',
       '15 min of dynamic and static stretching each morning.',
       0, 6, 1,
       'Improve joint range of motion', 3,
       'Hold each stretch for 30 s.'),

      (4, 'DIET', 'NORMAL',
       'Anti-Inflammatory Diet',
       'Emphasize fruits, vegetables, and omega-3 rich foods to reduce inflammation.',
       120, 6, 3,
       'Reduce systemic inflammation', 3,
       'Include berries and leafy greens.'),

      (4, 'SUPPLEMENT', 'HARD',
       'Vitamin D3',
       '2000 IU daily to support bone density and immunity.',
       10, 6, 1,
       'Maintain optimal vitamin D levels', 4,
       'Take with the largest meal.'),

      -- Report 5
      (5, 'TRAINING', 'HARD',
       'Sprint Interval Training',
       '10×100 m sprints with 60 s rest to boost anaerobic capacity.',
       0, 4, 2,
       'Improve sprint speed', 4,
       'Warm up properly before sprints.'),

      (5, 'DIET', 'NORMAL',
       'High-Protein, Low-Fat Plan',
       'Lean proteins, plenty of veggies, minimal added fats.',
       110, 4, 4,
       'Support muscle repair', 4,
       'Aim for 30 g protein per meal.'),

      (5, 'SUPPLEMENT', 'NORMAL',
       'Multivitamin Complex',
       'One tablet daily to cover any micronutrient gaps.',
       20, 4, 1,
       'Ensure daily micronutrient intake', 3,
       'Take in the morning with food.'),

      -- Report 6
      (6, 'TRAINING', 'NORMAL',
       'Core Stability Program',
       'Daily plank progressions and rotational exercises for 8 weeks.',
       0, 8, 1,
       'Enhance core strength', 4,
       'Maintain neutral spine at all times.'),

      (6, 'DIET', 'NORMAL',
       'Hydration & Electrolyte Focus',
       'Drink 2.5 L water daily plus natural electrolyte sources.',
       0, 8, 5,
       'Optimize hydration levels', 3,
       'Add a pinch of salt and lemon.'),

      (6, 'SUPPLEMENT', 'HARD',
       'Probiotic Support',
       'Daily probiotic capsule to support gut health and nutrient absorption.',
       25, 8, 1,
       'Improve digestive health', 3,
       'Take before bedtime.');

insert into summary(report_id, summarized_content) values
       (1, 'VO₂ max is excellent at 58.5 and body fat is healthy at 12.5%, but under-pressure heart rate peaks at 165 bpm. Recommendation: add 2–3 low-intensity cardio sessions per week and breathing drills to improve recovery.'),

       (2, 'VO₂ max has dropped to 42.3 and body fat risen to 18.2%, with resting HR at 68 bpm. Recommendation: perform 3 HIIT workouts weekly, cut simple carbs to maintain a 300 kcal deficit daily, and prioritize ≥7 hours of sleep.'),

       (3, 'VO₂ max is low at 32.8 and body fat high at 28.5%, resting HR 85 bpm. Recommendation: build aerobic base with 4 moderate 30–40 min sessions at 60–70% max HR and follow a 500 kcal deficit high-protein diet to reduce fat and increase lean mass.'),

       (4, 'VO₂ max of 38.2 and body fat of 22% are borderline, though strength numbers are strong. Recommendation: add two 20 min tempo runs and one 60 min easy run weekly, and replace processed snacks with whole-food proteins and vegetables to lower body fat.'),

       (5, 'VO₂ max is outstanding at 62.1 and body fat very low at 8.5%, but jump height is modest at 55 cm. Recommendation: incorporate plyometric drills and unilateral leg exercises twice weekly, with a protein-rich post-workout snack for muscle repair.'),

       (6, 'VO₂ max is average at 35.7 with 25.8% body fat, and resting HR 78 bpm. Recommendation: mix two weekly HIIT sessions with one 45 min endurance workout, daily core circuits, and swap sugary snacks for high-fiber foods to stabilize blood sugar.');

