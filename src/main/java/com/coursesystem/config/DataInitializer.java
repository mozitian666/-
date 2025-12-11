package com.coursesystem.config;

import com.coursesystem.entity.Course;
import com.coursesystem.entity.User;
import com.coursesystem.repository.CourseRepository;
import com.coursesystem.repository.UserRepository;
import com.coursesystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Override
    public void run(String... args) throws Exception {
        // Initialize Users
        if (userRepository.count() == 0) {
            // Teachers
            User teacher1 = new User();
            teacher1.setUsername("teacher_john");
            teacher1.setPassword("password123");
            teacher1.setFullName("John Doe");
            teacher1.setRole(User.Role.TEACHER);
            userService.registerUser(teacher1);

            User teacher2 = new User();
            teacher2.setUsername("teacher_jane");
            teacher2.setPassword("password123");
            teacher2.setFullName("Jane Smith");
            teacher2.setRole(User.Role.TEACHER);
            userService.registerUser(teacher2);

            // Students
            User student1 = new User();
            student1.setUsername("student_alice");
            student1.setPassword("password123");
            student1.setFullName("Alice Brown");
            student1.setRole(User.Role.STUDENT);
            userService.registerUser(student1);

            User student2 = new User();
            student2.setUsername("student_bob");
            student2.setPassword("password123");
            student2.setFullName("Bob Wilson");
            student2.setRole(User.Role.STUDENT);
            userService.registerUser(student2);
            
            System.out.println("Default users created.");
        }

        // Initialize Courses
        if (courseRepository.count() == 0) {
            User teacher1 = userRepository.findByUsername("teacher_john").orElseThrow();
            User teacher2 = userRepository.findByUsername("teacher_jane").orElseThrow();

            createCourse("CS101", "Intro to Computer Science", 4, 30, teacher1, "MONDAY", "09:00", "11:00");
            
            // CS102 requires CS101
            Course cs102 = createCourse("CS102", "Data Structures", 4, 30, teacher1, "WEDNESDAY", "09:00", "11:00");
            Course cs101 = courseRepository.findByCode("CS101").orElseThrow();
            cs102.getPrerequisites().add(cs101);
            courseRepository.save(cs102);

            createCourse("MATH101", "Calculus I", 4, 40, teacher2, "TUESDAY", "10:00", "12:00");
            createCourse("ENG101", "English Literature", 2, 50, teacher2, "FRIDAY", "14:00", "16:00");
            
            // PHY101 requires MATH101
            Course phy101 = createCourse("PHY101", "Physics I", 4, 30, teacher1, "THURSDAY", "13:00", "15:00");
            Course math101 = courseRepository.findByCode("MATH101").orElseThrow();
            phy101.getPrerequisites().add(math101);
            courseRepository.save(phy101);
            
            System.out.println("Default courses created.");
        }
    }

    private Course createCourse(String code, String name, int credits, int capacity, User teacher, String day, String start, String end) {
        Course course = new Course();
        course.setCode(code);
        course.setName(name);
        course.setDescription(name + " Description");
        course.setCredits(credits);
        course.setCapacity(capacity);
        course.setTeacher(teacher);
        course.setDayOfWeek(day);
        course.setStartTime(LocalTime.parse(start));
        course.setEndTime(LocalTime.parse(end));
        return courseRepository.save(course);
    }
}
