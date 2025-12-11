# Course Selection System Development Plan

Based on your requirements, I will develop a full-stack Course Selection System using Java Spring Boot, MySQL, and native HTML/CSS/JS.

## 1. System Architecture & Database Design
### 1.1 Database Schema (MySQL)
I will design a normalized database structure including:
*   **Users Table**: Stores students and teachers with role differentiation and encrypted passwords.
*   **Courses Table**: Stores course details (ID, name, credits, capacity, schedule info).
*   **Prerequisites Table**: Manages course dependencies (Many-to-Many).
*   **Enrollments Table**: Tracks student course selections and grades.

### 1.2 Backend Tech Stack
*   **Framework**: Spring Boot 3.x
*   **Data Access**: Spring Data JPA (Hibernate)
*   **Security**: Spring Security (BCrypt for passwords, Session-based auth)
*   **Build Tool**: Maven

### 1.3 Frontend Architecture
*   **Structure**: Multi-page application (Login, Teacher Dashboard, Student Dashboard).
*   **Tech**: HTML5, CSS3, Vanilla JavaScript (Fetch API for backend communication).

## 2. Implementation Steps

### Phase 1: Project Initialization & Database
1.  Initialize Spring Boot project structure.
2.  Create `schema.sql` for database table creation.
3.  Configure `application.properties` for MySQL connection.

### Phase 2: Backend Core Modules
1.  **User Module**: Implement `User` entity, Repository, Service (Login/Register), and Controller.
2.  **Course Module**: Implement `Course` entity, CRUD operations for Teachers.
3.  **Enrollment Module**: Implement logic for Students to select/drop courses.
    *   **Validation Logic**: Implement checks for Prerequisites, Credit Limit (30 max), and Time Conflicts.

### Phase 3: Frontend Development
1.  **Common**: Create shared CSS and API utility (JS) for handling REST calls.
2.  **Auth Page**: Login and Registration forms (`index.html`).
3.  **Teacher Portal**: UI for adding/viewing courses and students.
4.  **Student Portal**: UI for browsing courses, enrolling, and viewing schedule.

### Phase 4: Testing & Refinement
1.  Implement Global Exception Handling (return JSON errors).
2.  Write Unit Tests for critical business logic (e.g., prerequisite checks).
3.  Verify all requirements against the provided specification.

### Phase 5: Documentation
1.  Generate API Documentation (Markdown).
2.  Create Deployment Guide and User Manual.

## 3. Key Business Rules to Implement
*   **Prerequisites**: Recursive check to ensure all prior courses are completed.
*   **Credit Cap**: Verify total credits <= 30 before allowing enrollment.
*   **Time Conflict**: Check if new course time overlaps with existing enrollments.

Does this plan meet your expectations? If confirmed, I will begin by setting up the project structure and database schema.