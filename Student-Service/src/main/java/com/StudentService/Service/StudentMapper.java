package com.StudentService.Service;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.StudentService.Model.Student;
import com.StudentService.Model.StudentProfileDto;

@Mapper(componentModel = "spring")
public interface StudentMapper {

    StudentMapper INSTANCE = Mappers.getMapper(StudentMapper.class);

    StudentProfileDto studentToStudentProfileDto(Student student);

}