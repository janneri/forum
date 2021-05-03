package com.example.forum.exception;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY) // with the latest Jackson this is not needed
public record ErrorResponse(String error, String message) {
}
