package com.example.formbuilder.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException::class)
    fun handleResourceNotFound(exception: ResourceNotFoundException): ResponseEntity<String> {
        return ResponseEntity(exception.message, HttpStatus.NOT_FOUND)
    }

    // You can add more exception handlers here if desired
}
