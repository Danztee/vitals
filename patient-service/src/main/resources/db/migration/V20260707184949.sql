ALTER TABLE patient
    DROP COLUMN date_of_birth;

ALTER TABLE patient
    ADD date_of_birth date NOT NULL;