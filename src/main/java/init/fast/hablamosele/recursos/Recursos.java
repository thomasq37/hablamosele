package init.fast.hablamosele.recursos;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
@Data
public class Recursos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition = "LONGTEXT")
    private String banner;
    private String titulo;
    private String description;
    private String tags;
    @ElementCollection
    @CollectionTable(
            name = "recursos_infografias",
            joinColumns = @JoinColumn(name = "recursos_id")
    )
    @Column(name = "infografia", columnDefinition = "LONGTEXT")
    private List<String> infografias;
}

