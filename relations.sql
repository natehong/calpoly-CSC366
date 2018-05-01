--Subject to change as program requirements change or become more defined

-- assumes that customers must have/make an account before being able to make a reservation
-- no need to mask payment info in the java layer...assuming employees will not steal
CREATE TABLE customers (
   login VARCHAR(25) NOT NULL,
   password VARCHAR(50) NOT NULL,
   first_name VARCHAR(25) NOT NULL,
   last_name VARCHAR(25) NOT NULL,
   email VARCHAR(50),
   street_number INT CHECK (street_number > 0),
   street VARCHAR(50),
   city VARCHAR(50),
   zip_code INT CHECK (zip_code > 0),
   credit_card BIGINT CHECK (credit_card > 0),  -- only one credit card can be held on an individual account at a time
   exp_date DATE NOT NULL,
   crc_code INT CHECK (crc_code > 0),
   PRIMARY KEY (login)
);

-- simple list of employees
CREATE TABLE employees (
   emp_id INT CHECK (emp_id > 0) NOT NULL,
   password VARCHAR(25) NOT NULL,
   first_name VARCHAR(25) NOT NULL,
   last_name VARCHAR(25) NOT NULL,
   PRIMARY KEY (emp_id)
);

CREATE TABLE rooms (
   room_code INT CHECK (room_code >= 101) NOT NULL, -- 'xyz' - x = floor, yz = room
   ocean_view BOOLEAN NOT NULL,  -- ocean view = true / pool view = false
   bed_type VARCHAR(15) NOT NULL,   -- twin queen or single queen
   base_price NUMERIC(5,2) NOT NULL,   -- java program will look here when no special prices are indicated
   PRIMARY KEY (room_code)
);

-- for add ons like wifi, breakfast, etc
CREATE TABLE additional_charges (
   charge_code INT NOT NULL,
   description VARCHAR(50) NOT NULL,
   cost NUMERIC(5,2) NOT NULL,
   PRIMARY KEY (charge_code)
);

-- contains room prices for certain dates that differ from the base price
-- prices will be room + date specific
-- will be the first place to look for room price
CREATE TABLE special_room_rates (
   special_rate_code INT NOT NULL,
   room INT CHECK (room >= 101) NOT NULL,
   book_date DATE NOT NULL,
   rate NUMERIC(5,2) CHECK (rate > 0) NOT NULL,
   PRIMARY KEY (special_rate_code),
   FOREIGN KEY (room) REFERENCES rooms (room_code)
);

-- used to store and check which rooms are occupied on certain dates
CREATE TABLE reservations (
   res_code INT NOT NULL,
   check_in DATE NOT NULL,
   check_out DATE NOT NULL,
   room INT NOT NULL,
   customer VARCHAR(25) NOT NULL,
   PRIMARY KEY (res_code),
   FOREIGN KEY (room) REFERENCES rooms (room_code),
   FOREIGN KEY (customer) REFERENCES customers (login)
);

-- keeps track of any additional charges added to a particular reservation
-- multiple charges can be made throughout the duration of a reservation
CREATE TABLE additional_charges_invoices (
   invoice_code INT CHECK (invoice_code > 0) NOT NULL,
   reservation INT NOT NULL,
   charge_date DATE NOT NULL, -- date the extra charge was added
   charge INT NOT NULL, -- what type of charge was added
   quantity INT CHECK (quantity > 0) NOT NULL,  -- when multiple identical charges made to a reservation on the same day
   PRIMARY KEY (invoice_code),
   FOREIGN KEY (reservation) REFERENCES reservations (res_code),
   FOREIGN KEY (charge) REFERENCES additional_charges (charge_code)
);

-- this will keep track of when a reservation is made, what was the room charge for each day
-- ensures that current prices are recored/locked-in in case changes are made/added in "special_room_rates"
-- allows for correctly charging when customer checks out early
CREATE TABLE room_rate_history (
   rate_id INT CHECK (rate_id > 0) NOT NULL,
   reservation INT CHECK (reservation > 0) NOT NULL,  -- for a specific reservation made
   res_date DATE NOT NULL, -- specific date in reservation duration
   rate NUMERIC(5,2) NOT NULL,   -- the rate at time of reservation
   PRIMARY KEY (rate_id),
   FOREIGN KEY (reservation) REFERENCES reservations (res_code)
);
