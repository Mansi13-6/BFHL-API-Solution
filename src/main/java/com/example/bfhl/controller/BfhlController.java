package com.example.bfhl.controller;

import com.example.bfhl.dto.RequestDto;
import com.example.bfhl.dto.ResponseDto;
import com.example.bfhl.service.BfhlService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class BfhlController {

    private final BfhlService service;

    @PostMapping("/bfhl")
    public ResponseEntity<ResponseDto> process(
            @Valid @RequestBody RequestDto request,
            @RequestHeader("X-Request-Id") String requestId
    ) {

        return ResponseEntity.ok(
                service.process(
                        request.getData(),
                        requestId
                )
        );
    }

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("UP");
    }
}