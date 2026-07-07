CREATE SEQUENCE IF NOT EXISTS revinfo_seq START WITH 1 INCREMENT BY 50;

CREATE TABLE patient
(
    id                UUID         NOT NULL,
    first_name        VARCHAR(255) NOT NULL,
    last_name         VARCHAR(255) NOT NULL,
    email             VARCHAR(255) NOT NULL,
    address           VARCHAR(255) NOT NULL,
    phone_number      VARCHAR(255),
    date_of_birth     TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    registration_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT pk_patient PRIMARY KEY (id)
);

CREATE TABLE revchanges
(
    rev        BIGINT NOT NULL,
    entityname VARCHAR(255)
);

CREATE TABLE revinfo
(
    rev      BIGINT NOT NULL,
    revtstmp BIGINT,
    CONSTRAINT pk_revinfo PRIMARY KEY (rev)
);

ALTER TABLE patient
    ADD CONSTRAINT uc_patient_email UNIQUE (email);

ALTER TABLE revchanges
    ADD CONSTRAINT fk_revchanges_on_default_tracking_modified_entities_changelog FOREIGN KEY (rev) REFERENCES revinfo (rev);