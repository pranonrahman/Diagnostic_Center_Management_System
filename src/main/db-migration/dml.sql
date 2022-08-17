insert into role(id, name, created, updated) values(1, 'ADMIN', current_timestamp(), current_timestamp());
insert into role(id, name, created, updated) values(2, 'DOCTOR', current_timestamp(), current_timestamp());
insert into role(id, name, created, updated) values(3, 'PATIENT', current_timestamp(), current_timestamp());
insert into role(id, name, created, updated) values(4, 'RECEPTIONIST', current_timestamp(), current_timestamp());

insert into medicine (id, name, generic_name, unit_price, available_units, created, updated)
values (1, 'Napa', 'Paracetamol', 10, 100, current_timestamp(), current_timestamp());

insert into medicine (id, name, generic_name, unit_price, available_units, created, updated)
values (2, 'Seclo 20', 'Omeprazole', 5.40, 100, current_timestamp(), current_timestamp());

insert into medicine (id, name, generic_name, unit_price, available_units, created, updated)
values (3, 'Fexo 120', 'Fexofenadine Hydrochloride', 73.60, 100, current_timestamp(), current_timestamp());

insert into medicine (id, name, generic_name, unit_price, available_units, created, updated)
values (4, 'Ceevit', 'Vitamin C', 17.1, 100, current_timestamp(), current_timestamp());

insert into facility (id, name, price, created, updated) values (1, 'MRI', 1200, current_timestamp(), current_timestamp());
insert into facility (id, name, price, created, updated) values (2, 'City Scan', 1700, current_timestamp(), current_timestamp());
insert into facility (id, name, price, created, updated) values (3, 'CBC', 400, current_timestamp(), current_timestamp());
insert into facility (id, name, price, created, updated) values (4, 'Covid 19 Test', 200, current_timestamp(), current_timestamp());

insert into user (id, user_name, password, name, phone, email, gender, date_of_birth, created, updated)
values (1, 'akkas', 'akkas', 'Akkas Ali Mollah', '01458697896', 'akkas.1999@gmail.com', 'MALE', '1995-02-02', current_timestamp(), current_timestamp());

insert into user_role (user_id, role_id) values (1, 1);
insert into admin (id, created, updated) values (1, current_timestamp(), current_timestamp());