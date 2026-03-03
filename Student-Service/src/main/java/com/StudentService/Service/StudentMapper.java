package com.StudentService.Service;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import com.StudentService.Model.Student;
import com.StudentService.Model.StudentProfileDto;

@Mapper(componentModel = "spring")
public interface StudentMapper {
    StudentProfileDto studentToStudentProfileDto(Student student);
    StudentProfileDto toDto(Student student);

    Student toEntity(StudentProfileDto dto);

    void updateStudentFromDto(StudentProfileDto dto, @MappingTarget Student student);
//	StudentProfileDto toDto(Student student);

}