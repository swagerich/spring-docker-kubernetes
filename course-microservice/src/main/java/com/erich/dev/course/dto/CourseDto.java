package com.erich.dev.course.dto;


import com.erich.dev.course.entity.Course;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
@Builder
public class CourseDto {

    private Long id;

    @NotEmpty
    private String name;

    public static CourseDto fromEntity(Course course) {
        if (course == null) {
            log.error("Coure vino null");
            return null;
        }
        return CourseDto.builder()
                .id(course.getId())
                .name(course.getName())
                .build();
    }

    public static Course toEntity(CourseDto courseDto) {
        if (courseDto == null) {
            log.error("Coure vino null");
            return null;
        }
        return Course.builder()
                .id(courseDto.getId())
                .name(courseDto.getName())
                .build();
    }
}
