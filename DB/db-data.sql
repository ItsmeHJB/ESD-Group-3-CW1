/* Insert Users */
INSERT INTO Users (username, password, firstname, lastname, email, address, role) VALUES ('meaydin', 'aydinme', 'Mehmet', 'Aydin', 'meaydin@test.com', 'Mehmets Address, London, NW4 0BH', 'doctor');
INSERT INTO Users (username, password, firstname, lastname, email, address, role) VALUES ('eaydin', '12345me', 'Emin', 'Aydin', 'eaying@test.com', 'Emiin''s Address, Bristol, BS16', 'nurse');
INSERT INTO Users (username, password, firstname, lastname, email, address, role) VALUES ('caidan', '5432@10', 'Charly', 'Aidan', 'caidan@test.com', '14 King Street, Aberdeen, AB24 1BR', 'client');
INSERT INTO Users (username, password, firstname, lastname, email, address, role) VALUES ('princehassan', 'prince_passwd', 'Prince', 'Hassan', 'princehassan@test.com', 'Non-UK street, Non-UK Town, Non_UK', 'client');
INSERT INTO Users (username, password, firstname, lastname, email, address, role) VALUES ('admin', 'admin_passwd', 'admin', 'admin', 'admin@test.com', 'admin', 'admin');

/* Insert Clients */
INSERT INTO Clients (userid, isnhs) VALUES ((SELECT id FROM Users WHERE username = 'caidan'), TRUE);
INSERT INTO Clients (userid, isnhs) VALUES ((SELECT id FROM Users WHERE username = 'princehassan'), FALSE);

/* Insert Employees */
INSERT INTO Employees (userid, isfulltime) VALUES ((SELECT id FROM Users WHERE username = 'meaydin'), TRUE);
INSERT INTO Employees (userid, isfulltime) VALUES ((SELECT id FROM Users WHERE username = 'eaydin'), FALSE);

/* Insert Operations */
INSERT INTO Operations (employeeid, clientid, date, starttime, endtime, charge, slot, is_paid) VALUES ((SELECT id FROM Employees WHERE id = 1), (SELECT id FROM Clients WHERE id = 1), '2021-01-01', '12:00:00', '13:00:00', 200.00, 1, TRUE); 
INSERT INTO Operations (employeeid, clientid, date, starttime, endtime, charge, slot, is_paid) VALUES ((SELECT id FROM Employees WHERE id = 1), (SELECT id FROM Clients WHERE id = 1), '2021-01-02', '12:00:00', '13:00:00', 200.00, 1, FALSE);
INSERT INTO Operations (employeeid, clientid, date, starttime, endtime, charge, slot, is_paid) VALUES ((SELECT id FROM Employees WHERE id = 2), (SELECT id FROM Clients WHERE id = 2), '2021-01-02', '12:00:00', '13:00:00', 200.00, 1, FALSE);
INSERT INTO Operations (employeeid, clientid, date, starttime, endtime, charge, slot, is_paid) VALUES ((SELECT id FROM Employees WHERE id = 1), (SELECT id FROM Clients WHERE id = 1), '2020-12-16', '12:00:00', '13:00:00', 200.00, 1, TRUE); 
INSERT INTO Operations (employeeid, clientid, date, starttime, endtime, charge, slot, is_paid) VALUES ((SELECT id FROM Employees WHERE id = 1), (SELECT id FROM Clients WHERE id = 1), '2020-12-14', '12:00:00', '13:00:00', 200.00, 1, FALSE);
INSERT INTO Operations (employeeid, clientid, date, starttime, endtime, charge, slot, is_paid) VALUES ((SELECT id FROM Employees WHERE id = 2), (SELECT id FROM Clients WHERE id = 2), '2021-12-02', '12:00:00', '13:00:00', 200.00, 1, FALSE);

/* Create Prescriptions */
INSERT INTO Prescriptions (clientid, employeeid, drug_name, dosage, is_repeat, date_start, date_end) VALUES ((SELECT id FROM Clients WHERE id = 1), (SELECT id FROM Employees WHERE id = 1), 'Codeine', '50mg daily', TRUE, '2020-12-15', '2021-01-15');
INSERT INTO Prescriptions (clientid, employeeid, drug_name, dosage, is_repeat, date_start, date_end) VALUES ((SELECT id FROM Clients WHERE id = 1), (SELECT id FROM Employees WHERE id = 2), 'Tramadol', '50mg daily', TRUE, '2020-12-10', '2021-01-10');
INSERT INTO Prescriptions (clientid, employeeid, drug_name, dosage, is_repeat, date_start, date_end) VALUES ((SELECT id FROM Clients WHERE id = 2), (SELECT id FROM Employees WHERE id = 1), 'Lactulose', '15mls daily', TRUE, '2020-12-15', '2021-01-15');
INSERT INTO Prescriptions (clientid, employeeid, drug_name, dosage, is_repeat, date_start, date_end) VALUES ((SELECT id FROM Clients WHERE id = 2), (SELECT id FROM Employees WHERE id = 1), 'Cyclizine', '50mg weekly', TRUE, '2020-11-17', '2020-12-17');

/* Insert Prices */
INSERT INTO Prices (appointmenttype, employeetype, priceperslot) VALUES ('surgery', 'doctor', 9.99);
INSERT INTO Prices (appointmenttype, employeetype, priceperslot) VALUES ('surgery', 'nurse', 8.99);
INSERT INTO Prices (appointmenttype, employeetype, priceperslot) VALUES ('consultation', 'doctor', 5.99);
INSERT INTO Prices (appointmenttype, employeetype, priceperslot) VALUES ('consultation', 'nurse', 4.99);

/* Insert referral */
INSERT INTO Referrals (clientid, name, address) VALUES ((SELECT id FROM Clients WHERE id = 1), 'RUH Cardiac Ward', 'B45, RUH Bath, Combe Park, Bath, BA1 3NG');

/* Insert BookingSlots */
INSERT INTO BookingSlots (employeeid, clientid, issurgery, date, starttime, endtime, slot, hasbeenpaid) VALUES (1, 2, TRUE,'2020-01-01','12:00:00','12:10:00', 1, FALSE);

/* Insert dummy data into ApiCredentials */
INSERT INTO ApiCredentials (googlemapsapisecret) VALUES ('ReplaceMeWithAValidGoogleMapsAPISecret');