package co.com.zenway.model.reporte;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reporte {
    private Long idSolicitud;
    private BigDecimal monto;
    private String fechaAprobacion;
    private Long solicitudesAprobadas;


}
