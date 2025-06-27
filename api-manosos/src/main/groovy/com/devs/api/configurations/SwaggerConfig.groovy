package com.devs.api.configurations

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info
import org.springframework.context.annotation.Configuration

@OpenAPIDefinition(
  info = @Info(
    title = 'Mi API',
    version = 'v1',
    description = 'Documentación OpenAPI con Springdoc'
  )
)
@Configuration
class SwaggerConfig { }