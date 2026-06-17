package com.example.bfhl.service;

import com.example.bfhl.dto.ResponseDto;

import java.util.List;

public interface BfhlService {

    ResponseDto process(
            List<String> data,
            String requestId
    );
}