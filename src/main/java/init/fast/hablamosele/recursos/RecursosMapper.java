package init.fast.hablamosele.recursos;

import java.util.List;
import java.util.stream.Collectors;

public final class RecursosMapper {

    private RecursosMapper() {}

    public static RecursosStatistiquesDTO toStatDTO(Recursos r) {
        RecursosStatistiquesDTO dto = new RecursosStatistiquesDTO();
        dto.setTitulo(r.getTitulo());
        dto.setNbVisualisaciones(r.getNbVisualisaciones());
        return dto;
    }

    public static List<RecursosStatistiquesDTO> toStatDTOList(List<Recursos> recursos) {
        return recursos.stream().map(RecursosMapper::toStatDTO).collect(Collectors.toList());
    }
}
