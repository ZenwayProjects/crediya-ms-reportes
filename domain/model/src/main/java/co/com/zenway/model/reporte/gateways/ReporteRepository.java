package co.com.zenway.model.reporte.gateways;

import co.com.zenway.model.reporte.Reporte;
import reactor.core.publisher.Mono;

public interface ReporteRepository {
    Mono<Reporte> save(Reporte reporte);
    Mono<Reporte> getByIdNumber(Long idSolicitud);
}
