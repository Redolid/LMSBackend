package com.example.LMS.service;

import com.example.LMS.dto.CourseRequestDTO;
import com.example.LMS.dto.LessonDTO;
import com.example.LMS.entity.Course;
import com.example.LMS.entity.Lesson;
import com.example.LMS.entity.User;
import com.example.LMS.repository.CourseRepository;
import com.example.LMS.repository.LessonRepository;
import com.example.LMS.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.Set;

import java.util.List;


@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private UserRepository userRepository;

    // --- Feature 1: Course Creation ---
    public Course addCourse(CourseRequestDTO courseRequestDTO, Long instructorId) {
        User instructor = userRepository.findById(instructorId)
                .orElseThrow(() -> new RuntimeException("Instructor not found!"));

        if (!instructor.getRole().equals("INSTRUCTOR")) {
            throw new RuntimeException("Only instructors can create courses!");
        }

        Course course = new Course();
        course.setTitle(courseRequestDTO.getTitle());
        course.setDescription(courseRequestDTO.getDescription());
        course.setInstructor(instructor);
        course.setDurationInHours(courseRequestDTO.getDurationInHours());

        return courseRepository.save(course);
    }

    public Course addMediaToCourse(Long courseId, MultipartFile mediaFile) {
        Course course = getCourseById(courseId);

        // Logic to upload media to cloud storage or file system
        String mediaUrl = "uploaded_media_url"; // Example placeholder
        course.getMediaUrls().add(mediaUrl);

        return courseRepository.save(course);
    }

    public Lesson addLesson(Long courseId, LessonDTO lessonDTO) {
        Course course = getCourseById(courseId);

        Lesson lesson = new Lesson();
        lesson.setTitle(lessonDTO.getTitle());
        lesson.setDescription(lessonDTO.getDescription());
        lesson.setDurationInMinutes(lessonDTO.getDurationInMinutes());
        lesson.setCourse(course);

        return lessonRepository.save(lesson);
    }

    // --- Feature 2: Enrollment Management ---
    public List<Course> viewAvailableCourses() {
        return courseRepository.findAll(); // Available to Students
    }

    public void enrollStudent(Long courseId, Long studentId) {
        Course course = getCourseById(courseId);
        User student = userRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found!"));

        if (!student.getRole().equals("STUDENT")) {
            throw new RuntimeException("Only students can enroll in courses!");
        }

        course.getEnrolledStudents().add(student);
        courseRepository.save(course);
    }

    public Set<User> viewEnrolledStudents(Long courseId, Long userId) { //List was returning error as the get returns set
        Course course = getCourseById(courseId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found!"));

        if (!user.getRole().equals("ADMIN") && !course.getInstructor().getId().equals(userId)) {
            throw new RuntimeException("Only admins or the course instructor can view enrolled students!");
        }

        return course.getEnrolledStudents();
    }

    // --- Feature 3: Attendance Management ---
    public String generateLessonOtp(Long lessonId, Long instructorId) {
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new RuntimeException("Lesson not found!"));

        if (!lesson.getCourse().getInstructor().getId().equals(instructorId)) {
            throw new RuntimeException("Only the instructor can generate OTPs for the lesson!");
        }

        String otp = String.valueOf((int) (Math.random() * 9000) + 1000); // Generate 4-digit OTP
        lesson.setOtp(otp);
        lessonRepository.save(lesson);

        return otp;
    }

    public void markAttendance(Long lessonId, Long studentId, String otp) {
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new RuntimeException("Lesson not found!"));

        User student = userRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found!"));

        if (!lesson.getOtp().equals(otp)) {
            throw new RuntimeException("Invalid OTP provided!");
        }

        lesson.getAttendees().add(student);
        lessonRepository.save(lesson);
    }

    // --- Existing Methods (Refactored to Use Helpers) ---
    public Course getCourseById(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found!"));
    }
    
    public Course updateCourse(Long id, CourseRequestDTO courseRequestDTO) {
        Course existingCourse = getCourseById(id);
        existingCourse.setTitle(courseRequestDTO.getTitle());
        existingCourse.setDescription(courseRequestDTO.getDescription());
        existingCourse.setInstructorName(courseRequestDTO.getInstructorName());
        existingCourse.setDurationInHours(courseRequestDTO.getDurationInHours());

        return courseRepository.save(existingCourse);
    }

    public void deleteCourse(Long id) {
        courseRepository.deleteById(id);
    }
    
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }
}
