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

-- view all reservations made by a particular customer
SELECT *
FROM reservations
WHERE customer = REQUESTED_customer_login
ORDER BY check_in;

-- *NOTE* should run all 3 together to completely remove reservation data from database
-- remove a certain reservation if check-in date has not passed
DELETE FROM reservations
WHERE res_code = REQUESTED_res_code
AND current_date > check_in;

-- remove pre-paided charges for an upcoming reservation
DELETE FROM additional_charges_invoices
WHERE reservation = REQUESTED_res_code
AND current_date > charge_date;

-- remove room rate record for cancelled reservation
DELETE FROM room_rate_history
WHERE reservation = REQUESTED_res_code
AND current_date > charge_date;

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

--
-- DEREK LOOK - UPDATED
--
-- calculate total charge for additional charges
SELECT SUM(cost * quantity) AS additional_charges_total
FROM additional_charges_invoices
INNER JOIN additional_charges ON charge = charge_code
WHERE reservation = REQUESTED_res_code
AND current_date >= charge_date;

-- list additional charges given a reservation
SELECT *
FROM additional_charges_invoices
INNER JOIN additional_charges ON charge = charge_code
WHERE reservation = REQUESTED_res_code
-- AND current_date >= charge_date;

-- list room charges
SELECT *
FROM room_rate_history
WHERE reservation = REQUESTED_res_code
-- AND current_date >= res_date;

-- combined total charge
SELECT SUM(total)
FROM
   (SELECT *
   FROM
      (SELECT SUM(rate) AS total
      FROM room_rate_history
      WHERE reservation = REQUESTED_res_code )AS room_total
    --   AND current_date >= res_date) AS room_total
   UNION
   SELECT *
   FROM
      (SELECT SUM(cost * quantity) AS total
      FROM additional_charges_invoices
      INNER JOIN additional_charges
      ON charge = charge_code
      WHERE reservation = REQUESTED_res_code )AS additional_total
    --   AND current_date >= charge_date) AS additional_total

   ) AS combined_total;

-- list all available rooms given today's date
-- shows special room rate if applicable
SELECT (room_code / 100) AS floor, (room_code - ((room_code/100) * 100)) AS room_number, ocean_view, bed_type, base_price, rate AS special_rate
FROM rooms
LEFT JOIN special_room_rates ON (room = room_code) AND (current_date = book_date)
WHERE room_code NOT IN
   (SELECT room
   FROM reservations
   WHERE current_date BETWEEN check_in AND check_out)
ORDER BY room_code;

-- calculate total room charge before reservation is made given date
--    will be called in a loop at the java level to get total cost
SELECT room_code, ocean_view, bed_type, base_price, rate
FROM rooms
LEFT JOIN special_room_rates ON (room = room_code) AND (REQUESTED_date = book_date)
WHERE room_code = REQUESTED_room;

-- given (check in, check out, view, bed type) list available rooms, price, and special price if there is one
SELECT *
FROM rooms
INNER JOIN special_room_rates ON room_code = room
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

-- use this to add admin attribute to employee table and set an employee as an admin
ALTER TABLE employees
ADD COLUMN admin BOOL;

UPDATE employees
SET admin = 'true'
WHERE emp_id = 11111;
