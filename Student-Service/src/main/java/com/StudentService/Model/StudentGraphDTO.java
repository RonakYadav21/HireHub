package com.StudentService.Model;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class StudentGraphDTO {

    private String month;
    private Long count;

}