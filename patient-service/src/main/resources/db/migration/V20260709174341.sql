ALTER TABLE patient
    ADD CONSTRAINT uc_patient_phonenumber UNIQUE (phone_number);

ALTER TABLE patient
    DROP COLUMN date_of_birth;

ALTER TABLE patient
    ADD date_of_birth date;

UPDATE patient
SET date_of_birth = '1970-01-01'
WHERE date_of_birth IS NULL;

ALTER TABLE patient
    ALTER COLUMN date_of_birth SET NOT NULL;