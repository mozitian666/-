package com.coursesystem.controller;

import com.coursesystem.entity.Enrollment;
import com.coursesystem.entity.User;
import com.coursesystem.service.CourseService;
import com.coursesystem.service.EnrollmentService;
import com.coursesystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/student")
public class StudentController {

    @Autowired
    private EnrollmentService enrollmentService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private UserService userService;

    private User getCurrentStudent() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userService.findByUsername(auth.getName());
    }

    @GetMapping("/courses")
    public ResponseEntity<?> getAllCourses() {
        return ResponseEntity.ok(courseService.getAllCourses());
    }

    @GetMapping("/enrollments")
    public ResponseEntity<?> getMyEnrollments() {
        return ResponseEntity.ok(enrollmentService.getStudentEnrollments(getCurrentStudent()));
    }

    @PostMapping("/enroll/{courseId}")
    public ResponseEntity<?> enroll(@PathVariable Long courseId) {
        try {
            return ResponseEntity.ok(enrollmentService.enrollCourse(getCurrentStudent(), courseId));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/enroll/{courseId}")
    public ResponseEntity<?> drop(@PathVariable Long courseId) {
        try {
            enrollmentService.dropCourse(getCurrentStudent(), courseId);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
