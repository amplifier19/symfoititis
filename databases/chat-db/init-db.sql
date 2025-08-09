CREATE TABLE IF NOT EXISTS chat_rooms(
  chat_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  room VARCHAR(36) UNIQUE NOT NULL,
  dep_id INT NOT NULL,
  c_id INT NOT NULL,
  t_id VARCHAR(36) NOT NULL,
  s_id VARCHAR(36) NOT NULL
);

CREATE INDEX idx_chat_rooms ON chat_rooms(room);

CREATE TYPE message_type AS ENUM ('TEXT', 'ATTACHMENT');

CREATE TABLE IF NOT EXISTS chat_messages(
  message_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  room VARCHAR(36) NOT NULL,
  dep_id INT NOT NULL,
  c_id INT NOT NULL,
  is_teacher BOOLEAN NOT NULL,
  sender_id VARCHAR(36) NOT NULL,
  recipient_id VARCHAR(36) NOT NULL,
  type message_type NOT NULL,
  content TEXT,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (room) REFERENCES chat_rooms(room) ON DELETE CASCADE
);

CREATE INDEX idx_chat_messages_room ON chat_messages(room);

CREATE TABLE IF NOT EXISTS unread_messages(
  room VARCHAR(36) NOT NULL,
  participant_id VARCHAR(36) NOT NULL,
  unread_count INT NOT NULL DEFAULT 0,
  last_read_message_id INT NOT NULL DEFAULT 0, 
  PRIMARY KEY (room, participant_id)
);

CREATE OR REPLACE FUNCTION after_insert_on_chat_rooms()
RETURNS TRIGGER AS $$
BEGIN
  INSERT INTO unread_messages(room, participant_id, unread_count, last_read_message_id)
  VALUES 
    (NEW.room, NEW.t_id, 0, 0),
    (NEW.room, NEW.s_id, 0, 0)
  ON CONFLICT DO NOTHING;
  RETURN NEW;
END
$$ LANGUAGE plpgsql;

CREATE TRIGGER after_insert_on_chat_rooms
AFTER INSERT ON chat_rooms
FOR EACH ROW
EXECUTE FUNCTION after_insert_on_chat_rooms();

CREATE OR REPLACE FUNCTION enforce_monotonic_read_progress()
RETURNS TRIGGER AS $$
BEGIN
  IF NEW.last_read_message_id < OLD.last_read_message_id THEN
    NEW.last_read_message_id := OLD.last_read_message_id;
  END IF;
  IF NEW.unread_count < 0 THEN
    NEW.unread_count := 0;
  END IF;
  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER before_update_on_unread_messages
BEFORE UPDATE ON unread_messages
FOR EACH ROW
EXECUTE FUNCTION enforce_monotonic_read_progress();

