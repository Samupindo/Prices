package com.develop.prices.modelo;



import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// Este es el manejador global de excepciones para toda la aplicación.
@RestControllerAdvice
public class GlobalExceptionHandler {

    // Maneja las excepciones HttpMessageNotReadableException, que ocurren cuando hay un problema al deserializar el cuerpo del mensaje.
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        // Mensaje de error que se enviará en la respuesta
        String errorMessage = "Fields misentered";

        // Se devuelve una respuesta con el código de estado 400 (Bad Request) y el mensaje de error
        return new ResponseEntity<>(new ErrorResponse(errorMessage), HttpStatus.BAD_REQUEST);
    }

    // Clase interna que representa la estructura de la respuesta de error
    public static class ErrorResponse {
        private String message;

        public ErrorResponse(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}

