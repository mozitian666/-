package com.coursesystem.service;

import com.coursesystem.entity.Course;
import com.coursesystem.entity.User;
import com.coursesystem.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public List<Course> getCoursesByTeacher(User teacher) {
        return courseRepository.findByTeacher(teacher);
    }

    public Course getCourseById(Long id) {
        return courseRepository.findById(id).orElseThrow(() -> new RuntimeException("Course not found"));
    }

    @Transactional
    public Course createCourse(Course course) {
        if (courseRepository.existsByCode(course.getCode())) {
            throw new RuntimeException("Course code already exists");
        }
        return courseRepository.save(course);
    }

    @Transactional
    public void deleteCourse(Long id, User teacher) {
        Course course = getCourseById(id);
        if (!course.getTeacher().getId().equals(teacher.getId())) {
            throw new RuntimeException("You are not authorized to delete this course");
        }
        courseRepository.delete(course);
    }
}
