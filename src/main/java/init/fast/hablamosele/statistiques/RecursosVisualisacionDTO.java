package init.fast.hablamosele.statistiques;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RecursosVisualisacionDTO {
    private Long id;
    private LocalDateTime dateVisualisacion;
    private String ip;
    private Long recursosId;
    private String recursosTitulo;
}
