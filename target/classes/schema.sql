CREATE DATABASE IF NOT EXISTS course_system;
USE course_system;

-- Users Table
CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL, -- 'STUDENT', 'TEACHER'
    full_name VARCHAR(100) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Courses Table
CREATE TABLE IF NOT EXISTS courses (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    code VARCHAR(20) NOT NULL UNIQUE,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    credits INT NOT NULL,
    capacity INT NOT NULL,
    teacher_id BIGINT NOT NULL,
    day_of_week VARCHAR(10), -- e.g., 'MONDAY'
    start_time TIME,
    end_time TIME,
    FOREIGN KEY (teacher_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Course Prerequisites Table (Many-to-Many self-reference)
CREATE TABLE IF NOT EXISTS course_prerequisites (
    course_id BIGINT NOT NULL,
    prerequisite_id BIGINT NOT NULL,
    PRIMARY KEY (course_id, prerequisite_id),
    FOREIGN KEY (course_id) REFERENCES courses(id) ON DELETE CASCADE,
    FOREIGN KEY (prerequisite_id) REFERENCES courses(id) ON DELETE CASCADE
);

-- Enrollments Table
CREATE TABLE IF NOT EXISTS enrollments (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    student_id BIGINT NOT NULL,
    course_id BIGINT NOT NULL,
    enrollment_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    grade VARCHAR(2), -- e.g., 'A', 'B', etc. (Optional)
    FOREIGN KEY (student_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (course_id) REFERENCES courses(id) ON DELETE CASCADE,
    UNIQUE (student_id, course_id) -- Prevent duplicate enrollment
);
