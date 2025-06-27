package com.devs.api.configurations

import org.springframework.context.annotation.Configuration
import io.swagger.v3.oas.models.servers.Server
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info


@Configuration
class OpenAPIConfig {

  @Value('${springdoc.url.server}')
  private String urlServer

  @Bean
  OpenAPI OpenAPIConfig() {

    Info info = new Info()
      .title("Mañosos API")
      .version("1.0")
      .description("This API responses.")

    Server server = new Server()
    server.setUrl(urlServer)
    server.setDescription("Server URL in Development environment")

    return new OpenAPI().info(info).servers(List.of(server))
  }
}