package init.fast.hablamosele.recursos;

import init.fast.hablamosele.categorias.Categoria;
import init.fast.hablamosele.niveles.Nivel;
import init.fast.hablamosele.statistiques.RecursosVisualisacion;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
@Data
@Entity
public class Recursos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Lob
    private String banner;
    private String titulo;
    private String description;
    private String tags;
    @ElementCollection
    @CollectionTable(
            name = "recursos_infografias",
            joinColumns = @JoinColumn(name = "recursos_id")
    )
    @Column(name = "infografia", columnDefinition = "TEXT")
    private List<String> infografias;

    @ManyToMany
    @JoinTable(
            name = "recursos_categorias",
            joinColumns = @JoinColumn(name = "recursos_id"),
            inverseJoinColumns = @JoinColumn(name = "categoria_id")
    )
    private List<Categoria> categorias;

    @ManyToMany
    @JoinTable(
            name = "recursos_niveles",
            joinColumns = @JoinColumn(name = "recursos_id"),
            inverseJoinColumns = @JoinColumn(name = "nivel_id")
    )
    private List<Nivel> niveles;
    @OneToMany(mappedBy = "recursos", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RecursosVisualisacion> recursosVisualisaciones;
}

