package com.coursesystem.controller;

import com.coursesystem.entity.Course;
import com.coursesystem.entity.User;
import com.coursesystem.dto.CourseCreateRequest;
import com.coursesystem.service.CourseService;
import com.coursesystem.service.EnrollmentService;
import com.coursesystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.HashSet;
import java.util.Set;
import java.time.LocalTime;

@RestController
@RequestMapping("/api/teacher")
public class TeacherController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private UserService userService;

    @Autowired
    private EnrollmentService enrollmentService;

    private User getCurrentTeacher() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userService.findByUsername(auth.getName());
    }

    @GetMapping("/courses")
    public ResponseEntity<List<Course>> getMyCourses() {
        return ResponseEntity.ok(courseService.getCoursesByTeacher(getCurrentTeacher()));
    }

    @GetMapping("/all-courses")
    public ResponseEntity<List<Course>> getAllCoursesForTeacher() {
        return ResponseEntity.ok(courseService.getAllCourses());
    }

    @GetMapping("/courses/{id}/students")
    public ResponseEntity<?> getCourseStudents(@PathVariable Long id) {
        try {
            // Validate ownership
            Course course = courseService.getCourseById(id);
            if (!course.getTeacher().getId().equals(getCurrentTeacher().getId())) {
                 return ResponseEntity.status(403).body("Not authorized");
            }
            return ResponseEntity.ok(enrollmentService.getCourseEnrollments(course));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/courses")
    public ResponseEntity<?> createCourse(@RequestBody CourseCreateRequest request) {
        try {
            Course course = new Course();
            course.setCode(request.getCode());
            course.setName(request.getName());
            course.setDescription(request.getDescription());
            course.setCredits(request.getCredits());
            course.setCapacity(request.getCapacity());
            course.setTeacher(getCurrentTeacher());
            course.setDayOfWeek(request.getDayOfWeek());
            if (request.getStartTime() != null) {
                course.setStartTime(LocalTime.parse(request.getStartTime()));
            }
            if (request.getEndTime() != null) {
                course.setEndTime(LocalTime.parse(request.getEndTime()));
            }

            if (request.getPrerequisiteIds() != null && !request.getPrerequisiteIds().isEmpty()) {
                Set<Course> prereqs = new HashSet<>();
                for (Long preId : request.getPrerequisiteIds()) {
                    Course pre = courseService.getCourseById(preId);
                    prereqs.add(pre);
                }
                course.setPrerequisites(prereqs);
            }

            return ResponseEntity.ok(courseService.createCourse(course));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/courses/{id}")
    public ResponseEntity<?> deleteCourse(@PathVariable Long id) {
        try {
            courseService.deleteCourse(id, getCurrentTeacher());
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
