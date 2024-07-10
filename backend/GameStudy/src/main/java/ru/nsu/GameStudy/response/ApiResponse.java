package ru.nsu.GameStudy.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatusCode;

@Data
@AllArgsConstructor
public class ApiResponse {
    private boolean result;
    private String message;
}