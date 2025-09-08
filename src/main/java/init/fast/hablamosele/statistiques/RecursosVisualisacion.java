package init.fast.hablamosele.statistiques;

import init.fast.hablamosele.recursos.Recursos;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
public class RecursosVisualisacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime dateVisualisacion;
    private String ip;
    @ManyToOne
    @JoinColumn(name = "recursos_id")
    private Recursos recursos;
}
