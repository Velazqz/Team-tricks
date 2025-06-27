package com.devs.api.error

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.server.ResponseStatusException
import org.springframework.http.HttpStatus
import groovy.util.logging.Slf4j

@Slf4j
@ControllerAdvice
class GlobalExceptionHandler {

  @ExceptionHandler(ResponseStatusException)
  ResponseEntity<Map> handleResponseStatus(ResponseStatusException ex) {
    Map body = [
      code   : ex.body.status,
      message: ex.reason,
      meta   : [ errors: [ ex.reason ] ]
    ]
    return new ResponseEntity<>(body, ex.getStatusCode())
  }

  @ExceptionHandler(Exception)
  ResponseEntity<Map> handleGeneric(Exception ex) {
    log.error("Error interno", ex)
    Map body = [
      code   : 500,
      message: "Error al procesar la solicitud",
      meta   : [ errors: [ ex.message ?: 'Error interno desconocido' ] ]
    ]
    return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR)
  }
}
