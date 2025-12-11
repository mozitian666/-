package com.coursesystem.service;

import com.coursesystem.entity.Course;
import com.coursesystem.entity.Enrollment;
import com.coursesystem.entity.User;
import com.coursesystem.repository.CourseRepository;
import com.coursesystem.repository.EnrollmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
public class EnrollmentService {

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private CourseRepository courseRepository;

    public List<Enrollment> getStudentEnrollments(User student) {
        return enrollmentRepository.findByStudent(student);
    }

    public List<Enrollment> getCourseEnrollments(Course course) {
        return enrollmentRepository.findByCourse(course);
    }

    @Transactional
    public Enrollment enrollCourse(User student, Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        // 1. Check if already enrolled
        if (enrollmentRepository.existsByStudentAndCourse(student, course)) {
            throw new RuntimeException("Already enrolled in this course");
        }

        // 2. Check Capacity
        // Note: In a real high-concurrency app, this needs locking.
        List<Enrollment> courseEnrollments = enrollmentRepository.findByCourse(course);
        if (courseEnrollments.size() >= course.getCapacity()) {
            throw new RuntimeException("Course is full");
        }

        List<Enrollment> studentEnrollments = enrollmentRepository.findByStudent(student);

        // 3. Check Credit Limit (Max 21)
        int currentCredits = studentEnrollments.stream()
                .mapToInt(e -> e.getCourse().getCredits())
                .sum();
        if (currentCredits + course.getCredits() > 21) {
            throw new RuntimeException("Credit limit exceeded (Max 21)");
        }

        // 4. Check Prerequisites
        Set<Course> prerequisites = course.getPrerequisites();
        for (Course preReq : prerequisites) {
            boolean hasTaken = studentEnrollments.stream()
                    .anyMatch(e -> e.getCourse().getId().equals(preReq.getId()));
            // In a real system, we would check if the course was passed in a previous semester.
            // Here, we assume if it's in enrollments, it's satisfied (simplified).
            // Or better: we can't really check "passed" without a history.
            // Let's assume for this project: You must have the pre-req in your records.
            if (!hasTaken) {
                 throw new RuntimeException("Prerequisite not met: " + preReq.getCode());
            }
        }

        // 5. Check Time Conflict
        for (Enrollment existing : studentEnrollments) {
            Course existingCourse = existing.getCourse();
            if (isTimeConflict(course, existingCourse)) {
                throw new RuntimeException("Time conflict with course: " + existingCourse.getCode());
            }
        }

        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setCourse(course);
        return enrollmentRepository.save(enrollment);
    }

    @Transactional
    public void dropCourse(User student, Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));
        
        Enrollment enrollment = enrollmentRepository.findByStudentAndCourse(student, course)
                .orElseThrow(() -> new RuntimeException("Not enrolled in this course"));
        
        enrollmentRepository.delete(enrollment);
    }

    private boolean isTimeConflict(Course c1, Course c2) {
        if (c1.getDayOfWeek() == null || c2.getDayOfWeek() == null) return false;
        if (!c1.getDayOfWeek().equalsIgnoreCase(c2.getDayOfWeek())) return false;
        
        // Check overlap
        // (Start1 < End2) and (Start2 < End1)
        return c1.getStartTime().isBefore(c2.getEndTime()) && c2.getStartTime().isBefore(c1.getEndTime());
    }
}
