package com.example.bfhl.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class RequestDto {

    @NotNull
    private List<String> data;

}