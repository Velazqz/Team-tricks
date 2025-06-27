package com.devs.api.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException
import org.springframework.http.HttpStatus
import groovy.util.logging.Slf4j

@Slf4j
@RestController
class OverfetchingController {

  private static final List<Map<String,Object>> users = [
    [id: 1, nombre: 'Omar Aparicio',    email: 'o.aparicio@example.com',   antiguedad: '4 años', telefono: '+123456789', puesto: 'El jefe de jefes señores'],
    [id: 2, nombre: 'Teresa Martinez',  email: 't.martinez@example.com',   antiguedad: '4 años',  telefono: '+987654321', puesto: 'La mera buena'],
    [id: 3, nombre: 'Valerie Velazquez', email: 'vg.velazquez@example.com',  antiguedad: '3 años', telefono: '+192837465', puesto: 'La que hace que chambea'],
    [id: 4, nombre: 'Raúl Cornejo',  email: 'r.cornejo@example.com',    antiguedad: 'Casi 3 años', telefono: '+564738291', puesto: 'El viejo mañoso'],
    [id: 5, nombre: 'Uziel Alonso',  email: 'uj.alonso@example.com',    antiguedad: '1 año', telefono: '+564738291', puesto: 'El creativo'],
    [id: 6, nombre: 'Fernando Chavez',  email: 'r.cornejo@example.com',    antiguedad: 'Meses', telefono: '+564738291', puesto: 'El que canta']
  ]

  @GetMapping("/users")
  List<Map<String,Object>> getUsers(@RequestParam(value="fields", required=false) String fieldsParam) {
    if (!fieldsParam) {
      return users
    }
    List<String> data = fieldsParam.split(',').collect { it.trim() }
    def validKeys = users*.keySet().flatten().unique() as Set
    List<String> invalid = data.findAll { !validKeys.contains(it) }
    if (invalid) {
      throw new ResponseStatusException(
        HttpStatus.BAD_REQUEST,
        "Campo(s) no válido(s): ${invalid.join(', ')}"
      )
    }
    return users.collect { user ->
      data.collectEntries { key ->
        [(key): user[key]]
      }
    }
  }
}
