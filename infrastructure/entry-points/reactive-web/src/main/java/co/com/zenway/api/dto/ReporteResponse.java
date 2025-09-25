package co.com.zenway.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReporteResponse {
    private Long totalSolicitudesAprobadas;
    private BigDecimal montoTotal;
}
