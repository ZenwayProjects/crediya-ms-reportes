package co.com.zenway.api.docs;


import co.com.zenway.api.ReportesHandler;
import co.com.zenway.api.dto.ReporteResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springdoc.core.annotations.RouterOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class ReporteRouterDoc {

    @Bean
    @RouterOperation(
            path = "/api/v1/reportes",
            produces = {"application/json; charset=UTF-8"},
            method = RequestMethod.GET,
            beanClass = ReportesHandler.class,
            beanMethod = "obtenerReportes",
            operation = @Operation(
                    operationId = "obtenerReportes",
                    summary = "Obtener reporte general de solicitudes",
                    description = "Devuelve un resumen con el total de solicitudes aprobadas y el monto total aprobado.",
                    responses = {
                            @ApiResponse(
                                    responseCode = "200",
                                    description = "Reporte generado exitosamente",
                                    content = @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = ReporteResponse.class)
                                    )
                            ),
                            @ApiResponse(responseCode = "401", description = "Usuario no autenticado"),
                            @ApiResponse(responseCode = "403", description = "Acceso denegado"),
                            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
                    }
            )
    )
    public RouterFunction<ServerResponse> obtenerReporte(ReportesHandler reportesHandler) {
        return route(GET("/api/v1/reportes"), reportesHandler::obtenerReportes);
    }




}
