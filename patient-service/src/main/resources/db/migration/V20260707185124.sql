ALTER TABLE patient
    ALTER COLUMN date_of_birth TYPE date USING (date_of_birth::date);