package co.com.zenway.sqs.listener;

import co.com.zenway.model.sqs.dto.SolicitudAprobadaEvent;
import co.com.zenway.usecase.ReporteUseCase;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.services.sqs.model.Message;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
@Log4j2
public class SQSProcessor implements Function<Message, Mono<Void>> {
    private final ReporteUseCase reporteUseCase;
    private final ObjectMapper objectMapper;

    @Override
    public Mono<Void> apply(Message message) {
        try {
            // 1. Deserializar mensaje
            SolicitudAprobadaEvent event =
                    objectMapper.readValue(message.body(), SolicitudAprobadaEvent.class);

            log.info("Procesando evento de solicitud aprobada: {}", event);

            // 2. Llamar caso de uso
            return reporteUseCase
                    .procesarSolicitudAprobada(
                            event.getIdSolicitud(),
                            event.getFechaAprobacion(),
                            event.getMonto()
                    )
                    .doOnSuccess(r -> log.info("Reporte actualizado en Dynamo: {}", r))
                    .then();

        } catch (Exception e) {
            log.error("Error procesando mensaje SQS", e);
            // Importante: retornar vacío para no bloquear el flujo
            return Mono.empty();
        }
    }
}
