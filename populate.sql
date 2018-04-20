INSERT INTO customers (login, password, first_name, last_name, email, street_number, street, city, zip_code, credit_card, exp_date, crc_code)
VALUES
('nh', 'password1', 'Nathan', 'Hong', 'nh@gmail.com', 1234, 'A street', 'Manteca', 95337, 1010101010101010, '2017-01-01', 111),
('dl', 'password2', 'Derek', 'Lung', 'dl@gmail.com', 5647, 'B street', 'Galt', 95632, 2020202020202020, '2017-02-02', 222),
('pm', 'password3', 'Patrick', 'Malapira', 'pm@gmail.com', 94536, 'C street', 'Fremont', 95632, 3030303030303030, '2017-03-03', 333),
('js', 'password69', 'Japji', 'Singh', 'js@gmail.com', 92501, '69 street', 'Flavortown', 95632, 6969696969696969, '2017-06-09', 666);

INSERT INTO employees (emp_id, password, first_name, last_name)
VALUES
(11111, 'password1', 'f1', 'l1'),
(22222, 'password2', 'f2', 'l2'),
(33333, 'password3', 'f3', 'l3'),
(44444, 'password4', 'f4', 'l4'),
(55555, 'password5', 'f5', 'l5');

INSERT INTO rooms (room_code, ocean_view, bed_type, base_price)
VALUES
-- floor 1
(101, 'true', 'double queen', 100),
(102, 'true', 'single king', 100),
(103, 'true', 'double queen', 100),
(104, 'true', 'single king', 100),
(105, 'true', 'double queen', 100),
(106, 'true', 'single king', 100),
(107, 'false', 'double queen', 100),
(108, 'false', 'single king', 100),
(109, 'false', 'double queen', 100),
(110, 'false', 'single king', 100),
(111, 'false', 'double queen', 100),
(112, 'false', 'single king', 100),
-- floor 2
(201, 'true', 'double queen', 100),
(202, 'true', 'single king', 100),
(203, 'true', 'double queen', 100),
(204, 'true', 'single king', 100),
(205, 'true', 'double queen', 100),
(206, 'true', 'single king', 100),
(207, 'false', 'double queen', 100),
(208, 'false', 'single king', 100),
(209, 'false', 'double queen', 100),
(210, 'false', 'single king', 100),
(211, 'false', 'double queen', 100),
(212, 'false', 'single king', 100),
-- floor 3
(301, 'true', 'double queen', 100),
(302, 'true', 'single king', 100),
(303, 'true', 'double queen', 100),
(304, 'true', 'single king', 100),
(305, 'true', 'double queen', 100),
(306, 'true', 'single king', 100),
(307, 'false', 'double queen', 100),
(308, 'false', 'single king', 100),
(309, 'false', 'double queen', 100),
(310, 'false', 'single king', 100),
(311, 'false', 'double queen', 100),
(312, 'false', 'single king', 100),
-- floor 4
(401, 'true', 'double queen', 100),
(402, 'true', 'single king', 100),
(403, 'true', 'double queen', 100),
(404, 'true', 'single king', 100),
(405, 'true', 'double queen', 100),
(406, 'true', 'single king', 100),
(407, 'false', 'double queen', 100),
(408, 'false', 'single king', 100),
(409, 'false', 'double queen', 100),
(410, 'false', 'single king', 100),
(411, 'false', 'double queen', 100),
(412, 'false', 'single king', 100),
-- floor 5
(501, 'true', 'double queen', 100),
(502, 'true', 'single king', 100),
(503, 'true', 'double queen', 100),
(504, 'true', 'single king', 100),
(505, 'true', 'double queen', 100),
(506, 'true', 'single king', 100),
(507, 'false', 'double queen', 100),
(508, 'false', 'single king', 100),
(509, 'false', 'double queen', 100),
(510, 'false', 'single king', 100),
(511, 'false', 'double queen', 100),
(512, 'false', 'single king', 100);

INSERT INTO additional_charges (charge_code, description, cost)
VALUES
(1, 'wifi', 10),
(2, 'breakfast', 15),
(3, 'dry clean', 17),
(4, 'pool', 5),
(5, 'paperview', 7);

-- only ocean view rooms on the top two floors gets price bumps
INSERT INTO special_room_rates (special_rate_code, room, book_date, rate)
VALUES
-- New Year's Day
-- floor 4
(1, 401, '2018-01-01', 150),
(2, 402, '2018-01-01', 150),
(3, 403, '2018-01-01', 150),
(4, 404, '2018-01-01', 150),
(5, 405, '2018-01-01', 150),
(6, 406, '2018-01-01', 150),
-- floor 5
(7, 501, '2018-01-01', 150),
(8, 502, '2018-01-01', 150),
(9, 503, '2018-01-01', 150),
(10, 504, '2018-01-01', 150),
(11, 505, '2018-01-01', 150),
(12, 506, '2018-01-01', 150),

-- Fourth of July
-- floor 4
(13, 401, '2018-07-04', 165),
(14, 402, '2018-07-04', 165),
(15, 403, '2018-07-04', 165),
(16, 404, '2018-07-04', 165),
(17, 405, '2018-07-04', 165),
(18, 406, '2018-07-04', 165),
-- floor 5
(19, 501, '2018-07-04', 165),
(20, 502, '2018-07-04', 165),
(21, 503, '2018-07-04', 165),
(22, 504, '2018-07-04', 165),
(23, 505, '2018-07-04', 165),
(24, 506, '2018-07-04', 165),

-- Thanksgiving
-- floor 4
(25, 401, '2018-11-22', 140),
(26, 402, '2018-11-22', 140),
(27, 403, '2018-11-22', 140),
(28, 404, '2018-11-22', 140),
(29, 405, '2018-11-22', 140),
(30, 406, '2018-11-22', 140),
-- floor 5
(31, 501, '2018-11-22', 140),
(32, 502, '2018-11-22', 140),
(33, 503, '2018-11-22', 140),
(34, 504, '2018-11-22', 140),
(35, 505, '2018-11-22', 140),
(36, 506, '2018-11-22', 140);

INSERT INTO reservations (res_code, check_in, check_out, room, customer)
VALUES
(1, '2018-01-03', '2018-01-05', 201, nh),
(2, '2018-03-03', '2018-03-04', 311, dl),
(3, '2018-07-03', '2018-07-05', 401, pm),
(4, '2018-11-20', '2018-11-21', 506, js);

INSERT INTO additonal_charges_invoices (invoice_code, reservation, charge_date, charge, quantity)
VALUES
(1, 1, '2018-01-03', 1, 1),
(2, 1, '2018-01-04', 1, 1),
(3, 1, '2018-01-05', 1, 1),
(4, 2, '2018-03-03', 2, 2),
(5, 2, '2018-03-04', 3, 1);

INSERT INTO room_rate_history (rate_id, reservation, res_date, rate)
VALUES
(1, 1, '2018-01-03', 100),
(2, 1, '2018-01-04', 100),
(3, 1, '2018-01-05', 100),
(4, 2, '2018-03-03', 100),
(5, 2, '2018-03-04', 100),
(6, 3, '2018-07-03', 100),
(7, 3, '2018-07-04', 165), -- special price
(8, 3, '2018-07-05', 100),
(9, 4, '2018-11-20', 100),
(10, 4, '2018-11-21', 100);
