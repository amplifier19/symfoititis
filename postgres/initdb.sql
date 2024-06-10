-- Permissions
GRANT SELECT ON TABLE public.universities, public.departments, public.courses, public.notes TO student;
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE public.universities, public.departments, public.courses, public.notes TO admin;

DROP TABLE IF EXISTS notes;
DROP TABLE IF EXISTS courses;
DROP TABLE IF EXISTS departments;
DROP TABLE IF EXISTS universities;

-- Universities
CREATE TABLE IF NOT EXISTS universities (
   uni_id INT GENERATED ALWAYS AS IDENTITY
   (START WITH 1 INCREMENT BY 1),
   uni_display_name VARCHAR(64) NOT NULL,
   uni_alt_name VARCHAR(32) NOT NULL,
   UNIQUE(uni_alt_name),
   PRIMARY KEY(uni_id)
);
INSERT INTO universities (uni_display_name, uni_alt_name) VALUES
    ('Πανεπιστήμιο Δυττικής Αττικής', 'uniwa'),
    ('Οικονομικό Πανεπιστήμιο Αθηνών', 'aueb');


-- Departments
CREATE TABLE IF NOT EXISTS departments (
    dep_id INT GENERATED ALWAYS AS IDENTITY
    (START WITH 1 INCREMENT BY 1),
    uni_id INT NOT NULL,
    dep_display_name VARCHAR(64) NOT NULL,
    dep_alt_name VARCHAR(32) NOT NULL,
    PRIMARY KEY(dep_id),
    CONSTRAINT fk_university
        FOREIGN KEY(uni_id)
            REFERENCES universities(uni_id)
            ON DELETE CASCADE
);
INSERT INTO departments (uni_id, dep_display_name, dep_alt_name) VALUES
    (1, 'Μηχανικών Πληροφορικής και Υπολογιστών', 'ice'),
    (2, 'Πληροφορικής', 'infotech'),
    (2, 'Διοικητικής Επιστήμης και Τεχνολογίας', 'dmst'),
    (2, 'Λογιστικής και Χρηματοοικονομικής', 'accfin');


-- Courses
CREATE TABLE IF NOT EXISTS courses (
    c_id INT GENERATED ALWAYS AS IDENTITY
    (START WITH 1 INCREMENT BY 1),
    dep_id INT NOT NULL,
    semester INT NOT NULL,
    c_display_name VARCHAR (64) NOT NULL,
    description TEXT,
    PRIMARY KEY (c_id),
    CONSTRAINT fk_department
        FOREIGN KEY (dep_id)
        REFERENCES departments (dep_id)
        ON DELETE CASCADE
);
INSERT INTO courses (dep_id, semester, c_display_name) VALUES
    (1, 2, 'Σχεδιάση και Ανάλυση Αλγορίθμων'),
    (1, 3, 'Λειτουργικά Συστήματα Ι'),
    (1, 4, 'Σήματα και Συστήματα'),
    (1, 4, 'Δίκτυα ΙΙ');


-- Notes
CREATE TABLE IF NOT EXISTS notes (
    note_id INT GENERATED ALWAYS AS IDENTITY
    (START WITH 1 INCREMENT BY 1),
    c_id INT NOT NULL,
    type VARCHAR(32) NOT NULL,
    note_display_name VARCHAR(64) NOT NULL,
    note_filename VARCHAR(64),
    PRIMARY KEY (note_id),
    CONSTRAINT fk_course
        FOREIGN KEY (c_id)
        REFERENCES courses (c_id)
        ON DELETE CASCADE
);
INSERT INTO notes (c_id, type, note_display_name) VALUES
    (1, 'theory', 'Complexity-1'),
    (1, 'theory', 'Complexity-2');
