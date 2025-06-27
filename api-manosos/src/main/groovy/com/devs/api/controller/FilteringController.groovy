package com.devs.api.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException
import org.springframework.http.HttpStatus
import groovy.util.logging.Slf4j

@Slf4j
@RestController

class FilteringController {

  private static final List<Map<String, String>> greetingsList = [
    [language: 'English', greeting: 'Hello'],
    [language: 'Español', greeting: 'Hola'],
    [language: 'Frances', greeting: 'Bonjour'],
    [language: 'Aleman', greeting: 'Hallo'],
    [language: 'Italiano', greeting: 'Ciao'],
    [language: 'Portuguese', greeting: 'Olá'],
    [language: 'Japones', greeting: 'こんにちは'],
    [language: 'Chino', greeting: '你好']
  ]

  @GetMapping("greetings")
  List<Map<String, String>> getGreetings(@RequestParam(value = "language", required = false) String language) {
    if (!language) {
      return greetingsList
    }

    if (language.equalsIgnoreCase("500")) {
      throw new RuntimeException("Prueba de error 500")
    }
    if (language.equalsIgnoreCase("401")) {
      throw new ResponseStatusException(
        HttpStatus.UNAUTHORIZED,
        "No estás autorizado"
      )
    }
    if (language.equalsIgnoreCase("403")) {
      throw new ResponseStatusException(
        HttpStatus.FORBIDDEN,
        "Acceso denegado"
      )
    }

    List<String> selectLanguage = greetingsList.findAll {it.language.equalsIgnoreCase(language)}
    if (!selectLanguage) {
      throw new ResponseStatusException(
        HttpStatus.NOT_FOUND,
        "Idioma '${language}' no encontrado"
      )
    }
    selectLanguage
  }

}