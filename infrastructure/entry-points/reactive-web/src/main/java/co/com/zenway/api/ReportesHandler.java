package co.com.zenway.api;

import co.com.zenway.api.dto.ReporteResponse;
import co.com.zenway.usecase.ReporteUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ReportesHandler {

    private final ReporteUseCase reporteUseCase;

    public Mono<ServerResponse> obtenerReportes(ServerRequest serverRequest) {
        return serverRequest.principal()
                .cast(JwtAuthenticationToken.class)
                .flatMap(auth ->
                        Mono.zip(
                                reporteUseCase.obtenerTotalSolicitudesAprobadas(),
                                reporteUseCase.obtenerTotalMonto()
                        ).flatMap(tuple -> {
                            var response = new ReporteResponse(tuple.getT1(), tuple.getT2());
                            return ServerResponse.ok().bodyValue(response);
                        }));
    }

}
