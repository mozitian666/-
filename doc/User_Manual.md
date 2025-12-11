# Course Selection System

## Overview
A full-stack Course Selection System built with Java Spring Boot (Backend) and HTML/CSS/JS (Frontend).

## Prerequisites
*   Java 17 or higher
*   Maven 3.6+
*   MySQL 8.0+

## Configuration
1.  Create a MySQL database named `course_system` (or it will be created automatically).
2.  Update database credentials in `src/main/resources/application.properties`:
    ```properties
    spring.datasource.username=root
    spring.datasource.password=root
    ```

## Installation & Running
1.  Navigate to project root:
    ```bash
    cd c:\Users\86133\Desktop\CourseSystem
    ```
2.  Run with Maven:
    ```bash
    mvn spring-boot:run
    ```
3.  Access the application at `http://localhost:8080`.

## User Manual

### Roles
1.  **Teacher**: Manage courses.
2.  **Student**: Browse and enroll in courses.

### Getting Started
1.  **Registration**:
    *   Go to `http://localhost:8080`.
    *   Click "Register".
    *   Create a Teacher account (Role: TEACHER).
    *   Create a Student account (Role: STUDENT).

2.  **Teacher Workflow**:
    *   Login as Teacher.
    *   Add new courses with Schedule (Day/Time) and Credits.
    *   View enrolled students for each course.

3.  **Student Workflow**:
    *   Login as Student.
    *   Browse "Available Courses".
    *   Click "Enroll" (System checks for conflicts, prerequisites, and credit limits).
    *   View "My Schedule" to see enrolled courses.
    *   Click "Drop" to remove a course.

## API Documentation
Base URL: `/api`

### Auth
*   `POST /auth/register`: Register new user.
*   `POST /auth/login`: Form login.
*   `GET /auth/me`: Get current user info.

### Teacher
*   `GET /teacher/courses`: List my courses.
*   `POST /teacher/courses`: Create course.
*   `DELETE /teacher/courses/{id}`: Delete course.
*   `GET /teacher/courses/{id}/students`: List enrolled students.

### Student
*   `GET /student/courses`: List all courses.
*   `GET /student/enrollments`: List my enrollments.
*   `POST /student/enroll/{courseId}`: Enroll.
*   `DELETE /student/enroll/{courseId}`: Drop.

## Database Schema
See `src/main/resources/schema.sql` for DDL scripts.
