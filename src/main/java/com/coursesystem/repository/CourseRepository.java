package com.coursesystem.repository;

import com.coursesystem.entity.Course;
import com.coursesystem.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findByTeacher(User teacher);
    boolean existsByCode(String code);
    Optional<Course> findByCode(String code);
}
