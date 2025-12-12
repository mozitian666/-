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
  - Login page: <img width="2422" height="1238" alt="signIn" src="https://github.com/user-attachments/assets/31346827-7bdf-4f16-b2d4-f2b8768a32b7" />

  - Register page: <img width="1396" height="1168" alt="register" src="https://github.com/user-attachments/assets/6526ad40-acb8-4600-80c6-5b06338e1925" />


## Teacher Portal
- Entry: after login, the app routes to `teacher.html` for Teacher accounts.
- Create course: fill Course Code, Name, Credits, Capacity, Day of Week, Start/End Time.
  - Prerequisites: in the "Prerequisites" panel, check existing courses to set them as prerequisites. Students must satisfy these before enrollment.
  - On success, a toast appears and the course shows under "My Courses".
- View enrolled students: click `View Students` on the course card to see a modal with name and username.
- Delete course: click `Delete`. Ownership and relations are checked and removed accordingly.
- Example screenshots:
  - Teacher dashboard: <img width="2106" height="1223" alt="teacher_dashborad" src="https://github.com/user-attachments/assets/2214980e-3dde-42b7-8aef-6d284dd204cf" />

  - Teacher course view: <img width="1887" height="1184" alt="teacherCourseView" src="https://github.com/user-attachments/assets/a9851cea-aba9-4421-b4fe-1bcd96f06aa9" />


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
  - Available courses: <img width="1931" height="1164" alt="available_courses" src="https://github.com/user-attachments/assets/bd25c9b0-f436-4f2e-9b05-95285fdfb395" />

  - Weekly schedule: <img width="2090" height="1138" alt="schedule" src="https://github.com/user-attachments/assets/4fc53a04-764c-4af9-89ae-970b69515934" />

  - Student enrolled courses: <img width="1978" height="1146" alt="studentEnrolled" src="https://github.com/user-attachments/assets/ed0d0a51-ab6a-4955-b6d9-489a14022305" />


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
