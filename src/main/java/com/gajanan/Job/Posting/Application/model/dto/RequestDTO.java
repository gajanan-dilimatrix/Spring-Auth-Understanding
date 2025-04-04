package com.gajanan.Job.Posting.Application.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestDTO {

    @NotNull(message = "Title is mandatory")
    @NotBlank(message = "Title cannot be blank")
    private String title;

    @NotNull(message = "Company is mandatory")
    @NotBlank(message = "Company cannot be blank")
    private String company;

    @NotNull(message = "Description is mandatory")
    @NotBlank(message = "Description cannot be blank")
    private String description;

    @NotNull(message = "Location is mandatory")
    @NotBlank(message = "Location cannot be blank")
    private String location;

    private Double salary;
}
