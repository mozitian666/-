# User Manual (Course Selection System)

## Overview
- A full-stack Course Selection System built with Java Spring Boot (backend), vanilla HTML/CSS/JavaScript (frontend), and MySQL.
- Supports Student and Teacher roles. Implements enrollment, drop, time-conflict checks, prerequisite validation, and a per-user credit cap (≤ 21 credits).

## Environment & Startup
- Requirements: Java 17, MySQL 8.0+
- Database setup: edit `src/main/resources/application.properties` to set `spring.datasource.username` and `spring.datasource.password`. Ensure the MySQL service is running and the `course_system` database is accessible.
- Start the app: double-click `run/start.bat`. The browser opens `http://localhost:8080` after a short delay.
- If you see "Failed to fetch": wait until the backend finishes starting (Tomcat on port 8080), then refresh. Also verify MySQL credentials and service.

## Accounts & Roles
- Default accounts are listed in `doc/Default_Users.txt`; default courses in `doc/Default_Courses.txt`.
- Self-registration: use the Register panel on the login page and choose a `Role` of `STUDENT` or `TEACHER`.

## Login & Registration
- Visit `http://localhost:8080/login.html`.
- After successful registration you will see "Registration successful! Please login." then sign in using the newly created account.
- Example screenshots:
  - Login page: `./doc/images/logIn.png`
  - Register page: `./doc/images/register.png`

## Teacher Portal
- Entry: after login, the app routes to `teacher.html` for Teacher accounts.
- Create course: fill Course Code, Name, Credits, Capacity, Day of Week, Start/End Time.
  - Prerequisites: in the "Prerequisites" panel, check existing courses to set them as prerequisites. Students must satisfy these before enrollment.
  - On success, a toast appears and the course shows under "My Courses".
- View enrolled students: click `View Students` on the course card to see a modal with name and username.
- Delete course: click `Delete`. Ownership and relations are checked and removed accordingly.
- Example screenshots:
  - Teacher dashboard: `.doc/images/teacher_dashboard.png`
  - Teacher course view: '.doc/images/teacherCourseView.png'

## Student Portal
- Entry: after login, the app routes to `student.html` for Student accounts.
- Credit progress: top bar shows current credits out of 21; color changes on approaching/exceeding the cap.
- Tabs:
  - `My Schedule`: weekly grid showing enrolled courses by `dayOfWeek` and `startTime/endTime`.
  - `Available Courses`: course listing with `Enroll` actions.
- Validation rules:
  - Prerequisites: if unmet, enrollment fails with `Prerequisite not met: <CODE>`.
  - Credit cap: if current + new credits > 21, fails with `Credit limit exceeded (Max 21)`.
  - Time conflict: same day with overlapping times fails and indicates the conflicting course.
- Drop: in `My Schedule`, click `Drop Course` on the course card.
- Example screenshots:
  - Student dashboard: `./doc/images/student_dashboard.png`
  - Available courses: `./doc/images/available_courses.png`
  - Weekly schedule: `.doc/images/schedule.png`
  - Student enrolled courses: `./doc/images/studentEnrolled.png`

## Common Issues & Troubleshooting
- Login/Registration "Failed to fetch":
  - Confirm `start.bat` console shows `Tomcat started on port 8080` and `Started CourseSystemApplication`.
  - Check MySQL service and credentials.
  - Ensure local firewall/security software is not blocking `localhost:8080`.
- Enrollment failures:
  - `Prerequisite not met`: enroll in all listed prerequisites first.
  - `Credit limit exceeded (Max 21)`: drop some courses or choose fewer credits.
  - Time conflict: choose a non-overlapping course/time.
- Course not showing on schedule:
  - The schedule displays only "enrolled" courses. First enroll via `Available Courses`, then check `My Schedule`.

## Paths & Assets
- Run script: `run/start.bat`
- Executable jar: `src/course-system.jar`
- Configuration: `src/main/resources/application.properties`
- Frontend pages: `src/main/resources/static/` (`login.html`, `teacher.html`, `student.html`)
- Default data: `doc/Default_Users.txt`, `doc/Default_Courses.txt`
