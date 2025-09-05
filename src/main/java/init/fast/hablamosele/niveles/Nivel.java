package init.fast.hablamosele.niveles;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
@Data
@Entity
public class Nivel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
}

