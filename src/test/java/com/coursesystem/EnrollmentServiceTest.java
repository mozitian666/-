package com.coursesystem;

import com.coursesystem.entity.Course;
import com.coursesystem.entity.Enrollment;
import com.coursesystem.entity.User;
import com.coursesystem.repository.CourseRepository;
import com.coursesystem.repository.EnrollmentRepository;
import com.coursesystem.service.EnrollmentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class EnrollmentServiceTest {

    @Mock
    private EnrollmentRepository enrollmentRepository;

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private EnrollmentService enrollmentService;

    @Test
    public void testEnrollSuccess() {
        User student = new User();
        student.setId(1L);

        Course course = new Course();
        course.setId(2L);
        course.setCredits(3);
        course.setCapacity(30);
        // Time and prerequisites are null/empty by default, so no conflict

        when(courseRepository.findById(2L)).thenReturn(Optional.of(course));
        when(enrollmentRepository.existsByStudentAndCourse(student, course)).thenReturn(false);
        when(enrollmentRepository.findByCourse(course)).thenReturn(new ArrayList<>()); // 0 enrollments
        when(enrollmentRepository.findByStudent(student)).thenReturn(new ArrayList<>()); // 0 credits
        when(enrollmentRepository.save(any(Enrollment.class))).thenAnswer(i -> i.getArguments()[0]);

        Enrollment result = enrollmentService.enrollCourse(student, 2L);

        assertNotNull(result);
        verify(enrollmentRepository, times(1)).save(any(Enrollment.class));
    }

    @Test
    public void testEnrollCreditLimitExceeded() {
        User student = new User();
        student.setId(1L);

        Course newCourse = new Course();
        newCourse.setId(2L);
        newCourse.setCredits(4);
        newCourse.setCapacity(50); // Set capacity to avoid NPE

        // Mock existing enrollments (total 18 credits)
        List<Enrollment> existingEnrollments = new ArrayList<>();
        Course c1 = new Course(); c1.setCredits(10);
        Course c2 = new Course(); c2.setCredits(8);
        // Course c3 = new Course(); c3.setCredits(8); // Removed to lower total
        
        Enrollment e1 = new Enrollment(); e1.setCourse(c1);
        Enrollment e2 = new Enrollment(); e2.setCourse(c2);
        // Enrollment e3 = new Enrollment(); e3.setCourse(c3);
        
        existingEnrollments.add(e1);
        existingEnrollments.add(e2);
        // existingEnrollments.add(e3);

        when(courseRepository.findById(2L)).thenReturn(Optional.of(newCourse));
        when(enrollmentRepository.findByStudent(student)).thenReturn(existingEnrollments);
        
        // 18 + 4 = 22 > 21 -> Should throw exception
        assertThrows(RuntimeException.class, () -> {
            enrollmentService.enrollCourse(student, 2L);
        });
    }
}
