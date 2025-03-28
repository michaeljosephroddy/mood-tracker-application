-- Create the database
CREATE DATABASE IF NOT EXISTS mood_tracker_app;
USE mood_tracker_app;

-- Create the users table
CREATE TABLE IF NOT EXISTS users (
    user_id VARCHAR(36) PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL
);

-- Create the mood_entries table
CREATE TABLE IF NOT EXISTS mood_entries (
    mood_entry_id VARCHAR(36) PRIMARY KEY,
    user_id VARCHAR(36) NOT NULL,
    moods JSON NOT NULL,
    date DATETIME NOT NULL,
    description TEXT,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

-- Insert mock users
INSERT INTO users (user_id, username, email, password_hash) VALUES
('user-123', 'john_doe', 'john@example.com', 'hashed_password_123'),
('user-456', 'jane_smith', 'jane@example.com', 'hashed_password_456'),
('user-789', 'mike_jones', 'mike@example.com', 'hashed_password_789'),
('user-101', 'anna_brown', 'anna@example.com', 'hashed_password_101'),
('user-202', 'emma_white', 'emma@example.com', 'hashed_password_202');

-- Insert mock data
INSERT INTO mood_entries (mood_entry_id, user_id, moods, date, description) VALUES
('550e8400-e29b-41d4-a716-446655440000', 'user-123', '["Happy", "Excited"]', '2025-03-24 12:00:00', 'Feeling great today!'),
('550e8400-e29b-41d4-a716-446655440005', 'user-123', '["Motivated", "Energetic"]', '2025-03-23 12:00:00', 'Had a productive day.'),
('550e8400-e29b-41d4-a716-446655440006', 'user-123', '["Calm", "Peaceful"]', '2025-03-22 12:00:00', 'Enjoyed some meditation.'),

('550e8400-e29b-41d4-a716-446655440001', 'user-456', '["Sad", "Tired"]', '2025-03-23 12:00:00', 'Had a long day at work.'),
('550e8400-e29b-41d4-a716-446655440007', 'user-456', '["Frustrated", "Exhausted"]', '2025-03-22 12:00:00', 'Dealing with deadlines.'),
('550e8400-e29b-41d4-a716-446655440008', 'user-456', '["Hopeful", "Determined"]', '2025-03-21 12:00:00', 'Planning for a fresh start.'),

('550e8400-e29b-41d4-a716-446655440002', 'user-789', '["Angry", "Frustrated"]', '2025-03-22 12:00:00', 'Traffic was terrible today.'),
('550e8400-e29b-41d4-a716-446655440009', 'user-789', '["Relieved", "Content"]', '2025-03-21 12:00:00', 'Finally finished an important task.'),
('550e8400-e29b-41d4-a716-446655440010', 'user-789', '["Excited", "Happy"]', '2025-03-20 12:00:00', 'Looking forward to the weekend.'),

('550e8400-e29b-41d4-a716-446655440003', 'user-101', '["Calm", "Relaxed"]', '2025-03-21 12:00:00', 'Spent the day at the beach.'),
('550e8400-e29b-41d4-a716-446655440011', 'user-101', '["Inspired", "Creative"]', '2025-03-20 12:00:00', 'Worked on a new project idea.'),
('550e8400-e29b-41d4-a716-446655440012', 'user-101', '["Nostalgic", "Happy"]', '2025-03-19 12:00:00', 'Revisited old memories through photos.'),

('550e8400-e29b-41d4-a716-446655440004', 'user-202', '["Anxious", "Stressed"]', '2025-03-20 12:00:00', 'Big presentation coming up.'),
('550e8400-e29b-41d4-a716-446655440013', 'user-202', '["Confident", "Prepared"]', '2025-03-19 12:00:00', 'Presentation practice went well.'),
('550e8400-e29b-41d4-a716-446655440014', 'user-202', '["Excited", "Hopeful"]', '2025-03-18 12:00:00', 'Looking forward to a trip.');
