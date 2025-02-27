CREATE TABLE chat_rooms(
  chat_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  room VARCHAR(36) UNIQUE NOT NULL,
  dep_id INT NOT NULL,
  c_id INT NOT NULL,
  t_id VARCHAR(36) NOT NULL,
  s_id VARCHAR(36) NOT NULL
);

CREATE INDEX idx_chat_rooms_room ON chat_rooms(room);

CREATE TYPE message_type AS ENUM ('TEXT', 'ATTACHMENT');

CREATE TABLE chat_messages(
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
