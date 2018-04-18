--Subject to change as program requirements change or become more defined

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
   credit_card BIGINT CHECK (credit_card > 0),
   exp_date DATE,
   crc_code INT CHECK (crc_code > 0),
   PRIMARY KEY (login)
);

CREATE TABLE employees (
   emp_id INT CHECK (emp_id > 0) NOT NULL,
   first_name VARCHAR(25) NOT NULL,
   last_name VARCHAR(25) NOT NULL,
   PRIMARY KEY (emp_id)
);

CREATE TABLE rooms (
   room_code INT CHECK (room_code > 101) NOT NULL, -- 'xyz' - x = floor, yz = room
   view BOOLEAN NOT NULL,
   bed_type VARCHAR(15) NOT NULL,
   base_price NUMERIC(5,2) NOT NULL,
   PRIMARY KEY (room_code)
);

CREATE TABLE additional_charges (
   charge_code INT NOT NULL,
   description VARCHAR(50) NOT NULL,
   cost NUMERIC(5,2) NOT NULL,
   PRIMARY KEY (charge_code)
);

CREATE TABLE special_room_charges (
   special_charge_code INT NOT NULL,
   room INT CHECK (room_code > 101) NOT NULL,
   res_date DATE NOT NULL,
   cost NUMERIC(5,2) CHECK (cost > 0) NOT NULL,
   PRIMARY KEY (special_charge_code),
   FOREIGN KEY (room) REFERENCES rooms (room_code)
);

CREATE TABLE reservations (
   res_code INT NOT NULL,
   check_in DATE NOT NULL,
   check_out DATE NOT NULL,
   room INT NOT NULL,
   customer VARCHAR(25) NOT NULL,
   PRIMARY KEY (res_code),
   FOREIGN KEY (room) REFERENCES rooms (room_code),
   FOREIGN KEY(customer) REFERENCES customers (login)
);

CREATE TABLE additional_charges_invoices (
   invoice_code INT CHECK (invoice_code > 0) NOT NULL,
   reservation INT NOT NULL,
   charge_date DATE NOT NULL,
   charge INT NOT NULL,
   quantity INT CHECK (quantity > 0) NOT NULL,
   PRIMARY KEY (invoice_code),
   FOREIGN KEY (reservation) REFERENCES reservations (res_code),
   FOREIGN KEY (charge) REFERENCES extra_charges (charge_code)
);

CREATE TABLE room_charge_history (
   room_charge_id INT CHECK (room_charge_id > 0) NOT NULL,
   reservation INT CHECK (reservation > 0) NOT NULL,
   res_date DATE NOT NULL,
   room_cost NUMERIC(5,2) NOT NULL,
   PRIMARY KEY (room_charge_id),
   FOREIGN KEY (reservation) REFERENCES reservations (res_code)
);
