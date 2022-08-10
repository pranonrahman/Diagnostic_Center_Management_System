CREATE TABLE person(
     id NUMERIC(19),
     name VARCHAR(50) NOT NULL,
     phone VARCHAR(15) NOT NULL,
     email VARCHAR(50),
     gender VARCHAR(10),
     date_of_birth DATE NOT NULL ,
     user_name VARCHAR(50) NOT NULL UNIQUE,
     password VARCHAR(50) NOT NULL,
     doctor_id NUMERIC(19),
     admin_id NUMERIC(19),
     patient_id NUMERIC(19),
     receptionist_id NUMERIC(19),
     PRIMARY KEY (id)
);

CREATE TABLE role(
  id NUMERIC(19),
  name VARCHAR(50) NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE particular(
    id NUMERIC(19),
    name VARCHAR(100) NOT NULL,
    unit_price NUMERIC(20, 4) NOT NULL,
    units INT NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE medicine(
     id NUMERIC(19),
     name VARCHAR(50) NOT NULL,
     generic_name VARCHAR(50),
     unit_price NUMERIC(20, 4),
     available_units INT,
     PRIMARY KEY (id)
);

CREATE TABLE facility(
     id NUMERIC(19),
     name VARCHAR(50) NOT NULL,
     price NUMERIC(20, 4),
     PRIMARY KEY (id)
);

CREATE TABLE admin(
    id NUMERIC(19),
    person_id NUMERIC(19),
    PRIMARY KEY (id),
    FOREIGN KEY (person_id) references person(id)
);

CREATE TABLE patient(
    id NUMERIC(19),
    person_id NUMERIC(19),
    PRIMARY KEY (id),
    FOREIGN KEY (person_id) references person(id)
);

CREATE TABLE receptionist(
    id NUMERIC(19),
    person_id NUMERIC(19),
    PRIMARY KEY (id),
    FOREIGN KEY (person_id) references person(id)
);

CREATE TABLE doctor(
    id NUMERIC(19),
    person_id NUMERIC(19),
    fee NUMERIC(20, 4),
    PRIMARY KEY (id),
    FOREIGN KEY (person_id) references person(id)
);

CREATE TABLE invoice(
    id NUMERIC(19),
    invoice_id VARCHAR(100) NOT NULL UNIQUE,
    generation_date DATE NOT NULL,
    total_cost NUMERIC(20, 4) NOT NULL,
    receptionist_id NUMERIC(19) NOT NULL,
    patient_id NUMERIC(19) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (receptionist_id) references receptionist(id),
    FOREIGN KEY (patient_id) references patient(id)
);

CREATE TABLE prescription(
    id NUMERIC(19),
    symptoms VARCHAR(3000),
    diagnosis VARCHAR(3000),
    comment VARCHAR(3000),
    date_of_visit DATE NOT NULL,
    patient_id NUMERIC(19) NOT NULL,
    doctor_id NUMERIC(19) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (doctor_id) references doctor(id),
    FOREIGN KEY (patient_id) references patient(id)
);

CREATE TABLE invoice_particular(
    invoice_id NUMERIC(19) NOT NULL,
    particular_id NUMERIC(19) NOT NULL
);

CREATE TABLE prescription_facility(
    prescription_id NUMERIC(19) NOT NULL,
    facility_id NUMERIC(19) NOT NULL
);

CREATE TABLE person_role(
    person_id NUMERIC(19) NOT NULL,
    role_id NUMERIC(19) NOT NULL
);