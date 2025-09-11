package init.fast.hablamosele.recursos;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public final class RecursosMapper {

    private RecursosMapper() {}

    public static RecursosDTO toDTO(Recursos r) {
        if (r == null) {
            return null;
        }

        RecursosDTO dto = new RecursosDTO();
        dto.setId(r.getId());
        dto.setBanner(r.getBanner());
        dto.setTitulo(r.getTitulo());
        dto.setDescription(r.getDescription());
        dto.setTags(r.getTags());
        dto.setCategorias(r.getCategorias());
        dto.setNiveles(r.getNiveles());
        dto.setNbInfografias(r.getNbInfografias());
        dto.setNbCahiersActivite(r.getNbCahiersActivite());
        return dto;
    }

    public static List<RecursosDTO> toDTOList(List<Recursos> recursos) {
        if (recursos == null) {
            return null;
        }
        return recursos.stream()
                .map(RecursosMapper::toDTO)
                .collect(Collectors.toList());
    }
}