package co.com.zenway.usecase;

import co.com.zenway.model.reporte.Reporte;
import co.com.zenway.model.reporte.gateways.ReporteRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@RequiredArgsConstructor
public class ReporteUseCase {

    private final ReporteRepository repository;

    public Mono<Reporte> procesarSolicitudAprobada(Long idSolicitud, String fecha, BigDecimal monto) {
        return repository.getByIdNumber(idSolicitud)
                .defaultIfEmpty(new Reporte(idSolicitud,  monto, fecha, 0L))
                .flatMap(r -> {
                    r.setSolicitudesAprobadas(r.getSolicitudesAprobadas() + 1);
                    r.setMonto(r.getMonto().add(monto));
                    return repository.save(r);
                });
    }

    public Mono<Long> obtenerTotalSolicitudesAprobadas() {
        return repository.getAll()
                .map(Reporte::getSolicitudesAprobadas)
                .reduce(0L, Long::sum);
    }

    public Mono<BigDecimal> obtenerTotalMonto() {
        return repository.getAll()
                .map(Reporte::getMonto)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }


}
