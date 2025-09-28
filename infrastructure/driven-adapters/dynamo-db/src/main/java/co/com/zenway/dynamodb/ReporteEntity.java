package co.com.zenway.dynamodb;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.*;

import java.math.BigDecimal;

/* Enhanced DynamoDB annotations are incompatible with Lombok #1932
         https://github.com/aws/aws-sdk-java-v2/issues/1932*/
@DynamoDbBean
public class ReporteEntity {

    private Long idSolicitud;
    private String fechaAprobacion;
    private BigDecimal monto;
    private Long solicitudesAprobadas;

    public ReporteEntity() {
    }

    public ReporteEntity(Long idSolicitud, String fechaAprobacion, BigDecimal monto, Long solicitudesAprobadas) {
        this.idSolicitud = idSolicitud;
        this.fechaAprobacion = fechaAprobacion;
        this.monto = monto;
        this.solicitudesAprobadas = solicitudesAprobadas;
    }

    @DynamoDbPartitionKey
    @DynamoDbAttribute("id_solicitud")
    public Long getIdSolicitud() {
        return idSolicitud;
    }

    public void setIdSolicitud(Long idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    public String getFechaAprobacion() {
        return fechaAprobacion;
    }

    public void setFechaAprobacion(String fechaAprobacion) {
        this.fechaAprobacion = fechaAprobacion;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public Long getSolicitudesAprobadas() {
        return solicitudesAprobadas;
    }

    public void setSolicitudesAprobadas(Long solicitudesAprobadas) {
        this.solicitudesAprobadas = solicitudesAprobadas;
    }
}
