package com.Admin_Service.Model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StudentGraphDTO {

    private String month;
    private Long count;

}