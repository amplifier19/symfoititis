CREATE TYPE slot_status_enum AS ENUM ('AVAILABLE', 'UNAVAILABLE', 'BOOKED');

CREATE TABLE availability_slots(
  av_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  dep_id INT NOT NULL,
  c_id INT NOT NULL,
  t_id VARCHAR(36) NOT NULL,
  date DATE NOT NULL,
  week_day INT CHECK (week_day >= 0 AND week_day <= 6) NOT NULL,
  start_time INT CHECK (start_time >= 0 AND start_time <= 23) NOT NULL,
  state slot_status_enum DEFAULT 'AVAILABLE' NOT NULL,
  UNIQUE (t_id, date, start_time)
);

CREATE TABLE availability_events (
  e_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  av_id INT NOT NULL,
  start_time INT CHECK (start_time >= 0 AND start_time <= 23) NOT NULL,
  state slot_status_enum NOT NULL,
  created_at TIMESTAMP(3) DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (av_id) REFERENCES availability_slots(av_id) ON DELETE CASCADE
);

-- Before insert on availability_slots, check if the slot exists at this date as unavailable.
CREATE OR REPLACE FUNCTION before_insert_on_availability_slots()
RETURNS TRIGGER AS $$
BEGIN
  PERFORM 1 FROM availability_slots
  WHERE t_id = NEW.t_id AND date = NEW.date AND start_time = NEW.start_time AND state = 'UNAVAILABLE';
  IF FOUND THEN
    UPDATE availability_slots
    SET state = 'AVAILABLE' 
    WHERE t_id = NEW.t_id AND date = NEW.date AND start_time = NEW.start_time AND state = 'UNAVAILABLE';
    RETURN NULL;
  END IF;
  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER before_insert_on_availability_slots_trigger
BEFORE INSERT ON availability_slots
FOR EACH ROW
EXECUTE FUNCTION before_insert_on_availability_slots();

-- After insert on availability_slots, insert new event.
CREATE OR REPLACE FUNCTION after_insert_on_availability_slots()
RETURNS TRIGGER AS $$
BEGIN
  INSERT INTO availability_events(av_id, start_time, state)
  VALUES (NEW.av_id, NEW.start_time, 'AVAILABLE');
  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER after_insert_on_availability_slots_trigger
AFTER INSERT ON availability_slots
FOR EACH ROW
EXECUTE FUNCTION after_insert_on_availability_slots();

-- Before update on availability_slots, check state on start_time or state change
CREATE OR REPLACE FUNCTION before_update_on_availability_slots()
RETURNS TRIGGER AS $$
BEGIN
  IF NEW.state = 'UNAVAILABLE' AND OLD.state = 'BOOKED' THEN
    RAISE EXCEPTION 'Slot at %:00 cannot be removed because it has been booked', OLD.start_time;
  END IF;
  IF NEW.start_time <> OLD.start_time AND OLD.state = 'BOOKED' THEN
    RAISE EXCEPTION 'Start time cannot be updated to %:00 because it has been booked at %:00', NEW.start_time, OLD.start_time;
  END IF;
  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER before_update_on_availability_slots_trigger
BEFORE UPDATE ON availability_slots
FOR EACH ROW
EXECUTE FUNCTION before_update_on_availability_slots();

-- After update on availability_slots, insert new event.
CREATE OR REPLACE FUNCTION after_update_on_availability_slots()
RETURNS TRIGGER AS $$
BEGIN
  IF NEW.state != OLD.state OR NEW.start_time != OLD.start_time THEN
    INSERT INTO availability_events(av_id, start_time, state)
    VALUES (NEW.av_id, NEW.start_time, NEW.state);
  END IF;
  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER after_update_on_availability_slots_trigger
AFTER UPDATE ON availability_slots
FOR EACH ROW
EXECUTE FUNCTION after_update_on_availability_slots();


CREATE TYPE booking_status_enum AS ENUM ('ACTIVE', 'CANCELED');

CREATE TABLE bookings (
  b_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  av_id INT NOT NULL,
  s_id VARCHAR(36) NOT NULL,
  room VARCHAR(36) NOT NULL,
  state booking_status_enum DEFAULT 'ACTIVE' NOT NULL,
  FOREIGN KEY (av_id) REFERENCES availability_slots(av_id) ON DELETE CASCADE
);
CREATE INDEX idx_bookings_av_id ON bookings(av_id);

CREATE TABLE booking_events (
  e_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  b_id INT NOT NULL,
  state booking_status_enum NOT NULL,
  created_at TIMESTAMP(3) DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (b_id) REFERENCES bookings(b_id) ON DELETE CASCADE
);

-- Before insert at bookings check availability for this slot, then update availability_slot state to booked
CREATE OR REPLACE FUNCTION before_insert_on_bookings()
RETURNS TRIGGER AS $$ 
BEGIN
  UPDATE availability_slots
  SET state = 'BOOKED'
  WHERE av_id = NEW.av_id AND state = 'AVAILABLE';
  IF NOT FOUND THEN
    RAISE EXCEPTION 'The slot at %:00 may have been already booked', NEW.start_time;
  END IF;
  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER before_insert_on_bookings_trigger
BEFORE INSERT ON bookings
FOR EACH ROW
EXECUTE FUNCTION before_insert_on_bookings();

-- After insert at bookings, insert new event
CREATE OR REPLACE FUNCTION after_insert_on_bookings()
RETURNS TRIGGER AS $$ 
BEGIN
  INSERT INTO booking_events(b_id, state)
  VALUES (NEW.b_id, 'ACTIVE');
  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER after_insert_on_bookings_trigger
AFTER INSERT ON bookings
FOR EACH ROW
EXECUTE FUNCTION after_insert_on_bookings();

-- After state update at bookings, mark corresponding availability slot as availalble and insert new event
CREATE OR REPLACE FUNCTION after_update_on_bookings()
RETURNS TRIGGER AS $$
BEGIN
  IF NEW.state = 'CANCELED' THEN
    UPDATE availability_slots
    SET state = 'AVAILABLE'
    WHERE av_id = NEW.av_id;
  END IF;
  IF NEW.state != OLD.state THEN
    INSERT INTO booking_events(b_id, state)
    VALUES (NEW.b_id, NEW.state);
  END IF;
  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER after_update_on_bookings_trigger
AFTER UPDATE ON bookings
FOR EACH ROW
EXECUTE FUNCTION after_update_on_bookings();

CREATE TABLE purchase_products (
  id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  price INT NOT NULL CHECK (price > 0),
  anchor_price INT CHECK (anchor_price > 0),
  message TEXT,
  hours INT NOT NULL CHECK (hours > 0),
  weight INT NOT NULL DEFAULT 0,
  increment_balance_weight BOOLEAN NOT NULL DEFAULT false
);

CREATE TABLE student_balance (
  id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  student_id VARCHAR(36) NOT NULL,
  hours INT NOT NULL DEFAULT 0,
  weight INT NOT NULL DEFAULT 0
);
CREATE INDEX idx_student_balance_student_id ON student_balance(student_id);
