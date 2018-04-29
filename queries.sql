-- show table schema query
SELECT column_name
FROM information_schema.COLUMNS
WHERE table_name = 'TABLE_NAME';

-- *NOTE* all attributes with a 'REQUESTED' prefix should be replaced in the java level

-- given (check in, check out, view, bed type) list available rooms
SELECT room_code, ocean_view, bed_type
FROM rooms
WHERE ocean_view = REQUESTED_view AND
bed_type = REQUESTED_bed_type AND
room_code NOT IN
   (SELECT DISTINCT room AS reserved_rooms
   FROM reservations
   WHERE ocean_view = REQUESTED_view
   AND bed_type = REQUESTED_bed_type
   AND ((check_in BETWEEN REQUESTED_check_in AND REQUESTED_check_out)
   OR (check_out BETWEEN REQUESTED_check_in AND REQUESTED_check_out)))
ORDER BY room_code;

-- example query
-- SELECT room_code, ocean_view, bed_type
-- FROM rooms
-- WHERE ocean_view = 'true' AND
-- bed_type = 'double queen' AND
-- room_code NOT IN
--    (SELECT DISTINCT room AS reserved_rooms
--    FROM reservations
--    WHERE ocean_view = 'true'
--    AND bed_type = 'double queen'
--    AND ((check_in BETWEEN '2018-01-03' AND '2018-01-05')
--    OR (check_out BETWEEN '2018-01-03' AND '2018-01-05')))
-- ORDER BY room_code;

-- view all reservations made by a particular customer
SELECT *
FROM reservations
WHERE customer = REQUESTED_customer_login
ORDER BY check_in;

-- example query
-- SELECT *
-- FROM reservations
-- WHERE customer = 'nh'
-- ORDER BY check_in;

-- *NOTE* should run all 3 together to completely remove reservation data from database
-- remove a certain reservation if check-in date has not passed
DELETE FROM reservations
WHERE res_code = REQUESTED_res_code
AND current_date > check_in;

-- example query
-- DELETE FROM reservations
-- WHERE res_code = 69
-- AND current_date > check_in;

-- remove pre-paided charges for an upcoming reservation
DELETE FROM additional_charges_invoices
WHERE reservation = REQUESTED_res_code
AND current_date > charge_date;

-- example query
-- DELETE FROM additional_charges_invoices
-- WHERE reservation = 69
-- AND current_date > charge_date;

-- remove room rate record for cancelled reservation
DELETE FROM room_rate_history
WHERE reservation = REQUESTED_res_code
AND current_date > charge_date;

-- example query
-- DELETE FROM room_rate_history
-- WHERE reservation = 69
-- AND current_date > charge_date;

-- list additional items available for purchase
SELECT description, cost
FROM additional_charges;

-- adding an item to an invoice
INSERT INTO additional_charges_invoices VALUES ();
-- calculate final bill at check-out...given reservation ID

-- calculate total for just using the room
SELECT SUM(rate) AS room_charge_total
FROM room_rate_history
WHERE reservation = REQUESTED_res_code
AND current_date >= res_date;

-- example query
-- SELECT SUM(rate) AS room_charge_total
-- FROM room_rate_history
-- WHERE reservation = 1
-- AND current_date >= res_date;

-- calculate total charge for additional charges
SELECT SUM(cost) AS additional_charges_total
FROM additional_charges_invoices
INNER JOIN additional_charges
ON charge = charge_code
WHERE reservation = REQUESTED_res_code
AND current_date >= charge_date;

-- example query
-- SELECT SUM(cost) AS additional_charges_total
-- FROM additional_charges_invoices
-- INNER JOIN additional_charges
-- ON charge = charge_code
-- WHERE reservation = 1
-- AND current_date >= charge_date;

-- combined total charge
SELECT SUM(total)
FROM
   (SELECT *
   FROM
      (SELECT SUM(rate) AS total
      FROM room_rate_history
      WHERE reservation = REQUESTED_res_code
      AND current_date >= res_date) AS room_total
   UNION
   SELECT *
   FROM
      (SELECT SUM(cost) AS total
      FROM additional_charges_invoices
      INNER JOIN additional_charges
      ON charge = charge_code
      WHERE reservation = REQUESTED_res_code
      AND current_date >= charge_date) AS additional_total
   ) AS combined_total;

-- example query
-- SELECT SUM(total)
-- FROM
--    (SELECT *
--    FROM
--       (SELECT SUM(rate) AS total
--       FROM room_rate_history
--       WHERE reservation = 1
--       AND current_date >= res_date) AS room_total
--    UNION
--    SELECT *
--    FROM
--       (SELECT SUM(cost) AS total
--       FROM additional_charges_invoices
--       INNER JOIN additional_charges
--       ON charge = charge_code
--       WHERE reservation = 1
--       AND current_date >= charge_date) AS additional_total
--    ) AS combined_total;
