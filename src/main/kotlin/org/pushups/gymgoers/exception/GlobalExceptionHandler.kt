package org.pushups.gymgoers.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler{

    @ExceptionHandler(ResourceNotFoundException::class)
    fun  handleNotFound(ex: ResourceNotFoundException): ResponseEntity<Map<String, String?>>{
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(mapOf("error" to ex.message))
    }

    @ExceptionHandler(ClassFullException::class)
    fun handleClassFull(ex: ClassFullException): ResponseEntity<Map<String, String?>> {
        return ResponseEntity.status(HttpStatus.CONFLICT)
            .body(mapOf("error" to ex.message))
    }
}