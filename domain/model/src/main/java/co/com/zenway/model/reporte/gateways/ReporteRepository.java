package co.com.zenway.model.reporte.gateways;

import co.com.zenway.model.reporte.Reporte;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface ReporteRepository {
    Mono<Reporte> save(Reporte reporte);
    Mono<Reporte> getByIdNumber(Long idSolicitud);
    Flux<Reporte> getAll();


}
