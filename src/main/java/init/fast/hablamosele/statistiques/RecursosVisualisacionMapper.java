package init.fast.hablamosele.statistiques;
import java.util.List;
import java.util.stream.Collectors;

public final class RecursosVisualisacionMapper {

    private RecursosVisualisacionMapper() {}

    public static RecursosVisualisacionDTO toDTO(RecursosVisualisacion r) {
        if (r == null) {
            return null;
        }

        RecursosVisualisacionDTO dto = new RecursosVisualisacionDTO();
        dto.setId(r.getId());
        dto.setDateVisualisacion(r.getDateVisualisacion());
        dto.setIp(r.getIp());
        dto.setRecursosId(r.getRecursos().getId());
        dto.setRecursosTitulo(r.getRecursos().getTitulo());

        return dto;
    }

    public static List<RecursosVisualisacionDTO> toDTOList(List<RecursosVisualisacion> recursos) {
        if (recursos == null) {
            return null;
        }
        return recursos.stream()
                .map(RecursosVisualisacionMapper::toDTO)
                .collect(Collectors.toList());
    }
}