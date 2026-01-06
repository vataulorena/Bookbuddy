package com.bookbuddy.bookbuddy.exception;

public record ApiError(
        int status,
        String message
) {
}
