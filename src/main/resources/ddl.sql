CREATE TABLE user(
     id NUMERIC(19),
     name VARCHAR(50) NOT NULL,
     phone VARCHAR(15) NOT NULL,
     email VARCHAR(50),
     gender VARCHAR(10),
     date_of_birth DATE NOT NULL ,
     user_name VARCHAR(50) NOT NULL UNIQUE,
     password VARCHAR(50) NOT NULL,
     created DATETIME NOT NULL,
     updated DATETIME NOT NULL,
     PRIMARY KEY (id)
);

CREATE TABLE role(
  id NUMERIC(19),
  name VARCHAR(50) NOT NULL,
  created DATETIME NOT NULL,
  updated DATETIME NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE particular(
    id NUMERIC(19),
    name VARCHAR(100) NOT NULL,
    unit_price NUMERIC(20, 4) NOT NULL,
    units INT NOT NULL,
    created DATETIME NOT NULL,
    updated DATETIME NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE medicine(
     id NUMERIC(19),
     name VARCHAR(50) NOT NULL,
     generic_name VARCHAR(50),
     unit_price NUMERIC(20, 4),
     available_units INT,
     created DATETIME NOT NULL,
     updated DATETIME NOT NULL,
     PRIMARY KEY (id)
);

CREATE TABLE facility(
     id NUMERIC(19),
     name VARCHAR(50) NOT NULL,
     price NUMERIC(20, 4),
     created DATETIME NOT NULL,
     updated DATETIME NOT NULL,
     PRIMARY KEY (id)
);

CREATE TABLE admin(
    id NUMERIC(19),
    created DATETIME NOT NULL,
    updated DATETIME NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (id) REFERENCES user(id) ON DELETE CASCADE
);

CREATE TABLE patient(
    id NUMERIC(19),
    created DATETIME NOT NULL,
    updated DATETIME NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (id) REFERENCES user(id) ON DELETE CASCADE
);

CREATE TABLE receptionist(
    id NUMERIC(19),
    created DATETIME NOT NULL,
    updated DATETIME NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (id) REFERENCES user(id) ON DELETE CASCADE
);

CREATE TABLE doctor(
    id NUMERIC(19),
    fee NUMERIC(20, 4),
    created DATETIME NOT NULL,
    updated DATETIME NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (id) REFERENCES user(id) ON DELETE CASCADE
);

CREATE TABLE invoice(
    id NUMERIC(19),
    invoice_id VARCHAR(100) NOT NULL UNIQUE,
    total_cost NUMERIC(20, 4) NOT NULL,
    receptionist_id NUMERIC(19) NOT NULL,
    patient_id NUMERIC(19) NOT NULL,
    created DATETIME NOT NULL,
    updated DATETIME NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (receptionist_id) references receptionist(id) ON DELETE CASCADE,
    FOREIGN KEY (patient_id) references patient(id) ON DELETE CASCADE
);

CREATE TABLE prescription(
    id NUMERIC(19),
    symptoms VARCHAR(3000),
    diagnosis VARCHAR(3000),
    comment VARCHAR(3000),
    patient_id NUMERIC(19) NOT NULL,
    doctor_id NUMERIC(19) NOT NULL,
    created DATETIME NOT NULL,
    updated DATETIME NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (doctor_id) references doctor(id) ON DELETE CASCADE,
    FOREIGN KEY (patient_id) references patient(id) ON DELETE CASCADE
);

CREATE TABLE invoice_particular(
    invoice_id NUMERIC(19) NOT NULL,
    particular_id NUMERIC(19) NOT NULL
);

CREATE TABLE prescription_facility(
    prescription_id NUMERIC(19) NOT NULL,
    facility_id NUMERIC(19) NOT NULL
);

CREATE TABLE user_role(
    user_id NUMERIC(19) NOT NULL,
    role_id NUMERIC(19) NOT NULL
);