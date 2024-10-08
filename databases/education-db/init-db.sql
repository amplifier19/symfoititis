CREATE TABLE IF NOT EXISTS courses (
  c_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  dep_id INT NOT NULL,
  semester INT CHECK (semester >= 0 AND semester <= 99 ) NOT NULL,
  c_display_name VARCHAR (64) NOT NULL
);

CREATE TYPE note_type_enum AS ENUM ('theory', 'lab');

CREATE TABLE IF NOT EXISTS notes (
  note_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  c_id INT NOT NULL,
  type note_type_enum NOT NULL,
  note_display_name VARCHAR(64) NOT NULL,
  note_filename VARCHAR(128)
);
