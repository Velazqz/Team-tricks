package com.devs.api.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import groovy.util.logging.Slf4j

@Slf4j
@RestController

class GreetingsController {

  @GetMapping("greetings")
  String greetings(){
    "Greetings"
  }
}