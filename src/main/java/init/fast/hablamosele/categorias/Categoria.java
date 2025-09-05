package init.fast.hablamosele.categorias;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
@Data
@Entity
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
}

