package init.fast.hablamosele.statistiques;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "recursos_id")
    private Recursos recursos;
}
