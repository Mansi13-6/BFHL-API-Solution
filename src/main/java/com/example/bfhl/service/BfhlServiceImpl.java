package com.example.bfhl.service;

import com.example.bfhl.dto.ResponseDto;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Pattern;

@Service
public class BfhlServiceImpl implements BfhlService {

    private static final Pattern NUMBER_PATTERN =
            Pattern.compile("^-?\\d+(\\.\\d+)?$");

    private static final Pattern ALPHA_PATTERN =
            Pattern.compile("^[a-zA-Z]+$");

    private String formatNumber(Double value) {

        if (value == null) {
            return null;
        }

        if (value % 1 == 0) {
            return String.valueOf(value.longValue());
        }

        return String.valueOf(value);
    }

    private String formatNumber(double value) {

        if (value % 1 == 0) {
            return String.valueOf((long) value);
        }

        return String.valueOf(value);
    }

    @Override
    public ResponseDto process(
            List<String> data,
            String requestId
    ) {

        long start = System.currentTimeMillis();

        Set<String> unique = new LinkedHashSet<>();
        boolean containsDuplicates = false;
        int invalidCount = 0;

        for (String value : data) {

            if (value == null || value.trim().isEmpty()) {
                invalidCount++;
                continue;
            }

            if (!unique.add(value)) {
                containsDuplicates = true;
            }
        }

        List<String> oddNumbers = new ArrayList<>();
        List<String> evenNumbers = new ArrayList<>();
        List<String> alphabets = new ArrayList<>();
        List<String> specialChars = new ArrayList<>();
        List<Double> numbers = new ArrayList<>();

        Map<String, Integer> frequency = new HashMap<>();

        int vowelCount = 0;

        String longest = "";
        String shortest = null;

        for (String value : unique) {

            if (NUMBER_PATTERN.matcher(value).matches()) {

                double number = Double.parseDouble(value);

                numbers.add(number);

                if (number % 1 == 0) {

                    long n = (long) number;

                    if (n % 2 == 0) {
                        evenNumbers.add(formatNumber(number));
                    } else {
                        oddNumbers.add(formatNumber(number));
                    }
                }
            }

            else if (ALPHA_PATTERN.matcher(value).matches()) {

                String upper = value.toUpperCase();

                alphabets.add(upper);

                if (upper.length() > longest.length()) {
                    longest = upper;
                }

                if (shortest == null ||
                        upper.length() < shortest.length()) {
                    shortest = upper;
                }

                for (char ch : upper.toCharArray()) {

                    String c = String.valueOf(ch);

                    frequency.put(
                            c,
                            frequency.getOrDefault(c, 0) + 1
                    );

                    if ("AEIOU".contains(c)) {
                        vowelCount++;
                    }
                }
            }
            else {

                StringBuilder alpha = new StringBuilder();
                StringBuilder numericPart = new StringBuilder();

                boolean specialFound = false;

                for (char ch : value.toCharArray()) {

                    if (Character.isLetter(ch)) {

                        alpha.append(ch);

                        String c =
                                String.valueOf(
                                        Character.toUpperCase(ch)
                                );

                        alphabets.add(c);

                        frequency.put(
                                c,
                                frequency.getOrDefault(c, 0) + 1
                        );

                        if ("AEIOU".contains(c)) {
                            vowelCount++;
                        }
                    }

                    else if (Character.isDigit(ch)) {

                        numericPart.append(ch);
                    }

                    else {
                        specialFound = true;
                    }
                }

                if (numericPart.length() > 0) {

                    numbers.add(
                            Double.parseDouble(
                                    numericPart.toString()
                            )
                    );
                }

                if (alpha.length() > 0) {

                    String a = alpha.toString()
                            .toUpperCase();

                    if (a.length() > longest.length()) {
                        longest = a;
                    }

                    if (shortest == null ||
                            a.length() < shortest.length()) {
                        shortest = a;
                    }
                }

                if (specialFound) {
                    specialChars.add(value);
                }
            }
        }

        double sum = numbers.stream()
                .mapToDouble(Double::doubleValue)
                .sum();

        Double largest = numbers.stream()
                .max(Double::compareTo)
                .orElse(0.0);

        Double smallest = numbers.stream()
                .min(Double::compareTo)
                .orElse(0.0);

        List<String> sortedNumbers = numbers.stream()
                .sorted()
                .map(this::formatNumber)
                .toList();

        Map<String, Object> summary = new HashMap<>();

        summary.put(
                "total_elements_received",
                data.size()
        );

        summary.put(
                "valid_elements_processed",
                unique.size()
        );

        summary.put(
                "invalid_elements_ignored",
                invalidCount
        );

        long processingTime =
                System.currentTimeMillis() - start;

        return ResponseDto.builder()
                .is_success(true)
                .request_id(requestId)
                .odd_numbers(oddNumbers)
                .even_numbers(evenNumbers)
                .alphabets(alphabets)
                .special_characters(specialChars)
                .sum(formatNumber(sum))
                .largest_number(formatNumber(largest))
                .smallest_number(formatNumber(smallest))
                .alphabet_count(alphabets.size())
                .number_count(numbers.size())
                .special_character_count(specialChars.size())
                .contains_duplicates(containsDuplicates)
                .processing_time_ms(processingTime)
                .unique_element_count(unique.size())
                .sorted_numbers(sortedNumbers)
                .vowel_count(vowelCount)
                .alphabet_frequency(frequency)
                .longest_alphabetic_value(longest)
                .shortest_alphabetic_value(shortest)
                .summary(summary)
                .build();
    }
}