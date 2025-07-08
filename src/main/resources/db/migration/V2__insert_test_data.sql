/* ---------- LOCATIONS ---------- */
INSERT INTO locations (country, city, street, street_number) VALUES
                                                                 ('Czech Republic', 'Prague', 'Main Street',  '1A'),   -- id = 1
                                                                 ('Czech Republic', 'Brno',   'Second Street', '22B'), -- id = 51
                                                                 ('Czech Republic', 'Ostrava','Third Avenue',  '333C');-- id = 101


/* ---------- ROOMS (17) ---------- */
INSERT INTO rooms (active, location_id, person_capacity) VALUES
                                                             (true, 1,   2),   -- id 1
                                                             (true, 1,   4),   -- id 51
                                                             (true, 1,   1),   -- id 101
                                                             (true, 51,  3),   -- id 151
                                                             (true, 51,  2),   -- id 201
                                                             (true, 51,  5),   -- id 251
                                                             (true, 101, 3),   -- id 301
                                                             (true, 101, 2),   -- id 351
                                                             (true, 101, 1),   -- id 401
                                                             (true, 1,   6),   -- id 451
                                                             (true, 51,  3),   -- id 501
                                                             (true, 101, 2),   -- id 551
                                                             (true, 1,   4),   -- id 601
                                                             (true, 51,  3),   -- id 651
                                                             (true, 101, 2),   -- id 701
                                                             (false,51,  7),   -- id 751
                                                             (false,1,   3);   -- id 801

/* ---------- ROOM EQUIPMENT ---------- */
INSERT INTO room_equipment (room_id, equipment) VALUES
                                                    (1,   'TV'),
                                                    (1,   'FRIDGE'),
                                                    (51,  'WASHING_MACHINE'),
                                                    (151, 'TV'),
                                                    (151, 'WASHING_MACHINE'),
                                                    (201, 'FRIDGE'),
                                                    (301, 'TV'),
                                                    (451, 'FRIDGE'),
                                                    (501, 'TV'),
                                                    (501, 'WASHING_MACHINE'),
                                                    (601, 'FRIDGE'),
                                                    (651, 'TV');


/* ---------- RESERVATIONS ---------- */
-- Jedna rezervace pro prvních 10 pokojů (id 1–451)
INSERT INTO reservations (room_id, date_from, date_to) VALUES
                                                           (1,   '2025-07-10 10:00:00', '2025-07-10 12:00:00'),
                                                           (51,  '2025-07-11 09:00:00', '2025-07-11 11:00:00'),
                                                           (101, '2025-07-12 14:00:00', '2025-07-12 18:00:00'),
                                                           (151, '2025-07-13 08:00:00', '2025-07-13 10:30:00'),
                                                           (201, '2025-07-14 15:00:00', '2025-07-14 17:00:00'),
                                                           (251, '2025-07-15 10:00:00', '2025-07-15 13:00:00'),
                                                           (301, '2025-07-16 09:00:00', '2025-07-16 12:00:00'),
                                                           (351, '2025-07-17 14:00:00', '2025-07-17 17:00:00'),
                                                           (401, '2025-07-18 11:00:00', '2025-07-18 14:00:00'),
                                                           (451, '2025-07-19 13:00:00', '2025-07-19 16:00:00');

-- Tři rezervace pro pokoj id 501
INSERT INTO reservations (room_id, date_from, date_to) VALUES
                                                           (501, '2025-07-10 08:00:00', '2025-07-10 10:00:00'),
                                                           (501, '2025-07-11 11:00:00', '2025-07-11 13:00:00'),
                                                           (501, '2025-07-12 09:00:00', '2025-07-12 11:00:00');