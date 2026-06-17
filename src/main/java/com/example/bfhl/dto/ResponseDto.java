package com.example.bfhl.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@Builder
public class ResponseDto {

    private boolean is_success;

    private String request_id;

    private List<String> odd_numbers;

    private List<String> even_numbers;

    private List<String> alphabets;

    private List<String> special_characters;

    private String sum;

    private String largest_number;

    private String smallest_number;

    private int alphabet_count;

    private int number_count;

    private int special_character_count;

    private boolean contains_duplicates;

    private long processing_time_ms;

    private int unique_element_count;

    private List<String> sorted_numbers;

    private int vowel_count;

    private Map<String, Integer> alphabet_frequency;

    private String longest_alphabetic_value;

    private String shortest_alphabetic_value;

    private Map<String,Object> summary;
}