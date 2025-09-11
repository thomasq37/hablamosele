package init.fast.hablamosele.recursos;


import init.fast.hablamosele.categorias.Categoria;
import init.fast.hablamosele.niveles.Nivel;
import lombok.Data;

import java.util.List;


@Data
public class RecursosDTO {
    private Long id;
    private String banner;
    private String titulo;
    private String description;
    private String tags;
    private List<Categoria> categorias;
    private List<Nivel> niveles;
    private Integer nbInfografias;
    private Integer nbCahiersActivite;
}
