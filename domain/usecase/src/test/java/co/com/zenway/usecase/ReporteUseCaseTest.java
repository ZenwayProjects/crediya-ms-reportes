package co.com.zenway.usecase;

import co.com.zenway.model.reporte.Reporte;
import co.com.zenway.model.reporte.gateways.ReporteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ReporteUseCaseTest {

        private ReporteRepository repository;
        private ReporteUseCase useCase;

        @BeforeEach
        void setUp() {
            repository = mock(ReporteRepository.class);
            useCase = new ReporteUseCase(repository);
        }

        @Test
        void procesarSolicitudAprobadaDebeCrearNuevoReporteCuandoNoExiste() {
            Long idSolicitud = 1L;
            String fecha = "2025-09-25";
            BigDecimal monto = BigDecimal.valueOf(1000);

            when(repository.getByIdNumber(idSolicitud)).thenReturn(Mono.empty());
            when(repository.save(any())).thenAnswer(invocation -> Mono.just(invocation.getArgument(0)));

            StepVerifier.create(useCase.procesarSolicitudAprobada(idSolicitud, fecha, monto))
                    .assertNext(reporte -> {
                        assertThat(reporte.getIdSolicitud()).isEqualTo(idSolicitud);
                        assertThat(reporte.getFechaAprobacion()).isEqualTo(fecha);
                        assertThat(reporte.getSolicitudesAprobadas()).isEqualTo(1L);
                        assertThat(reporte.getMonto()).isEqualByComparingTo(monto);
                    })
                    .verifyComplete();

            verify(repository).save(any(Reporte.class));
        }

        @Test
        void procesarSolicitudAprobadaDebeActualizarReporteExistente() {
            Long idSolicitud = 2L;
            String fecha = "2025-09-25";
            BigDecimal monto = BigDecimal.valueOf(500);

            Reporte existente = new Reporte(idSolicitud, BigDecimal.valueOf(1000), fecha, 2L);

            when(repository.getByIdNumber(idSolicitud)).thenReturn(Mono.just(existente));
            when(repository.save(any())).thenAnswer(invocation -> Mono.just(invocation.getArgument(0)));

            StepVerifier.create(useCase.procesarSolicitudAprobada(idSolicitud, fecha, monto))
                    .assertNext(reporte -> {
                        assertThat(reporte.getSolicitudesAprobadas()).isEqualTo(3L);
                        assertThat(reporte.getMonto()).isEqualByComparingTo("1500");
                    })
                    .verifyComplete();

            verify(repository).save(any(Reporte.class));
        }

        @Test
        void obtenerTotalSolicitudesAprobadasDebeSumarCorrectamente() {
            Reporte r1 = new Reporte(1L, BigDecimal.valueOf(1000), "2025-09-25", 2L);
            Reporte r2 = new Reporte(2L, BigDecimal.valueOf(2000), "2025-09-25", 3L);

            when(repository.getAll()).thenReturn(Flux.just(r1, r2));

            StepVerifier.create(useCase.obtenerTotalSolicitudesAprobadas())
                    .expectNext(5L)
                    .verifyComplete();
        }

        @Test
        void obtenerTotalMontoDebeSumarCorrectamente() {
            Reporte r1 = new Reporte(1L, BigDecimal.valueOf(1000), "2025-09-25", 2L);
            Reporte r2 = new Reporte(2L, BigDecimal.valueOf(2000), "2025-09-25", 3L);

            when(repository.getAll()).thenReturn(Flux.just(r1, r2));

            StepVerifier.create(useCase.obtenerTotalMonto())
                    .expectNext(BigDecimal.valueOf(3000))
                    .verifyComplete();
        }
}
